package bc.juhaohd.com.controller.buy;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lib.common.hxp.view.ListViewForScrollView;
import com.lib.common.hxp.view.PullableScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.yjn.swipelistview.swipelistviewlibrary.widget.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.adapter.BaseAdapterHelper;
import bc.juhaohd.com.adapter.QuickAdapter;
import bc.juhaohd.com.bean.AddressBean;
import bc.juhaohd.com.bean.CartGoods;
import bc.juhaohd.com.bean.Logistics;
import bc.juhaohd.com.bean.PayResult;
import bc.juhaohd.com.bean.ScGoods;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.controller.BaseController;
import bc.juhaohd.com.listener.ILogisticsChooseListener;
import bc.juhaohd.com.listener.INetworkCallBack;
import bc.juhaohd.com.listener.INetworkCallBack02;
import bc.juhaohd.com.listener.ITwoCodeListener;
import bc.juhaohd.com.ui.activity.OrderPayHomeActivity;
import bc.juhaohd.com.ui.activity.buy.ConfirmOrderActivity;
import bc.juhaohd.com.ui.activity.buy.ConfirmOrderNewActivity;
import bc.juhaohd.com.ui.activity.user.OrderDetailActivity;
import bc.juhaohd.com.ui.activity.user.UserAddrAddNewActivity;
import bc.juhaohd.com.ui.activity.user.UserAddrNewActivity;
import bc.juhaohd.com.ui.view.popwindow.SelectLogisticsPopWindow;
import bc.juhaohd.com.ui.view.popwindow.WebSharePopWindow;
import bc.juhaohd.com.utils.UIUtils;
import bocang.json.JSONArray;
import bocang.json.JSONObject;
import bocang.utils.AppDialog;
import bocang.utils.AppUtils;
import bocang.utils.IntentUtil;
import bocang.utils.LogUtils;
import bocang.utils.MyToast;

/**
 * @author: Jun
 * @date : 2017/2/24 16:53
 * @description :提交订单
 */
public class ConfirmOrderController extends BaseController implements INetworkCallBack {
    private ConfirmOrderNewActivity mView;
//    private TextView consignee_tv, phone_tv, address_tv, money_tv;
//    private TextView  money_tv;
    private Intent mIntent;
    private SwipeMenuListView listView;
    private MyAdapter mMyAdapter;
    private String mConsigneeId = "";
    private LinearLayout logistic_type_rl;
    private LinearLayout logistic_ll, main_ll;
    private TextView logistic_title_tv, loginstic_tv, loginstic_phone_tv, loginstic_address_tv;
    private Logistics mlogistics;
    private SelectLogisticsPopWindow mPopWindow;
    private PullableScrollView scrollView;
    private com.alibaba.fastjson.JSONObject mOrderObject;
    private int currentSelectedAddress;
    private List<AddressBean> addressBeen;
    private QuickAdapter addressAdapter;
    private ListViewForScrollView lv_address;
    private TextView tv_tips;
    private ImageView iv_add_address;

    public ConfirmOrderController(ConfirmOrderActivity v) {
//        mView = v;
        initView();
        initViewData();
    }

    public ConfirmOrderController(ConfirmOrderNewActivity confirmOrderNewActivity) {
        mView = confirmOrderNewActivity;
        initView();
        initViewData();
    }

    private void initViewData() {
        mMyAdapter = new MyAdapter();
        if(listView==null)return;
        listView.setAdapter(mMyAdapter);
//        if (!AppUtils.isEmpty(mView.mAddressObject)) {
//            String name = mView.mAddressObject.getString(Constance.name);
//            String address = mView.mAddressObject.getString(Constance.address);
//            String phone = mView.mAddressObject.getString(Constance.mobile);
//            mConsigneeId = mView.mAddressObject.getString(Constance.id);
////            consignee_tv.setText(name);
////            address_tv.setText("收货地址:" + address);
////            phone_tv.setText(phone);
//        } else {
//        }
        sendAddressList();
    }

    private void initView() {
//        consignee_tv = (TextView) mView.findViewById(R.id.consignee_tv);
//        phone_tv = (TextView) mView.findViewById(R.id.phone_tv);
//        address_tv = (TextView) mView.findViewById(R.id.address_tv);
        if(mView==null)return;
        listView = (SwipeMenuListView) mView.findViewById(R.id.listView);
        logistic_type_rl = (LinearLayout) mView.findViewById(R.id.logistic_type_rl);
        logistic_ll = (LinearLayout) mView.findViewById(R.id.logistic_ll);
        main_ll = (LinearLayout) mView.findViewById(R.id.main_ll);
//        remark_tv = (TextView) mView.findViewById(R.id.remark_tv);
        logistic_title_tv = (TextView) mView.findViewById(R.id.logistic_title_tv);
        loginstic_tv = (TextView) mView.findViewById(R.id.loginstic_tv);
        loginstic_phone_tv = (TextView) mView.findViewById(R.id.loginstic_phone_tv);
        loginstic_address_tv = (TextView) mView.findViewById(R.id.loginstic_address_tv);
        scrollView = (PullableScrollView) mView.findViewById(R.id.scrollView);
        lv_address = (ListViewForScrollView) mView.findViewById(R.id.order_sv);
        tv_tips = (TextView) mView.findViewById(R.id.tv_tips);
        iv_add_address = (ImageView) mView.findViewById(R.id.iv_add_address);
        iv_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(mView,UserAddrAddNewActivity.class,false);
            }
        });
        scrollView.smoothScrollTo(0, 20);
        listView.setFocusable(false);
        currentSelectedAddress = 0;
        addressBeen = new ArrayList<>();
        addressAdapter = new QuickAdapter<AddressBean>(mView, R.layout.item_user_address_new) {
            @Override
            protected void convert(BaseAdapterHelper helper, AddressBean item) {
                helper.setText(R.id.consignee_tv,item.getName());
                helper.setText(R.id.phone_tv,item.getMobile());
                String region="";

                for (int i=0;i<item.getRegions().size();i++){
                    if(i>0){
                        switch (i){
                            case 1:
                                region+=item.getRegions().get(i).getName()+"省";
                                break;
                            case 2:
                                region+=item.getRegions().get(i).getName()+"市";
                                break;

                            case 3:
                                region+=item.getRegions().get(i).getName()+"区";
                                break;
                        }
                    }
                }
                helper.setText(R.id.address_tv,region+item.getAddress());
                ImageView imageView=helper.getView(R.id.iv_select);
                if(helper.getPosition()== currentSelectedAddress){
                    mConsigneeId=item.getId()+"";

                    imageView.setImageDrawable(mView.getResources().getDrawable(R.mipmap.cb_selected_cart));
                }else {
                    imageView.setImageDrawable(mView.getResources().getDrawable(R.mipmap.cb_normal_cart));
                }
            }
        };
        if(lv_address!=null){
            lv_address.setAdapter(addressAdapter);
            lv_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentSelectedAddress=position;
                    addressAdapter.notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }

    public void sendAddressList() {
        mNetWork.sendAddressList(this);
    }

    @Override
    public void onSuccessListener(String requestCode, JSONObject ans) {
        mView.hideLoading();
        switch (requestCode) {
            case NetWorkConst.CONSIGNEELIST:
                JSONArray consigneeList = ans.getJSONArray(Constance.consignees);
                if (consigneeList.length() == 0)
                    return;
                addressBeen=new ArrayList<>();
                for(int i=0;i<consigneeList.length();i++){
                    addressBeen.add(new Gson().fromJson(String.valueOf(consigneeList.getJSONObject(i)),AddressBean.class));
                }
                if(addressBeen==null||addressBeen.size()<=0){
                    if(tv_tips!=null)tv_tips.setVisibility(View.VISIBLE);
                    if(lv_address!=null)lv_address.setVisibility(View.GONE);
                }else {
                    if(tv_tips!=null)tv_tips.setVisibility(View.GONE);
                    if(lv_address!=null)lv_address.setVisibility(View.VISIBLE);
                }
                addressAdapter.replaceAll(addressBeen);
//                mConsigneeId=addressBeen.get(0).getId()+"";
//                sendCheckOutCart();
                String name = consigneeList.getJSONObject(0).getString(Constance.name);
                String address = consigneeList.getJSONObject(0).getString(Constance.address);
                String phone = consigneeList.getJSONObject(0).getString(Constance.mobile);
//                consignee_tv.setText(name);
//                address_tv.setText("收货地址:" + address);
//                phone_tv.setText(phone);
//                mConsigneeId = consigneeList.getJSONObject(0).getString(Constance.id);
                break;
            case NetWorkConst.CheckOutCart:
//                LogUtils.logE("ans:",ans.toString());
                JSONObject orderObject = ans.getJSONObject(Constance.order);
                if(AppUtils.isEmpty(orderObject)){
                    MyToast.show(mView,"当前没有可支付的数据!");
                    return;
                }
                String order= orderObject.getString(Constance.id);
//                sendPayment(order, "alipay.app");
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须上传到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息


                    String resultStatus = payResult.getResultStatus();
                    Log.d("TAG", "resultStatus=" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        MyToast.show(mView, "支付成功");
                        mView.finish();

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            AppDialog.messageBox("支付结果确认中");

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            AppDialog.messageBox("支付失败");
                            mView.hideLoading();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    public void onFailureListener(String requestCode, JSONObject ans) {
        mView.hideLoading();
        if (null == mView || mView.isFinishing())
            return;
        if (AppUtils.isEmpty(ans)) {
            AppDialog.messageBox(UIUtils.getString(R.string.server_error));
            return;
        }
        AppDialog.messageBox(ans.getString(Constance.error_desc));

    }


    /**
     * 支付订单
     *
     * @param order
     * @param code
     */
    private void sendPayment(final String order, String code) {
        mNetWork.sendPayment(order, code, new INetworkCallBack02() {
            @Override
            public void onSuccessListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
//                LogUtils.logE("sendPay",ans.toString());
                mOrderObject=ans.getJSONObject(Constance.order);
                WebSharePopWindow window = new WebSharePopWindow(mView, mView,0);
                window.getPayOrder(order);
                window.onShow(main_ll);
                window.setListener(new ITwoCodeListener() {
                    @Override
                    public void onTwoCodeChanged(String path) {
                        if("支付成功".equals(path)){
                            MyToast.show(mView,"支付成功");
                            Intent intent = new Intent(mView, OrderDetailActivity.class);
                            intent.putExtra(Constance.order, mOrderObject.toJSONString());
                            intent.putExtra(Constance.state, 1);
                            mView.startActivity(intent);
                            mView.finish();
                        }else{
                            AppDialog.messageBox("支付失败");
                            mView.hideLoading();
                            Intent intent = new Intent(mView, OrderDetailActivity.class);
                            intent.putExtra(Constance.order, mOrderObject.toJSONString());
                            mView.startActivity(intent);
                            mView.finish();
                        }
                    }
                });
            }

            @Override
            public void onFailureListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                mView.hideLoading();
                MyToast.show(mView, "支付失败!");
            }
        });

    }

    /**
     * 支付宝支付
     */
    private void SubmitAliPay(String notifyUrl) {
        //开始支付
        aliPay(notifyUrl);
    }

    //标记是支付
    private static final int SDK_PAY_FLAG = 1;
    private static final String TAG = "PayActivity";

    /**
     * 开始-支付宝支付
     */
    private void aliPay(final String ss) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mView);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(ss, true);
                //异步处理支付结果
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 选择地址
     */
    public void selectAddress() {
        mIntent = new Intent(mView, UserAddrNewActivity.class);
        mIntent.putExtra(Constance.isSELECTADDRESS, true);
        mView.startActivityForResult(mIntent, Constance.FROMADDRESS);
    }

    public void ActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constance.FROMADDRESS && !AppUtils.isEmpty(data)) {
            JSONObject consignee = (JSONObject) data.getSerializableExtra(Constance.address);
            if (AppUtils.isEmpty(consignee))
                return;
            String name = consignee.getString(Constance.name);
            String address = consignee.getString(Constance.address);
            String phone = consignee.getString(Constance.mobile);
            mConsigneeId = consignee.getString(Constance.id);
//            consignee_tv.setText(name);
//            address_tv.setText("收货地址:" + address);
//            phone_tv.setText(phone);
        } else if (requestCode == Constance.FROMLOG && !AppUtils.isEmpty(data)) {
            mlogistics = (Logistics) data.getSerializableExtra(Constance.logistics);
            logistic_title_tv.setVisibility(View.GONE);
            loginstic_tv.setVisibility(View.VISIBLE);
            loginstic_phone_tv.setVisibility(View.VISIBLE);
            loginstic_address_tv.setVisibility(View.VISIBLE);
            loginstic_tv.setText(mlogistics.getName());
            loginstic_phone_tv.setText(mlogistics.getTel());
            loginstic_address_tv.setText("提货地址: " + mlogistics.getAddress());
        } else if (resultCode == 007) {
            String value = data.getStringExtra(Constance.VALUE);
            if (AppUtils.isEmpty(value))
                return;
//            remark_tv.setText(value);
        }else if(requestCode==200&&resultCode==200){
            mView.setResult(200,data);
            mView.finish();
        }

    }

    /**
     * 结算订单
     */
    public void settleOrder() {
        if (AppUtils.isEmpty(mConsigneeId)) {
            MyToast.show(mView, "请选择收货地址!");
            return;
        }
        sendCheckOutCart();
    }

    /**
     * 结算购物车
     */
    private void sendCheckOutCart() {
        String consignee = mConsigneeId;
        String shipping = "";
        String tel = "";
        String address = "";

        if (mType == 0 && AppUtils.isEmpty(mlogistics)) {
            MyToast.show(mView, "请选择物流地址!");
            return;
        }
        if (mType == 1 && AppUtils.isEmpty(mConsigneeId)) {
            MyToast.show(mView, "请选择收货地址!");
            return;
        }

        if (!AppUtils.isEmpty(mlogistics)) {
            shipping = mlogistics.getName();
            tel = mlogistics.getTel();
            address = mlogistics.getAddress();
        }
        String idArray = "[";
        for (int i = 0; i < mView.goodsList.size(); i++) {
            String id = mView.goodsList.get(i).getId()+"";
            if (i == mView.goodsList.size() - 1) {
                idArray += id + "]";
            } else {
                idArray += id + ",";
            }
        }
        mView.setShowDialog(true);
        mView.setShowDialog("正在结算中...");
        mView.showLoading();
        String name="";
        for(AddressBean temp:addressBeen){
            if(consignee.equals(temp.getId()+"")){
                name=temp.getName();
//                tel=temp.getMobile();
//                address=temp.getAddress();
            }
        }
        Intent intent=new Intent(mView, OrderPayHomeActivity.class);
        intent.putExtra(Constance.consignee,consignee);
        intent.putExtra(Constance.shipping,shipping);
        intent.putExtra(Constance.tel,tel);
        intent.putExtra(Constance.address,address);
        intent.putExtra(Constance.id,idArray);
        intent.putExtra(Constance.money,mView.money);
        intent.putExtra(Constance.name,name);
        mView.startActivityForResult(intent,200);
//        LogUtils.logE("ConfirmController",mConsigneeId+","+shipping+","+tel+","+address+","+idArray+"");
//        mNetWork.sendCheckOutCart(consignee, shipping, tel, address, idArray, "", this);
    }

    private int mType = 1;

    /**
     * 选择运输方式
     */
    public void selectCheckType(int type) {
        if (type == R.id.radioLogistic) {
            mType = 0;
            logistic_type_rl.setVisibility(View.VISIBLE);
            if (AppUtils.isEmpty(mlogistics)) {
                logistic_title_tv.setVisibility(View.VISIBLE);
                loginstic_tv.setVisibility(View.GONE);
                loginstic_phone_tv.setVisibility(View.GONE);
                loginstic_address_tv.setVisibility(View.GONE);
            } else {
                logistic_title_tv.setVisibility(View.GONE);
                loginstic_tv.setVisibility(View.VISIBLE);
                loginstic_phone_tv.setVisibility(View.VISIBLE);
                loginstic_address_tv.setVisibility(View.VISIBLE);
            }

        } else {
            mType = 1;
            logistic_type_rl.setVisibility(View.GONE);
        }
    }

    /**
     * 选择物流公司
     */
    public void selectLogistic() {
        mPopWindow = new SelectLogisticsPopWindow(mView, mView);
        mPopWindow.onShow(main_ll);
        mPopWindow.setListener(new ILogisticsChooseListener() {
            @Override
            public void onLogisticsChanged(JSONObject object) {
                if (AppUtils.isEmpty(object))
                    return;
                logistic_title_tv.setVisibility(View.GONE);
                loginstic_tv.setVisibility(View.VISIBLE);
                loginstic_phone_tv.setVisibility(View.VISIBLE);
                loginstic_address_tv.setVisibility(View.VISIBLE);
                mlogistics = new Logistics();
                mlogistics.setAddress(object.getString(Constance.address));
                mlogistics.setTel(object.getString(Constance.tel));
                mlogistics.setName(object.getString(Constance.name));
                loginstic_tv.setText(mlogistics.getName());
                loginstic_phone_tv.setText(mlogistics.getTel());
                loginstic_address_tv.setText("提货地址: " + mlogistics.getAddress());
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        private DisplayImageOptions options;
        private ImageLoader imageLoader;

        public MyAdapter() {
            options = new DisplayImageOptions.Builder()
                    // 设置图片下载期间显示的图片
                    .showImageOnLoading(R.drawable.bg_default)
                    // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageForEmptyUri(R.drawable.bg_default)
                    // 设置图片加载或解码过程中发生错误显示的图片
                    // .showImageOnFail(R.drawable.ic_error)
                    // 设置下载的图片是否缓存在内存中
                    .cacheInMemory(true)
                    // 设置下载的图片是否缓存在SD卡中
                    .cacheOnDisk(true)
                    // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                    // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .considerExifParams(true)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片可以放大（要填满ImageView必须配置memoryCacheExtraOptions大于Imageview）
                    // .displayer(new FadeInBitmapDisplayer(100))//
                    // 图片加载好后渐入的动画时间
                    .build(); // 构建完成

            // 得到ImageLoader的实例(使用的单例模式)
            imageLoader = ImageLoader.getInstance();
        }


        @Override
        public int getCount() {
            if (null == mView.goodsList)
                return 0;
            return mView.goodsList.size();
        }


        @Override
        public ScGoods getItem(int position) {
            if (null == mView.goodsList)
                return null;
            return mView.goodsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mView, R.layout.item_lv_order, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                holder.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
                holder.contact_service_tv = (TextView) convertView.findViewById(R.id.contact_service_tv);
                holder.SpecificationsTv = (TextView) convertView.findViewById(R.id.SpecificationsTv);
                holder.priceTv = (TextView) convertView.findViewById(R.id.priceTv);
                holder.numTv = (TextView) convertView.findViewById(R.id.numTv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
           ScGoods goodsObject = mView.goodsList.get(position);
            holder.nameTv.setText(goodsObject.getProduct().getName());

            String img=goodsObject.getImg();
            if(!img.contains("http")){
                img=NetWorkConst.SCENE_HOST+img;
            }

            imageLoader.displayImage(img
                    , holder.imageView, options);

            String property = goodsObject.getProperty();
            holder.SpecificationsTv.setText(property);
            String price = goodsObject.getPrice();
            String num = goodsObject.getAmount()+"";
            holder.priceTv.setText("￥" + price);
            holder.numTv.setText("x" + num);
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView nameTv;
            TextView priceTv;
            TextView SpecificationsTv;
            TextView numTv;
            TextView contact_service_tv;
        }
    }

}
