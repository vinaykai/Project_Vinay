package com.vkai.project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.VideoView;

public class SportActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sport);

        String vidAddress = "http://r13---sn-q4f7snes.googlevideo.com/videoplayback?expire=1460251759&sver=3&initcwndbps=1326250&dur=0.000&ms=au&mv=m&mt=1460230078&ip=2a03%3A8180%3A1001%3A16a%3A%3A8ee1&fexp=9407610%2C9408210%2C9408522%2C9416074%2C9416126%2C9416891%2C9419451%2C9420452%2C9422342%2C9422596%2C9423662%2C9426926%2C9427902%2C9428398%2C9429149%2C9429165%2C9429585%2C9432363%2C9433301%2C9433424%2C9433858%2C9433999&ipbits=0&pl=40&id=o-AN-Rr-a0YYjrl16DMFUy_5-KNo3opRkyIPQJ9aJDrukE&mime=video%2Fwebm&mn=sn-q4f7snes&mm=31&itag=43&lmt=1298939416885132&key=yt6&ratebypass=yes&upn=SBJE1jowfQs&signature=9368BA5CE11F045565195FD156EAD5178664F51D.1B6B0B243F7224C8E75FF140E57C0B5F56ABA58E&nh=IgpwcjA1LmRmdzA2KgkxMjcuMC4wLjE&source=youtube&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&title=How+to+Do+a+Sit+Up";
        Uri uri = Uri.parse(vidAddress);

        VideoView mVideoView  = (VideoView)findViewById(R.id.myVideoView);
        mVideoView.setMediaController(new android.widget.MediaController(this));
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();

        Button back=(Button)findViewById(R.id.backButton);
        back.setOnClickListener(
                new AdapterView.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent b=new Intent (SportActivity.this,VideoActivity.class);
                        startActivity(b);
                    }
                }
        );
    }
}
