package me.jkz.permissions.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.jkz.permissions.PermissionsUtils;
import me.jkz.permissions.R;

public class MainActivity extends AppCompatActivity {

    private TextView txtMessage;
    private Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMessage = (TextView) findViewById(R.id.message);
        btnRequest = (Button) findViewById(R.id.request);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsUtils.checkAndRequestPermissions(MainActivity.this);
            }
        });

        // Request permissions
        boolean isNeeded = PermissionsUtils.checkAndRequestPermissions(this);
        if (isNeeded) {
            btnRequest.setVisibility(View.VISIBLE);
            txtMessage.setText("On request...");
        } else {
            btnRequest.setVisibility(View.GONE);
            txtMessage.setText("Granted.");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGranted = PermissionsUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((isGranted)) {
            txtMessage.setText("Granted!");
        } else {
            txtMessage.setText("Denied!");
        }
    }
}
