package com.aurages.ArestaurantWeb.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.aurages.ArestaurantWeb.Aurages;
import com.aurages.ArestaurantWeb.Model.BranchesModel;
import com.aurages.ArestaurantWeb.Model.Error_log;
import com.aurages.ArestaurantWeb.Model.ModelBill;
import com.aurages.ArestaurantWeb.Model.ModelCatogry;
import com.aurages.ArestaurantWeb.Model.ModelPPrinter;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.Model.ModelTables;
import com.aurages.ArestaurantWeb.Model.Model_PaymentType;
import com.aurages.ArestaurantWeb.Model.OrderOptions;
import com.aurages.ArestaurantWeb.Model.TablePlaseModel;
import com.aurages.ArestaurantWeb.Model.Temp_Order;
import com.aurages.ArestaurantWeb.Model.UserHostModel;
import com.aurages.ArestaurantWeb.Model.WebOrderDetails_model;
import com.aurages.ArestaurantWeb.Model.WebOrders_model;
import com.aurages.ArestaurantWeb.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.aurages.ArestaurantWeb.Activity.OrdersScreen.round;

public class SqlLiteDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Rest.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    public static final String TABLE_NAME = "WebOrders";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_STATUS = "SaveOrder";
    public static final String COLUMN_NAME = "Guid";
    // public static final String COLUMN_STATUS = "status";


    public SqlLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private static boolean CreateTable(Context context, SQLiteDatabase db) {
        boolean enableCreate = true;
//        String sql;
//        try {
//            sql = "select * from Mats";
//            Cursor c = db.rawQuery(sql, null);
//            c.moveToFirst();
//            if (!c.isAfterLast())
//                c.close();
//        } catch (Exception e) {
//            enableCreate = true;
//        }
        return ((ExecuteFile(context, R.raw.tables, db))
                && (ExecuteFile(context, R.raw.tables_rest, db)));
    }

    private static boolean ExecuteFile(Context context, int resourceId, SQLiteDatabase db) {
        String sql;
        InputStream is = context.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine;
        try {
            sql = "";
            while ((readLine = br.readLine()) != null) {
                if (readLine.trim().equals("GO")) {
                    if (!sql.trim().equals("")) {
                        if (!Execute(sql.trim(), db))
                            return false;
                    }
                    sql = "";
                } else
                    sql = sql + " " + readLine.trim();
            }
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, " Error Create Table " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private static boolean Execute(String sql, SQLiteDatabase db) {
        try {
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public static String GetCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK);
        String date = sdf.format(new Date());
        return date;
    }

    public static String GetCurrentDateWhithoutTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        String date = sdf.format(new Date());
        return date;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateTable(Aurages.getContext(), db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL("DROP TABLE IF EXISTS WebOrders");

        onCreate(db);
    }

    public ArrayList<ModelProduct> GetAllMats(String giud) {
        String query = "SELECT * FROM products where category_id ='" + giud + "'";
        ArrayList<ModelProduct> Mats1 = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                ModelProduct m = new ModelProduct();
                m.setId(c.getString(c.getColumnIndex("ID")));
                m.setNameAr(c.getString(c.getColumnIndex("nameAr")));
                m.setDescriptionAr(c.getString(c.getColumnIndex("descriptionAr")));
                m.setNameEn(c.getString(c.getColumnIndex("nameEn")));
                m.setDescriptionEn(c.getString(c.getColumnIndex("descriptionEn")));
                m.setNote(c.getString(c.getColumnIndex("note")));
                m.setSku(c.getString(c.getColumnIndex("sku")));
                m.setPrice(c.getString(c.getColumnIndex("price")));
                m.setSellType(c.getString(c.getColumnIndex("sellType")));
                m.setTax(c.getDouble(c.getColumnIndex("tax")));
                m.setTimedEventFrom(c.getString(c.getColumnIndex("timedEventFrom")));
                m.setTimedEventTo(c.getString(c.getColumnIndex("timedEventTo")));
                m.setActive(c.getInt(c.getColumnIndex("active")));
                m.setImage(c.getString(c.getColumnIndex("image")));
                m.setPrinterId(c.getString(c.getColumnIndex("printer_id")));
                m.setClassId(c.getString(c.getColumnIndex("active")));

                m.setCategoryId(c.getString(c.getColumnIndex("category_id")));

                m.setAddByUserId(c.getString(c.getColumnIndex("addByUserId")));
                m.setCreatedAt(c.getString(c.getColumnIndex("created_at")));
                m.setUpdatedAt(c.getString(c.getColumnIndex("updated_at")));
                m.setCode(c.getInt(c.getColumnIndex("code")));

                Mats1.add(m);
            }
            c.close();
        }

        return Mats1;
    }

    public Cursor getOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM  WebOrders ORDER BY  ID  ASC";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public ArrayList<BranchesModel> GetBranch() {
        String query = "SELECT * FROM branches";
        ArrayList<BranchesModel> branchesModels = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                BranchesModel m = new BranchesModel();
                m.setId(c.getString(c.getColumnIndex("id")));
                m.setName(c.getString(c.getColumnIndex("name")));
                m.setSlugable(c.getString(c.getColumnIndex("slugable")));
                branchesModels.add(m);

            }
            c.close();

        }
        return branchesModels;
    }
//    public ArrayList<Mats> GetAllMatsEl(String guid) {
//        String query = "SELECT * FROM Mats where ParentMaterial ='" + guid + "' order by ShowId";
//        ArrayList<Mats> Mats1 = new ArrayList<>();
//        SQLiteDatabase database = getReadableDatabase();
//        Cursor c = database.rawQuery(query, null);
//        if (c != null) {
//            while (c.moveToNext()) {
//                Mats m = new Mats();
//                m.setGuid(c.getString(c.getColumnIndex("Guid")));
//                m.setNumber(c.getInt(c.getColumnIndex("Number")));
//                m.setCode(c.getInt(c.getColumnIndex("Code")));
//                m.setName(c.getString(c.getColumnIndex("Name")));
//                m.setEnglishName(c.getString(c.getColumnIndex("EnglishName")));
//                m.setFrenchName(c.getString(c.getColumnIndex("FrenchName")));
//                m.setGroupGuid(c.getString(c.getColumnIndex("GroupGuid")));
//                m.setClassGuid(c.getString(c.getColumnIndex("ClassGuid")));
//                m.setPartGuid(c.getString(c.getColumnIndex("PartGuid")));
//                m.setPrice1(c.getDouble(c.getColumnIndex("Price1")));
//                m.setPrice2(c.getDouble(c.getColumnIndex("Price2")));
//                m.setPrice3(c.getDouble(c.getColumnIndex("Price3")));
//                m.setShowId(c.getInt(c.getColumnIndex("ShowId")));
//                m.setKind(c.getInt(c.getColumnIndex("Kind")));
//                m.setColor(c.getDouble(c.getColumnIndex("Color")));
//                m.setNotes(c.getString(c.getColumnIndex("Notes")));
//                m.setEnglishNotes(c.getString(c.getColumnIndex("EnglishNotes")));
//                m.setImageGuid(c.getString(c.getColumnIndex("ImageGuid")));
//                m.setComboParent(c.getString(c.getColumnIndex("ComboParent")));
//                m.setStoreCount(c.getDouble(c.getColumnIndex("StoreCount")));
//                m.setMinCount(c.getDouble(c.getColumnIndex("MinCount")));
//                m.setColorR(c.getInt(c.getColumnIndex("ColorR")));
//                m.setColorB(c.getInt(c.getColumnIndex("ColorB")));
//                m.setColorG(c.getInt(c.getColumnIndex("ColorG")));
//                m.setIsPrintPart(c.getInt(c.getColumnIndex("IsPrintPart")));
//                m.setIsMobile(c.getInt(c.getColumnIndex("IsMobile")));
//                m.setIsNewGroup(c.getInt(c.getColumnIndex("IsNewGroup")));
//                m.setIsEnjoymentGroup(c.getInt(c.getColumnIndex("IsEnjoymentGroup")));
//                m.setIsBidGroup(c.getInt(c.getColumnIndex("IsBidGroup")));
//                m.setID(c.getInt(c.getColumnIndex("ID")));
//                m.setIsExport(c.getInt(c.getColumnIndex("IsExport")));
//                m.setGroupMaterialCheck(c.getInt(c.getColumnIndex("GroupMaterialCheck")));
//                m.setParentMaterial(c.getString(c.getColumnIndex("ParentMaterial")));
//
//                Mats1.add(m);
//            }
//            c.close();
//        }
//
//        return Mats1;
//    }

    public boolean IsMatGroup(String guid) {
        boolean result = false;
        String sql = "select GroupMaterialCheck from Mats where Guid = '"
                + guid + "'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(sql, null);
        if (c != null) {
            while (c.moveToNext()) {
                int dResult = c.getInt(c.getColumnIndex("GroupMaterialCheck"));
                if (dResult == 1)
                    result = true;
            }
        }
        return result;
    }

    public boolean addorders(String name, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_STATUS, status);


        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean updateOrderStatus(int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_STATUS, status);
        db.update("WebOrders", contentValues, "SaveOrder ='" + 0 + "'", null);
        db.close();
        return true;
    }

    public boolean IsPoroductCategory(String ID) {
        boolean result = false;
        String sql = "select category_id from products where ID = '"
                + ID + "'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(sql, null);
        if (c != null) {
            while (c.moveToNext()) {
                String dResult = c.getString(c.getColumnIndex("category_id"));
                if (!dResult.equals(""))
                    result = true;
            }
        }
        return result;
    }

    public SQLiteDatabase con() {
        SQLiteDatabase con = getReadableDatabase();
        return con;
    }

    public boolean Execute(String sql) {
        try {
            SQLiteDatabase database = getReadableDatabase();
            database.execSQL(sql);
            return true;
        } catch (Exception e) {
//            Setting.Message +=  "\n" +  ": " + e.getMessage();
//            Setting.Message +=  "\n" + ": " + sql;
            return false;
        }
    }

    public String GetValueSql(String sql, String field, String defaultValue) {
        try {
            Cursor c = con().rawQuery(sql, null);
            c.moveToFirst();
            if (c.isAfterLast() == false) {
                if (c.getString(c.getColumnIndex(field)) != null)
                    return c.getString(c.getColumnIndex(field));
                else
                    return defaultValue;
            } else
                return defaultValue;
        } catch (Exception e) {
            //Setting.Message +=  "\n" + AsMessage.Error031 + ": " + e.getMessage();
            return defaultValue;
        }
    }

    public String GetValue(String tableName, String field, String where, String defaultValue) {
        try {
            String sql;
            sql = " select [" + field + "] from " + tableName;
            if (where.equals("") == false)
                sql = sql + " where " + where;

            return GetValueSql(sql, field, defaultValue);
        } catch (Exception e) {
            //Setting.Message +=  "\n" + AsMessage.Error028 + ": " + e.getMessage();
            return defaultValue;
        }
    }

    public ArrayList<ModelCatogry> GetAllGroups() {
        String query = "SELECT * FROM categories ";
        ArrayList<ModelCatogry> Groups1 = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                ModelCatogry m = new ModelCatogry();
                m.setId(c.getString(c.getColumnIndex("ID")));
                m.setName(c.getString(c.getColumnIndex("name")));
                m.setSku(c.getString(c.getColumnIndex("sku")));
                m.setTimedEventFrom(c.getString(c.getColumnIndex("timedEventFrom")));
                m.setTimedEventTo(c.getString(c.getColumnIndex("timedEventTo")));
                m.setActive(c.getInt(c.getColumnIndex("active")));
                m.setCatId(c.getString(c.getColumnIndex("cat_id")));
                m.setImage(c.getString(c.getColumnIndex("image")));
                m.setAddByUserId(c.getString(c.getColumnIndex("addByUserId")));
                m.setCreatedAt(c.getString(c.getColumnIndex("created_at")));
                m.setUpdatedAt(c.getString(c.getColumnIndex("updated_at")));
                m.setCode(c.getInt(c.getColumnIndex("code")));

                Groups1.add(m);
            }
            c.close();
        }

        return Groups1;
    }
    public boolean isTableExists( boolean openDb) {

        SQLiteDatabase database = getReadableDatabase();
        if(openDb) {
            if(database == null || !database.isOpen()) {
                database = getReadableDatabase();
            }

            if(!database.isReadOnly()) {
                database.close();
                database = getReadableDatabase();
            }
        }
        String query = "SELECT * FROM  branches";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {

                cursor.close();
                return true;
            }
            else {
                cursor.close();
            }
            cursor.close();
        }
        return false;
    }
    public ArrayList<Temp_Order> GetAllTempOrder() {
        String query = "SELECT * FROM Temp_orders ";
        ArrayList<Temp_Order> TempOrder = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                Temp_Order t = new Temp_Order();
                t.setId(c.getColumnIndex("ID"));
                t.setGuid(c.getString(c.getColumnIndex("Guid")));
                t.setName(c.getString(c.getColumnIndex("Name")));
                t.setEnglish_name(c.getString(c.getColumnIndex("EnglishName")));
                t.setQty(c.getInt(c.getColumnIndex("Qty")));
                t.setPrice(c.getFloat(c.getColumnIndex("Price")));
                t.setNotes(c.getString(c.getColumnIndex("Notes")));
                t.setMatCode(c.getString(c.getColumnIndex("MatCode")));

                TempOrder.add(t);
            }
            c.close();
        }

        if (database != null)
            database.close();
        return TempOrder;
    }

    public ArrayList<Temp_Order> GetAllTempOrderFromWebDet(String id) {
        String query = "SELECT * FROM WebOrderDetails w inner join Mats m  on m.code = w.MatCode where OrderID ='" + id + "'";
        ArrayList<Temp_Order> TempOrder = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                Temp_Order t = new Temp_Order();
                t.setGuid(c.getString(c.getColumnIndex("id")));
                t.setName(c.getString(c.getColumnIndex("Name")));
                t.setQty(c.getInt(c.getColumnIndex("Qty")));
                t.setPrice(c.getFloat(c.getColumnIndex("Price")));
                t.setNotes(c.getString(c.getColumnIndex("Notes")));

                TempOrder.add(t);
            }
            c.close();
        }

        return TempOrder;
    }

    public ArrayList<TablePlaseModel> GetAllTablePlaces() {
        String query = "SELECT * FROM TablesPlaces ";
        ArrayList<TablePlaseModel> tablePlaseModels = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                TablePlaseModel t = new TablePlaseModel();
                t.setGuid(c.getString(c.getColumnIndex("Guid")));
                t.setName(c.getString(c.getColumnIndex("Name")));
                t.setEnglishName(c.getString(c.getColumnIndex("EnglishName")));
                t.setFrenchName(c.getString(c.getColumnIndex("FrenchName")));
                t.setCode(c.getString(c.getColumnIndex("Code")));
                t.setNumber(c.getInt(c.getColumnIndex("Number")));
                t.setNotes(c.getString(c.getColumnIndex("Notes")));

                t.setHostGuid(c.getString(c.getColumnIndex("HostGuid")));
                t.setUserGuid(c.getString(c.getColumnIndex("UserGuid")));
                t.setTablePrinter(c.getInt(c.getColumnIndex("TablePrinter")));
                t.setPrintCopies(c.getInt(c.getColumnIndex("PrintCopies")));

                t.setPrinterName(c.getString(c.getColumnIndex("PrinterName")));

                tablePlaseModels.add(t);
            }
            c.close();
        }

        return tablePlaseModels;
    }

    public ArrayList<ModelPPrinter> GetAllPrinter() {

        String query = "SELECT * FROM Printers ";

        ArrayList<ModelPPrinter> printerModels = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                ModelPPrinter t = new ModelPPrinter();
                t.setId(c.getString(c.getColumnIndex("ID")));
                t.setName(c.getString(c.getColumnIndex("name")));
                t.setEnName(c.getString(c.getColumnIndex("eName")));
                t.setPrinter(c.getString(c.getColumnIndex("printer")));
                t.setPrinterName(c.getString(c.getColumnIndex("printerName")));
                t.setPrinterIndex(c.getInt(c.getColumnIndex("printerIndex")));
                t.setCopiesNumber(c.getInt(c.getColumnIndex("copiesNumber")));
                t.setNote(c.getString(c.getColumnIndex("note")));
                t.setBranchId(c.getString(c.getColumnIndex("branch_id")));
                t.setAddByUserId(c.getString(c.getColumnIndex("addByUserId")));
                t.setCreatedAt(c.getString(c.getColumnIndex("created_at")));
                t.setUpdatedAt(c.getString(c.getColumnIndex("updated_at")));
                printerModels.add(t);

            }
            c.close();
        }

        return printerModels;
    }

 public ArrayList<Model_PaymentType> GetAllPayment() {
        String query = "SELECT * FROM Payment ";
        ArrayList<Model_PaymentType> paymentTypesModels = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                Model_PaymentType t = new Model_PaymentType();
                t.setId(c.getString(c.getColumnIndex("ID")));
                t.setName(c.getString(c.getColumnIndex("name")));
                t.setNameEn(c.getString(c.getColumnIndex("eName")));
                t.setValue(c.getInt(c.getColumnIndex("value")));
                t.setType(c.getInt(c.getColumnIndex("type")));
                t.setDefault(c.getInt(c.getColumnIndex("defaults")));
                t.setNote(c.getString(c.getColumnIndex("note")));
                t.setAddByUserId(c.getString(c.getColumnIndex("addByUserId")));
                t.setCreatedAt(c.getString(c.getColumnIndex("created_at")));
                t.setUpdatedAt(c.getString(c.getColumnIndex("updated_at")));
                paymentTypesModels.add(t);
            }
            c.close();
        }

        return paymentTypesModels;
    }

   public ArrayList<ModelBill> GETBill() {
        String query = "SELECT * FROM Bill ";
        ArrayList<ModelBill> modelBills = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                ModelBill t = new ModelBill();
                t.setId(c.getString(c.getColumnIndex("ID")));
                t.setBillKindNumber(c.getInt(c.getColumnIndex("BillKindNumber")));
                t.setBillKindName(c.getString(c.getColumnIndex("BillKindName")));
                t.setBillKindNameEnglish(c.getString(c.getColumnIndex("BillKindNameEnglish")));
                t.setAddByUserId(c.getString(c.getColumnIndex("addByUserId")));
                t.setCreatedAt(c.getString(c.getColumnIndex("created_at")));
                t.setUpdatedAt(c.getString(c.getColumnIndex("updated_at")));
                modelBills.add(t);

            }
            c.close();


        }
        return modelBills;

    }


    public ArrayList<ModelTables> GetAllTables() {
        String query = "SELECT * FROM Tables";
        ArrayList<ModelTables> tableModels = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                ModelTables t = new ModelTables();
                t.setId(c.getString(c.getColumnIndex("id")));
                t.setName(c.getString(c.getColumnIndex("Name")));
                t.setNumber(c.getInt(c.getColumnIndex("number")));
                t.setChairsNumber(c.getInt(c.getColumnIndex("ChairsNumber")));
                t.setMaxChairsNumber(c.getInt(c.getColumnIndex("MaxChairsNumber")));
                t.setStatus(c.getInt(c.getColumnIndex("status")));
                t.setFloorId(c.getString(c.getColumnIndex("floor_id")));

                t.setBranchId(c.getString(c.getColumnIndex("branch_id")));
                t.setAddByUserId(c.getString(c.getColumnIndex("addByUserId")));
                t.setCreatedAt(c.getString(c.getColumnIndex("created_at")));
                t.setUpdatedAt(c.getString(c.getColumnIndex("updated_at")));
                t.setAddByUserId(c.getString(c.getColumnIndex("addByUserId")));
                tableModels.add(t);
            }
            c.close();
        }

        return tableModels;
    }

    public ArrayList<OrderOptions> OrderOptions(String guid) {
        String query = "SELECT * FROM modifires where product_id = '" + guid + "'";
        Context context = Aurages.getContext();
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
//        if (prefs.getBoolean("useit", false)) {
//            query += " where Guid in (select OrderOptionGuid from MatOrderOptions where MatGuid = '"
//                    + guid + "') ";
//            query += " or (select count(*) from MatOrderOptions where MatGuid = '"
//                    + guid + "') = 0 ";
//        }
//        query += " Order by ShowId, Number ";

        ArrayList<OrderOptions> orderOptions = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                OrderOptions t = new OrderOptions();
                t.setId(c.getString(c.getColumnIndex("ID")));
                t.setNameAr(c.getString(c.getColumnIndex("nameAr")));
                t.setNameEn(c.getString(c.getColumnIndex("nameEn")));
                t.setSku(c.getString(c.getColumnIndex("sku")));
                t.setCost(c.getInt(c.getColumnIndex("cost")));
                t.setTax(c.getInt(c.getColumnIndex("tax")));
                t.setPrice(c.getInt(c.getColumnIndex("price")));
                t.setUnit(c.getString(c.getColumnIndex("unit")));
                t.setAddByUserId(c.getString(c.getColumnIndex("addByUserId")));
                t.setCreated_at(c.getString(c.getColumnIndex("created_at")));
                t.setUpdated_at(c.getString(c.getColumnIndex("updated_at")));
                t.setCode(c.getInt(c.getColumnIndex("code")));
                t.setProduct_id(c.getString(c.getColumnIndex("product_id")));
                orderOptions.add(t);
            }
            c.close();
        }

        return orderOptions;
    }

    public Temp_Order GetRowTempOrder(String guid) {
        String query = "SELECT * FROM Temp_orders where Guid='" + guid + "'";
        Temp_Order t = new Temp_Order();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                t.setName(c.getString(c.getColumnIndex("Name")));
                t.setEnglish_name(c.getString(c.getColumnIndex("EnglishName")));
                t.setQty(c.getInt(c.getColumnIndex("Qty")));
                t.setPrice(c.getFloat(c.getColumnIndex("Price")));
                t.setNotes(c.getString(c.getColumnIndex("Notes")));

            }
            c.close();
        }
        database.close();
        return t;
    }

    //insert to Error_log_ac
    public boolean InsertTo_Error(String error_no, String error_txt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Error_no", error_no);
        contentValues.put("Error_txt", error_txt);
        long result1 = db.insert("Error_log", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    //insert to Branch
    public boolean InsertBranch(String id, String name, String slugable, String delivery_price,
                                String address_address,

                                Double tax,
                                String phone,
                                String addByUserId,
                                String created_at,
                                String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("slugable", slugable);
        contentValues.put("delivery_price", delivery_price);
        contentValues.put("address_address", address_address);
        contentValues.put("tax", tax);
        contentValues.put("phone", phone);
        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);


        long result1 = db.insert("branches", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    //insert printers
    public boolean InsertPrinters(String id, String name, String ename, String printer,
                                  String printer_Name,
                                  int printer_index,
                                  int printer_copies,
                                  String note,
                                  String branch_id,
                                  String addByUserId,
                                  String created_at,
                                  String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("name", name);
        contentValues.put("eName", ename);
        contentValues.put("printer", printer);
        contentValues.put("printerName", printer_Name);
        contentValues.put("printerIndex", printer_index);
        contentValues.put("copiesNumber", printer_copies);
        contentValues.put("note", note);
        contentValues.put("branch_id", branch_id);
        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);


        long result1 = db.insert("Printers", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }


    //insert to categories
    public boolean InsertCategories(String id, String name, String sku,
                                    Object timedEventFrom,
                                    Object timedEventTo,
                                    int active,
                                    String cat_id,
                                    String image,
                                    String addByUserId,
                                    String created_at,
                                    String updated_at,
                                    int code,
                                    String menu_id,
                                    String category_id
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("name", name);
        contentValues.put("sku", sku);
        contentValues.put("timedEventTo", String.valueOf(timedEventTo));
        contentValues.put("active", active);
        contentValues.put("timedEventFrom", String.valueOf(timedEventFrom));
        contentValues.put("cat_id", cat_id);
        contentValues.put("image", image);
        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);
        contentValues.put("code", code);
        contentValues.put("menu_id", menu_id);
        contentValues.put("category_id", category_id);


        long result1 = db.insert("categories", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    //insert to Products
    public boolean
    InsertProducts(String id, String nameAr, String descriptionAr,
                   String nameEn, String descriptionEn, String sku, String price, String sellType,
                   double tax, String timedEventFrom, String timedEventTo,
                   int active,
                   String image,
                   String printer_id,
                   String class_id,
                   String catogry_id,
                   String addByUserId,

                   String created_at,
                   String updated_at,
                   int code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("nameAr", nameAr);
        contentValues.put("descriptionAr", descriptionAr);
        contentValues.put("nameEn", nameEn);
        contentValues.put("descriptionEn", descriptionEn);
        contentValues.put("sku", sku);
        contentValues.put("price", price);
        contentValues.put("sellType", sellType);
        contentValues.put("tax", tax);
        contentValues.put("timedEventFrom", timedEventFrom);
        contentValues.put("timedEventTo", timedEventTo);
        contentValues.put("active", active);
        contentValues.put("image", image);
        contentValues.put("printer_id", printer_id);
        contentValues.put("class_id", class_id);
        contentValues.put("category_id", catogry_id);
        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);
        contentValues.put("code", code);


        long result1 = db.insert("products", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    //insert to Tables
    public boolean InsertTables(String id, String Name, int number,
                                int ChairsNumber, int MaxChairsNumber, int status, String floor_id, String branch_id,
                                String addByUserId,
                                String created_at,
                                String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("Name", Name);
        contentValues.put("number", number);
        contentValues.put("ChairsNumber", ChairsNumber);
        contentValues.put("MaxChairsNumber", MaxChairsNumber);
        contentValues.put("status", status);
        contentValues.put("floor_id", floor_id);
        contentValues.put("branch_id", branch_id);
        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);


        long result1 = db.insert("Tables", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    //insert to floors
    public boolean InsertFloors(String id, String name, String description,
                                String menu_id, String branch_id, String addByUserId,
                                String created_at,
                                String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("menu_id", menu_id);
        contentValues.put("branch_id", branch_id);
        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);


        long result1 = db.insert("floors", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    //insert to ingredients
    public boolean InsertIngredients(String id, String nameAr, String nameEn,
                                     String note, int price, String unit, String sku, String addByUserId,
                                     String created_at,
                                     String updated_at,
                                     int code,
                                     String product_id,
                                     String ingredient_id,
                                     int quantity_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("nameAr", nameAr);
        contentValues.put("nameEn", nameEn);
        contentValues.put("note", note);
        contentValues.put("price", price);
        contentValues.put("unit", unit);
        contentValues.put("sku", sku);

        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);
        contentValues.put("code", code);
        contentValues.put("product_id", product_id);
        contentValues.put("ingredient", ingredient_id);
        contentValues.put("quantity_id", quantity_id);


        long result1 = db.insert("ingredients", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    //insert to modifires
    public boolean InsertModifires(String id, String nameAr, String nameEn,
                                   String sku, double cost, double tax, double price, String unit,
                                   String addByUserId,
                                   String created_at,
                                   String updated_at,
                                   int code,
                                   String product_id,
                                   String modifier_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("nameAr", nameAr);
        contentValues.put("nameEn", nameEn);
        contentValues.put("sku", sku);
        contentValues.put("cost", cost);
        contentValues.put("tax", tax);
        contentValues.put("price", price);
        contentValues.put("unit", unit);
        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);
        contentValues.put("code", code);
        contentValues.put("product_id", product_id);
        contentValues.put("modifier_id", modifier_id);


        long result1 = db.insert("modifires", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    //insert to menus
    public boolean InsertMenus(String id, String name, String description, String addByUserId,
                               String created_at,
                               String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);


        long result1 = db.insert("menus", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }
    public boolean InsertPayment(String id, String name, String name_eng, int value,int type,int defaults,String notes,String addByUserId,
                               String created_at,
                               String updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("name", name);
        contentValues.put("eName", name_eng);
        contentValues.put("value", value);
        contentValues.put("type", type);
        contentValues.put("defaults", defaults);
        contentValues.put("note", notes);
        contentValues.put("addByUserId", addByUserId);
        contentValues.put("created_at", created_at);
        contentValues.put("updated_at", updated_at);


        long result1 = db.insert("Payment", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }
    public boolean InsertBill(String id, int numberbill,String BillKindName,String BillKindNameEnglish,String addByUserId,
                              String created_at,String updated_at ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("BillKindNumber",numberbill);
        contentValues.put("BillKindName",BillKindName);
        contentValues.put("BillKindNameEnglish",BillKindNameEnglish);
        contentValues.put("addByUserId",addByUserId);
        contentValues.put("created_at",created_at);
        contentValues.put("updated_at",updated_at);
        long result1 = db.insert("Bill", null, contentValues);
        if (result1 == -1) {
            return false;
        } else {
            return true;

        }


    }



    public boolean LoginHost(String password) {
        String query = "SELECT * FROM Hosts where  Password='" + password + "'";
        Context context = Aurages.getContext();
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (!c.isAfterLast()) {
            while (c.moveToNext()) {
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("Code", c.getString(c.getColumnIndex("Code")));
                edit.apply();
            }
            c.close();
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Error_log> Get_error_logs() {
        String query = "SELECT * FROM Error_log ";
        Context context = Aurages.getContext();


        ArrayList<Error_log> error_logs = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                Error_log e = new Error_log();
                e.setError_no(c.getString(c.getColumnIndex("Error_no")));
                e.setError_txt(c.getString(c.getColumnIndex("Error_txt")));
                error_logs.add(e);
            }
            c.close();
        }

        return error_logs;
    }

    public UserHostModel Get_userData(String pass) {
        String query = "SELECT * FROM Hosts where Password= '" + pass + "'";

        UserHostModel userHostModel = new UserHostModel();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            if (c.moveToNext()) {
                userHostModel.setU_guid(c.getString(c.getColumnIndex("Guid")));
                userHostModel.setU_name(c.getString(c.getColumnIndex("Name")));
            }
            c.close();
        }

        return userHostModel;
    }

    //insert to my orderGetAllGroups
    public boolean InsertTo_Temp_orders(String guid, String name, String english_name, double qty, String price, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Guid", guid);
        contentValues.put("Name", name);
        contentValues.put("EnglishName", english_name);
        contentValues.put("Qty", qty);
        contentValues.put("Price", price);
        contentValues.put("Notes", "");
        contentValues.put("MatCode", guid);


        ////////////////////////
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor res = db2.rawQuery("select Qty from Temp_orders where MatCode='" + guid + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            //do update
            int db_count = res.getInt(0);
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("Qty", db_count + qty);
            long result1 = db2.update("Temp_orders", contentValues2, "MatCode='" + guid + "'", null);
            if (result1 == -1) {
                return false;
            } else {
                return true;

            }

        } else {
            long result = db.insert("Temp_orders", null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }

        }
    }


    public boolean InsertTo_Temp_ordersFromView(String guid, int stateview) {
        boolean resulttt = false;
        SQLiteDatabase db = this.getWritableDatabase();
        WebOrderDetails_model weborderDet;
        ArrayList<WebOrderDetails_model> webOrderDetails_models = Get_WebOrderDetails_model(guid);
        if (webOrderDetails_models.size() > 0) {
            for (int i = 0; i < webOrderDetails_models.size(); i++) {
                weborderDet = webOrderDetails_models.get(i);
                ContentValues contentValues = new ContentValues();

                contentValues.put("Guid", guid);
                contentValues.put("Name", GetMatName(weborderDet.getMatCode()));
                contentValues.put("EnglishName", weborderDet.getMatCode());
                contentValues.put("Qty", weborderDet.getQty());
                contentValues.put("Price", weborderDet.getPrice());
                contentValues.put("Notes", weborderDet.getNotes());
                contentValues.put("view_state", stateview);
                contentValues.put("MatCode", weborderDet.getMatCode());


                ////////////////////////
//                SQLiteDatabase db2 = this.getReadableDatabase();
//                Cursor res = db2.rawQuery("select Qty from Temp_orders where guid='" + guid + "'", null);
//                res.moveToFirst();
//                if (!res.isAfterLast()) {
//                    //do update
//                    int db_count = res.getInt(0);
//                    ContentValues contentValues2 = new ContentValues();
//                    contentValues2.put("Qty", db_count + weborderDet.getQty());
//                    long result1 = db2.update("Temp_orders", contentValues2, "guid='" + guid + "'", null);
//                    if (result1 == -1) {
//                        resulttt = false;
//                    } else {
//                        resulttt = true;
//
//                    }
//
//                } else {
                long result = db.insert("Temp_orders", null, contentValues);
                if (result == -1) {
                    resulttt = false;
                } else {
                    resulttt = true;
                }

//                }
            }

        } else {
            resulttt = false;
        }


        if (resulttt == true) {
            boolean x = DeleteFromTable("WebOrderDetails", "OrderID = " + guid);
            boolean y = DeleteFromTable("WebOrders", "Guid = " + guid);
        }
        return resulttt;
    }

    private boolean DeleteFromTable(String table, String cond) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("delete from " + table + " " + "where" + " " + cond);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return db.delete(table, cond, null) > 0;
    }


    //insert to my orderSql
    public boolean InsertTo_Temp_ordersFromSql(String guid, String name, String english_name, double qty, double price, String notes, String IsChecked, String IsDelivered) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Guid", guid);
        contentValues.put("Name", name);
        contentValues.put("EnglishName", english_name);
        contentValues.put("Qty", qty);
        contentValues.put("Price", price);
        contentValues.put("Notes", "");
        contentValues.put("IsChecked", IsChecked);
        contentValues.put("IsDelivered", IsDelivered);

        ////////////////////////
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor res = db2.rawQuery("select Qty from Temp_orders where guid='" + guid + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            //do update
            int db_count = res.getInt(0);
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("Qty", db_count + qty);
            long result1 = db2.update("Temp_orders", contentValues2, "guid='" + guid + "'", null);
            if (result1 == -1) {
                return false;
            } else {
                return true;

            }

        } else {
            long result = db.insert("Temp_orders", null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }

        }
    }
    public boolean minQuyntatiy(String guid,int qty,double price) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        int quantity = qty - 1;
        double price_sum=price * quantity;

        contentValues.put("Qty", quantity);
        contentValues.put("Price", price_sum);


        long result1 = db.update("Temp_orders", contentValues, "MatCode='" + guid + "'", null);
        if (result1 == -1) {
            return false;
        } else {
            return true;
        }
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



    //Delete row from temp
    public boolean deleteRowFromMyTemp(String guid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Temp_orders", "MatCode='" + guid + "'", null) > 0;
    }
    public void deleteWebOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM WebOrders WHERE BillDate <= date('now','-1day')";
        db.execSQL(sql);
        String sqls= "DELETE FROM WebOrderDetails WHERE BillDate <= date('now','-1day')";
        db.execSQL(sqls);

    }



    //Delete row from temp
    public boolean deleteFromWebOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("WebOrders", null, null) > 0;
    }

    //Delete table
    public void deleteAllTableData(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + tableName);
    }

    public boolean Update_Temp_Row(String guid, String qty, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Qty", qty);
        //contentValues.put("Price", price);
        contentValues.put("Notes", notes);
        long result1 = db.update("Temp_orders", contentValues, "MatCode='" + guid + "'", null);
        if (result1 == -1) {
            return false;
        } else {
            return true;
        }
    }



    public boolean Update_Order_Row(int id, String BillState) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BillState", BillState);
        //contentValues.put("Price", price);
        long result1 = db.update("WebOrders", contentValues, "id='" + id + "'", null);
        if (result1 == -1) {
            return false;
        } else {
            return true;
        }
    }
public ArrayList<ModelPPrinter> PrintToProduct(String product_id){
    SQLiteDatabase database = getReadableDatabase();
ArrayList <ModelPPrinter> printers = new ArrayList<>();
    String query = "SELECT * FROM products  inner join Printers s  on s.ID = products.printer_id WHERE products.ID='" +product_id + "'" ;
    //database.execSQL(query);
    Cursor c = database.rawQuery(query, null);
    if (c != null) {
        while (c.moveToNext()) {
            ModelPPrinter w = new ModelPPrinter();
            w.setId(c.getString(c.getColumnIndex("ID")));
            w.setName(c.getString(c.getColumnIndex("name")));
            w.setName(c.getString(c.getColumnIndex("printer")));


            printers.add(w);
        }
        c.close();
    }

    return printers;
}


    public boolean UpdateLocal(int  id,String Guid,int saveOrder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SaveOrder", saveOrder);
        contentValues.put("Guid", Guid);
        //contentValues.put("Price", price);
        long result1 = db.update("WebOrders", contentValues, "ID='" + id + "'", null);
        if (result1 == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<WebOrders_model> Get_Orders(int BillKind, String BillState) {
        String query = "SELECT * FROM WebOrders " +
                "WHERE Number = " + BillKind + " AND BillState =" + BillState + " ";
//        if (!bill_type.equals("") || !date_from.equals("") || !date_to.equals("")) {
//            if (!bill_type.equals("")) {
//                query = query + "and BillKind = " + bill_type + " ";
//            }
//            if (!date_from.equals("") && !date_to.equals("")) {
//                //query = query+"and cast(Date as date) BETWEEN cast('"+date_from+"' as date) AND cast('"+date_to+"' as date) ";
//                //query = query+"and cast(Date as date) BETWEEN cast('2019-10-26' as date) AND cast('2019-10-27' as date) ";
//                query = query + "and cast(Date as date) BETWEEN cast('" + date_from + "' as date) and cast('" + date_to + "' as date) ";
//            }
//
//        }
        query = query + "order by ID desc ";

        ArrayList<WebOrders_model> webOrders_models = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                WebOrders_model w = new WebOrders_model(2,0);
                w.setId(c.getInt(c.getColumnIndex("ID")));
                w.setGuid(c.getString(c.getColumnIndex("Guid")));

                w.setNumber(c.getInt(c.getColumnIndex("Number")));
                w.setDailyNumber(c.getInt(c.getColumnIndex("DailyNumber")));
                w.setBillKind(c.getInt(c.getColumnIndex("BillKind")));
                w.setDate(c.getString(c.getColumnIndex("Date")));
                w.setBillDate(c.getString(c.getColumnIndex("BillDate")));
                w.setDeliveryDate(c.getString(c.getColumnIndex("DeliveryDate")));
                w.setBillState(c.getInt(c.getColumnIndex("BillState")));
                w.setTableCode(c.getString(c.getColumnIndex("TableCode")));
                w.setCustCode(c.getString(c.getColumnIndex("CustomCode")));
                w.setTotal(c.getDouble(c.getColumnIndex("Total")));
                w.setDiscount(c.getFloat(c.getColumnIndex("Discount")));
                w.setDiscountPercent(c.getString(c.getColumnIndex("DiscountPercent")));
                w.setExtra(c.getFloat(c.getColumnIndex("Extra")));
                w.setTax(c.getFloat(c.getColumnIndex("Tax")));
                w.setPayment(c.getFloat(c.getColumnIndex("Payment")));
                w.setService(c.getFloat(c.getColumnIndex("Service")));
                w.setNotes(c.getString(c.getColumnIndex("Notes")));
                w.setUserCode(c.getString(c.getColumnIndex("UserCode")));
                w.setPrinted(c.getInt(c.getColumnIndex("Printed")));

                w.setBranchCode(c.getString(c.getColumnIndex("BranchCode")));

                w.setSaveOrder(c.getInt(c.getColumnIndex("SaveOrder")));

                webOrders_models.add(w);
            }
            c.close();
        }

        return webOrders_models;
    }
    public ArrayList<WebOrders_model> Get_All_Orders() {
        String query = "SELECT * FROM WebOrders ";
//        if (!bill_type.equals("") || !date_from.equals("") || !date_to.equals("")) {
//            if (!bill_type.equals("")) {
//                query = query + "and BillKind = " + bill_type + " ";
//            }
//            if (!date_from.equals("") && !date_to.equals("")) {
//                //query = query+"and cast(Date as date) BETWEEN cast('"+date_from+"' as date) AND cast('"+date_to+"' as date) ";
//                //query = query+"and cast(Date as date) BETWEEN cast('2019-10-26' as date) AND cast('2019-10-27' as date) ";
//                query = query + "and cast(Date as date) BETWEEN cast('" + date_from + "' as date) and cast('" + date_to + "' as date) ";
//            }
//
//        }
        query = query + "order by ID desc ";

        ArrayList<WebOrders_model> webOrders_models = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                WebOrders_model w = new WebOrders_model(2,1);
                w.setId(c.getInt(c.getColumnIndex("ID")));
                w.setGuid(c.getString(c.getColumnIndex("Guid")));

                w.setNumber(c.getInt(c.getColumnIndex("Number")));
                w.setDailyNumber(c.getInt(c.getColumnIndex("DailyNumber")));
                w.setBillKind(c.getInt(c.getColumnIndex("BillKind")));
                w.setDate(c.getString(c.getColumnIndex("Date")));
                w.setBillDate(c.getString(c.getColumnIndex("BillDate")));
                w.setDeliveryDate(c.getString(c.getColumnIndex("DeliveryDate")));
                w.setBillState(c.getInt(c.getColumnIndex("BillState")));
                w.setTableCode(c.getString(c.getColumnIndex("TableCode")));
                w.setCustCode(c.getString(c.getColumnIndex("CustomCode")));
                w.setTotal(c.getDouble(c.getColumnIndex("Total")));
                w.setDiscount(c.getFloat(c.getColumnIndex("Discount")));
                w.setDiscountPercent(c.getString(c.getColumnIndex("DiscountPercent")));
                w.setExtra(c.getFloat(c.getColumnIndex("Extra")));
                w.setTax(c.getFloat(c.getColumnIndex("Tax")));
                w.setPayment(c.getFloat(c.getColumnIndex("Payment")));
                w.setService(c.getFloat(c.getColumnIndex("Service")));
                w.setNotes(c.getString(c.getColumnIndex("Notes")));
                w.setUserCode(c.getString(c.getColumnIndex("UserCode")));
                w.setPrinted(c.getInt(c.getColumnIndex("Printed")));

                w.setBranchCode(c.getString(c.getColumnIndex("BranchCode")));

                w.setSaveOrder(c.getInt(c.getColumnIndex("SaveOrder")));

                webOrders_models.add(w);
            }
            c.close();
        }

        return webOrders_models;
    }

    public ArrayList<WebOrders_model> Get_report_bills(String bill_type, String date_from, String date_to) {
        String query = "SELECT * FROM WebOrders " +
                "WHERE ID IS NOT NULL ";

        if (!bill_type.equals("") || !date_from.equals("") || !date_to.equals("")) {
            if (!bill_type.equals("")) {
                query = query + "and BillKind = " + bill_type + " ";
            }
            if (!date_from.equals("") && !date_to.equals("")) {
                //query = query+"and cast(Date as date) BETWEEN cast('"+date_from+"' as date) AND cast('"+date_to+"' as date) ";
                //query = query+"and cast(Date as date) BETWEEN cast('2019-10-26' as date) AND cast('2019-10-27' as date) ";
                query = query + "and cast(Date as date) BETWEEN cast('" + date_from + "' as date) and cast('" + date_to + "' as date) ";
            }

        }
        query = query + "order by Number desc ";

        ArrayList<WebOrders_model> webOrders_models = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                WebOrders_model w = new WebOrders_model(1,0);
                w.setNumber(c.getInt(c.getColumnIndex("Number")));
                w.setDailyNumber(c.getInt(c.getColumnIndex("DailyNumber")));
                w.setBillKind(c.getInt(c.getColumnIndex("BillKind")));
                w.setDate(c.getString(c.getColumnIndex("Date")));
                w.setBillDate(c.getString(c.getColumnIndex("BillDate")));
                w.setHijriBillDate(c.getString(c.getColumnIndex("HijriBillDate")));
                w.setDeliveryDate(c.getString(c.getColumnIndex("DeliveryDate")));
                w.setBillState(c.getInt(c.getColumnIndex("BillState")));
                w.setRoomCode(c.getString(c.getColumnIndex("RoomCode")));
                w.setTableCode(c.getString(c.getColumnIndex("TableCode")));
                w.setHostCode(c.getString(c.getColumnIndex("HostCode")));
                w.setCustCode(c.getString(c.getColumnIndex("CustCode")));
                w.setCustName(c.getString(c.getColumnIndex("CustName")));

                w.setPayType(c.getInt(c.getColumnIndex("PayType")));
                w.setTotal(c.getDouble(c.getColumnIndex("Total")));
                w.setDiscount(c.getFloat(c.getColumnIndex("Discount")));
                w.setDiscountPercent(c.getString(c.getColumnIndex("DiscountPercent")));
                w.setExtra(c.getFloat(c.getColumnIndex("Extra")));
                w.setTax(c.getFloat(c.getColumnIndex("Tax")));
                w.setTaxAdmin(c.getFloat(c.getColumnIndex("TaxAdmin")));
                w.setPayment(c.getFloat(c.getColumnIndex("Payment")));
                w.setService(c.getFloat(c.getColumnIndex("Service")));
                w.setNotes(c.getString(c.getColumnIndex("Notes")));
                w.setUserCode(c.getString(c.getColumnIndex("UserCode")));
                w.setRestNumber(c.getInt(c.getColumnIndex("RestNumber")));
                w.setPrinted(c.getInt(c.getColumnIndex("Printed")));
                w.setPersonNo(c.getFloat(c.getColumnIndex("PersonNo")));
                w.setExport(c.getString(c.getColumnIndex("Export")));
                w.setExportWeb(c.getString(c.getColumnIndex("ExportWeb")));
                w.setBranchCode(c.getString(c.getColumnIndex("BranchCode")));
                w.setImageCode(c.getString(c.getColumnIndex("ImageCode")));
                w.setStateWeb(c.getString(c.getColumnIndex("StateWeb")));
                w.setIsEnd(c.getString(c.getColumnIndex("IsEnd")));
                w.setSaveOrder(c.getInt(c.getColumnIndex("SaveOrder")));

                webOrders_models.add(w);
            }
            c.close();
        }

        return webOrders_models;
    }

    public ArrayList<WebOrderDetails_model> Get_report_Mat_Sale(String _group_guid, String _mat_guid, String date_from, String date_to) {
//        String query = "SELECT * FROM WebOrderDetails w inner join Mats m  on m.code = w.MatCode" +
        String query = "SELECT * FROM WebOrderDetails w inner join Mats m  on m.code = w.MatCode " +
                "WHERE w.ID IS NOT NULL ";

        if (!_group_guid.equals("") || !_mat_guid.equals("") || !date_from.equals("") || !date_to.equals("")) {
            if (!_group_guid.equals("")) {
                query = query + "and w.MatCode in (select Code from Mats where GroupGuid = '" + _group_guid + "') ";
            }

            if (!_mat_guid.equals("")) {
                query = query + "and w.MatCode in (select Code from Mats where Guid = '" + _mat_guid + "') ";
            }
            if (!date_from.equals("") && !date_to.equals("")) {
                //query = query+"and cast(Date as date) BETWEEN cast('"+date_from+"' as date) AND cast('"+date_to+"' as date) ";
                //query = query+"and cast(Date as date) BETWEEN cast('2019-10-26' as date) AND cast('2019-10-27' as date) ";
                //query = query+"and cast(Date as date) BETWEEN cast('"+date_from+"' as date) and cast('"+date_to+"' as date) ";
            }
        }

        query = query + " order by w.Number desc ";

        ArrayList<WebOrderDetails_model> webOrders_models = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                WebOrderDetails_model w = new WebOrderDetails_model();
                w.setNumber(c.getInt(c.getColumnIndex("Code")));
                w.setOrderNumber(c.getInt(c.getColumnIndex("OrderNumber")));
                w.setOrderID(c.getInt(c.getColumnIndex("OrderID")));
                w.setMatCode(c.getString(c.getColumnIndex("Name")));
                w.setQty(c.getFloat(c.getColumnIndex("Qty")));
                w.setPrice(c.getFloat(c.getColumnIndex("Price")));
                w.setOrderType(c.getInt(c.getColumnIndex("OrderType")));
                w.setNotes(c.getString(c.getColumnIndex("Notes")));
                w.setBillType(c.getInt(c.getColumnIndex("BillType")));
                w.setPrinted(c.getInt(c.getColumnIndex("Printed")));
                w.setHostCode(c.getString(c.getColumnIndex("HostCode")));
                w.setIsChecked(c.getInt(c.getColumnIndex("IsChecked")));
                w.setParentOrder(c.getInt(c.getColumnIndex("ParentOrder")));
                w.setExport(c.getInt(c.getColumnIndex("Export")));
                w.setSaveOrderDetails(c.getInt(c.getColumnIndex("SaveOrderDetails")));

                webOrders_models.add(w);
            }
            c.close();
        }

        return webOrders_models;
    }

    public ArrayList<WebOrderDetails_model> Get_WebOrderDetails_model(String orderid) {
//        String query = "SELECT * FROM WebOrderDetails w inner join Mats m  on m.code = w.MatCode" +
//        String query = "SELECT * FROM WebOrderDetails where OrderID = " + OrderID ;
        String query = "SELECT * FROM WebOrderDetails where BillDate = '" + orderid + "'";
//        query = query + " order by Number desc ";

        ArrayList<WebOrderDetails_model> webOrders_models = new ArrayList<>();
        try {
            SQLiteDatabase database = getReadableDatabase();
            Cursor c = database.rawQuery(query, null);
            if (c != null) {
                while (c.moveToNext()) {
                    WebOrderDetails_model w = new WebOrderDetails_model();
                    w.setNumber(c.getInt(c.getColumnIndex("ID")));
                    w.setNumber(c.getInt(c.getColumnIndex("Number")));
                    w.setOrderNumber(c.getInt(c.getColumnIndex("OrderNumber")));
                    w.setOrderID(c.getInt(c.getColumnIndex("OrderID")));
                    w.setMatCode(c.getString(c.getColumnIndex("MatCode")));
                    w.setQty(c.getFloat(c.getColumnIndex("Qty")));
                    w.setPrice(c.getFloat(c.getColumnIndex("Price")));
                    w.setOrderType(c.getInt(c.getColumnIndex("OrderType")));
                    w.setNotes(c.getString(c.getColumnIndex("Notes")));
                    w.setBillType(c.getInt(c.getColumnIndex("BillType")));
                    w.setPrinted(c.getInt(c.getColumnIndex("Printed")));
                    w.setHostCode(c.getString(c.getColumnIndex("HostCode")));
                    w.setIsChecked(c.getInt(c.getColumnIndex("IsChecked")));
                    w.setParentOrder(c.getInt(c.getColumnIndex("ParentOrder")));
                    w.setExport(c.getInt(c.getColumnIndex("Export")));
                    w.setSaveOrderDetails(c.getInt(c.getColumnIndex("SaveOrderDetails")));

                    webOrders_models.add(w);
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return webOrders_models;
    }

    public boolean SaveOrder(String codeTable, String BillKind,String orderid ,String BillState,String customer_name ,double total,float tax,String Payment,String branch_code,int Printed,int state,int number) {


        boolean result = false;

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String codeHost = prefs.getString("Code", "");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BillKind", BillKind);
        contentValues.put("BillState", BillState);
        contentValues.put("TableCode", codeTable);
        contentValues.put("CustomCode",customer_name);
        contentValues.put("Total",total);
        contentValues.put("Extra", 0);
        contentValues.put("Tax",tax);
        contentValues.put("DailyNumber", 1);
        contentValues.put("BillDate", GetCurrentDateTime());
        contentValues.put("Date", GetCurrentDateWhithoutTime());
        contentValues.put("Number", number);
        contentValues.put("Total", total);
        contentValues.put("Payment", Payment);
        contentValues.put("BranchCode", branch_code);
        contentValues.put("Printed", Printed);
        contentValues.put("Remain", 1);
        contentValues.put("SaveOrder", state);





        long result1 = db.insert("WebOrders", null, contentValues);
        if (result1 == -1) {
            result = false;
        } else {
            int MaxID = 0;
            String query = "select * from WebOrders where ID=(SELECT  Max(ID) as ID   from WebOrders )";
            SQLiteDatabase database = getReadableDatabase();
            Cursor c = database.rawQuery(query, null);
            if (c != null) {
                while (c.moveToNext()) {
                    MaxID = c.getInt(c.getColumnIndex("ID"));

                }

              // String OrderId = GetLastInsertedOrder();

                ArrayList<Temp_Order> temp_orders = GetAllTempOrder();
                for (int i = 0; i < temp_orders.size(); i++) {

                    Temp_Order tempOrder = temp_orders.get(i);

                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("Number", number);
                    contentValues2.put("OrderID",orderid);
                    contentValues2.put("OrderNumber",MaxID);
                    contentValues2.put("MatCode", tempOrder.getMatCode());
                    contentValues2.put("Qty", tempOrder.getQty());
                    contentValues2.put("price", tempOrder.getPrice());
                    contentValues2.put("ParentOrder", tempOrder.getGuid());
                    contentValues2.put("Notes", tempOrder.getMatCode());
                    SQLiteDatabase db2 = this.getWritableDatabase();
                    long result2 = db2.insert("WebOrderDetails", null, contentValues2);
//                    try {
//                        db2.insertOrThrow("WebOrderDetails", null, contentValues2);
//                        result = true;
//                    }catch (SQLiteConstraintException e){
//                        result = false;
//                    }
                    if (result2 == -1) {
                        result = false;
                    } else {
                        result = true;
                    }
                }


            } else {
                result = false;
            }


        }
        return result;
    }
    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM  WebOrders  WHERE   SaveOrder = '"+0+"'";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    //insert to weborder
    public boolean SaveOrder2(String codeTable, String BillKind, String BillState, String id, String total) {
        boolean result = false;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String codeHost = prefs.getString("Code", "");
        double tax = Double.parseDouble(prefs.getString("Tax", "0"));


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {


            contentValues.put("ID", id);
            contentValues.put("BillKind", BillKind);

            contentValues.put("BillKind", BillKind);
            contentValues.put("StateWeb", 1);
            contentValues.put("BillState", BillState);
            contentValues.put("TableCode", codeTable);
            contentValues.put("HostCode", codeHost);
            contentValues.put("Extra", 0);
            contentValues.put("Tax", tax);
            contentValues.put("IsEnd", 0);
            contentValues.put("DailyNumber", 1);
            contentValues.put("BillDate", GetCurrentDateTime());
            contentValues.put("Date", GetCurrentDateWhithoutTime());
            contentValues.put("Number", 1);
            contentValues.put("Total", total);
//            String query = "insert into  WebOrders (ID, BillKind, StateWeb, BillState, TableCode, HostCode," +
//                    "Extra,Tax, IsEnd, DailyNumber, BillDate, Date, Number" +
//                    ") values ("+id+ ","  +
//                    BillKind+ ","  +
//                    "1"+ ","  +
//                    BillState+ ","  +
//                    codeTable+ ","  +
//                    codeHost+ ","  +
//                    "0"+ ","  +
//                    tax+ ","  +
//                    "0"+ ","  +
//                    "1"+ ","  +
//                    GetCurrentDateTime()+ ","  +
//                    GetCurrentDateWhithoutTime()+ ","  +
//                    "1"+
//                    ") ";
//            try {
//                db.execSQL(query);
//            }catch (Exception e){
//                e.printStackTrace();
//                result = false;
//            }

            long result1 = db.insert("WebOrders", null, contentValues);
            if (result1 == -1) {
                result = false;
            } else {
                            //  String OrderId = GetLastInsertedOrder();
                ArrayList<Temp_Order> temp_orders = GetAllTempOrder();
                for (int i = 0; i < temp_orders.size(); i++) {
                    Temp_Order tempOrder = temp_orders.get(i);
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("Number", 1);
                    contentValues2.put("OrderNumber", tempOrder.getGuid());
                    contentValues2.put("OrderID", "");
                    contentValues2.put("MatCode", tempOrder.getMatCode());
                    contentValues2.put("Qty", tempOrder.getQty());
                    contentValues2.put("price", tempOrder.getPrice());
                    contentValues2.put("ParentOrder", tempOrder.getGuid());
                    contentValues2.put("HostCode", codeHost);
                    contentValues2.put("Notes", tempOrder.getNotes());
                    SQLiteDatabase db2 = this.getWritableDatabase();
                    long result2 = db2.insert("WebOrderDetails", null, contentValues2);
//                    try {
//                        db2.insertOrThrow("WebOrderDetails", null, contentValues2);
//                        result = true;
//                    }catch (SQLiteConstraintException e){
//                        result = false;
//                    }
                    if (result2 == -1) {
                        result = false;
                    } else {
                        result = true;
                    }
                }


            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }



  // public String GetLastInsertedOrder() {


     //   return OrderID;

    //}

    public String GetBranchName(String branch_id) {

        String query = "select name FROM branches where id = '" + branch_id + "'";
        String name = "";
        try {
            SQLiteDatabase database = getReadableDatabase();
            Cursor c = database.rawQuery(query, null);
            if (c != null) {
                while (c.moveToNext()) {

                    name = c.getString(c.getColumnIndex("name"));
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;

    }



    public String GetMatName(String matCode) {

        String query = "select nameEn FROM products where ID = '" + matCode + "'";
        String name = "";
        try {
            SQLiteDatabase database = getReadableDatabase();
            Cursor c = database.rawQuery(query, null);
            if (c != null) {
                while (c.moveToNext()) {

                    name = c.getString(c.getColumnIndex("nameEn"));
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;

    }

    public ArrayList<WebOrders_model> Get_NotSavedOrders() {
        String query = "SELECT * FROM WebOrders " +
                "WHERE StateWeb = " + 0 + " AND BillState =" + 1 + " ";
//        if (!bill_type.equals("") || !date_from.equals("") || !date_to.equals("")) {
//            if (!bill_type.equals("")) {
//                query = query + "and BillKind = " + bill_type + " ";
//            }
//            if (!date_from.equals("") && !date_to.equals("")) {
//                //query = query+"and cast(Date as date) BETWEEN cast('"+date_from+"' as date) AND cast('"+date_to+"' as date) ";
//                //query = query+"and cast(Date as date) BETWEEN cast('2019-10-26' as date) AND cast('2019-10-27' as date) ";
//                query = query + "and cast(Date as date) BETWEEN cast('" + date_from + "' as date) and cast('" + date_to + "' as date) ";
//            }
//
//        }
        query = query + "order by Number desc ";

        ArrayList<WebOrders_model> webOrders_models = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                WebOrders_model w = new WebOrders_model(2,0);
                w.setId(c.getInt(c.getColumnIndex("ID")));
                w.setGuid(c.getString(c.getColumnIndex("Guid")));
                w.setNumber(c.getInt(c.getColumnIndex("Number")));
                w.setDailyNumber(c.getInt(c.getColumnIndex("DailyNumber")));
                w.setBillKind(c.getInt(c.getColumnIndex("BillKind")));
                w.setDate(c.getString(c.getColumnIndex("Date")));
                w.setBillDate(c.getString(c.getColumnIndex("BillDate")));
                w.setHijriBillDate(c.getString(c.getColumnIndex("HijriBillDate")));
                w.setDeliveryDate(c.getString(c.getColumnIndex("DeliveryDate")));
                w.setBillState(c.getInt(c.getColumnIndex("BillState")));
                w.setRoomCode(c.getString(c.getColumnIndex("RoomCode")));
                w.setTableCode(c.getString(c.getColumnIndex("TableCode")));
                w.setHostCode(c.getString(c.getColumnIndex("HostCode")));
                w.setCustCode(c.getString(c.getColumnIndex("CustCode")));
                w.setCustName(c.getString(c.getColumnIndex("CustName")));

                w.setPayType(c.getInt(c.getColumnIndex("PayType")));
                w.setTotal(c.getDouble(c.getColumnIndex("Total")));
                w.setDiscount(c.getFloat(c.getColumnIndex("Discount")));
                w.setDiscountPercent(c.getString(c.getColumnIndex("DiscountPercent")));
                w.setExtra(c.getFloat(c.getColumnIndex("Extra")));
                w.setTax(c.getFloat(c.getColumnIndex("Tax")));
                w.setTaxAdmin(c.getFloat(c.getColumnIndex("TaxAdmin")));
                w.setPayment(c.getFloat(c.getColumnIndex("Payment")));
                w.setService(c.getFloat(c.getColumnIndex("Service")));
                w.setNotes(c.getString(c.getColumnIndex("Notes")));
                w.setUserCode(c.getString(c.getColumnIndex("UserCode")));
                w.setRestNumber(c.getInt(c.getColumnIndex("RestNumber")));
                w.setPrinted(c.getInt(c.getColumnIndex("Printed")));
                w.setPersonNo(c.getFloat(c.getColumnIndex("PersonNo")));
                w.setExport(c.getString(c.getColumnIndex("Export")));
                w.setExportWeb(c.getString(c.getColumnIndex("ExportWeb")));
                w.setBranchCode(c.getString(c.getColumnIndex("BranchCode")));
                w.setImageCode(c.getString(c.getColumnIndex("ImageCode")));
                w.setStateWeb(c.getString(c.getColumnIndex("StateWeb")));
                w.setIsEnd(c.getString(c.getColumnIndex("IsEnd")));
                w.setSaveOrder(c.getInt(c.getColumnIndex("SaveOrder")));

                webOrders_models.add(w);
            }
            c.close();
        }

        return webOrders_models;
    }


}
