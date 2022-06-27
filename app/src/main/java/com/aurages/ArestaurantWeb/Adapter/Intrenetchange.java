package com.aurages.ArestaurantWeb.Adapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Model.InsertOrder;
import com.aurages.ArestaurantWeb.Model.InsertOrderDetails;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.Model.WebOrderDetails_model;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aurages.ArestaurantWeb.Aurages.getContext;

public class Intrenetchange extends BroadcastReceiver {
    private Context context;
    private SqlLiteDataBase db;
    private static final String SHARED_PREF_NAME = "aurages_rest";


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        db = new SqlLiteDataBase(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType()
                    == ConnectivityManager.TYPE_MOBILE) {
                Cursor cursor = db.getUnsyncedNames();
                if (cursor.moveToFirst()) {
                    do {
                        saveOrder(
                                cursor.getInt(cursor.getColumnIndex("ID")),
                                cursor.getString(cursor.getColumnIndex("Guid"))
                                , cursor.getInt(cursor.getColumnIndex("Number")),
                                cursor.getInt(cursor.getColumnIndex("DailyNumber")),
                                cursor.getString(cursor.getColumnIndex("BillKind")),
                                cursor.getString(cursor.getColumnIndex("Date")),
                                cursor.getString(cursor.getColumnIndex("BillDate")),
                                cursor.getString(cursor.getColumnIndex("DeliveryDate")),
                                cursor.getString(cursor.getColumnIndex("BillState")),
                                cursor.getString(cursor.getColumnIndex("TableCode")),
                                cursor.getString(cursor.getColumnIndex("CustomCode")),
                                cursor.getFloat(cursor.getColumnIndex("Total")),
                                cursor.getFloat(cursor.getColumnIndex("Discount")),
                                cursor.getFloat(cursor.getColumnIndex("DiscountPercent")),
                                cursor.getFloat(cursor.getColumnIndex("Extra")),
                                cursor.getFloat(cursor.getColumnIndex("Tax")),
                                cursor.getString(cursor.getColumnIndex("Payment")),
                                cursor.getFloat(cursor.getColumnIndex("Service")),
                                cursor.getString(cursor.getColumnIndex("Notes")),
                                cursor.getString(cursor.getColumnIndex("UserCode")),
                                cursor.getInt(cursor.getColumnIndex("Printed")),
                                cursor.getFloat(cursor.getColumnIndex("Remain")),

                                cursor.getString(cursor.getColumnIndex("BranchCode")),
                                cursor.getInt(cursor.getColumnIndex("SaveOrder"))


                        );
                    } while (cursor.moveToNext());
                }
            }

        }

    }

    private void saveOrder(final int id, String Guid, int Number, int DailyNumber, String BillKind, String Date,
                           String BillDate,
                           String DeliveryDate, String BillState, String TableCode, String CustCode,
                         float Total, float Discount,
                           float DiscountPercent, float Extra, float Tax,
                         String Payment,
                           float Service, String Notes, String UserCode,
                           int Printed, float remain,String BranchCode, int saveOrder) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        String token = "Bearer " + sp.getString("token", "");
        String add_by_userid = sp.getString("user_id", "");

        ApiInterface apiInterface = ApiClient.getClient(context).create(ApiInterface.class);

        InsertOrder order = new InsertOrder(DailyNumber, Date, BillDate, DeliveryDate, BillState,
                Total, Discount,DiscountPercent, Extra, Tax, Service,
                "ana",Printed,remain
                , BranchCode, CustCode, TableCode, Payment,BillKind,UserCode);
Call<inser_order_response> call =apiInterface.addOrder(order,token);
call.enqueue(new Callback<inser_order_response>() {
    @Override
    public void onResponse(Call<inser_order_response> call, Response<inser_order_response> response) {
          if (response.isSuccessful()){
            db.UpdateLocal(id,response.body().getOrder(),1);



              SqlLiteDataBase helper = new SqlLiteDataBase(context);

            int Id=0;
            int quantity=0;
            float price=0;


              DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
              DateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
              Date currentTime = Calendar.getInstance().getTime();
              String products="";
              String product_num="";

              String dates = dateFormat.format(currentTime);
              for (int i = 0; i < OrdersScreen.temp_order.size(); i++) {
                  Id += OrdersScreen.temp_order.get(i).getId();
                  product_num += OrdersScreen.temp_order.get(i).getMatCode();
                  quantity +=OrdersScreen.temp_order.get(i).getQty();
                  price += OrdersScreen.temp_order.get(i).getPrice();

                  products += helper.GetMatName(product_num);



              }
              OrderDetails(Id,quantity,price, products, 1, dates, response.body().getOrder(), product_num);

              context.sendBroadcast(new Intent(AdapterTables.DATA_SAVED_BROADCAST));
              }




    }

    @Override
    public void onFailure(Call<inser_order_response> call, Throwable t) {

    }
});
    }


public void OrderDetails(int number,int Qty,float price,String matcode,
                         int printed,String delivaryDate,String orderId,String product_num){
    SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
    String token = "Bearer " + sp.getString("token", "");
    String add_by_userid = sp.getString("user_id", "");
    Date currentTime = Calendar.getInstance().getTime();

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
    String dates = dateFormat.format(currentTime);
    ApiInterface apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
    InsertOrderDetails orderDetails = new InsertOrderDetails(number,Qty,price,matcode,printed,dates,delivaryDate,orderId,product_num,add_by_userid);

    Call<orderDetails_response> calls = apiInterface.addOrderDetails(orderDetails, token);
calls.enqueue(new Callback<orderDetails_response>() {
    @Override
    public void onResponse(Call<orderDetails_response> call, Response<orderDetails_response> response) {
        SqlLiteDataBase helper = new SqlLiteDataBase(context);



        Log.i("aleldin", "insert data");
        Alerter.create((Activity) context)
                .setTitle("send order")
                .setText("send order server")
                .setBackgroundColorRes(
                        R.color.colorAccent)
                .setDuration(2000).show();
        helper.updateOrderStatus(1);

    }

    @Override
    public void onFailure(Call<orderDetails_response> call, Throwable t) {
        Alerter.create((Activity)context)
                .setTitle("send order")
                .setText("send order server")
                .setBackgroundColorRes(
                        R.color.colorAccent)
                .setDuration(2000).show();
    }
});

}



}
