package cn.ifreedomer.com.androidguide.event;

import java.util.ArrayList;
import java.util.List;

import cn.ifreedomer.com.androidguide.model.ContentModel;

/**
 * @author:eavawu
 * @date: 6/19/16.
 * @todo:
 */
public class ContentEvent {
    private String subTitle;
    private List<ContentModel> contentModels = new ArrayList<>();

    public ContentEvent(List<ContentModel> contentModels, String subTitle) {
        this.contentModels = contentModels;
        this.subTitle = subTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<ContentModel> getContentModels() {
        return contentModels;
    }

    public void setContentModels(List<ContentModel> contentModels) {
        this.contentModels = contentModels;
    }
}
