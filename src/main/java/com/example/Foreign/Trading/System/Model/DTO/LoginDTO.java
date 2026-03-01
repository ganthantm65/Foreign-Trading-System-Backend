package com.example.Foreign.Trading.System.Model.DTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginDTO {
    private String email;
    private String password;
    private String role;

    public LoginDTO(String email,String password,String role){
        this.email=email;
        this.password=password;
        this.role=role;
    }
}
