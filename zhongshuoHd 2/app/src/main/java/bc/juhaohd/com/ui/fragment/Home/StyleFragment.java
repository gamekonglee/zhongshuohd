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

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseFragment;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.StyleController;
import bc.juhaohd.com.ui.activity.MainNewActivity;
import bc.juhaohd.com.utils.MyShare;
import bc.juhaohd.com.utils.UIUtils;
import bocang.utils.AppUtils;

import static bc.juhaohd.com.ui.activity.IssueApplication.mUserObject;

/**
 * Created by DEMON on 2018/1/19.
 */
public class StyleFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_xiandaijianyue;
    private TextView tv_oushi;
    private TextView tv_zhongshi;
    private TextView tv_xinzhongshi;
    private TextView tv_quanbu;
    private TextView tv_meishi;
    private TextView tv_yijia;
    private TextView tv_houxiandai;
    private TextView tv_tianyuan;
    public TextView tv_server;
    private StyleController styleController;
    private ImageView iv_xdjy;
    private ImageView iv_zs;
    private ImageView iv_xzs;
    private ImageView iv_os;
    private ImageView iv_ms;
    private ImageView iv_hxd;
    private ImageView iv_yj;
    private ImageView iv_ty;
    private ImageView iv_qb;
    private Bitmap icon_zs_xdjy;
    private Bitmap icon_zs_os;
    private Bitmap icon_zs_zs;
    private Bitmap icon_zs_xzs;
    private Bitmap icon_zs_ms;
    private Bitmap icon_zs_yj;
    private Bitmap icon_zs_hxd;
    private Bitmap icon_zs_style_qb;
    private Bitmap icon_zs_ty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_style,null);
    }

    @Override
    protected void initController() {
        styleController = new StyleController(this);
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected void initView() {
        tv_xiandaijianyue = (TextView) getView().findViewById(R.id.tv_xiandaijianyue);
        tv_oushi = (TextView) getView().findViewById(R.id.tv_oushi);
        tv_zhongshi = (TextView) getView().findViewById(R.id.tv_zhongshi);
        tv_xinzhongshi = (TextView) getView().findViewById(R.id.tv_xinzhongshi);
        tv_quanbu = (TextView) getView().findViewById(R.id.tv_quanbu);
        tv_meishi = (TextView) getView().findViewById(R.id.tv_meishi);
        tv_yijia = (TextView) getView().findViewById(R.id.tv_yijia);
        tv_houxiandai = (TextView) getView().findViewById(R.id.tv_houxiandai);
        tv_tianyuan = (TextView) getView().findViewById(R.id.tv_tianyuan);
        tv_server = getView().findViewById(R.id.tv_server);

        iv_xdjy = getView().findViewById(R.id.iv_xdjy);
        iv_zs = getView().findViewById(R.id.iv_zs);
        iv_xzs = getView().findViewById(R.id.iv_xzs);
        iv_os = getView().findViewById(R.id.iv_os);
        iv_ms = getView().findViewById(R.id.iv_ms);
        iv_hxd = getView().findViewById(R.id.iv_hxd);
        iv_yj = getView().findViewById(R.id.iv_yj);
        iv_ty = getView().findViewById(R.id.iv_ty);
        iv_qb = getView().findViewById(R.id.iv_qb);

        icon_zs_xdjy = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_xdjy);
        icon_zs_os = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_os);
        icon_zs_zs = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_zs);
        icon_zs_xzs = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_xzs);
        icon_zs_ms = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_ms);
        icon_zs_yj = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_yj);
        icon_zs_hxd = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_hxd);
        icon_zs_style_qb = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_style_qb);
        icon_zs_ty = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_ty);

        iv_xdjy.setImageBitmap(icon_zs_xdjy);
        iv_zs.setImageBitmap(icon_zs_zs);
        iv_xzs.setImageBitmap(icon_zs_xzs);
        iv_os.setImageBitmap(icon_zs_os);
        iv_ms.setImageBitmap(icon_zs_ms);
        iv_yj.setImageBitmap(icon_zs_yj);
        iv_hxd.setImageBitmap(icon_zs_hxd);
        iv_qb.setImageBitmap(icon_zs_style_qb);
        iv_ty.setImageBitmap(icon_zs_ty);

        tv_xiandaijianyue.setOnClickListener(this);
        tv_oushi.setOnClickListener(this);
        tv_zhongshi.setOnClickListener(this);
        tv_xinzhongshi.setOnClickListener(this);
        tv_quanbu.setOnClickListener(this);
        tv_meishi.setOnClickListener(this);
        tv_yijia.setOnClickListener(this);
        tv_houxiandai.setOnClickListener(this);
        tv_tianyuan.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        if(icon_zs_xdjy!=null)icon_zs_xdjy.recycle();
        icon_zs_xdjy=null;
        if(icon_zs_zs!=null)icon_zs_zs.recycle();
        icon_zs_zs=null;
        if(icon_zs_xzs!=null)icon_zs_xzs.recycle();
        icon_zs_xzs=null;
        if(icon_zs_os!=null)icon_zs_os.recycle();
        icon_zs_os=null;
        if(icon_zs_ms!=null)icon_zs_ms.recycle();
        icon_zs_ms=null;
        if(icon_zs_yj!=null)icon_zs_yj.recycle();
        icon_zs_yj=null;
        if(icon_zs_hxd!=null)icon_zs_hxd.recycle();
        icon_zs_hxd=null;
        if(icon_zs_style_qb!=null)icon_zs_style_qb.recycle();
        icon_zs_style_qb=null;
        if(icon_zs_ty!=null)icon_zs_ty.recycle();
        icon_zs_ty=null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), MainNewActivity.class);
        String filterType="";
        switch (v.getId()){
            case R.id.tv_xiandaijianyue:
                filterType="现代简约";
                break;
            case R.id.tv_oushi:
                filterType="欧式";
                break;
            case R.id.tv_zhongshi:
                filterType="中式";
                break;
            case R.id.tv_xinzhongshi:
                filterType="新中式";
                break;
            case R.id.tv_quanbu:
                filterType="全部";
                break;
            case R.id.tv_meishi:
                filterType="美式";
                break;
            case R.id.tv_yijia:
                filterType="宜家";
                break;
            case R.id.tv_houxiandai:
                filterType="后现代";
                break;
            case R.id.tv_tianyuan:
                filterType="田园";
                break;
        }
        intent.putExtra(Constance.filter_attr_name,filterType);
        startActivity(intent);

    }
}
