package com.dwshop.admin.user;

import com.dwshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> listAll() {
        List<User> listUsers = (List<User>) repo.findAll();
        return listUsers;
    }
}
