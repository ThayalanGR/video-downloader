package com.example.videodownloader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.TextView;
import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class CreateDownloadUrl extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createdownloadurl);
        String youtubeLink = "https://www.youtube.com/watch?v=668nUCeBHyY";


        @SuppressLint("StaticFieldLeak") YouTubeUriExtractor ytEx = new YouTubeUriExtractor(this) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if (ytFiles != null) {
                    int itag = 22;
                    // Here you can get download url
                    String downloadUrl = ytFiles.get(itag).getUrl();
                    TextView tv = (TextView) findViewById(R.id.centertext);
                    tv.setText(downloadUrl);
                    Intent downloadFileIntent = new Intent(CreateDownloadUrl.this, DownloadFile.class);
                    downloadFileIntent.putExtra("downloadUrl", downloadUrl);
                    startActivity(downloadFileIntent);
                }
            }
        };

        ytEx.execute(youtubeLink);

    }


}
