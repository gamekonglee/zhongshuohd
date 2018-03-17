package bc.juhaohd.com.ui.fragment.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiiu.filter.util.UIUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseFragment;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.HomeIndexController;
import bc.juhaohd.com.ui.activity.CartActivity;
import bc.juhaohd.com.ui.activity.HomeShowNewActivity;
import bc.juhaohd.com.ui.activity.IssueApplication;
import bc.juhaohd.com.ui.activity.MainNewActivity;
import bc.juhaohd.com.ui.activity.MainNewForJuHaoActivity;
import bc.juhaohd.com.ui.activity.TimeBuyActivity;
import bc.juhaohd.com.ui.activity.programme.DiyActivity;
import bc.juhaohd.com.ui.activity.programme.MatchHomeActivity;
import bc.juhaohd.com.ui.activity.programme.ScreenActivity;
import bc.juhaohd.com.ui.activity.user.WebActivity;
import bc.juhaohd.com.ui.view.popwindow.VideoPopWindow;
import bc.juhaohd.com.utils.MyShare;
import bc.juhaohd.com.utils.UIUtils;
import bocang.utils.AppUtils;
import bocang.utils.MyToast;

import static bc.juhaohd.com.ui.activity.IssueApplication.mUserObject;

/**
 * Created by DEMON on 2018/1/18.
 */
public class HomeIndexFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_juhao;
    private TextView tv_solution;
    private TextView tv_buytime;
    private TextView tv_match;
    private TextView tv_360;
    private TextView tv_cart;
    private TextView tv_audio;
    private ImageView iv_code;
    private HomeIndexController mController;
    public TextView tv_server;
    private TextView tv_screen;
    private Bitmap bitmap_audio;
    private Bitmap bitmap_cart;
    private Bitmap bitmap_qr_code;
    private Bitmap bitmap_solution;
    private Bitmap bitmap_match;
    private ImageView iv_audio;
    private ImageView iv_cart;
    private ImageView iv_qr_code;
    private ImageView iv_solution;
    private ImageView iv_match;
    private TextView tv_code;
    private ImageView iv_360;
    private Bitmap bitmap_360;
    private ImageView iv_screen;
    private Bitmap bitmap_screen;
    private ImageView iv_temai;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.frament_home_index,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void initController() {
        mController = new HomeIndexController(this);
    }

    @Override
    protected void initViewData() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        tv_juhao = (TextView) getView().findViewById(R.id.tv_juhao);
        tv_solution = (TextView) getView().findViewById(R.id.tv_solution);
        tv_buytime = (TextView) getView().findViewById(R.id.tv_buytime);
        tv_match = (TextView) getView().findViewById(R.id.tv_match);
        tv_360 = (TextView) getView().findViewById(R.id.tv_360);
        tv_cart = (TextView) getView().findViewById(R.id.tv_cart);
        tv_audio = (TextView) getView().findViewById(R.id.tv_audio);
        iv_code = (ImageView) getView().findViewById(R.id.iv_code);
        tv_server = getView().findViewById(R.id.tv_server);
        tv_screen = getView().findViewById(R.id.tv_screen);
        iv_audio = getView().findViewById(R.id.iv_audio);
        iv_cart = getView().findViewById(R.id.iv_cart);
        iv_qr_code = getView().findViewById(R.id.iv_qr_code);
        iv_solution = getView().findViewById(R.id.iv_solution);
        iv_match = getView().findViewById(R.id.iv_match);
        tv_code = getView().findViewById(R.id.tv_code);
        iv_360 = getView().findViewById(R.id.iv_360);
        iv_screen = getView().findViewById(R.id.iv_screen);
        iv_temai = getView().findViewById(R.id.iv_temai);

        bitmap_audio = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_video);
        iv_audio.setImageBitmap(bitmap_audio);
        bitmap_cart = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_shopping);
        iv_cart.setImageBitmap(bitmap_cart);
        bitmap_qr_code = UIUtils.readBitMap(getActivity(), R.mipmap.qr_code);
        iv_qr_code.setImageBitmap(bitmap_qr_code);
        bitmap_solution = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_match);
        iv_solution.setImageBitmap(bitmap_solution);
        bitmap_match = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_diy);
        iv_match.setImageBitmap(bitmap_match);

        bitmap_360 = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_360);
        iv_360.setImageBitmap(bitmap_360);
        bitmap_screen = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_screen);
        iv_screen.setImageBitmap(bitmap_screen);

        tv_juhao.setOnClickListener(this);
        tv_solution.setOnClickListener(this);
        tv_buytime.setOnClickListener(this);
        tv_match.setOnClickListener(this);
        tv_360.setOnClickListener(this);
        tv_cart.setOnClickListener(this);
        tv_audio.setOnClickListener(this);
        tv_screen.setOnClickListener(this);
        iv_temai.setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();



    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventA(bc.juhaohd.com.bean.Message msg) {
//        LogUtils.logE("event bus",msg.getMsg()+"");
//        startActivity(new Intent(getActivity(),TestSwitchActivity.class));
        if(mUserObject==null)
        {
            tv_server.setText("");
            tv_code.setText("");
            return;
        }
        String yaoqing = mUserObject.getString(Constance.invite_code);
        if (!AppUtils.isEmpty(yaoqing))
            tv_code.setText("注册邀请码:" + yaoqing);

        if (!AppUtils.isEmpty(mUserObject.getString("parent_id"))) {
            if (mUserObject.getInt("parent_id") == 0) {
                MyShare.get(getActivity()).putInt(Constance.USERCODEID, mUserObject.getInt("id"));
            } else {
                MyShare.get(getActivity()).putInt(Constance.USERCODEID, mUserObject.getInt("parent_id"));
            }

        }
        if (!AppUtils.isEmpty(mUserObject.getString("parent_name"))) {
            MyShare.get(getActivity()).putString(Constance.USERCODE, mUserObject.getString("parent_name"));
        } else {
            MyShare.get(getActivity()).putString(Constance.USERCODE, mUserObject.getString("nickname"));
        }

        String user_name = MyShare.get(getActivity()).getString(Constance.USERCODE);
        String name = mUserObject.getString(Constance.username);
        if (AppUtils.isEmpty(user_name)) {

            tv_server.setText(name);
        } else {

            tv_server.setText(user_name);
        }
    /* Do something */
    }
    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        String token = MyShare.get(UIUtils.getContext()).getString(Constance.TOKEN);
        switch (v.getId()){
        case R.id.tv_juhao:
//            startActivity(new Intent(getActivity(), MainNewForJuHaoActivity.class));
//            if (AppUtils.isEmpty(token)) {
//                IntentUtil.startActivity(getActivity(), LoginActivity.class, false);
//            } else {
//
//                IntentUtil.startActivity(getActivity(), MainNewActivity.class, false);
//            }
            break;
        case R.id.tv_solution:
            startActivity(new Intent(getActivity(), MatchHomeActivity.class));
            break;
        case R.id.tv_buytime:
            startActivity(new Intent(getActivity(), TimeBuyActivity.class));
            break;
        case R.id.tv_match:
            startActivity(new Intent(getActivity(), DiyActivity.class));
            break;
        case R.id.tv_360:
            MyToast.show(getActivity(),"数据正在整理中");
//            Intent mIntent = new Intent(getActivity(), WebActivity.class);
//            mIntent.putExtra(Constance.url, "http://vr.justeasy.cn/view/273599.html");
//            this.startActivity(mIntent);
            break;
        case R.id.tv_cart:
            startActivity(new Intent(getActivity(),CartActivity.class));
            break;
        case R.id.tv_audio:
            MyToast.show(getActivity(),"数据正在整理中");
//            VideoPopWindow popWindow = new VideoPopWindow(getActivity().getBaseContext(), getActivity());
//            popWindow.onShow(((HomeShowNewActivity)getActivity()).main_rl);
            break;
            case R.id.tv_screen:
                startActivity(new Intent(getActivity(), ScreenActivity.class));
                break;
            case R.id.iv_temai:
                startActivity(new Intent(getActivity(), MainNewActivity.class));
                break;

    }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        if(bitmap_audio!=null)bitmap_audio.recycle();
        if(bitmap_cart!=null)bitmap_cart.recycle();
        if(bitmap_match!=null)bitmap_match.recycle();
        if(bitmap_qr_code!=null)bitmap_qr_code.recycle();
        if(bitmap_360!=null)bitmap_360.recycle();
        if(bitmap_screen!=null)bitmap_screen.recycle();
        bitmap_screen=null;
        bitmap_360=null;
        bitmap_match=null;
        bitmap_qr_code=null;
        bitmap_cart=null;
        bitmap_audio=null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }
}
