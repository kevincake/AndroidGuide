package cn.ifreedomer.com.androidguide.adapter;

import android.content.Context;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;

import cn.ifreedomer.com.androidguide.model.ContentModel;

/**
 * @author:eavawu
 * @date: 6/19/16.
 * @todo:
 */
public class MainRvAdapter extends CommonAdapter<ContentModel>{


    public MainRvAdapter(Context context, int layoutId, List<ContentModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, ContentModel contentModel) {

    }
}
