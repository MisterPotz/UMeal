package com.solidus_snake.best.umeal.university_canteen;

import android.content.res.Resources;

import com.solidus_snake.best.umeal.JSONBuilder.JSONBuilder;
import com.solidus_snake.best.umeal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class UniversityBuilderJSON extends JSONBuilder<UniversityCanteen> {

    public UniversityBuilderJSON setDayToBuild(int day_to_build) {
        this.day_to_build = day_to_build;
        return this;
    }

    public UniversityBuilderJSON setCanteenToLook(int canteen_to_look) {
        this.canteen_to_look = canteen_to_look;
        return this;
    }

    public UniversityBuilderJSON setSetOnlyWorkingTime(boolean set_only_working_time) {
        this.set_only_working_time = set_only_working_time;
        return this;
    }
    private int day_to_build;
    private int canteen_to_look;
    private boolean set_only_working_time;
    private String string_from_json;
    String[] categories_full_list; //категории для текущего дня


    //настройки по умолчанию
    public UniversityBuilderJSON(Resources res){
        setSetOnlyWorkingTime(true);
        setCanteenToLook(0);
        setDayToBuild(0);
        InputStream is = res.openRawResource(R.raw.university_canteens);
        Scanner scanner = new Scanner(is);
        StringBuilder builder = new StringBuilder();
        while(scanner.hasNextLine()){
            builder.append(scanner.nextLine());
        }
        string_from_json = builder.toString();
    }

    @Override
    public UniversityCanteen getObjectFromJSON() {
        String name = "";
        try {
            JSONObject root= new JSONObject(string_from_json);
            JSONObject u_canteen = root.getJSONArray("university-canteens")
                    .getJSONObject(canteen_to_look); //получаем необходимую столовую
            name = u_canteen.getString("name"); //получаем название столовой

            //получаем расписание для столовой
            JSONArray schedules = u_canteen.getJSONArray("schedule");

            int schedules_length = schedules.length();

            //задаём для каждого дня собственное расписание столовой
            String working_times[] = new String[schedules.length()];
            for (int i = 0; i < schedules_length; i++){
                working_times[i]  =  schedules.getJSONObject(i).getString("time");
            }

            //создаем класс-дату для столовок, который потом инициализирует требуемую столовую
            DishScheduleContainer[] containers = new DishScheduleContainer[schedules_length];
            for (int i = 0; i < schedules_length; i++){
                containers[i] = new DishScheduleContainer(new CategorizedDish[0], working_times[i]);
            }

            //возвращаем частично заданную университетскую столовку (не заданы блюда)
            return new UniversityCanteen(name, containers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new UniversityCanteen();

    }

    public int getAmountOfCategories(){

        String name = "";
        try {
            JSONObject root= new JSONObject(string_from_json);
            JSONObject u_canteen = root.getJSONArray("university-canteens")
                    .getJSONObject(canteen_to_look); //получаем необходимую столовую
            name = u_canteen.getString("name"); //получаем название столовой

            //получаем расписание для столовой
            JSONArray schedules = u_canteen.getJSONArray("schedule");
            JSONObject full_day_info = schedules.getJSONObject(day_to_build);
            JSONArray categories = full_day_info.getJSONArray("categories");
            return categories.length();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String[] getCategories() {


        String name = "";
        try {
            JSONObject root= new JSONObject(string_from_json);
            JSONObject u_canteen = root.getJSONArray("university-canteens")
                    .getJSONObject(canteen_to_look); //получаем необходимую столовую
            name = u_canteen.getString("name"); //получаем название столовой

            //получаем расписание для столовой
            JSONArray schedules = u_canteen.getJSONArray("schedule");
            JSONObject full_day_info = schedules.getJSONObject(day_to_build);
            JSONArray categories = full_day_info.getJSONArray("categories");
            int categories_length = categories.length();
            String[] categories_string = new String[categories_length];
            for (int i =0; i < categories_length; i++){
                categories_string[i] = categories.getJSONObject(i).getString("name");
            }
            return categories_string;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}
