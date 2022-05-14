package org.example.manager;


import lombok.AllArgsConstructor;
import org.example.authentication.Authentication;
import org.example.authenticator.Authenticator;
import org.example.dto.*;
import org.example.exception.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class PurchasesManager {
    private NamedParameterJdbcTemplate template;
    private Authenticator authenticator;


    public PurchaseMakeResponseDTO make(long productId, int qty) throws NotAuthenticatedException, PasswordNotMatchesException, ProductOutOfStockException {
        Authentication authentication = authenticator.authenticate();

        ProductGetByIdResponseDTO product = template.queryForObject(
                //language=PostgreSQL
                """
                        SELECT id, name, price, qty, photo FROM products
                        WHERE removed = FALSE AND id = :id
                        """,
                Map.of("id", productId),
                BeanPropertyRowMapper.newInstance(ProductGetByIdResponseDTO.class)
        );

        if (product.getQty() < qty) {
            throw new ProductOutOfStockException();
        }

        template.update(
                //language=PostgreSQL
                """
                        UPDATE products SET qty = qty - :qty
                        WHERE id = :id
                        """,
                Map.of("id", productId,
                        "qty", qty)

        );
        int sum = product.getPrice() * qty;
        PurchaseMakeResponseDTO responseDTO = template.queryForObject(
                //language=PostgreSQL
                """
                        INSERT INTO purchases  (product_id, product_name, qty, product_price, user_id, sum)
                        VALUES (:product_id, :product_name, :qty, :product_price, :user_id, :sum)
                        RETURNING id, product_id, product_name, qty, product_price, user_id, sum
                        """,
                Map.of("product_id", product.getId(),
                        "product_name", product.getName(),
                        "qty", qty,
                        "product_price", product.getPrice(),
                        "user_id", authentication.getId(),
                        "sum", sum),

                BeanPropertyRowMapper.newInstance(PurchaseMakeResponseDTO.class)
        );

        if (!authentication.isAnonymous()) {
            int bonus = product.getPrice() * qty * 2 / 100;
            template.update(
                    //language=PostgreSQL
                    """
                            INSERT INTO bonuses (user_id, purchase_id, bonus)
                            VALUES (:user_id, :purchase_id, :bonus)
                               
                            """,
                    Map.of("user_id", authentication.getId(),
                            "purchase_id", responseDTO.getId(),
                            "bonus", bonus
                    )
            );


        }
        return responseDTO;
    }

    public List<PurchaseGetAllResponseDTO> getAll(int limit, long offset) throws NotAuthenticatedException, PasswordNotMatchesException, InvalidDataException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();
        if (limit > 50) {
            throw new InvalidDataException();
        }
        if (limit <= 0) {
            throw new InvalidDataException();
        }
        if (offset < 0) {
            throw new InvalidDataException();
        }
        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }
        return template.query(
                //language=PostgreSQL
                """
                        SELECT p.product_id, p.product_name, p.qty, p.product_price, p.user_id, p.sum, COALESCE(u.login, 'anonymous') user_login, COALESCE(b.bonus, 0) bonus FROM purchases p
                        LEFT JOIN users u ON u.id = p.user_id
                        LEFT JOIN bonuses b ON b.purchase_id = p.id
                        ORDER BY p.id
                        LIMIT  :limit OFFSET :offset
                        """,
                Map.of("limit", limit,
                        "offset", offset),
                BeanPropertyRowMapper.newInstance(PurchaseGetAllResponseDTO.class)
        );


    }

    public PurchaseStatsResponseDTO stats() throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        Authentication authentication = authenticator.authenticate();

        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        PurchaseStatsResponseDTO stats = template.queryForObject(
                //language=PostgreSQL
                """
                        SELECT SUM(qty * product_price) sum,
                        MIN(qty * product_price) min,
                        MAX(qty * product_price) max,
                        AVG(qty * product_price) avg FROM purchases
                        """,
                Map.of(),
                BeanPropertyRowMapper.newInstance(PurchaseStatsResponseDTO.class)
        );
        return stats;
    }

}