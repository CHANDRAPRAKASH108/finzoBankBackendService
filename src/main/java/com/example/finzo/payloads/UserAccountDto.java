package com.example.finzo.payloads;

import com.example.finzo.utils.AccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAccountDto {
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotEmpty
    private String email;
    @NotEmpty
    private String contactNumber;
    @NotEmpty
    private String panNumber;
    @NotEmpty
    private String aadharNumber;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @PositiveOrZero(message = "Value must be positive or zero")
    private Integer balance;
    private String password;
}
