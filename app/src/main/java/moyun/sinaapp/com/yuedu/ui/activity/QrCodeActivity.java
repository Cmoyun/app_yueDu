package moyun.sinaapp.com.yuedu.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.ui.util.BitmapLuminanceSource;
import moyun.sinaapp.com.yuedu.ui.util.QrCodeTv;

import java.io.*;

/**
 * Created by Moy on 十月08  008.
 */
public class QrCodeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView captureScanMask;
    FrameLayout qrCodeView;
    Camera camera;
    QrCodeTv qrCodeTv;
    Button captureLightBtn;
    FrameLayout captureCropView;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_UNLOCK:
                    camera.startPreview();
                    break;
            }
        }
    };
    private static final int HANDLE_UNLOCK = 0;

    /**
     * 自动对焦后输出图片
     */
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera arg1) {
            if (data != null) {
                int[] location = new int[2];
                captureCropView.getLocationOnScreen(location);
                Camera.Parameters parameters = camera.getParameters();
                int width = parameters.getPreviewSize().width;
                int height = parameters.getPreviewSize().height;
                YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                yuv.compressToJpeg(new Rect(location[1], location[0], location[1] + captureCropView.getHeight(), location[0] + captureCropView.getMeasuredWidth()), 90, out);

                byte[] bytes = out.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // 旋转角度
                Matrix m = new Matrix();
                m.setRotate(90, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                final String str = BitmapLuminanceSource.getResult(bitmap).trim();
                if (!"empty".equals(str)) {
                    String regx = "^https?:\\/\\/(([a-zA-Z0-9_-])+(\\.)?)*(:\\d+)?(\\/((\\.)?(\\?)?=?&?[a-zA-Z0-9_-](\\?)?)*)*$";
                    CoordinatorLayout coord = (CoordinatorLayout) findViewById(R.id.snackbar);
                    if (str.matches(regx)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "来自二维码扫描");
                        bundle.putString("url", str);
                        Intent intent = new Intent(QrCodeActivity.this, NewsWebActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar snackbar = Snackbar.make(coord, "字符 " + str, Snackbar.LENGTH_SHORT);
                        snackbar.setAction("复制", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                cmb.setPrimaryClip(ClipData.newPlainText(null, str));
                            }
                        });
                        snackbar.show();
                        handler.sendEmptyMessageDelayed(HANDLE_UNLOCK, 3 * 1000);
                    }
                }
            }
        }
    };

    /**
     * 保存图片
     */
//    private void saveBitmap(Bitmap bitmap) {
//        File f = new File("/sdcard/MoyTest/", "twm.png");
//        if (f.exists()) {
//            f.delete();
//        }
//        FileOutputStream ouxt = null;
//        try {
//            ouxt = new FileOutputStream(f);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 90, ouxt);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (ouxt != null) {
//                    ouxt.flush();
//                    ouxt.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        findView();
        var();
        initView();
    }

    private void var() {
        camera = getCameraInstance();
        // 创建Preview view并将其设为activity中的内容
        qrCodeTv = new QrCodeTv(this, camera);
        qrCodeTv.setSurfaceTextureListener(qrCodeTv);
        // 设置浑浊
//        qrCodeTv.setAlpha(0.5f);

    }

    private void initView() {
        qrCodeView.addView(qrCodeTv);
        captureLightBtn.setOnClickListener(this);
        camera.setPreviewCallback(previewCallback);
    }

    private void findView() {
        qrCodeView = (FrameLayout) findViewById(R.id.camera);
        captureLightBtn = (Button) findViewById(R.id.capture_light_btn);
        captureScanMask = (ImageView) findViewById(R.id.capture_scan_mask);
        captureCropView = (FrameLayout) findViewById(R.id.capture_crop_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera(); // 在暂停事件中立即释放摄像头
    }

    // -- Util Start

    /**
     * 安全获取Camera对象实例的方法
     */

    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(getDefaultCameraId()); // 试图获取Camera实例
            c.setDisplayOrientation(90);
        } catch (Exception e) {
            // 摄像头不可用（正被占用或不存在）
        }
        return c; // 不可用则返回null
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.lock();
            camera.release(); // 为其它应用释放摄像头
            camera = null;
        }
    }

    private int getDefaultCameraId() {
        int defaultId = -1;

        // Find the total number of cameras available
        int mNumberOfCameras = Camera.getNumberOfCameras();

        // Find the ID of the default camera
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < mNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                defaultId = i;
            }
        }
        if (-1 == defaultId) {
            if (mNumberOfCameras > 0) {
                // 如果没有后向摄像头
                defaultId = 0;
            } else {
                // 没有摄像头
                Toast.makeText(getApplicationContext(), "没有摄像头",
                        Toast.LENGTH_LONG).show();
            }
        }
        return defaultId;
    }

    boolean isLightOn = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_light_btn:
                Camera.Parameters parameter = camera.getParameters();
                if (isLightOn) { // 点亮
                    v.setSelected(true);
                    parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(parameter);
                } else { // 关闭
                    v.setSelected(false);
                    parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameter);
                }
                isLightOn = !isLightOn;
                break;
        }
    }
    // -- Util End
}
