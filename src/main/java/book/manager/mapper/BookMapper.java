package book.manager.mapper;

import book.manager.entity.Book;
import book.manager.entity.Borrow;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from book")
    List<Book> allBook();

    @Select("select * from book where bid = #{bid}")
    Book getBookByBid(int bid);

    @Delete("delete from book where bid = #{bid}")
    void deleteBook(int bid);

    @Insert("insert into book(title,`desc`,price) values(#{title},#{desc},#{price})")
    void addBook(@Param("title") String title,
                 @Param("desc") String desc,
                 @Param("price") double price);

    @Insert("insert into borrow(bid,sid,time) values(#{bid},#{sid},NOW())")
    void addBorrow(@Param("bid") int bid,@Param("sid") int sid);

    @Delete("delete from borrow where bid = #{bid} and sid = #{sid}")
    void returnBook(@Param("bid") int bid,@Param("sid") int sid);

    @Select("select * from borrow")
    List<Borrow> borrowList();

    @Select("select * from borrow where sid = #{sid}")
    List<Borrow> borrowListBySid(int sid);
}
