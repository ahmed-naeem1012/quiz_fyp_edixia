package com.example.quizme;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
//impor t android.support.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class addcategorypage extends AppCompatActivity {

    Button btnbrowse, btnupload;
    EditText txtdata;
    ImageView imgview;
    Uri FilePathUri;
    String userid;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;

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

        btnbrowse = (Button) findViewById(R.id.btnbrowse);
        btnupload = (Button) findViewById(R.id.btnupload);
        txtdata = (EditText) findViewById(R.id.txtdata);
        imgview = (ImageView) findViewById(R.id.image_view);
        userid=firebaseAuth.getCurrentUser().getUid();
        progressDialog = new ProgressDialog(addcategorypage.this);// context name as per your project name


        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                UploadImage();

                File newfile = new File(imageuri.getPath());
                try {
                    compressor=new Compressor(addcategorypage.this).setMaxHeight(125).setMaxWidth(125)
                            .setQuality(50).compressToBitmap(newfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
//                newfile.compareTo(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

                compressor.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte thumb[]= byteArrayOutputStream.toByteArray();


                UploadTask image_path = storageReference.child("user_image").child(userid+".jpg").putBytes(thumb);

                image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            storeuserdata(task,txtdata);

                        }else {
                            String error= task.getException().toString();
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


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

//Out side of Oncreate()
    }

    private void storeuserdata(Task<UploadTask.TaskSnapshot> task, EditText txtdata) {
        String downloaduri;
        if (task!=null){
         downloaduri = storageReference.child("Images").getDownloadUrl().toString();

        }else {
            downloaduri=imageuri.toString();
        }

        Map<String, String> userdata = new HashMap<>();
        userdata.put("categoryName",txtdata.toString())  ;
        userdata.put("categoryImage" ,downloaduri.toString());
        firestore.collection("categories").document(userid).set(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

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


//        Toast.makeText(getApplicationContext(), "passing", Toast.LENGTH_SHORT).show();

//        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            FilePathUri = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
//                imgview.setImageBitmap(bitmap);
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        }
//    }

            }
        }


//        public String GetFileExtension (Uri uri){
//
//            ContentResolver contentResolver = getContentResolver();
//            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//
//        }
//
//
//        public void UploadImage () {
//
//            if (FilePathUri != null) {
//
//                progressDialog.setTitle("Image is Uploading...");
//                progressDialog.show();
//                StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
//                storageReference2.putFile(FilePathUri)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                String TempImageName = txtdata.getText().toString().trim();
//                                progressDialog.dismiss();
//                                Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
//                                @SuppressWarnings("VisibleForTests")
//                                uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());
//                                String ImageUploadId = databaseReference.push().getKey();
//                                databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
//
//
//                            }
//                        });
//            } else {
//
//                Toast.makeText(addcategorypage.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
//
//            }
//        }
//    }

