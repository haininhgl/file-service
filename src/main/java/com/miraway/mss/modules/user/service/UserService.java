package com.miraway.mss.modules.user.service;

import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.InternalServerException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.user.dto.UserDTO;

public interface UserService {
    UserDTO getCurrentLoginUser() throws ResourceNotFoundException, ForbiddenException, InternalServerException;
}
