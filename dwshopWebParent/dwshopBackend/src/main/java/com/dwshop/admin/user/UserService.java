package com.dwshop.admin.user;

import com.dwshop.common.entity.Role;
import com.dwshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        // if the user is creating a new or editing an existing user
        boolean isUpdatingUser = (user.getId() != null); // if the user id is not null, then we are editing an existing user

        if (isUpdatingUser){ // updating user
            User existingUser = userRepo.findById(user.getId()).get();

            if(user.getPassword().isEmpty()){ // if the user does not enter a new password, then use the existing password
                user.setPassword(existingUser.getPassword());
            }else { // if the user enters a new password, then encode the new password
                encodePassword(user);
            }

        }else { // creating new user
            encodePassword(user);
        }
        userRepo.save(user); // finally save the user
    }

    public boolean isEmailUnique(Integer id, String email){
        User userByEmail= userRepo.getUserByEmail(email);

        if (userByEmail == null) return true; // if True (userByEmail is null), then the email is unique - no user with this email

        //? Determine if we are editing an existing user or creating a new one
        boolean isCreatingNew = (id == null); // Assume id==null means we are creating a new user

        if(isCreatingNew){ // if creating new user
            if (userByEmail != null) return false; // but the email is already save in the database
        } else { // if editing existing user
            if (userByEmail.getId() != id) return false; // if the found user id is not the same as the id of the user we are editing
        }

        return true; // the email is unique and can be used
        //return userByEmail == null; // if True (userByEmail is null), then the email is unique
    }

    public void delete(Integer id) throws UserNotFoundException {

        Long countById = userRepo.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }

        User user = userRepo.findById(id).get();
                //.orElseThrow(() -> new UserNotFoundException("Could not find any user with ID " + id));

        // Remove the association between the user and roles
        user.getRoles().clear(); // This will remove the user's roles associations in the users_roles table

        // Now, delete the user
        userRepo.deleteById(id);

    }

    // private Methods
    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }


    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get(); // may throw NoSuchElementException
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }

    }


}
