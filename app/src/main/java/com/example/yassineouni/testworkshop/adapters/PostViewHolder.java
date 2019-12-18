package com.example.yassineouni.testworkshop.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yassineouni.testworkshop.R;
import com.example.yassineouni.testworkshop.models.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private TextView mTitle;
    private TextView mDescription;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.text_menu_title);
        mDescription = itemView.findViewById(R.id.text_menu_description);
    }

    public void bind(Post post) {
        mTitle.setText(post.getTitle());
        mDescription.setText(post.getDescription());
    }
}
