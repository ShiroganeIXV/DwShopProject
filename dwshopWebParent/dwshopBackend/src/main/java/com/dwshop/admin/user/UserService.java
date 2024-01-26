package com.dwshop.admin.user;

import com.dwshop.common.entity.Role;
import com.dwshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    // PasswordEncoder is a Spring Security interface for encrypting passwords.
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        List<User> listUsers = (List<User>) userRepo.findAll();
        return listUsers;
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public void save(User user) {
        encodePassword(user);
        userRepo.save(user);
    }

    public boolean isEmailUnique(String email){
        User userByEmail= userRepo.getUserByEmail(email);
        return userByEmail == null; // if True (userByEmail is null), then the email is unique
    }

    // private Methods
    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }


}
