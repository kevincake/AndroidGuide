package cn.ifreedomer.com.androidguide.model;

/**
 * @author:eavawu
 * @date: 6/19/16.
 * @todo:
 */
public class SubTitleModel {
    private String subTitle;
    private int position;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "SubTitleModel{" +
                "subTitle='" + subTitle + '\'' +
                ", position=" + position +
                '}';
    }
}
