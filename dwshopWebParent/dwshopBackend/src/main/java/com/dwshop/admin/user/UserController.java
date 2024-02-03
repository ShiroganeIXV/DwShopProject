package com.dwshop.admin.user;

import com.dwshop.common.entity.Role;
import com.dwshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //! Create new user
    @GetMapping("/users/new")
    public String newUser(Model model){
        User user = new User();
        user.setEnabled(true);
        List<Role> listRoles = service.listRoles();
        // bind USER object with model attribute
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        // add title to model attribute
        model.addAttribute("pageTitle", "Create New User");
        return "user_form";
    }
        //! save user
    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes){
        System.out.println(user);
        service.save(user);

        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/users";
    }


    //! Edit User
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name="id") Integer id, RedirectAttributes redirectAttributes, Model model){
        try {
            User user = service.get(id);
            List<Role> listRoles = service.listRoles();
            model.addAttribute("user", user);
            model.addAttribute("listRoles", listRoles);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            return "user_form";

        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("messageError", ex.getMessage());; // ex.getMessage() is the message we set in UserNotFoundException in service
            return "redirect:/users";
        }

    }

    //! Delete User
    @GetMapping("/users/delete/{id}")
    public String deleteUser (@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes){ // used to pass attributes to the model after a redirect. It's a way to carry data when you do a redirect.
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The user ID " + id + " has been deleted successfully."); // addfalshattribute: used to pass attributes to the model after a redirect. It's a way to carry data when you do a redirect.

        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("messageError", e.getMessage());

        }

        return "redirect:/users";
    }

    //! Enable/Disable User
    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnableStatus(@PathVariable(name="id") Integer id, @PathVariable(name="status") boolean enabled, RedirectAttributes redirectAttributes){
        service.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled"; // if enabled is true, status = "enabled", else status = "disabled"
        String message = "The user ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }

}
