package com.aurages.ArestaurantWeb.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aurages.ArestaurantWeb.Dialog.close_Dialoge;
import com.aurages.ArestaurantWeb.Model.LoginRequest;
import com.aurages.ArestaurantWeb.Model.LoginResponse;
import com.aurages.ArestaurantWeb.Model.local.DataManager;
import com.aurages.ArestaurantWeb.R;

import com.aurages.ArestaurantWeb.apiService.retrofit.ApiClient;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.tapadoo.alerter.Alerter;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLoginActivity extends AppCompatActivity {
    private Button doLoginButton;
    private EditText firstNameEditText, binCodeEditText;
    private String firstName, binCode;
    public static String token = "";
    private static final String SHARED_PREF_NAME = "aurages_rest";
    CircleImageView logo;
    TextInputLayout textInputLayout;
    TextInputLayout textInputLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        doLoginButton = findViewById(R.id.doLoginButton);
        firstNameEditText = findViewById(R.id.loginEmployeeNameEditText);
        binCodeEditText = findViewById(R.id.loginEmployeeBinCodeEditText);
        Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        logo = findViewById(R.id.imageView3);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputLayout2 = findViewById(R.id.textInputLayout2);

        logo.startAnimation(animSlideDown);
        textInputLayout.startAnimation(animSlideDown);
        textInputLayout2.startAnimation(animSlideDown);
        firstNameEditText.startAnimation(animSlideDown);
        binCodeEditText.startAnimation(animSlideDown);
        doLoginButton.startAnimation(animSlideDown);

        doLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doValidation();

            }
        });

    }

    void doValidation() {

        firstName = firstNameEditText.getText().toString().trim();
        binCode = binCodeEditText.getText().toString().trim();
        if (firstName.length() == 0) {
            hideAlerter();
            firstNameEditText.setError("REQUIRED");
            firstNameEditText.requestFocus();
        } else if (binCode.length() > 4) {
            hideAlerter();
            binCodeEditText.setError("YOUR BINCODE LENGHT IS TOO LONG");
            binCodeEditText.requestFocus();
        } else if (binCode.length() < 4) {
            hideAlerter();
            binCodeEditText.setError("YOUR BINCODE LENGHT IS TOO SHORT");
            binCodeEditText.requestFocus();
        } else if (binCode.length() == 0) {
            hideAlerter();
            binCodeEditText.setError("REQUIRED");
            binCodeEditText.requestFocus();
        } else {
            performSignin();
        }
    }

    private void performSignin() {
        DataManager.init(getApplicationContext());

        showAlerter("login in", "please wait...");
        LoginRequest loginRequest = new LoginRequest(firstName, binCode);
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<LoginResponse> login = apiInterface.doLogin(loginRequest);
        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideAlerter();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        token = response.body().getToken();
                        Log.i("TAG", token);

                        String firstNamestring = response.body().getUser().getFirstName();
                        String lastName = response.body().getUser().getLastName();
                        String binCodeString = response.body().getUser().getBinCode();
                        DataManager.write(DataManager.firstName, firstNamestring);
                        DataManager.write(DataManager.lastName, lastName);
                        DataManager.write(DataManager.token, token);
                        DataManager.write(DataManager.bincode, binCode);
                        String user_id = response.body().getUser().getId().toString();

                        SharedPreferences sp = getApplication().getSharedPreferences(SHARED_PREF_NAME, getApplication().MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();

                        edit.putString("name", firstNamestring + " " + lastName);
                        edit.putString("pinCode", binCodeString);
                        edit.putString("token", token);
                        edit.putString("user_id", user_id);
                        edit.apply();


                        if (binCodeString.isEmpty()) {
                            startActivity(new Intent(NewLoginActivity.this, PinCodeAc.class));
                        } else {
                            Intent i = new Intent(NewLoginActivity.this, SyncActivity.class);

                            i.putExtra("token", token);

                            startActivity(i);
                        }
                        showResultAlerter("DONE", "Welcome back" + " " + firstNamestring + " " + lastName);

                    } else {
                        //showErrorAlerter("Failed","Try again later");

                    }
                } else {
                    showErrorAlerter("Failed", "PLEASE CHECK YOUR CREDENTIALS");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideAlerter();
                showErrorAlerter("Failed", "connect network");
            }
        });
    }

    void showAlerter(String title, String message) {
        Alerter.create(this).enableVibration(true)
                .setBackgroundColorRes(R.color.orange).setTitle(title).setText(message)
                .enableProgress(true)
                .show();
    }

    void hideAlerter() {
        if (Alerter.isShowing()) {
            Alerter.hide();
        }
    }

    void showErrorAlerter(String title, String messaage) {
        Alerter.create(this).enableVibration(true)
                .setBackgroundColorRes(R.color.red).setTitle(title).setText(messaage)
                .setIcon(R.drawable.ic_error_log)
                .show();
    }

    void showResultAlerter(String title, String message) {
        Alerter.create(this).enableVibration(true)
                .setBackgroundColorRes(R.color.app_blue).setTitle(title).setText(message)
                .setIcon(R.drawable.ic_done___)
                .show();
    }

    @Override
    public void onBackPressed() {

        openDialog();

    }
    public void openDialog() {
        close_Dialoge close_dialoge= new close_Dialoge();
        close_dialoge.show(getSupportFragmentManager(), "close dialog");
    }
}
