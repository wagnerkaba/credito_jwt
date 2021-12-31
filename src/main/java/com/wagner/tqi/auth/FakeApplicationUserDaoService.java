package com.wagner.tqi.auth;

import com.google.common.collect.Lists;
import com.wagner.tqi.security.ApplicationUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUserDetails> selectApplicationUserByUsername(String username) {

        return getApplicationUsers()
                .stream().filter(applicationUserDetails -> username.equals(applicationUserDetails.getUsername()))
                .findFirst();
    }

    private List<ApplicationUserDetails> getApplicationUsers(){
        List<ApplicationUserDetails> applicationUserDetails = Lists.newArrayList(
                new ApplicationUserDetails(
                        ApplicationUserRole.STUDENT.getGrantedAuthorities(),
                        "ana",
                        passwordEncoder.encode("pass"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUserDetails(
                        ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
                        "tom",
                        passwordEncoder.encode("pass"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUserDetails(
                        ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        "linda",
                        passwordEncoder.encode("pass"),
                        true,
                        true,
                        true,
                        true
                )
        );
        return applicationUserDetails;
    }
}
