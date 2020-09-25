package com.ifanr.activitys;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.common.base.Strings;

public class MessageActivity extends AppCompatActivity {

    public static void start(String message, Context ctx) {
        if (ctx != null) {
            Intent intent = new Intent(ctx, MessageActivity.class);
            intent.putExtra(ARG_MESSAGE, message);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }
    }

    private static final String ARG_MESSAGE = "ARG_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("叮咚...你收到一条消息");
        }

        String message = getIntent().getStringExtra(ARG_MESSAGE);
        TextView messageTv = findViewById(R.id.message);
        messageTv.setText(message);
    }
}
