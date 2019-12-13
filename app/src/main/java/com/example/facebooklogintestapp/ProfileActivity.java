package com.example.facebooklogintestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView txtName, txtBirthday, txtEmail, txtLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profile_image);
        txtName = findViewById(R.id.profile_name);
        txtBirthday = findViewById(R.id.profile_birthday);
        txtEmail = findViewById(R.id.profile_email);
        txtLink = findViewById(R.id.profile_link);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String birthday = intent.getStringExtra("birthday");
        String email = intent.getStringExtra("email");
        String imageUrl = intent.getStringExtra("imageUrl");
        String profileUrl = intent.getStringExtra("profileUrl");

        Picasso.with(ProfileActivity.this).load(imageUrl).into(profileImageView);
        txtName.setText(firstName+" "+lastName);
        txtBirthday.setText(birthday);
        txtEmail.setText(email);
        txtLink.setText(profileUrl);

    }
}
