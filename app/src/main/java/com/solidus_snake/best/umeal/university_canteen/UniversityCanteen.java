package com.solidus_snake.best.umeal.university_canteen;

//класс столовки
//был много раз переделан, так что есть неиспользуемые функции
public class UniversityCanteen{
    private final String name;
    private final DishScheduleContainer[] dish_schedule_container; //этот массив выражает собой полное меню блюд по всем дням недели и категориям
    UniversityCanteen(){
        name = "";
        dish_schedule_container = new DishScheduleContainer[0];
    }
    public UniversityCanteen(String name, DishScheduleContainer[] dish_schedule_container_) {
        this.name = name;
        dish_schedule_container = dish_schedule_container_;
    }

    public String getName(){
        return name;
    }
    public String getSchedule(int i){ //получить рабочее время для указнного дня
        return dish_schedule_container[i].getWorkingTime();
    }
    public String[] getCategories(int i){ //получить категории для указанного дня
        return dish_schedule_container[i].getCategories();
    }
    public int getSizeCategories(){//получить количество типов еды в меню для указанного дня
        return dish_schedule_container[0].getCategories().length;
    }
    public int getSizeCategories(int i){//получить количество типов еды в меню для указанного дня
        return dish_schedule_container[i].getCategories().length;
    } //возвращает количество категорий
    public int getSizeOfDishListAtCategory(int day, int category_item){//возвращает количество блюд в указанной категории указанного дня
        return  dish_schedule_container[day].getSizeOfDishListAtCategory(category_item);
    }
    public String[] getDishList(int day, int category_item){ //получить список блюд для указанного дня в указанной категории
        return dish_schedule_container[day]
                .getCategorizedDish(category_item)
                .getDishesNamesList();
    }

}


