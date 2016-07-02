package cn.ifreedomer.com.androidguide.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class SignInActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.account_et)
    EditText accountEt;
    @Bind(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.password_rapper)
    TextInputLayout passwordRapper;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.login_forget_tv)
    TextView loginForgetTv;
    @Bind(R.id.wechat_iv)
    ImageView wechatIv;
    @Bind(R.id.qq_iv)
    ImageView qqIv;
    @Bind(R.id.sina_iv)
    ImageView sinaIv;
    @Bind(R.id.goto_register_tv)
    TextView gotoRegisterTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.login_btn, R.id.login_forget_tv, R.id.goto_register_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                UserModel userModel = new UserModel();
                userModel.setAccount(accountEt.getText().toString());
                userModel.setPwd(pwdEt.getText().toString());
                userModel.signIn(new SimpleRetrofitCallBack<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        AppManager.getInstance().setUser(loginResult.getUser());
                        AppManager.getInstance().setToken(loginResult.getToken());
                        AppManager.getInstance().setLogin(true);
                        IntentUtils.startMainActivity(SignInActivity.this);
                        SignInActivity.this.finish();
                    }
                });
                break;
            case R.id.login_forget_tv:
                break;
            case R.id.goto_register_tv:
                IntentUtils.startSignUpActivity(this);
                break;
        }
    }
}
