package com.example.library1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(Book book);

    @Query("SELECT * FROM books WHERE genre = :genre")
    List<Book> getBooksByGenre(String genre);

    @Query("SELECT * FROM books WHERE id = :bookId")
    Book getBookById(int bookId);

    @Query("SELECT * FROM books")
    List<Book> getAllBooks();
}
