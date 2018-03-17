package bc.juhaohd.com.ui.activity.user;

import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.controller.user.LoginController;
import bc.juhaohd.com.ui.activity.HomeShowNewActivity;

/**
 * @author: Jun
 * @date : 2017/2/7 13:42
 * @description :登录
 */
public class LoginActivity extends BaseActivity {
    private LoginController mController;
    private TextView typeTv,type02Tv;
    private Button login_bt;
    private TextView regiest_tv,find_pwd_tv;
    @Override
    protected void InitDataView() {

    }

    @Override
    protected void initController() {
        mController=new LoginController(this);


//        mController.phone_et.setText("admini");
//        mController.pwd_et.setText("admini");
//        mController.sendLogin();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login_new);
        //沉浸式状态栏
        setColor(this, getResources().getColor(R.color.colorPrimary));
//        type02Tv=getViewAndClick(R.id.type02Tv);
//        typeTv=getViewAndClick(R.id.typeTv);
        login_bt=getViewAndClick(R.id.login_bt);

//        regiest_tv=getViewAndClick(R.id.regiest_tv);
//        find_pwd_tv=getViewAndClick(R.id.find_pwd_tv);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.typeTv:
                mController.selectType(R.id.typeTv);
                break;
            case R.id.type02Tv:
                mController.selectType(R.id.type02Tv);
                break;
            case R.id.login_bt:
                mController.sendLogin();
//                mController.showShare("http://app.bocang.cc/Ewm/index/url/aikeshidun.bocang.cc","奥克特商城");
                break;
            case R.id.regiest_tv:
                mController.sendRegiest();
                break;
            case R.id.find_pwd_tv:
                mController.sendFindPwd();
                break;
        }
    }

    @Override
    public void goBack(View v) {
        HomeShowNewActivity.mFragmentState=0;
        super.goBack(v);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

        }
