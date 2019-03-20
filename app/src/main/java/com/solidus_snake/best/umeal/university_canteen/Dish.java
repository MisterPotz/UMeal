package com.solidus_snake.best.umeal.university_canteen;

import android.os.Parcel;
import android.os.Parcelable;
//класс блюда, хранящий все характеристики блюда,
//наседуется от Parcelable ля удобства передачи между активностями
public class Dish implements Parcelable {
    private final String name;
    private final String price;
    private final String weight;
    private final String calories;
    private final String ratio;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name,
                this.price,
                this.weight,
                this.calories,
                this.ratio});
    }

    public static final Parcelable.Creator<Dish> CREATOR
            = new Parcelable.Creator<Dish>() {
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };
    Dish(Parcel in){
        String[] data = new String[5];
        in.readStringArray(data);
        name = data[0];
        price = data[1];
        weight = data[2];
        calories = data[3];
        ratio = data[4];
    }
    Dish(String name_, String price_, String weight_, String calories, String ratio){
        name = name_;
        price = price_;
        weight = weight_;
        this.calories = calories;
        this.ratio = ratio;
    }
    Dish(String name_, String price_, String weight_){
        name = name_;
        price = price_;
        weight = weight_;
        this.calories = "";
        this.ratio = "";
    }
    Dish(){
        name = "";
        price = "";
        weight = "";
        calories = "";
        ratio = "";
    }
    public String getName() {
        return name;
    }
    public String getPrice(){
        return price;
    }
    public String getWeight(){
      return weight;
    }
    public String[] getString(){
        String[] to_return = new String[3];
        to_return[0] = name;
        to_return[1] = price;
        to_return[2] = weight;
        return to_return;
    }

    public String getCalories() {
        return calories;
    }

    public String getRatio() {
        return ratio;
    }
}

