package com.LoginFirebase.Interface;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.LoginFirebase.Models.User;
import com.LoginFirebase.R;
import com.LoginFirebase.Service.Usuario.UserService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private UserService userService;

    private LinearLayout LoginLayout;
    private AnimationDrawable animationDrawable;

    private Button btnLogin,btnRegister, btnGithub;
    private TextView txtEmail,txtPassword;

    private boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = new UserService();

        LoginLayout = findViewById(R.id.loginLayout);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnGithub = findViewById(R.id.github_icon);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        animationDrawable = (AnimationDrawable) LoginLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtEmail.length() <= 0){
                    txtEmail.setError("Ingrese un Correo");
                    txtEmail.requestFocus();
                } else if(txtPassword.length() <= 0){
                    txtPassword.setError("Ingrese una Contraseña");
                    txtPassword.requestFocus();
                } else{
                    login = false;
                    validateUser(txtEmail.getText().toString(),txtPassword.getText().toString());
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
            }
        });

        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/Legend299/android-login-firebase")));
            }
        });

    }
    
    private boolean validateUser(String email, String password){

        userService.Get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    User user = data.getValue(User.class);
                    if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                        login = true;
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
                    }
                }

                if(login == false)
                    Toast.makeText(getApplicationContext(), "Correo o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return login;
    }
}
