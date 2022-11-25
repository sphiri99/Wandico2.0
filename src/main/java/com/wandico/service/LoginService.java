package com.wandico.service;

import com.wandico.entity.UserDetails;
import com.wandico.repo.LoginRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginService {
    private LoginRepo loginRepo;

    public LoginService(LoginRepo loginRepo) {
        this.loginRepo = loginRepo;
    }

    public UserDetails getUserDetails(String email) {
        return loginRepo.findByUsername(email);

    }

    public void saveUserDetails(UserDetails userDetails) {

        userDetails.setPassword(userDetails.getPassword());
        userDetails.setPasswordConfirm(userDetails.getPasswordConfirm());
        loginRepo.save(userDetails);

    }

    public UserDetails findByUsername(String username) {
        return loginRepo.findByUsername(username);
    }

    public List<UserDetails> getUserDetailsList() {
        return loginRepo.findAll();

    }

}
