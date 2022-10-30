package com.example.receiverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String MESSAGE_INTERNAL_ACTION = "send_message_internal";

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("send_message_internal")){
                String str = intent.getStringExtra("message");
                Log.i(TAG, "Mensaje interno: " + str);
            } else if (intent.getAction().equals("send_message_external")) {
                String str = intent.getStringExtra("message");
                Log.i(TAG, "Mensaje externo: " +str);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyReceiver myReceiverInternal = new MyReceiver();
        MyReceiver myReceiverExternal = new MyReceiver();

        IntentFilter myIntentFilterInternal = new IntentFilter("send_message_internal");
        IntentFilter myIntentFilterExternal = new IntentFilter("send_message_external");
        registerReceiver(myReceiverInternal, myIntentFilterInternal);
        registerReceiver(myReceiverExternal, myIntentFilterExternal);

        EditText etMessage = findViewById(R.id.etMessage);
        Button btnSender = findViewById(R.id.btnSender);

        btnSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();
                Intent intent = new Intent(MESSAGE_INTERNAL_ACTION);
                intent.putExtra("message", message);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent);
            }
        });
    }
}