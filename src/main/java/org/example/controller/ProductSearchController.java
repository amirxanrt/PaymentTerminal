package org.example.controller;


import lombok.AllArgsConstructor;
import org.example.dto.ProductSearchResponseDTO;
import org.example.manager.SearchManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductSearchController {
    private SearchManager manager;

    @RequestMapping("products.search")
    public List<ProductSearchResponseDTO> search(String query, String language, int limit, long offset){
        return manager.search(query, language, limit, offset);
    }
}
