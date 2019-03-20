package com.solidus_snake.best.umeal.university_canteen;

public class DishScheduleContainer{ //содержит полное меню блюд, разбитых по категориям и расписание столовой на один день
    private final CategorizedDish[] categorizedDishes;
    private final String working_time;
    DishScheduleContainer(){
        categorizedDishes = new CategorizedDish[0];
        working_time = "";
    }
    DishScheduleContainer(CategorizedDish[] dishes_, String working_time_){
        categorizedDishes = dishes_;
        working_time = working_time_;
    }
    DishScheduleContainer(String working_time_){
        categorizedDishes = new CategorizedDish[0];
        working_time = working_time_;
    }
    public CategorizedDish getCategorizedDish(int i){
        return categorizedDishes[i];
    }
    public int getSizeOfDishListAtCategory(int category_item){
        return categorizedDishes[category_item].getDishListSize();
    }
    public String[] getCategories(){
        String[] categories = new String[categorizedDishes.length]; //получим все категории
        for (int i = 0; i < categories.length; i++ ){
            categories[i] = categorizedDishes[i].getCategory(); //записываем категорию
        }
        return categories; //возвращаем все найденные категории
    }
    public String getWorkingTime(){
        return working_time;
    }
}
