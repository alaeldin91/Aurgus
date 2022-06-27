package com.aurages.ArestaurantWeb.Dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.aurages.ArestaurantWeb.R;

public class TakeAwayDetails extends AppCompatDialogFragment implements View.OnClickListener {



    Button btn_save;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.take_away_details, null);
        builder.setView(view);

//        Bundle args = getArguments();
//        mat_img_guid = args.getString("mat_img_guid");

        btn_save  = view.findViewById(R.id.btn_save_takeawy_details);
        btn_save.setOnClickListener(this);


        return builder.create();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_takeawy_details:{
                Toast.makeText(getContext(), "todo \n Save", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
