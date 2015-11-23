package moyun.sinaapp.com.yuedu.utils;

import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import moyun.sinaapp.com.yuedu.http.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Moy on 九月18  018.
 */
public class Lang {

    public static void handMsg(Handler handler, int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

    public static <T> boolean isEmpty(T target) {
        if (target == null) {
            return true;
        } else if (target.getClass() == String.class) {
            return "".equals(((String) target).trim());
        }
        return false;
    }

    /**
     * 下载文件
     *
     * @param url     下载链接
     * @param keepDir 保存路径
     * @return 文件路径
     */
    public static void down(final String url, final String keepDir, final NotificationCompat.Builder builder, final NotificationManager notificationManager, final int notificationId) {
        HttpHandler.me().getOkHttpClient().newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // 失败
                try {
                    throw new Exception("Moy down fail");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = response.body().byteStream();
                File file = new File(keepDir, _getFileName(url));
                FileOutputStream fos = new FileOutputStream(file);
                int len;
                byte[] buf = new byte[2048];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.flush();
                if (is != null) is.close();
                if (fos != null) fos.close();
                builder.setContentTitle("下载完毕")
                        .setContentText("已保存")
                        .setProgress(0, 0, false);
                notificationManager.notify(notificationId, builder.build());
            }
        });
    }

    public static String _getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    public static boolean mkDir(String dirPath) {
        File f = new File(dirPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        return true;
    }


    public static int checkSelfPermission(String accessFineLocation) {
        return 0;
    }

    public static File uploadApp(String path) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "update.apk");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }


    public static final File bitmap2File(Bitmap bitmap) throws IOException {
        File f;
        String picturesDir = Environment.getExternalStorageDirectory().getPath() + "/Pictures";
        Lang.mkDir(picturesDir);
        FileOutputStream fos = new FileOutputStream(f = new File(picturesDir, "tmpFile.png"));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        fos.write(bytes);
        fos.close();
        return f;
    }

}
