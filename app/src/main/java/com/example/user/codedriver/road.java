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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class road extends AppCompatActivity {
    private Runnable mRunnable;
    private Handler mHandler;
    SoundPool sound;
    int soundId1,soundId2,soundId3;



    ImageView[] ansList;
    final int LASTSTOP =1;
    final int ELSE=2;
    final int ansCnt=5;

    final String[] forestAns = {"22200","12200","32000",""};
    final String[] desertAns = {"22220","12122","32320","22200"};
    final String[] cityAns = {"12200","22200","21232","21200"};

    String[] answer={"","","",""};
    String question="";
    String a = "@drawable/back_";
    String d="";
    String ad="";
    String pac = "";
    Intent intent;







    int row=0;
    int state=1;
    char[] ans = {'0','0','0','0','0'};


    int stage;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road);

        init();
    }
    public void onDestroy(){
        recycleView(findViewById(R.id.linearlayout));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case LASTSTOP:

            case ELSE:
                if(resultCode==RESULT_CANCELED){
                    finish();
                }
                break;
        }


    }


    private void init() {
        sound =new SoundPool(10, AudioManager.STREAM_MUSIC,0);//사운드풀 객체 생성
        ansList = new ImageView[ansCnt];
        ansList[0] = (ImageView)findViewById(R.id.iv1);
        ansList[1] = (ImageView)findViewById(R.id.iv2);
        ansList[2] = (ImageView)findViewById(R.id.iv3);
        ansList[3] = (ImageView)findViewById(R.id.iv4);
        ansList[4] = (ImageView)findViewById(R.id.iv5);
        intent = getIntent();
        pac = getPackageName();
        stage =intent.getIntExtra("stage",1);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        switch(stage){
            case 1:
                linearLayout.setBackgroundResource(R.drawable.back_forest1);
                answer = forestAns;
                question="124";
                d="forest";
                break;
            case 2:
                linearLayout.setBackgroundResource(R.drawable.back_desert1);
                answer = desertAns;
                question="1324";
                d="desert";
                break;
            case 3:
                linearLayout.setBackgroundResource(R.drawable.back_city1);
                answer=cityAns;
                question="1324";
                d="city";
                break;
        }

    }

    public void arrowClick(View view) {
//        sound.play(soundId1,3f,3f,0,0,1f);
        play(1);

        if(row!=ansCnt){
            switch(view.getId()){
                case R.id.btnleft:
                    ansList[row].setImageResource(R.drawable.turnleft);
                    ans[row]='1';
                    break;
                case R.id.btngo:
                    ansList[row].setImageResource(R.drawable.go);
                    ans[row]='2';
                    break;
                case R.id.btnright:
                    ansList[row].setImageResource(R.drawable.turnright);
                    ans[row]='3';
                    break;

            }

            if(row<ansCnt){
                ansList[row].setVisibility(View.VISIBLE);
                row++;
            }
        }
    }

    public void subClick(View view){
        play(1);




        switch (view.getId()) {
            case R.id.back:
//                sound.play(soundId1,3f,3f,0,0,1f);

                if (row != 0) {
                    row--;
//                    ansList[row].setImageResource(R.drawable.arrow);
                    ansList[row].setVisibility(View.INVISIBLE);
                    ans[row] = '0';
                }
                break;
            case R.id.submit:
                if (String.valueOf(ans).equals(answer[state-1]))  {
//                        Toast.makeText(this, "정답입니다.", Toast.LENGTH_SHORT).show();
                    //목적지 도착 토스트
                    toast= Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_SHORT);
                    toastImg=new ImageView(getApplicationContext());
                    toastImg.setImageResource(R.drawable.arrival);
                    toast.setView(toastImg);
                    toast.setGravity(Gravity.CENTER,50,50);
                    toast.setMargin(1000, 1000);
                    toast.show();
                    switch(question.charAt(state-1)){
                        case '1':
                            intent = new Intent(this, com.example.user.codedriver.busstop.class);
                            System.out.println("버정");
                            break;
                        case '2':
                            intent = new Intent(this,trafficlight.class);
                            System.out.println("신호등");
                            break;

                        case '3':
                            intent = new Intent(this, com.example.user.codedriver.gasstation.class);
                            System.out.println("주유소");
                            break;
                        case '4':
                            System.out.println("종착역");
                            intent = new Intent(this, com.example.user.codedriver.laststop.class);
                            break;
                    }
                    state++;
//                    sound.play(soundId2,3f,3f,0,0,1f);
                    play(2);
                    ad=a+d+state;
                    int resID = getResources().getIdentifier(ad,"drawable",pac);


                    if(linearLayout != null) {
                        Drawable bg = linearLayout.getBackground();
                        if(bg != null) {
                            bg.setCallback(null);
                            ((BitmapDrawable)bg).getBitmap().recycle();
                            linearLayout.setBackground(null);
                        }
                    }
                    linearLayout.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),resID)));
//                    linearLayout.setBackgroundResource(resID);//뒷배경 정하기
                    initial();//코딩창 비우기
//                    intent.putExtra("stage",stage);

//                        startActivityForResult(intent,ELSE);
                    mRunnable=new Runnable(){
                        @Override
                        public void run() {
                            intent.putExtra("stage",stage);
                            if(question.charAt(state-2)=='4'){
                                startActivityForResult(intent,LASTSTOP);
                            }else {
                                startActivityForResult(intent, ELSE);
                            }
                        }
                    };
                    mHandler=new Handler();
                    mHandler.postDelayed(mRunnable,1800);
                    intent.putExtra("stage",stage);//forest,desert,city맵정보 넘김


//                    startActivityForResult(intent,111);//제어문 문제 시작
                }else{
//                        Toast.makeText(this, "틀렸습니다.", Toast.LENGTH_SHORT).show();
                    OX(2);
                    play(3);
                }
                break;
        }
    }
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("돌아가기")
                .setMessage("메뉴로 이동하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = getIntent();
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                })
                .setNegativeButton("아니오", null).show();
    }



    public void initial(){
        while (row >0) {
            row--;
            ansList[row].setVisibility(View.INVISIBLE);
            ans[row] = '0';
        }
    }

}
