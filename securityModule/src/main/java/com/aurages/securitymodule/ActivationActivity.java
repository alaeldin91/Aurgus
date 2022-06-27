package com.Aurages.securitymodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivationActivity extends Activity implements SecurityDelegate {
    //finals
    public static final int REQUEST_ACTIVATION = 0;
    public static final int CHECK_STATUS = 1;

    //status
    int status = CHECK_STATUS;
    boolean sendRequestAfterCheckingStatus = false;

    SamaSecurityHelper helper;
    ProgressDialog prgrsDlg;

    //Views
    TextView textStatus;
    EditText editName;
    EditText editPhone;
    EditText editAddress;
    EditText editRemark;
    EditText editEmail;
    Button btnActive;
    //Other

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activation);

        textStatus = (TextView) findViewById(R.id.textStatus);
        editName = (EditText) findViewById(R.id.editTextName);
        editPhone = (EditText) findViewById(R.id.editTextPhone);
        editAddress = (EditText) findViewById(R.id.editTextAddress);
        editRemark = (EditText) findViewById(R.id.editTextRemark);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        btnActive = (Button) findViewById(R.id.btnActive);
        btnActive.setOnLongClickListener(new OnLongClickActive());

        CreateProgressDialog();

        helper = new SamaSecurityHelper(ActivationActivity.this);

    }

    private void CreateProgressDialog() {
        ProgressDialog dlg = new ProgressDialog(this);
        dlg.setCancelable(false);
        dlg.setTitle(R.string.please_wait);
        dlg.setMessage("Validating Activation Settings ...");

        dlg.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dlgInterface, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dlgInterface.dismiss();

                    if (status == CHECK_STATUS)
                        finish();
                }
                return true;
            }
        });

        prgrsDlg = dlg;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void clickRequestActive(View vi) {
        if (!editName.getText().toString().contentEquals("") &&
                !editPhone.getText().toString().contentEquals("") &&
                !editAddress.getText().toString().contentEquals("") &&
                !editEmail.getText().toString().contentEquals("")) {
            sendRequestAfterCheckingStatus = true;
            prgrsDlg.setMessage("Validating Activation Settings ...");
            prgrsDlg.show();
            helper.SecurityCheck();
        } else {
            sendRequestAfterCheckingStatus = false;
            prgrsDlg.setMessage("Validating Activation Settings ...");
            prgrsDlg.show();
            helper.SecurityCheck();
        }
    }

    private void SendRequst() {
        if (APISecurityHelper.ActivationStatus == SamaSecurityHelper.SECURITY_STATUS_ACTIVATED)
            return;

        if (!editName.getText().toString().contentEquals("") &&
                !editPhone.getText().toString().contentEquals("") &&
                !editAddress.getText().toString().contentEquals("") &&
                !editEmail.getText().toString().contentEquals("")) {
            if (APISecurityHelper.ActivationStatus == SamaSecurityHelper.SECURITY_STATUS_UNREGISTERD ||
                    APISecurityHelper.ActivationStatus == SamaSecurityHelper.SECURITY_STATUS_CONN_ERR) {
                prgrsDlg.setMessage("Requesting Activation ...");
                prgrsDlg.show();

                helper.requestActivation(editName.getText().toString(),
                        editPhone.getText().toString(),
                        editAddress.getText().toString(),
                        editRemark.getText().toString(),
                        editEmail.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Already registered", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void RequestReturnedResponse(int requestStatus) {
        prgrsDlg.dismiss();

        if (requestStatus == SamaSecurityHelper.REQUEST_SENT) {
            textStatus.setText("Activation Request Sent Successfully");
            Toast.makeText(getApplicationContext(), "Activation Request Sent Successfully", Toast.LENGTH_SHORT).show();
        }

        if (requestStatus == SamaSecurityHelper.REQUEST_ALREADY_REGISTERED) {
            textStatus.setText("Already Registered, Waiting review.");
            Toast.makeText(getApplicationContext(), "Already Registered, Waiting review.", Toast.LENGTH_SHORT).show();
        }
        if (requestStatus == SamaSecurityHelper.REQUEST_CONN_ERR) {
            textStatus.setText("Couldn't send activation request, This maybe caused by connection problems.");
            Toast.makeText(getApplicationContext(), "Couldn't send activation request, This maybe caused connection problems.", Toast.LENGTH_LONG).show();
        }

        if (requestStatus == SamaSecurityHelper.REQUEST_FILLCONTENT) {
            textStatus.setText("Fill Content please..");
            Toast.makeText(getApplicationContext(), "No Content..", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void CheckEndedWithResult(int resultCode) {

        prgrsDlg.dismiss();

        switch (resultCode) {
            case SamaSecurityHelper.SECURITY_STATUS_UNREGISTERD:
                textStatus.setText("Status : UnRegisterd Please Fill Content..");
                break;

            case SamaSecurityHelper.SECURITY_STATUS_CONN_ERR:
                textStatus.setText("Connection Error..");
                break;

            case SamaSecurityHelper.SECURITY_STATUS_ACTIVATED:
                textStatus.setText("Activated Successfully.");
                finish();
                break;

            case SamaSecurityHelper.SECURITY_STATUS_WAITING_REVIEW:
                textStatus.setText("Activation requested waiting.");
                break;

            case SamaSecurityHelper.SECURITY_STATUS_INACTIVE:
                textStatus.setText("Status : Inactive");
                break;

        }

        if (sendRequestAfterCheckingStatus) {
            SendRequst();
            sendRequestAfterCheckingStatus = false;
        }
    }

    public void longClickActive() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Manual Activation");

        View view = inflater.inflate(R.layout.dialog_local_active, null);

        final EditText editKey = (EditText) view.findViewById(R.id.editTextActiveKey);
        EditText editIMEI = (EditText) view.findViewById(R.id.editTextIMEI);
        editIMEI.setText(new SystemInfoHelper(this).GetUniqueID("0118839360"));

        dialog.setView(view);


        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (ActivationkeyChecker.CheckActive(ActivationActivity.this, editKey.getText().toString())) {
                    APISecurityHelper.ActivationStatus = SamaSecurityHelper.SECURITY_STATUS_ACTIVATED;
                    APISecurityHelper.SetIsActivated(getApplicationContext(), true);
                    dialog.cancel();
                }
            }
        });

        dialog.show();
    }

    class OnLongClickActive implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View vi) {
            longClickActive();
            return true;
        }
    }
}
