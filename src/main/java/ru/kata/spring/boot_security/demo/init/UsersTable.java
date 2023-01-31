package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class UsersTable {

    private final UserService userService;
    private final RoleService roleService;

    public UsersTable(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUsers() {

        Role user = new Role( "ROLE_USER", 1);
        Role admin = new Role("ROLE_ADMIN", 2);

        roleService.save(user);
        roleService.save(admin);

        User user1 = new User("User", "Ivan", 33, "user@mail.ru", "200");
        User user2 = new User("Admin", "Nikolay", 45, "admin@mail.ru", "200");
        User user3 = new User("Super", "Man",20, "super@ya.ru", "200");
        User user4 = new User("BEZ", "ROLI", 7,  "USER@TEST.RU", "200");

        user1.addRole(roleService.findByName("ROLE_ADMIN"));
        user1.addRole(roleService.findByName("ROLE_USER"));
        user2.addRole(roleService.findByName("ROLE_ADMIN"));
        user3.addRole(roleService.findByName("ROLE_USER"));
        user4.addRole(roleService.findByName(""));

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);
    }
}




