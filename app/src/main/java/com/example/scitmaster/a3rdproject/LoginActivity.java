package com.example.scitmaster.a3rdproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText editId, editPwd;
    String id_data, pw_data;

    Button btnLogin;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        textView = findViewById(R.id.textView);
    }//onCreate

    public void btnLogin(View view) {
        editId = findViewById(R.id.editId);
        editPwd = findViewById(R.id.editPwd);

        id_data = editId.getText().toString();
        pw_data = editPwd.getText().toString();

        Log.v("Result : ", "ID: "+id_data + ", PW : " + pw_data);




        SendThread thread = new SendThread();
        thread.start();
    }

    public void nonMember(View view) {
        Toast.makeText(this, "회원가입 해주세요", Toast.LENGTH_SHORT).show();
    }

    class  SendThread extends Thread{
        //각자의 IP 주소
        String addr = "http://10.10.1.166:8888/test/app_login?id=" + id_data + "&pwd=" + pw_data;

        @Override
        public void run() {
            try {
                // URL 연결 및 데이터 전송을 위한 기본 세팅
                Log.v("Test", "1");
                URL url = new URL(addr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("dataType", "json");
                urlConnection.setConnectTimeout(10000);
                Log.v("Test", "2");
                //Client 로부터의 데이터를 stringBuilder 로 받는다
                StringBuilder stringBuilder = new StringBuilder();

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                    Log.v("Test", "3");
                    int dataFromClient;
                    while ((dataFromClient = inputStreamReader.read())!= -1) {
                        stringBuilder.append((char) dataFromClient);
                    }//while

                    Log.v("AfterDB", "data: "+stringBuilder);

                    if (stringBuilder.toString().equals("Success")) {
                        Log.v("AfterDB", "Success");
                    }else if (stringBuilder.toString().equals("Fail")) {
                        Log.v("AfterDB", "Fail");
                    }//inner If
                }//outer If

//tetst
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//run
    }//sendThread
}
