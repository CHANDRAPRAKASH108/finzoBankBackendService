package com.example.finzo.payloads;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAccountDto {
    private Integer id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotEmpty
    private String email;
    @NotEmpty
    private String contact_number;
    @NotEmpty
    private String pan_number;
    @NotEmpty
    private String aadhar_number;
    private String status;
    @NotEmpty
    private String balance;
}
