package com.wagner.tqi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationUserPermission {

    PERSON_READ("person:read"),
    PERSON_WRITE("person:write"),
    LOAN_READ("loan:read"),
    LOAN_WRITE("loan:write");

    private final String permission;


}

