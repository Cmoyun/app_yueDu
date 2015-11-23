package moyun.sinaapp.com.yuedu;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.test.ApplicationTestCase;
import android.view.View;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.*;
import moyun.sinaapp.com.yuedu.http.HttpHandler;

import java.io.File;
import java.io.IOException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final String IMGUR_CLIENT_ID = "5656";
    private final OkHttpClient client = new OkHttpClient();

    public void testUrl() {
        File f = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/", "tmpFile.png");
        Bitmap mipmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_play_arrow_black_24dp);
//        _sendFeedback("55", f, "getContext()", new ImageView(getContext()));
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"uploadFile\""),
                        RequestBody.create(MEDIA_TYPE_PNG, f))
                .addFormDataPart("email", "email")
                .addFormDataPart("content", "content")
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url(String.format("%s/Dragon_project/MsgJSON.action", Custom.domain))
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Logger.v("--- "+ response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * 提交表单
     *
     * @param email
     * @param imgFile
     * @param content
     * @return
     */
    private boolean _sendFeedback(String email, File imgFile, String content, final View skin) {
        Logger.v("---FK   调用中");
        boolean rsState = true;
        MultipartBuilder tmp = new MultipartBuilder().type(MultipartBuilder.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("content", content).addFormDataPart("pic", imgFile.getName(), RequestBody.create(null, imgFile))
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), imgFile));
        RequestBody requestBody = tmp.build();
        HttpHandler.me().getOkHttpClient().newCall(new Request.Builder().url(String.format("%s/Dragon_project/MsgJSON.action", Custom.domain)).post(requestBody).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Logger.v("json "+ json);
            }
        });
        return rsState;
    }
}