package moyun.sinaapp.com.yuedu.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import moyun.sinaapp.com.yuedu.Custom;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.ImageBean;
import moyun.sinaapp.com.yuedu.utils.IntentUtil;
import moyun.sinaapp.com.yuedu.utils.Lang;

/**
 * Created by Moy on 十月07  007.
 */
public class SeeImageActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView pic;
    RelativeLayout rl;
    Button toggle;
    ImageButton drown;
    ImageButton share;

    boolean isHighDefinition = false;
    ImageBean imageBean;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pic);
        findView(); // 寻找id
        handlerIntent(); // 意图处理
        initView();
    }

    private void initView() {
        togglePic();
    }

    private void findView() {
        pic = (ImageView) findViewById(R.id.pic);
        rl = (RelativeLayout) findViewById(R.id.rl);
        drown = (ImageButton) findViewById(R.id.drown);
        share = (ImageButton) findViewById(R.id.share);
        toggle = (Button) findViewById(R.id.toggle);

        rl.setOnClickListener(this);
        drown.setOnClickListener(this);
        share.setOnClickListener(this);
        toggle.setOnClickListener(this);
    }

    private void handlerIntent() {
        Intent intent = getIntent();
        imageBean = intent.getParcelableExtra("pic");
        url = imageBean.getSmallUrl();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl:
                finish();
                break;
            case R.id.drown:
                Toast.makeText(this, "下载中，请稍后..", Toast.LENGTH_SHORT).show();
                String picturesDir = Environment.getExternalStorageDirectory().getPath() + "/Pictures"; // 存储路径
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + picturesDir + "/" + Lang._getFileName(url)), "image/*");
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                builder.setContentTitle("下载图片中..")
                        .setContentText("请求握手")
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setProgress(100, 0, true)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_get_app_black_24dp);
                if (Lang.mkDir(picturesDir)){
                    notificationManager.notify(Custom.NOTIFICATION_DOWN_PIC, builder.build());
                    Lang.down(url, picturesDir, builder, notificationManager, Custom.NOTIFICATION_DOWN_PIC);
                }
                break;
            case R.id.share:
                startActivity(IntentUtil.shareAction(String.format("%s ", url)));
                break;
            case R.id.toggle:
                togglePic();
                break;
        }
    }

    private void togglePic() {
        url = isHighDefinition ? imageBean.getBiggerUrl() : imageBean.getSmallUrl();
        String str = isHighDefinition ? "高清" : "标清";
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .crossFade()
                .into(pic);
        toggle.setText(str);
        isHighDefinition = !isHighDefinition;
    }
}
