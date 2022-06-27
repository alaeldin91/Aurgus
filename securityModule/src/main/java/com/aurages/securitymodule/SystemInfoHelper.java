package com.Aurages.securitymodule;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

class SystemInfoHelper 
{
	private Activity _context;
	
	public SystemInfoHelper(Activity context)
	{
		_context = context;
	}
	
	public String GetUniqueID(String defValue)
	{
		String result = GetIMEI();
		if (result.equals("") || result.equals("000000000000000"))
			result = GetDeviceID();
		if (result.equals(""))
			result = GetSubscriberID();
		if (result.equals(""))
			result = GetSimID();
		if (result.equals(""))
			result = GetAndroidID();	
		if (result.equals(""))
			result = defValue;
		
		return result;
	}
	
	private String GetIMEI()
	{//  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
		try 
		{
	    	TelephonyManager TelephonyMgr = (TelephonyManager)_context.getSystemService(Context.TELEPHONY_SERVICE);
	    	String res = TelephonyMgr.getDeviceId();	    	
	    	return res.trim();
		} 
	    catch (Exception e) 
	    {
	        return "";
	    }
	}
	
	public String GetSubscriberID()
	{//  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
		try 
		{
	    	TelephonyManager TelephonyMgr = (TelephonyManager)_context.getSystemService(Context.TELEPHONY_SERVICE);
	    	String res = TelephonyMgr.getSubscriberId();   	
	    	return res.trim();
		} 
	    catch (Exception e) 
	    {
	        return "";
	    }
	}
	
	public String GetSimID()
	{//  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
		try 
		{
	    	TelephonyManager TelephonyMgr = (TelephonyManager)_context.getSystemService(Context.TELEPHONY_SERVICE);
	    	String res = TelephonyMgr.getSimSerialNumber();
	    	return res.trim();
		} 
	    catch (Exception e) 
	    {
	        return "";
	    }
	}
	
	
	
	public String GetDeviceID()
    {
    	try 
    	{
	        String res = "35" + //we make this look like a valid IMEI
	        	Build.BOARD.length()%10+ Build.BRAND.length()%10 + 
	        	Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 + 
	        	Build.DISPLAY.length()%10 + Build.HOST.length()%10 + 
	        	Build.ID.length()%10 + Build.MANUFACTURER.length()%10 + 
	        	Build.MODEL.length()%10 + Build.PRODUCT.length()%10 + 
	        	Build.TAGS.length()%10 + Build.TYPE.length()%10 + 
	        	Build.USER.length()%10 ; //13 digits
	        res = res.trim();
	        return res;
	    } 
	    catch (Exception e) 
	    {
	        return "";
	    }
    }
	
	public String GetAndroidID()
    {
    	try 
    	{
	        String res = Secure.getString(_context.getContentResolver(), Secure.ANDROID_ID);
	        res = res.trim();
	        return res;
	    } 
	    catch (Exception e) 
	    {
	        return "";
	    }
    }
}
