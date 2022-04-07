package book.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

/**
 * 专用于处理页面响应的控制器
 */
@Controller
public class PageController {
    @RequestMapping("/index")
    public String index(Model model){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("username",user.getUsername());
        model.addAttribute("role",(Objects.equals(user.getAuthorities().toString(), "ROLE_USER") ?"普通学生":"管理员"));
        return "index";
    }

    @RequestMapping("/login")
    public String login() { return "login"; }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping("/admin")
    public String admin() { return "index"; }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
}
