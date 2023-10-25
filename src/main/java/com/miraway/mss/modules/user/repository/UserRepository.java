package com.miraway.mss.modules.user.repository;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

import com.miraway.mss.client.AuthorizedFeignClient;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.user.dto.UserDTO;
import com.miraway.mss.web.rest.responses.APIResponse;
import javax.validation.constraints.Size;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedFeignClient(name = "uaa")
public interface UserRepository {
    @GetMapping(value = "/api/internal/users/getUserByLogin")
    APIResponse<UserDTO> getUserByLogin(@RequestParam @Size(max = STRING_MAX_LENGTH) String login) throws ResourceNotFoundException;
}
