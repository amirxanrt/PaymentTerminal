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

@AllArgsConstructor
@Component
public class BonusCardManager {
    private NamedParameterJdbcTemplate template;
    private Authenticator authenticator;



    public CardCreateResponseDTO create(CardCreateRequestDTO requestDTO) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException {
        Authentication authentication = authenticator.authenticate();

        if (!authentication.getRole().equals(Authentication.ROLE_ADMIN)){
            throw new ForbiddenException();
        }
        return template.queryForObject(
                //language=PostgreSQL
                """
            INSERT INTO  cards (user_id, city, phone_number, bonus_card, qr_code)
            VALUES (:user_id, :city, :phone_number, :bonus_card, :qr_code)
            RETURNING id, user_id, city, phone_number, bonus_card, qr_code
            """,
                Map.of(
                        "user_id", requestDTO.getUserId(),
                        "city", requestDTO.getCity(),
                        "phone_number", requestDTO.getPhoneNumber(),
                        "bonus_card", requestDTO.getBonusCard(),
                        "qr_code", requestDTO.getQrCode()
                ),
                BeanPropertyRowMapper.newInstance(CardCreateResponseDTO.class)
        );

    }

    public List<CardGetAllResponseDTO> getAll(int limit, long offset) throws InvalidDataException, NotAuthenticatedException, PasswordNotMatchesException, ForbiddenException {
        if (limit > 50) {
            throw new InvalidDataException();
        }
        if(limit <= 0){
            throw  new InvalidDataException();
        }
        if(offset < 0){
            throw new InvalidDataException();
        }

        Authentication authentication = authenticator.authenticate();
        if (authentication.isAnonymous()) {
            throw new ForbiddenException();
        }
        return template.query(
                //language=PostgreSQL
                """
                SELECT c.id, c.user_id, c.city, c.phone_number, c.bonus_card, c.qr_code, COALESCE(b.bonus, 0) bonus FROM cards c
                LEFT JOIN bonuses b on c.user_id = b.user_id
                WHERE removed = FALSE
                ORDER BY id
                LIMIT  :limit OFFSET :offset
                """,
                Map.of("limit", limit,
                        "offset", offset),
                BeanPropertyRowMapper.newInstance(CardGetAllResponseDTO.class)
        );
    }
}

