package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.*;
import org.example.exception.ForbiddenException;
import org.example.exception.InvalidDataException;
import org.example.exception.NotAuthenticatedException;
import org.example.exception.PasswordNotMatchesException;
import org.example.manager.ProductManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
public class ProductController {
    private ProductManager manager;


    @RequestMapping("/products.getAll")
    public List<ProductGetAllResponseDTO> getAll(int limit, long offset) throws InvalidDataException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.getAll(limit, offset);
    }

    @RequestMapping("/products.getById")
    public ProductGetByIdResponseDTO getById(long id) {
        return manager.getById(id);
    }

    @RequestMapping("/products.create")
    public ProductCreateResponseDTO create(ProductCreateRequestDTO requestDTO) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.create(requestDTO);
    }

    @RequestMapping("/products.update")
    public ProductUpdateResponseDTO update(ProductUpdateRequestDTO requestDTO) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        return manager.update(requestDTO);
    }

    @RequestMapping("/products.removeById")
    public ProductRemoveByIdResponseDTO removeById(long id) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        return manager.removeById(id);
    }

    @RequestMapping("/products.restoreById")
    public ProductRestoreByIResponseDTO restoreById(long id) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        return manager.restoreById(id);
    }

    @RequestMapping("/products.getAllRemoved")
    public List<ProductGetAllRemovedResponseDTO> getAllRemoved (int limit, long offset) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.getAllRemoved(limit, offset);
    }
}