package cn.ifreedomer.com.androidguide.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.markdown4j.Markdown4jProcessor;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;
import cn.ifreedomer.com.androidguide.constants.IntentConstants;
import cn.ifreedomer.com.androidguide.util.FileUtil;
import cn.ifreedomer.com.androidguide.util.LogUtil;
import us.feras.mdv.MarkdownView;

public class ReaderActivity extends BaseActivity {
    @Bind(R.id.markdownview)
    MarkdownView markdownview;
    private String TAG = "ReaderActivity";
    @Bind(R.id.phone_toolbar)
    Toolbar phoneToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        ButterKnife.bind(this);
        String stringExtra = getIntent().getStringExtra(IntentConstants.READER_PATH);
        try {
//            markdownView.loadMarkdown();

            String html = new Markdown4jProcessor().process(FileUtil.readSDFile(stringExtra));
            markdownview.loadMarkdown(html);


//            markdownTv.setText(Html.fromHtml(html));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.info(TAG, stringExtra);


    }
}
