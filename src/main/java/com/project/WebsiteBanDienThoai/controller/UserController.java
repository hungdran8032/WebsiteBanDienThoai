package com.project.WebsiteBanDienThoai.controller;

import com.project.WebsiteBanDienThoai.model.User;
import com.project.WebsiteBanDienThoai.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal){
        String username = principal.getName();
        try{
            User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Người dùng này không tồn tại"));
            model.addAttribute("user", user);
        }catch (UsernameNotFoundException e){
            model.addAttribute("errorMessage", "Không tìm thấy");
        }
        return "/profiles/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, Principal principal) {
        String username = principal.getName();
        try {
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Người dùng này không tồn tại"));
            model.addAttribute("user", user);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("errorMessage", "Không tìm thấy người dùng");
            return "error";
        }
        return "profiles/edit-profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@Valid @ModelAttribute("user") User updatedUser,
                                BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new));
            return "profiles/edit-profile";
        }

        String username = principal.getName();
        try {
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Người dùng này không tồn tại"));
            // Update the user details
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setAddress(updatedUser.getAddress());
            user.setPhone(updatedUser.getPhone());
            user.setEmail(updatedUser.getEmail());
            user.setGender(updatedUser.getGender());
            user.setBirthdate(updatedUser.getBirthdate());

            userService.save(user); // Save updated user details

            model.addAttribute("user", user);
            model.addAttribute("successMessage", "Profile updated successfully");
        } catch (UsernameNotFoundException e) {
            model.addAttribute("errorMessage", "Không tìm thấy người dùng");
            return "error";
        }
        return "profiles/profile";
    }

    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new User()); // Thêm một đối tượng User mới vào
        return "register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           @NotNull BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "register"; // Trả về lại view "register" nếu có lỗi
        }
        if(userService.findByUsername(user.getUsername()).isPresent()){
            model.addAttribute("usernameError", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác.");
            return "register";
        }
        userService.save(user); // Lưu người dùng vào cơ sở dữ liệu
        userService.setDefaultRole(user.getUsername());
        return "redirect:/login"; // Chuyển hướng người dùng tới trang "login"
    }

    @GetMapping("/admin/user")
    public String showListAccount(Model model) {
        model.addAttribute("users",userService.getAllAccount());
        model.addAttribute("roles", userService.getAllRoles());
        return "/admin/list-user";
    }


}