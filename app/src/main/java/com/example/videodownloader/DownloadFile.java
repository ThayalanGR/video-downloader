package com.example.videodownloader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class DownloadFile extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadfile);
        final TextView tv = (TextView) findViewById(R.id.downloadurltext);
        try {
            final String downloadUrl = getIntent().getExtras().getString("downloadUrl");
            tv.setText(downloadUrl);
            if (isConnectingToInternet() && isSDCardPresent()) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            downloadHandler(downloadUrl, tv);
                        } catch (Exception e) {

                            e.printStackTrace();
                            tv.setText(e.toString());
//                            Toast.makeText(DownloadFile.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                thread.start();
            } else {
                Toast.makeText(this, "sd card not availabel", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //Check if internet is present or not
    private boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    //Check If SD Card is present or not method
    private boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    private void downloadHandler(String fileUrl, TextView tv) {
        String downloadUrl = fileUrl;
        Log.d("download", "downloadHandler: " + downloadUrl);
        tv.setText("download handler");

        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();

            File apkStorage = new File(
                    Environment.getExternalStorageDirectory() + "/"
                            + "VideoDownloader");

            if (!apkStorage.exists()) {
                apkStorage.mkdir();
                Log.e("download", "Directory Created.");
            }
            String downloadFileName = getSaltString() + ".mp4";
            //Create file name by picking download file name from URL

            File outputFile = new File(apkStorage, downloadFileName);

            if (!outputFile.exists()) {

                outputFile.createNewFile();
                Log.e("downloadFile", "File Created");


            }


            FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location
            InputStream is = c.getInputStream();//Get InputStream for connection
            byte[] buffer = new byte[1024];//Set buffer type
            int len1 = 0;//init length
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);//Write new file
            }
            fos.close();
            is.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            tv.setText(ex.toString());
        }

    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }


}
