package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class excercises_list_layout extends AppCompatActivity {
    public static final String NAME="NAME";
    private TextView textView;
    private String name;
    private int age;
    TextView counter;


    RecyclerView recyclerView,recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercises_list_layout);


        textView=(TextView) findViewById(R.id.mName);
        Intent intent=getIntent();
        name=intent.getStringExtra(NAME);
        counter=(TextView)findViewById(R.id.numberincremented);

        recyclerView=(RecyclerView) findViewById(R.id.rclview1);
        recyclerView2=(RecyclerView) findViewById(R.id.rclview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        String arr[]={"Exidia Recommendations"};

        int icons[]=
                {
                        R.drawable.finalimg1,
                };

        String arr1[]={"BreakFast Items","Fruits","Watches","Stationary Items","Beauty Products"};


        int icons1[]={
                R.drawable.img_5,
                R.drawable.finalimag3,
                R.drawable.finalimag2,
                R.drawable.finalimag4,
                R.drawable.finalimag5
        };


        int icons2[]={
                R.drawable.starimagefinal
        };
        int icons3[]={
                R.drawable.sem,
                R.drawable.sem,
                R.drawable.starimagefinal,
                R.drawable.sem,
                R.drawable.starimagefinal,
        };
        String desc[]={
                "Frequently Bought - 9 Products"
        };
        String desc2[]={
                "3 Products","5 Products","8 Products","5 Products","3 Products"
        };
        recyclerView.setAdapter(new excercises_adapter(this,icons,icons2,arr,desc));
        recyclerView2.setAdapter(new excercises_adapter(this,icons1,icons3,arr1,desc2));
    }


    }
