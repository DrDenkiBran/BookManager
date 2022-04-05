package book.manager.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 专用于处理页面响应的控制器
 */
@Controller
public class PageController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login() { return "login"; }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping("/admin")
    public String admin() { return "index"; }
}
