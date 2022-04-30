package com.recordgate.RecordGate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> listUsers = userService.allUsersList();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }
    @GetMapping("/users/new")
    public String showNewForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add New User");
        return "user_form";
    }
   @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes){
      userService.saveUser(user);
      redirectAttributes.addFlashAttribute("message", "User has been saved successfully");
      return "redirect:/users";
   }
   @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
       try {
          User user = userService.get(id);
           model.addAttribute("user", user);
           model.addAttribute("pageTitle", "Update User ID " + id + " ");

           return "user_form";
       } catch (userNotFoundExeption e) {
           redirectAttributes.addFlashAttribute("message", "User has been updated successfully");
           return "redirect:/users";
       }
   }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The user is "+ id +" has been deleted successfully");

        } catch (userNotFoundExeption e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }


}
