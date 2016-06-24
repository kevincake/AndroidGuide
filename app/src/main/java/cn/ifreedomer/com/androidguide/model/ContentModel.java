package cn.ifreedomer.com.androidguide.model;

/**
 * @author:eavawu
 * @date: 6/19/16.
 * @todo:
 */
public class ContentModel {
    private String content;
    private String title;
    private long time;
    private int position;
    private String realFileName;

    public String getRealFileName() {
        return realFileName;
    }

    public void setRealFileName(String realFileName) {
        this.realFileName = realFileName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long  getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ContentModel{" +
                "content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", position=" + position +
                '}';
    }
}
