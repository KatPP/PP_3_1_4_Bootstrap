package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//Прописываем контроллеры:

// Страница пользователя.
    @GetMapping("/admin")
    public String viewUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "index";
    }
// лист юзеров

    @GetMapping("/admin/users")
    public String findAll(Principal principal, Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }
//добавление пользователя

    @GetMapping("/admin/user-create")
    public String addUser(@ModelAttribute("adduser") User user, Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser(@ModelAttribute("adduser") User user,
                             @RequestParam("roles") Set<Role> roles) {
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    // удадение пользователя

    @GetMapping("admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    //обновление  пользователя.


    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(Model model, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        model.addAttribute("adduser", user);
        model.addAttribute("roles", roleService.findAll());
        return "user-update";
    }

    @PostMapping("/admin/user-update")
    public String updateUser(@RequestParam("roles") Set<Role> roles,
                             @RequestParam("username") String username,
                             @RequestParam("lastname") String lastname,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam Long id) {
        User user = userService.findById(id);
        user.setRoles(roles);
        user.setUsername(username);
        user.setEmail(email);
        user.setLastname(lastname);
        user.setPassword(password);
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

}