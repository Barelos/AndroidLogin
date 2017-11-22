package com.example.barel.loginapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private final int LOGIN = 0;
    private final int SIGN = 1;
    private final int CHANGE = 2;

    private int port = 2001;
    private String ip = "10.0.0.2";

    private static String[] requests = {"login", "sign", "change"};

    private EditText username;
    private EditText password;
    private CheckBox signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        signIn = (CheckBox) findViewById((R.id.checkBox));
    }

    static String makeMessage(String username, String password, int req){
        return "1234-" + requests[req] + "-" + username + "-" + password;
    }

    public void login(View view){
        int req = signIn.isChecked() ? SIGN : LOGIN;
        String msg = makeMessage(username.getText().toString(), password.getText().toString(), req);
        try {
            Client client = new Client(ip, port, msg, this);
            client.execute();
        } catch (Exception e) {
            Log.d("DEBUG","MAIN: " + e.getMessage());
        }
    }

    public void info(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.host_ip, null);
        final EditText hostText =  v.findViewById(R.id.editText3);
        final EditText portText =  v.findViewById(R.id.editText4);
        hostText.setText(ip);
        portText.setText(Integer.toString(port));
        builder
                .setView(v)
                .setPositiveButton(R.string.info_pos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ip = hostText.getText().toString();
                        port = Integer.parseInt(portText.getText().toString());
                        Log.d("DEBUG", ip + " : " + Integer.toString(port));
                    }
                })
                .setNegativeButton(R.string.info_neg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("DEBUG", ip + " : " + Integer.toString(port));
                    }
                })
                .show();
    }
}
