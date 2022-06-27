package com.sama.securitymodule;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBSecurityHelper
{
	final String __DRIVER ="com.mysql.jdbc.Driver";
	final String __SERVER = "Aurages.com";
	final String __DB_NAME = "Aurages_k";
	final String __USERNAME = "Aurages_k";
	final String __PASSWORD = "YB7PgJ-eR92k";



	public static void SetIsActivated(Context context, boolean value)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();

		editor.putBoolean("IsActivated", value)
		.commit();
	}

	public static boolean IsActivated(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return prefs.getBoolean("IsActivated", false);
	}

	public static boolean IsActivated(Activity activity, boolean checkNow)
	{
		/// don't check
//		SamaSecurityHelper helper = new SamaSecurityHelper(activity);
//		helper.SecurityCheck();

		Context context = activity.getApplicationContext();
		SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		return prefs.getBoolean("IsActivated", false);
	}

	public static int ActivationStatus = -1;

	public Connection dbConnection;

	public String LastError = "";

	private String ConnectionString()
	{
		String connStr = "";
		connStr = "jdbc:mysql://" + __SERVER + ":3306" +
 			   "/" + __DB_NAME +
 			  "?user=" + __USERNAME +
 			  "&password=" + __PASSWORD +
 			  "&useUnicode=true&characterEncoding=UTF-8"
 			  + "&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";

		return connStr;
	}

	public boolean OpenMySQL()
	{
		LastError = "";
		try
		{
			if (dbConnection != null && !dbConnection.isClosed())
				return true;

			Class.forName(__DRIVER).newInstance();

			String url = ConnectionString();
			Log.d("DBHelper",url);
			dbConnection = DriverManager.getConnection(url);
			Log.d("DBHelper","Connected");
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String msg = e.getMessage();
			Log.d("msg", msg);
			LastError = msg;
			return false;
		}
	}

	public boolean ExecuteDMLStatement(String sql)
	{
		boolean result = false;
		LastError = "";
		try
		{
			if (OpenMySQL())
			{
				Statement st = dbConnection.createStatement();
				int rowsAffected = st.executeUpdate(sql);
				result = rowsAffected > 0;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();


			LastError = e.getMessage();

			result = false;
		}

		return result;
	}

	public ResultSet ExecuteSelectStatement(String sql)
	{
		ResultSet result = null;
		LastError = "";
		try
		{
			if (OpenMySQL())
			{
				Log.d("Creating Statement", sql);

				Statement st = dbConnection.createStatement();
				result = st.executeQuery(sql);

				Log.d("Query Fetched", sql);

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();


			LastError = e.getMessage();

			result = null;
		}

		return result;
	}

	public void Close()
	{
		try
		{
			dbConnection.close();
		}
		catch (Exception ex)
		{
		}
	}

	@Override
	protected void finalize() throws Throwable
	{
		Close();
		super.finalize();
	}
}
