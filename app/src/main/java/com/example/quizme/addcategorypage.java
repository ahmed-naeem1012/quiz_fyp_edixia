package com.example.quizme;

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
import android.provider.MediaStore;
//impor t android.support.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class addcategorypage extends AppCompatActivity {

    Button btnbrowse, btnupload;
    EditText txtdata ;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;

    private Uri imageuri;
    private Bitmap compressor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategorypage);

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        btnbrowse = (Button)findViewById(R.id.btnbrowse);
        btnupload= (Button)findViewById(R.id.btnupload);
        txtdata = (EditText)findViewById(R.id.txtdata);
        imgview = (ImageView)findViewById(R.id.image_view);
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

            }
        });


        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(addcategorypage.this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                            PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getApplicationContext(), "Permisssion Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(addcategorypage.this , new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },1);
                    }else {
                        ChooseImage();
                    }


                }else{
                    ChooseImage();
                }
            }

            private void ChooseImage() {
                Toast.makeText(addcategorypage.this, "Choose Image", Toast.LENGTH_SHORT).show();

                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(addcategorypage.this);
            }
        });


//Out side of Oncreate()
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            FilePathUri = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
//                imgview.setImageBitmap(bitmap);
//            }
//            catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        }

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestCode == RESULT_OK){
                imageuri = result.getUri();
                imgview.setImageURI(imageuri);
            }else if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
    }


//    public String GetFileExtension(Uri uri) {
//
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
//
//    }


//    public void UploadImage() {
//
//        if (FilePathUri != null) {
//
//            progressDialog.setTitle("Image is Uploading...");
//            progressDialog.show();
//            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
//            storageReference2.putFile(FilePathUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            String TempImageName = txtdata.getText().toString().trim();
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
//                            @SuppressWarnings("VisibleForTests")
//                            uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());
//                            String ImageUploadId = databaseReference.push().getKey();
//                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
//                        }
//                    });
//        }
//        else {
//
//            Toast.makeText(addcategorypage.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
//
//        }
//    }
}