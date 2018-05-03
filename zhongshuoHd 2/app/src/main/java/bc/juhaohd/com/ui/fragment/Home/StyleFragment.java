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

import com.pgyersdk.crash.PgyCrashManager;

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
    private TextView tv_qs;
    private TextView tv_bofe;
    private TextView tv_lcd;
    private TextView tv_cw;
    private TextView tv_gdd;
    private TextView tv_sz;
    private ImageView iv_qs;
    private ImageView iv_bofg;
    private ImageView iv_lcd;
    private ImageView iv_cw;
    private ImageView iv_gdd;
    private ImageView iv_sz;
    private Bitmap icon_zs_qingshe;
    private Bitmap icon_zs_beou;
    private Bitmap icon_zs_lcd;
    private Bitmap icon_zs_cw;
    private Bitmap icon_guodao;
    private Bitmap icon_shangzhao;

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
        tv_qs = getView().findViewById(R.id.tv_qs);
        tv_bofe = getView().findViewById(R.id.tv_bofg);
        tv_lcd = getView().findViewById(R.id.tv_lcd);
        tv_cw = getView().findViewById(R.id.tv_cw);
        tv_gdd = getView().findViewById(R.id.tv_gdd);
        tv_sz = getView().findViewById(R.id.tv_sz);

        iv_xdjy = getView().findViewById(R.id.iv_xdjy);
        iv_zs = getView().findViewById(R.id.iv_zs);
        iv_xzs = getView().findViewById(R.id.iv_xzs);
        iv_os = getView().findViewById(R.id.iv_os);
        iv_ms = getView().findViewById(R.id.iv_ms);
        iv_hxd = getView().findViewById(R.id.iv_hxd);
        iv_yj = getView().findViewById(R.id.iv_yj);
        iv_ty = getView().findViewById(R.id.iv_ty);
        iv_qb = getView().findViewById(R.id.iv_qb);
        iv_qs = getView().findViewById(R.id.iv_qs);
        iv_bofg = getView().findViewById(R.id.iv_bofg);
        iv_lcd = getView().findViewById(R.id.iv_lcd);
        iv_cw = getView().findViewById(R.id.iv_cw);
        iv_gdd = getView().findViewById(R.id.iv_gdd);
        iv_sz = getView().findViewById(R.id.iv_sz);


        icon_zs_xdjy = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_xiandaijianyue);
        icon_zs_os = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_oushi);
        icon_zs_zs = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_yakeli);
        icon_zs_xzs = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_xinzhongshi);
        icon_zs_ms = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_teyi);
        icon_zs_yj = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_yj);
        icon_zs_hxd = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_zhongshi);
        icon_zs_style_qb = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_fengshan);
        icon_zs_ty = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_ty);
        icon_zs_qingshe = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_qingshe);
        icon_zs_beou = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_beiou);
        icon_zs_lcd = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_lvcaiddeng);
        icon_zs_cw = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_chuwei);
        icon_guodao = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_guodaodeng);
        icon_shangzhao = UIUtils.readBitMap(getActivity(), R.mipmap.icon_zs_shangzhao);


        iv_xdjy.setImageBitmap(icon_zs_xdjy);
        iv_zs.setImageBitmap(icon_zs_zs);
        iv_xzs.setImageBitmap(icon_zs_xzs);
        iv_os.setImageBitmap(icon_zs_os);
        iv_ms.setImageBitmap(icon_zs_ms);
        iv_yj.setImageBitmap(icon_zs_yj);
        iv_hxd.setImageBitmap(icon_zs_hxd);
        iv_qb.setImageBitmap(icon_zs_style_qb);
        iv_ty.setImageBitmap(icon_zs_ty);
        iv_qs.setImageBitmap(icon_zs_qingshe);
        iv_bofg.setImageBitmap(icon_zs_beou);
        iv_lcd.setImageBitmap(icon_zs_lcd);
        iv_cw.setImageBitmap(icon_zs_cw);
        iv_gdd.setImageBitmap(icon_guodao);
        iv_sz.setImageBitmap(icon_shangzhao);

        tv_xiandaijianyue.setOnClickListener(this);
        tv_oushi.setOnClickListener(this);
        tv_zhongshi.setOnClickListener(this);
        tv_xinzhongshi.setOnClickListener(this);
        tv_quanbu.setOnClickListener(this);
        tv_meishi.setOnClickListener(this);
        tv_yijia.setOnClickListener(this);
        tv_houxiandai.setOnClickListener(this);
        tv_tianyuan.setOnClickListener(this);
        tv_qs.setOnClickListener(this);
        tv_bofe.setOnClickListener(this);
        tv_lcd.setOnClickListener(this);
        tv_cw.setOnClickListener(this);
        tv_gdd.setOnClickListener(this);
        tv_sz.setOnClickListener(this);
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
        if(icon_zs_qingshe!=null)icon_zs_qingshe.recycle();
        icon_zs_qingshe=null;
        if(icon_zs_beou!=null)icon_zs_beou.recycle();
        icon_zs_beou=null;
        if(icon_zs_lcd!=null)icon_zs_lcd.recycle();
        icon_zs_lcd=null;
        if(icon_zs_cw!=null)icon_zs_cw.recycle();
        icon_zs_cw=null;
        if(icon_guodao!=null)icon_guodao.recycle();
        icon_guodao=null;
        if(icon_shangzhao!=null)icon_shangzhao.recycle();
        icon_shangzhao=null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            EventBus.getDefault().register(this);
        }catch (Exception e){
            e=new Exception("styleFrag");
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
        String filterType="";
        switch (v.getId()){
            case R.id.tv_xiandaijianyue:
                filterType="现代简约";
                break;
            case R.id.tv_oushi:
                filterType="欧式";
                break;
            case R.id.tv_zhongshi:
                filterType="亚克力吸顶灯";
                break;
            case R.id.tv_xinzhongshi:
                filterType="新中式";
                break;
            case R.id.tv_quanbu:
                filterType="风扇灯";
                break;
            case R.id.tv_meishi:
                filterType="铁艺吸顶灯";
                break;
            case R.id.tv_yijia:
                filterType="美式";
                break;
            case R.id.tv_houxiandai:
                filterType="中式";
                break;
            case R.id.tv_tianyuan:
                filterType="餐吊";
                break;
            case R.id.tv_qs:
                filterType="轻奢";
                break;
            case R.id.tv_bofg:
                filterType="北欧风格";
                break;
            case R.id.tv_lcd:
                filterType="铝材灯";
                break;
            case R.id.tv_cw:
                filterType="厨卫";
                break;
            case R.id.tv_gdd:
                filterType="过道灯";
                break;
            case R.id.tv_sz:
                filterType="商照";
                break;
        }
        intent.putExtra(Constance.filter_attr_name,filterType);
        startActivity(intent);

    }
}
