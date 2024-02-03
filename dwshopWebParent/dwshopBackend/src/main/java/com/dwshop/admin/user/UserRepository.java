package com.dwshop.admin.user;

import com.dwshop.common.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query ("SELECT u FROM User u WHERE u.email = :email") //@Query: provided by Spring Data JPA, which is a part of the larger Spring Framework. They are used to create custom queries and bind method parameters to query parameters respectively.
    public User getUserByEmail(@Param("email") String email); //@Param("email") String email: email is the parameter in the query

    public Long countById(Integer id); //countById: Spring Data JPA will automatically generate the query for us

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1") /// ?1 and ?2 are the parameters
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled); // where id = ?1 and enabled = ?2

}



