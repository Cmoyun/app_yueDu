package moyun.sinaapp.com.yuedu.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.news.DocBean;
import moyun.sinaapp.com.yuedu.entity.news.SlidesBean;
import moyun.sinaapp.com.yuedu.http.HttpHandler;
import moyun.sinaapp.com.yuedu.ui.activity.NewsImgActivity;
import moyun.sinaapp.com.yuedu.ui.activity.NewsWebActivity;
import moyun.sinaapp.com.yuedu.ui.fragment.NewsPagerFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Moy on 九月17  017.
 */
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.DefViewHolder> {
    private NewsPagerFragment context;
    private LayoutInflater inflater;
    private List data;

    public NewsItemAdapter(NewsPagerFragment context, List data) {
        this.context = context;
        inflater = LayoutInflater.from(context.getContext());
        this.data = data;
    }

    @Override
    public DefViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.wg_news_item, viewGroup, false);
        return new DefViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DefViewHolder viewHolder, int i) {
        if (data.get(i) instanceof DocBean) {
            DocBean doc = ((DocBean) data.get(i));
            viewHolder.imgs.setVisibility(View.GONE);
            viewHolder.content.setVisibility(View.VISIBLE);
            viewHolder.content_news_title.setText(doc.getTitle());
            viewHolder.content_update_time.setText(doc.getUpdateTime());
            Glide.with(context).load(doc.getThumbnail()).into(viewHolder.content_news_pic);
        } else if (data.get(i) instanceof SlidesBean) {
            SlidesBean slide = ((SlidesBean) data.get(i));
            viewHolder.content.setVisibility(View.GONE);
            viewHolder.imgs.setVisibility(View.VISIBLE);
            viewHolder.imgs_news_title.setText(slide.getTitle());
            Glide.with(context).load(slide.getStyle().getImages().get(0)).into(viewHolder.imgs_news_pic1);
            Glide.with(context).load(slide.getStyle().getImages().get(1)).into(viewHolder.imgs_news_pic2);
            Glide.with(context).load(slide.getStyle().getImages().get(2)).into(viewHolder.imgs_news_pic3);
        }
        if (getItemCount() - i <= 1) {
            context.handler.sendEmptyMessage(NewsPagerFragment.HANDLER_LOADING_NEXT_NEWS);
        }
    }

    public void addItem(Object obj) {
        data.add(obj);
        this.notifyItemInserted(getItemCount());
    }

    public void addItems(List<Object> data) {
        for (Object o : data) {
            addItem(o);
        }
    }

    public void updateItems(List<Object> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    //ViewHolder
    class DefViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View imgs;
        TextView imgs_news_title;
        ImageView imgs_news_pic1;
        ImageView imgs_news_pic2;
        ImageView imgs_news_pic3;

        View content;
        ImageView content_news_pic;
        TextView content_news_title;
        TextView content_update_time;

        public DefViewHolder(View itemView) {
            super(itemView);
            imgs = itemView.findViewById(R.id.item_imgs);
            imgs_news_title = (TextView) imgs.findViewById(R.id.news_title);
            imgs_news_pic1 = (ImageView) imgs.findViewById(R.id.news_pic1);
            imgs_news_pic2 = (ImageView) imgs.findViewById(R.id.news_pic2);
            imgs_news_pic3 = (ImageView) imgs.findViewById(R.id.news_pic3);
            imgs.setOnClickListener(this);

            content = itemView.findViewById(R.id.item_content);
            content_news_pic = (ImageView) content.findViewById(R.id.news_pic);
            content_news_title = (TextView) content.findViewById(R.id.news_title);
            content_update_time = (TextView) content.findViewById(R.id.update_time);
            content.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Context context = v.getContext();
            switch (v.getId()) {
                case R.id.item_content:
                    DocBean doc = ((DocBean) data.get(getPosition()));
                    try {
                        HttpHandler.me().getAsy(doc.getId(), new HttpHandler.OnAsyResultCallBack<String>(String.class) {
                            @Override
                            public void onResponse(String rs) {
                                String title = "", url = "";
                                try {
                                    JSONObject html = new JSONObject(rs);
                                    JSONObject body = html.getJSONObject("body");
                                    title = body.getString("title");
                                    url = body.getString("wapurl");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("title", title);
                                bundle.putString("url", url);
                                Intent intent = new Intent(context, NewsWebActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onFailure(Request request, IOException e) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.item_imgs:

                    final SlidesBean slide = ((SlidesBean) data.get(getPosition()));
                    try {
                        HttpHandler.me().getAsy(slide.getId(), new HttpHandler.OnAsyResultCallBack<String>(String.class) {
                            @Override
                            public void onResponse(String rs) {
                                String title = "";
                                String[] descriptions = new String[0];
                                String[] imgs = new String[0];
                                try {
                                    JSONObject html = new JSONObject(rs);
                                    JSONObject body = html.getJSONObject("body");
                                    title = body.getString("title");
                                    JSONArray slides = body.getJSONArray("slides");
                                    descriptions = new String[slides.length()];
                                    imgs = new String[slides.length()];
                                    for (int i = 0; i < slides.length(); i++) {
                                        JSONObject item = slides.getJSONObject(i);
                                        descriptions[i] = item.getString("description");
                                        imgs[i] = item.getString("image");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("title", title);
                                bundle.putStringArray("description", descriptions);
                                bundle.putStringArray("image", imgs);
                                Intent intent = new Intent(context, NewsImgActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onFailure(Request request, IOException e) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
