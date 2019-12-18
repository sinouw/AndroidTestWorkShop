package com.example.yassineouni.testworkshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.yassineouni.testworkshop.adapters.PostAdapter;
import com.example.yassineouni.testworkshop.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListPostActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    DatabaseReference myRef;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);

        mRecycler = findViewById(R.id.ls_menus);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Posts");
        final List<Post> posts=new ArrayList<Post>();


        // Read from the database
        myRef.child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    posts.add(child.getValue(Post.class));
                    Log.d("Post:", "Value is: " + child.getValue(Post.class));

                }
                Log.d("Posts", "Value is: " + posts);
                PostAdapter adapter = new PostAdapter(posts);
                mRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error", "Failed to read value.", error.toException());
            }
        });






    }
}
