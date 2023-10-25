package com.miraway.mss.modules.object.service;

import static com.miraway.mss.constants.Constants.THUMBNAIL_PATH;

import com.miraway.mss.modules.common.exception.BadRequestException;
import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.InternalServerException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.dto.filter.ObjectFilter;
import com.miraway.mss.modules.object.entity.Object;
import com.miraway.mss.modules.object.entity.ObjectUsage;
import com.miraway.mss.modules.object.enumaration.ObjectType;
import com.miraway.mss.modules.object.repository.ObjectRepository;
import com.miraway.mss.modules.organization.dto.OrganizationDTO;
import com.miraway.mss.modules.organization.service.OrganizationService;
import com.miraway.mss.modules.user.dto.UserDTO;
import com.miraway.mss.modules.user.service.UserService;
import com.miraway.mss.web.rest.request.MoveFileRequest;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ObjectServiceImpl implements ObjectService {

    private final ObjectRepository objectRepository;

    private final ObjectUsageService objectUsageService;

    private final UserService userService;

    private final OrganizationService organizationService;

    public ObjectServiceImpl(
        ObjectRepository objectRepository,
        ObjectUsageService objectUsageService,
        UserService userService,
        OrganizationService organizationService
    ) {
        this.objectRepository = objectRepository;
        this.objectUsageService = objectUsageService;
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @Override
    public Page<Object> getObjectList(ObjectFilter filter, Pageable pageable)
        throws ForbiddenException, InternalServerException, ResourceNotFoundException {
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
    public Object createFolder(Object object)
        throws BadRequestException, ForbiddenException, InternalServerException, ResourceNotFoundException {
        validateObject(object);

        UserDTO currentUser = userService.getCurrentLoginUser();
        OrganizationDTO currentUserOrganization = currentUser.getOrganization();
        String folderOrganizationId = object.getOrganizationId();

        Set<String> authorizedOrganizationIdList = organizationService.getChildrenIdsByIds(Set.of(currentUserOrganization.getId()));

        if (!authorizedOrganizationIdList.contains(folderOrganizationId)) {
            throw new ForbiddenException("Người dùng không có quyền tạo thư mục ");
        }

        object.setName(UUID.randomUUID().toString().substring(0, 10));
        object.setThumbnailPath(THUMBNAIL_PATH);
        return objectRepository.save(object);
    }

    @Override
    public Object getById(String id) throws ResourceNotFoundException {
        Object object = objectRepository.findById(id).orElse(null);
        if (object == null) {
            throw new ResourceNotFoundException("Không tìm thấy tài nguyên");
        }

        return object;
    }

    private boolean isObjectExisted(String displayName, String updatedParentId) {
        return objectRepository.existsByDisplayNameIgnoreCaseAndParentId(displayName, updatedParentId);
    }

    private void validateObject(Object object) throws BadRequestException {
        String parentId = object.getParentId();
        boolean isUpdate = StringUtils.isNotBlank(parentId);
        boolean isObjectExisted = isObjectExisted(object.getDisplayName(), isUpdate ? parentId : null);

        if (isObjectExisted) {
            throw new BadRequestException("Tên mới trùng với một tên đã tồn tại trong thư mục.");
        }
    }

    @Override
    public Object updateById(String id, Object object) throws BadRequestException {
        validateObject(object);
        objectRepository.save(object);
        return object;
    }

    @Override
    public List<Object> updateFile(MoveFileRequest request) throws ResourceNotFoundException, BadRequestException {
        Set<String> sourceObjects = request.getSourceIds();
        Object targetObject = getById(request.getIdTarget());

        if (targetObject.getType() != ObjectType.FOLDER) {
            throw new ResourceNotFoundException("Đối tượng di chuyển đến phải là thư mục");
        }

        List<Object> movedObjects = objectRepository.getByIdIn(sourceObjects);
        if (CollectionUtils.isEmpty(movedObjects)) {
            throw new BadRequestException("Không có đối tượng nào để di chuyển");
        }

        for (String sourceObjectId : sourceObjects) {
            Object sourceObject = getById(sourceObjectId);
            movedObjects.add(sourceObject);
        }

        for (Object sourceObject : movedObjects) {
            String targetPath = targetObject.getPath();
            String name = sourceObject.getName();
            sourceObject.setPath(targetPath + File.separator + name);
        }

        objectRepository.saveAll(movedObjects);
        return movedObjects;
    }

    @Override
    public List<String> softDelete(Set<String> ids) throws ResourceNotFoundException, BadRequestException {
        List<Object> objects = objectRepository.getByIdIn(ids);
        if (objects.isEmpty()) {
            throw new BadRequestException("Yêu cầu không hợp lệ.");
        }

        List<ObjectUsage> deletable = objectUsageService.findByIdInAndIsDeleable(ids);

        if (deletable.isEmpty()) {
            throw new BadRequestException("Không có đối tượng nào để xóa");
        }

        Set<String> objectIdDeletable = deletable.stream().map(objectUsage -> objectUsage.getObject().getId()).collect(Collectors.toSet());

        objectRepository.softDeleteObjects(objectIdDeletable);

        StringBuilder builder = new StringBuilder();
        int count = 1;

        for (String id : objectIdDeletable) {
            Object object = getById(id);
            if (count > 1) {
                builder.append(", ");
            }
            builder.append(object.getDisplayName());
            count++;
        }

        return Collections.singletonList(builder.toString());
    }
}
