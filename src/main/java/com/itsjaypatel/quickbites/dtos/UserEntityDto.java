package com.itsjaypatel.quickbites.dtos;

import com.itsjaypatel.quickbites.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<Role> roles;
}
