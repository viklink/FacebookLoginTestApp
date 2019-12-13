package com.example.facebooklogintestapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private ImageView imageView;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    ProgressDialog dialog;

    private String first_name;
    private String last_name;
    private String birthday;
    private String email;
    private String id;
    private String link;
    URL profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.finger_print);

        loginButton = findViewById(R.id.login_button);
        imageView = findViewById(R.id.flowers);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions(Arrays.asList("email", "public_profile", "user_birthday", "user_link"));

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
                parameters.putString("fields", "first_name, last_name, birthday, email, id, link");
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

        //Если уже залогинен
        if (AccessToken.getCurrentAccessToken() != null) {

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    getData(object);
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "first_name, last_name, birthday, email, id, link");
            request.setParameters(parameters);
            request.executeAsync();
        }
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
            birthday = object.getString("birthday");
            email = object.getString("email");
            id = object.getString("id");
            link = object.getString("link");

            profile_image
                    = new URL("https://graph.facebook.com/"
                    + id
                    + "/picture?type=normal");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("firstName", first_name);
        intent.putExtra("lastName", last_name);
        intent.putExtra("birthday", birthday);
        intent.putExtra("email", email);
        intent.putExtra("imageUrl", profile_image.toString());
        intent.putExtra("profileUrl", link);
        startActivity(intent);
    }
}
