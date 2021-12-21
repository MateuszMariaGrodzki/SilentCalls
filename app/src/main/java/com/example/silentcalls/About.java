package com.example.silentcalls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class About extends AppCompatActivity {

    private ImageView imGithub;
    private ImageView imLinkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initFields();
        imGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MateuszMariaGrodzki/SilentCalls"));
                startActivity(githubIntent);
            }
        });
        imLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkedinIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://linkedin.com/in/mateuszgrodzki"));
                startActivity(linkedinIntent);
            }
        });
    }

    private void initFields(){
        imGithub = findViewById(R.id.im_github);
        imLinkedin = findViewById(R.id.im_linkedin);
    }
}