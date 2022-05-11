package com.LoginFirebase.Service.Usuario;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.LoginFirebase.Models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.LoginFirebase.R;

import java.io.ByteArrayOutputStream;

public class UserService {

    private final DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    public UserService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(User.class.getSimpleName());
    }

    public void AddUser(User user, Intent data, Context context, int photo_mode){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Subiendo imagen...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if(photo_mode == 101) {
            AddFileStorage(user, data, context);
        }else if(photo_mode == 102){
            AddFileCamera(user, data, context);
        }

    }

    public void AddFileStorage(User user, Intent data, Context context){
        Uri FileUri = data.getData();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference(user.getId());

        storageReference.child("file"+FileUri.getLastPathSegment());
        storageReference.putFile(FileUri).addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            user.setImagen(String.valueOf(uri));
            progressDialog.dismiss();

            ((Activity)context).finish();
            ((Activity)context).overridePendingTransition(R.anim.no_animation, R.anim.slide_down);

            databaseReference.child(user.getId()).setValue(user);
        }));
    }

    public void AddFileCamera(User user, Intent data, Context context){

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference(user.getId());

        Bitmap picture = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte picture_bytes[] = bytes.toByteArray();

        storageReference.child("file"+user.getId()+".JPEG");

        storageReference.putBytes(picture_bytes).addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            user.setImagen(String.valueOf(uri));
            progressDialog.dismiss();

            ((Activity)context).finish();
            ((Activity)context).overridePendingTransition(R.anim.no_animation, R.anim.slide_down);

            databaseReference.child(user.getId()).setValue(user);
        }));
    }

    public Query Get(){
        return databaseReference;
    }

}
