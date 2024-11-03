package com.example.library1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyBook extends AppCompatActivity {
    private TextView tvTitle, tvAuthor, tvYear, tvDescription, tvGenre;
    private ImageView ivBookImage;
    private AppDatabase db;
    private BookDao bookDao;
    private GenreDao genreDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book); // Убедитесь, что здесь правильный layout

        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvYear = findViewById(R.id.tvYear);
        tvDescription = findViewById(R.id.tvDescription);
        tvGenre = findViewById(R.id.tvGenre);
        ivBookImage = findViewById(R.id.ivBookImage);

        db = AppDatabase.getDatabase(getApplicationContext());
        bookDao = db.bookDao();
        genreDao = db.genreDao();

        int bookId = getIntent().getIntExtra("bookId", -1);
        if (bookId != -1) {
            loadBookDetails(bookId);
        }
    }

    private void loadBookDetails(int bookId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Book book = bookDao.getBookById(bookId);
            if (book != null) {
                runOnUiThread(() -> {
                    tvTitle.setText(book.getTitle()); // Исправлено на getTitle
                    tvAuthor.setText(book.getAuthor());
                    tvYear.setText(book.getYear()); // Предполагается, что getYear возвращает String
                    tvDescription.setText(book.getDescription());

                    // Загрузка изображения, если оно есть
                    if (book.getImageUri() != null) {
                        ivBookImage.setImageURI(Uri.parse(book.getImageUri()));
                    }

                    // Получение жанра по id, если необходимо
                    // Вы можете использовать genreDao для получения информации о жанре, если нужно
                    // String genreName = genreDao.getGenreById(book.getGenreId()).getName();
                    // tvGenre.setText(genreName);
                });
            }
        });
    }
}
