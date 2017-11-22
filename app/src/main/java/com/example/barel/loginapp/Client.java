package com.example.barel.loginapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Client extends AsyncTask<Void, Void, Void> {
    private String host;
    private int port;
    private String msg;
    private String line;
    private Context context;

    public Client(String host, int port, String msg, Context context){
        this.host = host;
        this.port = port;
        this.msg = msg;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        this.line = "Failed";
        try{
            Log.d("DEBUG", "Trying to connect...");
            // open socket
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(this.host, this.port), 5000);
            // send msg
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.write(msg.getBytes());
            // receive reply
            DataInputStream is = new DataInputStream(socket.getInputStream());
            while(is.available() == 0){} // busy waiting
            byte[] bytes = new byte[256];
            int n = is.read(bytes);
            this. line = new String(Arrays.copyOfRange(bytes, 0, n));
        }catch(Exception e){
            // make toast
            Log.d("DEBUG", "CLIENT: " + e.getMessage());
        }
        // show result
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast toast = Toast.makeText(this.context, this.line, Toast.LENGTH_LONG);
        toast.show();
    }
}
