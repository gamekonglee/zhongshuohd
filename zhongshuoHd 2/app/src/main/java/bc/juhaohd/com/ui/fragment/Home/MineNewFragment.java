package bc.juhaohd.com.ui.fragment.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.InputStream;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseFragment;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.user.MineController;
import bc.juhaohd.com.ui.activity.HomeShowNewActivity;
import bc.juhaohd.com.ui.activity.user.LoginActivity;
import bc.juhaohd.com.ui.view.ShowDialog;
import bc.juhaohd.com.utils.MyShare;
import bc.juhaohd.com.utils.UIUtils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Jun
 * @time 2017/1/5  12:00
 * @desc 我的页面
 */
public class MineNewFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView head_cv;
    public View collect_rl, out_login_rl, order_rl,
            payment_rl, delivery_rl, Receiving_rl,address_rl,stream_rl;
    private MineController mController;
    private TextView tv_setting;
    private TextView tv_change;
    private ImageView iv_order;
    private ImageView iv_address;
    private ImageView iv_logistcs;
    private ImageView iv_collect;
    private ImageView iv_setting;
    private Bitmap bitmap_address;
    private Bitmap bitmap_order;
    private Bitmap bitmap_logist;
    private Bitmap bitmap_collect;
    private Bitmap bitmap_setting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fm_mine_new, null);
    }

    @Override
    protected void initController() {
        mController = new MineController(this);
//        order_rl.performClick();
    }

    @Override
    public void onStart() {
        super.onStart();
        mController.sendUser();
    }


    @Override
    protected void initViewData() {

    }

    @Override
    protected void initView() {
        head_cv = (CircleImageView) getActivity().findViewById(R.id.head_cv);
        collect_rl = (View) getActivity().findViewById(R.id.collect_rl);
//        version_rl = (View) getActivity().findViewById(R.id.version_rl);
//        clear_cache_rl = (View) getActivity().findViewById(R.id.clear_cache_rl);
        address_rl = (View) getActivity().findViewById(R.id.address_rl);
//        cotact_cutomer_rl = (View) getActivity().findViewById(R.id.cotact_cutomer_rl);
//        out_login_rl = (View) getActivity().findViewById(R.id.out_login_rl);
        order_rl = (View) getActivity().findViewById(R.id.order_rl);
        payment_rl = (View) getActivity().findViewById(R.id.payment_rl);
        delivery_rl = (View) getActivity().findViewById(R.id.delivery_rl);
        Receiving_rl = (View) getActivity().findViewById(R.id.Receiving_rl);
        stream_rl = (View) getActivity().findViewById(R.id.stream_rl);
        tv_setting = (TextView) getActivity().findViewById(R.id.tv_setting);
        tv_change = (TextView) getActivity().findViewById(R.id.tv_change);
        iv_order = getActivity().findViewById(R.id.iv_order);
        iv_address = getActivity().findViewById(R.id.iv_address);
        iv_logistcs = getActivity().findViewById(R.id.iv_logistcs);
        iv_collect = getActivity().findViewById(R.id.iv_collect);
        iv_setting = getActivity().findViewById(R.id.iv_setting);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPurgeable=true;
//        options.inInputShareable=true;
//        options.inJustDecodeBounds=false;
        //设置该属性可获得图片的长宽等信息，但是避免了不必要的提前加载动画
//        InputStream is=null;
//            is= UIUtils.Drawable2InputStream(getResources().getDrawable(R.mipmap.icon_setting_mine));
//            //decodestream
//            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

        bitmap_order = UIUtils.readBitMap(getActivity(), R.mipmap.icon_my_order);
        iv_order.setImageBitmap(bitmap_order);
        bitmap_address = UIUtils.readBitMap(getActivity(), R.mipmap.icon_receiving_address);
        bitmap_logist = UIUtils.readBitMap(getActivity(), R.mipmap.icon_my_logistics);
        bitmap_collect = UIUtils.readBitMap(getActivity(), R.mipmap.icon_my_collection);
        bitmap_setting = UIUtils.readBitMap(getActivity(), R.mipmap.icon_setting_mine);

        iv_address.setImageBitmap(bitmap_address);
        iv_logistcs.setImageBitmap(bitmap_logist);
        iv_collect.setImageBitmap(bitmap_collect);
        iv_setting.setImageBitmap(bitmap_setting);

        tv_change.setOnClickListener(this);
        head_cv.setOnClickListener(this);
        collect_rl.setOnClickListener(this);
//        version_rl.setOnClickListener(this);
//        clear_cache_rl.setOnClickListener(this);
//        cotact_cutomer_rl.setOnClickListener(this);
//        out_login_rl.setOnClickListener(this);
        order_rl.setOnClickListener(this);
        payment_rl.setOnClickListener(this);
        delivery_rl.setOnClickListener(this);
        Receiving_rl.setOnClickListener(this);
        address_rl.setOnClickListener(this);
        stream_rl.setOnClickListener(this);
        tv_setting.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change:
            case R.id.head_cv://头像
                mController.setHead();
                break;
            case R.id.collect_rl://我的收藏
                mController.setCollect();
                break;
            case R.id.version_rl://当前版本
                //                mController.setOrder();
                break;
            case R.id.clear_cache_rl://清楚缓存
                mController.clearCache();
                break;
            case R.id.cotact_cutomer_rl://联系客服
                mController.sendCall();
                break;
            case R.id.out_login_rl://退出登录
                ShowDialog mDialog = new ShowDialog();
                mDialog.show(this.getActivity(), "提示", "确定退出登录?", new ShowDialog.OnBottomClickListener() {
                    @Override
                    public void positive() {
                        MyShare.get(MineNewFragment.this.getActivity()).putString(Constance.TOKEN, "");
                        Intent logoutIntent = new Intent(MineNewFragment.this.getActivity(), LoginActivity.class);
                        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(logoutIntent);
                    }
                    @Override
                    public void negtive() {

                    }
                });
                break;
            case R.id.order_rl:
                mController.setOrder();
                break;
            case R.id.payment_rl:
                mController.setPayMen();
                break;
            case R.id.delivery_rl:
                mController.setDelivery();
                break;
            case R.id.Receiving_rl://待收货
                mController.setReceiving();
                break;
            case R.id.address_rl://管理收货地址
                mController.setAddress();
                break;
            case R.id.stream_rl://管理物流地址
                mController.setStream();
                break;
            case R.id.tv_setting:
                mController.setSetting();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!bitmap_address.isRecycled())bitmap_address.recycle();
        if(!bitmap_collect.isRecycled())bitmap_collect.recycle();
        if(!bitmap_logist.isRecycled())bitmap_logist.recycle();
        if(!bitmap_order.isRecycled())bitmap_order.recycle();
        if(!bitmap_setting.isRecycled())bitmap_setting.recycle();
        iv_address.setImageDrawable(null);
        iv_logistcs.setImageDrawable(null);
        iv_collect.setImageDrawable(null);
        iv_setting.setImageDrawable(null);
    }

    public void clear() {
        mController.clear();
    }
}
