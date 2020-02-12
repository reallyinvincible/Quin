package com.exuberant.quin.ui.auth.authenticated;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exuberant.quin.R;
import com.exuberant.quin.data.db.entity.User;
import com.exuberant.quin.ui.auth.AuthActivity;
import com.exuberant.quin.ui.register.RegisterActivity;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.List;

public class AuthenticatedUserAdapter extends RecyclerView.Adapter<AuthenticatedUserAdapter.AuthenticatedViewHolder> {

    List<User> userList;
    Context context;

    public AuthenticatedUserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public AuthenticatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_authenticated_user, parent, false);
        return new AuthenticatedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthenticatedViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.emailTextView.setText(user.getEmail());
        holder.phoneTextView.setText(user.getPhoneNumber());
        holder.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(user);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.getFragmentControlInterface().getViewModel().removeUser(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void registerUser(User localUser) {
        Gson gson = new Gson();
        String userJson = gson.toJson(localUser);
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra("userdata", userJson);
        context.startActivity(intent);
        ((Activity) context).finishAfterTransition();
    }

    class AuthenticatedViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, emailTextView, phoneTextView;
        MaterialButton deleteButton, signinButton;

        public AuthenticatedViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            emailTextView = itemView.findViewById(R.id.tv_email);
            phoneTextView = itemView.findViewById(R.id.tv_phone);
            deleteButton = itemView.findViewById(R.id.btn_remove);
            signinButton = itemView.findViewById(R.id.btn_sign_in);
        }
    }

}
