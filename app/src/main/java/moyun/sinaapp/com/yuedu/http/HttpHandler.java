package moyun.sinaapp.com.yuedu.http;

import android.os.Handler;
import android.os.Looper;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Created by Moy on 八月26  026.
 */
public class HttpHandler {

    private static OkHttpClient okHttpClient;
    private static HttpHandler mOkHttpHandler = null;
    private Handler mMainHandler;

    static {
        okHttpClient = new OkHttpClient();
    }

    private HttpHandler() {
        mMainHandler = new Handler(Looper.myLooper());
    }


    // -- 异步访问 asy
    public void getAsy(String url, final OnAsyResultCallBack onAsyResultCallBack) throws IOException {
        deliveryResult(_get(url), onAsyResultCallBack);
    }

    public void postAsy(String url, final OnAsyResultCallBack onAsyResultCallBack, Param... params) throws IOException {
        deliveryResult(_post(url, params), onAsyResultCallBack);
    }


    // -- 同步访问 Syn
    public Response getSyn(String url) throws IOException {
        return _get(url).execute();
    }

    public String getSynString(String url) throws IOException {
        return getSyn(url).body().string();
    }

    public Response postSyn(String url, Param... params) throws IOException {
        return _post(url).execute();
    }

    public String postSynString(String url, Param... params) throws IOException {
        return postSyn(url, params).body().string();
    }
    // ----

    // 用于生产 Call对象
    private Call _get(String url) {
        final Request request = new Request.Builder().url(url).build();
        return okHttpClient.newCall(request);
    }

    private Call _post(String url, RequestBody requestBody) {
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        return okHttpClient.newCall(request);
    }

    private Call _post(String url, Param... params) {
        return _post(url, buildPostRequestBody(params));
    }

    /**
     * 转发异步结果处理
     *
     * @param call     Call对象
     * @param callBack 响应回调
     */
    private void deliveryResult(Call call, final OnAsyResultCallBack callBack) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                if (callBack != null) {

                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailure(request, e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (callBack != null) {
                    ResponseBody responseBody = response.body();
                    if (callBack.getType() == byte[].class) {
                        callBack.onResponse(responseBody.bytes());
                    } else if (callBack.getType() == InputStream.class) {
                        callBack.onResponse(responseBody.byteStream());
                    } else if (callBack.getType() == Reader.class) {
                        callBack.onResponse(responseBody.charStream());
                    } else {
                        callBack.onResponse(responseBody.string());
                    }
                }
            }
        });
    }

//    private void sendSuccess(final Object obj, final OnAsyResultCallBack callBack) {
//        mMainHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                callBack.onResponse(obj);
//            }
//        });
//    }

    /**
     * 构建单纯的 String-key 形式的RequestBody
     *
     * @param params Param对象
     * @return RequestBody
     */
    private RequestBody buildPostRequestBody(Param... params) {
        if (params == null) params = new Param[0];

        FormEncodingBuilder formBuilder = new FormEncodingBuilder();
        for (Param param : params) {
            formBuilder.add(param.key, param.value);
        }

        return formBuilder.build();
    }

    /**
     * @return OkHttpClient
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }


    /**
     * 构建OkHttpHandler对象
     * 一个封装 OkHttp
     *
     * @return
     */
    public static HttpHandler me() {
        if (mOkHttpHandler == null) {
            mOkHttpHandler = new HttpHandler();
        }
        return mOkHttpHandler;
    }

    // post 请求参数体
    public static class Param {

        String key;
        String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 用于接收okhttp执行异步情况下响应回调
     */
    public static abstract class OnAsyResultCallBack<T> {

        Class<T> clazz;
        public OnAsyResultCallBack(Class<T> clazz) {
            this.clazz = clazz;
        }

        public abstract void onResponse(T rs);

        public abstract void onFailure(Request request, IOException e);

        public Class getType() {
            return this.clazz;
        }
    }
}
