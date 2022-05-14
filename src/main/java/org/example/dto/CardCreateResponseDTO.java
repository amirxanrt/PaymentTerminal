package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardCreateResponseDTO {
    private long userId;
    private String city;
    private long phoneNumber;
    private long bonusCard;
    private String qrCode;
}
