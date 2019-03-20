package com.solidus_snake.best.umeal.university_canteen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.solidus_snake.best.umeal.R;

public class UniversityCanteensHolder extends RecyclerView.ViewHolder {
    private TextView txtName, txtTime;
    public UniversityCanteensHolder(View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.u_canteen_name);
        txtTime = itemView.findViewById(R.id.u_canteen_working_time);
    }
    //устанавливаем всю информацию на виджетах
    public void setDetails(UniversityCanteen u_c, int day) {
        txtName.setText(u_c.getName());
        txtTime.setText(u_c.getSchedule(day));
    }
}