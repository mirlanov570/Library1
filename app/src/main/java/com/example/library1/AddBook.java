package com.example.library1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddBook extends AppCompatActivity {
    private EditText etAuthor, etTitle, etYear, etDescription;
    private Spinner spinnerGenre;
    private ImageView ivPicture;
    private Button btnSave, btnSelectImage;
    private Uri imageUri;

    private static final int PICK_IMAGE = 1;

    // Предопределенный список жанров
    private String[] genres = {"Фантастика", "Роман", "Детектив", "Научная литература", "Исторический роман", "Поэзия"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Инициализация элементов интерфейса
        etTitle = findViewById(R.id.editTextTitle);
        etAuthor = findViewById(R.id.editTextAuthor);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        etDescription = findViewById(R.id.editTextDescription);
        etYear = findViewById(R.id.editTextYear);
        ivPicture = findViewById(R.id.imageViewBook);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSave = findViewById(R.id.btnSave);

        // Настройка адаптера для выбора жанра
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        // Обработчик нажатия на кнопку выбора изображения
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // Обработчик нажатия на кнопку сохранения книги
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", etTitle.getText().toString());
                resultIntent.putExtra("author", etAuthor.getText().toString());
                resultIntent.putExtra("genre", spinnerGenre.getSelectedItem().toString());
                resultIntent.putExtra("year", etYear.getText().toString());
                resultIntent.putExtra("description", etDescription.getText().toString());
                resultIntent.putExtra("imageUri", imageUri != null ? imageUri.toString() : null);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    // Метод для открытия выбора изображения
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ivPicture.setImageURI(imageUri); // Устанавливаем выбранное изображение
        }
    }
}
