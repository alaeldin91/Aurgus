package com.aurages.ArestaurantWeb.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.aurages.ArestaurantWeb.Dialog.close_Dialoge;
import com.aurages.ArestaurantWeb.Model.local.DataManager;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SharePref;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.poovam.pinedittextfield.CirclePinField;
import com.poovam.pinedittextfield.LinePinField;
import com.poovam.pinedittextfield.PinField;

import org.jetbrains.annotations.NotNull;

public class PinCodeAc extends AppCompatActivity {
    Toolbar toolbar;
    //PinEntryEditText pinEntry;
    CirclePinField circlePinField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pincode2);
        hideSoftKeyboard();

        //Toolbar
        toolbar = findViewById(R.id.public_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        circlePinField = findViewById(R.id.circleField);
        circlePinField.setOnTextCompleteListener(new PinField.OnTextCompleteListener() {
            @Override
            public boolean onTextComplete (@NotNull String enteredText) {
                DataManager.init(getApplicationContext());


                String userPinCode = DataManager.read(DataManager.bincode,null);

                if(userPinCode.equals(enteredText)){
                    Toast.makeText(PinCodeAc.this,"Success Access",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PinCodeAc.this,DashboardActivity.class));

                }else{
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(0)
                            .playOn(circlePinField);
                    circlePinField.setText("");
                    Toast.makeText(PinCodeAc.this, "Incorrect code !", Toast.LENGTH_SHORT).show();
                }



                return false; // Return true to keep the keyboard open else return false to close the keyboard
            }
        });
    }
    //hide keypad

    private void HideSoftKeys() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void Type(View v) {
        switch (v.getId()){

            case R.id.type0:{
                circlePinField.append("0");
                break;
            }
            case R.id.type1:{
                circlePinField.append("1");
                break;
            }
            case R.id.type2:{
                circlePinField.append("2");
                break;
            }
            case R.id.type3:{
                circlePinField.append("3");
                break;
            }
            case R.id.type4:{
                circlePinField.append("4");
                break;
            }
            case R.id.type5:{
                circlePinField.append("5");
                break;
            }
            case R.id.type6:{
                circlePinField.append("6");
                break;
            }
            case R.id.type7:{
                circlePinField.append("7");
                break;
            }
            case R.id.type8:{
                circlePinField.append("8");
                break;
            }
            case R.id.type9:{
                circlePinField.append("9");
                break;
            }
            case R.id.type_cancel:{
                finish();
                break;
            }
            case R.id.type_clear:{
                circlePinField.setText("");
                break;
            }

        }
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
