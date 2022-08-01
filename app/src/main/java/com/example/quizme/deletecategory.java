package com.example.quizme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class deletecategory extends AppCompatActivity {
    Button DELbtn;
    EditText catname;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletecategory);
        db = FirebaseFirestore.getInstance();
        DELbtn=findViewById(R.id.newdeletebtn);
        catname=findViewById(R.id.newtxtdeletedata);

        DELbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catename=catname.getText().toString();
                catname.setText("");
                Deletedata(catename);
            }
        });
    }

    private void Deletedata(String catname){
        db.collection("categories").whereEqualTo("categoryName",catname)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()&&!task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                    String documentid=documentSnapshot.getId();

                    db.collection("categories").document(documentid)
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Bis Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Hmm Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}