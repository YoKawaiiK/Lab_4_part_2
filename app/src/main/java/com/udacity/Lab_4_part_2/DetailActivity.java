package com.udacity.Lab_4_part_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.Lab_4_part_2.model.Sandwich;
import com.udacity.Lab_4_part_2.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Переменные для элементов
    private ImageView ingredientsIv;
    private TextView mPlaceOfOriginTv;
    private TextView mDescriptionTv;
    private TextView mIngredientsTv;
    private TextView mAlsoKnownAsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Получение по id элементов
        ingredientsIv = findViewById(R.id.image_iv);
        mPlaceOfOriginTv = findViewById(R.id.origin_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mAlsoKnownAsTv = findViewById(R.id.also_known_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // Параметры, полученные при нажатии на элемент с списке
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // Получение массива sandwiches из строк, где каждая строка
        // соответствует отдельному сэндвичу
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

        // Получение одного сэндвича по его позиции
        String json = sandwiches[position];

        // Полученный sandwich десеаризируем и превращаем в объект
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Заполняем контентом
        populateUI(sandwich);
    }

    // Применяется, если сэндвич не найден
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // Заполнение контентом
    private void populateUI(Sandwich sandwich) {
        // Название в header
        setTitle(sandwich.getMainName());
        Picasso.with(this)
                .load(sandwich.getImage()).into(ingredientsIv);

        mPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());

        mDescriptionTv.setText(sandwich.getDescription());

        // Преобразование массива в строку, где элементы массива разделены запятой
        String formatTextIngredients = android.text.TextUtils.join(", ",
                sandwich.getIngredients());
        mIngredientsTv.setText(formatTextIngredients);

        // Преобразование массива в строку, где элементы массива разделены запятой
        String formatTextAlsoKnownAs = android.text.TextUtils.join(", ",
                sandwich.getAlsoKnownAs());

        mAlsoKnownAsTv.setText(formatTextAlsoKnownAs);
    }
}
