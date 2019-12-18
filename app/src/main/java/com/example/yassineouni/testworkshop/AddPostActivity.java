package com.example.yassineouni.testworkshop;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yassineouni.testworkshop.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;


public class AddPostActivity extends AppCompatActivity implements View.OnClickListener {

    Button addNewPostButton;
    Button redirectHomeButton;
    EditText title;
    EditText description;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        addNewPostButton= findViewById(R.id.AddNewPostButton);
        redirectHomeButton= findViewById(R.id.redirectHomeBtn);
        title= findViewById(R.id.titleEditText);
        description= findViewById(R.id.descriptionEditText);

        addNewPostButton.setOnClickListener(this);
        redirectHomeButton.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Posts");

    }

    @Override
    public void onClick(View view) {
            if(view.getId()==addNewPostButton.getId()){
              String strTitle  = title.getText().toString();
              String strDescription  = description.getText().toString();

                database = FirebaseDatabase.getInstance();
                String uuid = UUID.randomUUID().toString();
                myRef.child("post")
                        .push()
                        .setValue(new Post(uuid,strTitle,strDescription));
            }
            if(view.getId()==redirectHomeButton.getId()) {
                finish();
            }
        }
}
