package com.exuberant.quin.ui.register;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.exuberant.quin.R;
import com.exuberant.quin.data.db.entity.User;
import com.exuberant.quin.data.repository.UserRepository;
import com.exuberant.quin.data.repository.UserRepositoryImpl;
import com.exuberant.quin.ui.home.HomeActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    private User user;
    private TextInputLayout emailTextInputLayout, nameTextInputLayout, passwordTextInputLayout, phoneTextInputLayout;
    private MaterialButton registerButton;
    private RegisterViewModel viewModel;
    public static final int PICK_IMAGE = 902;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    private void initialize() {
        UserRepository repository = new UserRepositoryImpl(this);
        RegisterViewModelFactory viewModelFactory = new RegisterViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RegisterViewModel.class);
        viewModel.accountAdded().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if (account != null) {
                    showSnackBar("Account added successfully", 1);
                    launchHome(account);
                } else {
                    showSnackBar("Could not add account", 0);
                }
            }
        });
        emailTextInputLayout = findViewById(R.id.tl_email);
        passwordTextInputLayout = findViewById(R.id.tl_password);
        nameTextInputLayout = findViewById(R.id.tl_name);
        phoneTextInputLayout = findViewById(R.id.tl_phone_number);
        registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra("userdata")){
            String userData = intent.getStringExtra("userdata");
            Gson gson = new Gson();
            user = gson.fromJson(userData, User.class);
        }
        bindUi();
    }

    private void launchHome(Account account) {
        Gson gson = new Gson();
        String accountString = gson.toJson(account);
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        intent.putExtra("account", accountString);
        startActivity(intent);
        finishAfterTransition();
    }

    private void validate() {
        String name = nameTextInputLayout.getEditText().getText().toString();
        String password = passwordTextInputLayout.getEditText().getText().toString();
        String phone = phoneTextInputLayout.getEditText().getText().toString();
        if (name.length() <= 0){
            showSnackBar("Invalid name", 0);
        } else if (password.length() <= 6) {
            showSnackBar("Password too small", 0);
        } else if (phone.length() != 10){
            showSnackBar("Invalid phone number", 0);
        } else {
            user.setName(name);
            user.setHashedPassword(password);
            user.setPhoneNumber(phone);
            viewModel.addAccount(user);
        }
    }

    private void bindUi() {
        if (user.getEmail() != "" && user.getEmail().length() > 0){
            emailTextInputLayout.getEditText().setText(user.getEmail());
            emailTextInputLayout.getEditText().setEnabled(false);
            emailTextInputLayout.setAlpha(0.5f);
        }
        passwordTextInputLayout.getEditText().setText(user.getHashedPassword());
        nameTextInputLayout.getEditText().setText(user.getName());
        phoneTextInputLayout.getEditText().setText(user.getPhoneNumber());
    }

    void showSnackBar(String message, int color) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.main_layout), message, Snackbar.LENGTH_SHORT);
        if (color == 0) {
            snackbar.getView().setBackgroundResource(R.color.colorErrorSnackbar);
        } else {
            snackbar.getView().setBackgroundResource(R.color.colorSuccessSnackbar);
        }
        snackbar.show();
    }

}
