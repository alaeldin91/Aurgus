package com.aurages.ArestaurantWeb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions;
import com.aurages.ArestaurantWeb.Dialog.Payment;
import com.aurages.ArestaurantWeb.Dialog.ShowRecieptDialoge;
import com.aurages.ArestaurantWeb.Dialog.Tables;
import com.aurages.ArestaurantWeb.Model.ImageModel;
import com.aurages.ArestaurantWeb.Model.InsertOrder;
import com.aurages.ArestaurantWeb.Model.InsertOrderDetails;
import com.aurages.ArestaurantWeb.Model.ModelBill;
import com.aurages.ArestaurantWeb.Model.ModelTables;
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

import static com.aurages.ArestaurantWeb.Adapter.AdapterTables.NAME_NOT_SYNCED_WITH_SERVER;
import static com.aurages.ArestaurantWeb.Adapter.AdapterTables.NAME_SYNCED_WITH_SERVER;
import static com.aurages.ArestaurantWeb.Aurages.getContext;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private ArrayList<Model_PaymentType> paymentTypes;
    String type;
    ArrayList<Temp_Order> temp_orders;

    public static Tables tables;
    private static final String SHARED_PREF_NAME = "aurages_rest";

    SqlLiteDataBase helper;
    private LayoutInflater mInflater;
    private ArrayList<ImageModel> imageModels= new ArrayList<>();

    private Context context;
    private FragmentManager fragmentManager;
Payment payment;

    public PaymentAdapter(ArrayList<Model_PaymentType> paymentTypes,ArrayList<ImageModel> imageModels,String type, Context context,Payment payment) {
        this.mInflater = LayoutInflater.from(context);

        this.paymentTypes = paymentTypes;
        this.imageModels=imageModels;
        this.type = type;
        this.context = context;
        this.payment=payment;
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



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_payment, parent, false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
        final ImageModel model = imageModels.get(position);
      //  paymentTypes = databaseHelper.GetAllPayment();
        //holder.payment_image.setImageResource(model.getImagePath());
        //holder.payment_name.setText(paymentTypes.get(position).getName());
        SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        String bill_id= sps.getString("billId","");
        String billname=sps.getString("billName","");
        ArrayList<ModelBill>bills;
      //  bills=databaseHelper.GETBill();

        holder.payment_image.setOnClickListener(new View.OnClickListener() {
            SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);



            @Override
            public void onClick(View v) {

                    if (bill_id.equals("78f3e350-df9e-11ea-aca2-0fc22d0889ad")){
                        String paymentId = paymentTypes.get(position).getId();

                        SharedPreferences.Editor edit = sps.edit();
                        edit.putString("payment_id", paymentId);
                        edit.apply();
                        Log.i("ala", paymentId);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        DialogeSave_PrinterOptions printerOptions = new DialogeSave_PrinterOptions(scanForActivity(context), fragmentManager, type);
                        printerOptions.openTableDialog(type);
                      payment.dismiss();


                }
                    else {
                    switch (type) {
                        case "print": {
                           ShowRecieptDialoge recieptDialoge = new ShowRecieptDialoge((Activity) context, fragmentManager);
                            saveServer();
                           recieptDialoge.show();
                            String paymentId = paymentTypes.get(position).getId();
                            SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sps.edit();
                            edit.putString("payment_id", paymentId);
                            edit.apply();
                            payment.dismiss();

                            Log.i("ala", paymentId);

                            break;

                        }
                 /**       case "close": {
                            if (new SqlLiteDataBase(context).SaveOrder("0", 0, "0", OrdersScreen.EdtEndTotal.getText().toString().trim(), "0", "0", "1", 0, 1)) {
                                new SqlLiteDataBase(context).deleteAllTableData("Temp_orders");
                                OrdersScreen.adapterTemp.notifyDataSetChanged();
                                OrdersScreen.temp_order = databaseHelper.GetAllTempOrder();
                                OrdersScreen.adapterTemp = new AdapterTemp(context, OrdersScreen.temp_order);
                                OrdersScreen.TempOrder.setAdapter(OrdersScreen.adapterTemp);
                                OrdersScreen.EdtTotal.setText(OrdersScreen.total + "");

                                DialogeSave_PrinterOptions.tables.dismiss();

                            } else {
                                Toast.makeText(context, "Order Not Saved !", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }**/
                    }
                    }



                }



        });



    }
    public void saveServer() {

        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
        int position = sps.getInt("position", 0);
        Date currentTime = Calendar.getInstance().getTime();
        // String table_id = t.getId();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
        String dates = dateFormat.format(currentTime);
        temp_orders = new ArrayList<>();
        String billdate = dates + "  " + timeformat.format(currentTime);
        String deliverydate = dates + "  " + timeformat.format(currentTime);
        String billSatate = sp.getString("type", "0");
        float discount = AdapterTemp.fdiscount;
        float sum = (float) OrdersScreen.total;
        float total = sum - discount;
        float percentdiscount = discount * 100 / sum;
        float extra = 0;
        float service = 0;
        float remain = 0;
        String Name = sp.getString("selected_name_print", "");
        String type_printer = sp.getString("selected_type_print", "");
        Log.i("ala", type_printer);
        String branch_id = sp.getString("branch_id", "");

        String type_connect = sp.getString("selected_type_connect", "");
        Log.i("ala", type_connect);
        String payment_type = sp.getString("payment_id","");
        float tax = AdapterTemp.Setting_tax;
        int print_count = 2;
        String note = sp.getString("note", "");
        //int qty = sp.getInt("Qty", 0);
        //float price = sp.getFloat("price", 0);
        Toast.makeText(context, "note" + note, Toast.LENGTH_LONG).show();
        String add_by_userid = sp.getString("user_id", "");
        String token = "Bearer " + sp.getString("token", "");
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("branch_id", branch_id);

        UUID default_uuid_zero = UUID.fromString("00000000-0000-0000-0000-000000000000");

        String table_id = default_uuid_zero.toString();

        String customer_id = "635a7e20-ce7f-11ea-9ab8-3353d47deab4";
        UUID bill_id_default = UUID.fromString("22222222-222-2222-2222-22222222222");
        String bill_id = sps.getString("billId","");
        int billlKindnum=sps.getInt("billNum",0);
        String billstates=   Integer.toString(billlKindnum);
        SqlLiteDataBase helper = new SqlLiteDataBase(getContext());
        String desc = sp.getString("desc", "");
        int printed = 2;
        int daily = 1;

        ApiInterface apiInterface = ApiClient.getClient(context).create(ApiInterface.class);

        if (temp_orders !=null) {
            String query = "SELECT * FROM WebOrders ORDER BY ID DESC  LIMIT 1";
            String OrderID = "";
            int number =1;
            int number_order ;


            try {
                SQLiteDatabase database = helper.getReadableDatabase();
                Cursor c = database.rawQuery(query, null);
                if (c != null) {
                    while (c.moveToNext()) {

                        number = c.getInt(c.getColumnIndex("ID"));

                    }
                    c.close();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }


            InsertOrder order = new InsertOrder(number, dates, billdate, deliverydate,billstates , total, discount, percentdiscount, extra, tax, service, note, print_count, remain
                    , branch_id, customer_id, table_id, payment_type, bill_id, add_by_userid);
            Call<inser_order_response> call = apiInterface.addOrder(order, token);
            int finalNumber = number;
            int finalNumber1 = number;
            call.enqueue(new Callback<inser_order_response>() {
                @Override
                public void onResponse(Call<inser_order_response> call, Response<inser_order_response> response) {
                    if (response.isSuccessful()) {
                        orderDetails(response.body().getOrder());

                    /**    databaseHelper.SaveOrder(table_id, 0, billstates,
                                OrdersScreen.EdtEndTotal.getText().toString().trim(),payment_type ,branch_id,"1",NAME_SYNCED_WITH_SERVER, finalNumber);
**/
                    } else {
                        Alerter.create((Activity) context)
                                .setTitle("not send order")
                                .setText("your order is not send please try again any time")

                                .setBackgroundColorRes(
                                        R.color.orange)
                                .setDuration(2000).show();
                        UUID GuidUi = UUID.randomUUID();
                        String Guid = GuidUi.toString();

                      /**  databaseHelper.SaveOrder(table_id, 0, billSatate,
                                OrdersScreen.EdtEndTotal.getText().toString().trim(),payment_type,branch_id,"1", NAME_NOT_SYNCED_WITH_SERVER, finalNumber);

**/
                    }

                }

                @Override
                public void onFailure(Call<inser_order_response> call, Throwable t) {
                    UUID GuidUi = UUID.randomUUID();
                    String Guid = GuidUi.toString();
                  /**  databaseHelper.SaveOrder(table_id, 0, "0",
                            OrdersScreen.EdtEndTotal.getText().toString().trim(),payment_type,branch_id,"1", NAME_NOT_SYNCED_WITH_SERVER, finalNumber1);
**/

                }
            });


            //Call<inser_order_response> call = apiInterface.addOrder(order, token);
            //sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        }
        else {
            Toast.makeText(context,"please choose product",Toast.LENGTH_LONG).show();
        }
    }
    public void orderDetails(String orders){




        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        int position = sps.getInt("position", 0);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
        Date currentTime = Calendar.getInstance().getTime();
        UUID bill_id_default = UUID.fromString("22222222-222-2222-2222-22222222222");
        UUID default_uuid_zero = UUID.fromString("00000000-0000-0000-0000-000000000000");
        String bill_id = bill_id_default.toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
        String dates = dateFormat.format(currentTime);
        String billdate = dates + "  " + timeformat.format(currentTime);
        String deliverydate = dates + "  " + timeformat.format(currentTime);
        Toast.makeText(context, "deliverydate is" + deliverydate, Toast.LENGTH_LONG).show();
        String billSatate = sp.getString("type", "0");
        float discount = AdapterTemp.fdiscount;
        float sum = (float) OrdersScreen.total;
        float total = sum - discount;
        float percentdiscount = discount * 100 / sum;
        float extra = 0;
        float service = 0;

        float remain = 0;
        String Name = sp.getString("selected_name_print", "");
        String type_printer = sp.getString("selected_type_print", "");
        Log.i("ala", type_printer);

        String type_connect = sp.getString("selected_type_connect", "");
        Log.i("ala", type_connect);
        String payment_type = "635a7e20-ce7f-11ea-9ab8-3353d47deab4";
        float tax = AdapterTemp.Setting_tax;
        int print_count = 2;
        String note = sp.getString("note", "");
        //int qty = sp.getInt("Qty", 0);
        //float price = sp.getFloat("price", 0);
        Toast.makeText(context, "note" + note, Toast.LENGTH_LONG).show();
        String add_by_userid = sp.getString("user_id", "");
        String token = "Bearer " + sp.getString("token", "");
        String customer_id = "635a7e20-ce7f-11ea-9ab8-3353d47deab4";

        SqlLiteDataBase helper = new SqlLiteDataBase(getContext());
        String desc = sp.getString("desc", "");
        int printed = 2;
        String product_id = sp.getString("product_id", "");
        int dailynumber = 0;

        //String OrderId = helper.GetLastInsertedOrder();
        //   Log.i("tag",OrderId);
        int number = 0;

        // String id = sps.getString("createorder", "");
        for (int i = 0; i < OrdersScreen.temp_order.size(); i++) {
            int quantitys = OrdersScreen.temp_order.get(i).getQty();
            float prices = OrdersScreen.temp_order.get(i).getPrice();
            String product_num = OrdersScreen.temp_order.get(i).getMatCode();
            String products = helper.GetMatName(product_num);

            ApiInterface apiInterface = ApiClient.getClient(getContext()).create(ApiInterface.class);
            if (quantitys != 0 && prices != 0 && product_num != null) {
                String query = "SELECT * FROM WebOrders ORDER BY ID DESC  LIMIT 1";
                String OrderID = "";
                int ordernumber=1;


                try {
                    SQLiteDatabase database = helper.getReadableDatabase();
                    Cursor c = database.rawQuery(query, null);
                    if (c != null) {
                        while (c.moveToNext()) {

                            OrderID = c.getString(c.getColumnIndex("Guid"));
                            number =c.getInt(c.getColumnIndex("ID"));

                        }
                        c.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }


                InsertOrderDetails orderDetails = new InsertOrderDetails(number,quantitys, prices, products, printed, dates, billdate, orders, product_num, add_by_userid);
                Call<orderDetails_response> calls = apiInterface.addOrderDetails(orderDetails, token);

                calls.enqueue(new Callback<orderDetails_response>() {
                    @Override
                    public void onResponse(Call<orderDetails_response> call, Response<orderDetails_response> response) {
                        if (response.isSuccessful()) {

                            Log.i("aleldin", "insert data");
                            Log.i("tag", "response is" + response.body().getOrderId());
                        }
                    }

                    @Override
                    public void onFailure(Call<orderDetails_response> call, Throwable t) {
                        Log.i("not send"
                                , t.getMessage());





                    }
                });


            }
            else {

            }
        }
    }


    public Model_PaymentType getItem(int id) {
        return paymentTypes.get(id);
    }

    @Override
    public int getItemCount() {
        return paymentTypes.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView payment_name;
        ImageView payment_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            payment_name= itemView.findViewById(R.id.cash);
            payment_image=itemView.findViewById(R.id.cash_image);


        }


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

}
