package org.example.controller;


import lombok.AllArgsConstructor;
import org.example.dto.*;
import org.example.exception.*;
import org.example.manager.PurchasesManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PurchasesController {
    private PurchasesManager manager;

    @RequestMapping("/purchase.make")
    public PurchaseMakeResponseDTO make(long productId, int qty) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException, ProductOutOfStockException {
        return manager.make(productId, qty);
    }
    @RequestMapping("/purchase.getAll")
    public List<PurchaseGetAllResponseDTO> getAll(int limit, long offset) throws NotAuthenticatedException, PasswordNotMatchesException, InvalidDataException, ForbiddenException {
        return manager.getAll(limit, offset);
    }

    @RequestMapping("/purchase.stats")
    public PurchaseStatsResponseDTO stats() throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        return manager.stats();
    }


}
