package com.aurages.ArestaurantWeb.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

public class URL {

    public static String ROOT_URL = "http://dash.aurages.com/api/";
    public static String ROOT_image = "http://dash.aurages.com/storage/";

    public static String Mats_URL = ROOT_URL + "getAllMat";
    public static String Groups_URL = ROOT_URL + "getAllgroups";
    public static String Parts_URL = ROOT_URL + "getAllparts";
    public static String ComboParents_URL = ROOT_URL + "getAllComboParent";
    public static String CUSTS_URL = ROOT_URL + "getAllCust";
    public static String OrderOptions_URL = ROOT_URL + "getAllOrderOption";
    public static String Tables_URL = ROOT_URL + "getAllTable";
    public static String Hosts_URL = ROOT_URL + "getAllHost";
    public static String TablesPlaces_URL = ROOT_URL + "";
    public static String Quests_URL = ROOT_URL + "";
    public static String QuestAnswers_URL = ROOT_URL + "";
    public static String MatOrderOptions_URL = ROOT_URL + "getAllMatOrderOption";
    public static String DealParents_URL = ROOT_URL + "getAllDeal_parent";
    public static String images_URL = ROOT_URL + "getAllImages";

    public static String TABLE_COLUMNS = ROOT_URL + "";

    //LOGIN_URL
    public static String LOGIN_URL = ROOT_URL + "login";
    public static String GET_BRANCH = ROOT_URL + "getAllBranch";
    public static String GET_FLOOOR = ROOT_URL + "getFloor";
    public static String GET_MENU = ROOT_URL + "getMenu";
    public static String GET_CATEGORY = ROOT_URL + "getCategory";
    public static String GET_PRODUCTS = ROOT_URL + "getProduct";
    public static String GET_INGREDIENTS = ROOT_URL + "getIngrediant";
    public static String GET_MODIFIRES = ROOT_URL + "getModifier";
    public static String GET_TABLES= ROOT_URL + "getTable";
    //getCategory

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    //0912987969

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("https://dash.aurages.com/api/");//You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

}
