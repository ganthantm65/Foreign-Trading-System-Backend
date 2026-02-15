package com.example.Foreign.Trading.System.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VerificationOTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otp_id;

    private String email;

    private Integer otp;

    private LocalDateTime localDateTime;
}
