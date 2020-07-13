package com.example.user.codedriver;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.user.codedriver.R;
import com.example.user.codedriver.road;

public class menu extends AppCompatActivity {
    int stage=0;
    LinearLayout backLinear;
    SoundPool sound;
    int soundId1,soundId2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        backLinear = (LinearLayout)findViewById(R.id.backLinear);
        backLinear.setBackgroundResource(R.drawable.menu);
        sound =new SoundPool(10, AudioManager.STREAM_MUSIC,0);//사운드풀 객체 생성

    }
    private void play(int id) {
        switch(id){
            case 1:
                soundId1= sound.load(this,R.raw.clicksound,1);
                sound.setOnLoadCompleteListener (new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int soundId, int status) {
                        sound.play(soundId1,3f,3f,0,0,1f);
                    }
                });

                break;
            case 2:
                soundId2= sound.load(this,R.raw.clearsound,1);
                sound.setOnLoadCompleteListener (new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int soundId, int status) {
                        sound.play(soundId2,3f,3f,0,0,1f);
                    }
                });
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backLinear.setBackground(null);
        System.gc();

    }

    public void menuClick(View view) {
        play(1);
        Intent intent=new Intent(this, road.class);;
        switch(view.getId()){
            case R.id.btn1:
                stage=1;
                break;
            case R.id.btn2:
                stage=2;
                break;
            case R.id.btn3:
                stage=3;
                break;
        }
        intent.putExtra("stage",stage);
        startActivity(intent);
    }
}
