package cn.ifreedomer.com.androidguide.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import org.markdown4j.Markdown4jProcessor;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;
import cn.ifreedomer.com.androidguide.constants.IntentConstants;
import cn.ifreedomer.com.androidguide.util.FileUtil;
import cn.ifreedomer.com.androidguide.util.LogUtil;
import cn.ifreedomer.com.androidguide.util.TempUtil;
import us.feras.mdv.MarkdownView;

public class ReaderActivity extends BaseActivity {
    @BindView(R.id.markdownview)
    MarkdownView markdownview;
    private String TAG = "ReaderActivity";
    @BindView(R.id.toolbar)
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
            if (!TextUtils.isEmpty(TempUtil.getTitleName())){
                mActionBarToolbar.setTitle(TempUtil.getTitleName());
               // TempUtil.setTitleName(null);
            }


//            markdownTv.setText(Html.fromHtml(html));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.info(TAG, stringExtra);


    }


}
