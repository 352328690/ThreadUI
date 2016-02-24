package threadtest.com.example.ui;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {

    private static int a = 1;

    private Button button;

    private TextView textView;

    MyHandler handler = new MyHandler(this);//实例化类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.text);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = a;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    static class MyHandler extends Handler{
        WeakReference<MainActivity> mActivity;//此处泛型填写Myhandler所在的外部类，也就是所在的Activity

        MyHandler(MainActivity mainActivity){
            mActivity = new WeakReference<>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {

            MainActivity activity = mActivity.get();

            switch (msg.what){
                case 1:
                    activity.textView.setText("我开始慌了！");
                    a = 2;
                    break;
                case 2:
                    activity.textView.setText("我开始困了！");
                    a = 1;
                    break;
                default:
                    break;
            }

        }
    }
}
