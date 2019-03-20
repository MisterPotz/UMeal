package com.solidus_snake.best.umeal.university_canteen;

import android.content.res.Resources;

import com.solidus_snake.best.umeal.JSONBuilder.JSONBuilder;
import com.solidus_snake.best.umeal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class DishBuilderJSON extends JSONBuilder<Dish[]> {
    private int canteen_item;
    private int day;
    private int category;
    private String string_from_json;



    //этот параметр нужен для того, чтобы определить, нужно ли читать джисон объект блюда полностью
    //если нет - ускоряет работу
    //в последствии так и не понадобилось, но лень чистить код
    private boolean full_dish_build;

    //нужен номер конкретного блюда, когда понадобится полная сборка - чтобы не проверять все подряд
    private int dish;

    public void setFullDishBuild(boolean full_dish_build) {
        this.full_dish_build = full_dish_build;
    }

    public DishBuilderJSON setCanteenItem(int canteen_item) {
        this.canteen_item = canteen_item;
        return this;
    }

    public DishBuilderJSON setDay(int day) {
        this.day = day;
        return this;
    }

    public DishBuilderJSON setCategory(int category) {
        this.category = category;
        return this;
    }

    public DishBuilderJSON(Resources res){
        InputStream is = res.openRawResource(R.raw.university_canteens);
        Scanner scanner = new Scanner(is);
        StringBuilder builder = new StringBuilder();
        while(scanner.hasNextLine()){
            builder.append(scanner.nextLine());
        }
        //сразу читаем весь джисон и сохраняем его
        string_from_json = builder.toString();
    }
    //получить только одно блюдо
    public Dish getDishFromJSON(){

        Dish dish_to_ret = new Dish();
        try {
            JSONObject root= new JSONObject(string_from_json);
            JSONObject u_canteen = root.getJSONArray("university-canteens")
                    .getJSONObject(canteen_item); //получаем необходимую столовую

            //получаем расписание для столовой
            JSONArray schedules = u_canteen.getJSONArray("schedule");

            //получаем объект требуемого дня
            JSONObject time = schedules.getJSONObject(day);

            //получаем категории выбранного дня
            JSONArray categories_list = time.getJSONArray("categories");

            //получаем объект массива блюд требуемой категории
            JSONArray dish_list_json = categories_list.getJSONObject(category).getJSONArray("list");

            //задаём размерность требуемого массива блюда
            if (this.dish < 0 || this.dish > dish_list_json.length()) {
                throw new IllegalArgumentException();
            }
            JSONObject dish = dish_list_json.getJSONObject(this.dish);
            dish_to_ret = new Dish(dish.getString("name"),
                    dish.getString("price").concat(".0"),
                    dish.getString("weight"),
                    //здесь видно, что калории и соотношение не заложены жестко, а по-честному
                    //подгружаются из БД
                    //просто очень геморройно все это искать, поэтому в джисоне конкретно эти данные
                    //одинаковы для всех
                    dish.getString("calories"),
                    dish.getString("ratio"));
            //возвращаем блюда
            return dish_to_ret;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return dish_to_ret;
    }

    //получить массив всех блюд под одной категорией из джисон
    @Override
    public Dish[] getObjectFromJSON() {
        //в начале получим строку, которую будем парсить на джсон


        Dish[] dishes = new Dish[0];
        try {
            JSONObject root= new JSONObject(string_from_json);
            JSONObject u_canteen = root.getJSONArray("university-canteens")
                    .getJSONObject(canteen_item); //получаем необходимую столовую

            //получаем расписание для столово
            JSONArray schedules = u_canteen.getJSONArray("schedule");

            //получаем объект требуемого дня
            JSONObject time = schedules.getJSONObject(day);

            //получаем категории выбранного дня
            JSONArray categories_list = time.getJSONArray("categories");

            //получаем объект массива блюд требуемой категории
            JSONArray dish_list_json = categories_list.getJSONObject(category).getJSONArray("list");

            //задаём размерность требуемого массива блюда
            int dishes_length = dish_list_json.length();
            dishes = new Dish[dishes_length];

            if (full_dish_build) {
                //получаем и записываем все блюда
                for (int y = 0; y < dishes_length; y++) {
                    JSONObject dish = dish_list_json.getJSONObject(y);
                    dishes[y] = new Dish(dish.getString("name"),
                            dish.getString("price"),
                            dish.getString("weight"),
                            //здесь видно, что калории и соотношение не заложены жестко, а по-честному
                            //подгружаются из БД
                            //просто очень геморройно все это искать, поэтому в джисоне конкретно эти данные
                            //одинаковы для всех
                            dish.getString("calories"),
                            dish.getString("ratio"));
                }
            }
            else
                //это, кажется, уже не нужно, но пусть будет (не хочется проверять)
                for (int y = 0; y < dishes_length; y++) {
                    JSONObject dish = dish_list_json.getJSONObject(y);
                    dishes[y] = new Dish(dish.getString("name"),
                            dish.getString("price"),
                            dish.getString("weight"));
                }
            //возвращаем блюда
            return dishes;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dishes;
    }
}
