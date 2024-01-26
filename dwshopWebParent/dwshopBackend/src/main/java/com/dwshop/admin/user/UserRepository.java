package com.dwshop.admin.user;

import com.dwshop.common.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query ("SELECT u FROM User u WHERE u.email = :email") //@Query: provided by Spring Data JPA, which is a part of the larger Spring Framework. They are used to create custom queries and bind method parameters to query parameters respectively.
    public User getUserByEmail(@Param("email") String email); //@Param("email") String email: email is the parameter in the query

}
