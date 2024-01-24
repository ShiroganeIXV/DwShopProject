package com.dwshop.admin.user;

import com.dwshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    // list all users
    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    // add new user
    @GetMapping("/users/new")
    public String newUser(Model model){
        User user = new User();
        // bind USER object with model attribute
        model.addAttribute("user", user);
        return "user_form";
    }
        // save user
    @PostMapping("/users/save")
    public String saveUser(User user){
        System.out.println(user);
        return "redirect:/users";
    }



}