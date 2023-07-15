package com.example.finzo.entity;
import com.example.finzo.utils.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAccountResponse {
    private String id;
    private String name;
    private String address;
    private String email;
    private String contactNumber;
    private String panNumber;
    private String aadharNumber;
    private AccountStatus status;
    private Integer balance;
    private String userId;
}
