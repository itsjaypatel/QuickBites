package com.itsjaypatel.quickbites.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

    private Double balance;

    private UserEntityDto user;

    private List<WalletTransactionDto> transactions;
}
