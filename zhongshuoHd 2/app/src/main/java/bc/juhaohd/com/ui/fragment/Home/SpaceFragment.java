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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseFragment;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.SpaceController;
import bc.juhaohd.com.ui.activity.HomeShowNewActivity;
import bc.juhaohd.com.ui.activity.MainNewActivity;
import bc.juhaohd.com.utils.MyShare;
import bc.juhaohd.com.utils.UIUtils;
import bocang.utils.AppUtils;

import static bc.juhaohd.com.ui.activity.IssueApplication.mUserObject;

/**
 * Created by DEMON on 2018/1/19.
 */
public class SpaceFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_keting;
    private TextView tv_ertongfang;
    private TextView tv_canting;
    private TextView tv_weiyujian;
    private TextView tv_louti;
    private TextView tv_quanbu;
    private TextView tv_zoulang;
    private TextView tv_woshi;
    private TextView tv_bieshu;
    private TextView tv_shufang;
    private TextView tv_yangtai;
    public TextView tv_server;
    private SpaceController spaceController;
    private Bitmap icon_zs_kt;
    private Bitmap icon_zs_etf;
    private Bitmap icon_zs_ct;
    private Bitmap icon_zs_ws;
    private Bitmap icon_zs_sf;
    private Bitmap icon_zs_wyj;
    private Bitmap icon_zs_zl;
    private Bitmap icon_zs_yt;
    private Bitmap icon_zs_lt;
    private Bitmap icon_zs_bs;
    private Bitmap icon_zs_space_qb;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_space,null);
    }

    @Override
    protected void initController() {
        spaceController = new SpaceController(this);

    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected void initView() {
        tv_keting = (TextView) getView().findViewById(R.id.tv_keting);
        tv_ertongfang = (TextView) getView().findViewById(R.id.tv_ertongfang);
        tv_canting = (TextView) getView().findViewById(R.id.tv_canting);
        tv_weiyujian = (TextView) getView().findViewById(R.id.tv_weiyujian);
        tv_louti = (TextView) getView().findViewById(R.id.tv_louti);
        tv_quanbu = (TextView) getView().findViewById(R.id.tv_quanbu);
        tv_zoulang = (TextView) getView().findViewById(R.id.tv_zoulang);
        tv_woshi = (TextView) getView().findViewById(R.id.tv_woshi);
        tv_bieshu = (TextView) getView().findViewById(R.id.tv_bieshu);
        tv_shufang = (TextView) getView().findViewById(R.id.tv_shufang);
        tv_yangtai = (TextView) getView().findViewById(R.id.tv_yangtai);
        tv_server = getView().findViewById(R.id.tv_server);

        ImageView iv_01=getView().findViewById(R.id.iv_kt);
        ImageView iv_02=getView().findViewById(R.id.iv_rtf);
        ImageView iv_03=getView().findViewById(R.id.iv_ct);
        ImageView iv_04=getView().findViewById(R.id.iv_wyj);
        ImageView iv_05=getView().findViewById(R.id.iv_lt);
        ImageView iv_06=getView().findViewById(R.id.iv_qb);
        ImageView iv_07=getView().findViewById(R.id.iv_gd);
        ImageView iv_08=getView().findViewById(R.id.iv_ws);
        ImageView iv_09=getView().findViewById(R.id.iv_bs);
        ImageView iv_10=getView().findViewById(R.id.iv_sf);
        ImageView iv_11=getView().findViewById(R.id.iv_yt);

        icon_zs_kt = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_kt);
        icon_zs_etf = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_etf);
        icon_zs_ct = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_ct);
        icon_zs_ws = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_ws);
        icon_zs_sf = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_sf);
        icon_zs_wyj = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_wyj);
        icon_zs_zl = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_zl);
        icon_zs_yt = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_yt);
        icon_zs_lt = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_lt);
        icon_zs_bs = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_bs);
        icon_zs_space_qb = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_space_qb);

        iv_01.setImageBitmap(icon_zs_kt);
        iv_02.setImageBitmap(icon_zs_etf);
        iv_03.setImageBitmap(icon_zs_ct);
        iv_04.setImageBitmap(icon_zs_wyj);
        iv_05.setImageBitmap(icon_zs_lt);
        iv_06.setImageBitmap(icon_zs_space_qb);
        iv_07.setImageBitmap(icon_zs_zl);
        iv_08.setImageBitmap(icon_zs_ws);
        iv_09.setImageBitmap(icon_zs_bs);
        iv_10.setImageBitmap(icon_zs_sf);
        iv_11.setImageBitmap(icon_zs_yt);

        tv_keting.setOnClickListener(this);
        tv_ertongfang.setOnClickListener(this);
        tv_canting.setOnClickListener(this);
        tv_weiyujian.setOnClickListener(this);
        tv_louti.setOnClickListener(this);
        tv_quanbu.setOnClickListener(this);
        tv_zoulang.setOnClickListener(this);
        tv_woshi.setOnClickListener(this);
        tv_bieshu.setOnClickListener(this);
        tv_shufang.setOnClickListener(this);
        tv_yangtai.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
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
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), MainNewActivity.class);
        String filername="全部";
        switch (v.getId()){
            case R.id.tv_keting:
                filername="客厅";
                break;
            case R.id.tv_ertongfang:
                filername="儿童房";
                break;
            case R.id.tv_canting:
                filername="餐厅";
                break;
            case R.id.tv_weiyujian:
                filername="厨卫";
                break;
            case R.id.tv_louti:
                filername="楼梯";
                break;
            case R.id.tv_quanbu:
                filername="全部";
                break;
            case R.id.tv_zoulang:
                filername="过道";
                break;
            case R.id.tv_woshi:
                filername="卧室";
                break;
            case R.id.tv_bieshu:
                filername="其他";
                break;
            case R.id.tv_shufang:
                filername="书房";
                break;
            case R.id.tv_yangtai:
                filername="阳台";
                break;

        }
        intent.putExtra(Constance.filter_attr_name,filername);
        startActivity(intent);

    }

    @Override
    public void onDestroy() {
        if(icon_zs_kt!=null)icon_zs_kt.recycle();
        icon_zs_kt=null;
        if(icon_zs_etf!=null)icon_zs_etf.recycle();
        icon_zs_etf=null;
        if(icon_zs_ct!=null)icon_zs_ct.recycle();
        icon_zs_ct=null;
        if(icon_zs_wyj!=null)icon_zs_wyj.recycle();
        icon_zs_wyj=null;
        if(icon_zs_zl!=null)icon_zs_zl.recycle();
        icon_zs_zl=null;
        if(icon_zs_yt!=null)icon_zs_yt.recycle();
        icon_zs_yt=null;
        if(icon_zs_lt!=null)icon_zs_lt.recycle();
        icon_zs_lt=null;
        if(icon_zs_space_qb!=null)icon_zs_space_qb.recycle();
        icon_zs_space_qb=null;
        if(icon_zs_ws!=null)icon_zs_ws.recycle();
        icon_zs_ws=null;
        if(icon_zs_bs!=null)icon_zs_bs.recycle();
        icon_zs_bs=null;
        if(icon_zs_sf!=null)icon_zs_sf.recycle();
        icon_zs_sf=null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
