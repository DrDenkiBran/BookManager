package book.manager.service;

import book.manager.entity.Book;


import java.util.List;


public interface BookService {
    List<Book> getAllBook();

    void deleteBookById(int bid);
    void addBook(String title,String desc,double price);
}
