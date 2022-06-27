package com.aurages.ArestaurantWeb.Adapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Activity.SyncActivity;
import com.aurages.ArestaurantWeb.Dialog.DialogeSaveOptions;
import com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions;
import com.aurages.ArestaurantWeb.Dialog.ShowRecieptDialoge;
import com.aurages.ArestaurantWeb.Dialog.Tables;
import com.aurages.ArestaurantWeb.Model.InsertOrder;
import com.aurages.ArestaurantWeb.Model.InsertOrderDetails;
import com.aurages.ArestaurantWeb.Model.ModelTables;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.Model.WebOrderDetails_model;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.Model.inser_order_response;
import com.aurages.ArestaurantWeb.Model.orderDetails_response;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiClient;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiInterface;
import com.tapadoo.alerter.Alerter;

import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aurages.ArestaurantWeb.Aurages.getContext;

public class AdapterTables extends RecyclerView.Adapter<AdapterTables.ViewHolder> {
    private static final String SHARED_PREF_NAME = "aurages_rest";
    private static OutputStream outputStream;
    private static Socket btsocket;
    Tables table;
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final String DATA_SAVED_BROADCAST = "com.aurages.ArestaurantWeb.datasaved";

    byte FONT_TYPE;

    String type;
    private BroadcastReceiver broadcastReceiver;

    private ArrayList<ModelTables> tableModels;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    ArrayList<WebOrders_model> orders;
    ArrayList<Temp_Order> temp_orders;
    ArrayList<WebOrderDetails_model>ordersDetails;
    private FragmentManager fragmentManager;





    public AdapterTables(Context context, ArrayList<ModelTables> e, String type,Tables tables) {
        this.mInflater = LayoutInflater.from(context);
        this.tableModels = e;
        this.context = context;
        this.type = type;
        this.table=tables;
    }








    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.table_place, parent, false);




        return new ViewHolder(view);
    }





    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        orders= new ArrayList<>();
        ModelTables t = tableModels.get(position);
        SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sps.edit();
        edit.putInt("position",position);

    /*    holder.e_no.setText(error_log.getError_no());
        holder.e_txt.setText(error_log.getError_txt());*/

        holder.tablep_name.setText(t.getName());

        //holder.t_place_lay.setBackgroundColor(Color.argb(255, t.getColorR(), t.getColorG(), t.getColorB()));
        //holder.t_place_card.setCardBackgroundColor(Color.argb(255, t.getColorR(), t.getColorG(), t.getColorB()));


        holder.t_place_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);

                switch (type) {
                    case "print": {
                        saveServer();
                        ShowRecieptDialoge recieptDialoge= new ShowRecieptDialoge((Activity) context, fragmentManager);
                        table.dismiss();
                        recieptDialoge.show();
                     //   Tables tables= new Tables(type);
                      //  tables.dismiss();

                             break;

                    }



                   /** case "close": {
                        if (new SqlLiteDataBase(context).SaveOrder(t.getId(),0 , "0", OrdersScreen.EdtEndTotal.getText().toString().trim(),"0",t.getBranchId(),"1",0,1)) {
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


//                }


            }
        });


    }


    public void saveServer() {

            SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
            SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
            SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
            int position = sps.getInt("position", 0);
            ModelTables t = tableModels.get(position);
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
            String branch_id = t.getBranchId();
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("branch_id", branch_id);

            String customer_id = "635a7e20-ce7f-11ea-9ab8-3353d47deab4";
            String table_id = t.getId();
            UUID bill_id_default = UUID.fromString("22222222-222-2222-2222-22222222222");
            UUID default_uuid_zero = UUID.fromString("00000000-0000-0000-0000-000000000000");
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

                       /**     databaseHelper.SaveOrder(t.getId(), 0, "0",
                                    OrdersScreen.EdtEndTotal.getText().toString().trim(),payment_type ,t.getBranchId(),"1",NAME_SYNCED_WITH_SERVER, finalNumber);

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

                        /**    databaseHelper.SaveOrder(t.getId(), 0, "0",
                                    OrdersScreen.EdtEndTotal.getText().toString().trim(),payment_type,t.getBranchId(),"1", NAME_NOT_SYNCED_WITH_SERVER, finalNumber);

**/
                        }

                    }

                    @Override
                    public void onFailure(Call<inser_order_response> call, Throwable t) {
                        UUID GuidUi = UUID.randomUUID();
                        String Guid = GuidUi.toString();
                   /**     databaseHelper.SaveOrder(table_id, 0, "0",
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




   /** public  void  ordersend() {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);
        int position = sps.getInt("position", 0);
        ModelTables t = tableModels.get(position);

        Intrenetchange broadcast = new Intrenetchange();
        if (URL.isNetworkConnected(getContext())) {
            if (!URL.isInternetAvailable()) {


                    OrdersScreen.adapterTemp.notifyDataSetChanged();
                    OrdersScreen.temp_order = databaseHelper.GetAllTempOrder();
                    OrdersScreen.adapterTemp = new AdapterTemp(context, OrdersScreen.temp_order);
                    OrdersScreen.TempOrder.setAdapter(OrdersScreen.adapterTemp);
                    OrdersScreen.EdtTotal.setText(OrdersScreen.total + "");
                    Alerter.create((Activity) context)
                            .setTitle("save order")
                            .setText("save order in local database")
                            .setBackgroundColorRes(
                                    R.color.orange)
                            .setDuration(2000).show();
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
                    String branch_id = t.getBranchId();
                    String customer_id = "635a7e20-ce7f-11ea-9ab8-3353d47deab4";
                    String table_id = t.getId();
                    SqlLiteDataBase helper = new SqlLiteDataBase(getContext());
                    String desc = sp.getString("desc", "");
                    int printed = 2;


                    String product_id = sp.getString("product_id", "");
                    int dailynumber = 0;

                    if (product_id.equals("")) {

                        Toast.makeText(getContext(), "your not save and send", Toast.LENGTH_LONG).show();

                    } else {
                        InsertOrder order = new InsertOrder(dailynumber, dates, billdate, deliverydate, "0", total, discount, percentdiscount, extra, tax, service, note, print_count, remain
                                , branch_id, customer_id, table_id, payment_type, bill_id, add_by_userid);
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


                        Call<inser_order_response> call = apiInterface.addOrder(order, token);
                        sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

                        SharedPreferences.Editor edit = sps.edit();

                        call.enqueue(new Callback<inser_order_response>() {
                            @Override
                            public void onResponse(Call<inser_order_response> call, Response<inser_order_response> response) {
                                if (response.isSuccessful()) {
                                    inser_order_response createorder = response.body();
                                    String orders = createorder.getCreateorder();


                                    // Gson gson =new Gson();
                                    Log.i("musa", "order id is" + orders);

                                    edit.putString("createorder", orders);
                                    edit.apply();
                                    Alerter.create((Activity) context)
                                            .setTitle("send order")
                                            .setText("send order server")


                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();

                                    databaseHelper.SaveOrder(orders,t.getNumber() + "", 0, "0",
                                            OrdersScreen.EdtEndTotal.getText().toString().trim());



                                } else {
                                    Alerter.create((Activity) context)
                                            .setTitle("failed send order")
                                            .setText("failed send order to server")


                                            .setBackgroundColorRes(
                                                    R.color.orange)
                                            .setDuration(5000).show();
                                }


                            }


                            @Override
                            public void onFailure(Call<inser_order_response> call, Throwable t) {
                                Log.i("alaeldin", t.getMessage().toString());
                            }
                        });

                        }

                } else {
                    //network error
                }


            } else {
                //no internet
            }


            String type = sp.getString("selected_type_print", "");
            String connect = sp.getString("selected_type_connect", "");
            if (type.equals("thermalprinter") && connect.equals("NETWORK_CONNECT")) {
                //  printBill();
                printer s = new printer((Activity) context, fragmentManager);
                s.printBill();
            } else {
                Log.i("tags", "is not thermal and not network");
            }


            // int dailynumber = sp.getInt("temp_no", 0);
            //   int number = position + 1;
            // String bill_id = "635a7e20-ce7f-11ea-9ab8-3353d47deab4";

        }
**/
    public void orderDetails(String orders){




            SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
            SharedPreferences sps = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
            int position = sps.getInt("position", 0);
            ModelTables t = tableModels.get(position);
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
            String branch_id = t.getBranchId();
            String customer_id = "635a7e20-ce7f-11ea-9ab8-3353d47deab4";
            String table_id = t.getId();
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


        @Override
    public int getItemCount() {
        return tableModels.size();
    }

    //    // convenience method for getting data at click position
    public ModelTables getItem(int id) {
        return tableModels.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tablep_name;
        ImageView tablep_image;
        LinearLayout t_place_lay;
        CardView t_place_card;

        ViewHolder(View itemView) {
            super(itemView);
            tablep_name = itemView.findViewById(R.id.table_name);
            tablep_image = itemView.findViewById(R.id.table_image);
            t_place_lay = itemView.findViewById(R.id.t_place_lay);
            t_place_card = itemView.findViewById(R.id.t_place_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    private void saveOrder(int Number, int DailyNumber, String BillKind, String Date,
                           String BillDate,
                           String DeliveryDate, String BillState, String TableCode, String CustCode,
                           String CustName, int PayType, float Total, float Discount,
                           float DiscountPercent, float Extra, float Tax,
                           float Remain, String Payment,
                           float Service, String Notes, String UserCode,
                           int Printed, String BranchCode, int saveOrder) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        String token = "Bearer " + sp.getString("token", "");
        String add_by_userid = sp.getString("user_id", "");
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(context);


        ApiInterface apiInterface = ApiClient.getClient(context).create(ApiInterface.class);

        InsertOrder order = new InsertOrder(DailyNumber, Date, BillDate, DeliveryDate, BillState,
                Total, Discount,DiscountPercent, Extra, Tax, Service,
                Notes,Printed,Remain
                , BranchCode, CustCode, TableCode, Payment,BillKind, add_by_userid);
        Call<inser_order_response> call =apiInterface.addOrder(order,token);
        call.enqueue(new Callback<inser_order_response>() {
            @Override
            public void onResponse(Call<inser_order_response> call, Response<inser_order_response> response) {
                if (response.isSuccessful()){
                   databaseHelper .updateOrderStatus(saveOrder);






                }
            }

            @Override
            public void onFailure(Call<inser_order_response> call, Throwable t) {

            }
        });
    }
}
