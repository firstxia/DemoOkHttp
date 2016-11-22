package itcast.zz.okhttputil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL_GET = "http://apis.juhe.cn/mobile/get?phone=13812345678&key=daf8fa858c330b22e342c882bcbac622";
    public static final String URL_POST = "http://apis.juhe.cn/mobile/get";
    private Button btn_get;
    private Button btn_post;
    private TextView tv_ok;
    private TextView tv_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
            case R.id.btn_get://get请求
                OkHttpUtils
                        .get()
                        .url(URL_POST)
                        .addParams("phone", "13838381234")
                        .addParams("key", "daf8fa858c330b22e342c882bcbac622")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {
                                //失败
                                tv_error.setText("get:" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response) {
                                //成功
                                tv_ok.setText("get :" + response);
                            }
                        });

                break;
            case R.id.btn_post:
//                phone=13812345678&key=daf8fa858c330b22e342c882bcbac622
                OkHttpUtils
                        .post()
                        .url(URL_POST)
                        .addParams("phone", "13838381234")
                        .addParams("key", "daf8fa858c330b22e342c882bcbac622")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {
                                tv_error.setText("post :" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response) {
                                tv_ok.setText("post :" + response);
                            }
                        });

                break;
        }
    }
}
