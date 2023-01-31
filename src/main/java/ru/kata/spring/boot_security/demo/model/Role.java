package ru.kata.spring.boot_security.demo.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    public Role() {
    }

    public Role(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public String getNoPrefix() {
        String pr = "ROLE_";
        return name.substring(pr.length());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
