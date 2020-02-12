package com.exuberant.quin.ui.home.accounts;

import android.accounts.Account;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exuberant.quin.R;
import com.exuberant.quin.ui.home.HomeActivity;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    List<Account> accountList;

    public AccountAdapter(List<Account> accountList) {
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_account, parent, false);
        return new AccountAdapter.AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        final Account account = accountList.get(position);
        holder.nameTextView.setText(account.name);
        String email = HomeActivity.getControlInterface().getAccountManager().getUserData(account, "email");
        holder.emailTextView.setText(email);
        String phone = HomeActivity.getControlInterface().getAccountManager().getUserData(account, "phone");
        holder.phoneTextView.setText(phone);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.getControlInterface().getViewModel().removeAccount(account);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, emailTextView, phoneTextView;
        MaterialButton deleteButton;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            emailTextView = itemView.findViewById(R.id.tv_email);
            phoneTextView = itemView.findViewById(R.id.tv_phone);
            deleteButton = itemView.findViewById(R.id.btn_remove);
        }
    }

}
