package com.example.user.codedriver;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    final int RULE = 123;
    private long pressedTime;
    MediaPlayer bgm;
    SoundPool sound;
    int soundId1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgm = new MediaPlayer();
        bgm.stop();
        bgm = MediaPlayer.create(this,R.raw.bgm);
        bgm.start();
        bgm.setLooping(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RULE){
            if(resultCode==RESULT_OK){
            }
        }
    }


    public void btnClick(View view) {
        sound=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        soundId1= sound.load(this,R.raw.clicksound,1);
        sound.setOnLoadCompleteListener (new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int soundId, int status) {
                sound.play(soundId1,3f,3f,0,0,1f);
            }
        });
        Intent intent;
        switch(view.getId()){
            case R.id.btn1:
                intent = new Intent(this,menu.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                intent = new Intent(this, com.example.user.codedriver.gamerule.class);
                startActivityForResult(intent,RULE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if ( pressedTime == 0 ) {
            Toast.makeText(MainActivity.this, "한번 더 누르면 종료됩니다", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                pressedTime = 0;
            }
            else {
               // finishAffinity();
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        }
    }

}
