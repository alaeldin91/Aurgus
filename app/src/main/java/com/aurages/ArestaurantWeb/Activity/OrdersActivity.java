package com.aurages.ArestaurantWeb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aurages.ArestaurantWeb.Adapter.AdapterGroups;
import com.aurages.ArestaurantWeb.Adapter.AdapterMats;
import com.aurages.ArestaurantWeb.Adapter.AdapterTemp;
import com.aurages.ArestaurantWeb.Dialog.Tables;
import com.aurages.ArestaurantWeb.Dialog.TablesGetBill;
import com.aurages.ArestaurantWeb.Model.Groups;
import com.aurages.ArestaurantWeb.Model.Mats;
import com.aurages.ArestaurantWeb.Model.ModelCatogry;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;

import java.util.ArrayList;

import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.GridMats;

public class OrdersActivity extends AppCompatActivity {
    RecyclerView ordersRecyclerView;
    RecyclerView foodRecyclerView;
    RecyclerView categoryRecyclerView;
    Button   doSaveButton;
    Button   doSearchButton;
    Button   doCancelButton;
    Button   doPrintButton;
    Button   doExitButton;
    Button   doEditButton;
    Button   doFlameButton;
    Button   doDeleteButton;
    TextView customerNameText;
    TextView waiterNameText;
    TextView tableNumberText;
    TextView timeWatchText;
    TextView orderTotalText;
    TextView orderDiscountText;
    TextView orderTaxText;
    TextView orderTotalPriceEndText;
    public static ArrayList<ModelProduct> foodList = new ArrayList<>();
    public static ArrayList<ModelCatogry> categoryList = new ArrayList<>();
    public static ArrayList<Temp_Order> ordersList = new ArrayList<>();
    public static AdapterMats foodAdapter;
    public static AdapterTemp orderAdapter;
    public static double total = 0;
    public static Tables tables;
    public static TablesGetBill tablesGetBill;
    public static String last_view_guid = "",last_view_Billkind="",last_view_tableCode="";
    SharedPreferences prefs;
    AdapterGroups categoryAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initiateViews();
        foodAdapter = new AdapterMats(getApplicationContext(),foodList);
        orderAdapter = new AdapterTemp(getApplicationContext(),ordersList);
        categoryAdapter = new AdapterGroups(getApplicationContext(),categoryList);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        managingRecyclerViews();

    }

    private void managingRecyclerViews() {
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        ordersRecyclerView.setAdapter(orderAdapter);
        foodRecyclerView.setAdapter(foodAdapter);
        categoryRecyclerView.setAdapter(categoryAdapter);


    }

    private void initiateViews() {
        ordersRecyclerView = findViewById(R.id.orderPageOrdersRecyclerView);
        foodRecyclerView = findViewById(R.id.orderPageFoodRecyclerView);
        categoryRecyclerView = findViewById(R.id.orderPageCategoryRecyclerView);
        doCancelButton = findViewById(R.id.orderPageCancelOrderButton);
        doDeleteButton  = findViewById(R.id.orderPageDeleteOrderButton);
        doSearchButton = findViewById(R.id.orderPageSearchOrderButton);
        doSaveButton = findViewById(R.id.orderPageSaveOrderButton);
        doEditButton = findViewById(R.id.orderPageEditOrderButton);
        doPrintButton = findViewById(R.id.orderPagePrintOrderButton);
        doFlameButton = findViewById(R.id.orderPageFlameOrderButton);
        doExitButton = findViewById(R.id.orderPageExitOrderButton);
        customerNameText = findViewById(R.id.orderPageCustomerNameText);
        waiterNameText = findViewById(R.id.orderPageWaiterNameText);
        tableNumberText = findViewById(R.id.orderPageTableNumberText);
        timeWatchText = findViewById(R.id.orderPageTimeWatchTextView);
        orderTotalText = findViewById(R.id.orderPageTotalPriceText);
        orderDiscountText = findViewById(R.id.orderPageOrdersDiscountText);
        orderTaxText = findViewById(R.id.orderPageTaxText);
        orderTotalPriceEndText = findViewById(R.id.orderPageTotalEndText);
    }

    void doSomething (){
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(this);
        categoryList = databaseHelper.GetAllGroups();
        ordersList = databaseHelper.GetAllTempOrder();
        int foodNumber = Integer.parseInt(prefs.getString("mat_element", "4"));
        int categoryNumber = Integer.parseInt(prefs.getString("group_element", "1"));

//
        if (categoryList.size() > 0) {
            foodList = databaseHelper.GetAllMats(categoryList.get(0).getId());

            total = 0;
//
            //progressBar.setVisibility(View.GONE);
        } else {
            databaseHelper.InsertTo_Error("Database is empty", "Data base no imported, you must Sync your data..");
            //progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        doSomething();
    }

    @Override
    protected void onStart() {
        super.onStart();
        doSomething();
    }
}
