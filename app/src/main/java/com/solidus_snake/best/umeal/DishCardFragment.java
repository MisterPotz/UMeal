package com.solidus_snake.best.umeal;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.solidus_snake.best.umeal.dish_selector.DishAdapter;
import com.solidus_snake.best.umeal.university_canteen.Dish;


import java.util.ArrayList;

//фрагмент, который будет использоваться как список одной категории элемента tabLayout
public class DishCardFragment extends Fragment {
    public DishCardFragment() {
        // Required empty public constructor
    }

    //заготавливаем слушатели для связи с активностью
    OnClickListener onClickListener;
    public interface OnClickListener{

        //это для щелчка по кнопочке карточки
        void onItemClick(Dish dish, int category);

        //это для щелчка по самой карточке
        void onCardClick(Dish dish, int category);
    }

    private RecyclerView recyclerView;
    private ArrayList<Dish> dishes;
    private int category;
    private DishAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //получаем данные из активности
        Dish[] dishes = (Dish[]) getArguments().getParcelableArray("dishes");

        //получаем полный список блюд для данной категории, за которую отвечает фрагмент
        category = getArguments().getInt("category");

        //переписываем в ArrayList все блюда
        this.dishes = new ArrayList<>(dishes.length);

        for (Dish dish : dishes){
            this.dishes.add(dish);
        }
        //мы получили все блюда
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        //настраиваем слушатель, как параметр служит активность-наследник интерфейса фрагмента
        setOnAddToCartClickListener(getActivity());

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main2, parent, false);

        // 1. получаем ссылку на RecyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.tab_recycler_view);

        // 2. производим set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        // 3. создаём адаптер списка блюд в recycleView
        DishAdapter.MyOnButtonClickListener listener = new DishAdapter.MyOnButtonClickListener() {
            @Override
            public void onItemClick(int dish_item) {

                //мы соединили активити и фрагмент через интерфейс. а потом фрагмент
                //и его составляющие RecycleView также - через интерфейс и слушатель
                //теперь при нажатии на кнопку вызовется слушатель и выполнится функция
                //прописанная в активности - передаем туда выбранное блюдо
                //чтобы оно было занесено в корзину
                onClickListener.onItemClick(dishes.get(dish_item), category);
            }

            //обработчик нажатия на всю карточку
            @Override
            public void onWholeViewClick(int dish_item) {
                onClickListener.onCardClick(dishes.get(dish_item), category);
            }
        };

        adapter = new DishAdapter(getContext(), dishes, listener);

        // 4. устанавливаем адаптер во view
        recyclerView.setAdapter(adapter);

        return rootView;
    }
//установка слушателя
    private void setOnAddToCartClickListener(Activity activity) {
        try {
            onClickListener = (OnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

}
