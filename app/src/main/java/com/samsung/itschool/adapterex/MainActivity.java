package com.samsung.itschool.adapterex;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button createButton;
    LinearLayout linearLayout;
    int count = 1;
    int userID = 9999;

    MyOpenHelper myOpenHelper;
    SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myOpenHelper =new MyOpenHelper(this);
        sdb = myOpenHelper.getWritableDatabase();

        listView = findViewById(R.id.bookList);
        createButton = findViewById(R.id.createButton);
        linearLayout = findViewById(R.id.my_layout);

        //Подготовка данных - 1 этап создание списка объектов
        final LinkedList<Book> books = new LinkedList<>();
        books.add(new Book("Война и мир", "Лев Толстой", "2004", R.drawable.book));
        books.add(new Book("Основание", "Айзек Азимов", "2017", R.drawable.osnovanie));
        books.add(new Book("Преступление и наказание", "Федор Достоевский", "1986", R.drawable.prestuplenie));
        books.add(new Book("Шинель", "Николай Гоголь", "2008", R.drawable.shinel));
        books.add(new Book("Зерцалия", "Евгений Гоглоев", "2019", R.drawable.zertsalia));
        books.add(new Book("Феникс Сапиенс", "Борис Штерн", "2020", R.drawable.book));

        //Подготовка данных 2 этап: список с ключами
        LinkedList<HashMap<String, Object>> mapBooks = new LinkedList<>();
        for (int i = 0; i < books.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("author", books.get(i).author);
            map.put("title", books.get(i).title);
            map.put("year", books.get(i).year);
            map.put("cover", books.get(i).cover);
            mapBooks.add(map);
        }

        //подготовка 3 этап: вспомогательные массивы
        String[] keyFrom = {"author", "title", "year", "cover"};
        int [] idTo = {R.id.author, R.id.title, R.id.year, R.id.cover};
        //Создание адаптера
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.list_item, books);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, mapBooks, R.layout.list_item,
                keyFrom, idTo);
        //установка адаптера на ListView



        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), i + ") " + books.get(i), Toast.LENGTH_SHORT)
                        .show();
            }
        });


        //создание новых кнопок
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //объявление новой кнопки
                Button newButton = new Button(getApplicationContext());
                //создание обязательных настроек
                newButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                //необязательные настройки
                newButton.setText("Кнопка №" + count);
                newButton.setId(userID + count);
                newButton.setBackgroundColor(Color.GREEN);
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        linearLayout.removeView(view);
                    }
                });
                //установка новой кнопки в контейнер
                linearLayout.addView(newButton);
                count++;
            }
        });
    }
}
