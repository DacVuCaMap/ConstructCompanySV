package com.app.ConStructCompany.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Token")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "tokenString")
    private String tokenString;
    @Column(name = "expiration")
    private boolean expiration;
    @Column(name = "revoked")
    private boolean revoked;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accId")
    private Account account;
}