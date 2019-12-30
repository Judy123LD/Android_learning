package com.example.forceoffline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private Button login_button;
    private EditText edit_account;
    private EditText edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_account=(EditText)findViewById(R.id.account);
        edit_password=(EditText)findViewById(R.id.password);

        login_button=(Button)findViewById(R.id.login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=edit_account.getText().toString();
                String password=edit_password.getText().toString();
                Log.i(TAG, "account: "+account);
                Log.i(TAG, "password: "+password);
                if(account.equals("admin")&&password.equals("123")){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"the account or passeord is invaild",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
