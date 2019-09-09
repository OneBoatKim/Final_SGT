package com.example.menulist_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Tumbler_greenseed_main extends AppCompatActivity {

    int greenseed;
    TextView nickname;
    TextView seedgrade;
    TextView seed;
    TextView seed2;
    TextView stat1;
    TextView stat2;
    TextView stat3;
    TextView stat4;
    MyApplication myApplication;
    String tname;
    String tid;
    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;
    ProgressBar progressBar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler_greenseed_main);
        myApplication = (MyApplication)getApplication();

        nickname = (TextView)findViewById(R.id.tumbler_greenseed_txt_nickname); // 닉네임 대신에 텀블러 이름으로 함
        seedgrade = (TextView)findViewById(R.id.tumbler_greenseed_txt_seedgrade);
        seed = (TextView)findViewById(R.id.tumbler_greenseed_txt_seed);
        seed2 = (TextView)findViewById(R.id.tumbler_greenseed_txt_seed2);
        progressBar1 = (ProgressBar)findViewById(R.id.tumbler_greenseed_progress_bar_1);
        progressBar2 = (ProgressBar)findViewById(R.id.tumbler_greenseed_progress_bar_2);
        progressBar3 = (ProgressBar)findViewById(R.id.tumbler_greenseed_progress_bar_3);
        progressBar4 = (ProgressBar)findViewById(R.id.tumbler_greenseed_progress_bar_4);
        stat1 = (TextView)findViewById(R.id.tumbler_greenseed_txt_stat1);
        stat2 = (TextView)findViewById(R.id.tumbler_greenseed_txt_stat2);
        stat3 = (TextView)findViewById(R.id.tumbler_greenseed_txt_stat3);
        stat4 = (TextView)findViewById(R.id.tumbler_greenseed_txt_stat4);




        Intent intent = getIntent();
        tid = intent.getExtras().getString("tumbler_id");
        tname = intent.getExtras().getString("tumbler_name");

        nickname.setText(tname);

        String url = "http://"+myApplication.getServerUrl()+"/greenTumblerServer/mobile/tumbler/greenSeed/" + tid; // 텀블러 아이디 인텐트로 훔쳐와야댐 그때 유저 닉네임이나 텀블러 이름 가져오면 더 좋을듯
        /*ContentValues contentValues = new ContentValues();
        contentValues.put("tumblerId","1");*/

        Tumbler_grennseedTask tumbler_grennseedTask = new Tumbler_grennseedTask(url,null);
        tumbler_grennseedTask.execute();
    }

    public void go_back(View v){
        finish();
    }

    public void go_2(View v){
        Intent intent = new Intent(Tumbler_greenseed_main.this,Tumbler_greenseed_2.class);
        startActivity(intent);
    }


    public class Tumbler_grennseedTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        Tumbler_grennseedTask(String url, ContentValues values){
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
            greenseed = Integer.parseInt(result);
            seedgrade.setTypeface(Typeface.createFromAsset(getAssets(),"RIXGOL.TTF"));
            seed.setText(String.valueOf(greenseed));
            seed2.setText(String.valueOf(greenseed));

            progressBar1.setProgress(greenseed);
            progressBar2.setProgress(greenseed);
            progressBar3.setProgress(greenseed);
            progressBar4.setProgress(greenseed);

            if(greenseed < 20){
                seedgrade.setText("새싹");
                stat1.setText(String.valueOf(greenseed));

            }else if(greenseed >=20 && greenseed < 30){
                seedgrade.setText("묘목");
                stat1.setText("20");
                stat2.setText(String.valueOf(greenseed));

            }else if(greenseed >=30 && greenseed < 50){
                seedgrade.setText("나무");
                stat1.setText("20");
                stat2.setText("30");
                stat3.setText(String.valueOf(greenseed));


            }else if(greenseed >=50 && greenseed < 150){
                seedgrade.setText("숲");
                stat1.setText("20");
                stat2.setText("30");
                stat3.setText("50");
                stat4.setText(String.valueOf(greenseed));


            }else{
                seedgrade.setText("숲");
                stat1.setText("20");
                stat2.setText("30");
                stat3.setText("50");
                stat4.setText(String.valueOf(greenseed));

            }

        }
    }

}
