package com.solidus_snake.best.umeal.dish_selector;

import android.os.Parcel;
import android.os.Parcelable;

import com.solidus_snake.best.umeal.DishCardFragment;
import com.solidus_snake.best.umeal.university_canteen.Dish;

import java.util.ArrayList;

public class DishCart  implements Parcelable {
    private ArrayList<Dish> selectedDishes;

    public static final Parcelable.Creator<DishCart> CREATOR
            = new Parcelable.Creator<DishCart>() {
        public DishCart createFromParcel(Parcel in) {
            return new DishCart(in);
        }

        public DishCart[] newArray(int size) {
            return new DishCart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(selectedDishes);
    }
    public DishCart(Parcel in){
        selectedDishes = new ArrayList<>();
        in.readTypedList(selectedDishes, Dish.CREATOR); //читаем все блюда
    }
    public DishCart(){
        selectedDishes = new ArrayList<>(0);
    }
    public Dish getDish(int i){
        return selectedDishes.get(i);
    }
    public DishCart addDish(Dish item){
        selectedDishes.add(item);
        return this;
    }
    public DishCart pop(){
        selectedDishes.remove(selectedDishes.size()-1);
        return this;
    }
    public DishCart remove(int i){ //удалить выбранный элемент
        selectedDishes.remove(i);
        return this;
    }
    public int getSize(){
        return selectedDishes.size();
    }
}
