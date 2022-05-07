package com.example.accessibilityservicetest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    MaterialButton btnAccessibility;
    EditText contact;
    EditText msg;
    MaterialButton btnSend;
    MaterialButton btnReset;

    public static final String TAG = "mainTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAccessibility = findViewById(R.id.btnAccess);
        btnSend = findViewById(R.id.btnSend);
        btnReset = findViewById(R.id.btnReset);
        msg = findViewById(R.id.edtMsg);
        contact = findViewById(R.id.edtContact);

        if(!checkAccessibilityPermission()){
            Toast.makeText(MainActivity.this, "activate the service", Toast.LENGTH_SHORT).show();
        }

        btnAccessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "btn Accessibility clicked");
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactConfig.msg = "";
                ContactConfig.contact = "";
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "btn Send clicked");
                ContactConfig.contact = contact.getText().toString();
                ContactConfig.msg = msg.getText().toString();
                if( !(ContactConfig.contact.equals("")) && !(ContactConfig.msg.equals("")) ) {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    startActivity(launchIntent);
                } else {
                    Toast.makeText(MainActivity.this, "enter contact and message...", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public boolean checkAccessibilityPermission () {
        int accessEnabled = 0;
        try {
            accessEnabled = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (accessEnabled == 0) {
            // if not construct intent to request permission
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // request permission via start activity for result
            startActivity(intent);
            return false;
        } else {
            return true;
        }
    }

}