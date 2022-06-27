package com.aurages.ArestaurantWeb.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Adapter.AdapterOptions;
import com.aurages.ArestaurantWeb.Adapter.AdapterTemp;
import com.aurages.ArestaurantWeb.Model.OrderOptions;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.temp_order;

public class TempEditDialog extends Dialog {
    public Activity activity;
    public static String temp_id = "";
    private ImageView del_card;
    public static EditText notetempedtdesign;
    private Temp_Order temp_order_DB = new Temp_Order();
    private RecyclerView order_option_recycler;
    private ArrayList<OrderOptions> orderOptions = new ArrayList<>();
    private AdapterOptions adapterOptions;


    private String matCode = "";

    private TextView txt_name,txt_qty,txt_price;
    ImageButton imageButton_pluse,imageButton_min,imageButton_cancel,imageButton_save;

    public TempEditDialog(@NonNull Activity a, String temp_id, String matCode) {
        super(a);
        this.activity = a;
        this.temp_id = temp_id;
        this.matCode = matCode;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.temp_edit_design);
        getWindow().setLayout(((getWidth(activity) / 100) * 100), ((getHeight(activity) / 100) * 110));
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        temp_order_DB = databaseHelper.GetRowTempOrder(temp_id);


        del_card = findViewById(R.id.del_card_design);
        notetempedtdesign = findViewById(R.id.notetempedtdesign);
        del_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notetempedtdesign.setText("");
            }
        });

        notetempedtdesign.setText(temp_order_DB.getNotes());

        order_option_recycler = findViewById(R.id.order_option_recycler);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        order_option_recycler.addItemDecoration(dividerItemDecoration);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int Note_no = Integer.parseInt(prefs.getString("note_element", "1"));

        order_option_recycler.setLayoutManager(new GridLayoutManager(getContext(), Note_no, RecyclerView.HORIZONTAL, false));
        orderOptions = databaseHelper.OrderOptions(temp_id);
        if(orderOptions.size()>0){
            adapterOptions = new AdapterOptions(getContext(), orderOptions);
            order_option_recycler.setAdapter(adapterOptions);
        }else{
            findViewById(R.id.rec_lay).setVisibility(View.INVISIBLE);
        }


        txt_name = findViewById(R.id.temp_edit_itemname);
        txt_qty = findViewById(R.id.temp_edit_qty);
        txt_price = findViewById(R.id.temp_edit_price);

        txt_name.setText(temp_order_DB.getName());
        txt_price.setText(String.valueOf(calcTotal(temp_order_DB.getQty(),temp_order_DB.getPrice())));
        txt_qty.setText(String.valueOf(round(temp_order_DB.getQty(),1)));

        imageButton_pluse = findViewById(R.id.temp_edit_plus1);
        imageButton_min = findViewById(R.id.min1);
        imageButton_cancel = findViewById(R.id.img_btn_cancel);
        imageButton_save = findViewById(R.id.img_btn_save);

        imageButton_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        imageButton_pluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //plus
                double qty = inc_dec(txt_qty.getText().toString().trim(),1);
                double totalprice = calcTotal(qty,temp_order_DB.getPrice());
                txt_qty.setText(String.valueOf(round(qty,1)));
                txt_price.setText(String.valueOf(round(totalprice,1)));
            }
        });
        imageButton_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double qty = inc_dec(txt_qty.getText().toString(),0);
                double totalprice = calcTotal(qty,temp_order_DB.getPrice());
                txt_qty.setText(String.valueOf(round(qty,1)));
                txt_price.setText(String.valueOf(round(totalprice,1)));
            }
        });
        imageButton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
                                if (databaseHelper.Update_Temp_Row(matCode, txt_qty.getText().toString().trim(), notetempedtdesign.getText().toString().trim())) {


                                    OrdersScreen.total = 0;
                                    OrdersScreen.EdtTotal.setText(OrdersScreen.total + "");

                                    OrdersScreen.temp_order = databaseHelper.GetAllTempOrder();
                                    OrdersScreen.adapterTemp = new AdapterTemp(getContext(), temp_order);
                                    OrdersScreen.TempOrder.setAdapter(OrdersScreen.adapterTemp);
                                    OrdersScreen.adapterTemp.notifyDataSetChanged();


                                } else {
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                }

                dismiss();
            }
        });

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.temp_edit_design, null);
        RadioEvent(view);

    }

    private double calcTotal(double qty, double price) {
        return qty*price;
    }


    private int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    private int getHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    public static int[] radioValue = new int[]{0,1,2};
    public static void RadioEvent(View view) {

        RadioGroup radioGroup;
        radioGroup = view.findViewById(R.id.temp_edit_radio2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public  void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radio_mins2:
                        int x = radioValue[0];
               break;

                    case R.id.radio_plus2:
                        int y =radioValue[1];
  break;

                    case R.id.radio_plus_plus2:
                       int z =radioValue[2];

break;
                }
            }
        });



    }
    public double inc_dec(String value, int type) {
        double res = 0;
        switch (type) {
            case 0: {
                if (Double.parseDouble(value) == 1) {
                    res = Double.parseDouble(value);
                } else {
                    res = Double.parseDouble(value) - 1;
                }
                break;
            }
            case 1: {
                res = Double.parseDouble(value) + 1;
                break;
            }
        }

        return res;

    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
