package book.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 专用于处理页面响应的控制器
 */
@Controller
public class PageController {
    @RequestMapping("/index")
    public String login(){
        return "index";
    }
}
