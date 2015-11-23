package moyun.sinaapp.com.yuedu.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.ImageBean;
import moyun.sinaapp.com.yuedu.ui.activity.SeeImageActivity;
import moyun.sinaapp.com.yuedu.ui.fragment.ImagesPagerFragment;
import moyun.sinaapp.com.yuedu.ui.fragment.NewsPagerFragment;
import moyun.sinaapp.com.yuedu.utils.SingletonUtil;

import java.util.List;

/**
 * Created by Moy on 九月17  017.
 */
public class ImageItemAdapter extends RecyclerView.Adapter<ImageItemAdapter.DefViewHolder> {
    private ImagesPagerFragment context;
    private LayoutInflater inflater;
    private List data;

    public ImageItemAdapter(ImagesPagerFragment context, List data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context.getContext());
    }

    @Override
    public DefViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.wg_image_item, viewGroup, false);
        return new DefViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DefViewHolder viewHolder, int i) {
        Logger.v(SingletonUtil.gson().toJson(data));

        Glide.with(context)
                .load(((ImageBean) data.get(i)).getSmallUrl())
                .placeholder(R.mipmap.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .crossFade()
                .into(viewHolder.imageItem);

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

        ImageView imageItem;

        public DefViewHolder(View itemView) {
            super(itemView);
            imageItem = (ImageView) itemView.findViewById(R.id.image_item);
            imageItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_item:
                    ImageBean pic = ((ImageBean) data.get(getPosition()));
                    Intent intent = new Intent(context.getContext(), SeeImageActivity.class);
                    intent.putExtra("pic", pic);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
