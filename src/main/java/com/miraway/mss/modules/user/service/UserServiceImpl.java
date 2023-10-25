package com.miraway.mss.modules.user.service;

import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.InternalServerException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.organization.dto.OrganizationDTO;
import com.miraway.mss.modules.user.dto.UserDTO;
import com.miraway.mss.modules.user.repository.UserRepository;
import com.miraway.mss.security.SecurityUtils;
import com.miraway.mss.web.rest.responses.APIResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getCurrentLoginUser() throws ResourceNotFoundException, ForbiddenException, InternalServerException {
        String login = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (login == null) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng đang đăng nhập.");
        }

        APIResponse<UserDTO> response;
        try {
            response = userRepository.getUserByLogin(login);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng.");
        } catch (Exception e) {
            throw new InternalServerException();
        }

        if (response == null || response.getData() == null) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng.");
        }

        UserDTO user = response.getData();
        if (!user.isActivated()) {
            throw new ForbiddenException();
        }

        OrganizationDTO organization = user.getOrganization();
        if (organization == null || StringUtils.isBlank(organization.getId()) || !organization.isActivated()) {
            throw new ForbiddenException("Người dùng đang đăng nhập thuộc tổ chức không hợp lệ.");
        }

        return user;
    }
}
