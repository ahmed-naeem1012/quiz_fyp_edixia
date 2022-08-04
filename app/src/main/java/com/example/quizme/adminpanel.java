package com.example.quizme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class adminpanel extends AppCompatActivity {


    private RecyclerView recyclerview;
    ArrayList<adminuser> userarraylist;
    adapterforadmin adapterforadmin;
    FirebaseFirestore database;
    Button addcategorybtn,deletequizcategory,addquestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        recyclerview = findViewById(R.id.recyclerViewid);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        addcategorybtn=findViewById(R.id.addCatB);
        deletequizcategory=findViewById(R.id.deletecategory);
        addquestion=findViewById(R.id.addquestion);


        database = FirebaseFirestore.getInstance();
        userarraylist = new ArrayList<adminuser>();
        adapterforadmin = new adapterforadmin(adminpanel.this, userarraylist, new adapterforadmin.Itemclicklistener() {
            @Override
            public void onitemclick(adminuser adminuser) {
                Toast.makeText(getApplicationContext(), adminuser.getCategoryName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerview.setAdapter(adapterforadmin);

        loadData();

        addcategorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminpanel.this, addcategorypage.class);
                startActivity(intent);
            }
        });
        deletequizcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminpanel.this, deletecategory.class);
                startActivity(intent);
            }
        });
        addquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminpanel.this, addquestions.class);
                startActivity(intent);
            }
        });
    }


    private void loadData() {
        database.collection("categories").orderBy("categoryName",
                Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.e("Firestore error",error.getMessage());
                    Toast.makeText(adminpanel.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {

                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        userarraylist.add(dc.getDocument().toObject(adminuser.class));
                    }
                    adapterforadmin.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), userarraylist.toString(), Toast.LENGTH_LONG);
                }

            }
        });

        adapterforadmin.notifyDataSetChanged();
    }
}












