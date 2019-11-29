package com.example.facebooklogintestapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder>{

    private List<User> userList = new ArrayList<>();
    // private onUserClickListener userClickListener;

    public void setUsers(List<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clearUsers() {
        userList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_user, viewGroup, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        //заполнить данными TextView внутри itemView
        holder.bind(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView textName;
        private TextView textPhone;

        public UserViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textPhone = itemView.findViewById(R.id.textPhone);
        }

        public void bind(User user) {
            textName.setText(user.getName());
            textPhone.setText(user.getPhoneNumber());
        }
    }
}
