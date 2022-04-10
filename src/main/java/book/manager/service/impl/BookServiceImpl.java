package book.manager.service.impl;

import book.manager.entity.Book;
import book.manager.entity.Borrow;
import book.manager.entity.BorrowDetails;
import book.manager.mapper.BookMapper;
import book.manager.mapper.UserMapper;
import book.manager.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Resource
    BookMapper bookMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List<Book> getAllBook() {
        return bookMapper.allBook();
    }

    @Override
    public List<Book> getAllBookWithOutBorrow() {
        List<Book> books = bookMapper.allBook();
        List<Integer> borrows = bookMapper.borrowList()
                .stream()
                .map(Borrow::getBid)
                .collect(Collectors.toList());
        return books
                .stream()
                .filter(book->!borrows.contains(book.getBid()))
                .collect(Collectors.toList());

    }

    @Override
    public List<Book> getAllBookById(int id) {
        Integer sid = userMapper.getSidByUid(id);
        if (sid == null) return Collections.emptyList();
        return bookMapper.borrowListBySid(sid)
                .stream()
                .map(borrow -> bookMapper.getBookByBid(borrow.getBid()))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteBookById(int bid) {
        bookMapper.deleteBook(bid);
    }

    @Override
    public void addBook(String title, String desc, double price) {
        bookMapper.addBook(title,desc,price);
    }

    @Override
    public void borrowBook(int bid,int id) {
        Integer sid = userMapper.getSidByUid(id);
        if (sid == null) return;
        bookMapper.addBorrow(bid,sid);
    }

    @Override
    public void returnBook(int bid,int id) {
        Integer sid = userMapper.getSidByUid(id);
        if (sid == null) return;
        bookMapper.returnBook(bid,sid);
    }

    @Override
    public List<BorrowDetails> getBorrowDetailsList() {
        return bookMapper.borrowDetailsList();
    }


}
