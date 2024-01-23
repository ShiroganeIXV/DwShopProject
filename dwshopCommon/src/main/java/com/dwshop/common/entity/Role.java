package com.dwshop.common.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false, unique = true)
    private String name;

    @Column(length = 150, nullable = false)
    private String description;

    // Constructors
    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(String admin, String manageEverything) {
        this.name = admin;
        this.description = manageEverything;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // equals() and hashCode() methods

    @Override
    public boolean equals(Object o) { // checks if two objects are the same
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() { //returns an integer and it's used in storing and retrieving objects quickly in hash-based collections.
        return Objects.hash(getId());
    }

    // toString() method

    @Override
    public String toString() {
//        return "Role{" +
//                "name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                '}';
        return this.name;
    }
}
