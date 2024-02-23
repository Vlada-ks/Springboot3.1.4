package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;


    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;

    }

    @GetMapping
    public String showAllUser(Model model) {
        model.addAttribute("users", userServiceImpl.getAllUsers());
        return "usersRead";
    }

    @GetMapping("/new")
    public String addNewUser(Model model) {
        model.addAttribute("newUser", new User());
        return "userAdd";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "userAdd";
        userServiceImpl.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id, Model model) {
        User user = userServiceImpl.getUserById(id);
        model.addAttribute("newUpdate", user);
        return "userUpdate";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult bindingResult, BindingResult bindingResultForId, Model model) {
        model.addAttribute("newUpdate", userServiceImpl.getUserById(id));
        if (bindingResult.hasErrors() || bindingResultForId.hasErrors()) {
            user.setId(id);
            return "userUpdate";
        }
        userServiceImpl.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Integer id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }
} 
