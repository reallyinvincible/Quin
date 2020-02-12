package com.exuberant.quin.ui.auth.signup;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exuberant.quin.R;
import com.exuberant.quin.ui.auth.AuthActivity;
import com.exuberant.quin.utils.ValidatorUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupFragment extends Fragment {

    private TextInputLayout emailTextInputLayout, passwordTextInputLayout, confirmPasswordTextInputLayout;
    private MaterialButton signupButton, loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        emailTextInputLayout = view.findViewById(R.id.tl_signup_email);
        passwordTextInputLayout = view.findViewById(R.id.tl_signup_password);
        confirmPasswordTextInputLayout = view.findViewById(R.id.tl_signup_confirm_password);
        signupButton = view.findViewById(R.id.btn_signup);
        loginButton = view.findViewById(R.id.btn_login);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.getFragmentControlInterface().hideKeyboard();
                validate();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.getFragmentControlInterface().switchToLogin();
            }
        });
    }

    private void validate() {
        String email = emailTextInputLayout.getEditText().getText().toString();
        String password = passwordTextInputLayout.getEditText().getText().toString();
        String confirmPassword = confirmPasswordTextInputLayout.getEditText().getText().toString();
        if (!password.equals(confirmPassword)) {
            AuthActivity.getFragmentControlInterface().showSnackbar("Password do not match", 0);
        } else if (!ValidatorUtils.isValidEmail(email)) {
            AuthActivity.getFragmentControlInterface().showSnackbar("Invalid email", 0);
        } else {
            AuthActivity.getFragmentControlInterface().startLoading();
            signupWithEmail(email, password);
        }
    }

    //Create account with Firebase
    private void signupWithEmail(String email, String password) {
        final FirebaseAuth firebaseAuth = AuthActivity.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    AuthActivity.getFragmentControlInterface().stopLoading();
                                    AuthActivity.getFragmentControlInterface().showSnackbar("Please verify the link sent to your email", 1);
                                    AuthActivity.getFragmentControlInterface().switchToLogin();
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            AuthActivity.getFragmentControlInterface().showSnackbar("Authentication Failed", 0);
                            AuthActivity.getFragmentControlInterface().stopLoading();
                        }
                    }
                });
    }

}
