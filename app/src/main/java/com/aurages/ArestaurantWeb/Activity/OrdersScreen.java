package com.aurages.ArestaurantWeb.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;

import androidx.recyclerview.widget.RecyclerView;

import com.aurages.ArestaurantWeb.Adapter.AdapterGroups;
import com.aurages.ArestaurantWeb.Adapter.AdapterMats;
import com.aurages.ArestaurantWeb.Adapter.AdapterTemp;
import com.aurages.ArestaurantWeb.Adapter.Intrenetchange;
import com.aurages.ArestaurantWeb.Dialog.DialogTempeditdesin;
import com.aurages.ArestaurantWeb.Dialog.DialogeSaveOptions;
import com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions;
import com.aurages.ArestaurantWeb.Dialog.DialogeUpdateOrder;
import com.aurages.ArestaurantWeb.Dialog.OutSideOrder;
import com.aurages.ArestaurantWeb.Dialog.TabLayDialog;
import com.aurages.ArestaurantWeb.Dialog.Tables;
import com.aurages.ArestaurantWeb.Dialog.TablesGetBill;
import com.aurages.ArestaurantWeb.Dialog.printer;
import com.aurages.ArestaurantWeb.Model.BranchesModel;
import com.aurages.ArestaurantWeb.Model.InsertOrder;
import com.aurages.ArestaurantWeb.Model.InsertOrderDetails;
import com.aurages.ArestaurantWeb.Model.ModelBill;
import com.aurages.ArestaurantWeb.Model.ModelCatogry;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.Model.ModelTables;
import com.aurages.ArestaurantWeb.Model.Model_PaymentType;
import com.aurages.ArestaurantWeb.Model.Model_payment_spinner;
import com.aurages.ArestaurantWeb.Model.Model_table_spinner;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.Model.inser_order_response;
import com.aurages.ArestaurantWeb.Model.module_bill;
import com.aurages.ArestaurantWeb.Model.orderDetails_response;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiClient;
import com.aurages.ArestaurantWeb.apiService.retrofit.ApiInterface;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.tapadoo.alerter.Alerter;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.angmarch.views.SpinnerTextFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aurages.ArestaurantWeb.Aurages.getContext;

public class OrdersScreen extends AppCompatActivity implements View.OnClickListener {
    public static final String DATA_SAVED_BROADCAST = "com.aurages.datasaved";
    private BroadcastReceiver broadcastReceiver;
    private static final String SHARED_PREF_NAME = "aurages_rest";
    ArrayList<ModelBill> bil=new ArrayList<>();
    ArrayList<Model_PaymentType> paymentTypes=new ArrayList<>();
    ArrayList<ModelTables> table_no=new ArrayList<>();
    LinearLayout linear_table;
  TextView  invice_number;
    public static RecyclerView GridMats, GridGroups, TempOrder;
    public static ArrayList<ModelProduct> MatsList = new ArrayList<>();
    public static ArrayList<ModelCatogry> GroupsList = new ArrayList<>();
    public static ArrayList<Temp_Order> temp_order = new ArrayList<>();
    public static AdapterMats adaptermats;
    public static AdapterTemp adapterTemp;
    public static double total = 0;
    public static Tables tables;
    NiceSpinner table_name;
    NiceSpinner payment_type;
    public static ArrayList<WebOrders_model> order = new ArrayList<>();

    public static TextView EdtTotal, EdtTax, EdtEndTotal;
    NiceSpinner invoice_Kind;
    public static EditText discount;
     TextView waiter_name;
     TextView customer_name;
    public static Context context;
    public static TablesGetBill tablesGetBill;
    public static String last_view_guid = "", last_view_Billkind = "", last_view_tableCode = "";
    SharedPreferences prefs;
    public static LinearLayout layout_cont1;
    AdapterGroups adapterGroups;
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    CardView card_save2;
    ImageButton card_save, card_close, card_view;
    ImageView card_delete_temp, tempimg_printer, back;

    final String TAG = "";
    TextView txt_total;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.orderscdesign);
        discount = findViewById(R.id.EdtDiscount);
        tempimg_printer = findViewById(R.id.img_printer_temp);
        txt_total = findViewById(R.id.txt_total);
        layout_cont1 = findViewById(R.id.cont1);
        invoice_Kind = findViewById(R.id.nice_spinner_invoice_kind);
        payment_type = findViewById(R.id.nice_spinner_payment);
        table_name = findViewById(R.id.nice_spinner_table);
      waiter_name= findViewById(R.id.waiter_name);
      customer_name= findViewById(R.id.customer_name);
        linear_table=findViewById(R.id.linear_table);
        invice_number=findViewById(R.id.invice_number);

        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getApplicationContext());
        registerReceiver(new Intrenetchange(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
Log.i("success","send data");
                //loading the names again

            }
        };

        txt_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        GridMats = findViewById(R.id.GridMats);
        GridGroups = findViewById(R.id.GridGroups);
        TempOrder = findViewById(R.id.temp_order_rec);

        card_save = findViewById(R.id.orderdesign_save_cardview);
        // card_save2 = findViewById(R.id.orderdesign_save2_cardview);
        card_close = findViewById(R.id.orderdesign_close_cardview);
        card_view = findViewById(R.id.orderdesign_view_cardview);
        card_delete_temp = findViewById(R.id.card_delete_temp);
        tempimg_printer = findViewById(R.id.img_printer_temp);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        tempimg_printer.setOnClickListener(this);
        card_save.setOnClickListener(this);
        // card_save2.setOnClickListener(this);
        card_close.setOnClickListener(this);
        card_view.setOnClickListener(this);
        card_delete_temp.setOnClickListener(this);

        SharedPreferences sp = this.getSharedPreferences(SHARED_PREF_NAME, this.MODE_PRIVATE);
        String name = sp.getString("name", "");
        waiter_name.setText(name);



        String query = "SELECT * FROM WebOrders ORDER BY ID DESC  LIMIT 1";
        String OrderID = "";
        int ordernumber=1;


        try {
            SQLiteDatabase database = databaseHelper.getReadableDatabase();
            Cursor c = database.rawQuery(query, null);
            if (c != null) {
                while (c.moveToNext()) {

                    OrderID = c.getString(c.getColumnIndex("Guid"));
                    ordernumber =c.getInt(c.getColumnIndex("ID"));

                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
 invice_number.setText(ordernumber+"");
        bil= databaseHelper.GETBill();
        ArrayList<BranchesModel> branch_array = databaseHelper.GetBranch();
        for (int x=0 ;x<branch_array.size();x++) {
            String branch_id = branch_array.get(x).getId();
            SharedPreferences sps = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

            SharedPreferences.Editor edit = sps.edit();

            edit.putString("branch_ids", branch_id);
edit.apply();
        }
        List <module_bill>   bilmod= new ArrayList<>();
        for (int i = 0; i < bil.size(); i++) {
            bilmod.add(new module_bill(bil.get(i).getBillKindName()));
            SpinnerTextFormatter textFormatter = new SpinnerTextFormatter<module_bill>() {
                @Override
                public Spannable format(module_bill bil_model) {
                    return new SpannableString(bil_model.getBillKindName());
                }
            };

            invoice_Kind.setSpinnerTextFormatter(textFormatter);
            invoice_Kind.setSelectedTextFormatter(textFormatter);
            invoice_Kind.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
                @Override
                public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                    module_bill bil_Name = (module_bill) invoice_Kind.getSelectedItem();
                  String bill_id=  bil.get(position).getId();
                  int statement_id= bil.get(position).getBillKindNumber();

                  //  Toast.makeText(OrdersScreen.this, "Selected: " + bil_Name.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("alaeldin","id is"+bill_id);
                    SharedPreferences sps = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
                  if (position==0){
                      linear_table.setVisibility(View.VISIBLE);
                      List <Model_table_spinner>   tablesmod= new ArrayList<>();
                      table_no= databaseHelper.GetAllTables();
                      for (int i=0;i<table_no.size();i++){
                          tablesmod.add(new Model_table_spinner(table_no.get(i).getName()));
                          SpinnerTextFormatter textFormatter = new SpinnerTextFormatter<Model_table_spinner>() {
                              @Override
                              public Spannable format(Model_table_spinner tablemodel) {
                                  return new SpannableString(tablemodel.getName());
                              }
                          };
                          Log.i("ala",""+tablesmod);
                          table_name.setSpinnerTextFormatter(textFormatter);
                          table_name.setSelectedTextFormatter(textFormatter);
                          table_name.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
                              @Override
                              public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                                  Model_table_spinner modelTables = (Model_table_spinner) table_name.getSelectedItem();
                                  // Toast.makeText(OrdersScreen.this, "Selected: " + modelTables.toString(), Toast.LENGTH_SHORT).show();
                                  String tab_id= table_no.get(position).getId();
                                  String branch_id= table_no.get(position).getBranchId();
                                  SharedPreferences sps = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
                                  SharedPreferences.Editor edit = sps.edit();
                                  edit.putString("branch_ids",branch_id);
                                  edit.putString("tab_id",tab_id);
                                  edit.apply();
                                  Log.i("ala",tab_id);
                              }
                          });

                          table_name.attachDataSource(tablesmod);


                      }




                  }
                  else {
                      linear_table.setVisibility(View.INVISIBLE);
                      UUID bill_id_default = UUID.fromString("22222222-222-2222-2222-22222222222");
                      UUID default_uuid_zero = UUID.fromString("00000000-0000-0000-0000-000000000000");
                  String tab_id     = bill_id_default.toString();
                      SharedPreferences.Editor edit = sps.edit();
                      ArrayList<BranchesModel> branch_array = databaseHelper.GetBranch();
                      for (int i=0 ;i<branch_array.size();i++){
                          String branch_id= branch_array.get(i).getId();
                          edit.putString("branch_ids",branch_id);
                          edit.putString("tab_id",tab_id);
                          edit.apply();
                          Log.i("ala",tab_id);
                      }



                  }
                  SharedPreferences.Editor edit = sps.edit();
                    edit.putString("bill_ids",bill_id);
                    edit.putInt("statement_id",statement_id);
                    edit.apply();
                    Log.i("ala",bill_id);


                }
            });
            invoice_Kind.attachDataSource(bilmod);
        }
        paymentTypes= databaseHelper.GetAllPayment();
   List <Model_payment_spinner>   payment_mod_name= new ArrayList<>();
        for (int i = 0; i < paymentTypes.size(); i++) {
            payment_mod_name.add(new Model_payment_spinner(paymentTypes .get(i).getName()));
            SpinnerTextFormatter textFormatter = new SpinnerTextFormatter<Model_payment_spinner>() {
                @Override
                public Spannable format(Model_payment_spinner type_model) {
                    return new SpannableString(type_model.getName());
                }
            };

            payment_type.setSpinnerTextFormatter(textFormatter);
            payment_type.setSelectedTextFormatter(textFormatter);
            payment_type.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
                @Override
                public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                    Model_payment_spinner paymentType1 = (Model_payment_spinner) payment_type.getSelectedItem();
                    //    Toast.makeText(OrdersScreen.this, "Selected: " + paymentTypes.toString(), Toast.LENGTH_SHORT).show();
                    String pay_id=   paymentTypes.get(position).getId();
                    SharedPreferences sps = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sps.edit();
                    edit.putString("pay_id",pay_id);
                    edit.apply();
                    Log.i("ala",pay_id);
                }
            });
            payment_type.attachDataSource(payment_mod_name);
        }
        List <Model_table_spinner>   tablesmod= new ArrayList<>();
        table_no= databaseHelper.GetAllTables();
        for (int i=0;i<table_no.size();i++){
            tablesmod.add(new Model_table_spinner(table_no.get(i).getName()));
            SpinnerTextFormatter textFormatter = new SpinnerTextFormatter<Model_table_spinner>() {
                @Override
                public Spannable format(Model_table_spinner tablemodel) {
                    return new SpannableString(tablemodel.getName());
                }
            };
            Log.i("ala",""+tablesmod);
            table_name.setSpinnerTextFormatter(textFormatter);
            table_name.setSelectedTextFormatter(textFormatter);
            table_name.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
                @Override
                public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                    Model_table_spinner modelTables = (Model_table_spinner) table_name.getSelectedItem();
                   // Toast.makeText(OrdersScreen.this, "Selected: " + modelTables.toString(), Toast.LENGTH_SHORT).show();
                  String tab_id= table_no.get(position).getId();
                  String branch_id= table_no.get(position).getBranchId();
                    SharedPreferences sps = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sps.edit();
                    edit.putString("branch_ids",branch_id);
                    edit.putString("tab_id",tab_id);
                    edit.apply();
                    Log.i("ala",tab_id);
                }
            });

            table_name.attachDataSource(tablesmod);


        }




    Sprite doubleBounce = new DoubleBounce();


        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();


                SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getApplicationContext());




                //txt_qty.setText(String.valueOf(round(qty,1)));
                //txt_price.setText(String.valueOf(round(totalprice,1)))
                if (temp_order.get(position).getQty() > 1) {
                    if (databaseHelper.minQuyntatiy(temp_order.get(position).getMatCode(),temp_order.get(position).getQty(),temp_order.get(position).getPrice())) {
                        TextView txt_qty =viewHolder.itemView.findViewById(R.id.temp_qty);
                        TextView txt_price=viewHolder.itemView.findViewById(R.id.temp_price);
                       double quntaty=inc_dec(txt_qty.getText().toString(),0);
                        double totalprice = calcTotal(quntaty,temp_order.get(position).getPrice());
                        String quy=String.valueOf(round(quntaty,1));
                        String prices=String.valueOf(round(totalprice,1));
                        txt_qty.setText(quy);
                        txt_price.setText(prices);
                        adapterTemp.notifyItemChanged(position);
                        Log.i(TAG, "onSwiped: "+quy+prices);




                    }
                }
                else {

                }


            }
        };
        ItemTouchHelper itemTouchHelpers = new ItemTouchHelper(simpleCallback);
        itemTouchHelpers.attachToRecyclerView(TempOrder);
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
            new ItemTouchHelper.SimpleCallback(0 ,ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    int position = viewHolder.getAdapterPosition();

                    SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getApplicationContext());
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrdersScreen.this);
                    builder.setTitle("Delete Order");
                    builder.setMessage("are you sure delete this item");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    if (databaseHelper.deleteRowFromMyTemp(temp_order.get(position).getMatCode())) {
                                        //  total = total - temp_order.get(position).getQty() * temp_order.get(position).getPrice();
                                        float totals = (float) total;
                                        EdtTotal.setText(totals + "");
                                        EdtTotal.setText(totals + "");
                                        EdtTax.setText("");
                                        EdtEndTotal.setText("0" + " " + "SDG");
                                        temp_order.remove(position);
                                        adapterTemp.notifyDataSetChanged();
                                        Toast.makeText(OrdersScreen.this, "Deleted", Toast.LENGTH_SHORT).show();
                                        if (temp_order.size() == 0) {
                                            last_view_guid = "";
                                            //ShowSave2();
                                        }
                                    }

                                }
                            });
                    builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            adapterTemp.notifyDataSetChanged();

                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();


                }
            };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(TempOrder);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    }

    public void Close(View v) {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "Resume");
        OnresumeFun();
        //ShowSave2();
    }


    private void OnresumeFun(){
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(this);
        databaseHelper.deleteWebOrder();
        GroupsList = databaseHelper.GetAllGroups();
        temp_order = databaseHelper.GetAllTempOrder();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));

            }


        };







        int Mat_no = Integer.parseInt(prefs.getString("mat_element", "6"));
        int Group_no = Integer.parseInt(prefs.getString("group_element", "1"));
        GridMats.setLayoutManager(new GridLayoutManager(this,Mat_no,RecyclerView.VERTICAL,false));
        GridGroups.setLayoutManager(new GridLayoutManager(this, Group_no, RecyclerView.HORIZONTAL, false));

        TempOrder.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        //
        if (GroupsList.size() > 0) {

            MatsList = databaseHelper.GetAllMats(GroupsList.get(0).getId());
              adaptermats = new AdapterMats(this, MatsList);
            adapterGroups = new AdapterGroups(this, GroupsList);

            total = 0;
            adapterTemp=new AdapterTemp(this,temp_order);
            GridGroups.setAdapter(adapterGroups);
            GridMats.setAdapter(adaptermats);
            TempOrder.setAdapter(adapterTemp);
            //progressBar.setVisibility(View.GONE);
        } else {
            databaseHelper.InsertTo_Error("Data base is empty", "Data base no imported, you must Sync your data..");
            //progressBar.setVisibility(View.GONE);
        }


        EdtTotal = findViewById(R.id.EdtTotal);
        EdtTax = findViewById(R.id.EdtTax);
        EdtEndTotal = findViewById(R.id.EdtEndTotal);
    }


   /** private void ShowSave2(){
        if(!last_view_guid.equals("")){
            card_save2.setVisibility(View.VISIBLE);
            card_view.setEnabled(false);
            card_close.setEnabled(false);
            card_save.setEnabled(false);
        }else{
            card_save2.setVisibility(View.INVISIBLE);
            card_view.setEnabled(true);
            card_close.setEnabled(true);
            card_save.setEnabled(true);
        }
    }**/


    public void openTableDialogGetbill() {
        tablesGetBill = new TablesGetBill();
        tablesGetBill.show(getSupportFragmentManager()
                , "Get Bill");


        tablesGetBill.setCancelable(true);
    }

    public void GpsDialog(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(OrdersScreen.this);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;


        builder.setMessage(msg)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                new SqlLiteDataBase(OrdersScreen.this).deleteAllTableData("Temp_orders");
                                EdtTax.setText("");
                                EdtEndTotal.setText("0"+" "+"SDG");
                                total = 0;
                                EdtTotal.setText(total + "");
                                temp_order.clear();
                                adapterTemp.notifyDataSetChanged();
                                Toast.makeText(OrdersScreen.this, "Deleted", Toast.LENGTH_SHORT).show();
//                                card_save2.setVisibility(View.INVISIBLE);
                                last_view_guid="";
                                //ShowSave2();
                                layout_cont1.setVisibility(View.INVISIBLE);
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {

                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void openDialogTakeoff(FragmentManager fragmentManager) {

//        if (!com.sama.securitymodule.DBSecurityHelper.IsActivated(this.getApplicationContext())) {
//            Snackbar_msg("Version was Not Activated !",0);
//        }else{

        OutSideOrder outSideOrder = new OutSideOrder();
        //Bundle args=new Bundle();
        outSideOrder.show(fragmentManager
                , "Take off");
        outSideOrder.setCancelable(true);
        //}

    }


    public void GoCard(View view) {

    }

    private void OpenTempEditDialog() {
        DialogTempeditdesin dialogTempeditdesin = new DialogTempeditdesin(this);
        //Bundle args=new Bundle();

        dialogTempeditdesin.setCancelable(true);

        dialogTempeditdesin.show();

    }

    public void OpenSaveOption(String type) {
              DialogeSaveOptions saveOptions = new DialogeSaveOptions(this, getSupportFragmentManager(), type);
        //Bundle args=new Bundle();

        saveOptions.setCancelable(true);

        saveOptions.show();



        }
    public void OpenSaveOption_printer(String type) {
        DialogeSave_PrinterOptions saveOptions = new DialogeSave_PrinterOptions(this, getSupportFragmentManager(), type);
        //Bundle args=new Bundle();

        saveOptions.setCancelable(true);

        saveOptions.show();



    }







    private void Close_Dialog() {
        TabLayDialog tabLayDialog = new TabLayDialog(this, getSupportFragmentManager());
        //Bundle args=new Bundle();

        tabLayDialog.setCancelable(true);

        tabLayDialog.show();

    }
 //   printer s = new printer(OrdersScreen.this,getSupportFragmentManager());
    Runnable saveservers = new Runnable() {


        public void run() {
            try {
                   saveServer();
                System.out.println("" +"send data");



            } catch (Exception x) {
                System.out.println("Exception thrown.");
            }

        }


    };

    Runnable cleardata = new Runnable() {


        public void run() {
            try {
            clearTemp();
                System.out.println("" +"cleart data");



            } catch (Exception x) {
                System.out.println("Exception thrown.");
            }

        }


    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderdesign_save_cardview: {
               // findViewById(R.id.included1).setVisibility(View.VISIBLE);
                if ((new SqlLiteDataBase(this).GetAllTempOrder().size() > 0)) {
                     saveServer();
                    Alerter.create((OrdersScreen.this))
                            .setTitle("send order")
                            .setText("send order")
                            .setBackgroundColorRes(
                                    R.color.colorAccent)
                            .setDuration(2000).show();


                }






                else {
                    Alerter.create((OrdersScreen.this))
                            .setTitle("please choice your product")
                            .setText("no product")
                            .setBackgroundColorRes(
                                    R.color.orange)
                            .setDuration(2000).show();

                }

                break;
            }
            case R.id.orderdesign_save2_cardview: {
                if (new SqlLiteDataBase(getApplicationContext()).SaveOrder2(last_view_tableCode, last_view_Billkind, "0",last_view_guid,EdtEndTotal.getText().toString().trim())) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();


                    card_save2.setVisibility(View.INVISIBLE);
                    last_view_guid="";
                    last_view_Billkind="";
                    new SqlLiteDataBase(getApplicationContext()).deleteAllTableData("Temp_orders");
                    OnresumeFun();
                   // ShowSave2();
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    card_save2.setVisibility(View.VISIBLE);
                  //  ShowSave2();
                }
                break;
            }
            case R.id.orderdesign_close_cardview: {

                DialogeUpdateOrder orders = new DialogeUpdateOrder(this,getSupportFragmentManager());
                //Bundle args=new Bundle();

                orders.setCancelable(true);

                orders.show();


                break;
            }
            case R.id.orderdesign_view_cardview: {



                Intent intent = new Intent(getApplicationContext(), Tablayout.class);
                intent.putExtra("type", "view");
                startActivity(intent);
                break;
            }
            case R.id.card_delete_temp: {
                GpsDialog("Delete Order data ?");

                break;
            }
            case R.id.back:{
                Intent i = new Intent(OrdersScreen.this,DashboardActivity.class);
                startActivity(i);
                clearTemp();
                break;
            }
            case R.id.img_printer_temp:{
                if ((new SqlLiteDataBase(this).GetAllTempOrder().size() > 0)) {
printer s= new printer(this,getSupportFragmentManager());
s.printBill();
s.PrinterRecieptResturant();
                saveServer();
                    Alerter.create((OrdersScreen.this))
                            .setTitle("send order")
                            .setText("send order server")
                            .setBackgroundColorRes(
                                    R.color.colorAccent)
                            .setDuration(2000).show();



                }

                else {
                    Alerter.create((OrdersScreen.this))
                            .setTitle("please choice your product")
                            .setText("no product")
                            .setBackgroundColorRes(
                                    R.color.orange)
                            .setDuration(2000).show();

                }
                break;

            }
        }
    }


    private void CloseFun() {
        if (!(new SqlLiteDataBase(this).GetAllTempOrder().size() > 0)) {
            Intent intent = new Intent(getApplicationContext(), Tablayout.class);
            intent.putExtra("type", "close");
            startActivity(intent);
        } else {
            OpenSaveOption("close");
        }
    }




    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "Paused");
    }
    public void saveServer() {

        SharedPreferences sp = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences sps = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getApplicationContext());


        Date currentTime = Calendar.getInstance().getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
        String dates = dateFormat.format(currentTime);

        String billdate = dates + "  " + timeformat.format(currentTime);
        String deliverydate = dates + "  " + timeformat.format(currentTime);
  String branch_id= sp.getString("branch_ids","");
  String table_id=sp.getString("tab_id","");
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
        String payment_type = sp.getString("pay_id","");
        float tax = AdapterTemp.Setting_tax;
        int print_count = 2;
        String note = sp.getString("note", "");
        Toast.makeText(getApplicationContext(), "note" + note, Toast.LENGTH_LONG).show();
        String add_by_userid = sp.getString("user_id", "");
        String token = "Bearer " + sp.getString("token", "");
        String customer_id = "635a7e20-ce7f-11ea-9ab8-3353d47deab4";
        String bill_id = sps.getString("bill_ids","");
        int billlKindnum=sps.getInt("statement_id",0);
        String billstates=   Integer.toString(billlKindnum);
        SqlLiteDataBase helper = new SqlLiteDataBase(getContext());
        ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);


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
        int finalNumber2 = number;
        call.enqueue(new Callback<inser_order_response>() {
                @Override
                public void onResponse(Call<inser_order_response> call, Response<inser_order_response> response) {
                    if (response.isSuccessful()) {
                        orderDetails(response.body().getOrder());

                  databaseHelper.SaveOrder(table_id,bill_id,response.body().getOrder(),billstates,customer_id ,total,tax,payment_type,branch_id,print_count,0, billlKindnum);
                    } else {
                        Alerter.create(OrdersScreen.this)
                                .setTitle("not send order")
                                .setText("your order is not send please try again any time")

                                .setBackgroundColorRes(
                                        R.color.orange)
                                .setDuration(2000).show();
                        UUID GuidUi = UUID.randomUUID();
                        String Guid = GuidUi.toString();



                    }

                }


                @Override
                public void onFailure(Call<inser_order_response> call, Throwable t) {
                    UUID GuidUi = UUID.randomUUID();
                    String Guid = GuidUi.toString();
                    Log.i("table_id",table_id);
                    databaseHelper.SaveOrder(table_id,bill_id,Guid,billstates,customer_id ,total,tax,payment_type,branch_id,print_count,0, billlKindnum);



                }
            });
Log.i("tag",table_id);



        }



    public void orderDetails(String orders){




        SharedPreferences sp = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        Date currentTime = Calendar.getInstance().getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
        String dates = dateFormat.format(currentTime);
        String billdate = dates + "  " + timeformat.format(currentTime);
        String deliverydate = dates + "  " + timeformat.format(currentTime);

        String type_printer = sp.getString("selected_type_print", "");
        Log.i("ala", type_printer);

        String type_connect = sp.getString("selected_type_connect", "");
        Log.i("ala", type_connect);

        String note = sp.getString("note", "");

        String add_by_userid = sp.getString("user_id", "");
        String token = "Bearer " + sp.getString("token", "");

        SqlLiteDataBase helper = new SqlLiteDataBase(getApplicationContext());
        int printed = 2;
        int number = 0;
        for (int i = 0; i < OrdersScreen.temp_order.size(); i++) {
            int quantitys = OrdersScreen.temp_order.get(i).getQty();
            float prices = OrdersScreen.temp_order.get(i).getPrice();
            String product_num = OrdersScreen.temp_order.get(i).getMatCode();
            String products = helper.GetMatName(product_num);

            ApiInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
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
                    clearTemp();
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
    public void clearTemp() {
        new SqlLiteDataBase(getContext()).deleteAllTableData("Temp_orders");
        OrdersScreen.adapterTemp.notifyDataSetChanged();
        SqlLiteDataBase helper = new SqlLiteDataBase(getApplicationContext());
        OrdersScreen.temp_order = helper.GetAllTempOrder();
        OrdersScreen.EdtEndTotal.setText("");
        OrdersScreen.EdtTotal.setText("");
        OrdersScreen.EdtTax.setText("");
        OrdersScreen.adapterTemp = new AdapterTemp(getContext(), OrdersScreen.temp_order);
        OrdersScreen.TempOrder.setAdapter(OrdersScreen.adapterTemp);

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
    private double calcTotal(double qty, double price) {
        return qty*price;
    }
}



