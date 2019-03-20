package com.solidus_snake.best.umeal.university_canteen;

public class CategorizedDish{ //список блюд под одной категорией
    private final Dish[] dishes; //список блюд
    private final String category; //категория блюда
    CategorizedDish(){
        dishes = new Dish[0];
        category = "";
    }
    CategorizedDish(Dish[] dishes_, String category_){
        dishes = dishes_;
        category = category_;
    }
    CategorizedDish(String category_){
        dishes = new Dish[0];
        category = category_;
    }
    public int getDishListSize(){
        return dishes.length;
    }
    public String getCategory() {
        return category;
    }

    public Dish getDish(int item_number) {
        return dishes[item_number];
    }
    public String[] getDishesNamesList(){
        String[] name_array = new String[dishes.length];
        for (int i = 0; i < dishes.length; i++){
            name_array[i] = dishes[i].getName();
        }
        return name_array;
    }

}
