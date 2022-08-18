package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class adminlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin2);

        Button login,returnbtn;
        login=findViewById(R.id.adminloginbtn);
        returnbtn=findViewById(R.id.returnbtn);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailBox,passwordBox;
                final String email,password;
                emailBox=findViewById(R.id.emailBox);
                passwordBox=findViewById(R.id.passwordBox);
                email = emailBox.getText().toString();
                password = passwordBox.getText().toString();

                if (email.equals("admin@gmail.com") && password.equals("admin") )
                {
                Intent intent=new Intent(adminlogin.this,adminpanel.class);
                startActivity(intent);}
                else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        returnbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
}