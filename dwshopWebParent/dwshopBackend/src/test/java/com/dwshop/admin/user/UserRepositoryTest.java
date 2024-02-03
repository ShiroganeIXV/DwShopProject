package com.dwshop.admin.user;

import com.dwshop.common.entity.Role;
import com.dwshop.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // use real database
@Rollback(false) // don't rollback after test
public class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager; // alternative to the standard JPA EntityManager with additional features useful for testing. It's designed to be used in combination with @DataJpaTest annotation.

    @Test
    public void testCreateUserWithOneRole(){
        //Given
        User user1 = new User("dw001@gmail.come","12345","Diana","Wu");
        // Get roles from database
        Role role1 = entityManager.find(Role.class, 1);

        //When
        // Add roles to user
        user1.addRole(role1);
        User savedUser = repo.save(user1);

        //Then
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateUserWithTwoRoles() {
        //Given
        User user2 = new User("ns001@outlook.com", "12345", "Naoto", "Shirogane");
        Role roleEditor = entityManager.find(Role.class,3);
        Role roleAssistant = entityManager.find(Role.class,5);
        user2.addRole(roleEditor);
        user2.addRole(roleAssistant);

        // When
        User savedUser = repo.save(user2);

        // Then
        assertThat(savedUser.getId()).isGreaterThan(0); //the id would be 0 or null if the save operation failed
    }

    @Test
    public void testListAllUsers() {
        //Given


        //When
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(System.out::println); // method reference replace lambda expression (user -> System.out.println(user))

        //Then
        assertThat(listUsers).size().isGreaterThan(0);
    }

    @Test
    public void testGetUserById() {
        //Given
        Integer userId = 1;

        //When
        User user = repo.findById(userId).get();
        System.out.println(user);
        //Then
        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        //Given
        String email = "dianawu@gmail.com";
        Integer userId = 1;
        User user = repo.findById(userId).get();
        user.setEnabled(true);
        user.setEmail(email);

        //When
        User updatedUser = repo.save(user);

        //Then
        assertThat(updatedUser.getEmail()).isEqualTo(email);
        assertThat(updatedUser.isEnabled()).isTrue();
    }

    @Test
    public void testUpdateUserRoles() {
        //Given
        Integer userId = 3;
        User user = repo.findById(userId).get();
        Role roleEditor = entityManager.find(Role.class,3);
        Role roleSalesperson = entityManager.find(Role.class,2);
            // remove roleEditor
        user.getRoles().remove(roleEditor);
            // add roleSalesperson
        user.addRole(roleSalesperson);
        //When
        User updatedUser = repo.save(user);

        //Then
        assertThat(updatedUser.getRoles()).contains(roleSalesperson);

    }

    @Test
    public void testDeleteUser(){
        //Given
        Integer userId = 3;

        //When
        repo.deleteById(userId);

        //Then
        boolean isExist = repo.findById(userId).isPresent();
        assertThat(isExist).isFalse();
    }

    // Test for unique email
    @Test
    public void testGetUserByEmail() {
        // String email = "abc@edf.com";  // This will be null bc there is no user with that email
        String email = "ns001@outlook.com";
        User user = repo.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        //given
        Integer id = 1;

        //when
        Long countById = repo.countById(id);

        //then
        assertThat(countById).isNotNull().isGreaterThan(0);

    }
}

