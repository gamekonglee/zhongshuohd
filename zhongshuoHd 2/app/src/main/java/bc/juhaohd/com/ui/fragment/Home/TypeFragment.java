package bc.juhaohd.com.ui.fragment.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiiu.filter.util.UIUtil;
import com.pgyersdk.crash.PgyCrashManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseFragment;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.TypeController;
import bc.juhaohd.com.ui.activity.HomeShowNewActivity;
import bc.juhaohd.com.ui.activity.MainNewActivity;
import bc.juhaohd.com.utils.MyShare;
import bc.juhaohd.com.utils.UIUtils;
import bocang.utils.AppUtils;

import static bc.juhaohd.com.ui.activity.IssueApplication.mUserObject;

/**
 * Created by DEMON on 2018/1/18.
 */
public class TypeFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_diaodeng;
    private TextView tv_taideng;
    private TextView tv_bideng;
    private TextView tv_luodideng;
    private TextView tv_xidingdeng;
    private TextView tv_shangyezhaoming;
    private TextView tv_quanbu;
    public TextView tv_server;
    private TypeController typeController;
    private Bitmap icon_zs_dd;
    private Bitmap icon_zs_xdd;
    private Bitmap icon_zs_td;
    private Bitmap icon_zs_bd;
    private Bitmap icon_zs_ldd;
    private Bitmap icon_zs_jqd;
    private Bitmap icon_zs_type_qb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_type,null);
    }

    @Override
    protected void initController() {
        typeController = new TypeController(this);

    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected void initView() {
        tv_diaodeng = (TextView) getView().findViewById(R.id.tv_diaodeng);
        tv_taideng = (TextView) getView().findViewById(R.id.tv_taideng);
        tv_bideng = (TextView) getView().findViewById(R.id.tv_bideng);
        tv_luodideng = (TextView) getView().findViewById(R.id.tv_luodideng);
        tv_xidingdeng = (TextView) getView().findViewById(R.id.tv_xidingdeng);
        tv_shangyezhaoming = (TextView) getView().findViewById(R.id.tv_shangyezhaoming);
        tv_quanbu = (TextView) getView().findViewById(R.id.tv_quanbu);
        tv_server = getView().findViewById(R.id.tv_server);
        ImageView iv_dd=getView().findViewById(R.id.iv_dd);
        ImageView iv_xdd=getView().findViewById(R.id.iv_xdd);
        ImageView iv_td=getView().findViewById(R.id.iv_td);
        ImageView iv_bd=getView().findViewById(R.id.iv_bd);
        ImageView iv_ldd=getView().findViewById(R.id.iv_ldd);
        ImageView iv_jqd=getView().findViewById(R.id.iv_jqd);
        ImageView iv_qb=getView().findViewById(R.id.iv_qb);

        icon_zs_dd = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_dd);
        icon_zs_xdd = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_xdd);
        icon_zs_td = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_td);
        icon_zs_bd = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_bd);
        icon_zs_ldd = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_ldd);
        icon_zs_jqd = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_jqd);
        icon_zs_type_qb = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_type_qb);

        iv_dd.setImageBitmap(icon_zs_dd);
        iv_xdd.setImageBitmap(icon_zs_xdd);
        iv_td.setImageBitmap(icon_zs_td);
        iv_bd.setImageBitmap(icon_zs_bd);
        iv_ldd.setImageBitmap(icon_zs_ldd);
        iv_jqd.setImageBitmap(icon_zs_jqd);
        iv_qb.setImageBitmap(icon_zs_type_qb);


        tv_diaodeng.setOnClickListener(this);
        tv_taideng.setOnClickListener(this);
        tv_bideng.setOnClickListener(this);
        tv_luodideng.setOnClickListener(this);
        tv_xidingdeng.setOnClickListener(this);
        tv_shangyezhaoming.setOnClickListener(this);
        tv_quanbu.setOnClickListener(this);
        iv_dd.setOnClickListener(this);
        iv_xdd.setOnClickListener(this);
        iv_td.setOnClickListener(this);
        iv_bd.setOnClickListener(this);
        iv_ldd.setOnClickListener(this);
        iv_jqd.setOnClickListener(this);
        iv_qb.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        if(icon_zs_dd!=null)icon_zs_dd.recycle();
        icon_zs_dd=null;
        if(icon_zs_xdd!=null)icon_zs_xdd.recycle();
        icon_zs_xdd=null;
        if(icon_zs_td!=null)icon_zs_td.recycle();
        icon_zs_td=null;
        if(icon_zs_bd!=null)icon_zs_bd.recycle();
        icon_zs_bd=null;
        if(icon_zs_ldd!=null)icon_zs_ldd.recycle();
        icon_zs_ldd=null;
        if(icon_zs_jqd!=null)icon_zs_jqd.recycle();
        icon_zs_jqd=null;
        if(icon_zs_type_qb!=null)icon_zs_type_qb.recycle();
        icon_zs_type_qb=null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            EventBus.getDefault().register(this);
        }catch (Exception e){
            e=new Exception("typeFrag");
            PgyCrashManager.reportCaughtException(getActivity(),e);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventA(bc.juhaohd.com.bean.Message msg) {
//        LogUtils.logE("event bus",msg.getMsg()+"");
//        startActivity(new Intent(getActivity(),TestSwitchActivity.class));
        if(mUserObject==null)
        {
            tv_server.setText("");
//            tv_code.setText("");
            return;
        }
        String yaoqing = mUserObject.getString(Constance.invite_code);
        if (!AppUtils.isEmpty(yaoqing))
//            tv_code.setText("注册邀请码:" + yaoqing);

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
        Intent intent=new Intent(getActivity(), MainNewActivity.class);
        String filterType="全部";
        switch (v.getId()){
            case  R.id.tv_diaodeng:
            case R.id.iv_dd:
                filterType="吊灯";
                break;
            case R.id.iv_td:
            case R.id.tv_taideng:
                filterType="台灯";
                break;
            case R.id.iv_bd:
            case R.id.tv_bideng:
                filterType="壁灯";
                break;
            case R.id.iv_ldd:
            case R.id.tv_luodideng:
                filterType="落地灯";
                break;
            case R.id.iv_xdd:
            case R.id.tv_xidingdeng:
                filterType="吸顶灯";
                break;
            case R.id.iv_jqd:
            case R.id.tv_shangyezhaoming:
                filterType="镜前灯";
                break;
            case R.id.iv_qb:
            case R.id.tv_quanbu:
                filterType="餐吊";
                break;
        }
        intent.putExtra(Constance.filter_attr_name,filterType);
        startActivity(intent);
    }
}
