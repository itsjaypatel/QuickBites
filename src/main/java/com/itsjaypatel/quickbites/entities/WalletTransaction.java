package com.itsjaypatel.quickbites.entities;

import com.itsjaypatel.quickbites.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private TransactionType type;

    private Double amount;

    private String paymentId;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
