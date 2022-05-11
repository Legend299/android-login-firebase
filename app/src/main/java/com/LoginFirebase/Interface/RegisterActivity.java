package com.LoginFirebase.Interface;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.LoginFirebase.Models.User;
import com.LoginFirebase.R;
import com.LoginFirebase.Service.Usuario.UserService;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int CAMERA_PERMISSION_CODE = 102;
    private static int photo_mode = 0;

    private LinearLayout LoginLayout;
    private AnimationDrawable animationDrawable;

    private Button btnLogin, btnRegister;
    private TextView txtName, txtLastName, txtEmail, txtPassword, txtPassword2, txtAge, txtPhone, txtAddress;

    private CircleImageView btnProfilePicture;

    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LoginLayout = findViewById(R.id.loginLayout);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnProfilePicture = findViewById(R.id.profile_picture);

        txtName = findViewById(R.id.txtName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtPassword2 = findViewById(R.id.txtPassword2);
        txtAge = findViewById(R.id.txtAge);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);


        animationDrawable = (AnimationDrawable) LoginLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        btnProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlertDialog(Gravity.CENTER);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateFields()){
                    if(validatePassword(txtPassword.getText().toString(), txtPassword2.getText().toString())){
                        registerUserData();
                    }else {
                        txtPassword2.setError("Las contraseñas no coinciden");
                        txtPassword2.setText("");
                        txtPassword2.requestFocus();
                    }
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
            }
        });

    }

    private void openAlertDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_profile_dialog);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowsAtrributes = window.getAttributes();
        windowsAtrributes.gravity = gravity;
        window.setAttributes(windowsAtrributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button btnCamera = dialog.findViewById(R.id.btnCamera);
        Button btnGallery = dialog.findViewById(R.id.btnGallery);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                dialog.dismiss();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private boolean validatePassword(String password1, String password2){
        if(password1.equals(password2))
            return true;

        return false;
    }

    private boolean validateFields(){
        if(txtName.getText().toString().length() <= 1){
            txtName.setError("Ingresa tu nombre");
            txtName.requestFocus();
            return false;
        } else if(txtLastName.getText().toString().length() <= 1){
            txtLastName.setError("Ingresa tu apellido");
            txtLastName.requestFocus();
            return false;
        } else if(txtEmail.getText().toString().length() <= 1){
            txtEmail.setError("Ingresa tu correo");
            txtEmail.requestFocus();
            return false;
        } else if(txtPassword.getText().toString().length() <= 1){
            txtPassword.setError("Ingresa tu contraseña");
            txtPassword.requestFocus();
            return false;
        } else if(txtPassword2.getText().toString().length() <= 1){
            txtPassword2.setError("Las contraseñas no coinciden");
            txtPassword2.setText("");
            txtPassword2.requestFocus();
            return false;
        } else if(txtAge.getText().toString().length() < 1){
            txtAge.setError("Ingresa tu edad");
            txtAge.requestFocus();
            return false;
        } else if(txtPhone.getText().toString().length() <= 9){
            txtPhone.setError("Ingresa tu telefono");
            txtPhone.requestFocus();
            return false;
        } else if(txtAddress.getText().toString().length() <= 1){
            txtAddress.setError("Ingresa tu dirección");
            txtAddress.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void registerUserData(){
        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setName(txtName.getText().toString());
        user.setLastName(txtLastName.getText().toString());
        user.setEmail(txtEmail.getText().toString());
        user.setPassword(txtPassword.getText().toString());
        user.setAge(txtAge.getText().toString());
        user.setPhone(txtPhone.getText().toString());
        user.setAddress(txtAddress.getText().toString());

        UserService userService = new UserService();
        userService.AddUser(user,data, this, photo_mode);
    }

    private void fileUpload(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, STORAGE_PERMISSION_CODE);
    }

    private void openCamera(){
        Intent intent = new Intent();
        intent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_PERMISSION_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case STORAGE_PERMISSION_CODE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    photo_mode = STORAGE_PERMISSION_CODE;
                    this.data = data;
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap getSelectedImage = BitmapFactory.decodeFile(filePath);

                    btnProfilePicture.setImageBitmap(getSelectedImage);
                }
                break;
            case CAMERA_PERMISSION_CODE:
                if(resultCode == RESULT_OK){
                    photo_mode = CAMERA_PERMISSION_CODE;
                    this.data = data;
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    btnProfilePicture.setImageBitmap(imageBitmap);
                    }
                    break;
        }

    };

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
        }
        else {
            switch (requestCode){
                case STORAGE_PERMISSION_CODE:
                    fileUpload();
                    break;
                case CAMERA_PERMISSION_CODE:
                    openCamera();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
            else {
                Toast.makeText(this, "No hay permisos para usar la cámara", Toast.LENGTH_SHORT) .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fileUpload();
            } else {
                Toast.makeText(this, "No hay permisos para usar la galería", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
