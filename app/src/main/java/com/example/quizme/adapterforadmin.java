package com.example.quizme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class adapterforadmin extends RecyclerView.Adapter<adapterforadmin.Viewholder> {

    Context context;
    ArrayList<adminuser> userArrayList;

    public adapterforadmin(Context context, ArrayList<adminuser> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.activity_catitem,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder Holder, int pos) {

        adminuser adminuser = userArrayList.get(pos);
        Holder.quiz_category.setText(adminuser.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
         TextView quiz_category;
         Button deleteB;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            quiz_category = itemView.findViewById(R.id.namecategory);
//            deleteB = itemView.findViewById(R.id.deletecategory);
        }
    }
}

