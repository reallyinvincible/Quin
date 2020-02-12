package com.exuberant.quin.ui.auth.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exuberant.quin.R;
import com.exuberant.quin.data.db.entity.User;
import com.exuberant.quin.ui.auth.AuthActivity;
import com.exuberant.quin.ui.auth.authenticated.AuthenticatedBottomSheet;
import com.exuberant.quin.ui.register.RegisterActivity;
import com.exuberant.quin.utils.ValidatorUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public class LoginFragment extends Fragment {

    private TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    private MaterialButton loginButton, signupButton, authUserButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        emailTextInputLayout = view.findViewById(R.id.tl_login_email);
        passwordTextInputLayout = view.findViewById(R.id.tl_login_password);
        loginButton = view.findViewById(R.id.btn_login);
        signupButton = view.findViewById(R.id.btn_signup);
        authUserButton = view.findViewById(R.id.btn_auth_users);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.getFragmentControlInterface().hideKeyboard();
                validate();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.getFragmentControlInterface().switchToSignup();
            }
        });
        authUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenticatedBottomSheet bottomSheet = new AuthenticatedBottomSheet();
                bottomSheet.show(getChildFragmentManager(), "");
            }
        });
    }

    private void validate() {
        String email = emailTextInputLayout.getEditText().getText().toString();
        String password = passwordTextInputLayout.getEditText().getText().toString();
        if (!ValidatorUtils.isValidEmail(email)) {
            AuthActivity.getFragmentControlInterface().showSnackbar("Invalid email", 0);
        } else {
            AuthActivity.getFragmentControlInterface().startLoading();
            loginWithEmail(email, password);
        }
    }

    void loginWithEmail(String email, final String password) {
        final FirebaseAuth firebaseAuth = AuthActivity.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                AuthActivity.getFragmentControlInterface().showSnackbar("Authentication successful", 1);
                                User localUser = new User(user.getUid(), user.getEmail(), user.getEmail(), password, "");
                                AuthActivity.getFragmentControlInterface().stopLoading();
                                registerUser(localUser);
                            } else {
                                AuthActivity.getFragmentControlInterface().showSnackbar("Please verify the link sent to your email", 1);
                                AuthActivity.getFragmentControlInterface().stopLoading();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            AuthActivity.getFragmentControlInterface().showSnackbar("Authentication failed", 0);
                            AuthActivity.getFragmentControlInterface().stopLoading();
                        }

                    }
                });
    }

    private void registerUser(User localUser) {
        Gson gson = new Gson();
        String userJson = gson.toJson(localUser);
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        intent.putExtra("userdata", userJson);
        startActivity(intent);
        getActivity().finishAfterTransition();
    }


}
