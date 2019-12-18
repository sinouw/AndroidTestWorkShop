package com.example.yassineouni.testworkshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    private EditText userName,email,password;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private Uri pickedImgUri;
    private ImageView imgUserPhoto;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.register_userName);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        imgUserPhoto = findViewById(R.id.register_userPhoto);
        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        findViewById(R.id.signUpBtn1).setOnClickListener(this);
        findViewById(R.id.signInBtn1).setOnClickListener(this);
        findViewById(R.id.register_userPhoto).setOnClickListener(this);
    }
    private void createAccount(final String email, String password, final String userName) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myRef = database.getReference("users");
                            myRef.child(mAuth.getCurrentUser().getUid()).child("username").setValue(userName);
                            myRef.child(mAuth.getCurrentUser().getUid()).child("email").setValue(email);
                            myRef.child(mAuth.getCurrentUser().getUid()).child("imgurl").setValue(pickedImgUri.toString());

                            showToast("Account created");
                            uploadUserPhoto(userName,pickedImgUri,mAuth.getCurrentUser());
                        } else {
                            showToast("Account creation failed "+ task.getException().getMessage());
                        }
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 1 && data != null){
            pickedImgUri = data.getData();
            imgUserPhoto.setImageURI(pickedImgUri);
        }
    }

    private void uploadUserPhoto(final String name,Uri pickedImgUri, final FirebaseUser currentUser){
        mStorage = FirebaseStorage.getInstance().getReference().child("user_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();
                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            showToast("Register complete");
                                            updateUI();
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }

    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signUpBtn1){
            if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || pickedImgUri == null){
                showToast("Please verify all fields");
            } else
                createAccount(email.getText().toString(), password.getText().toString(),userName.getText().toString());
        } else if(view.getId() == R.id.signInBtn1) {
            Intent loginIntent = new Intent(RegisterActivity.this,SignInActivity.class);
            startActivity(loginIntent);
        } else if(view.getId() == R.id.register_userPhoto){
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, 1);
        }
    }
}
