package com.solidus_snake.best.umeal.dish_selector;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import com.solidus_snake.best.umeal.R;
import com.solidus_snake.best.umeal.university_canteen.Dish;


public class DishHolder extends RecyclerView.ViewHolder {
    private TextView txtName;
    public ImageButton btnCart;
    private TextView txtPrice;

    public DishHolder(View itemView) {
        super(itemView);
        this.txtName = (TextView) itemView.findViewById(R.id.dish_name);
        this.btnCart= (ImageButton) itemView.findViewById(R.id.cart_button);
        this.txtPrice = (TextView) itemView.findViewById(R.id.dish_price);
    }
    public void setDetails(String name, String price) { //получаем на вход строку-имя блюда
        txtName.setText(name);
        txtPrice.setText(price);
    }
}