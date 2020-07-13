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

public class gasstation extends AppCompatActivity {
    int[] answer = {-1,-1};
    int pointer;
    int fuel; //현재 그림속 연료
    ImageView[] ansImg;
    LinearLayout thisLayout;
    int stage=1;
    LinearLayout backLinear;
    SoundPool sound;
    int soundId1,soundId2,soundId3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasstation);

        fuel = (int)(Math.random()*1000) % 5;
        ImageView queGas= (ImageView)findViewById(R.id.queGas);
        switch (fuel) {
            case 0:
                queGas.setImageResource(R.drawable.gas0);
                break;
            case 1:
                queGas.setImageResource(R.drawable.gas1);
                break;
            case 2:
                queGas.setImageResource(R.drawable.gas2);
                break;
            case 3:
                queGas.setImageResource(R.drawable.gas3);
                break;
            case 4:
                queGas.setImageResource(R.drawable.gas4);
                break;
        }
        init();
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

    private void init(){
        sound =new SoundPool(10, AudioManager.STREAM_MUSIC,0);//사운드풀 객체 생성

        pointer = 0;
        ansImg = new ImageView[2];
        ansImg[0] = (ImageView)findViewById(R.id.input1);
        ansImg[1] = (ImageView)findViewById(R.id.input2);
        backLinear = (LinearLayout)findViewById(R.id.gas);

        Intent intent = getIntent();
        stage =intent.getIntExtra("stage",1);
        setBackground(stage,backLinear);

    }
    public void setBackground(int stage,LinearLayout backLinear){
        switch(stage){
            case 1:
//                backLinear.setBackgroundResource(R.drawable.problem_back1);
                backLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.problem_back1)));

                break;
            case 2:
//                backLinear.setBackgroundResource(R.drawable.problem_back2);
                backLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.problem_back2)));

                break;
            case 3:
//                backLinear.setBackgroundResource(R.drawable.problem_back3);
                backLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.problem_back3)));

                break;
        }
    }

    @Override
    public void onDestroy() {
        recycleView(findViewById(R.id.gas));
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



    public void numbtnClick(View view){
        play(1);

        if(pointer < 2) {
            switch (view.getId()) {
                case R.id.onebtn:
                    ansImg[pointer].setImageResource(R.drawable.one);
                    answer[pointer] = 1;
                    break;
                case R.id.twobtn:
                    ansImg[pointer].setImageResource(R.drawable.two);
                    answer[pointer] = 2;
                    break;
                case R.id.threebtn:
                    ansImg[pointer].setImageResource(R.drawable.three);
                    answer[pointer] = 3;
                    break;
                case R.id.fourbtn:
                    ansImg[pointer].setImageResource(R.drawable.four);
                    answer[pointer] = 4;
                    break;
                case R.id.fivebtn:
                    ansImg[pointer].setImageResource(R.drawable.five);
                    answer[pointer] = 5;
                    break;
            }
            pointer++;
            if(pointer == 1){
                ansImg[pointer].setImageResource(R.drawable.question);
            }
            if(pointer == 3){
                pointer = 2;
            }
        }

    }

    public void ccbtnClick(View view) {
        play(1);

        switch (view.getId()) {
            case R.id.back:
                while (pointer >= 1) {
                    pointer--;
                    if(pointer == 0){
                        ansImg[1].setImageResource(android.R.color.transparent);
                    }
                    ansImg[pointer].setImageResource(R.drawable.question);
                    answer[pointer] = -1;
                    if(pointer == -1){
                        pointer = 0;
                    }
                    break;
                }
                break;
            case R.id.submit:
                if ((5-fuel) == (answer[0] * answer[1])) {
                    play(2);

                    OX(1);
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();//내 할일 끝~! 이란 뜻

                } else {
                    play(3);
                    OX(2);
                    ansImg[0].setImageResource(R.drawable.question);
                    answer[0] = -1;
                    ansImg[1].setImageResource(android.R.color.transparent);
                    answer[1] = -1;
                    pointer = 0;
                }
                break;
        }
    }
}


