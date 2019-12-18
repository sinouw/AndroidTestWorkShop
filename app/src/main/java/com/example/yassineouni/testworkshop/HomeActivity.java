package com.example.yassineouni.testworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener {


    TextView name,email;
    ImageView userImg;
    FirebaseUser currentUser ;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        userImg = findViewById(R.id.userPhoto);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        email.setText(currentUser.getEmail());
        name.setText(currentUser.getDisplayName());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(userImg);




        findViewById(R.id.redirectPost).setOnClickListener(this);
        findViewById(R.id.redirectToList).setOnClickListener(this);
        findViewById(R.id.signOutBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            if (view.getId() == R.id.signOutBtn) {
                mAuth.signOut();
                Intent loginIntent = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(loginIntent);
                finish();
            }
            if (view.getId() == R.id.redirectPost) {
                Intent addPostIntent = new Intent(HomeActivity.this, AddPostActivity.class);
                startActivity(addPostIntent);
            }
            if (view.getId() == R.id.redirectToList) {
                Intent listPostsIntent = new Intent(HomeActivity.this, ListPostActivity.class);
                startActivity(listPostsIntent);
            }
        }
}
