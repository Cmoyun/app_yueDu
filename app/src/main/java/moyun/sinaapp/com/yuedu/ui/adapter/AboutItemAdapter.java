package moyun.sinaapp.com.yuedu.ui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mingle.widget.ShapeLoadingDialog;
import com.squareup.okhttp.Request;
import moyun.sinaapp.com.yuedu.Custom;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.AboutItemBean;
import moyun.sinaapp.com.yuedu.http.HttpHandler;
import moyun.sinaapp.com.yuedu.ui.activity.FeedbackActivity;
import moyun.sinaapp.com.yuedu.ui.activity.NewsWebActivity;
import moyun.sinaapp.com.yuedu.utils.Lang;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Moy on 十月07  007.
 */
public class AboutItemAdapter extends RecyclerView.Adapter<AboutItemAdapter.VH> {

    List<AboutItemBean> data;
    Context context;
    LayoutInflater inflater;

    Handler handler = new Handler(Looper.myLooper());

    public AboutItemAdapter(Context context, List<AboutItemBean> data) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.wg_about_item, parent, false);//
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        AboutItemBean now = data.get(position);
        holder.bigger.setText(now.getOne() + "");
        holder.small.setText(now.getTwo() + "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    // VH
    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bigger;
        TextView small;
        View itemView;

        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            findView(itemView);
            initView();
        }

        private void initView() {
            itemView.setOnClickListener(this);
        }

        private void findView(View itemView) {
            bigger = (TextView) itemView.findViewById(R.id.bigger);
            small = (TextView) itemView.findViewById(R.id.small);
        }

        @Override
        public void onClick(View v) {
            int pos = getPosition();
            AboutItemBean now = data.get(pos);
            switch (now.getType()) {
                case 0:
                    break;
                case 1:
                    final ShapeLoadingDialog shapeLoadingDialog = new ShapeLoadingDialog(context);
                    shapeLoadingDialog.setLoadingText("加载中...");
                    shapeLoadingDialog.show();
                    PackageManager packageManager = context.getPackageManager();
                    PackageInfo info = null;
                    try {
                        info = packageManager.getPackageInfo(context.getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        HttpHandler.me().getAsy(String.format("%s/Dragon_project/JSON.action?v=%s&os=%d&app=%s", Custom.domain, info.versionName, 0, "dragon"), new HttpHandler.OnAsyResultCallBack<String>(String.class) {
                            @Override
                            public void onResponse(String rs) {
                                try {
                                    final JSONObject obj = new JSONObject(rs);
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            _checkUpdate(obj, shapeLoadingDialog);
                                        }
                                    });
                                } catch (Exception e) {

                                }
                            }

                            @Override
                            public void onFailure(Request request, IOException e) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    // 打开网页
                    Bundle bundle = new Bundle();
                    bundle.putString("title", now.getOne());
                    bundle.putString("url", now.getTwo());
                    Intent intent = new Intent(context, NewsWebActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    break;
                case 3:
                    context.startActivity(new Intent(context, FeedbackActivity.class));
                    break;
            }
        }

        private void _checkUpdate(final JSONObject obj, final ShapeLoadingDialog shapeLoadingDialog) {
            shapeLoadingDialog.dismiss();
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (!obj.optBoolean("success")) {
                final JSONObject finalObj = obj;
                builder.setTitle("检查更新")
                        .setMessage(finalObj.optString("msg"))
                        .show();
            } else {
                JSONObject result = obj.optJSONObject("result");
                final String url = result.optString("url");
                final String app = result.optString("app");
                final String v = result.optString("version");
                builder.setTitle("检查更新")
                        .setMessage(obj.optString("msg"))
                        .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog pd;    //进度条对话框
                                pd = new ProgressDialog(context);
                                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                pd.setMessage("正在下载更新");
                                pd.show();
                                new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            File file = Lang.uploadApp(url);
                                            pd.dismiss();
                                            sleep(3000);
                                            _installApk(file);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                            }
                        })
                        .setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        }
    }

    //安装apk
    protected void _installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}