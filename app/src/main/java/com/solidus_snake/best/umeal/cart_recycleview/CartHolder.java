package com.solidus_snake.best.umeal.cart_recycleview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.solidus_snake.best.umeal.R;

public class CartHolder extends RecyclerView.ViewHolder {
    private TextView txtName;
    public ImageButton btnDelFromCart;
    private TextView txtPrice;

    public CartHolder(@NonNull View itemView) {
        super(itemView);
        this.txtName = (TextView) itemView.findViewById(R.id.cart_item_name);
        this.btnDelFromCart= (ImageButton) itemView.findViewById(R.id.delete_cart_item);
        this.txtPrice = (TextView) itemView.findViewById(R.id.cart_item_price);
    }
    public void setDetails(String name, String price){
        txtName.setText(name);
        txtPrice.setText(price);
    }
    //настраиваем слушателя, переданного извне
    final public void setListeners(View.OnClickListener listener){
        btnDelFromCart.setOnClickListener(listener);
    }


}
