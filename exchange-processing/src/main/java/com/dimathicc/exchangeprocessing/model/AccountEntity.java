package com.dimathicc.exchangeprocessing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ACCOUNT")
public class AccountEntity {
    @Id
    @SequenceGenerator(name = "account_generator", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    private Long id;
    @Column(name = "USER_ID", nullable = false)
    private Long userId;
    @Column(name = "CURRENCY_CODE", length = 3,nullable = false)
    private String currencyCode;
    @Column(name = "BALANCE", length = 3,nullable = false)
    private BigDecimal balance;
}
