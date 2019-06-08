package com.example.videodownloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadButton = (Button) findViewById(R.id.downloadVideo);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "hello";
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                Intent downloadVideoIntent = new Intent(MainActivity.this, CreateDownloadUrl.class);
                startActivity(downloadVideoIntent);
            }
        });
    }
}
