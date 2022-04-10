package book.manager.service;

import book.manager.entity.Book;
import book.manager.entity.BorrowDetails;


import java.util.List;


public interface BookService {
    List<Book> getAllBook();
    List<Book> getAllBookWithOutBorrow();
    List<Book> getAllBookById(int id);
    void deleteBookById(int bid);
    void addBook(String title,String desc,double price);
    void borrowBook(int bid,int sid);
    void returnBook(int bid,int sid);
    List<BorrowDetails> getBorrowDetailsList();
}
