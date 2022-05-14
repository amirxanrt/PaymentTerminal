package org.example.controller;


import lombok.AllArgsConstructor;
import org.example.dto.CardCreateRequestDTO;
import org.example.dto.CardCreateResponseDTO;
import org.example.dto.CardGetAllResponseDTO;
import org.example.dto.ProductGetAllResponseDTO;
import org.example.exception.ForbiddenException;
import org.example.exception.InvalidDataException;
import org.example.exception.NotAuthenticatedException;
import org.example.exception.PasswordNotMatchesException;
import org.example.manager.BonusCardManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BonusCardController {
    private BonusCardManager manager;


    @RequestMapping("/card.create")
    public CardCreateResponseDTO create (CardCreateRequestDTO requestDTO) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        return manager.create(requestDTO);
    }
    @RequestMapping("/card.getAll")
    public List<CardGetAllResponseDTO> getAll(int limit, long offset) throws InvalidDataException, NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        return manager.getAll(limit, offset);
    }

}
