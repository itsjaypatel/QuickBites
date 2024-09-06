package com.itsjaypatel.zomatoapp.dtos;

import com.itsjaypatel.zomatoapp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private Collection<Role> roles;
}
