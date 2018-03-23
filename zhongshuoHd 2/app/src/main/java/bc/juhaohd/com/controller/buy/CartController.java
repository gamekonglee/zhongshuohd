package bc.juhaohd.com.controller.buy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.lib.common.hxp.view.ListViewForScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.yjn.swipelistview.swipelistviewlibrary.widget.SwipeMenu;
import com.yjn.swipelistview.swipelistviewlibrary.widget.SwipeMenuCreator;
import com.yjn.swipelistview.swipelistviewlibrary.widget.SwipeMenuItem;
import com.yjn.swipelistview.swipelistviewlibrary.widget.SwipeMenuListView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.adapter.BaseAdapterHelper;
import bc.juhaohd.com.adapter.QuickAdapter;
import bc.juhaohd.com.bean.AddressBean;
import bc.juhaohd.com.bean.CartGoods;
import bc.juhaohd.com.bean.GoodsBean;
import bc.juhaohd.com.bean.ScAppImg;
import bc.juhaohd.com.bean.ScGoods;
import bc.juhaohd.com.bean.ScProduct;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.controller.BaseController;
import bc.juhaohd.com.listener.INetworkCallBack;
import bc.juhaohd.com.ui.activity.CartActivity;
import bc.juhaohd.com.ui.activity.IssueApplication;
import bc.juhaohd.com.ui.activity.buy.ConfirmOrderNewActivity;
import bc.juhaohd.com.ui.activity.buy.ExInventoryActivity;
import bc.juhaohd.com.ui.fragment.CartFragment;
import bc.juhaohd.com.utils.UIUtils;
import bocang.json.JSONArray;
import bocang.json.JSONObject;
import bocang.utils.AppUtils;
import bocang.utils.LogUtils;
import bocang.utils.MyToast;

import static bc.juhaohd.com.utils.UIUtils.getResources;

/**
 * @author: Jun
 * @date : 2017/2/21 14:35
 * @description :
 */
public class CartController extends BaseController implements INetworkCallBack {
    private CartActivity mView;
    private ListViewForScrollView mListView;
//    private JSONArray goodses;
//    private MyAdapter myAdapter;
    private CheckBox checkAll;
    private TextView money_tv,settle_tv,edit_tv;
    private boolean isStart=false;
    private LinearLayout sum_ll;
    private Boolean isEdit=false;
    private  List<ScGoods> goods;
//    private ProgressBar pd;
    private  JSONObject mAddressObject;
    private ProgressDialog pd;
    private TextView money_suply_tv;
    private TextView tv_count;
    private int count;
    private LinearLayout step_one;
//    private LinearLayout step_two;
//    private TextView tv_tips;
//    private ListViewForScrollView lv_address;
    private int currentStep;
    private TextView tv_jiesuan;
//    private TextView tv_zhifu;
    private int currentSelectedAddress;
    private QuickAdapter addressAdapter;
    private List<AddressBean> addressBeen;
//    private RadioGroup rg_express;
//    private ListView lv_order_product;
    private LinearLayout step_one_cart;
//    private LinearLayout step_two_order;
//    private LinearLayout step_three;
    private LinearLayout step_one_two;
    private CartAdapter goodsAdapter;
    private List<ScGoods> goodsTotalList;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    private AlertView mAlertViewExt;//窗口拓展例子
    private EditText etNum;//拓展View内容
    private InputMethodManager imm;
    private ViewGroup mExtView;

    public CartController(CartFragment v) {
//        mView = v;
        initView();
        initViewData();
    }

    public CartController(CartActivity cartActivity) {
        mView=cartActivity;
        initView();
        initViewData();
    }

    private void initViewData() {
        if(pd==null){
            pd = ProgressDialog.show(mView,"","加载中");
        }else {
            if(!pd.isShowing())pd.show();
        }
        sendAddressList();
    }


    private void initView() {
        mListView =  mView.findViewById(R.id.cart_lv);
        mListView.setDivider(null);//去除listview的下划线

//        myAdapter = new MyAdapter();
//
//        final SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(mView);
//
//                deleteItem.setBackground(new ColorDrawable(Color.parseColor("#fe3c3a")));
//                deleteItem.setWidth(dp2px(80));
//                deleteItem.setTitle("删除");
//                deleteItem.setTitleColor(Color.WHITE);
//                deleteItem.setTitleSize(20);
//                menu.addMenuItem(deleteItem);
//            }
//        };
//        mListView.setMenuCreator(creator);
//
//        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                if (index == 0) {
//                    if(pd==null){
//                        pd = ProgressDialog.show(mView,"","正在删除");
//                    }else {
//                        if(!pd.isShowing())pd.show();
//                    }
//                    String id = goodsTotalList.get(position).getId()+"";
////                    mDeleteIndex=position;
//                    isLastDelete = false;
//                    deleteShoppingCart(id);
//                }
//                return false;
//            }
//        });
        checkAll = (CheckBox) mView.findViewById(R.id.checkAll);
        money_tv = (TextView) mView.findViewById(R.id.totla_money_tv);
        money_suply_tv = (TextView) mView.findViewById(R.id.summoney_tv);
        settle_tv = (TextView) mView.findViewById(R.id.settle_tv);
        edit_tv = (TextView) mView.findViewById(R.id.edit_tv);
        sum_ll = (LinearLayout) mView.findViewById(R.id.sum_ll);
        tv_count = (TextView) mView.findViewById(R.id.tv_count);
        tv_jiesuan = (TextView) mView.findViewById(R.id.tv_jiesuan);
//        tv_zhifu = (TextView) mView.findViewById(R.id.tv_zhifu);

//        pd = (ProgressBar) mView.getActivity().findViewById(R.id.pd);
//        pd.setVisibility(View.VISIBLE);



        mExtView= (ViewGroup) LayoutInflater.from(mView).inflate(R.layout.alertext_form, null);
        etNum = (EditText) mExtView.findViewById(R.id.etName);
        etNum.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        imm = (InputMethodManager)mView.getSystemService(Context.INPUT_METHOD_SERVICE);
        step_one_cart = (LinearLayout) mView.findViewById(R.id.step_one_cart);
//        step_two_order = (LinearLayout) mView.findViewById(R.id.step_two_order);
//        step_three = (LinearLayout) mView.findViewById(R.id.step_three);
        step_one = (LinearLayout) mView.findViewById(R.id.step_one);
//        step_two = (LinearLayout) mView.findViewById(R.id.step_two);
        step_one_two = (LinearLayout) mView.findViewById(R.id.step_one_two);
//        tv_tips = (TextView) mView.findViewById(R.id.tv_tips);
//        lv_address = (ListViewForScrollView) mView.findViewById(R.id.order_sv);

//        lv_order_product = (ListView) mView.findViewById(R.id.listView);
        goodsTotalList = new ArrayList();
        goodsAdapter = new CartAdapter(mView, R.layout.item_lv_cart_order);
        mListView.setAdapter(goodsAdapter);
//        lv_order_product.setAdapter(goodsAdapter);
        currentSelectedAddress = 0;
        addressBeen = new ArrayList<>();
        addressAdapter = new QuickAdapter<AddressBean>(mView, R.layout.item_user_address_new) {
            @Override
            protected void convert(BaseAdapterHelper helper, AddressBean item) {
            helper.setText(R.id.consignee_tv,item.getName());
                helper.setText(R.id.phone_tv,item.getMobile());
                helper.setText(R.id.address_tv,item.getAddress());
                ImageView imageView=helper.getView(R.id.iv_select);
                if(helper.getPosition()==currentSelectedAddress){
                    imageView.setImageDrawable(mView.getResources().getDrawable(R.mipmap.cb_selected_cart));
                }else {
                    imageView.setImageDrawable(mView.getResources().getDrawable(R.mipmap.cb_normal_cart));
                }
            }
        };
//        lv_address.setAdapter(addressAdapter);
    }

    int mDeleteIndex =-1;

//    /**
//     * 导出清单
//     */
//    public void exportData() {
//        goodsAdapter.getCartGoodsCheck();
//        if(goods.size()==0){
//            MyToast.show(mView,"请选择产品");
//            return;
//        }
//        Intent intent=new Intent(mView,ExInventoryActivity.class);
//        intent.putExtra(Constance.goods, goods);
//        mView.startActivity(intent);
//    }


    private void deleteShoppingCart(String goodId) {
        mNetWork.sendDeleteCart(goodId, this);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }

    /**
     * 编辑
     */
    public void setEdit() {
        if(!isEdit){
            sum_ll.setVisibility(View.GONE);
            settle_tv.setText("删除");
            edit_tv.setText("完成");
            isEdit=true;

        }else{
            sum_ll.setVisibility(View.VISIBLE);
            settle_tv.setText("结算");
            edit_tv.setText("编辑");
            isEdit=false;
        }
    }

    public void sendShoppingCart() {
        mNetWork.sendShoppingCart(this);
    }

    public void sendUpdateCart(String good, String amount) {
//        MyToast.show(mView,"test:"+good+","+amount);
        mNetWork.sendUpdateCart(good, amount, this);
    }

    @Override
    public void onSuccessListener(String requestCode, JSONObject ans) {
//        pd.setVisibility(View.INVISIBLE);
        mView.showContentView();
        switch (requestCode) {
            case NetWorkConst.DeleteCART:
                if(isLastDelete==true){
                    isLastDelete=false;
//                    isCheckList.remove(mDeleteIndex);
                    sendShoppingCart();
                    return;
                }
                if(pd!=null&&pd.isShowing())pd.dismiss();
                break;
            case NetWorkConst.UpdateCART:
                sendShoppingCart();
                break;
            case NetWorkConst.GETCART:
//                LogUtils.logE("getcart:",ans.toString());
                if(pd!=null&&pd.isShowing())pd.dismiss();
                if (ans.getJSONArray(Constance.goods_groups).length() > 0) {
//                    CartGoods cartGoods=new Gson().fromJson(ans.getJSONArray(Constance.goods_groups).getJSONObject(0).toString(),CartGoods.class);
//                    goodses = ans.getJSONArray(Constance.goods_groups).getJSONObject(0).getJSONArray(Constance.goods);
                    JSONArray goodarray=ans.getJSONArray(Constance.goods_groups).getJSONObject(0).getJSONArray(Constance.goods);
                    goodsTotalList=new ArrayList<>();
                    for(int i=0;i<goodarray.length();i++){
                        try{
                            goodsTotalList.add(new Gson().fromJson(goodarray.getJSONObject(i).toString(),ScGoods.class));
                        }catch (Exception e){
                            ScGoods scGoods=new ScGoods();
                            scGoods.setId(Integer.parseInt(goodarray.getJSONObject(i).getString(Constance.id)));
                            scGoods.setAmount(Integer.parseInt(goodarray.getJSONObject(i).getString(Constance.amount)));
                            scGoods.setAttr_stock(goodarray.getJSONObject(i).getInt(Constance.attr_stock));
                            scGoods.setPrice(goodarray.getJSONObject(i).getString(Constance.price));
                            scGoods.setProperty(goodarray.getJSONObject(i).getString(Constance.property));
                            try {
                            scGoods.setProduct(new Gson().fromJson(goodarray.getJSONObject(i).getString(Constance.product), ScProduct.class));
                            }catch (Exception e2){
                                JSONObject jsonObject=goodarray.getJSONObject(i).getJSONObject(Constance.product);
                                ScProduct scProduct=new ScProduct();
                                scProduct.setName(jsonObject.getString(Constance.name));
                                scProduct.setOriginal_img(jsonObject.getString(Constance.original_img));
                                scProduct.setApp_img(new Gson().fromJson(jsonObject.getString(Constance.app_img), ScAppImg.class));
                                scProduct.setWarn_number(jsonObject.getInt(Constance.warn_number));
                                scGoods.setProduct(scProduct);
                            }
                            goodsTotalList.add(scGoods);
                        }
                    }
                    goodsAdapter.replaceAll(goodsTotalList);
                    goodsAdapter.notifyDataSetChanged();
                    goodsAdapter.addIsCheckAll(false);
                    goodsAdapter.getTotalMoney();
                    IssueApplication.mCartCount=goodsTotalList.size();
                } else {
                    goodsTotalList = new ArrayList<>();
                    goodsAdapter.replaceAll(goodsTotalList);
                    goodsAdapter.notifyDataSetChanged();
                    goodsAdapter.getTotalMoney();
                    IssueApplication.mCartCount=0;
                }
//                checkAll.setChecked(true);
//                sendSettle();

                isStart=true;
                break;
            case NetWorkConst.CONSIGNEELIST:
                if(pd!=null&&pd.isShowing())pd.dismiss();
                JSONArray consigneeList = ans.getJSONArray(Constance.consignees);
                if (consigneeList.length() == 0)
                    return;
                addressBeen=new ArrayList<>();
                for(int i=0;i<consigneeList.length();i++){
                    addressBeen.add(new Gson().fromJson(String.valueOf(consigneeList.getJSONObject(i)),AddressBean.class));
                }
                mAddressObject=consigneeList.getJSONObject(0);

                break;
        }

    }



    @Override
    public void onFailureListener(String requestCode, JSONObject ans) {
        isLastDelete=false;
        if(pd!=null&&pd.isShowing())pd.dismiss();
        mView.showContentView();
//        pd.setVisibility(View.INVISIBLE);
    }


//    private List<Integer> buyNum = new ArrayList<>();

    private ArrayList<Boolean> isCheckList = new ArrayList<>();

    public void setCkeckAll(Boolean isCheck) {
        if (goodsAdapter == null || goodsAdapter.isEmpty()) {
            return;
        }
        goodsAdapter.setIsCheckAll(isCheck);
        goodsAdapter.getTotalMoney();
        goodsAdapter.notifyDataSetChanged();

    }



    /**
     * 结算/删除
     */
    public void sendSettle() {
        goodsAdapter.getCartGoodsCheck();
        if(goods.size()==0){
            MyToast.show(mView,"请选择产品");
            return;
        }
//        currentStep = 2;
//        refreshUI();

        if(!isEdit){
            Intent intent=new Intent(mView,ConfirmOrderNewActivity.class);
            Bundle bundle=new Bundle();
//            bundle.putSerializable(Constance.goods, (Serializable) goods);
//            bundle.putParcelableArrayList(Constance.goods, (ArrayList<? extends Parcelable>) goods);
//            LogUtils.logE("tojson:",new Gson().toJson(goods));
            bundle.putString(Constance.goods,new Gson().toJson(goods));
//            intent.putParcelableArrayListExtra(Constance.goods, (ArrayList<? extends Parcelable>) goods);
            bundle.putDouble(Constance.money,mMoney);
//            intent.putExtra(Constance.money,mMoney);
//            intent.putExtra(Constance.address,mAddressObject);
            bundle.putSerializable(Constance.address,mAddressObject);
            intent.putExtras(bundle);
            mView.startActivityForResult(intent,200);

        }else{
            sendDeleteCart();
        }
    }


    private Boolean isLastDelete=false;

    /**
     * 删除购物车数据
     */
    public  void sendDeleteCart(){
        if(pd==null){
            pd=ProgressDialog.show(mView,"","正在删除");
        }else {
            if(!pd.isShowing())pd.show();
        }
        ArrayList<String> deleteList=new ArrayList<>();
        for(int i=0;i<isCheckList.size();i++){
            if(isCheckList.get(i)){
                String id = goodsTotalList.get(i).getId()+"" ;
                deleteList.add(id);
            }
        }
        for(int j=0;j<deleteList.size();j++){
            if(j==deleteList.size()-1){
                isLastDelete=true;
            }
//            mDeleteIndex=j;
            deleteShoppingCart(deleteList.get(j));
        }
    }

    double mMoney=0;
    DecimalFormat df   = new DecimalFormat("######0.00");
    /**
     * 获取收货地址
     */
    public void sendAddressList() {
        mNetWork.sendAddressList(this);
        mNetWork.sendShoppingCart(this);
    }
    public class CartAdapter extends QuickAdapter<ScGoods>{




        public CartAdapter(Context context, int layoutResId) {
            super(context, layoutResId);
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
        protected void convert(final BaseAdapterHelper helper, final ScGoods item) {
            helper.setText(R.id.nameTv,""+item.getProduct().getName());
            helper.setText(R.id.SpecificationsTv,item.getProperty());
            helper.setText(R.id.tv_num,"x"+item.getAmount());
            helper.setText(R.id.et_num,""+item.getAmount()+"");
            helper.setText(R.id.priceTv,"￥"+ df.format(Double.parseDouble(item.getPrice())));
            ImageView imageView=helper.getView(R.id.imageView);
            String ori_img=item.getProduct().getApp_img().getImg();
            int warn_number=item.getProduct().getWarn_number();
            if(warn_number==0)warn_number=1;
            imageLoader.displayImage(ori_img
                    , imageView, options);
            helper.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pd==null){
                        pd = ProgressDialog.show(mView,"","正在删除");
                    }else {
                        if(!pd.isShowing())pd.show();
                    }
                    String id = item.getId()+"";
//                    mDeleteIndex=position;
                    isLastDelete = true;
                    deleteShoppingCart(id);
                }
            });
//            imageLoader.displayImage(goodsObject.getJSONObject(Constance.product).getJSONObject(Constance.default_photo).getString(Constance.large)
//                    , holder.imageView, options);

//            holder.number_input_et.setMax(10000);//设置数量的最大值
//
//            holder.numTv.setText(goodsObject.getInt(Constance.amount)+"");
            final int finalWarn_number = warn_number;
            final EditText numTv=helper.getView(R.id.et_num);
            numTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCountDialo(helper.getPosition(),item.getAmount(),finalWarn_number);
                }
            });
            helper.setChecked(R.id.checkbox,isCheckList.get(helper.getPosition()));

            helper.setOnClickListener(R.id.rightTv, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd=ProgressDialog.show(mView,"","正在处理中...");
                    sendUpdateCart(item.getId()+"",(item.getAmount()+ finalWarn_number)+"");
                }
            });
            helper.setOnClickListener(R.id.leftTv, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getAmount()==finalWarn_number){
                        MyToast.show(mView,"亲,已经到底啦!");
                        return;
                    }
                    pd=ProgressDialog.show(mView,"","正在处理中...");
                    sendUpdateCart(item.getId()+"",(item.getAmount()-finalWarn_number)+"");
                }
            });
            CheckBox checkBox=helper.getView(R.id.checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setIsCheck(helper.getPosition(),isChecked);
                    getTotalMoney();
                }
            });
//
//            holder.contact_service_tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mView.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(NetWorkConst.QQURL)));
//                }
//            });


        }
        public void setIsCheckAll(Boolean isCheck) {
            if(AppUtils.isEmpty(goodsTotalList)) return;
            for (int i = 0; i < goodsTotalList.size(); i++) {
                isCheckList.set(i, isCheck);
            }
        }

        public void addIsCheckAll(Boolean isCheck) {
            isCheckList=new ArrayList<>();
            for (int i = 0; i < goodsTotalList.size(); i++) {
                isCheckList.add(isCheck);
            }

        }

        public void getCartGoodsCheck(){
            goods=new ArrayList<>();
            for(int i = 0; i < isCheckList.size(); i++){
                if(isCheckList.get(i)){
                    goods.add(goodsTotalList.get(i));
                }
            }
        }


        public void setIsCheck(int poistion, Boolean isCheck) {

            isCheckList.set(poistion, isCheck);
            getTotalMoney();


        }

        /**
         * 获取到总金额
         */
        public void getTotalMoney(){
            double isSumMoney = 0;
            count = 0;
            if(AppUtils.isEmpty(goodsTotalList)){
                checkAll.setChecked(false);
                money_tv.setText("￥" +0 + "");
                money_suply_tv.setText("￥" +0 + "");
                tv_count.setText(""+0);
                return;
            }
            for (int i = 0; i < goodsTotalList.size(); i++) {
                if (isCheckList.get(i) == true) {
                    double price = Double.parseDouble(goodsTotalList.get(i).getPrice());
                    int num=goodsTotalList.get(i).getAmount();
                    isSumMoney += (num * price);
                    count++;
                }
            }
            mMoney=isSumMoney;
            money_tv.setText("￥" +df.format(isSumMoney) + "");
            money_suply_tv.setText("￥" +df.format(isSumMoney) + "");
            tv_count.setText(""+count);
        }


    }

    private void showCountDialo(final int position, int count,int warn) {
        final Dialog dialog=new Dialog(mView,R.style.customDialog);
        dialog.setContentView(R.layout.item_shopcar_count_dialog);
        Window dialogWindow = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(true);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = getResources().getDisplayMetrics().widthPixels-50; // 宽度
//		lp.height = 700; // 高度
        TextView tv_add= (TextView) dialog.findViewById(R.id.tv_add);
        TextView tv_reduce= (TextView) dialog.findViewById(R.id.tv_reduce);
        final EditText et_num_dialog= (EditText) dialog.findViewById(R.id.et_num);
        final int beforeCount= goodsTotalList.get(position).getAmount();
        if(warn==0)warn=1;
        TextView tv_cancel= (TextView) dialog.findViewById(R.id.tv_cancel);
        TextView tv_ensure= (TextView) dialog.findViewById(R.id.tv_ensure);
        et_num_dialog.setText(""+count);
        final int finalWarn = warn;
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_num_dialog.getText()))
                {MyToast.show(mView,"商品数量要大于0");
                    return;}
                int  count= Integer.parseInt(et_num_dialog.getText().toString().trim());
                count+=finalWarn;
                et_num_dialog.setText(""+count);
            }
        });
        tv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_num_dialog.getText()))
                {MyToast.show(mView,"商品数量要大于0");
                    return;}
                int  count= Integer.parseInt(et_num_dialog.getText().toString().trim());
                if(count<=finalWarn){
                    return;
                }
                count-=finalWarn;
                et_num_dialog.setText(""+count);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_num_dialog.getText()))
                {MyToast.show(mView,"商品数量要大于0");
                    return;}
                int  count= Integer.parseInt(et_num_dialog.getText().toString().trim());
                if(count<finalWarn){
                    MyToast.show(mView,"商品数量要大于0");
                    return;
                }
//                updateCount(goodsTotalList.get(position).getId(),count,beforeCount);
//                goodsList.get(position).setCount(count);
                sendUpdateCart(goodsTotalList.get(position).getId()+"",count+"");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
