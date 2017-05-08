package space.levan.suishouji.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import space.levan.suishouji.R;
import space.levan.suishouji.utils.KeyBoardUtils;
import space.levan.suishouji.view.base.BaseActivity;

/**
 * 用户登录
 *
 * Created by WangZhiYao on 2017/5/8.
 */

public class LoginActivity extends BaseActivity
{
    @BindView(R.id.et_login_username)
    EditText mEtUsername;
    @BindView(R.id.et_login_password)
    EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_signin, R.id.btn_register, R.id.btn_forget_password})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_signin:
                userSignIn();
                if (KeyBoardUtils.isKeyBordOpen(this))
                {
                    KeyBoardUtils.closeKeyBord(mEtPassword, this);
                }
                break;
            case R.id.btn_register:
                showRegisterDialog();
                break;
            case R.id.btn_forget_password:
                showResetPasswordDialog();
                break;
        }
    }

    /**
     * 用户登录
     */
    private void userSignIn()
    {
        String username = mEtUsername.getText().toString().trim();
        String password = mEtPassword.getText().toString();
        
        if (!(TextUtils.equals(username, "") || TextUtils.equals(password, "")))
        {
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>()
            {
                @Override
                public void done(AVUser avUser, AVException e)
                {
                    if (e == null)
                    {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else 
        {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 用户注册
     */
    private void showRegisterDialog()
    {
        LayoutInflater inflater = (LayoutInflater)this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout registerLayout = (LinearLayout)inflater
                .inflate(R.layout.dialog_register, null);

        new AlertDialog.Builder(this)
                .setView(registerLayout)
                .setTitle("用户注册")
                .setPositiveButton("确定", (dialogInterface, i) ->
                {
                    EditText mEtUsername1 = (EditText)registerLayout.findViewById(R.id.et_register_username);
                    EditText mEtPassword1 = (EditText)registerLayout.findViewById(R.id.et_register_password);
                    EditText mEtVerifiedPassword = (EditText)registerLayout.findViewById(R.id.et_register_verified_password);
                    EditText mEtEmail = (EditText)registerLayout.findViewById(R.id.et_register_email);
                    String username = mEtUsername1.getText().toString().trim();
                    String password = mEtPassword1.getText().toString().trim();
                    String verifiedPassword = mEtVerifiedPassword.getText().toString().trim();
                    String email = mEtEmail.getText().toString().trim();
                    if (TextUtils.equals(password, verifiedPassword))
                    {
                        userRegister(username, password, email);
                    }
                    else
                    {
                        Toast.makeText(this, "两次输入的密码要一致！", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void userRegister(String username, String password, String email)
    {
        if (!(TextUtils.equals(username, "") && TextUtils.equals(password, "") && TextUtils.equals(email, "")))
        {
            AVUser user = new AVUser();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.signUpInBackground(new SignUpCallback()
            {
                @Override
                public void done(AVException e)
                {
                    if (e == null)
                    {
                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "该三项为必填项，不能为空！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 重置密码
     */
    private void showResetPasswordDialog()
    {
        LayoutInflater inflater = (LayoutInflater)this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout resetPasswordLayout = (LinearLayout)inflater
                .inflate(R.layout.dialog_reset_password, null);

        new AlertDialog.Builder(this)
                .setView(resetPasswordLayout)
                .setTitle("重置密码")
                .setPositiveButton("确定", (dialogInterface, i) ->
                {
                    EditText mEtEmail = (EditText)resetPasswordLayout.findViewById(R.id.et_reset_password_email);
                    String email = mEtEmail.getText().toString().trim();
                    userResetPassword(email);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void userResetPassword(String email)
    {
        if (!TextUtils.equals(email, ""))
        {
            AVUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback()
            {
                @Override
                public void done(AVException e)
                {
                    if (e == null)
                    {
                        Toast.makeText(LoginActivity.this, "重置密码邮件已经发送到邮箱", Toast.LENGTH_SHORT).show();
                    }
                    else 
                    {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else 
        {
            Toast.makeText(this, "请输入注册时填写的邮箱", Toast.LENGTH_SHORT).show();
        }
    }
}
