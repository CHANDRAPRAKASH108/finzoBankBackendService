package com.example.finzo.entity;

import com.example.finzo.utils.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserAccountEntity {
    @Id
    private String id;
    private String name;
    private String address;
    @Column(unique = true)
    private String email;
    @Column(unique = true, name = "contact_number")
    private String contactNumber;
    @Column(unique = true, name = "pan_number")
    private String panNumber;
    @Column(unique = true, name = "aadhar_number")
    private String aadharNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;
    private Integer balance;
}
