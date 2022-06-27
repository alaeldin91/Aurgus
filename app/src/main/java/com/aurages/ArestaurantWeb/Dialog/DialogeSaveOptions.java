package com.aurages.ArestaurantWeb.Dialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Adapter.AdapterBill;
import com.aurages.ArestaurantWeb.Adapter.AdapterTemp;
import com.aurages.ArestaurantWeb.Adapter.PaymentAdapter;
import com.aurages.ArestaurantWeb.Model.ImageModel;
import com.aurages.ArestaurantWeb.Model.ImageModelBill;
import com.aurages.ArestaurantWeb.Model.InsertOrder;
import com.aurages.ArestaurantWeb.Model.InsertOrderDetails;
import com.aurages.ArestaurantWeb.Model.ModelBill;
import com.aurages.ArestaurantWeb.Model.Model_PaymentType;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.Model.inser_order_response;
import com.aurages.ArestaurantWeb.Model.orderDetails_response;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiClient;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiInterface;
import com.tapadoo.alerter.Alerter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DialogeSaveOptions extends Dialog {
    private static final String SHARED_PREF_NAME = "aurages_rest";
    public static Table_save tables;
    public static CardView card_eatIn, card_takeAway, card_delivery;
    public Activity activity;
    public static   String type;
    public static RecyclerView bill_rec;
    public static ArrayList<ModelBill> modelBills = new ArrayList<>();
    public static AdapterBill adapterBill;

    ImageView saveoption_backimg;
    private FragmentManager fragmentManager;
    public DialogeSaveOptions(@NonNull Activity a, FragmentManager fragmentManager, String type) {
        super(a);
        this.activity = a;
        this.fragmentManager = fragmentManager;
        this.type = type;
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.save_option);
        getWindow().setLayout(((getWidth(activity) / 100) * 90), ((getHeight(activity) / 100) * 90));
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        bill_rec=findViewById(R.id.recycler_bill);
        bill_rec.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
       // modelBills = databaseHelper.GETBill();
        adapterBill= new AdapterBill(modelBills,fillImage(),type,scanForActivity(getContext()));
        bill_rec.setAdapter(adapterBill);
        saveoption_backimg = findViewById(R.id.saveoption_backimg);
        saveoption_backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTemp();
                dismiss();
            }
        });
    }
    public void openTableDialog(String type) {
        tables = new Table_save(type);
        tables.show(fragmentManager
                , "Mat Details");
        tables.setCancelable(false);
        dismiss();



    }
    public void clearTemp() {
        new SqlLiteDataBase(getContext()).deleteAllTableData("Temp_orders");
        OrdersScreen.adapterTemp.notifyDataSetChanged();
        SqlLiteDataBase helper = new SqlLiteDataBase(getContext());
        OrdersScreen.temp_order = helper.GetAllTempOrder();
        OrdersScreen.EdtEndTotal.setText("");
        OrdersScreen.EdtTotal.setText("");
        OrdersScreen.adapterTemp = new AdapterTemp(getContext(), OrdersScreen.temp_order);
        OrdersScreen.TempOrder.setAdapter(OrdersScreen.adapterTemp);

    }
    public ArrayList<ImageModelBill>fillImage() {
        ArrayList<ImageModelBill> imageModelArrayList = new ArrayList<>();
        ImageModelBill imageModel0 = new ImageModelBill();
        imageModel0.setId(System.currentTimeMillis());
        imageModel0.setImagePath(R.drawable.ic_spoon);
        imageModelArrayList.add(imageModel0);
        ImageModelBill imageModel1 = new ImageModelBill();
        imageModel1.setId(System.currentTimeMillis());
        imageModel1.setImagePath(R.drawable.ic_food_delivery);
        imageModelArrayList.add(imageModel1);
        ImageModelBill imageModel2 = new ImageModelBill();
        imageModel2.setId(System.currentTimeMillis());
        imageModel2.setImagePath(R.drawable.ic_courier);
        imageModelArrayList.add(imageModel2);
        return  imageModelArrayList;
    }
}


