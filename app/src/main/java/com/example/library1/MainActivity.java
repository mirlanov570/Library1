package com.example.library1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_BOOK_REQUEST = 1; // Код запроса для добавления книги
    private AppDatabase db;
    private BookDao bookDao;
    private ListView listView;
    private ImageButton btnAdd;
    private ArrayAdapter<String> adapter;
    private List<Book> bookList; // Список книг

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(getApplicationContext());
        bookDao = db.bookDao();

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);

        // Инициализируем список книг
        bookList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);

        // Загружаем книги
        loadBooks();

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBook.class);
            startActivityForResult(intent, ADD_BOOK_REQUEST); // Запускаем активность добавления книги
        });
    }

    // Метод для загрузки книг из базы данных
    private void loadBooks() {
        new Thread(() -> {
            bookList = bookDao.getAllBooks(); // Получаем все книги из базы данных
            runOnUiThread(() -> {
                updateBookList(); // Обновляем отображаемый список
            });
        }).start();
    }

    // Метод для обновления списка книг в интерфейсе
    private void updateBookList() {
        List<String> bookDetails = new ArrayList<>();
        for (Book book : bookList) {
            String details = "Название: " + book.getTitle() +
                    "\nАвтор: " + book.getAuthor() +
                    "\nЖанр: " + book.getGenre() +
                    "\nГод: " + book.getYear() +
                    "\nОписание: " + book.getDescription();
            bookDetails.add(details); // Добавляем информацию о книге
        }
        adapter.clear(); // Очищаем старый список
        adapter.addAll(bookDetails); // Добавляем новые детали книг
        adapter.notifyDataSetChanged(); // Уведомляем адаптер о изменениях
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BOOK_REQUEST && resultCode == RESULT_OK && data != null) {
            // Получение данных о книге из результата
            String title = data.getStringExtra("title");
            String author = data.getStringExtra("author");
            String genre = data.getStringExtra("genre");
            String year = data.getStringExtra("year");
            String description = data.getStringExtra("description");
            String imageUri = data.getStringExtra("imageUri");

            // Создание нового объекта книги
            Book newBook = new Book(title, author, genre, description, imageUri, year);

            // Сохранение книги в базе данных
            new Thread(() -> {
                bookDao.insert(newBook);
                loadBooks(); // Перезагрузка списка книг после добавления
            }).start();
        }
    }
}