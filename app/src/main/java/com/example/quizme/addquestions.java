package com.example.quizme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addquestions extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestions);


        final EditText category,question,option1,option2,option3,option4,answer,index;
        final Button upload;
        final FirebaseFirestore db;
        final ProgressDialog pd;


        category=findViewById(R.id.quizcategory);
        question=findViewById(R.id.question);
        option1=findViewById(R.id.option_01);
        option2=findViewById(R.id.option_02);
        option3=findViewById(R.id.option_03);
        option4=findViewById(R.id.option_04);
        answer=findViewById(R.id.answer);
        index=findViewById(R.id.index);
        upload=findViewById(R.id.uploadqs);

        db=FirebaseFirestore.getInstance();
        pd=new ProgressDialog(this);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String categoryget= category.getText().toString();
              String questionget= question.getText().toString();
              String option1get= option1.getText().toString();
              String option2get= option2.getText().toString();
              String option3get= option3.getText().toString();
              String option4get= option4.getText().toString();
              String answerget= answer.getText().toString();


              Editable indexget=index.getText();

              uploaddata(categoryget,questionget,option1get,option2get,option3get,option4get,answerget,indexget);
            }

            private void uploaddata(final String categoryget, final String questionget, final String option1get,
                                    final String option2get, final String option3get,
                                    final String option4get, final String answerget, Editable indexget) {

                pd.show();
                pd.setTitle("Adding Question");
                pd.setMessage("Please Wait");



                final Map<String, Object> doc = new HashMap<>();

                doc.put("question",questionget);
                doc.put("option1",option1get);
                doc.put("option2",option2get);
                doc.put("option3",option3get);
                doc.put("option4",option4get);
                doc.put("answer",answerget);
                doc.put("index", Integer.valueOf(String.valueOf(indexget)));

                final CollectionReference categories= db.collection("categories");


                categories.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            CategoryModel categoryModel = documentSnapshot.toObject(CategoryModel.class);
                            categoryModel.setCategoryId(documentSnapshot.getId());

                            String namegetter = categoryModel.getCategoryName().toUpperCase(Locale.ROOT).trim();
                            String namecheck= category.getText().toString().toUpperCase(Locale.ROOT).trim();
                            String var;


                            if (namegetter.length()==namecheck.length()){
                                var=categoryModel.getCategoryId();

                                db.collection("categories").document(var).collection("questions").add(doc).
                                        addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                                pd.dismiss();
                                                Toast.makeText(addquestions.this, "Question Added", Toast.LENGTH_SHORT).show();
                                                category.setText("");
                                                question.setText("");
                                                option1.setText("");
                                                option2.setText("");
                                                option3.setText("");
                                                option4.setText("");
                                                answer.setText("");
                                                index.setText("");


                                            }
                                        });
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addquestions.this, "Error Occurred. Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}