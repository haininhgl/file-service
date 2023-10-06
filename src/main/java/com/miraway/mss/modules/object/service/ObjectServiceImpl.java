package com.miraway.mss.modules.object.service;

import com.miraway.mss.modules.common.exception.BadRequestException;
import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.dto.filter.ObjectFilter;
import com.miraway.mss.modules.object.entity.Object;
import com.miraway.mss.modules.object.entity.ObjectUsage;
import com.miraway.mss.modules.object.repository.ObjectRepository;
import com.miraway.mss.web.rest.request.MoveFileRequest;
import com.miraway.mss.web.rest.request.ObjectRequest;
import com.miraway.mss.web.rest.request.RenameObjectRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public ObjectServiceImpl(ObjectRepository objectRepository, ObjectUsageService objectUsageService) {
        this.objectRepository = objectRepository;
        this.objectUsageService = objectUsageService;
    }

    @Override
    public Page<Object> getObjectList(ObjectFilter filter, Pageable pageable) {
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
    public List<Object> updateFile(List<MoveFileRequest> requests) throws ResourceNotFoundException, IOException {
        List<Object> movedObjects = new ArrayList<>();

        for (MoveFileRequest request : requests) {

            Object sourceObject = getById(request.getIdSource());
            Object targetObject = getById(request.getIdTarget());

            if (sourceObject == null || targetObject == null) {
                throw new ResourceNotFoundException("Không tìm thấy tài nguyên");
            } else {
                String sourcePath = getPathById(request.getIdSource());
                String targetPath = getPathById(request.getIdTarget());
                String name = findNameById(request.getIdSource());
                Path source = Paths.get(sourcePath);
                Path target = Paths.get(targetPath);
                Files.move(source, target.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                sourceObject.setPath(targetPath + File.separator + name);
                objectRepository.save(sourceObject);
            }
        }

        return movedObjects;
    }

    public List<String> softDelete(Set<String> ids) throws ResourceNotFoundException, ForbiddenException {
        List<String> deletedObjectIds = new ArrayList<>();
        StringBuilder builder = new StringBuilder("Những file đã xóa: ");

        for (String id : ids) {
            Object object = getById(id);

            if (object == null) {
                throw new ResourceNotFoundException("Object with ID " + id + " does not exist or is in use.");
            }

            List<ObjectUsage> objectUsages = objectUsageService.getByObjectId(id);
            List<ObjectUsage> deletable = objectUsages.stream()
                .filter(ObjectUsage::isDeletable)
                .collect(Collectors.toList());

            objectUsages.removeAll(deletable);

            if (!CollectionUtils.isEmpty(objectUsages)) {
                builder.append("Những file này không được xóa: ");
                for (ObjectUsage o : objectUsages) {
                    builder.append(o.getObject().getDisplayName()).append(", ");
                }
                throw new ForbiddenException(builder.toString());
            }

            object.setDeleted(true);
            objectRepository.save(object);
        }

        for (String id : ids) {
            Object object = getById(id);
            builder.append(object.getDisplayName()).append(", ");
        }

        deletedObjectIds.add(String.valueOf(builder));

        return deletedObjectIds;
    }
}
