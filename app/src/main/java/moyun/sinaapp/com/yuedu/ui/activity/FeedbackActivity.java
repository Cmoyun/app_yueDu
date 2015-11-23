package moyun.sinaapp.com.yuedu.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.*;
import moyun.sinaapp.com.yuedu.Custom;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.http.HttpHandler;
import moyun.sinaapp.com.yuedu.utils.Lang;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Moy on 十月08  008.
 */
public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout feedbackArea;
    EditText msg;
    Button send;
    Button addPic;
    android.support.v7.widget.Toolbar toolbar;

    private final int RESULT_IMAGE_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        findView();
        initView();
    }

    private void initView() {
        send.setOnClickListener(this);
        addPic.setOnClickListener(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("意见反馈");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findView() {
        feedbackArea = (LinearLayout) findViewById(R.id.feedbackArea);
        msg = (EditText) findViewById(R.id.msg);
        send = (Button) findViewById(R.id.send);
        addPic = (Button) findViewById(R.id.addPic);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                String str = msg.getText().toString().trim();
                if (str.length() > 0) {
                    View widget = LayoutInflater.from(this).inflate(R.layout.wg_feedback_chat_widget_text, null);
                    TextView msgText = (TextView) widget.findViewById(R.id.msg_text);
                    msgText.setText(str + "");
                    // 发送请求
                    _sendFeedback("555", null, str, _createMsg(msgText));
                    msg.setText("");
                }
                break;
            case R.id.addPic:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/jpeg");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    startActivityForResult(intent, RESULT_IMAGE_CODE);
                } else {
                    startActivityForResult(intent, RESULT_IMAGE_CODE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_IMAGE_CODE) { // 成功选择图片
            ContentResolver resolver = getContentResolver();
            Uri uri = data.getData();
            View widget = LayoutInflater.from(this).inflate(R.layout.wg_feedback_chat_widget_image, null);
            ImageView msgImg = (ImageView) widget.findViewById(R.id.msg_img);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
                msgImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 发送请求 todo 写请求处理 || 存数据库
            View skin = _createMsg(msgImg);
            try {
                _sendFeedback("555", Lang.bitmap2File(bitmap), "", skin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 创建一个对话框
     *
     * @param msgView 文字/图片
     * @return skin 对象
     */
    private View _createMsg(View msgView) {
        View skin = LayoutInflater.from(this).inflate(R.layout.wg_feedback_chat_skin, null);
        LinearLayout chatArea = (LinearLayout) skin.findViewById(R.id.chatArea);
        TextView time = (TextView) skin.findViewById(R.id.time);
        SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss");
        time.setText(d.format(new Date()) + "");
        chatArea.addView(msgView);
        feedbackArea.addView(skin);
        return skin;
    }


    /**
     * 提交表单
     *
     * @param email
     * @param img
     * @param content
     * @return
     */
    private boolean _sendFeedback(String email, File img, String content, final View skin) {
        Logger.v("---FK   调用中");
        boolean rsState = true;
        MultipartBuilder tmp = new MultipartBuilder().type(MultipartBuilder.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("content", content);
        if (img != null) {
            tmp.addFormDataPart("uploadFile", System.currentTimeMillis()+".png", RequestBody.create(MediaType.parse("image/png"), img))
                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"uploadFile\";"), RequestBody.create(MediaType.parse("application/octet-stream"), img));
        }
        RequestBody requestBody = tmp.build();
        HttpHandler.me().getOkHttpClient().newCall(new Request.Builder().url(String.format("%s/Dragon_project/MsgJSON.action", Custom.domain)).post(requestBody).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JSONObject finalJsonObj = jsonObj;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView msgState = ((ImageView) skin.findViewById(R.id.msg_state));
                        if (finalJsonObj.optBoolean("success")) {
                            msgState.setImageResource(R.mipmap.msg_success);
                        } else {
                            msgState.setImageResource(R.mipmap.msg_error);
                        }
                    }
                });
            }
        });
        return rsState;
    }
}
