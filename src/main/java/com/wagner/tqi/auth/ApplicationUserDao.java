package com.wagner.tqi.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    public Optional<ApplicationUserDetails> selectApplicationUserByUsername(String username);
}
