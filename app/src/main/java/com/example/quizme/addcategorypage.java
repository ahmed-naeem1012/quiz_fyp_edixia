package com.example.quizme;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class addcategorypage extends AppCompatActivity {

    Button btnbrowse, btnupload;
    EditText txtdata;
    ImageView imgview;
    String userid;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    UploadTask image_path;
    String image_name;
    String myimg;

    private Uri imageuri;
    private Bitmap compressor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategorypage);

        storageReference = FirebaseStorage.getInstance().getReference("Images");

        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        firestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        btnupload = (Button) findViewById(R.id.btnupload);
        txtdata = (EditText) findViewById(R.id.txtdata);
        imgview = (ImageView) findViewById(R.id.image_view);
        userid=firebaseAuth.getCurrentUser().getUid();
        progressDialog = new ProgressDialog(addcategorypage.this);// context name as per your project name



        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(addcategorypage.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Permisssion Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(addcategorypage.this, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, 1);
                    } else {
                        ChooseImage();
                    }
                } else {
                    ChooseImage();
                }
            }
        });


        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File newfile = new File(imageuri.getPath());
                try {
                    compressor=new Compressor(addcategorypage.this).setMaxHeight(125).setMaxWidth(125)
                            .setQuality(50).compressToBitmap(newfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
                compressor.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte thumb[]= byteArrayOutputStream.toByteArray();


                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                image_name= currentDateandTime+".jpg";
                image_path = storageReference.child("user_image").child(image_name).putBytes(thumb);



                image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){

                                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                StorageReference fileRef = storageRef.child("Images/user_image/"+image_name);

                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        myimg=uri.toString();
//                                        Toast.makeText(addcategorypage.this, myimg, Toast.LENGTH_SHORT).show();
                                        try {
                                            storeuserdata(task,txtdata.getText().toString(),myimg);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                            }
                        else {
                            String error= task.getException().toString();
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void storeuserdata(Task<UploadTask.TaskSnapshot> task, String txtdata,String imagename) throws IOException {

        Map<String, String> userdata = new HashMap<>();
        userdata.put("categoryName",txtdata.toString())  ;
        userdata.put("categoryImage" ,imagename);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        firestore.collection("categories").document(currentDateandTime).set(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Quiz Category Uploaded", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void ChooseImage(){
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(addcategorypage.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);

        imageuri = result.getUri();
        imgview.setImageURI(imageuri);


    }
}



