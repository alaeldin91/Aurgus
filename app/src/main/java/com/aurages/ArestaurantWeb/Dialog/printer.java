package com.aurages.ArestaurantWeb.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.aurages.ArestaurantWeb.Activity.OrdersScreen;
import com.aurages.ArestaurantWeb.Adapter.AdapterTemp;
import com.aurages.ArestaurantWeb.Model.ModelPPrinter;
import com.aurages.ArestaurantWeb.Model.PrinterCommands;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.R;
import com.aurages.ArestaurantWeb.Utils.SqlLiteDataBase;
import com.aurages.ArestaurantWeb.Utils.Utils;
import com.aurages.ArestaurantWeb.wagu.Block;
import com.aurages.ArestaurantWeb.wagu.Board;
import com.aurages.ArestaurantWeb.wagu.Table;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;


import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.total;
import static com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions.getHeight;
import static com.aurages.ArestaurantWeb.Dialog.DialogeSave_PrinterOptions.getWidth;

public class printer extends Dialog implements View.OnClickListener {
    private static final String SHARED_PREF_NAME = "aurages_rest";

    public static ArrayList<Temp_Order> temp_order = new ArrayList<>();
    public static ArrayList<ModelPPrinter> listPrinter = new ArrayList<>();
    private static Handler handler = new Handler();
    private static OutputStream outputStream;
    private static Socket btsocket;




    public ImageView logo;

    Activity activity;
    FragmentManager fragmentManager;


    Button btn;
    private boolean run_thread = true;
    private ArrayList<String> Name = new ArrayList();
    private ArrayList<String> Ip = new ArrayList();
    private ArrayList<String> printer = new ArrayList();
    private Temp_Order temp_order_DB = new Temp_Order();
    private ConnectivityManager connectivityManager;

    public printer(@NonNull Activity a, FragmentManager supportFragmentManager) {
        super(a);
        this.activity = a;

        this.fragmentManager = fragmentManager;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printer);
        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        getWindow().setLayout(((getWidth(activity) / 100) * 110), ((getHeight(activity) / 100) * 200));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getContext().getSharedPreferences(SHARED_PREF_NAME, getContext().MODE_PRIVATE);
                String types = sp.getString("selected_type_print", "");
                String connects = sp.getString("selected_type_connect", "");
                if (types.equals("thermalprinter") && connects.equals("NETWORK_CONNECT")) {


                }


            }
        });
    }

public String printPhoto(String encodedString) throws UnsupportedEncodingException {
    byte[] data = Base64.decode(encodedString, Base64.DEFAULT);
    return new String(data, "UTF-8");
}
    private void printText(byte[] msg ) {
        try {
            // Print normal text
            outputStream.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String leftRightAlign(String str1, String str2) {
        String ans = str1 + str2;
        if (ans.length() < 31) {
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }
    public void printUnicode(){
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void printCustom(String msg, int size, int align) {


        //Print config "mode"
        byte[] cc = new byte[]{0x1B, 0x21, 0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B, 0x21, 0x08};  // 1- only bold text
        byte[] ALLINEA_CT = {0x1B, 0x61, 0x01};
        byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B, 0x21, 0x10}; // 3- bold with large text
        try {
            switch (size) {
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);

                    break;
            }

            switch (align) {
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            byte[] CodePageArabic = new byte[] { 0x1B, 0x74, 0x25 };
            byte[] CodePageEnglish = new byte[] { 0x1B, 0x74, 0x00 };
            outputStream.write(Utils.UNICODE_TEXT);
            outputStream.write(msg.getBytes(Charset.forName("ISO-8859-6")));
            outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime[] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        return dateTime;
    }



       public void photoPrint(String encodedString) {
           Paint paint = new Paint();
           paint.setColor(Color.BLACK);
           paint.setStrokeWidth(12);
           Rect bounds = new Rect();
           paint.getTextBounds(encodedString, 0, encodedString.length(), bounds);
           Bitmap bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888);
           Canvas canvas = new Canvas(bitmap);
           canvas.drawText(encodedString, 0, 0, paint);
           byte[] command = Utils.decodeBitmap(bitmap);
           try {
               outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
           } catch (IOException e) {
               e.printStackTrace();
           }
           printText(command);


       }

    public void printBill() {
        int id = 1;
        byte ESC = 0x1B;
        String product_id = "";

        byte[] CutPaper = {ESC, 0x69};
        SharedPreferences sp = getContext().getSharedPreferences(SHARED_PREF_NAME, getContext().MODE_PRIVATE);
        SqlLiteDataBase helper = new SqlLiteDataBase(getContext());
        SharedPreferences.Editor edit = sp.edit();
        List<String> t2Headers = Arrays.asList("product", "qty", "price", "total");
        List<String> showrow = new ArrayList<>();
        ArrayList<Temp_Order> temp_orders = helper.GetAllTempOrder();
        edit.putString("temp_orders", "");
        edit.apply();

        Temp_Order tempOrder = null;
        int quantuty;
        int quantutys;
        float price;

        List<List<String>> t2Rows = new ArrayList<>();



        SqlLiteDataBase databaseHelper = new SqlLiteDataBase(getContext());
        temp_order = databaseHelper.GetAllTempOrder();
        int sum = 0;
        float discount = AdapterTemp.fdiscount;

        float total = sum - discount;
        //SqlLiteDataBase helper= new SqlLiteDataBase(getContext());
        // String nameproduct= helper.GetMatName(product_id);
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            try {


                try {

                    // String ipconnect=ip.getText().toString() ;


                    String ips = sp.getString("ip", "");

                    Log.i("tag", "" + "" + ips + "");
                    btsocket = new Socket("" + ips + "", 9100);
                    //    Log.i("tag",'"'+ips+'"');

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        if (btsocket == null) {
            Log.i("ala", "your socket is null");
        } else {
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                //Drawable logo = getContext().getResources().getDrawable(R.drawable.imgs);
                // printPhoto(logo);
                int number = 1;
                int number_order = 1;

                String query = "SELECT * FROM WebOrders ORDER BY ID DESC  LIMIT 1";

                try {
                    SQLiteDatabase database = helper.getReadableDatabase();
                    Cursor c = database.rawQuery(query, null);
                    if (c != null) {
                        while (c.moveToNext()) {


                            number = c.getInt(c.getColumnIndex("ID"));

                        }
                        c.close();
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            //printCustom(amt + "", 0, 0);


            }





        temp_order = databaseHelper.GetAllTempOrder();
         sum = 0;
        String products="";
         String output="";

        for (int i = 0; i < temp_order.size(); i++) {
            product_id = temp_order.get(i).getMatCode();

            products = databaseHelper.GetMatName(product_id);

                float sub_total;
                quantuty = temp_order.get(i).getQty();
                price = temp_order.get(i).getPrice();
                sum = temp_order.get(i).getId();

                sub_total = quantuty * price;

                showrow =
                        Arrays.asList(
                                (products),
                                String.valueOf(temp_order.get(i).getQty()),
                                String.valueOf(temp_order.get(i).getPrice()),
                                String.valueOf(sub_total));
                t2Rows.add(showrow);



        }


        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER);
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
        String dates = dateFormat.format(currentTime);
        String time = timeformat.format(currentTime);
        int number = 1;


        String query = "SELECT * FROM WebOrders ORDER BY ID DESC  LIMIT 1";
try {
    try {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {


                number = c.getInt(c.getColumnIndex("ID"));

            }
            c.close();
        }
        String branch_id = sp.getString("branch_id", "");
        String username = sp.getString("name", "");
        String branch_name = databaseHelper.GetBranchName(branch_id);
        String company = ""
                + "     " + "Resturant:" + branch_name + "\n"
                + "Invoice Number :" + number + " " + "user:" + username + "\n" +
                "date:" + dates + " " + "time:" + time + "\n"
                + "\n";
        discount = AdapterTemp.fdiscount;
        float sumtotal = (float) OrdersScreen.total;
        total = sumtotal - discount;
        float percentdiscount = discount * 100 / sum;
        String summary = ""
                + "total\n"
                + "disount\n"
                + "tax\n";
        String summaryVal = ""
                + total + "\n"
                + +discount + "\n"
                + AdapterTemp.Setting_tax + "\n";
        String advertise = " https://aurages.com\n";

        List<Integer> t2ColWidths = Arrays.asList(14, 6, 7, 8);
        Board b = new Board(48);
        b.setInitialBlock(new Block(b, 35, 3, company).allowGrid(false).setBlockAlign(Block.BLOCK_CENTRE));
        b.appendTableTo(0, Board.APPEND_BELOW, new Table(b, 35, t2Headers, t2Rows, t2ColWidths).setColAlignsList(colAlignList));
        Block summaryBlock = new Block(b, 15, 4, summary).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_LEFT);
        b.getBlock(5).setBelowBlock(summaryBlock);
        Block summaryValBlock = new Block(b, 15, 4, summaryVal).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_LEFT);
        summaryBlock.setRightBlock(summaryValBlock);

        summaryBlock.setBelowBlock(new Block(b, 33, 3, advertise).setDataAlign(Block.DATA_MIDDLE_LEFT).allowGrid(false));
        System.out.println(b.invalidate().build().getPreview());
//        outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
       // String output = "";

        //for (int i = b.invalidate().build().getPreview().length() - 1; i >= 0; i--) {
          //  output = output + b.invalidate().build().getPreview().charAt(i);
        //}

        printCustom(b.invalidate().build().getPreview(),1,1);
       outputStream.write(CutPaper, 0, CutPaper.length);
        outputStream.flush();
        outputStream.close();


    } catch (Exception e) {
        e.printStackTrace();
    }

}
catch (WindowManager.BadTokenException ex) {
    ex.printStackTrace();
}


    }


    public void PrinterRecieptResturant() {
        List<String> showrow = new ArrayList<>();
        int id = 1;
        List<List<String>> t2Rows = new ArrayList<>();
        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER);
        List<String> t2Headers = Arrays.asList("Item", "Qty", "Price", "total");

        byte ESC = 0x1B;
        byte[] CutPaper = {ESC, 0x69};
        SharedPreferences sp = getContext().getSharedPreferences(SHARED_PREF_NAME, getContext().MODE_PRIVATE);
        SqlLiteDataBase helper = new SqlLiteDataBase(getContext());
        SharedPreferences.Editor edit = sp.edit();

        ArrayList<Temp_Order> temp_order = helper.GetAllTempOrder();
        edit.putString("temp_orders", "");
        edit.apply();
        float discount = AdapterTemp.fdiscount;
        float sum = (float) total;
        float total = sum - discount;
        float percentdiscount = discount * 100 / sum;
        //SqlLiteDataBase helper= new SqlLiteDataBase(getContext());
        // String nameproduct= helper.GetMatName(product_id);
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        for (int i = 0; i < temp_order.size(); i++) {
            String product_id = temp_order.get(i).getMatCode();

            ArrayList<ModelPPrinter> printer = helper.PrintToProduct(product_id);
            for (int x = 0; x < printer.size(); x++) {
                String printer_name = printer.get(x).getName();
                //String address= printer.get(x).getPrinter();
                try {
                    btsocket = new Socket("" + printer_name + "", 9100);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {


                } catch (NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }

                if (btsocket == null) {
                    Log.i("ala", "your socket is null");
                } else {
                    OutputStream opstream = null;
                    try {
                        opstream = btsocket.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    outputStream = opstream;

                    //print command
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.i("tag", e.toString());
                    }

                    String username = sp.getString("name", "");


                    //Drawable logo = getContext().getResources().getDrawable(R.drawable.imgs);
                    // printPhoto(logo);
                    int number = 1;
                    int number_order = 1;

                    String query = "SELECT * FROM WebOrders ORDER BY ID DESC  LIMIT 1";

                    try {
                        SQLiteDatabase database = helper.getReadableDatabase();
                        Cursor c = database.rawQuery(query, null);
                        if (c != null) {
                            while (c.moveToNext()) {


                                number = c.getInt(c.getColumnIndex("ID"));

                            }
                            c.close();
                        }

                        String dateTime[] = getDateTime();
                        String branch_id = sp.getString("branch_id", "");
                        String branch_name = helper.GetBranchName(branch_id);

                        String products = helper.GetMatName(product_id);
                        float sub_total;
                        int quantuty = temp_order.get(i).getQty();

                        float price = temp_order.get(i).getPrice();
                        sum += temp_order.get(i).getId();

                        sub_total = quantuty * price;

                        showrow =

                                Arrays.asList(
                                        String.valueOf(products),
                                        String.valueOf(temp_order.get(i).getQty()),
                                        String.valueOf(temp_order.get(i).getPrice()),
                                        String.valueOf(sub_total));


                        //listdetails.add(" "+price);
                        //String json = gson.toJson(listdetails);
                        //SharedPreferences.Editor editor = sp.edit();
                        //editor.putString("Set",json );
                        //editor.commit();
                        //String products = helper.GetMatName(product_id);
                        String catogry_name = "";
                        String query_catogries = "SELECT * FROM products  LEFT JOIN  categories c on  products.category_id=c.ID where products.ID='" + product_id + "'";
                        c = database.rawQuery(query_catogries, null);
                        if (c != null) {
                            while (c.moveToNext()) {

                                catogry_name = c.getString(c.getColumnIndex("name"));

                            }
                        }
                        Log.i("muusa2", "this is my catogry" + catogry_name);
                        String advertise = " created by : https://aurages.com\n";
                        branch_id = sp.getString("branch_id", "");
                        username = sp.getString("name", "");
                        branch_name = helper.GetBranchName(branch_id);
                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
                        String dates = dateFormat.format(currentTime);
                        String time = timeformat.format(currentTime);
                        String company = ""
                                + "     " + "Resturant name:" + branch_name + "\n"
                                + "Invoices number:" + number + " " + "user:" + username + "\n" +
                                "DATE:" + dates + " " + "TIME:" + time + "\n"
                                + "\n";
                        String summary = ""
                                + "Catogry\n";

                        String summaryVal = ""
                                + catogry_name + "\n";

                        List<Integer> t2ColWidths = Arrays.asList(14, 6, 7, 8);



                            Board b = new Board(48);
                            b.setInitialBlock(new Block(b, 35, 3, company).allowGrid(false).setBlockAlign(Block.BLOCK_CENTRE));
                            b.appendTableTo(0, Board.APPEND_BELOW, new Table(b, 35, t2Headers, Collections.singletonList(showrow), t2ColWidths).setColAlignsList(colAlignList));
                            Block summaryBlock = new Block(b, 15, 4, summary).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_LEFT);
                            b.getBlock(5).setBelowBlock(summaryBlock);
                            Block summaryValBlock = new Block(b, 15, 4, summaryVal).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_LEFT);
                            summaryBlock.setRightBlock(summaryValBlock);
                            summaryBlock.setBelowBlock(new Block(b, 35, 3, advertise).setDataAlign(Block.DATA_MIDDLE_LEFT).allowGrid(false));
                            String content =b.invalidate().build().getPreview() ;
                        printCustom(b.invalidate().build().getPreview(),1,1);

                        outputStream.write(CutPaper, 0, CutPaper.length);
                            outputStream.flush();
                            outputStream.close();


                        } catch (Exception e) {
                            e.printStackTrace();


                    }
                }
                break;
            }


        }
    }
    @Override
    public void onClick(View v) {
        dismiss();

    }

}







