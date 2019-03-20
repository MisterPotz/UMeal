package com.solidus_snake.best.umeal.cart_recycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solidus_snake.best.umeal.CartActivity;
import com.solidus_snake.best.umeal.R;

import com.solidus_snake.best.umeal.dish_selector.DishCart;


public class CartAdapter extends RecyclerView.Adapter<CartHolder> {

    private DishCart dishList;
    private Context context;
    //получаем всё необходимое при инициализации адаптера
    public CartAdapter(Context context, DishCart dishList_) {
        this.dishList = dishList_;
        this.context = context;
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        //создаем новый элемент с ценой и названием блюда, а также с кнопкой удаления из корзины
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item,parent, false);

        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder cartHolder, int i) {

        //настраиваем текстовые элементы
        cartHolder.setDetails(dishList.getDish(i).getName(), dishList.getDish(i).getPrice());

        //настраиваем слушатели в cartHolder
        cartHolder.setListeners(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //при щелчке удалить блюдо, которое решили удалить
                int item = cartHolder.getAdapterPosition();

                //проверка, есть ли вообще такое блюдо (нужно, если человек будет тыкать очень быстро
                //на кнопку удаления
                if (item != RecyclerView.NO_POSITION){
                    removeAt(item);
                }
            }
        });
    }
    //функция для обновления REcycleView при удалении
    public void removeAt(int position) {
        //убираем нажатую позицию и вносим изменения в RecycleView
        dishList.remove(position);
        //уведомляем об изменении
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, dishList.getSize());
        //обновляем ценник
        ((CartActivity)context).updateCart();
    }
    @Override
    public int getItemCount() {
        return dishList.getSize();
    }
}
