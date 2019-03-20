package com.solidus_snake.best.umeal.university_canteen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.solidus_snake.best.umeal.R;
import java.util.ArrayList;


public class UniversityCanteensAdapter extends RecyclerView.Adapter<UniversityCanteensHolder>  {

    public interface OnCanteenClickListener{
        void OnClickListener(int data);
    }
    private OnCanteenClickListener mListener;
    private Context context;
    private ArrayList<UniversityCanteen> universities;
    private int currentDay;
    public UniversityCanteensAdapter(Context context, ArrayList<UniversityCanteen> u_canteens, int day, OnCanteenClickListener listener) {
        this.context = context;
        this.universities = u_canteens;
        this.currentDay = day;
        mListener = listener;
    }
    @NonNull
    @Override
    public UniversityCanteensHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_layout, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //получаем номер нажатой столовки
                int item = ((RecyclerView)parent).getChildLayoutPosition(v);
                mListener.OnClickListener(item);
            }
        }); //получаем RecycleView, для которого будет работать слушатель
        return new UniversityCanteensHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull UniversityCanteensHolder universityHolder, int i) {
        UniversityCanteen u_canteen = universities.get(i);
        universityHolder.setDetails(u_canteen, currentDay);
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }
    public void refresh(int i){
        currentDay = i;
        notifyDataSetChanged();
    }
}
