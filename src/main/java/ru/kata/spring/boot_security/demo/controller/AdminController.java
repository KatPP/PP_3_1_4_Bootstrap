package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

//Прописываем контроллеры:

    //отображение листа юзеров

    @GetMapping
    public String getUser(Model model, Authentication authentication) {
        model.addAttribute("userList", userService.findAll());
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("users", userService.findById(user.getId()));
        return "admin-panel";
    }


    // добавление пользователя
    @PostMapping("/newuser")
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    // Удаление

    @DeleteMapping("{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    // изменение
    @PostMapping("/{id}/editUser")
    public String userSaveEdit(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

}