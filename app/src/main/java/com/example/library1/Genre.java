package com.example.library1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "genres")
public class Genre {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    // Конструкторы
    public Genre(String name) {
        this.name = name;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
