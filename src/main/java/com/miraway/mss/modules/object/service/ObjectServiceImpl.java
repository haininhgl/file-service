package com.miraway.mss.modules.object.service;

import com.miraway.mss.modules.common.exception.BadRequestException;
import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.InternalServerException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.dto.filter.ObjectFilter;
import com.miraway.mss.modules.object.entity.Object;
import com.miraway.mss.modules.object.entity.ObjectUsage;
import com.miraway.mss.modules.object.repository.ObjectRepository;
import com.miraway.mss.modules.organization.dto.OrganizationDTO;
import com.miraway.mss.modules.organization.service.OrganizationService;
import com.miraway.mss.modules.user.dto.UserDTO;
import com.miraway.mss.modules.user.service.UserService;
import com.miraway.mss.web.rest.request.MoveFileRequest;
import com.miraway.mss.web.rest.request.ObjectRequest;
import com.miraway.mss.web.rest.request.RenameObjectRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.miraway.mss.constants.Constants.THUMBNAIL_PATH;

@Service
public class ObjectServiceImpl implements ObjectService {

    private final ObjectRepository objectRepository;

    private final ObjectUsageService objectUsageService;

    private final UserService userService;

    private final OrganizationService organizationService;

    public ObjectServiceImpl(ObjectRepository objectRepository, ObjectUsageService objectUsageService, UserService userService, OrganizationService organizationService) {
        this.objectRepository = objectRepository;
        this.objectUsageService = objectUsageService;
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @Override
    public Page<Object> getObjectList(ObjectFilter filter, Pageable pageable) throws ForbiddenException, InternalServerException, ResourceNotFoundException {
        UserDTO currentUser = userService.getCurrentLoginUser();
        OrganizationDTO currentUserOrganization = currentUser.getOrganization();
        Set<String> filterOrganizationIds = filter.getOrganizationIds();

        if (organizationService.isRootOrganization(currentUserOrganization)) {
            return objectRepository.getObjectList(filter, pageable);
        }
        Set<String> authorizedOrganizationIdList = organizationService.getChildrenIdsByIds(Set.of(currentUserOrganization.getId()));

        if (CollectionUtils.isEmpty(filterOrganizationIds)) {
            filter.setOrganizationIds(authorizedOrganizationIdList);
            return objectRepository.getObjectList(filter, pageable);
        }

        filterOrganizationIds.retainAll(authorizedOrganizationIdList);

        filter.setOrganizationIds(organizationService.getChildrenIdsByIds(filterOrganizationIds));
        return objectRepository.getObjectList(filter, pageable);
    }

    @Override
    public Object createFolder(ObjectRequest request) throws BadRequestException {
        boolean folderExists = objectRepository.existsByDisplayNameAndParentId(
            request.getDisplayName(), request.getParentId());

        if (folderExists) {
            throw new BadRequestException("Folder with the same displayName and parentId already exists.");
        }
        Object object = new Object();
        BeanUtils.copyProperties(request, object);
        object.setName(UUID.randomUUID().toString().substring(0, 10));
        object.setThumbnailPath(THUMBNAIL_PATH);
        return objectRepository.save(object);
    }

    public Object getById(String id) throws ResourceNotFoundException {
        Object object = objectRepository.findById(id).orElse(null);
        if (object == null) {
            throw new ResourceNotFoundException("Object not found!");
        }

        return object;
    }

    private boolean isNameConflict(String displayName, String parentId) {
        List<Object> objectsInSameFolder = objectRepository.findByParentId(parentId);

        return objectsInSameFolder.stream()
            .anyMatch(obj -> obj.getDisplayName().equals(displayName));
    }

    @Override
    public Object updateById(String id, RenameObjectRequest request) throws BadRequestException, ResourceNotFoundException {
        Object object = getById(id);
        if (isNameConflict(request.getDisplayName(), object.getParentId())) {
            throw new BadRequestException("Tên mới trùng với một tên đã tồn tại trong thư mục.");
        }
        BeanUtils.copyProperties(request, object);
        objectRepository.save(object);

        return object;
    }

    public String findNameById(String id) throws ResourceNotFoundException {
        Object object = getById(id);
        return object.getName();
    }

    public String getPathById(String id) throws ResourceNotFoundException {
        Object object = getById(id);
        return object.getPath();
    }

    @Override
    public List<Object> updateFile(MoveFileRequest request) throws ResourceNotFoundException {

        Set<String> sourceObjects = request.getIdSources();
        Object targetObject = getById(request.getIdTarget());
        List<Object> movedObjects = new ArrayList<>();

        for (String sourceObjectId : sourceObjects) {
            Object sourceObject = getById(sourceObjectId);

            if (sourceObjectId == null || targetObject == null) {
                throw new ResourceNotFoundException("Không tìm thấy tài nguyên");
            } else {
                String targetPath = getPathById(request.getIdTarget());
                String name = findNameById(request.getIdSources().toString());
                sourceObject.setPath(targetPath + File.separator + name);
                objectRepository.save(sourceObject);
                movedObjects.add(sourceObject);
            }
        }

        return movedObjects;
    }

    @Override
    public List<String> softDelete(Set<String> ids) throws ResourceNotFoundException, BadRequestException {
        List<String> deletedObjectIds = new ArrayList<>();
        for (String id : ids) {
            Object object = getById(id);

            if (object == null) {
                throw new ResourceNotFoundException("Object with ID " + id + " does not exist or is in use.");
            }
        }

        List<ObjectUsage> objectUsages = objectUsageService.getByObjectId(ids);

        List<ObjectUsage> deletable = objectUsages.stream()
            .filter(ObjectUsage::isDeletable)
            .collect(Collectors.toList());

        if (deletable.isEmpty()) {
            throw new BadRequestException("Không có đối tượng nào để xóa");
        }

        Set<String> objectIdDeletable = deletable.stream()
            .map(objectUsage -> objectUsage.getObject().getId())
            .collect(Collectors.toSet());

        objectRepository.updateIsDeleted(objectIdDeletable);

        StringBuilder builder = new StringBuilder();
        for (String id : ids) {
            Object object = getById(id);
            builder.append(object.getDisplayName()).append(", ");
        }

        deletedObjectIds.add(String.valueOf(builder));
        return deletedObjectIds;
    }
}
