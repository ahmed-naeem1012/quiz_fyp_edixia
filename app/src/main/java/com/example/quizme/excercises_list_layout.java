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

        String arr[]={"Edexia Trending"};

        int icons[]=
                {
                        R.drawable.img,
                };

        String arr1[]={"Visual Rhymes","English Pronunciation","History Trends","Science World","Alphabetic Skills"};


        int icons1[]={
                R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_3,
                R.drawable.finalimag4,
                R.drawable.img_4
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
                "Matika - Top Rated"
        };
        String desc2[]={
                "Nursery Rhymes","Learn English","History Classroom","Learnetic","ABCYa"
        };
        recyclerView.setAdapter(new excercises_adapter(this,icons,icons2,arr,desc));
        recyclerView2.setAdapter(new excercises_adapter(this,icons1,icons3,arr1,desc2));
    }


    }
