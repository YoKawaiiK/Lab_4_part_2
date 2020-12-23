package com.udacity.Lab_4_part_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Массив сэндвичей
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);

        View recyclerView = findViewById(R.id.sandwiches_list);
        assert recyclerView != null;


        setupRecyclerView((RecyclerView) recyclerView, Arrays.asList(sandwiches));
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<String> sandwiches) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, sandwiches));
    }


    // Адаптер для RecyclerView
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final MainActivity mParentActivity;
        private final List mValues;


        // Конструктор
        SimpleItemRecyclerViewAdapter(MainActivity parent, List<String> items) {
            mValues = items;
            mParentActivity = parent;
        }

        // Слушатели кликов по элементам списка
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Получение номера бутерброда из тега элемента
                int position = (int) view.getTag();
                // Получаем контектс
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                // Передача параметров  (номер бутерброда)
                intent.putExtra(DetailActivity.EXTRA_POSITION, position);
                // Запускаем Activity с описание бутерброда
                context.startActivity(intent);
            }
        };


        // возвращает объект ViewHolder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        // выполняет привязку объекта ViewHolder к объекту по определенной позиции
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            // название бутерброда в поле tv_name каждого элемента списка
            holder.mIdView.setText( String.valueOf(mValues.get(position)));
            // Номер элемента в тег элемента списка
            holder.itemView.setTag(position);
            // Навешиваем слушатель
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        // возвращает количество объектов в списке
        @Override
        public int getItemCount() {
            return mValues.size();
        }


        // хранит данные по одному объекту
        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.tv_name);
            }
        }


    }
}