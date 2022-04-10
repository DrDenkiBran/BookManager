package book.manager.controller.api;

import book.manager.entity.AuthUser;
import book.manager.service.AuthService;
import book.manager.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/user")
public class UserApiController {
    @Resource
    BookService bookService;
    @Resource
    AuthService authService;
    @RequestMapping(value = "borrow-book",method = RequestMethod.GET)
    public String borrowBook(@RequestParam("bid") int bid, @SessionAttribute("user")AuthUser user){
        bookService.borrowBook(bid,user.getId());
        return "redirect:/page/user/index";
    }

    @RequestMapping(value = "return-book", method = RequestMethod.GET)
    public String returnBook(@RequestParam("bid")int bid, @SessionAttribute("user")AuthUser user){
        bookService.returnBook(bid,user.getId());
        return "redirect:/page/user/index";
    }
}
