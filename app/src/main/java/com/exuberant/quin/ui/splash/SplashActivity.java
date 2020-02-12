package com.exuberant.quin.ui.splash;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.exuberant.quin.R;
import com.exuberant.quin.ui.auth.AuthActivity;
import com.google.android.material.snackbar.Snackbar;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private View view;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 9004;
    private boolean permissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animationView = findViewById(R.id.splash_screen_animation);
        animationView.setSpeed(1.5f);
        view = findViewById(R.id.splash_screen);
        checkPermission();
        animationView.playAnimation();
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                launchHome();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchHome();

            }
        });
    }

    void launchHome() {
        if (permissionGranted) {
            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (this, Manifest.permission.GET_ACCOUNTS)) {

                Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                        "Allow permissions to continue",
                        Snackbar.LENGTH_INDEFINITE).setAction("Grant",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestPermissions(
                                        new String[]{Manifest.permission
                                                .GET_ACCOUNTS},
                                        PERMISSIONS_MULTIPLE_REQUEST);
                            }
                        });
                snackbar.getView().setBackgroundResource(R.color.colorErrorSnackbar);
                snackbar.show();
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .GET_ACCOUNTS},
                        PERMISSIONS_MULTIPLE_REQUEST);
            }
        } else {
            permissionGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_MULTIPLE_REQUEST) {
            if (grantResults.length > 0) {
                boolean contactPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (contactPermission) {
                    permissionGranted = true;
                } else {
                    Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content),
                            "Allow permissions to continue",
                            Snackbar.LENGTH_INDEFINITE).setAction("Grant",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    requestPermissions(
                                            new String[]{Manifest.permission
                                                    .GET_ACCOUNTS},
                                            PERMISSIONS_MULTIPLE_REQUEST);
                                }
                            });
                    snackbar.getView().setBackgroundResource(R.color.colorErrorSnackbar);
                    snackbar.show();
                }
            }
        }
    }

}
