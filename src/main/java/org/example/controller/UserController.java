package org.example.controller;


import lombok.AllArgsConstructor;
import org.example.dto.*;
import org.example.exception.ForbiddenException;
import org.example.exception.InvalidDataException;
import org.example.exception.NotAuthenticatedException;
import org.example.exception.PasswordNotMatchesException;
import org.example.manager.UserManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private UserManager manager;

    @RequestMapping("/users.getAll")
    public List<UserGetAllResponseDTO> getAll(int limit, long offset) throws InvalidDataException, NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        return manager.getAll(limit, offset);
    }

    @RequestMapping("/users.getById")
    public UserGetByIdResponseDTO getById(long id) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        return manager.getById(id) ;
    }

    @RequestMapping("/users.register")
    public UserRegisterResponseDTO register(UserRegisterRequestDTO requestDTO) {
        return manager.register(requestDTO);
    }

    @RequestMapping("/users.me")
    public UserMeResponseDTO me() throws NotAuthenticatedException, PasswordNotMatchesException {
        return manager.me();
    }

    @RequestMapping("/users.removeById")
    public UserRemoveByIdResponseDTO removeById(long id) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.removeById(id);
    }

    @RequestMapping("/users.restoreById")
    public UserRestoreByIdResponseDTO restoreById(long id) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.restoreById(id);
    }

}
