package com.solidus_snake.best.umeal;

import com.solidus_snake.best.umeal.university_canteen.*; //package , связанный со столовыми

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //все необходимые переменные
    //      выбранный для просмотра день
    private int current_day_selected;
    private int categories_amount;
    //массив объектов, в которых заложено всё расписание всех столовых на каждый день
    //и со всеми категориями
    private ArrayList<UniversityCanteen> university_canteens_list;

    private UniversityCanteensAdapter adapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private Spinner spinner;
    Spinner mNavigationSpinner;
    ArrayAdapter<String> toolbar_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        //день выбора по умолчанию - понедельник, поэтому ноль
        current_day_selected = 0;
        adapter = null;
        //инициализируем наш массив всего
        university_canteens_list = new ArrayList<UniversityCanteen>();

        //заполняем наш массив с помощью этой функции
        createListData();

        String[] working_times = new String[university_canteens_list.size()];

        for (int i =0; i < university_canteens_list.size(); i++){
            //получаем массив расписаний для текущего дня (который выбран в спиннере
            working_times[i] = university_canteens_list.get(i).getSchedule(current_day_selected);
        }
        //получаем id для toolbar

        String[] days = getResources().getStringArray(R.array.names_of_days);



      //  mNavigationSpinner = new Spinner(getSupportActionBar().getThemedContext());
        mNavigationSpinner = (Spinner) findViewById(R.id.day_spinner);

        //готовим адаптер для spinner элемента
        toolbar_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, days);
        //используем стандартные библиотечные разметки
        toolbar_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mNavigationSpinner.setAdapter(toolbar_adapter);

        mNavigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (current_day_selected == position){
                    return;
                }else{
                    current_day_selected = position;
                    adapter.refresh(current_day_selected);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        UniversityCanteensAdapter.OnCanteenClickListener listener =
                new UniversityCanteensAdapter.OnCanteenClickListener() {
                    @Override
                    public void OnClickListener(final int data) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("canteen_number", data);
                        bundle.putInt("current_day", current_day_selected);
                        //TODO categories_amount
                        bundle.putInt("categories_amount", categories_amount);
                        Intent next = new Intent(getBaseContext(), Main2Activity.class);
                        next.putExtras(bundle);
                        getBaseContext().startActivity(next);


                    }
                };

        adapter = new UniversityCanteensAdapter(this,
                university_canteens_list, current_day_selected,
                listener);
        recyclerView.setAdapter(adapter);

    }

    //TODO адаптер то о дне нихуя не знает! поэтому спинер хуинер не воркабилити!
    //заполняем данные об университетских столовых, используя ресурсы
    private void createListData() {
        if (university_canteens_list.size() ==  0){
            UniversityBuilderJSON builder = new UniversityBuilderJSON(getResources());
            categories_amount = builder.getAmountOfCategories();
            for (int i = 0; i < 3; i++){
                builder.setDayToBuild(current_day_selected);
                builder.setCanteenToLook(i);
                builder.setSetOnlyWorkingTime(true);
                UniversityCanteen u_c = builder.getObjectFromJSON();
                university_canteens_list.add(u_c);
            }
        }
    }
}
