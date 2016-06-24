package cn.ifreedomer.com.androidguide.adapter;

import android.content.Context;
import android.view.View;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;

import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.model.ContentModel;
import cn.ifreedomer.com.androidguide.util.DateUtil;
import cn.ifreedomer.com.androidguide.util.IntentUtils;

/**
 * @author:eavawu
 * @date: 6/19/16.
 * @todo:
 */
public class MainRvAdapter extends CommonAdapter<ContentModel> {


    public MainRvAdapter(Context context, int layoutId, List<ContentModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final ContentModel contentModel) {
        holder.setOnClickListener(R.id.rootview, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startReaderActivity(mContext,contentModel.getRealFileName());
            }
        });
        holder.setText(R.id.title_tv, contentModel.getTitle());
        holder.setText(R.id.time_tv, String.format(mContext.getString(R.string.time_wrap), DateUtil.getDateFot(contentModel.getTime())));
    }
}
