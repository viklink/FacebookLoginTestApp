package com.example.facebooklogintestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileImageView;
    private TextView txtName, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.profile_name);
        txtEmail = findViewById(R.id.profile_email);
        profileImageView = findViewById(R.id.profile_image);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String email = intent.getStringExtra("email");
        String imageUrl = intent.getStringExtra("imageUrl");

        txtName.setText(firstName+" "+lastName);
        txtEmail.setText(email);
       // Glide.with(ProfileActivity.this).load(imageUrl).into(profileImageView);
        Picasso.with(ProfileActivity.this).load(imageUrl).into(profileImageView);

    }
}
