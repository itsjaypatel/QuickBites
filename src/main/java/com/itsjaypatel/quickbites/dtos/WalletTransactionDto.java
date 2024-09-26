package com.itsjaypatel.quickbites.dtos;

import com.itsjaypatel.quickbites.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransactionDto {

    private Long id;

    private String description;

    private TransactionType type;

    private Double amount;

    private LocalDateTime timestamp;
}
