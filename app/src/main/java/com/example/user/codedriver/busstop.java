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

public class busstop extends AppCompatActivity {
    int count=0;
    String[] answer=new String[4];
    int rand;
    int stage=1;

    ImageView input1;
    ImageView input2;
    ImageView input3;
    ImageView input4;
    LinearLayout backLinear;
    SoundPool sound;
    int soundId1,soundId2,soundId3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busstop);
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

        LinearLayout queLinear = (LinearLayout)findViewById(R.id.queLinear);
        backLinear = (LinearLayout)findViewById(R.id.bus);

        input1=(ImageView)findViewById(R.id.input1);
        input2=(ImageView)findViewById(R.id.input2);
        input3=(ImageView)findViewById(R.id.input3);
        input4=(ImageView)findViewById(R.id.input4);

        //문제 1/ 문제 2 난수로 선택
        rand = (int)(Math.random()*1000)%2+1;//1,2나옴
        switch (rand){
            case 1:
                queLinear.setBackgroundResource(R.drawable.ifquestion);
                break;
            case 2:
                queLinear.setBackgroundResource(R.drawable.ifquestion2);
                break;
        }
        //stage정보 받기
        Intent intent = getIntent();
        stage =intent.getIntExtra("stage",1);
        //스테이지 정보에 따라 background 설정
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
        recycleView(findViewById(R.id.bus));
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



    public void onBackClicked(View view) {
        play(1);

        if(count==1){
            input1.setImageResource(R.drawable.question);
            input2.setVisibility(View.INVISIBLE);
            count=0;
        }
        else if(count==2){
            input2.setImageResource(R.drawable.question);
            input3.setVisibility(View.INVISIBLE);
            count=1;
        }
        else if(count==3){
            input3.setImageResource(R.drawable.question);
            input4.setVisibility(View.INVISIBLE);
            count=2;
        }
        else if(count==4){
            input4.setImageResource(R.drawable.question);
            count=3;
        }
        //    System.out.println(count);
    }

    public void onSubmitClicked(View v){ //정답 체크(태우는 경우)
        play(1);

        Intent intent = new Intent();
        switch(rand){
            case 1:
                if(answer[0]=="full" && answer[1]=="boarding" && answer[2]=="left" && answer[3]=="waiting"){
                    play(2);
                    OX(1);
                    setResult(RESULT_OK,intent);
                    finish();//내 할일 끝~! 이란 뜻
                }
                else{
                    play(3);
                    OX(2);
                }
                break;
            case 2:
                if(answer[0]=="full" && answer[1]=="boarding" && answer[2]=="right" && answer[3]=="waiting"){
                    OX(1);
                    play(2);
                    setResult(RESULT_OK,intent);
                    finish();//내 할일 끝~! 이란 뜻
                }
                else{
                    play(3);
                    OX(2);
                }

        }

    }



    public void onClicked(View view) {
        play(1);

        if(count==0){ //한번 누를 때마다 id구별 후 이미지 띄움
            if(view.getId()==R.id.full){
                input1.setImageResource(R.drawable.full);
                input1.setVisibility(View.VISIBLE);
                input2.setVisibility(View.VISIBLE);
                answer[0]="full";
                count++;
            }
            else if(view.getId()==R.id.boarding){
                input1.setImageResource(R.drawable.boarding);
                input1.setVisibility(View.VISIBLE);
                input2.setVisibility(View.VISIBLE);
                answer[0]="boarding";
                count++;
            }
            else if(view.getId()==R.id.waiting){
                input1.setImageResource(R.drawable.waiting);
                input1.setVisibility(View.VISIBLE);
                input2.setVisibility(View.VISIBLE);
                answer[0]="waiting";
                count++;
            }
        }
        else if(count==1){
            if(view.getId()==R.id.full){
                input2.setImageResource(R.drawable.full);
                input2.setVisibility(View.VISIBLE);
                input3.setVisibility(View.VISIBLE);
                answer[1]="full";
                count++;
            }
            else if(view.getId()==R.id.boarding){
                input2.setImageResource(R.drawable.boarding);
                input2.setVisibility(View.VISIBLE);
                input3.setVisibility(View.VISIBLE);
                answer[1]="boarding";
                count++;
            }
            else if(view.getId()==R.id.waiting){
                input2.setImageResource(R.drawable.waiting);
                input2.setVisibility(View.VISIBLE);
                input3.setVisibility(View.VISIBLE);
                answer[1]="waiting";
                count++;
            }
        }
        else if(count==2){
            if(view.getId()==R.id.left){
                input3.setImageResource(R.drawable.left);
                input3.setVisibility(View.VISIBLE);
                input4.setVisibility(View.VISIBLE);
                answer[2]="left";
                count++;
            }
            else if(view.getId()==R.id.right){
                input3.setImageResource(R.drawable.right);
                input3.setVisibility(View.VISIBLE);
                input4.setVisibility(View.VISIBLE);
                answer[2]="right";
                count++;
            }

        }
        else if(count==3){
            if(view.getId()==R.id.full){
                input4.setImageResource(R.drawable.full);
                input4.setVisibility(View.VISIBLE);
                answer[3]="full";
                count++;
            }
            else if(view.getId()== R.id.boarding){
                input4.setImageResource(R.drawable.boarding);
                input4.setVisibility(View.VISIBLE);
                answer[3]="boarding";
                count++;
            }
            else if(view.getId()==R.id.waiting){
                input4.setImageResource(R.drawable.waiting);
                input4.setVisibility(View.VISIBLE);
                answer[3]="waiting";
                count++;
            }

        }
        //     System.out.println(count);
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
}
