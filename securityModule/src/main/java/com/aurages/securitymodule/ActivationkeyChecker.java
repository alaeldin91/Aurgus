package com.Aurages.securitymodule;

import java.util.Calendar;

import android.app.Activity;
import android.widget.Toast;


public class ActivationkeyChecker {	
	
	
	public static boolean CheckActive(Activity context, String key)
	{		
		if (key.trim().equals(""))
			return false;
		try
		{
			String serial;
			String code;
			serial = new SystemInfoHelper(context).GetUniqueID("0118839360");
			code = "";
			int r;
		    for (int i=0; i< serial.length(); i++)
		    {
		    	r = Integer.parseInt(serial.substring(i, i + 1));
		    	code += Integer.toString(r * (i+1) * 13 + (i+1));		    	
		    }
		    String reg = key;
		    if (reg.length() < 16)
		    {
		    	Toast.makeText(context, "Invalid Activation Key.", Toast.LENGTH_SHORT).show();
		    	return false;	
		    }
		    reg = reg.substring(0, reg.length()-16).trim();
		    if (!reg.equals(code))
		    {
		    	Toast.makeText(context, "Invalid Activation Key.", Toast.LENGTH_SHORT).show();		    	   	
				return false;
		    }
		    
		    reg = key;
		    String s = reg.substring(reg.length() - 16, reg.length());
			//
			String h = "";
			for (int i=0; i< s.length(); i++)
				if (i % 2 == 0)
					h += s.substring(i, i + 1);
			if (h.length() == 8)
			{
				int y = Integer.parseInt(h.substring(0, 4));
				int m = Integer.parseInt(h.substring(4, 6));
				int d = Integer.parseInt(h.substring(6, 8));
				
				final Calendar c = Calendar.getInstance();
		        int mYear = c.get(Calendar.YEAR);
		        int mMonth = c.get(Calendar.MONTH) + 1;
		        int mDay = c.get(Calendar.DAY_OF_MONTH);
		        if (mYear > y)
		        	return false;
		        if (mYear == y)
		        {
		        	if (mMonth > m)
		        		return false;
		        	if (mMonth == m)
		        	{
		        		if (mDay > d)
		        			return false;		        		
		        	}
		        }		        	
			}	
			return true;
		}
		catch (Exception e)
		{			
			return false;
		}	
	}
}
