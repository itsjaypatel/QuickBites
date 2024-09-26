package com.itsjaypatel.quickbites.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "firstname must not be blank")
    @Size(min = 3, message = "firstname length must have atleast 3 character")
    private String firstName;

    @NotBlank(message = "firstname must not be blank")
    @Size(min = 3, message = "firstname length must have atleast 3 character")
    private String lastName;

    @NotBlank(message = "email must not be blank")
    @Email(message = "email is not in valid format")
    private String email;

    @NotBlank(message = "password must not be blank")
    @Size(min = 5, message = "password length must have atleast 5 character")
    private String password;


    private PointDto address;
}
