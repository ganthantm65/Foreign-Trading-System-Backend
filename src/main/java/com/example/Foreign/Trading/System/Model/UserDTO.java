package com.example.Foreign.Trading.System.Model;

import lombok.Data;

@Data
public class UserDTO {
    private String userName;
    private String email;
    private String password;
    private String companyName;
    private String country;
    private String bankName;
    private String swiftCode;
    private String userType;

    public UserDTO(String userName, String email, String password, String companyName, String country, String bankName, String swiftCode,String userType) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.country = country;
        this.bankName = bankName;
        this.swiftCode = swiftCode;
        this.userType= userType;
    }
}
