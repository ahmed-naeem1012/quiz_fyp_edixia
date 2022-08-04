package com.example.quizme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
              String indexget= index.getText().toString();

              uploaddata(categoryget,questionget,option1get,option2get,option3get,option4get,answerget,indexget);
            }

            private void uploaddata(String categoryget, final String questionget, String option1get, String option2get, String option3get,
                                    String option4get, String answerget, String indexget) {

                pd.setTitle("Adding Question");
                pd.setMessage("Please Wait");
                pd.show();



                String id= UUID.randomUUID().toString();

                Map<String, Object> doc = new HashMap<>();
                doc.put("id",id);
                doc.put("Quiz Category", categoryget);
                doc.put("Question",questionget);
                doc.put("Option1",option1get);
                doc.put("Option2",option2get);
                doc.put("Option3",option3get);
                doc.put("Option4",option4get);
                doc.put("Answer",answerget);
                doc.put("Index",indexget);



                db.collection("New Questions").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        category.setText("");
                        question.setText("");
                        option1.setText("");
                        option2.setText("");
                        option3.setText("");
                        option4.setText("");
                        answer.setText("");
                        index.setText("");

                        Toast.makeText(addquestions.this, "New Question Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addquestions.this, "Task Failed  ", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });

            }
        });







    }
}