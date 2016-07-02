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
import cn.ifreedomer.com.androidguide.callback.SimpleRetrofitCallBack;
import cn.ifreedomer.com.androidguide.manager.AppManager;
import cn.ifreedomer.com.androidguide.model.LoginResult;
import cn.ifreedomer.com.androidguide.model.UserModel;
import cn.ifreedomer.com.androidguide.util.IntentUtils;

public class SignUpActivity extends BaseActivity {
    private static final String TAG = "SignUpActivity";
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
                UserModel userModel = new UserModel();
                userModel.setConfirmPwd(fullinfoPwdConfirmEt.getText().toString());
                userModel.setPwd(fullinfoPwdEt.getText().toString());
                userModel.setAccount(inputEmailEt.getText().toString());
                userModel.setName(fullinfoUsernameEt.getText().toString());
                userModel.setLogin(false);
                userModel.signUp(new SimpleRetrofitCallBack<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        AppManager.getInstance().setUser(loginResult.getUser());
                        AppManager.getInstance().setToken(loginResult.getToken());
                        AppManager.getInstance().setLogin(true);
                        IntentUtils.startMainActivity(SignUpActivity.this);
                        SignUpActivity.this.finish();
                    }
                });
                break;
            case R.id.back2login_tv:
                this.finish();
                break;
        }
    }
}
