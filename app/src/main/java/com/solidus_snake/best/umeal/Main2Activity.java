package com.solidus_snake.best.umeal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.solidus_snake.best.umeal.dish_selector.DishCart;
import com.solidus_snake.best.umeal.university_canteen.Dish;
import com.solidus_snake.best.umeal.university_canteen.DishBuilderJSON;
import com.solidus_snake.best.umeal.university_canteen.UniversityBuilderJSON;
import com.solidus_snake.best.umeal.university_canteen.UniversityCanteen;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Main2Activity extends AppCompatActivity implements DishCardFragment.OnClickListener {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DishCart dishCart;
    private ImageButton imgBtnToCart;
    private final int GET_DISH_TO_CART = 3;
    private final int GET_UPDATED_CART = 0;
    private final int DISH_CHANGED = 1;
    private final int DISH_NOT_CHANGED = 2;
    private int currentDay;
    private int currentCanteen;
    private int categories_amount;
    //при щелчке на кнопку ддобавить в корзину на карточке блюда
    //вызывается этот метод из фрагмента и передается блюдо, которое следует добавить
    //в корзину
    @Override
    public void onItemClick(Dish dish, int category) {

        dishCart.addDish(dish);
    }

    @Override
    public void onCardClick(Dish dish, int category) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("dish", dish);
        Intent intent = new Intent(getBaseContext(), DishInfoActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, GET_DISH_TO_CART);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dishCart = new DishCart();
        //найти Toolbar и произвести настройки стрелки назад и ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //получить данные с предыдущего окна (какая столовая была выбрана)
        Bundle input = this.getIntent()
                            .getExtras();

        currentCanteen = input.getInt("canteen_number");
        currentDay = input.getInt("current_day");
        categories_amount = input.getInt("categories_amount");

        //задать адаптер для tabLayout
        adapter = new TabAdapter(getSupportFragmentManager());

        //создать класс столовой из .json и забрать оттуда необходимые категории блюд
        viewPager = (ViewPager) findViewById(R.id.menu_list_container);
        viewPager.setAdapter(adapter);

        //найти tabLayout
        tabLayout = (TabLayout) findViewById(R.id.menu_categories_tabs);

        //перекрёстная настройка слушателей
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        //отдать tabLayout связанный с ним PageViewer
        tabLayout.setupWithViewPager(viewPager);


        //в фоне выполняем чтение файлов
        for (int i =0; i < categories_amount; i++){
            MyAsyncTask mTask = new MyAsyncTask();
            mTask.execute(i);
        }


        // Set up the ViewPager with the sections adapter.

        //настроить  кнопку корзины
        imgBtnToCart = (ImageButton) findViewById(R.id.to_cart_btn);
        imgBtnToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (dishCart.getSize() == 0){
                    Snackbar.make(view, "Вы еще ничего не выбрали :-(", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                Intent cart = new Intent(getBaseContext(), CartActivity.class);
                cart.putExtra("selected_dishes_list", dishCart);
                //устанавливаем то, что активность списка корзины должна вернуть результат
                startActivityForResult(cart, GET_UPDATED_CART);

            }
        });
    }
    //этот класс нужен, чтобы прога не виснула при переходе во вторую активность
    private class MyAsyncTask extends AsyncTask<Integer, Integer, Bundle> {
        @Override
        protected void onProgressUpdate(Integer... progress) {
            // [... Обновите индикатор хода выполнения, уведомления или другой
            // элемент пользовательского интерфейса ...]
        }

        //при получении данных добавляем все в tabAdapter
        @Override
        protected void onPostExecute(Bundle result) {
            // [... Сообщите о результате через обновление пользовательского
            // интерфейса, диалоговое окно или уведомление ...]
            String category = result.getString("category");
            DishCardFragment new_fragment = new DishCardFragment();

            //закидываем сюда прям всё что нужно
            new_fragment.setArguments(result);
            adapter.addFragment(new_fragment, category);
            adapter.notifyDataSetChanged();
        }

        //передаем сюда номер категории
        @Override
        protected Bundle doInBackground(Integer... parameter) {
            UniversityBuilderJSON builder = new UniversityBuilderJSON(getResources());
            builder.setCanteenToLook(currentCanteen).setDayToBuild(currentDay).setSetOnlyWorkingTime(true);
            String[] categories = builder.getCategories();
            DishBuilderJSON dish_builder = new DishBuilderJSON(getResources());
            Dish[] dishes;
                //добавление окошек tab в связи с новой категорией блюд
                //считаем пока, что день всегда понедельник
            Bundle bundle = new Bundle();

            dish_builder.setCanteenItem(currentCanteen)
                    .setCategory(parameter[0]).setDay(currentDay).setFullDishBuild(true);
            dishes = dish_builder.getObjectFromJSON();

            //пихаем всё в bundle
            bundle.putParcelableArray("dishes", dishes);
            bundle.putString("category", categories[parameter[0]]);
            return bundle;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case GET_UPDATED_CART:
                if (data == null) return;
                if (resultCode == DISH_NOT_CHANGED) return;
                if (resultCode == DISH_CHANGED){
                    dishCart=data.getExtras().getParcelable("selected_dishes_list");
                    return;
                }
            case GET_DISH_TO_CART:
                Dish returned_dish = data.getExtras().getParcelable("dish");
                int times_selected = data.getExtras().getInt("times_selected");
                for (int i = 0; i < times_selected; i++){
                    dishCart.addDish(returned_dish);
                }
                return;
        }

    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // do something useful
                finish();
                return (true);
            default:
                return (super.onOptionsItemSelected(item));
        }
    }

//адаптер для фрагментов
    public class TabAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();//сами фрагменты, которые будут отображаться
        private final List<String> mFragmentTitleList = new ArrayList<>(); //Названия категорий

        TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
