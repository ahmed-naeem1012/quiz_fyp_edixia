package com.example.quizme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizme.databinding.FragmentLeaderboardsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class adminpanel extends AppCompatActivity {


    private RecyclerView recyclerview;
    ArrayList<adminuser> userarraylist;
    adapterforadmin adapterforadmin;
    FirebaseFirestore database;
    Button addcategorybtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);


        recyclerview = findViewById(R.id.recyclerViewid);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        addcategorybtn=findViewById(R.id.addCatB);


        database = FirebaseFirestore.getInstance();
        userarraylist = new ArrayList<adminuser>();
        adapterforadmin = new adapterforadmin(adminpanel.this, userarraylist);

        recyclerview.setAdapter(adapterforadmin);

        loadData();



        addcategorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminpanel.this, addcategorypage.class);
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
//                        Toast.makeText(adminpanel.this, "its still ok", Toast.LENGTH_SHORT).show();
                        userarraylist.add(dc.getDocument().toObject(adminuser.class));

                    }
                    adapterforadmin.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), userarraylist.toString(), Toast.LENGTH_LONG);
                }

            }
        });


//        database.collection("categories")
//                .orderBy("categoryname", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
//                    adminuser adminuser = snapshot.toObject(adminuser.class);
//                    userarraylist.add(adminuser);
//                    Toast.makeText(getApplicationContext(),userarraylist.toString(),Toast.LENGTH_LONG);
//                }
//                for (DocumentChange dc :queryDocumentSnapshots.getDocumentChanges()){
//                    if(dc.getType()==DocumentChange.Type.ADDED){
//                        userarraylist.add(dc.getDocument().toObject(adminuser.class));
//                    }
//                    adapterforadmin.notifyDataSetChanged();
//
//                    Toast.makeText(getApplicationContext(),userarraylist.toString(),Toast.LENGTH_LONG);
//                }

        adapterforadmin.notifyDataSetChanged();
    }
}












