package org.example.manager;


import lombok.AllArgsConstructor;
import org.example.authentication.Authentication;
import org.example.authenticator.Authenticator;
import org.example.dto.*;
import org.example.exception.ForbiddenException;
import org.example.exception.InvalidDataException;
import org.example.exception.NotAuthenticatedException;
import org.example.exception.PasswordNotMatchesException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ProductManager {
    private NamedParameterJdbcTemplate template;
    private Authenticator authenticator;

    public List<ProductGetAllResponseDTO> getAll(int limit, long offset) throws InvalidDataException, NotAuthenticatedException, PasswordNotMatchesException {
        final Authentication authentication = authenticator.authenticate();
        if (limit > 50) {
            throw new InvalidDataException();
        }
        if(limit <= 0){
            throw  new InvalidDataException();
        }
        if(offset < 0){
            throw new InvalidDataException();
        }

        return template.query(
                //language=PostgreSQL
                """
                SELECT id, name, price, qty, photo FROM products
                WHERE removed = FALSE
                ORDER BY id
                LIMIT  :limit OFFSET :offset
                """,
                Map.of("limit", limit,
                        "offset", offset),
                BeanPropertyRowMapper.newInstance(ProductGetAllResponseDTO.class)
        );
    }

    public ProductGetByIdResponseDTO getById(long id) {
     return template.queryForObject(
             //language=PostgreSQL
             """
             SELECT id, name, price, qty, photo FROM products
             WHERE removed = FALSE AND id = :id
             """,
             Map.of("id", id),
             BeanPropertyRowMapper.newInstance(ProductGetByIdResponseDTO.class)
     );
    }


    public ProductCreateResponseDTO create(ProductCreateRequestDTO requestDTO) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        final Authentication authentication = authenticator.authenticate();
        if (authentication.isAnonymous()) {
            throw new ForbiddenException();
        }
        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)){
            throw new ForbiddenException();
        }
            return template.queryForObject(
                    //language=PostgreSQL
                    """
                INSERT INTO  products (name, price, qty, photo)
                VALUES (:name, :price, :qty, :photo)
                RETURNING id, name, price, qty, photo
                """,
                    Map.of(
                            "name", requestDTO.getName(),
                            "price", requestDTO.getPrice(),
                            "qty", requestDTO.getQty(),
                            "photo", requestDTO.getPhoto()
                    ),
                    BeanPropertyRowMapper.newInstance(ProductCreateResponseDTO.class)
            );

    }

    public ProductUpdateResponseDTO update(ProductUpdateRequestDTO requestDTO) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        final Authentication authentication = authenticator.authenticate();
        if(authentication.isAnonymous()){
            throw new ForbiddenException();
        }
        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)){
            throw new ForbiddenException();
        }

            return template.queryForObject(
                    //language=PostgreSQL
                    """
                    UPDATE products
                    SET name = :name, price = :price, qty = :qty, photo = :photo
                    WHERE removed = FALSE AND id = :id
                    RETURNING  id, name, price, qty, photo
                    """,
                    Map.of(
                           "id", requestDTO.getId(),
                            "name", requestDTO.getName(),
                            "price", requestDTO.getPrice(),
                            "qty", requestDTO.getQty(),
                            "photo", requestDTO.getPhoto()
                    ),
                    BeanPropertyRowMapper.newInstance(ProductUpdateResponseDTO.class)
            );

    }

    public ProductRemoveByIdResponseDTO removeById(long id) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        final Authentication authentication = authenticator.authenticate();
        if (authentication.isAnonymous()){
            throw new ForbiddenException();
        }
        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)){
            throw new ForbiddenException();
        }
            return template.queryForObject(
                    //language=PostgreSQL
                    """
                    UPDATE products SET removed = TRUE 
                    WHERE id = :id
                    RETURNING id, name, price, qty, photo
                    """,
                    Map.of("id", id),
                    BeanPropertyRowMapper.newInstance(ProductRemoveByIdResponseDTO.class)
            );

    }

    public ProductRestoreByIResponseDTO restoreById(long id) throws NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        final Authentication authentication = authenticator.authenticate();
        if (authentication.isAnonymous()){
            throw new ForbiddenException();
        }
        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)){
            throw new ForbiddenException();
        }
            return template.queryForObject(
                    //language=PostgreSQL
                    """
                    UPDATE products SET removed = FALSE
                    WHERE id = :id
                    RETURNING id, name, price, qty, photo
                    """,
                    Map.of("id", id),
                    BeanPropertyRowMapper.newInstance(ProductRestoreByIResponseDTO.class)
            );

    }

    public List<ProductGetAllRemovedResponseDTO> getAllRemoved(int limit, long offset) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        final Authentication authentication = authenticator.authenticate();
        if (authentication.isAnonymous()) {
            throw new ForbiddenException();
        }
        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)){
            throw new ForbiddenException();
        }
            return template.query(
                    //language=PostgreSQL
                    """
                SELECT id, name, price, qty, photo FROM products
                WHERE removed = TRUE
                ORDER BY  id
                LIMIT :limit OFFSET :offset
                """,
                Map.of("limit", limit,
                        "offset",offset),
                BeanPropertyRowMapper.newInstance(ProductGetAllRemovedResponseDTO.class)
        );

    }
}