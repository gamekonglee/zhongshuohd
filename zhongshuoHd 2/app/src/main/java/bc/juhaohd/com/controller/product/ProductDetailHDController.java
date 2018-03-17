package bc.juhaohd.com.controller.product;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.google.gson.Gson;
import com.lib.common.hxp.view.GridViewForScrollView;
import com.lib.common.hxp.view.ListViewForScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.bean.GoodsDetailZsBean;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.controller.BaseController;
import bc.juhaohd.com.listener.INetworkCallBack;
import bc.juhaohd.com.listener.INetworkCallBack02;
import bc.juhaohd.com.listener.INumberInputListener;
import bc.juhaohd.com.listener.ITwoCodeListener;
import bc.juhaohd.com.ui.activity.ConvenientBannerActivity;
import bc.juhaohd.com.ui.activity.IssueApplication;
import bc.juhaohd.com.ui.activity.product.ProductDetailHDActivity;
import bc.juhaohd.com.ui.activity.product.ProductDetailHDNewActivity;
import bc.juhaohd.com.ui.activity.programme.DiyActivity;
import bc.juhaohd.com.ui.activity.programme.ImageDetailActivity;
import bc.juhaohd.com.ui.adapter.ParamentAdapter;
import bc.juhaohd.com.ui.view.AutoLinefeedLayout;
import bc.juhaohd.com.ui.view.NumberInputView;
import bc.juhaohd.com.ui.view.popwindow.TwoCodeSharePopWindow;
import bc.juhaohd.com.utils.ImageUtil;
import bc.juhaohd.com.utils.MyShare;
import bc.juhaohd.com.utils.UIUtils;
import bocang.utils.AppDialog;
import bocang.utils.AppUtils;
import bocang.utils.LogUtils;
import bocang.utils.MyToast;

import static bc.juhaohd.com.utils.UIUtils.getResources;

/**
 * @author: Jun
 * @date : 2017/4/1 14:39
 * @description :
 */
public class ProductDetailHDController extends BaseController implements INetworkCallBack, View.OnClickListener {
    private ProductDetailHDNewActivity mView;
    private ConvenientBanner mConvenientBanner;
    private ArrayList<String> paths = new ArrayList<>();
    private double mIsLike = 0;
    private ListViewForScrollView properties_lv;
//    private JSONArray propertiesList;
    private ProAdapter mAdapter;
    private GoodsDetailZsBean.Attrs itemObject;
    private TextView name_tv, proPriceTv;
    private ParamentAdapter mParamentAdapter;
    private ListViewForScrollView parameter_lv;
    public WebView mWebView;
    private ImageView collectIv;
    private NumberInputView mNumberInputView;
//    private List<List<ProductSKUListBean>> productSkuListList;
//    private List<ProductSKUListBean> productSKUListBean;
    private String mPrice = "";
    private Intent mIntent;
    private int mAmount = 1;
//    private String mProperty = "";
    private ImageView two_code_iv;
    private TextView old_price;
    private String mOldPrice;
    private Dialog skuDialog;
    private String productName;
    private List<String> propertyList;
    private String[] propertyArray;
    private String all_name;
    private List<GoodsDetailZsBean.Properties> propertiesList;
    private GoodsDetailZsBean goodsDetailZsBean;
    private int pid;
    private TextView tv_stock;
    private TextView tv_goods_num;
    private int currentAttr;
    private View no_web;
    private int currentProperty;
    public  boolean isWebFinished;
    private List<List<TextView>> tvListList;
    private Dialog countDialog;
    private int currentCount;
    private TextView et_sku_num;


    public ProductDetailHDController(ProductDetailHDActivity v) {
//        mView = v;
        initView();
        initViewData();
    }

    public  ProductDetailHDController(ProductDetailHDNewActivity v) {
        mView = v;
        initView();
        initViewData();
    }

    private void initViewData() {
        sendProductDetail();
        sendProductDetail02();

    }


    /**
     * 产品详情
     */
    public void sendProductDetail() {
        mView.setShowDialog(true);
        mView.setShowDialog("载入中...");
        mView.showLoading();

        int id = mView.productId;
        if (AppUtils.isEmpty(id))
            return;
        mNetWork.sendProductDetail02(id + "", new INetworkCallBack02() {
            @Override
            public void onSuccessListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                mView.hideLoading();
//                LogUtils.logE("ans:",ans.toString());
                getProductDetail(ans.getJSONObject(Constance.product));
//                showSkuDiyDialo();
            }
            @Override
            public void onFailureListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                mView.hideLoading();
                MyToast.show(mView, "数据异常!");
            }
        });
    }


    /**
     * 产品详情
     */
    public void sendProductDetail02() {
        mNetWork.sendProductDetail(mView.productId, this);
    }

    /**
     * 获取产品详情信息
     */
    private void getProductDetail(com.alibaba.fastjson.JSONObject productObject) {
//        LogUtils.logE("product",productObject.toString());
        goodsDetailZsBean = new Gson().fromJson(productObject.toString(),GoodsDetailZsBean.class);
        final String value = goodsDetailZsBean.getGoods_desc();
        mIsLike = (int)goodsDetailZsBean.getIs_liked();
        productName = goodsDetailZsBean.getName();
        mPrice = goodsDetailZsBean.getCurrent_price();
        mOldPrice = goodsDetailZsBean.getPrice();

//        productSKUListBean
        name_tv.setText(productName);
//        com.alibaba.fastjson.JSONArray array = productObject.getJSONArray(Constance.photos);
        List<GoodsDetailZsBean.Photos> photosList= goodsDetailZsBean.getPhotos();
        for (int i = 0; i < photosList.size(); i++) {
            paths.add(photosList.get(i).getLarge());
        }
        getWebView(value);
        mConvenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, paths);
        mConvenientBanner.setPageIndicator(new int[]{R.drawable.dot_unfocuse, R.drawable.dot_focuse});
//        propertiesList = productObject.getJSONArray(Constance.properties);
        propertiesList = goodsDetailZsBean.getProperties();
        mAdapter = new ProAdapter();
        properties_lv.setAdapter(mAdapter);
        List<GoodsDetailZsBean.Attachments> attachmentsList= goodsDetailZsBean.getAttachments();
//        JSONArray attachArray = productObject.getJSONArray(Constance.attachments);
        mParamentAdapter = new ParamentAdapter(attachmentsList, mView);
        parameter_lv.setAdapter(mParamentAdapter);
        parameter_lv.setDivider(null);//去除listview的下划线
        selectCollect();

        ProductDetailHDNewActivity.isXianGou=false;
        com.alibaba.fastjson.JSONObject group_buy=productObject.getJSONObject(Constance.group_buy);
//        GoodsDetailZsBean.Properties
//        LogUtils.logE("group:buty", String.valueOf(group_buy));
        if(null!=group_buy&&!"212".equals(""+group_buy)){
            int isFinish=group_buy.getInteger(Constance.is_finished);
            if(isFinish==0){
                ProductDetailHDNewActivity.isXianGou=true;
            }
        }
        if(ProductDetailHDNewActivity.isXianGou) {
//            time_ll.setVisibility(View.VISIBLE);
            mOldPrice = goodsDetailZsBean.getCurrent_price();
            try {
                mPrice=group_buy.getJSONObject(Constance.ext_info).getJSONArray(Constance.price_ladder).getJSONObject(0).getString(Constance.price);
            }catch (Exception e){
                mPrice=mOldPrice;
            }

        }
        old_price.setText("￥" +mOldPrice);
        old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        proPriceTv.setText("￥" + mPrice);
        tv_stock.setText("销量："+goodsDetailZsBean.getSales_count());
        currentAttr = 0;
        currentProperty = 0;
        if (propertiesList.size() > 0) {
            double tempPrice=0;
            for(int i=0;i<propertiesList.size();i++){
               if(propertiesList.get(i).getName().contains("规格")){
                   currentProperty=i;
                   break;
               }
            }
            tempPrice=getLevelPrice(currentProperty,0);
            for(int j=0;j<propertiesList.get(currentProperty).getAttrs().size();j++){

                    if(tempPrice>getLevelPrice(currentProperty,j)){
                        tempPrice=getLevelPrice(currentProperty,j);
                        currentAttr =j;
                    }
            }
//            MyToast.show(mView,"currentAttr"+currentAttr);
            List<GoodsDetailZsBean.Attrs> attrsArray = propertiesList.get(currentProperty).getAttrs();
            final String name = attrsArray.get(currentAttr).getAttr_name();
            if (!AppUtils.isEmpty(attrsArray)) {
                double price =getLevelPrice(currentProperty, currentAttr);

                String parament = attrsArray.get(currentAttr).getAttr_name();
                double currentPrice =  price;
                mPrice=currentPrice+"";
                mOldPrice=(price)+"";
                proPriceTv.setText("￥" + currentPrice);
                old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                old_price.setText("￥" +mOldPrice);
                mView.mProperty = "{\"id\":"+attrsArray.get(currentAttr).getId()+"}";
            }
        }


    }

    private double getLevelPrice(int x,int y) {
        String levelid="";
        bocang.json.JSONObject mUser=IssueApplication.mUserObject;
        if(mUser!=null&&mUser.length()>0){
            levelid=IssueApplication.mUserObject.getString(Constance.level_id);
        }
        List<GoodsDetailZsBean.Attrs> attrsArray=goodsDetailZsBean.getProperties().get(x).getAttrs();
        double price;
        if(levelid.equals("104"))
        {
            price=attrsArray.get(y).getAttr_price_5();
        }else if(levelid.equals("103")){
            price=attrsArray.get(y).getAttr_price_4();
        }else if(levelid.equals("102")){
            price=attrsArray.get(y).getAttr_price_3();
        }else if(levelid.equals("101")){
            price=attrsArray.get(y).getAttr_price_2();
        }else{
            price=attrsArray.get(y).getAttr_price_1();
        }
        return price;
    }

    /**
     * 收藏图标状态
     */
    private void selectCollect() {
        if (mIsLike == 0) {
            collectIv.setImageResource(R.drawable.ic_collect_normal);
        } else {
            collectIv.setImageResource(R.drawable.ic_collect_press);
        }
    }

    /**
     * 分享产品
     */
    public void sendShareProduct() {
        shareUrl();
    }

    /**
     * 联系客服
     */
    public void sendCall() {
        mView.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(NetWorkConst.QQURL)));
    }

    /**
     * 购物车列表
     */
    public void sendcollectProduct() {
        mView.setShowDialog(true);
        mView.setShowDialog("正在收藏中!");
        mView.showLoading();
        String id = mView.productId + "";
        if (mIsLike == 0) {
            mNetWork.sendAddLikeCollect(id, this);
            mIsLike = 1;
        } else {
            mNetWork.sendUnLikeCollect(id, this);
            mIsLike = 0;
        }
    }

    /**
     * 配配看
     */
    public void sendDiy() {
        if (AppUtils.isEmpty(mView.goodses)) {
            MyToast.show(mView, "还没加载完毕，请稍后再试");
            return;
        }
        showSkuDiyDialo();

    }

    /**
     * 加入购物车
     */
    public void sendGoCart() {
        mView.setShowDialog(true);
        mView.setShowDialog("正在加入购物车中...");
        mView.showLoading();

        sendGoShoppingCart(mView.productId + "", mView.mProperty, mAmount);
    }


    private void shareUrl() {
        if (AppUtils.isEmpty(mView.goodses))
            return;
        String title = "来自 " + mView.goodses.getString(Constance.name) + " 产品的分享";
        MyShare.get(mView).putString(Constance.sharePath, mView.goodses.getString(Constance.share_url));
        MyShare.get(mView).putString(Constance.shareRemark, title);
        TwoCodeSharePopWindow popWindow = new TwoCodeSharePopWindow(mView, mView);
        popWindow.onShow(mView.main_ll);
        popWindow.setListener(new ITwoCodeListener() {
            @Override
            public void onTwoCodeChanged(String path) {

            }
        });
    }

    public void sendShoppingCart() {

        mNetWork.sendShoppingCart(this);
    }
    private void showAddGoodsToast(String words) {
        Toast toast=new Toast(mView);
        toast.setView(View.inflate(mView,R.layout.toast_layout_success,null));
        TextView tv_words= (TextView) toast.getView().findViewById(R.id.tv_words);
        tv_words.setText(words);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    private void showNoGoodsToast(String words) {
        Toast toast=new Toast(mView);
        toast.setView(View.inflate(mView,R.layout.toast_layout_no_goods,null));
        TextView tv_words= (TextView) toast.getView().findViewById(R.id.tv_words);
        tv_words.setText(words);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    /**
     * 加入购物车
     *
     * @param product
     * @param property
     * @param mount
     */
    private void sendGoShoppingCart(String product, String property, int mount) {
//        LogUtils.logE("sendShopcar","product:"+product+",propert:"+property+",mount:"+mount);
        mNetWork.sendShoppingCart(product, property, mount, this);
    }

    @Override
    public void onSuccessListener(String requestCode, bocang.json.JSONObject ans) {
        mView.hideLoading();
        switch (requestCode) {
            case NetWorkConst.ADDCART:
//                MyToast.show(mView, UIUtils.getString(R.string.go_cart_ok));
                showAddGoodsToast(UIUtils.getString(R.string.go_cart_ok));
                if(skuDialog!=null&&skuDialog.isShowing())skuDialog.dismiss();

                sendShoppingCart();
                break;
            case NetWorkConst.GETCART:
                if (ans.getJSONArray(Constance.goods_groups).length() > 0) {
                    IssueApplication.mCartCount = ans.getJSONArray(Constance.goods_groups)
                            .getJSONObject(0).getJSONArray(Constance.goods).length();
                    tv_goods_num.setVisibility(View.VISIBLE);
                    tv_goods_num.setText(""+ans.getJSONArray(Constance.goods_groups).length());
                } else {
                    IssueApplication.mCartCount = 0;
                    tv_goods_num.setVisibility(View.GONE);
                }
                EventBus.getDefault().post(Constance.CARTCOUNT);
                break;
            case NetWorkConst.ADDLIKEDPRODUCT:
                selectCollect();
                break;
            case NetWorkConst.ULIKEDPRODUCT:
                selectCollect();
                break;
            case NetWorkConst.PRODUCTDETAIL:
                mView.goodses = ans.getJSONObject(Constance.product);
                if(mView.goodses!=null){
                    String title = "来自 " + mView.goodses.getString(Constance.name) + " 产品的分享";
                    String url=mView.goodses.getString(Constance.share_url);
                    MyShare.get(mView).putString(Constance.sharePath, url);
                    MyShare.get(mView).putString(Constance.shareRemark, title);
//                    LogUtils.logE("shareUrl",url);
                    two_code_iv.setImageBitmap(ImageUtil.createQRImage(url, UIUtils.dip2PX(164), UIUtils.dip2PX(164)));
                }

                break;
        }

    }

    @Override
    public void onFailureListener(String requestCode, bocang.json.JSONObject ans) {
        mView.hideLoading();
        if (null == mView || mView.isFinishing())
            return;
        if (AppUtils.isEmpty(ans)) {
            AppDialog.messageBox(UIUtils.getString(R.string.server_error));
            return;
        }
        showNoGoodsToast(ans.getString(Constance.error_desc));
    }

    @Override
    public void onClick(View v) {

    }


    class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            // 你可以通过layout文件来创建，也可以像我一样用代码创建z，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            imageView.setImageResource(R.drawable.bg_default);
            ImageLoader.getInstance().displayImage(data, imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mView, ImageDetailActivity.class);
                    intent.putExtra(Constance.photo, paths.get(position));
                    mView.startActivity(intent);
                }
            });
        }
    }


    private void initView() {
        mConvenientBanner = (ConvenientBanner) mView.findViewById(R.id.convenientBanner);
        properties_lv = (ListViewForScrollView) mView.findViewById(R.id.properties_lv);
        name_tv = (TextView) mView.findViewById(R.id.name_tv);
        parameter_lv = (ListViewForScrollView) mView.findViewById(R.id.parameter_lv);
        proPriceTv = (TextView) mView.findViewById(R.id.proPriceTv);
        collectIv = (ImageView) mView.findViewById(R.id.collectIv);
        old_price = (TextView) mView.findViewById(R.id.old_price);
        no_web = mView.findViewById(R.id.no_web);

        mWebView = (WebView) mView.findViewById(R.id.webView);
        two_code_iv = (ImageView) mView.findViewById(R.id.iv_code);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                LogUtils.logE("progress",newProgress+"");
                if(newProgress==100) {
//                    LogUtils.logE("contentHeight", view.getContentHeight() + "");
//                    LogUtils.logE("height", view.getHeight() + "");
                    if (isWebFinished) {
                        return;
                    }
                    if (view.getContentHeight() <= 0) {
                        no_web.setVisibility(View.VISIBLE);
                    } else {
                        no_web.setVisibility(View.GONE);
                        isWebFinished = true;
                    }
                }

            }
        });
        isWebFinished = false;
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mNumberInputView = (NumberInputView) mView.findViewById(R.id.number_input_et);
        tv_stock = mView.findViewById(R.id.tv_stock);
        mNumberInputView.setMax(10000);
        mNumberInputView.setListener(new INumberInputListener() {
            @Override
            public void onTextChange(int index) {
                mAmount = index;
            }
        });
        tv_goods_num = mView.findViewById(R.id.tv_goods_num);

        mConvenientBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(mView, ConvenientBannerActivity.class);
                    intent.putExtra("path",paths);
                    mView.startActivity(intent);
            }
        });

    }

    /**
     * 加载网页
     *
     * @param htmlValue
     */
    private void getWebView(String htmlValue) {

        String html = htmlValue;
        html=html.replace("src=\"/", "src=\"" + NetWorkConst.SCENE_HOST+"/");
//        html = html.replace("<p><img src=\"", "<img src=\"" + NetWorkConst.SCENE_HOST);
//        html = html.replace("</p>", "");

        html = html.replace("<br/>","").replace("jpg\"/>", "jpg\"/><br/>");

        html = "<meta name=\"viewport\" content=\"width=device-width\"> <div style=\"text-align:center\">" + html + " </div>";
        html=html.replace("<p>","").replaceAll("</p>","");

//        LogUtils.logE("html",html);
        LogUtils.logE("url:",html);
        mWebView.loadData(html, "text/html; charset=UTF-8", null);//这种写法可以正确解析中文

    }

    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }

    private class ProAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (null == propertiesList)
                return 0;
            return propertiesList.size();
        }


        @Override
        public GoodsDetailZsBean.Properties getItem(int position) {
            if (null == propertiesList)
                return null;
            return propertiesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int xposition, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mView, R.layout.item_properties, null);
                //
                holder = new ViewHolder();
                holder.properties_name = (TextView) convertView.findViewById(R.id.properties_name);
                holder.itemGridView = (GridViewForScrollView) convertView.findViewById(R.id.itemGridView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final String name = propertiesList.get(xposition).getName();
            holder.properties_name.setText(name + ":");

            if (holder.itemGridView != null) {
                final List<GoodsDetailZsBean.Attrs> attrsList = propertiesList.get(xposition).getAttrs();
                final ItemProAdapter gridViewAdapter = new ItemProAdapter(attrsList);
                holder.itemGridView.setAdapter(gridViewAdapter);
                holder.itemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        itemObject = attrsList.get(position);
                        double ProperPrice =getLevelPrice(xposition,position);
                        gridViewAdapter.mCurrentPoistion = position;
                        gridViewAdapter.notifyDataSetChanged();
//                        double tempPrice=(Double.parseDouble(mPrice) + ProperPrice);
//                        double tempOldPrice=(Double.parseDouble(mOldPrice) + ProperPrice);
                        double tempPrice=ProperPrice;
                        double tempOldPrice= ProperPrice;
                        proPriceTv.setText("￥" + tempPrice);
                        old_price.setText("￥" + tempOldPrice);
                        old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                        bocang.json.JSONObject jsonObject=new bocang.json.JSONObject()
                        mView.mProperty = "\"{id:"+itemObject.getId()+"}\"";
                    }
                });
            }

            return convertView;
        }


        class ViewHolder {
            TextView properties_name;
            GridViewForScrollView itemGridView;
        }
    }


    private class ItemProAdapter extends BaseAdapter implements View.OnClickListener {
        public int mCurrentPoistion = 0;
        List<GoodsDetailZsBean.Attrs> mDatas;

        public ItemProAdapter(List<GoodsDetailZsBean.Attrs> datas) {
            this.mDatas = datas;
        }

        @Override
        public int getCount() {
            if (null == mDatas)
                return 0;
            return mDatas.size();
        }


        @Override
        public GoodsDetailZsBean.Attrs getItem(int position) {
            if (null == mDatas)
                return null;
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mView, R.layout.item_properties02, null);
                //
                holder = new ViewHolder();
                holder.item_tv = (TextView) convertView.findViewById(R.id.item_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.item_tv.setText(mDatas.get(position).getAttr_name().trim());
            holder.item_tv.setSelected(mCurrentPoistion == position ? true : false);

            return convertView;
        }

        @Override
        public void onClick(View view) {

        }

        class ViewHolder {
            TextView item_tv;
        }
    }

        public void showSkuDialo() {
//    }
            if (goodsDetailZsBean == null) {
                return;
            }
            skuDialog = UIUtils.showBottomInDialog(mView, R.layout.dialog_sku, mView.getResources().getDisplayMetrics().heightPixels / 2 + UIUtils.dip2PX(250 + 60));
            final View rootview=skuDialog.findViewById(R.id.view);
            final TextView tv_dialog_price = (TextView) skuDialog.findViewById(R.id.tv_price);
            final TextView tv_dialog_name=skuDialog.findViewById(R.id.tv_name);
            final TextView tv_dialog_stock=skuDialog.findViewById(R.id.tv_stock);
            final TextView tv_dialog_has_choose=skuDialog.findViewById(R.id.tv_has_choose);
            final ImageView iv_dialog_goods = (ImageView) skuDialog.findViewById(R.id.iv_goods);
            Button btn_dialog_add_to_sc = (Button) skuDialog.findViewById(R.id.btn_add_to_shopcar);
            RelativeLayout rl_dismiss = (RelativeLayout) skuDialog.findViewById(R.id.rl_dismiss);
            final LinearLayout ll_skulist = (LinearLayout) skuDialog.findViewById(R.id.ll_skulist);
            final TextView tv_reduce = (TextView) skuDialog.findViewById(R.id.tv_reduce);
            final TextView tv_add = (TextView) skuDialog.findViewById(R.id.tv_add);
            et_sku_num = (TextView) skuDialog.findViewById(R.id.et_num);
            TextView tv_remark=skuDialog.findViewById(R.id.tv_remark);
            if(TextUtils.isEmpty(goodsDetailZsBean.getRemark())){
                tv_remark.setVisibility(View.GONE);
            }else {
                tv_remark.setVisibility(View.VISIBLE);
                tv_remark.setText(goodsDetailZsBean.getRemark());
            }
            et_sku_num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                showCountDialo(Integer.parseInt(et_sku_num.getText().toString()), et_sku_num,rootview);
                }
            });
            tv_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String numstr= et_sku_num.getText()+"";
                    if(numstr==null||numstr.toString()==null||numstr.toString().trim()==null){
                        numstr="1";
                    }
                    int num = Integer.parseInt(numstr);
                    if (num <= 1) {
                        return;
                    }
                    num--;
                    et_sku_num.setText("" + num);
                }
            });
            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String numstr= et_sku_num.getText()+"";
                    if(numstr==null||numstr.toString()==null||numstr.toString().trim()==null){
                        numstr="1";
                    }
                    int num = Integer.parseInt(numstr);
                    num++;
                    et_sku_num.setText(""+num);
                }
            });
            rl_dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(skuDialog!=null&&skuDialog.isShowing())skuDialog.dismiss();
                }
            });
            tv_dialog_price.setText("¥" + mPrice);
            tv_dialog_name.setText(goodsDetailZsBean.getName());
            if(goodsDetailZsBean.getProperties()!=null&&goodsDetailZsBean.getProperties().size()>0) {
                String stock = goodsDetailZsBean.getProperties().get(0).getAttrs().get(0).getNumber();
                if (stock == null || TextUtils.isEmpty(stock)) {
                    stock = "0";
                }
                tv_dialog_stock.setText("(库存:" + stock + ")");
            }
            if(goodsDetailZsBean.getProperties()!=null&&goodsDetailZsBean.getProperties().size()>0&&goodsDetailZsBean.getProperties().get(0).getAttrs()!=null&&goodsDetailZsBean.getProperties().get(0).getAttrs().size()>0)
            {
                tv_dialog_has_choose.setText("已选择 \""+goodsDetailZsBean.getProperties().get(0).getAttrs().get(0).getAttr_name()+"\"");
            }

            propertyArray = new String[propertiesList.size()];
            for(int x=0;x<propertiesList.size();x++){
                propertyArray[x]=propertiesList.get(x).getName();
            }
//        tv_dialog_no.setText("商品编号：" + goodsDetail.getProductInfo().getPid());
            String imgUrl=NetWorkConst.SCENE_HOST+propertiesList.get(currentProperty).getAttrs().get(currentAttr).getImg();

            ImageLoader.getInstance().displayImage(imgUrl, iv_dialog_goods);
            btn_dialog_add_to_sc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TextUtils.isEmpty(et_sku_num.getText())){
                        Toast.makeText(mView, "请输入购买数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if( Integer.parseInt(et_sku_num.getText().toString())<goodsDetailZsBean.getWarn_number()){
                        MyToast.show(mView,"至少购买"+goodsDetailZsBean.getWarn_number()+"件");
                        return;
                    }
                    sendGoShoppingCart(goodsDetailZsBean.getId()+"",mView.mProperty, Integer.parseInt(et_sku_num.getText().toString()));
                }
            });
            if (propertiesList == null || propertiesList.size() <= 0) {
                TextView tv_auto = (TextView) View.inflate(mView,R.layout.item_dialog_text,null);
                tv_auto.setText(productName);
//                        tv_auto.setBackground(getDrawable(R.drawable.bg_corner_8c8e91));
//                        tv_auto.setTextColor(getResources().getColor(R.color.tv_232326));
                tv_auto.setGravity(Gravity.CENTER);
                tv_auto.setEnabled(true);
                tv_auto.setPadding(UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8));
                tv_auto.setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_orange_full_oval));
                tv_auto.setTextColor(Color.WHITE);
//                tv_auto.setTextSize(UIUtils.dip2PX(24));
                tv_auto.setEnabled(false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(UIUtils.dip2PX(5), UIUtils.dip2PX(5), UIUtils.dip2PX(5), 0);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                tv_auto.setLayoutParams(layoutParams);
//                    tv_auto.setLetterSpacing(5);
                ll_skulist.addView(tv_auto);
                return;
            }
            ll_skulist.removeAllViews();
//                final List<GridView> gridViewList=new ArrayList<>();
            final List<AutoLinefeedLayout> autoLinefeedLayouts = new ArrayList<>();
            tvListList = new ArrayList<>();
            List<LinearLayout> linearLayouts = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                final AutoLinefeedLayout autoLinefeedLayout = new AutoLinefeedLayout(mView);
                final LinearLayout linearLayout = new LinearLayout(mView);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayouts.add(linearLayout);
                final TextView textView =  (TextView) View.inflate(mView,R.layout.item_dialog_text_title,null);
                textView.setText(propertiesList.get(currentProperty).getName());
                textView.setTextColor(mView.getResources().getColor(R.color.tv_333333));
//                textView.setTextSize(UIUtils.dip2PX(36));
                LinearLayout.LayoutParams tvlayoutps = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tvlayoutps.setMargins(UIUtils.dip2PX(10), UIUtils.dip2PX(15), 0, UIUtils.dip2PX(15));
                textView.setLayoutParams(tvlayoutps);

//                final GridView gridView=new GridView(mView);

//                gridView.setNumColumns(2);
                linearLayouts.get(0).removeAllViews();
                linearLayouts.get(0).addView(textView);
                final int finalI = 0;
                final int currentPosition = 0;
                final List<TextView> tvList = new ArrayList<>();
                for (int g = 0; g < propertiesList.get(currentProperty).getAttrs().size(); g++) {
                    final LinearLayout linearLayoutForTv = new LinearLayout(mView);
                    final TextView tv_auto =  (TextView) View.inflate(mView,R.layout.item_dialog_text,null);
                    tv_auto.setText(propertiesList.get(currentProperty).getAttrs().get(g).getAttr_name());
//                        tv_auto.setBackground(getDrawable(R.drawable.bg_corner_8c8e91));
//                        tv_auto.setTextColor(getResources().getColor(R.color.tv_232326));
                    tv_auto.setGravity(Gravity.CENTER);
                    tv_auto.setSingleLine(true);
                    tv_auto.setEnabled(true);
//                    tv_auto.setTextSize(UIUtils.dip2PX(24));
                    tv_auto.setPadding(UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8));
                    final int finalY = g;
                    tv_auto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvListList.get(0).get(finalY).setEnabled(false);
                            currentAttr=finalY;
                            for (int x = 0; x < tvListList.get(0).size(); x++) {
                                if (x != finalY) {
                                    tvListList.get(0).get(x).setEnabled(true);
                                }
                                if (!tvListList.get(0).get(x).isEnabled()) {
//                            currentPosition=y;
//                            break;
                                    tvListList.get(0).get(x).setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_orange_full_oval));
                                    tvListList.get(0).get(x).setTextColor(Color.WHITE);
                                } else {
                                    tvListList.get(0).get(x).setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_efefef_10));
                                    tvListList.get(0).get(x).setTextColor(mView.getResources().getColor(R.color.tv_333333));
                                }
                            }
//                            mProperty=new Gson().toJson(propertiesList.get(finalI).getAttrs().get(finalY),GoodsDetailZsBean.Attrs.class);
                            mView.mProperty = "{\"id\":"+propertiesList.get(currentProperty).getAttrs().get(finalY).getId()+"}";
                            String name=propertiesList.get(currentProperty).getAttrs().get(finalY).getAttr_name();
//                            tv_dialog_name.setText(name);
                            tv_dialog_has_choose.setText("已选择\""+name+"\'");
                            String imgUrl=NetWorkConst.SCENE_HOST+propertiesList.get(currentProperty).getAttrs().get(finalY).getImg();
                            ImageLoader.getInstance().displayImage(imgUrl,iv_dialog_goods);
                            double price=getLevelPrice(currentProperty,finalY);


                            mPrice=""+price;
//                            mPrice=""+(Double.parseDouble(goodsDetailZsBean.getCurrent_price())+price);
                            tv_dialog_price.setText("￥" + mPrice);
                            String stock="0";
                            String goods_attr=propertiesList.get(currentProperty).getAttrs().get(finalY).getAttr_name();
                                if(propertiesList.get(currentProperty).getAttrs().get(currentAttr).getNumber()!=null){
                                    String stockstr= propertiesList.get(currentProperty).getAttrs().get(currentAttr).getNumber();
                                    if(stockstr!=null){
                                        if(stockstr.contains(".")){
                                        stockstr=stockstr.substring(0,stockstr.indexOf("."));
                                        if(stockstr==null||stockstr.indexOf(".")==0){
                                            stock="0";
                                        }else {
                                            stock=stockstr;
                                        }
                                        }else {
                                            stock=stockstr;
                                        }
                                    }

                                }

                            tv_stock.setText("销量："+goodsDetailZsBean.getSales_count());
                            tv_dialog_stock.setText("(库存"+stock+")");
//                            mNetWork.sendProductDetail02("" + pid, new INetworkCallBack02() {
//                                @Override
//                                public void onSuccessListener(String requestCode, JSONObject ans) {
//                                    getProductDetail(ans.getJSONObject(Constance.product));
//
//                                }
//
//                                @Override
//                                public void onFailureListener(String requestCode, JSONObject ans) {
//
//                                }
//                            });

                        }
                    });

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(UIUtils.dip2PX(5), UIUtils.dip2PX(5), 0, 0);
                    tv_auto.setLayoutParams(layoutParams);

                    if (propertyArray[currentProperty].equals(propertiesList.get(currentProperty).getAttrs().get(finalY).getAttr_name())) {
//                            currentPosition=y;
//                            break;
                        tv_auto.setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_orange_full_oval));
                        tv_auto.setTextColor(Color.WHITE);
                        tv_auto.setEnabled(false);
                    } else {
                        tv_auto.setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_efefef_10));
                        tv_auto.setTextColor(mView.getResources().getColor(R.color.tv_333333));
                        tv_auto.setEnabled(true);
                    }
                    linearLayoutForTv.addView(tv_auto);
                    linearLayoutForTv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    autoLinefeedLayout.addView(linearLayoutForTv);
                    tvList.add(tv_auto);
                }
                autoLinefeedLayouts.add(autoLinefeedLayout);
                linearLayouts.get(0).addView(autoLinefeedLayouts.get(0));
                int total_width = 0;
                int tv_heigt = 0;
                tvListList.add(tvList);
                for (int b = 0; b < tvListList.get(0).size(); b++) {
                    TextView view = tvListList.get(0).get(b);
                    total_width += view.getPaint().measureText(view.getText().toString());
                    view.measure(0, 0);
                    tv_heigt = view.getMeasuredHeight();
                    total_width += UIUtils.dip2PX(5);
                }
//                    System.out.println("total_width:"+total_width);
//                    System.out.println("widthpx:"+getResources().getDisplayMetrics().widthPixels);
//                    int row=total_width/getResources().getDisplayMetrics().widthPixels+1;
//                    int column=gridView.getAdapter().getCount()/row;
//                    gridView.setNumColumns(column);
//                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, row*tv_heigt+(row-1)*UIUtils.dip2PX(5));
//                    params.setMargins(UIUtils.dip2PX(10),0,UIUtils.dip2PX(15),0);
//                    gridView.setLayoutParams(params);
//                    gridView.setVerticalSpacing(UIUtils.dip2PX(5));

//                    linearLayout.addView(gridView);
//                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams.setMargins(0,5,0,0);
//                    ll_skulist.addView(linearLayout);
                LinearLayout.LayoutParams llps = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll_skulist.addView(linearLayouts.get(finalI), llps);
            }
            tvListList.get(0).get(currentAttr).performClick();
//            sendGoShoppingCart(goodsDetailZsBean.getId()+"",mView.mProperty, Integer.parseInt(et_num.getText().toString()));
        }

        private void refreshUI() {
//            propertyList = new ArrayList<String>();
//            if (propertiesList != null && propertiesList.size()> 0&&propertiesList.get(0).getAttrs()!=null&&propertiesList.get(0).getAttrs().size()>0) {
////                                skuCount=1;
//                propertyList.add(propertiesList.get(0).getAttrs().get(0).getAttr_name());
//            }
//            for (GoodsDetailZsBean.Properties bean : propertiesList) {
//                boolean isSame = false;
//                for (String s : propertyList) {
//                    if (bean.getAttrName().equals(s)) {
//                        isSame = true;
//                        break;
//                    }
//                }
//                if (!isSame) {
//                    propertyList.add(bean.getAttrName());
//                }
//            }

//            productSkuListList = new ArrayList<List<ProductSKUListBean>>();
//            for (int i = 0; i < propertyList.size(); i++) {
//                List<ProductSKUListBean> list = new ArrayList<ProductSKUListBean>();
//                for (ProductSKUListBean bean : productSKUListBean) {
//                    if (bean.getAttrName().equals(propertyList.get(i))) {
//                        boolean hasValue = false;
//                        for (ProductSKUListBean skuListBean : list) {
//                            if (skuListBean.getAttrValue().equals(bean.getAttrValue())) {
//                                hasValue = true;
//                            }
//                        }
//                        if (!hasValue) {
//                            list.add(bean);
//                        }
//                    }
//                }
//                productSkuListList.add(list);
//            }
//            StringBuilder sb = new StringBuilder();
//            sb.append(goodsDetailZsBean.getName());
//            propertyArray = new String[propertyList.size()];
//            for (int i = 0; i < propertyList.size(); i++) {
//                for (int j = 0; j < propertiesList.get(i).getAttrs().size(); j++) {
//                    if (goodsDetailZsBean.getId() == propertiesList.get(i).getAttrs().get(j).getId()) {
//                        boolean haspropertyValue = false;
//                        for (int k = 0; k < propertyArray.length; k++) {
//                            if (propertyArray[k] != null && propertyArray[k].equals(propertiesList.get(i).getAttrs().get(j).getAttr_name())) {
//                                haspropertyValue = true;
//                                break;
//                            }
//                        }
//                        if (!haspropertyValue) {
//                            propertyArray[i] = propertiesList.get(i).getAttrs().get(j).getAttr_name();
//                            break;
//                        }
//                    }
//                }
//            }
//
//            for (int x = 0; x < propertyArray.length; x++) {
//                sb.append("、");
//                sb.append(propertyArray[x]);
//            }
//            all_name = sb.toString();
//            name_tv.setText(all_name);

    }

    private void showCountDialo(int count, final TextView et_text, final View tv) {
        countDialog = new Dialog(mView, R.style.customDialog);
        countDialog.setContentView(R.layout.item_shopcar_count_dialog);
        Window dialogWindow = countDialog.getWindow();
        countDialog.setCanceledOnTouchOutside(true);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = getResources().getDisplayMetrics().widthPixels-50; // 宽度
//		lp.height = 700; // 高度
        TextView tv_add= (TextView) countDialog.findViewById(R.id.tv_add);
        TextView tv_reduce= (TextView) countDialog.findViewById(R.id.tv_reduce);
        final EditText et_num_dialog= (EditText) countDialog.findViewById(R.id.et_num);
//        final int beforeCount= goodsTotalList.get(position).getAmount();
        TextView tv_cancel= (TextView) countDialog.findViewById(R.id.tv_cancel);
        TextView tv_ensure= (TextView) countDialog.findViewById(R.id.tv_ensure);
        et_num_dialog.setText(""+count);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_num_dialog.getText()))
                {MyToast.show(mView,"商品数量要大于0");
                    return;}
                int  count= Integer.parseInt(et_num_dialog.getText().toString().trim());
                if(count<1){
                    MyToast.show(mView,"商品数量要大于0");
                    return;
                }
                count++;
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
                if(count<1){
                    MyToast.show(mView,"商品数量要大于0");
                    return;
                }
                if(count<=1){
                    return;
                }
                count--;
                et_num_dialog.setText(""+count);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDialog.dismiss();
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_num_dialog.getText()))
                {MyToast.show(mView,"商品数量要大于0");
                    return;}
                int  count= Integer.parseInt(et_num_dialog.getText().toString().trim());
                if(count<1){
                    MyToast.show(mView,"商品数量要大于0");
                    return;
                }
                currentCount = count;
//                updateCount(goodsTotalList.get(position).getId(),count,beforeCount);
//                goodsList.get(position).setCount(count);
                et_num_dialog.setText(""+count);
                et_text.setText(""+count);
                InputMethodManager imm= (InputMethodManager) mView.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_num_dialog.getWindowToken(),0);
                imm.hideSoftInputFromWindow(mView.getWindow().getDecorView().getWindowToken(), 0);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        mView.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              handler.sendEmptyMessage(0);
                               tv.invalidate();

                            }
                        });

                    }
                }.start();

            }
        });
        countDialog.show();
    }
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            countDialog.dismiss();
            skuDialog.dismiss();
            et_sku_num=skuDialog.findViewById(R.id.et_num);
            et_sku_num.setText(""+currentCount);
            skuDialog.show();
        }
    };
    public void showSkuDiyDialo() {
//    }
        if (goodsDetailZsBean == null) {
            return;
        }
        skuDialog = UIUtils.showBottomInDialog(mView, R.layout.dialog_sku_diy, mView.getResources().getDisplayMetrics().heightPixels / 2 + UIUtils.dip2PX(250 + 60));
        final View rootview=skuDialog.findViewById(R.id.view);
        final TextView tv_dialog_price = (TextView) skuDialog.findViewById(R.id.tv_price);
        final TextView tv_dialog_name=skuDialog.findViewById(R.id.tv_name);
        final TextView tv_dialog_stock=skuDialog.findViewById(R.id.tv_stock);
        final TextView tv_dialog_has_choose=skuDialog.findViewById(R.id.tv_has_choose);
        final ImageView iv_dialog_goods = (ImageView) skuDialog.findViewById(R.id.iv_goods);
        Button btn_dialog_add_to_sc = (Button) skuDialog.findViewById(R.id.btn_add_to_shopcar);
        RelativeLayout rl_dismiss = (RelativeLayout) skuDialog.findViewById(R.id.rl_dismiss);
        final LinearLayout ll_skulist = (LinearLayout) skuDialog.findViewById(R.id.ll_skulist);
        final TextView tv_reduce = (TextView) skuDialog.findViewById(R.id.tv_reduce);
        final TextView tv_add = (TextView) skuDialog.findViewById(R.id.tv_add);
        et_sku_num = (TextView) skuDialog.findViewById(R.id.et_num);
        et_sku_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountDialo(Integer.parseInt(et_sku_num.getText().toString()), et_sku_num,rootview);
            }
        });
        tv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numstr= et_sku_num.getText()+"";
                if(numstr==null||numstr.toString()==null||numstr.toString().trim()==null){
                    numstr="1";
                }
                int num = Integer.parseInt(numstr);
                if (num <= 1) {
                    return;
                }
                num--;
                et_sku_num.setText("" + num);
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numstr= et_sku_num.getText()+"";
                if(numstr==null||numstr.toString()==null||numstr.toString().trim()==null){
                    numstr="1";
                }
                int num = Integer.parseInt(numstr);
                num++;
                et_sku_num.setText(""+num);
            }
        });
        rl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(skuDialog!=null&&skuDialog.isShowing())skuDialog.dismiss();
            }
        });
        tv_dialog_price.setText("¥" + mPrice);
        tv_dialog_name.setText(goodsDetailZsBean.getName());
        if(goodsDetailZsBean.getProperties()!=null&&goodsDetailZsBean.getProperties().size()>0) {
            String stock = goodsDetailZsBean.getProperties().get(0).getAttrs().get(0).getNumber();
            if (stock == null || TextUtils.isEmpty(stock)) {
                stock = "0";
            }
            tv_dialog_stock.setText("(库存:" + stock + ")");
        }
        if(goodsDetailZsBean.getProperties()!=null&&goodsDetailZsBean.getProperties().size()>0&&goodsDetailZsBean.getProperties().get(0).getAttrs()!=null&&goodsDetailZsBean.getProperties().get(0).getAttrs().size()>0)
        {
            tv_dialog_has_choose.setText("已选择 \""+goodsDetailZsBean.getProperties().get(0).getAttrs().get(0).getAttr_name()+"\"");
        }

        propertyArray = new String[propertiesList.size()];
        for(int x=0;x<propertiesList.size();x++){
            propertyArray[x]=propertiesList.get(x).getName();
        }
//        tv_dialog_no.setText("商品编号：" + goodsDetail.getProductInfo().getPid());
        String imgUrl=NetWorkConst.SCENE_HOST+propertiesList.get(currentProperty).getAttrs().get(currentAttr).getImg();

        ImageLoader.getInstance().displayImage(imgUrl, iv_dialog_goods);
        btn_dialog_add_to_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(mView, DiyActivity.class);
                String url=NetWorkConst.SCENE_HOST+propertiesList.get(currentProperty).getAttrs().get(currentAttr).getImg();
                if(url==null)url=goodsDetailZsBean.getApp_img().getImg();
                mIntent.putExtra(Constance.product, mView.goodses);
                mIntent.putExtra(Constance.property, mView.mProperty);
                mIntent.putExtra(Constance.url,url);
                mView.goodses.add(Constance.c_property,mView.mProperty);
                mView.goodses.add(Constance.c_url,url);
                IssueApplication.mSelectProducts.add(mView.goodses);
                LogUtils.logE("mSelect",IssueApplication.mSelectProducts.toString());
                mView.startActivity(mIntent);
            }
        });
        if (propertiesList == null || propertiesList.size() <= 0) {
            TextView tv_auto = (TextView) View.inflate(mView,R.layout.item_dialog_text,null);
            tv_auto.setText(productName);
//                        tv_auto.setBackground(getDrawable(R.drawable.bg_corner_8c8e91));
//                        tv_auto.setTextColor(getResources().getColor(R.color.tv_232326));
            tv_auto.setGravity(Gravity.CENTER);
            tv_auto.setEnabled(true);
            tv_auto.setPadding(UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8));
            tv_auto.setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_orange_full_oval));
            tv_auto.setTextColor(Color.WHITE);
//                tv_auto.setTextSize(UIUtils.dip2PX(24));
            tv_auto.setEnabled(false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(UIUtils.dip2PX(5), UIUtils.dip2PX(5), UIUtils.dip2PX(5), 0);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            tv_auto.setLayoutParams(layoutParams);
//                    tv_auto.setLetterSpacing(5);
            ll_skulist.addView(tv_auto);
            return;
        }
        ll_skulist.removeAllViews();
//                final List<GridView> gridViewList=new ArrayList<>();
        final List<AutoLinefeedLayout> autoLinefeedLayouts = new ArrayList<>();
        tvListList = new ArrayList<>();
        List<LinearLayout> linearLayouts = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            final AutoLinefeedLayout autoLinefeedLayout = new AutoLinefeedLayout(mView);
            final LinearLayout linearLayout = new LinearLayout(mView);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayouts.add(linearLayout);
            final TextView textView =  (TextView) View.inflate(mView,R.layout.item_dialog_text_title,null);
            textView.setText(propertiesList.get(currentProperty).getName());
            textView.setTextColor(mView.getResources().getColor(R.color.tv_333333));
//                textView.setTextSize(UIUtils.dip2PX(36));
            LinearLayout.LayoutParams tvlayoutps = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvlayoutps.setMargins(UIUtils.dip2PX(10), UIUtils.dip2PX(15), 0, UIUtils.dip2PX(15));
            textView.setLayoutParams(tvlayoutps);

//                final GridView gridView=new GridView(mView);

//                gridView.setNumColumns(2);
            linearLayouts.get(0).removeAllViews();
            linearLayouts.get(0).addView(textView);
            final int finalI = 0;
            final int currentPosition = 0;
            final List<TextView> tvList = new ArrayList<>();
            for (int g = 0; g < propertiesList.get(currentProperty).getAttrs().size(); g++) {
                final LinearLayout linearLayoutForTv = new LinearLayout(mView);
                final TextView tv_auto =  (TextView) View.inflate(mView,R.layout.item_dialog_text,null);
                tv_auto.setText(propertiesList.get(currentProperty).getAttrs().get(g).getAttr_name());
//                        tv_auto.setBackground(getDrawable(R.drawable.bg_corner_8c8e91));
//                        tv_auto.setTextColor(getResources().getColor(R.color.tv_232326));
                tv_auto.setGravity(Gravity.CENTER);
                tv_auto.setSingleLine(true);
                tv_auto.setEnabled(true);
//                    tv_auto.setTextSize(UIUtils.dip2PX(24));
                tv_auto.setPadding(UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8));
                final int finalY = g;
                tv_auto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvListList.get(0).get(finalY).setEnabled(false);
                        currentAttr=finalY;
                        for (int x = 0; x < tvListList.get(0).size(); x++) {
                            if (x != finalY) {
                                tvListList.get(0).get(x).setEnabled(true);
                            }
                            if (!tvListList.get(0).get(x).isEnabled()) {
//                            currentPosition=y;
//                            break;
                                tvListList.get(0).get(x).setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_orange_full_oval));
                                tvListList.get(0).get(x).setTextColor(Color.WHITE);
                            } else {
                                tvListList.get(0).get(x).setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_efefef_10));
                                tvListList.get(0).get(x).setTextColor(mView.getResources().getColor(R.color.tv_333333));
                            }
                        }
//                            mProperty=new Gson().toJson(propertiesList.get(finalI).getAttrs().get(finalY),GoodsDetailZsBean.Attrs.class);
                        mView.mProperty = "{\"id\":"+propertiesList.get(currentProperty).getAttrs().get(finalY).getId()+"}";
                        String name=propertiesList.get(currentProperty).getAttrs().get(finalY).getAttr_name();
//                            tv_dialog_name.setText(name);
                        tv_dialog_has_choose.setText("已选择\""+name+"\'");
                        String imgUrl=NetWorkConst.SCENE_HOST+propertiesList.get(currentProperty).getAttrs().get(finalY).getImg();
                        ImageLoader.getInstance().displayImage(imgUrl,iv_dialog_goods);
                        double price=getLevelPrice(currentProperty,finalY);


                        mPrice=""+price;
//                            mPrice=""+(Double.parseDouble(goodsDetailZsBean.getCurrent_price())+price);
                        tv_dialog_price.setText("￥" + mPrice);
                        String stock="0";
                        String goods_attr=propertiesList.get(currentProperty).getAttrs().get(finalY).getAttr_name();
                        if(propertiesList.get(currentProperty).getAttrs().get(currentAttr).getNumber()!=null){
                            String stockstr= propertiesList.get(currentProperty).getAttrs().get(currentAttr).getNumber();
                            if(stockstr!=null){
                                if(stockstr.contains(".")){
                                    stockstr=stockstr.substring(0,stockstr.indexOf("."));
                                    if(stockstr==null||stockstr.indexOf(".")==0){
                                        stock="0";
                                    }else {
                                        stock=stockstr;
                                    }
                                }else {
                                    stock=stockstr;
                                }
                            }

                        }

                        tv_stock.setText("销量："+goodsDetailZsBean.getSales_count());
                        tv_dialog_stock.setText("(库存"+stock+")");

                    }
                });

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(UIUtils.dip2PX(5), UIUtils.dip2PX(5), 0, 0);
                tv_auto.setLayoutParams(layoutParams);

                if (propertyArray[currentProperty].equals(propertiesList.get(currentProperty).getAttrs().get(finalY).getAttr_name())) {
//                            currentPosition=y;
//                            break;
                    tv_auto.setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_orange_full_oval));
                    tv_auto.setTextColor(Color.WHITE);
                    tv_auto.setEnabled(false);
                } else {
                    tv_auto.setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_efefef_10));
                    tv_auto.setTextColor(mView.getResources().getColor(R.color.tv_333333));
                    tv_auto.setEnabled(true);
                }
                linearLayoutForTv.addView(tv_auto);
                linearLayoutForTv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                autoLinefeedLayout.addView(linearLayoutForTv);
                tvList.add(tv_auto);
            }
            autoLinefeedLayouts.add(autoLinefeedLayout);
            linearLayouts.get(0).addView(autoLinefeedLayouts.get(0));
            int total_width = 0;
            int tv_heigt = 0;
            tvListList.add(tvList);
            for (int b = 0; b < tvListList.get(0).size(); b++) {
                TextView view = tvListList.get(0).get(b);
                total_width += view.getPaint().measureText(view.getText().toString());
                view.measure(0, 0);
                tv_heigt = view.getMeasuredHeight();
                total_width += UIUtils.dip2PX(5);
            }
            LinearLayout.LayoutParams llps = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll_skulist.addView(linearLayouts.get(finalI), llps);
        }
        tvListList.get(0).get(currentAttr).performClick();
//        btn_dialog_add_to_sc.performClick();;
//            sendGoShoppingCart(goodsDetailZsBean.getId()+"",mView.mProperty, Integer.parseInt(et_num.getText().toString()));
    }

}
