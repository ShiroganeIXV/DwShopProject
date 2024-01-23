package com.dwshop.admin.user;

import com.dwshop.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // use real database
@Rollback(false) // do not rollback after test
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateFirstRole(){
        Role roleAdmin = new Role("Admin", "manage everything");
        Role saveRole = repo.save(roleAdmin); //The save method returns the saved entity. If the entity was new, the returned entity will include the generated ID. If the entity was existing, the returned entity will reflect any changes that were saved to the database.
        assertThat(saveRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles(){
        Role roleSalesperson = new Role("Salesperson", "manage product price, customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "manage categories, brands, products, articles and menus");
        Role roleShipper = new Role("Shipper", "view products, view orders and update order status");
        Role roleAssistant = new Role("Assistant", "manage questions and reviews");


        repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant)); //List.of() method is a convenient way to create an immutable list of objects in Java.
    }
}
