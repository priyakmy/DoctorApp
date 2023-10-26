package com.mcura.mcurapharmacy.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.Utils.EnumType;
import com.mcura.mcurapharmacy.model.LoginModel;
import com.mcura.mcurapharmacy.model.SubtenantSearchByLoginId;
import com.mcura.mcurapharmacy.retrofit.SubtenantLoginId;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public MCuraApplication mCuraApplication;
    private Button btnSubmit;
    private EditText etUsername;
    private EditText etPassword;
    private SharedPreferences mSharedPreference;
    private ProgressDialog progress;
    String username, password;
    int facilityTypeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        if (mSharedPreference.getInt(Constants.USER_ROLE_ID_KEY, 0) != 0) {
            if (mSharedPreference.getInt(Constants.ROLE_ID_KEY, 0)==EnumType.loginRoleType.mLabOrderUser.getID()) {
                Intent i = new Intent(LoginActivity.this, LabOrderActivity.class);

                startActivity(i);
                finish();
            }
            else if (mSharedPreference.getInt(Constants.ROLE_ID_KEY, 0)==EnumType.loginRoleType.mPharmacyUser.getID()) {
                Intent i = new Intent(LoginActivity.this, PharmacyActivity.class);

                startActivity(i);
                finish();
            }
        } else {
            inItView();
        }
    }

    private void inItView() {
        btnSubmit = (Button) findViewById(R.id.activity_login_btn_submit);
        etUsername = (EditText) findViewById(R.id.activity_login_et_username);
        etPassword = (EditText) findViewById(R.id.activity_login_et_password);
        btnSubmit.setOnClickListener(this);
    }


    public void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(LoginActivity.this);
            progress.setCancelable(false);
            progress.setMessage(getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_btn_submit:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                if (username.length() > 0) {
                    if (password.length() > 0) {
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(
                                etPassword.getWindowToken(), 0);
                        getLoginDetail();
                    } else {
                        etPassword.setError("Enter Password");
                    }
                } else {
                    etUsername.setError("Enter Username");
                }
                break;
        }
    }
    private void getLoginDetail(){
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getLogin(etUsername.getText().toString(),
                etPassword.getText().toString(), new Callback<LoginModel>() {
                    @Override
                    public void success(final LoginModel loginModel, Response response) {
                        if (loginModel.getLoginId()!= 0) {



                            SharedPreferences.Editor editor = mSharedPreference.edit();
                            editor.putInt(Constants.STATUS_ID_KEY, loginModel.getCurrentStatusId());
                            editor.putString(Constants.DOMAIN_KEY, loginModel.getDomain());
                            editor.putInt(Constants.LOGIN_ID_KEY, loginModel.getLoginId());
                            editor.putString(Constants.LOGIN_NAME_KEY, loginModel.getLoginName());
                            editor.putInt(Constants.PIN_KEY, loginModel.getPin());
                            editor.putString(Constants.PASSWORD_KEY, loginModel.getPwd());
                            editor.putInt(Constants.USER_ROLE_ID_KEY, loginModel.getUserRoleId());
                            editor.putInt(Constants.ROLE_ID_KEY,loginModel.getRoleId());
                            editor.apply();
                            if (loginModel.getRoleId()== EnumType.loginRoleType.mLabOrderUser.getID()) {
                                facilityTypeId = EnumType.facilityTypeId.mLabOrderType.getID();
                            } else if (loginModel.getRoleId()==EnumType.loginRoleType.mPharmacyUser.getID()) {
                                facilityTypeId = EnumType.facilityTypeId.mPharmacyType.getID();
                            }

                            mCuraApplication.getInstance().mCuraEndPoint.subtenantSearchByLoginId(loginModel.getUserRoleId(),loginModel.getLoginId(),facilityTypeId, new Callback<SubtenantSearchByLoginId[]>() {
                                @Override
                                public void success(SubtenantSearchByLoginId[] subtenantSearchByLoginId, Response response) {
                                    if (subtenantSearchByLoginId.length != 0) {






                                        SharedPreferences.Editor editor = mSharedPreference.edit();
                                        editor.putInt(Constants.SUB_TANENT_ID_KEY, subtenantSearchByLoginId[0].getSubTenantId());
                                        editor.putString(Constants.SUB_TANENT_ID_NAME, subtenantSearchByLoginId[0].getSubTenantName());



                                        if (loginModel.getRoleId()== EnumType.loginRoleType.mLabOrderUser.getID()) {
                                            editor.putInt(Constants.LAB_PHARMACY_TYPE, EnumType.LabPharmacyType.mlab.getID());
                                            Intent i = new Intent(LoginActivity.this, LabOrderActivity.class);
                                            startActivity(i);
                                        }
                                        else if (loginModel.getRoleId()==EnumType.loginRoleType.mPharmacyUser.getID()) {
                                            editor.putInt(Constants.LAB_PHARMACY_TYPE, EnumType.LabPharmacyType.mPharmacy.getID());
                                            Intent i = new Intent(LoginActivity.this, PharmacyActivity.class);
                                            startActivity(i);
                                        }
                                        editor.apply();
                                        dismissLoadingDialog();
                                        finish();
                                    } else {
                                        dismissLoadingDialog();
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    dismissLoadingDialog();
                                }
                            });

                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Login name or Password", Toast.LENGTH_LONG).show();
                            dismissLoadingDialog();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dismissLoadingDialog();
                    }
                });
    }


}
