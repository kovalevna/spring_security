package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUser());
        return "users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @ModelAttribute("my_role") String my_role) {
        Role role = new Role((my_role.equals("ADMIN") ? 1L : 2L), "ROLE" + my_role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(ModelMap modelMap, @PathVariable("id") int id) {
        modelMap.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
