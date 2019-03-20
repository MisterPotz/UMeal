package com.solidus_snake.best.umeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.solidus_snake.best.umeal.university_canteen.Dish;


public class DishInfoActivity extends AppCompatActivity {
    private TextView txtRatio;
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtWeight;
    private TextView txtCalories;
    private int selected_time;
    private Dish currentDish;
    private ImageButton btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_info);
        final Toolbar toolbar = findViewById(R.id.dish_info_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //сколько раз блюдо было выбрано за время работы этой активности
        selected_time = 0;
        //получаем переданное в эту активность блюдо
        currentDish = (Dish) getIntent().getExtras().getParcelable("dish");

        //получаем все необходимые поля
        txtRatio = (TextView) findViewById(R.id.dish_ratio);
        txtName = (TextView) findViewById(R.id.dish_name);
        txtPrice = (TextView) findViewById(R.id.dish_price);
        txtWeight = (TextView) findViewById(R.id.dish_weight);
        txtCalories = (TextView) findViewById(R.id.dish_calories);

        txtName.setText(currentDish.getName());
        txtPrice.setText(currentDish.getPrice());
        txtWeight.setText(currentDish.getWeight());
        txtCalories.setText(currentDish.getCalories());
        txtRatio.setText(currentDish.getRatio());

        //получаем кнопушку добавления в корзину
        btnAddToCart = (ImageButton) findViewById(R.id.dish_info_add_to_cart);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //делаем уведомленьице, что блюдо добавлено в корзину
                Snackbar.make(toolbar, "Блюдо добавлено в корзину", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                //увеличиваем счетчик
                selected_time++;


            }
        });
    }



    //при нажатии элемента этой активности определить, что было нажато и совершить некие действия
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //если чпонькнули строелочку назад, возвращаем назад результат
            case android.R.id.home:
                sendData();
                finish();
                return (true);
            default:
                return (super.onOptionsItemSelected(item));
        }
    }
    //поддержка механической кнопки назад устройства Android
    @Override
    public void onBackPressed() {

        sendData();
        finish();
    }

    //вспомогательная функция для завершения этой активности и возвращения в предыдущую
    private void sendData(){
        Bundle bundle = new Bundle();

        //бросаем в бандл текущее блюдо
        bundle.putParcelable("dish", currentDish);

        //вместе с ним бросаем счетчик нажатий добавления в корзину
        //чтобы потом обновить список избранных блюд
        bundle.putInt("times_selected", selected_time);
        Intent data = new Intent();
        data.putExtras(bundle);
        setResult(0, data);
    }
}
