package com.LoginFirebase.Interface;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LoginFirebase.Adapters.HomeAdapter;
import com.LoginFirebase.Models.User;
import com.LoginFirebase.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout HomeLayout;
    private AnimationDrawable animationDrawable;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        HomeLayout = findViewById(R.id.homeLayout);

        animationDrawable = (AnimationDrawable) HomeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(User.class.getSimpleName()), User.class)
                .build();

        homeAdapter = new HomeAdapter(options);
        recyclerView.setAdapter(homeAdapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
        homeAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        homeAdapter.stopListening();
    }

}
