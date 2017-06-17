package com.example.hi.volleytest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RequestQueue.RequestFinishedListener<Object> {
    private EditText mEmailEditText,mPasswordEditText;
    private Button mLoginButton,mForgotPassword;
    private String EMAIL,PASSWORD,STATUS;
    public static RequestQueue queue;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLogin();
        queue= Volley.newRequestQueue(this);
    }
    public void initLogin()
    {
        dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("Just a Sec...");
        mEmailEditText= (EditText) findViewById(R.id.login_email_edittext);
        mPasswordEditText= (EditText) findViewById(R.id.login_password_edittext);
        mLoginButton= (Button) findViewById(R.id.login_button);
       // mForgotPassword= (Button) findViewById(R.id.forgot_password_button);
        mLoginButton.setOnClickListener(this);
        //mForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.login_button){

            EMAIL=mEmailEditText.getText().toString().trim();
            PASSWORD=mPasswordEditText.getText().toString().trim();
            if(EMAIL.isEmpty())
            {
                mEmailEditText.setError("Fill Email");
                Toast.makeText(this,"fill email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(PASSWORD.isEmpty())
            {
                mPasswordEditText.setError("Fill Password");
                Toast.makeText(this, "Fill Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches())
            {
                mEmailEditText.setError("Invalid Email Address");
                Toast.makeText(this, "Invalid Email Adrress", Toast.LENGTH_SHORT).show();
                return;
            }
            if(PASSWORD.length()<4 || PASSWORD.length()>15)
            {
                mPasswordEditText.setError("Enter Password between 4 to 15");
                return;
            }
            dialog.show();
            getLogin();
        }
       /* if(id==R.id.forgot_password_button){
            EMAIL=mEmailEditText.getText().toString().trim();
            if(EMAIL.isEmpty())
            {
                mEmailEditText.setError("Fill Email");
                Toast.makeText(this,R.string.FILL_EMAIL, Toast.LENGTH_SHORT).show();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches())
            {
                mEmailEditText.setError("Invalid Email Address");
                Toast.makeText(this, "Invalid Email Adrress", Toast.LENGTH_SHORT).show();
                return;
            }
            dialog.show();
            getPassword();
        }*/
    }
    public  void getLogin()
    {
        String URL="http://sachtechsolution.com/eagle360/login.php?email="+EMAIL+"&password="+PASSWORD;
        JsonObjectRequest request=new JsonObjectRequest(URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                dialog.dismiss();
                try {
                    STATUS=object.getString("status");
                    Log.e(">>>",STATUS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(">>>","error"+error.toString());
            }
        });
        queue.add(request);
        queue.addRequestFinishedListener(this);
    }
  /*  public  void getPassword()
    {
        String URL="http://sachtechsolution.com/eagle360/forgotPassword.php?type=forgetPassword&user_email="+EMAIL;
        JsonObjectRequest request=new JsonObjectRequest(URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                dialog.dismiss();
                try {
                    STATUS=object.getString("status");
                    Log.e(">>>",STATUS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(">>>","error"+error.toString());
            }
        });
        queue.add(request);
        queue.addRequestFinishedListener(this);
    }*/

    @Override
    public void onRequestFinished(Request<Object> request) {
        if(STATUS.equals("ok"))
        {
            Log.e(">>>","innnnnnsssssss");
            Toast.makeText(this,"success", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,STATUS, Toast.LENGTH_SHORT).show();
        }
       /* if(STATUS.equals("done"))
        {
            Log.e(">>>","innnnnnsssssss");
            Toast.makeText(this,"password sent on email", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,STATUS, Toast.LENGTH_SHORT).show();
        }*/
    }


}