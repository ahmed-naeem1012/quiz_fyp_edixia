package com.example.quizme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class excercises_adapter extends RecyclerView.Adapter<excercises_adapter.holder> {

    int increment = 0;
    int incrementStat=0;
    int incrementwatch=0;
    int incrementbeaut=0;
    int incrementfruits=0;
    String data[];
    String description[];
    int images[];
    int Images2[];
    Context context;
    TextView numberincremented;

    public excercises_adapter(Context context, int[] images,int[] images2, String[] data, String[] desc) {
        this.context=context;
        this.images=images;
        this.Images2=images2;
        this.data = data;
        this.description=desc;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.rowlayer1,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder,int position) {
        holder.textView.setText(data[position]);
        holder.imageView.setImageResource(images[position]);
        holder.ratingdisp.setImageResource(Images2[position]);
        holder.descript.setText(description[position]);
    }
    @Override
    public int getItemCount() {
        return data.length;
    }

    class holder extends RecyclerView.ViewHolder{
        ImageView imageView,ratingdisp;
        TextView textView,descript, cartVal;

        RecyclerView recyclerView;
        ConstraintLayout constraintLayout;
        public holder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.img1);
            textView=(TextView) itemView.findViewById(R.id.tv);
            ratingdisp=(ImageView) itemView.findViewById(R.id.rating);
            descript=(TextView) itemView.findViewById(R.id.description);
            recyclerView=(RecyclerView) itemView.findViewById(R.id.rclview1);
            constraintLayout=(ConstraintLayout) itemView.findViewById(R.id.rowfile);
            cartVal = (TextView) itemView.findViewById(R.id.numberincremented);

            imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(textView.getText().toString().equals("Exidia Recommendations"))
                    {
                        Intent intent = new Intent(context.getApplicationContext(),excercises_layout.class);
                        context.startActivity(intent);
                        incrementCart();
                    }
                    else if(textView.getText().toString().equals("BreakFast Items"))
                    {
                        Toast.makeText(context,  "Item from Breakfast items is added to cart", Toast.LENGTH_SHORT).show();
                        incrementCart();
                    }
                    else if(textView.getText().toString().equals("Fruits"))
                    {
                        Toast.makeText(context, "Item From Fruits is added to cart", Toast.LENGTH_SHORT).show();
                        incrementfruits++;
                        cartVal.setText(String.valueOf(incrementfruits));
                    }
                    else if(textView.getText().toString().equals("Watches"))
                    {
                        Toast.makeText(context, "Item From Watches is added to cart", Toast.LENGTH_SHORT).show();
                        incrementwatch++;
                        cartVal.setText(String.valueOf(incrementwatch));
                    }
                    else if(textView.getText().toString().equals("Stationary Items"))
                    {
                        Toast.makeText(context, "Item From Stationary Products is added to cart", Toast.LENGTH_SHORT).show();
                        incrementStat++;
                        cartVal.setText(String.valueOf(incrementStat));
                    }
                    else if(textView.getText().toString().equals("Beauty Products"))
                    {
                        Toast.makeText(context, "Item From Beauty Products is added to cart", Toast.LENGTH_SHORT).show();
                        incrementbeaut++;
                        cartVal.setText(String.valueOf(incrementbeaut));
                    }
                }
                private void incrementCart()
                {
                    Integer count = increment+1;
                    do
                    {
                        increment++;
                        cartVal.setText(String.valueOf(increment));
                    }while(increment != count);
                }
            });

        }
    }
}
