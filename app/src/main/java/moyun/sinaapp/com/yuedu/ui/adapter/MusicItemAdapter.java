package moyun.sinaapp.com.yuedu.ui.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.MusicBean;
import moyun.sinaapp.com.yuedu.ui.activity.MusicActivity;
import moyun.sinaapp.com.yuedu.ui.fragment.AbsMusicPagerFragment;
import moyun.sinaapp.com.yuedu.ui.fragment.NewsPagerFragment;
import moyun.sinaapp.com.yuedu.utils.SingletonUtil;
import moyun.sinaapp.com.yuedu.widgets.service.MusicService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moy on 九月17  017.
 * MusicItem 样式 事件 控制
 */
public class MusicItemAdapter extends RecyclerView.Adapter<MusicItemAdapter.DefViewHolder> {
    private AbsMusicPagerFragment context;
    private LayoutInflater inflater;
    private List data;
    private int openType;

    public MusicItemAdapter(AbsMusicPagerFragment context, List data, int openType) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context.getContext());
        this.openType = openType;
    }

    public MusicItemAdapter(AbsMusicPagerFragment context, List data) {
        this(context, data, 1);
    }

    @Override
    public DefViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.wg_music_item, viewGroup, false);
        return new DefViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DefViewHolder viewHolder, int i) {
        Logger.v(SingletonUtil.gson().toJson(data));
        MusicBean m = ((MusicBean) data.get(i));
        viewHolder.musicName.setText(m.getSong_name() + "");
        viewHolder.authorName.setText(m.getSinger_name() + "");

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

        TextView musicName;
        TextView authorName;
        RelativeLayout musicItem;

        public DefViewHolder(View itemView) {
            super(itemView);
            musicName = (TextView) itemView.findViewById(R.id.music_image);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            musicItem = (RelativeLayout) itemView.findViewById(R.id.music_item);

            musicItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.music_item:
                    int pos = getPosition();
                    if (openType == 1) {
                        Intent intent = new Intent(context.getContext(), MusicActivity.class);
                        intent.putExtra("pos", pos);
                        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) data);
//                        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) data);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context.getContext(), MusicService.class);
                        intent.putExtra("url", ((MusicBean) data.get(pos)).getAudition_list().get(0).getUrl());
                        intent.putExtra("listPosition", pos);
                        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) data);
                        intent.putExtra("MSG", MusicService.INTENT_MCL_PLAY);
                        context.getContext().startService(intent);
                    }
                    break;
            }
        }
    }
}
