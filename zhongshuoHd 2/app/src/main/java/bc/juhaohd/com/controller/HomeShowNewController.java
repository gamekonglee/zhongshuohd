package bc.juhaohd.com.controller;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import bc.juhaohd.com.R;
import bc.juhaohd.com.bean.AppVersion;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.listener.INetworkCallBack;
import bc.juhaohd.com.net.ApiClient;
import bc.juhaohd.com.ui.activity.HomeShowNewActivity;
import bc.juhaohd.com.ui.view.BottomBarOfHome;
import bc.juhaohd.com.utils.MyShare;
import bc.juhaohd.com.utils.NetWorkUtils;
import bc.juhaohd.com.utils.upload.UpAppUtils;
import bocang.json.JSONObject;
import bocang.utils.AppUtils;
import bocang.utils.CommonUtil;
import bocang.utils.LogUtils;
import bocang.utils.MyToast;
import okhttp3.Call;
import okhttp3.Response;

import static bc.juhaohd.com.ui.activity.IssueApplication.mUserObject;

/**
 * Created by DEMON on 2018/1/18.
 */
public class HomeShowNewController extends BaseController implements INetworkCallBack{
    private HomeShowNewActivity mView;
    private String mAppVersion;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private AppVersion appVersion;

    public HomeShowNewController(HomeShowNewActivity homeShowNewActivity) {
        mView = homeShowNewActivity;
        initView();
        initViewData();
    }


    private void initViewData() {
        sendUser();
        sendVersion();
    }

    /**
     * 获取版本号
     */
    private void sendVersion() {
        mNetWork.sendVersion(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ans = NetWorkUtils.doGet(NetWorkConst.VERSION_URL_CONTENT);
                final JSONObject jsonObject=new JSONObject(ans);

                if (AppUtils.isEmpty(jsonObject))
                    return;
                mAppVersion=jsonObject.getString(Constance.version);
                String localVersion = CommonUtil.localVersionName(mView);
                if ("-1".equals(mAppVersion)) {

                } else {
                    boolean isNeedUpdate = CommonUtil.isNeedUpdate(localVersion, mAppVersion);
                    if (isNeedUpdate) {
                        mView.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(mView, R.style.customDialog);
                                dialog.setContentView(R.layout.dialog_update);
                                TextView tv_info= (TextView) dialog.findViewById(R.id.tv_update_info);
                                Button btn_upgrate= (Button) dialog.findViewById(R.id.btn_upgrate);
                                ImageView iv_close= (ImageView) dialog.findViewById(R.id.iv_close);
                                Button btn_dismiss=dialog.findViewById(R.id.btn_dismiss);
                                String updateInfo=jsonObject.getString(Constance.text);
                                tv_info.setText(""+ Html.fromHtml(updateInfo).toString());
                                dialog.show();
                                btn_dismiss.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                btn_upgrate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        appVersion = new AppVersion();
                                        appVersion.setVersion(mAppVersion);
                                        appVersion.setName(NetWorkConst.APK_NAME);
                                        appVersion.setDes("");
                                        appVersion.setForcedUpdate("0");
                                        appVersion.setUrl(NetWorkConst.DOWN_APK_URL);
                                        if (appVersion != null) {
                                            dialog.dismiss();

                                            if(Build.VERSION.SDK_INT>=23) { //判读版本是否在6.0以上{
                                                if (ContextCompat.checkSelfPermission(mView,
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                        != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(mView,
                                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//                                                    LogUtils.logE("sdk","request");
                                                }else{
                                                    //
//                                                    LogUtils.logE("sdk","no request");
                                                    new UpAppUtils(mView, appVersion);
                                                }
                                            } else {
//                                                LogUtils.logE("sdk<23","no request");
                                                new UpAppUtils(mView, appVersion);
                                            }

                                        }
                                    }
                                });
                                iv_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
//                                ShowDialog mDialog = new ShowDialog();
//                                mDialog.show(mView, "升级提示", "有最新的升级包，是否升级?", new ShowDialog.OnBottomClickListener() {
//                                    @Override
//                                    public void positive() {
//                                        //                                        broadcastReceiver = new UpdateApkBroadcastReceiver();
//                                        //                                        mView.registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//                                        //                                        Intent intent = new Intent(mView, UpdateApkService.class);
//                                        //                                        mView.startService(intent);
//                                        AppVersion appVersion = new AppVersion();
//                                        appVersion.setVersion(mAppVersion);
//                                        appVersion.setName(NetWorkConst.APK_NAME);
//                                        appVersion.setDes("");
//                                        appVersion.setForcedUpdate("0");
//                                        appVersion.setUrl(NetWorkConst.DOWN_APK_URL);
//                                        if (appVersion != null) {
//                                            new UpAppUtils(mView, appVersion);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void negtive() {
//                                        mView.finish();
//                                    }
//                                });
                            }
                        });


                    }
                }

            }
        }).start();

    }

    private void initView() {

    }

    /**
     * 获取用户信息
     */
    public void sendUser() {
        mNetWork.sendUser(this);
//
//        ApiClient.sendUser(NetWorkConst.PROFILE, new Callback<String>() {
//            @Override
//            public String parseNetworkResponse(Response response, int id) throws Exception {
//                return null;
//            }
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//
//            }
//        });
    }

    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }

    /**
     * 获取邀请码用户信息
     *
     * @param id
     */
    private void sendShopAddress(int id) {
        mNetWork.sendShopAddress(id, new INetworkCallBack() {
            @Override
            public void onSuccessListener(String requestCode, JSONObject ans) {
                JSONObject jsonObject = ans.getJSONObject(Constance.shop);
                if (AppUtils.isEmpty(jsonObject))
                    return;

                //                String parent_name =jsonObject.getString(Constance.parent_name);
                //                mView.operator_tv.setText(parent_name);
//                mView.tel_tv.setText("联系方式:" + jsonObject.getString(Constance.phone));
//                mView.address_tv.setText("商家地址:" + jsonObject.getString(Constance.address));
            }

            @Override
            public void onFailureListener(String requestCode, JSONObject ans) {
                MyToast.show(mView, "数据异常，请重试!");
            }
        });
    }


    @Override
    public void onSuccessListener(String requestCode, JSONObject ans) {
        switch (requestCode) {
            case NetWorkConst.PROFILE:
//                LogUtils.logE("userInfo",ans.toString());
                mUserObject = ans.getJSONObject(Constance.user);
                int userId = 0;
                if (!AppUtils.isEmpty(mUserObject.getString("parent_id"))) {
                    if (mUserObject.getInt("parent_id") == 0) {
                        MyShare.get(mView).putInt(Constance.USERCODEID, mUserObject.getInt("id"));
                        userId = mUserObject.getInt("id");
                    } else {
                        MyShare.get(mView).putInt(Constance.USERCODEID, mUserObject.getInt("parent_id"));
                        userId = mUserObject.getInt("parent_id");
                    }

                }

                if (!AppUtils.isEmpty(mUserObject.getString("parent_name"))) {
//                    mView.operator_tv.setText(mUserObject.getString("parent_name"));
                } else {
//                    mView.operator_tv.setText(mUserObject.getString("nickname"));
                }

                String yaoqing = mUserObject.getString(Constance.invite_code);
//                if(HomeShowNewActivity.mStyleFragment!=null)HomeShowNewActivity.mStyleFragment.onStart();
//                if(HomeShowNewActivity.mSpaceFragment!=null)HomeShowNewActivity.mSpaceFragment.onStart();
//                if(HomeShowNewActivity.mTypeFragment!=null)HomeShowNewActivity.mTypeFragment.onStart();
//                if(HomeShowNewActivity.mHomeFragment!=null)HomeShowNewActivity.mHomeFragment.onStart();
                EventBus.getDefault().postSticky(new bc.juhaohd.com.bean.Message(1,"1"));
//                if (!AppUtils.isEmpty(yaoqing))
//                    mView.two_code_tv.setText("注册邀请码:" + yaoqing);

//                mView.setContentView(R.layout.activity_home_show_new);
//                mView.bottom_bar = (BottomBarOfHome) mView.findViewById(R.id.title_bar01);
//                mView.bottom_bar.setOnClickListener(mView.mBottomBarClickListener);
//                mView.main_rl = (LinearLayout) mView.findViewById(R.id.main_rl);
//                mView.iv_left = (ImageView) mView.findViewById(R.id.iv_left);
//                mView.iv_right = (ImageView) mView.findViewById(R.id.iv_right);
//                mView.iv_left.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(mView.mFragmentState>0){
//                            mView.mFragmentState--;
//                            if(mView.mFragmentState==5){
//                                mView.mFragmentState--;
//                            }
//                            if(mView.mFragmentState==4){
//                                mView.mFragmentState--;
//                            }
//
//                            mView.refresUI();
//                        }
//                    }
//                });
//                mView.iv_right.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(mView.mFragmentState<7){
//                            mView.mFragmentState++;
//                            if(mView.mFragmentState==4){
//                                mView.mFragmentState++;
//                            }
//                            if(mView.mFragmentState==5){
//                                mView.mFragmentState++;
//                            }
//                            mView.refresUI();
//                        }
//
//                    }
//                });
//                mView.initTab();
//                mView.checkUI();
//                mView.refresUI();
                sendShopAddress(userId);
                break;
            case NetWorkConst.VERSION_URL:
                LogUtils.logE("URL",""+ans);
                break;
        }
    }

    @Override
    public void onFailureListener(String requestCode, JSONObject ans) {
        if (null == mView || mView.isFinishing())
            return;

    }
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                new UpAppUtils(mView, appVersion);
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
