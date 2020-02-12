package com.exuberant.quin.ui.auth;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.Fade;

import com.exuberant.quin.R;
import com.exuberant.quin.data.repository.UserRepository;
import com.exuberant.quin.data.repository.UserRepositoryImpl;
import com.exuberant.quin.ui.auth.login.LoginFragment;
import com.exuberant.quin.ui.auth.signup.SignupFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    private static FirebaseAuth firebaseAuth;
    private AuthViewModel viewModel;
    private static AuthControlInterface fragmentControlInterface;
    private Group loadingGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initialize();
    }

    private void initialize() {
        loadingGroup = findViewById(R.id.group_loading);
        UserRepository repository = new UserRepositoryImpl(this);
        AuthViewModelFactory viewModelFactory = new AuthViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(AuthViewModel.class);
        fragmentControlInterface = new AuthControlInterface() {
            @Override
            public void switchToLogin() {
                switchFragment(new LoginFragment());
            }

            @Override
            public void switchToSignup() {
                switchFragment(new SignupFragment());
            }

            @Override
            public void showSnackbar(String message, int value) {
                AuthActivity.this.showSnackBar(message, value);
            }

            @Override
            public void hideKeyboard() {
                hideSoftKeyBoard();
            }

            @Override
            public void startLoading() {
                loadingGroup.setVisibility(View.VISIBLE);
                stopInteraction();
            }

            @Override
            public void stopLoading() {
                loadingGroup.setVisibility(View.GONE);
                resumeInteraction();
            }

            @Override
            public AuthViewModel getViewModel() {
                return viewModel;
            }
        };
        firebaseAuth = FirebaseAuth.getInstance();
        fragmentControlInterface.switchToLogin();
    }


    void switchFragment(Fragment fragment) {
        fragment.setEnterTransition(new Fade());
        fragment.setExitTransition(new Fade());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
    }

    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static AuthControlInterface getFragmentControlInterface() {
        return fragmentControlInterface;
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

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void stopInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void resumeInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
