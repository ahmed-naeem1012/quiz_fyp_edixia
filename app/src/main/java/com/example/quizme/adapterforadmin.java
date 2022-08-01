package com.example.quizme;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class adapterforadmin extends RecyclerView.Adapter<adapterforadmin.Viewholder> {

    Context context;
    ArrayList<adminuser> userArrayList;
    private Itemclicklistener itemclicklistener;


    public adapterforadmin(Context context, ArrayList<adminuser> userArrayList,Itemclicklistener itemclicklistener) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.itemclicklistener=itemclicklistener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.activity_catitem,parent,false);
        return new Viewholder(view).linkadapter(this);
    }

    public void deletedata(){
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder Holder, @SuppressLint("RecyclerView") final int pos) {

        final adminuser adminuser = userArrayList.get(pos);
        Holder.quiz_category.setText(adminuser.getCategoryName());

        Holder.deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

//                Toast.makeText(context.getApplicationContext(),pos,Toast.LENGTH_SHORT).show();


                AlertDialog.Builder builder=new AlertDialog.Builder(Holder.quiz_category.getContext());
                builder.setTitle("Are You Sure");
                builder.setMessage("Deleted data cant be undone");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemclicklistener.onitemclick(userArrayList.get(pos));

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();

            }
        });

    }



    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    public interface Itemclicklistener{
        void onitemclick(adminuser adminuser);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
         TextView quiz_category;
         ImageView deleteB;
         RecyclerView recyclerView;
         private adapterforadmin adapterforadmin;


        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            quiz_category = itemView.findViewById(R.id.namecategory);
            recyclerView=itemView.findViewById(R.id.recyclerViewid);
            deleteB=itemView.findViewById(R.id.deletecategory);


            itemView.findViewById(R.id.deletecategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminuser adminusera = new adminuser();

                }
            });

        }



        public Viewholder linkadapter(adapterforadmin adapterforadmin){
            this.adapterforadmin=adapterforadmin;
            return this;
        };
    }




}

