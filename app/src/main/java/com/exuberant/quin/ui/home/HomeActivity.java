package com.exuberant.quin.ui.home;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.exuberant.quin.R;
import com.exuberant.quin.data.model.AccountWrapper;
import com.exuberant.quin.data.repository.UserRepository;
import com.exuberant.quin.data.repository.UserRepositoryImpl;
import com.exuberant.quin.ui.auth.AuthActivity;
import com.exuberant.quin.ui.home.accounts.AccountsBottomSheet;
import com.exuberant.quin.ui.home.users.UsersBottomSheet;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity {

    private static HomeControlInterface controlInterface;
    private HomeViewModel viewModel;
    private AccountManager accountManager;
    private AccountWrapper accountWrapper;
    private MaterialButton loginNewButton, logoutCurrentButton, showUsersButton, showAccountsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
    }

    private void initialize() {

        Intent intent = getIntent();
        if (intent.hasExtra("account")) {
            String accountString = intent.getStringExtra("account");
            Gson gson = new Gson();
            accountWrapper = gson.fromJson(accountString, AccountWrapper.class);
        }

        UserRepository repository = new UserRepositoryImpl(this);
        HomeViewModelFactory viewModelFactory = new HomeViewModelFactory(repository);
        accountManager = AccountManager.get(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);

        controlInterface = new HomeControlInterface() {
            @Override
            public HomeViewModel getViewModel() {
                return viewModel;
            }

            @Override
            public AccountManager getAccountManager() {
                return accountManager;
            }
        };

        loginNewButton = findViewById(R.id.btn_login_new);
        logoutCurrentButton = findViewById(R.id.btn_logout_current);
        showUsersButton = findViewById(R.id.btn_show_users);
        showAccountsButton = findViewById(R.id.btn_show_accounts);

        loginNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AuthActivity.class));
                finishAfterTransition();
            }
        });

        logoutCurrentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountManager.removeAccount(accountWrapper.getAccount(), null, null);
                startActivity(new Intent(HomeActivity.this, AuthActivity.class));
                finishAfterTransition();
            }
        });

        showUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersBottomSheet bottomSheet = new UsersBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "");
            }
        });

        showAccountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountsBottomSheet bottomSheet = new AccountsBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "");
            }
        });
    }

    public static HomeControlInterface getControlInterface() {
        return controlInterface;
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
