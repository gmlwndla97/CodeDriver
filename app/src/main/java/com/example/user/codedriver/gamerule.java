package com.example.user.codedriver;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class gamerule extends AppCompatActivity {
    int page=0;
    float pressedX;
    LinearLayout mLinear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamerule);
        mLinear = (LinearLayout)findViewById(R.id.mLinear);

        mLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float distance=0;
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        pressedX = motionEvent.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        distance= pressedX -motionEvent.getX();
                        break;
                }
                if (Math.abs(distance) < 100) {
                    return false;
                }
                if (distance > 0) {
// 손가락을 왼쪽으로 움직였으면 오른쪽 화면이 나타나야 한다.
                    if(page<4)page++;
                } else {

// 손가락을 오른쪽으로 움직였으면 왼쪽 화면이 나타나야 한다.
                    if(page>0)page--;

                }
                setBackground(page,mLinear);


                return true;
            }
        });


//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cnt++;
//                imageView.setImageResource(R.drawable.switchexp);
//            }
//        });
//        m_dataList = new ArrayList<>();
//        m_dataList.add("0");
//        m_dataList.add("1");
//        m_dataList.add("2");
//        m_dataList.add("3");
//        m_dataList.add("4");
//        SwipeStack swipeStack = (SwipeStack)findViewById(R.id.swipeStack);
//        swipeStack.setAdapter(new SwipeStackAdapter(m_dataList));
    }
    public void setBackground(int page,LinearLayout mLinear){
        switch(page){
            case 0:
                mLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.ruleexp)));
                break;
            case 1:
                mLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.ifexp)));
                break;
            case 2:
                mLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.switchexp)));
                break;
            case 3:
                mLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.forexp)));
                break;
            case 4:
                mLinear.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.whileexp)));
                break;

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLinear.setBackground(null);
        System.gc();

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


//    class SwipeStackAdapter extends BaseAdapter{
//        List<String> m_data;
//
//
//        public SwipeStackAdapter(List<String> m_data) {
//            this.m_data = m_data;
//        }
//
//        @Override
//        public int getCount() {
//            return m_data.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return m_data.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            convertView = getLayoutInflater().inflate(R.layout.cardview, parent, false);
//            ImageView iv = convertView.findViewById(R.id.img);
//            switch (position){
//                case 0:
//                    iv.setImageResource(R.drawable.ruleexp);
//                    break;
//                case 1:
//                    iv.setImageResource(R.drawable.ifexp);
//                    break;
//                case 2:
//                    iv.setImageResource(R.drawable.forexp);
//                    break;
//                case 3:
//                    iv.setImageResource(R.drawable.switchexp);
//                    break;
//                case 4:
//                    iv.setImageResource(R.drawable.whileexp);
//                    break;
//            }
//
//            return convertView;
//        }
//
//    }

}
