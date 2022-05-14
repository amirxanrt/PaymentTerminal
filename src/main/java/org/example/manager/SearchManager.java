package org.example.manager;


import lombok.AllArgsConstructor;
import org.example.authenticator.Authenticator;
import org.example.dto.ProductSearchResponseDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class SearchManager {
    private NamedParameterJdbcTemplate template;

    public List<ProductSearchResponseDTO> search(String query, String language, int limit, long offset) {
        return template.query(
                //language=PostgreSQL
                """
                SELECT id, name, price, qty, photo From products
                WHERE removed = FALSE
                AND to_tsvector(CAST(:language  AS regconfig), name || ' ' || products.name) @@ to_tsquery(CAST(:language AS regconfig), :query)
                ORDER BY id
                LIMIT :limit OFFSET :offset
                """,
                Map.of(
                        "query", query,
                        "language", language,
                        "limit", limit,
                        "offset", offset
                ),
                BeanPropertyRowMapper.newInstance(ProductSearchResponseDTO.class)
        );
    }
}
