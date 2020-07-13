package com.example.user.codedriver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class trafficlight extends AppCompatActivity {
    ImageView[] ansList;
    int row=0;
    char[] ans={'0','0','0','0'};
    String ans1="1324";
    String ans2="2413";
    int stage=1;
    LinearLayout backLinear;
    SoundPool sound;
    int soundId1,soundId2,soundId3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trafficlight);
        init();

    }
    @Override
    public void onDestroy() {
        recycleView(findViewById(R.id.traf));
        super.onDestroy();
    }
    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();
            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackground(null);
            }
        }
    }
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("돌아가기")
                .setMessage("메뉴로 이동하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = getIntent();
                        setResult(RESULT_CANCELED,intent);
                        finish();
                    }
                })
                .setNegativeButton("아니오", null).show();
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
            case 3:
                soundId3= sound.load(this,R.raw.failsound,1);
                sound.setOnLoadCompleteListener (new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int soundId, int status) {
                        sound.play(soundId3,3f,3f,0,0,1f);
                    }
                });
                break;
        }

    }
    Toast toast;
    ImageView toastImg;
    private void OX(int num) {
        //1은 O, 2는 X
        toast= Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_SHORT);
        toastImg=new ImageView(getApplicationContext());
        switch(num){
            case 1:
                toastImg.setImageResource(R.drawable.correct);
                break;
            case 2:
                toastImg.setImageResource(R.drawable.wrong);
                break;
        }
        toast.setView(toastImg);
        toast.show();
    }


    private void init() {
        sound =new SoundPool(10, AudioManager.STREAM_MUSIC,0);//사운드풀 객체 생성

        ansList = new ImageView[4];
        ansList[0] = (ImageView)findViewById(R.id.ans1);
        ansList[1] = (ImageView)findViewById(R.id.ans2);
        ansList[2] = (ImageView)findViewById(R.id.ans3);
        ansList[3] = (ImageView)findViewById(R.id.ans4);
        backLinear = (LinearLayout)findViewById(R.id.traf);

        Intent intent = getIntent();
        stage =intent.getIntExtra("stage",1);
        switch(stage){
            case 1:
                backLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.tproblem_back1)));
                break;
            case 2:
                backLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.tproblem_back2)));
                break;
            case 3:
                backLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.tproblem_back3)));
                break;
        }

    }

    public void subClick(View view) {
        play(1);

        switch (view.getId()) {
            case R.id.back:
                if (row != 0) {
                    if(row<4){
                        ansList[row].setVisibility(View.INVISIBLE);
                    }
                    row--;
                    ansList[row].setImageResource(R.drawable.question);
                    ans[row] = 0;
                }
                break;
            case R.id.submit:
                if (String.valueOf(ans).equals(ans1) || String.valueOf(ans).equals(ans2))  {
                    play(2);

                    OX(1);
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();//내 할일 끝~! 이란 뜻
                }else{
                    play(3);
                    OX(2);
                }
                break;
        }
    }

    public void btnClick(View view) {
        play(1);

        if (row != 4) {
            switch (view.getId()) {
                case R.id.btngreen:
                    ansList[row].setImageResource(R.drawable.btngreen);
                    ans[row] = '1';
                    break;
                case R.id.btnred:
                    ansList[row].setImageResource(R.drawable.btnred);
                    ans[row] = '2';
                    break;
                case R.id.go_trafficlight:
                    ansList[row].setImageResource(R.drawable.go_trafficlight);
                    ans[row] = '3';
                    break;
                case R.id.stop:
                    ansList[row].setImageResource(R.drawable.stop);
                    ans[row] = '4';
                    break;
            }
            if (row < 4) {
                ansList[row].setVisibility(View.VISIBLE);
                row++;
                if(row<4){
                    ansList[row].setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
