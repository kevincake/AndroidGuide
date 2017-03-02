package cn.ifreedomer.com.androidguide.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;
import cn.ifreedomer.com.androidguide.mail.MailThread;

public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sender_et)
    EditText senderEt;
    @Bind(R.id.topic_tv)
    TextView topicTv;
    @Bind(R.id.topic_et)
    EditText topicEt;
    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.content_et)
    EditText contentEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedback_menu, menu);
        menu.findItem(R.id.f_item_send).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MailThread mailThread = new MailThread(FeedBackActivity.this,topicEt.getText().toString(),contentEt.getText().toString(),senderEt.getText().toString());
                mailThread.start();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
