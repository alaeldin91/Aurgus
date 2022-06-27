package com.aurages.ArestaurantWeb.Dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;

import com.aurages.ArestaurantWeb.Model.UserHostModel;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SharePref;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

public class Login extends AppCompatDialogFragment {


    EditText password;
    AppCompatButton login_btn;
    ProgressBar log_ProgressBar;




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_lay, null);
        builder.setView(view);

//        Bundle args = getArguments();
//        mat_img_guid = args.getString("mat_img_guid");

        //username = view.findViewById(R.id.log_username);
        password = view.findViewById(R.id.log_pass);
        log_ProgressBar= view.findViewById(R.id.log_ProgressBar);

        login_btn = view.findViewById(R.id.log_btn);




        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
                log_ProgressBar.setVisibility(View.VISIBLE);
                //Toast.makeText(getContext(), ""+username.getText().toString().trim()+"\n"+password.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                if(databaseHelper.LoginHost(password.getText().toString().trim())){
                    Toast.makeText(getContext(), "Login success ^-^", Toast.LENGTH_SHORT).show();

                    UserHostModel userHostModel = databaseHelper.Get_userData(password.getText().toString().trim());
                    SharePref.SaveLogin(getContext(),userHostModel.getU_guid(),userHostModel.getU_name(),true);

                    log_ProgressBar.setVisibility(View.GONE);
                    dismiss();
                }else{
                    log_ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Login error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return builder.create();
    }




}
