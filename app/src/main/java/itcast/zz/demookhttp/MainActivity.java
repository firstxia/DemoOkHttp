package itcast.zz.demookhttp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL_GET = "http://apis.juhe.cn/mobile/get?phone=13812345678&key=daf8fa858c330b22e342c882bcbac622";
    public static final String URL_POST = "http://apis.juhe.cn/mobile/get";
    private OkHttpClient okHttpClient;
    private Button btn_get;
    private Button btn_post;
    private TextView tv_ok;
    private TextView tv_error;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // 1. 创建OkHttpClient
        okHttpClient = new OkHttpClient();
    }

    private void initView() {
        btn_get = (Button) findViewById(R.id.btn_get);
        btn_post = (Button) findViewById(R.id.btn_post);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_error = (TextView) findViewById(R.id.tv_error);

        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                //2 创建Request对象
                Request request = new Request.Builder()
                        .get()
                        .url(URL_GET)
                        .build();
                //3. okHttpClient 执行请求
                //                okHttpClient.newCall(request).execute()// 同步方法
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //失败 子线程
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_error.setText("get:失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //请求成功
                        final String json = response.body().string();//获取返回的字符串信息
//                        response.body().bytes();// 转换为byte数组
//                        response.body().byteStream();// 转换为字节流
//                        response.body().charStream();//  转换为字符流
//                        tv_ok.setText("get:"+json);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_ok.setText("get:" + json);
                            }
                        });
                    }
                });
                break;
            case R.id.btn_post:
//                phone=13812345678&key=daf8fa858c330b22e342c882bcbac622
                RequestBody body = new FormBody.Builder()
                        .add("phone", "13838381234")
                        .add("key", "daf8fa858c330b22e342c882bcbac622")
                        .build();
                Request postRequest = new Request.Builder()
                        .url(URL_POST)
                        .post(body)
                        .build();
                okHttpClient.newCall(postRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //失败 子线程
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_error.setText("post:失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //请求成功
                        final String json = response.body().string();//获取返回的字符串信息
//                        response.body().bytes();// 转换为byte数组
//                        response.body().byteStream();// 转换为字节流
//                        response.body().charStream();//  转换为字符流
//                        tv_ok.setText("get:"+json);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_ok.setText("post:" + json);
                            }
                        });
                    }
                });
                break;
        }
    }
}
