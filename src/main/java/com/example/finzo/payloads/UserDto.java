package com.example.finzo.payloads;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String password;
    @Nonnull
    private Integer roleId;
}
