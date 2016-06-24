package cn.ifreedomer.com.androidguide.model;

import java.util.HashMap;
import java.util.List;

/**
 * @author:eavawu
 * @date: 6/18/16.
 * @todo:
 */
public class NavExpandedModel {
    private String titleName = "";
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private HashMap<SubTitleModel, List<ContentModel>> contentMap = new HashMap<>();

    public String getTitleName() {
        return titleName;
    }

    public HashMap<SubTitleModel, List<ContentModel>> getContentMap() {
        return contentMap;
    }

    public void setContentMap(HashMap<SubTitleModel, List<ContentModel>> contentMap) {
        this.contentMap = contentMap;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    @Override
    public String toString() {
        return "NavExpandedModel{" +
                "titleName='" + titleName + '\'' +
                ", position=" + position +
                ", contentMap=" + contentMap +
                '}';
    }
}
