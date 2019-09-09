package com.example.menulist_test;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainPage2 extends AppCompatActivity {

    TextView txt_nickname;
    TextView txt_starcnt;
    TextView txt_tumbler_money;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication)getApplication();


        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            System.out.println(token);
            Log.e("exception","device token : "+token);
            //로그 서버 통신
            String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/main/updateFCM";
            ContentValues contentValues = new ContentValues();
            contentValues.put("account_id", myApplication.getAccountId());
            contentValues.put("fcm_token", token);
            TokenTask networkTask = new TokenTask(url,contentValues);
            networkTask.execute();
        } catch (Exception e) {
            Log.e("exception", e.toString());
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main_page);

        txt_nickname = (TextView)findViewById(R.id.main_txt_nickname);
        txt_nickname.setTypeface(Typeface.createFromAsset(getAssets(),"RIXGOL.TTF"));
        txt_starcnt = (TextView)findViewById(R.id.main_txt_star_cnt);
        txt_tumbler_money = (TextView)findViewById(R.id.main_txt_tumbler_money);

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/main/accounts/" + myApplication.getAccountId();
        MainPageTask networkTask = new MainPageTask(url,null);
        networkTask.execute();

        //////////////////////////////////////////

        /*Dialog dialog = new Dialog(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 없애기

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 테두리 지움

        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.mipmap.charge_img)); // 이미지 넣기

        setContentView(R.layout.pop_up_window);*/

        ////////////////////////////////////////////

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.pop_up_window);
        builder.setPositiveButton("닫기",null);
        builder.create().show();*/

        ////////////////////////////////////////////

        /*Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pop_up_window);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();*/
    }

    public void go_to_personal(View v){
        Intent intent = new Intent(MainPage2.this, Personal_list.class);
        startActivity(intent);
    }

    public void go_to_mainpage(View v){

    }

    public void go_to_tumbler(View v){
        Intent intent = new Intent(MainPage2.this, Tumbler_main.class);
        startActivity(intent);
    }


    public class MainPageTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        MainPageTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            try{
                JSONObject jsonObject = new JSONObject(result);
                String account = jsonObject.getString("account");
                JSONObject jsonaccount = new JSONObject(account);
                txt_nickname.setText(jsonaccount.getString("nickname"));
                txt_starcnt.setText(jsonaccount.getString("star_cnt"));
                String tumbler = jsonObject.getString("tumbler");
                JSONObject jsontumbler = new JSONObject(tumbler);
                txt_tumbler_money.setText(jsontumbler.getString("tumbler_Money")+"원");

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    public class TokenTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        TokenTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


        }
    }
}

