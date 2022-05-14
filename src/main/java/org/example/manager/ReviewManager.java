package org.example.manager;


import lombok.AllArgsConstructor;
import org.example.authentication.Authentication;
import org.example.authenticator.Authenticator;
import org.example.dto.ReviewGetAllResponseDTO;
import org.example.dto.ReviewResponseDTO;
import org.example.exception.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class ReviewManager {
    private NamedParameterJdbcTemplate template;
    private Authenticator authenticator;


    public ReviewResponseDTO review(long productId, String review) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException, NotMakeReviewException {
        Authentication authentication = authenticator.authenticate();

        if (!authentication.getRole().equals(Authentication.ROLE_USER)){
            throw  new NotMakeReviewException();
        }
        return template.queryForObject(
                //language=PostgreSQL
                """
                    INSERT INTO reviews ( user_id, product_id, review)
                    VALUES (:user_id, :product_id, :review)
                    RETURNING  id, user_id, product_id, review
                    """,
                Map.of("user_id" , authentication.getId(),
                        "product_id", productId,
                        "review", review),
                BeanPropertyRowMapper.newInstance(ReviewResponseDTO.class)


        );


    }

    public List<ReviewGetAllResponseDTO> getAll(int limit, long offset) throws NotAuthenticatedException, PasswordNotMatchesException, NotMakeReviewException, InvalidDataException, ForbiddenException {
           Authentication authentication = authenticator.authenticate();
        if (limit > 40) {
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
                   SELECT r.user_id, r.product_id, r.review, COALESCE (u.login, 'anonymous') user_login FROM reviews r
                   LEFT JOIN users u on u.id = r.user_id
                   ORDER BY r.id
                   LIMIT :limit OFFSET :offset
                    
                    """,
                Map.of("limit", limit,
                        "offset", offset),
                BeanPropertyRowMapper.newInstance(ReviewGetAllResponseDTO.class)

        );


    }
}
