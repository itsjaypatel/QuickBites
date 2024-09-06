package com.itsjaypatel.zomatoapp.dtos;

import com.itsjaypatel.zomatoapp.enums.Role;
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
