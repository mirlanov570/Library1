package com.example.library1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface GenreDao {

    @Insert
    void insert(Genre genre);

    @Query("SELECT * FROM genres")
    List<Genre> getAllGenres();

    @Query("SELECT * FROM genres WHERE id = :genreId") // Добавлен метод для получения жанра по ID
    Genre getGenreById(int genreId);
}
