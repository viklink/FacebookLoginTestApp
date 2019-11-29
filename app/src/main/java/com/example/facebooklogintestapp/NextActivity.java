package com.example.facebooklogintestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NextActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserRecyclerAdapter adapter;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
/*
        users.add(new User("User1", "123123"));
        users.add(new User("User2", "456456"));
        users.add(new User("User3", "789789"));
        users.add(new User("User4", "123456"));
        users.add(new User("User5", "456789"));
        users.add(new User("User6", "123789"));
        users.add(new User("User7", "789456"));
        users.add(new User("User8", "456123"));
        users.add(new User("User9", "456123"));
*/
        initRecyclerView();
        loadUsers();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserRecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void loadUsers() {
        List<User> users = getUsers();
        adapter.setUsers(users);
    }
    private List<User> getUsers() {
        return Arrays.asList(
                new User("User1", "123123"),
                new User("User2", "456456"),
                new User("User3", "789789"),
                new User("User4", "123456"),
                new User("User5", "456789"),
                new User("User6", "123789"),
                new User("User7", "789456"),
                new User("User8", "456123"),
                new User("User9", "456123")
        );
    }
}
