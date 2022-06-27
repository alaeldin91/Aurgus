package com.Aurages.securitymodule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.Aurages.securitymodule.AppController.TAG;

class SamaSecurityHelper {
    public static final int SECURITY_STATUS_ACTIVATED = 1;
    public static final int SECURITY_STATUS_WAITING_REVIEW = 2;
    public static final int SECURITY_STATUS_UNREGISTERD = 3;
    public static final int SECURITY_STATUS_INACTIVE = 4;
    public static final int SECURITY_STATUS_CONN_ERR = 5;


    Context mContext;
    Activity mActivity;
    private APISecurityHelper apiSecurityHelper = new APISecurityHelper();
    ProgressDialog mProgressDlg;

    public SamaSecurityHelper(Activity act) {
        mContext = act.getApplicationContext();
        mActivity = act;
    }

    public void SecurityCheck() {
        mProgressDlg = null;

        new SecurityCheckTask().execute();
    }

    class SecurityCheckTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... arg0) {
            return CheckStatus();
        }

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);

            if (mActivity instanceof SecurityDelegate) {
                ((SecurityDelegate) mActivity).CheckEndedWithResult(status);
            }
        }
    }

    public void requestActivation(String name, String phone, String address, String remark, String email) {
        String[] info = {name, phone, address, remark, email};

        new ActivationRequestTask().execute(info);
    }

    public static final int REQUEST_ALREADY_REGISTERED = 0;
    public static final int REQUEST_SENT = 1;
    public static final int REQUEST_CONN_ERR = 2;
    public static final int REQUEST_FILLCONTENT = 3;

    class ActivationRequestTask extends AsyncTask<String[], Void, Void> {
        @Override
        protected Void doInBackground(String[]... arg0) {
            String[] info = arg0[0];

            final int requestStatus = RequestActivation(info);

            mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (mActivity instanceof SecurityDelegate) {
                        ((SecurityDelegate) mActivity).RequestReturnedResponse(requestStatus);
                    }
                }
            });

            return null;
        }
    }

    private int RequestActivation(String[] info) {

        int status = REQUEST_CONN_ERR;
        SystemInfoHelper sysInfo = new SystemInfoHelper(mActivity);
        final String IMEI = sysInfo.GetUniqueID("0118839360");
        final ArrayList<String> Info = new ArrayList<>();

        Info.add(info[0]);
        Info.add(info[1]);
        Info.add(info[2]);
        Info.add(info[3]);
        Info.add(info[4]);

        RequestQueue queue = Volley.newRequestQueue(mActivity);

        final ArrayList<String> temp = new ArrayList<>();

        String url = "new_keygon";
        StringRequest postRequest = new StringRequest(Request.Method.POST, APISecurityHelper.API_LINK + url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject data = new JSONObject(response);
                            temp.add(data.getString("result"));
                            temp.add(data.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s", "admin", "9wJeJn]G_*C85qH[");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Imei", IMEI);
                params.put("Name", Info.get(0));
                params.put("Phone", Info.get(1));
                params.put("Address", Info.get(2));
                params.put("Remark", Info.get(3));
                params.put("Email", Info.get(4));
                //Log.d("daaaaaaaaaaaaaaata","Name=" +Info.get(0)+"  \n"+"Phone="+Info.get(1));

                return params;
            }
        };

        queue.add(postRequest);


        while (temp.size() == 0) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }


        try {
            APISecurityHelper.SetIsActivated(mActivity.getApplicationContext(), false);
            APISecurityHelper.ActivationStatus = SECURITY_STATUS_WAITING_REVIEW;
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (temp.get(0).equals("true") & temp.get(1).equals("Request_Already_Registered"))
            status = REQUEST_ALREADY_REGISTERED;
        else if (temp.get(0).equals("true") & temp.get(1).equals("Request_Sent"))
            status = REQUEST_SENT;
        else if (temp.get(0).equals("false") & temp.get(1).equals("Request_Sent"))
            status = REQUEST_CONN_ERR;
        else if (temp.get(0).equals("false") & temp.get(1).equals("Fill_Content"))
            status = REQUEST_FILLCONTENT;


        return status;

    }

    // Activation Logic
	/*private int CheckStatus()
	{
		int status = SECURITY_STATUS_UNREGISTERD;
		
		SystemInfoHelper sysInfo = new SystemInfoHelper(mActivity);
		String IMEI = sysInfo.GetUniqueID("0118839360");
		
		String sql = String.format("SELECT * FROM keygen WHERE hdd = '%s' AND TypePro = '5'", IMEI);
		
		if (mDbHelper.OpenMySQL())
		{
			ResultSet rs = mDbHelper.ExecuteSelectStatement(sql);
			if (rs != null)
			{
				try
				{
					if (rs.next())
					{
						if (!IsNull(rs, "hddkey") && !rs.getString("hddkey").equals(""))
						{
							if (rs.getInt("IsActive") == 1)
							{
								String hddKey = rs.getString("hddkey");
								
								if (ActivationkeyChecker.CheckActive(mActivity, hddKey))
								{
									status = SECURITY_STATUS_ACTIVATED;
								}
							}
							else
							{
								status = SECURITY_STATUS_INACTIVE;
							}
						}
						else if (rs.getInt("IsWait") == 1)
						{
							status = SECURITY_STATUS_WAITING_REVIEW;
						}
					}
					else
					{
						status = SECURITY_STATUS_UNREGISTERD;
					}
				}
				catch (Exception ex)
				{
					status = SECURITY_STATUS_CONN_ERR;
				}
			}
			else
			{
				status = SECURITY_STATUS_CONN_ERR;
			}
		}
		else
		{
			status = SECURITY_STATUS_CONN_ERR;
		}
		
		DBSecurityHelper.ActivationStatus = status;
		DBSecurityHelper.SetIsActivated(mActivity.getApplicationContext(), (status == SECURITY_STATUS_ACTIVATED));
		
		return status;
	}*/


    private int CheckStatus() {
        int status = SECURITY_STATUS_CONN_ERR;

        SystemInfoHelper sysInfo = new SystemInfoHelper(mActivity);
        final String IMEI = sysInfo.GetUniqueID("0118839360");

        RequestQueue queue = Volley.newRequestQueue(mActivity);

        final ArrayList<String> temp = new ArrayList<>();

        String url = "check";
        StringRequest postRequest = new StringRequest(Request.Method.POST, APISecurityHelper.API_LINK + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject data = new JSONObject(response);

                            temp.add(data.getString("result"));
                            temp.add(data.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        VolleyLog.d(TAG, "Error: " + error.getMessage());


                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s", "admin", "9wJeJn]G_*C85qH[");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Imei", IMEI);
                return params;
            }
        };

        queue.add(postRequest);


        while (temp.size() == 0) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (temp.get(0).equals("true")) {

            if (ActivationkeyChecker.CheckActive(mActivity, temp.get(1)))
                status = SECURITY_STATUS_ACTIVATED;
        } else if (temp.get(0).equals("false") & temp.get(1).equals("InActive"))
            status = SECURITY_STATUS_INACTIVE;
        else if (temp.get(0).equals("false") & temp.get(1).equals("Waiting"))
            status = SECURITY_STATUS_WAITING_REVIEW;
        else if (temp.get(0).equals("false") & temp.get(1).equals("Non_Registered_Fill"))
            status = SECURITY_STATUS_UNREGISTERD;

        APISecurityHelper.ActivationStatus = status;
        APISecurityHelper.SetIsActivated(mActivity.getApplicationContext(), (status == SECURITY_STATUS_ACTIVATED));

        return status;
    }
}
