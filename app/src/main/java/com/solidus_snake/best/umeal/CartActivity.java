package com.solidus_snake.best.umeal;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.widget.TextView;

import com.solidus_snake.best.umeal.cart_recycleview.CartAdapter;
import com.solidus_snake.best.umeal.dish_selector.DishCart;

//слушатели реализованы в package cart_recycleview

public class CartActivity extends AppCompatActivity{
    private DishCart selectedDishes;
    boolean wasChanged;
    private TextView txtPrice;
    private TextView txtPolite;
    private TextView txtCalories;
    private TextView txtRatio;

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //получаем список избранных покупателем блюд
        selectedDishes = getIntent().getExtras().getParcelable("selected_dishes_list");
        wasChanged = false;

        txtPrice = (TextView) findViewById(R.id.total_price);
        txtPolite = (TextView) findViewById(R.id.polite_text);
        txtCalories = (TextView) findViewById(R.id.cart_calories);
        txtRatio = (TextView) findViewById(R.id.cart_ratio);

        recyclerView = (RecyclerView) findViewById(R.id.shopping_list_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new CartAdapter(this,selectedDishes);
        recyclerView.setAdapter(cartAdapter);
        updateCart();

    }

    //найти и выставить суммарную стоимость
    private void setSumPrice(){
        float temp = 0f;
        for (int i = 0; i < selectedDishes.getSize(); i++){
            temp += Float.parseFloat(selectedDishes.getDish(i).
                    getPrice());
        }
        String total_price = Float.toString(temp);
        total_price = total_price.substring(0, total_price.indexOf('.')+2);
        txtPrice.setText(total_price);
    }

    //выставить текст-предложение в зависимости от количества блюд
    private void setPoliteText(){
        if (selectedDishes.getSize() == 0){
            txtPolite.setText(R.string.suggest_to_choose);
        }
        else
            txtPolite.setText(R.string.bon_apetit);
    }

    private void setCalories(){
        //находим общие калории
        float temp_c = 0f;
        for (int i = 0; i < selectedDishes.getSize(); i++){
            String temp_string = selectedDishes.getDish(i).getCalories();
            temp_string = temp_string.substring(0, temp_string.indexOf(' '));
            temp_c += Float.parseFloat(temp_string );
        }
        String total_c = Float.toString(temp_c);
        total_c = total_c.substring(0, total_c.indexOf('.')) + " Ккал";
        txtCalories.setText(total_c);
    }

    private void setRatio(){
        //находим общее процентное соотношение б/ж/у
        float belki = 0f;
        float zhyri = 0f;
        float uglevodi = 0f;
        String ratio;
        for (int i = 0; i < selectedDishes.getSize(); i++){
            String temp_ratio = selectedDishes.getDish(i).
                    getRatio();
            belki+= Float.parseFloat(temp_ratio.substring(0, temp_ratio.indexOf('/')));
            temp_ratio = temp_ratio.substring(temp_ratio.indexOf('/')+1);
            zhyri+= Float.parseFloat(temp_ratio.substring(0, temp_ratio.indexOf('/')));
            temp_ratio = temp_ratio.substring(temp_ratio.indexOf('/')+1);
            uglevodi+= Float.parseFloat(temp_ratio.substring(0, temp_ratio.indexOf(' ')));
        }
        belki /= selectedDishes.getSize();
        zhyri /= selectedDishes.getSize();
        uglevodi /= selectedDishes.getSize();
        ratio = getShortString(belki) + "/" + getShortString(zhyri) + "/" + getShortString(uglevodi) + " б/ж/у";
        txtRatio.setText(ratio);
    }

    //считаем все суммарные элементы и выводим их
    public void updateCart(){
        if (selectedDishes.getSize() == 0){
            txtPolite.setText(R.string.suggest_to_choose);
            txtPrice.setText("0.0");
            txtCalories.setText("0");
            txtRatio.setText("0/0/0");
            return;
        }
        setSumPrice();
        setPoliteText();
        setCalories();
        setRatio();
    }

    //вспомогательная функция для получения чисел из строк
    private String getShortString(float data){
        String total = Float.toString(data);
        total = total.substring(0, total.indexOf('.'));
        return total;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //при нажатии кнопки назад аккуратно завернуть в бандл
                //модифицированный список блюд и закрыть эту активность
                Intent intent = new Intent();
                intent.putExtra("selected_dishes_list", selectedDishes);
                setResult(1, intent);
                finish();
                return (true);
            default:
                return false;
        }
    }
    @Override
    public void onBackPressed() {
        //тоже самое что и при нажатии кнопки назад
        Intent intent = new Intent();
        intent.putExtra("selected_dishes_list", selectedDishes);
        setResult(1, intent);
        finish();
    }
}
