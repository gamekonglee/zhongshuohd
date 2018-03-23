package bc.juhaohd.com.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.HomeShowNewController;
import bc.juhaohd.com.ui.fragment.Home.HomeIndexFragment;
import bc.juhaohd.com.ui.fragment.Home.MineNewFragment;
import bc.juhaohd.com.ui.fragment.Home.SpaceFragment;
import bc.juhaohd.com.ui.fragment.Home.StyleFragment;
import bc.juhaohd.com.ui.fragment.Home.TypeFragment;
import bc.juhaohd.com.ui.fragment.SceneHDFragment;
import bc.juhaohd.com.ui.view.BottomBarOfHome;
import bc.juhaohd.com.utils.UIUtils;

public class HomeShowNewActivity extends BaseActivity {

//    public TextView tel_tv,address_tv,operator_tv,two_code_tv;
    private HomeShowNewController mHomeShowController;
    public static int mFragmentState=0;
    public static HomeIndexFragment mHomeFragment;
    Fragment currentFragmen;
    public BottomBarOfHome bottom_bar;
    public    TypeFragment mTypeFragment;
    public   SpaceFragment mSpaceFragment;
    public  StyleFragment mStyleFragment;
    public LinearLayout main_rl;
    public  MineNewFragment mMineFragment;
    public ImageView iv_left;
    public ImageView iv_right;
    private SceneHDFragment mSceenSelect;
    private boolean isAppInFront;
    private static HomeShowNewActivity homeShowNewActivity;
    private LinearLayout fl_content;

    public static HomeShowNewActivity getInstance() {
        if(homeShowNewActivity ==null) homeShowNewActivity =new HomeShowNewActivity();
        return homeShowNewActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(HomeShowNewActivity.this, "oncreate", Toast.LENGTH_SHORT).show();
        String packageName = this.getPackageName();
        homeShowNewActivity=this;
        ActivityManager activityManager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appTask = activityManager.getRunningTasks(1);
        if (appTask != null)
            if(appTask.size()>0)
                if(appTask.get(0).topActivity.toString().contains(packageName))
                    isAppInFront = true;

        checkUI();
    }

    public void checkUI() {
        if(mFragmentState==0){
            iv_left.setVisibility(View.INVISIBLE);
            iv_right.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(UIUtils.dip2PX(Integer.parseInt(getString(R.string.home_marign))),UIUtils.dip2PX(Integer.parseInt(getString(R.string.home_marign_top))),UIUtils.dip2PX(Integer.parseInt(getString(R.string.home_marign))),0);
            fl_content.setLayoutParams(layoutParams);
        }else {
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(UIUtils.dip2PX(Integer.parseInt(getString(R.string.home_marign2))),UIUtils.dip2PX(Integer.parseInt(getString(R.string.home_marign_top))),UIUtils.dip2PX(Integer.parseInt(getString(R.string.home_marign2))),0);
            fl_content.setLayoutParams(layoutParams);
            iv_left.setVisibility(View.VISIBLE);
            iv_right.setVisibility(View.VISIBLE);
        }
        if(mFragmentState==6){
            main_rl.setBackground(getResources().getDrawable(R.mipmap.bg_home));
        }else {
            main_rl.setBackground(getResources().getDrawable(R.mipmap.bg_orange));
        }
    }

    @Override
    protected void InitDataView() {

    }

    @Override
    protected void initController() {
        mHomeShowController = new HomeShowNewController(this);

    }

    @Override
    protected void initView() {
//        tel_tv = (TextView) findViewById(R.id.tel_tv);
//        address_tv = (TextView) findViewById(R.id.address_tv);
//        operator_tv = (TextView) findViewById(R.id.operator_tv);
//        two_code_tv = (TextView) findViewById(R.id.two_code_tv);
        setContentView(R.layout.activity_home_show_new);
        bottom_bar = (BottomBarOfHome) findViewById(R.id.title_bar01);
        bottom_bar.setOnClickListener(mBottomBarClickListener);
        main_rl = (LinearLayout) findViewById(R.id.main_rl);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        fl_content = findViewById(R.id.fl_content);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFragmentState>0){
                    mFragmentState--;
                    if(mFragmentState==5){
                        mFragmentState--;
                    }
                    if(mFragmentState==4){
                        mFragmentState--;
                    }

                    refresUI();
                }
            }
        });
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFragmentState<7){
                    mFragmentState++;
                    if(mFragmentState==4){
                        mFragmentState++;
                    }
                    if(mFragmentState==5){
                        mFragmentState++;
                    }
                    refresUI();
                }

            }
        });
        initTab();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        if (!isFinishing()) {
            super.onResume();
//            mFragmentState=6;
            refresUI();
        }
        mHomeShowController.sendUser();
//        Toast.makeText(HomeShowNewActivity.this, "oncreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onViewClick(View v) {

    }


    public void refresUI() {

        int tabid=R.id.frag_home_tv;
        switch (mFragmentState){
            case 0:
                tabid=R.id.frag_home_tv;
                break;
            case 1:
                tabid=R.id.frag_style_tv;
                break;
            case 2:
                tabid=R.id.frag_type_tv;
                break;
            case 3:
                tabid=R.id.frag_space_tv;
                break;
            case 4:
                tabid=R.id.frag_search_tv;
                break;
            case 5:
                tabid=R.id.frag_setting_tv;
//                tabid=R.id.frag_type_tv;
                if(isToken()){
                    return;
                }
                break;
            case 6:
                if(isToken()){
                    return;
                }
                tabid=R.id.frag_mine_tv;
                break;
        }
        if(main_rl==null||bottom_bar==null)return;
        if(mFragmentState==6){
            main_rl.setBackground(getResources().getDrawable(R.mipmap.bg_home));
        }else {
            main_rl.setBackground(getResources().getDrawable(R.mipmap.bg_orange));
        }
        bottom_bar.selectItem(tabid);
        checkUI();

    }

    /**
     * 初始化底部标签
     */
    public void initTab() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeIndexFragment();
        }
        if (!mHomeFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_content, mHomeFragment).commit();

            // 记录当前Fragment
            currentFragmen = mHomeFragment;
        }
//        if (mMineFragment == null) {
//            mMineFragment = new MineNewFragment();
//        }
//        if (!mMineFragment.isAdded()) {
//            // 提交事务
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fl_content, mMineFragment).commit();
//
//            // 记录当前Fragment
//            currentFragmen = mMineFragment;
//        }


    }
    public BottomBarOfHome.IBottomBarItemClickListener mBottomBarClickListener = new BottomBarOfHome.IBottomBarItemClickListener() {
        @Override
        public void OnItemClickListener(int resId) {
            switch (resId) {

                case R.id.frag_home_tv:
                    mFragmentState = 0;
                    clickTab1Layout();
                    break;
                case R.id.frag_style_tv:
                    mFragmentState=1;
                    clickTab4Layout();
                    break;
                case R.id.frag_type_tv:
                    mFragmentState=2;
                    clickTab2Layout();
                    break;
                case R.id.frag_space_tv:
                    mFragmentState=3;
                    clickTab3Layout();
                    break;

                case R.id.frag_search_tv:
                    clickTab5Layout();
                    break;
                case R.id.frag_setting_tv:
//                    mFragmentState=5;
                    if(isToken()){
                        return;
                    }
                    clickTab6Layout();
                    break;
                case R.id.frag_mine_tv:
                    if(isToken()){
                        return;
                    }
                    mFragmentState=6;
                    clickTab7Layout();
                    break;
//                case R.id.frag_diy_ll:
//                    mFragmentState = 1;
//                    clickTab2Layout();
//                    break;
//                case R.id.frag_match_ll:
//                    mFragmentState = 2;
//                    clickTab3Layout();
//                    break;
//                case R.id.frag_cart_ll:
//                    mFragmentState = 3;
//                    clickTab4Layout();
//                    break;
//                case R.id.frag_mine_ll:
//                    mFragmentState = 4;
//                    clickTab5Layout();
//                    break;
//                case R.id.frag_home_ll:
//                    mFragmentState = 5;
//                    clickTab6Layout();
//                    break;
            }
            checkUI();
        }




    };



    private void clickTab7Layout() {
        if (mMineFragment == null) {
            mMineFragment = new MineNewFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mMineFragment);
    }

    private void clickTab6Layout() {
//        if (mSceenSelect == null) {
//            mSceenSelect = new SceneHDFragment();
//        }
//        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mSceenSelect);

        startActivity(new Intent(this,SettingNewActivity.class));
    }

    private void clickTab4Layout() {
        if (mStyleFragment == null) {
            mStyleFragment = new StyleFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mStyleFragment);
    }

    private void clickTab3Layout() {
        if (mSpaceFragment == null) {
            mSpaceFragment = new SpaceFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mSpaceFragment);
    }

    private void clickTab5Layout() {
        startActivity(new Intent(this,SearchActivity.class));
    }
    private void clickTab2Layout() {
        if (mTypeFragment == null) {
            mTypeFragment = new TypeFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mTypeFragment);
    }

    /**
     * 点击第1个tab
     */
    public void clickTab1Layout() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeIndexFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mHomeFragment);

    }
    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragmen == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragmen)
                    .add(R.id.fl_content, fragment).commit();
        } else {
            transaction.hide(currentFragmen).show(fragment).commit();
        }

        currentFragmen = fragment;
    }
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ( secondTime - firstTime < 2000) {
                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                System.exit(0);
            } else {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public  void clearAll(){

        if(mTypeFragment!=null)mTypeFragment.onDestroy();
//        if(mMineFragment!=null)mMineFragment.onDestroy();
        if(mSpaceFragment!=null)mSpaceFragment.onDestroy();
        if(mStyleFragment!=null)mStyleFragment.onDestroy();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
      mHomeShowController.onRequestPermissionResult(requestCode,permissions,grantResults);
    }
}
