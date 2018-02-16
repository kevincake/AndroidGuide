package cn.ifreedomer.com.androidguide.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;
import cn.ifreedomer.com.androidguide.util.FileUtil;
import us.feras.mdv.MarkdownView;

public class HelpActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.markdownview)
    MarkdownView markdownview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        String html = null;
        try {
            InputStream open = getAssets().open("help.md");
            String helpContent = FileUtil.inputStream2String(open);
            html = new Markdown4jProcessor().process(helpContent);
            markdownview.loadMarkdown(html);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
