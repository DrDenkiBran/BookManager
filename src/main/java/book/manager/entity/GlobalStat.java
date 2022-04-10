package book.manager.entity;

import lombok.Data;

@Data
public class GlobalStat {
    public int userCount;
    public int bookCount;
    public int borrowCount;

    public GlobalStat(int studentCount, int bookCount, int borrowCount) {
        this.setUserCount(studentCount);
        this.setBookCount(bookCount);
        this.setBorrowCount(borrowCount);
    }
}
