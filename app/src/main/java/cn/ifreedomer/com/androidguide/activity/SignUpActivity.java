package cn.ifreedomer.com.androidguide.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifreedomer.com.androidguide.R;
import cn.ifreedomer.com.androidguide.activity.base.BaseActivity;

public class SignUpActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fullinfo_username_et)
    EditText fullinfoUsernameEt;
    @Bind(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;
    @Bind(R.id.input_email_et)
    EditText inputEmailEt;
    @Bind(R.id.email_wrap)
    TextInputLayout emailWrap;
    @Bind(R.id.fullinfo_pwd_et)
    EditText fullinfoPwdEt;
    @Bind(R.id.password_rapper)
    TextInputLayout passwordRapper;
    @Bind(R.id.fullinfo_pwd_confirm_et)
    EditText fullinfoPwdConfirmEt;
    @Bind(R.id.password_confirm_rapper)
    TextInputLayout passwordConfirmRapper;
    @Bind(R.id.fullinfo_commit_btn)
    Button fullinfoCommitBtn;
    @Bind(R.id.back2login_tv)
    TextView back2loginTv;
    @Bind(R.id.bottomsheet)
    BottomSheetLayout bottomsheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.fullinfo_commit_btn, R.id.back2login_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fullinfo_commit_btn:

                break;
            case R.id.back2login_tv:
                this.finish();
                break;
        }
    }
}
