package com.example.quizme;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class enrollstudent extends Fragment {

    public enrollstudent() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    enrollstudent binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_enroll, container, false);
    }
    EditText email,name,phone,course;
    ProgressDialog pd;
    FirebaseFirestore database;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Button button= view.findViewById(R.id.enroll);
        pd= new ProgressDialog(getContext());
        database= FirebaseFirestore.getInstance();

        email=view.findViewById(R.id.emailBox);
        name=view.findViewById(R.id.nameBox);
        phone=view.findViewById(R.id.phone);
        course=view.findViewById(R.id.course);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String mail=email.getText().toString().trim();
                String fn=name.getText().toString().trim();
                String phnum=phone.getText().toString().trim();
                String subj=course.getText().toString().trim();

                uploaddata(mail,fn,phnum,subj);
            }

            private void uploaddata(String mail, String fn, String phnum, String subj) {

                pd.setTitle("Enrolling The Student in Course");
                pd.setMessage("Please Wait");
                pd.show();
                String id= UUID.randomUUID().toString();


                Map<String, Object> doc = new HashMap<>();
                doc.put("id",id);
                doc.put("Email", mail);
                doc.put("Name",fn);
                doc.put("Phone Number", phnum);
                doc.put("Course Enrolled",subj);


                database.collection("Enrolled").document(id).set(doc).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Student Enrolled", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                email.setText("");
                                name.setText("");
                                phone.setText("");
                                course.setText("");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Procedure Failed", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }
        });

    }
}