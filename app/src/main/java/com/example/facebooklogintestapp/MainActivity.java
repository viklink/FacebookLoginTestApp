package com.example.facebooklogintestapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    ProgressDialog dialog;

    private String first_name;
    private String last_name;
    private String email;
    private String id;
    private String image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
        imageView = findViewById(R.id.flowers);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions(Arrays.asList("email", "public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            //Ответ на запрос о регистрации
            public void onSuccess(LoginResult loginResult) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Retrieving data...");
                dialog.show();

                String accessToken = loginResult.getAccessToken().getToken();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        dialog.dismiss();

                        getData(object);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name, last_name, email, id");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Десериализация полученного ответа
    private void getData(JSONObject object) {
        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");

            image_url = "http://graph.facebook.com/"+id+"/picture?type=normal";

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("firstName", first_name);
        intent.putExtra("lastName", last_name);
        intent.putExtra("email", email);
        intent.putExtra("imageUrl", image_url);
        startActivity(intent);
    }
}
