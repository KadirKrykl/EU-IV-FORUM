package com.example.eu_iv_forum.Province;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eu_iv_forum.Forum.ForumsActivity;
import com.example.eu_iv_forum.R;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    public List<City> city_list;

    public CityAdapter(List<City> city_list) {
        this.city_list = city_list;
    }


    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.province_row, parent, false);
        return new CityAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final CityAdapter.ViewHolder holder, int position) {
        String name_data = (String) city_list.get(holder.getAdapterPosition()).getName();
        holder.setNameText(name_data);
        final String city_id=city_list.get(position).CityId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=holder.mView.getContext();
                Intent singleIntent = new Intent(holder.mView.getContext(), ProvinceSingleActivity.class);
                Bundle b = new Bundle();
                b.putString("city_id", city_id);
                singleIntent.putExtras(b);
                context.startActivity(singleIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return city_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView text_city_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setNameText(String name){
            text_city_name = mView.findViewById(R.id.txt_provinceName);
            text_city_name.setText(name);
        }
    }
}
