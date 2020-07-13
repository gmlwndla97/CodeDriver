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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class laststop extends AppCompatActivity {
    String answer="";
    int stage=1;
    LinearLayout backLinear;

    private Runnable mRunnable;
    private Handler mHandler;
    SoundPool sound;
    int soundId1,soundId2,soundId3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laststop);
        init();
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

        Intent intent = getIntent();
        stage =intent.getIntExtra("stage",1);
        backLinear = (LinearLayout)findViewById(R.id.end);
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
        recycleView(findViewById(R.id.end));
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



    public void onClicked(View args){
        play(1);

        ImageView input1=(ImageView)findViewById(R.id.input1);
        if(((ImageButton)args).getId()==R.id.right_laststop){
            input1.setImageResource(R.drawable.right_laststop);
            answer="right";
        }
        else if(((ImageButton)args).getId()==R.id.left_laststop){
            input1.setImageResource(R.drawable.left_laststop);
            answer="left";

        }
        else if(((ImageButton)args).getId()== R.id.same){
            input1.setImageResource(R.drawable.same);
            answer="same";
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



    public void onBackClicked(View v){
        play(1);

        ImageView input1=(ImageView)findViewById(R.id.input1);
        input1.setImageResource(R.drawable.question);
    }

    public void onSubmitClicked(View args){
        play(1);

        Intent intent = getIntent();
        if(answer.equals("left"))
        {
            play(2);

//            Toast.makeText(this, "정답", Toast.LENGTH_SHORT).show();
//            System.out.println("정답");
            toast= Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_SHORT);
            toastImg=new ImageView(getApplicationContext());
            toastImg.setImageResource(R.drawable.complete);
            toast.setView(toastImg);
            toast.setGravity(Gravity.CENTER,50,50);
            toast.setMargin(1000, 1000);
            toast.show();
            setResult(RESULT_CANCELED,intent);
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            };
            mHandler=new Handler();
            mHandler.postDelayed(mRunnable,2000);

        }
        else {
            play(3);
            OX(2);
        }

    }
}