package com.solidus_snake.best.umeal.dish_selector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solidus_snake.best.umeal.R;
import com.solidus_snake.best.umeal.university_canteen.Dish;



import java.util.ArrayList;

//адаптер для recycleview в tabLayout
public class DishAdapter extends RecyclerView.Adapter<DishHolder>  {

    //интерфейс для соединения между фрагментом и адаптером
    public interface MyOnButtonClickListener{
        //для щелчка по кнопке
        void onItemClick(int dish_item);
        //для щелчка по карточке
        void onWholeViewClick(int dish_item);
    }
    MyOnButtonClickListener mListener;

    private Context context;
    private ArrayList<Dish> dishes;

    public DishAdapter(Context context, ArrayList<Dish> dishes_, MyOnButtonClickListener listener) {
        this.context = context;
        this.dishes = dishes_;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public DishHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.dish_card_layout, parent, false);
       //задаем слушателя, который будет работать при нажатии на карточку
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //при щелчке передаем номер кликнутой карты на уровень выше (во фрагмент)
                mListener.onWholeViewClick(((RecyclerView)parent).getChildAdapterPosition(v));
            }
        });
        return new DishHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final DishHolder dishHolder, int i) {

        dishHolder.setDetails(dishes.get(i).getName(), dishes.get(i).getPrice());
        //это слушатель для кнопки на карточке
        dishHolder.btnCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final DishHolder saved_dishHolder = dishHolder;
                //получаем позицию карточки в recycleView
                int item = saved_dishHolder.getAdapterPosition();

                //передадим найденную позицию в объект интерфейса, который на самом
                //деле является приведенным классом фрагмента, который создал этот адаптер
                mListener.onItemClick(item);
            }
        });
    }
    @Override
    public int getItemCount() {
        return dishes.size();
    }

}
