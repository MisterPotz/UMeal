package com.solidus_snake.best.umeal.dish_selector;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.View;


//этот слушатель определяет переход ниже по иерархии меню
public class CardClickListener implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private Context context;
    CardClickListener(RecyclerView mRecyclerView_, Context context_){
        //получаем подкласс View RecyclerView и контекст, в рамках которого будет задействоваться слушатель
        mRecyclerView = mRecyclerView_;
        //инициализация RecycleView, с которого будут обрабатываться нажатия
        context=context_;
    }
    @Override
    public void onClick(View view) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
    }
}
