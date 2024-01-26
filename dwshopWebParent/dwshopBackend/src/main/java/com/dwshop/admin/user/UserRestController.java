package com.dwshop.admin.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    private final UserService service;
    public UserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("email") String email){  //@Param: used to bind the HTTP request parameter named email to the method parameter email

        return service.isEmailUnique(email) ? "OK" : "Duplicated"; //ternary operator ? : is used to return one of two values based on a boolean condition. If the condition is true, it returns the value before the :. If the condition is false, it returns the value after the :.

    }


}
