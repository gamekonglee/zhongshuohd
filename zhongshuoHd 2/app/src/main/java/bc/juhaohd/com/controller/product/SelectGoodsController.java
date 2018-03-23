package bc.juhaohd.com.controller.product;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.common.hxp.view.GridViewForScrollView;
import com.lib.common.hxp.view.PullToRefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.bean.GoodsDetailZsBean;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.controller.BaseController;
import bc.juhaohd.com.listener.INetworkCallBack;
import bc.juhaohd.com.ui.activity.IssueApplication;
import bc.juhaohd.com.ui.activity.product.ClassifyGoodsActivity;
import bc.juhaohd.com.ui.activity.product.ProductDetailHDNewActivity;
import bc.juhaohd.com.ui.activity.product.SelectGoodsActivity;
import bc.juhaohd.com.ui.activity.programme.DiyActivity;
import bc.juhaohd.com.ui.view.AutoLinefeedLayout;
import bc.juhaohd.com.utils.ConvertUtil;
import bc.juhaohd.com.utils.UIUtils;
import bocang.json.JSONArray;
import bocang.json.JSONObject;
import bocang.utils.AppUtils;
import bocang.utils.MyToast;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * @author: Jun
 * @date : 2017/2/16 17:30
 * @description :产品列表
 */
public class SelectGoodsController extends BaseController implements INetworkCallBack, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private SelectGoodsActivity mView;
    private EditText et_search;
    private JSONArray goodses;
    private PullToRefreshLayout mPullToRefreshLayout;
    private ProAdapter mProAdapter;
    private GridViewForScrollView order_sv;
    private int page = 1;
    private View mNullView;
    private View mNullNet;
    private Button mRefeshBtn;
    private TextView mNullNetTv;
    private TextView mNullViewTv;
    private String per_pag = "20";
    public int mScreenWidth;
    public String mSortKey;
    public String mSortValue;
    private TextView popularityTv, priceTv, newTv, saleTv;
    private ImageView price_iv;
    private  Intent mIntent;
    private ProgressBar pd;
    private Dialog skuDialog;
    private int currentProperty;
    private int[] currentAttr;


    public SelectGoodsController(SelectGoodsActivity v) {
        mView = v;
        initView();
        initViewData();
    }

    private void initViewData() {
        if(!AppUtils.isEmpty(mView.mSort)){
            if(mView.mSort==4){
                selectSortType(R.id.saleTv);
            }else if(mView.mSort==5){
                selectSortType(R.id.newTv);
            }else if(mView.mSort==2){
                selectSortType(R.id.popularityTv);
            }
        }
        selectProduct(1, per_pag, null, null, null);
    }

    private void initView() {
        et_search = (EditText) mView.findViewById(R.id.et_search);
        mPullToRefreshLayout = ((PullToRefreshLayout) mView.findViewById(R.id.refresh_view));
        mPullToRefreshLayout.setOnRefreshListener(this);
        order_sv = (GridViewForScrollView) mView.findViewById(R.id.priductGridView);
        mProAdapter = new ProAdapter();
        order_sv.setAdapter(mProAdapter);
        order_sv.setOnItemClickListener(this);

        mNullView = mView.findViewById(R.id.null_view);
        mNullNet = mView.findViewById(R.id.null_net);
        mRefeshBtn = (Button) mNullNet.findViewById(R.id.refesh_btn);
        mNullNetTv = (TextView) mNullNet.findViewById(R.id.tv);
        mNullViewTv = (TextView) mNullView.findViewById(R.id.tv);
        mScreenWidth = mView.getResources().getDisplayMetrics().widthPixels;
        popularityTv = (TextView) mView.findViewById(R.id.popularityTv);
        priceTv = (TextView) mView.findViewById(R.id.priceTv);
        newTv = (TextView) mView.findViewById(R.id.newTv);
        saleTv = (TextView) mView.findViewById(R.id.saleTv);
        price_iv = (ImageView) mView.findViewById(R.id.price_iv);
        pd = (ProgressBar) mView.findViewById(R.id.pd);
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                // 修改回车键功能
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 先隐藏键盘
                    ((InputMethodManager) mView.getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(mView
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }
                selectProduct(1, per_pag, null, null, null);
                return false;
            }
        });
    }

    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }


    public void selectSortType(int type) {
        popularityTv.setTextColor(mView.getResources().getColor(R.color.fontColor60));
        priceTv.setTextColor(mView.getResources().getColor(R.color.fontColor60));
        newTv.setTextColor(mView.getResources().getColor(R.color.fontColor60));
        saleTv.setTextColor(mView.getResources().getColor(R.color.fontColor60));
        price_iv.setImageResource(R.drawable.arror);

        switch (type) {
            case R.id.popularityTv:
                popularityTv.setTextColor(mView.getResources().getColor(R.color.colorPrimaryRed));
                mSortKey="2";//人气
                mSortValue="1";//排序
                break;
            case R.id.stylell:
                priceTv.setTextColor(mView.getResources().getColor(R.color.colorPrimaryRed));
                price_iv.setImageResource(R.drawable.arror_top);
                mSortKey="1";//价格
                mSortValue="2";//排序
                break;
            case 2:
                priceTv.setTextColor(mView.getResources().getColor(R.color.colorPrimaryRed));
                price_iv.setImageResource(R.drawable.arror_button);
                mSortKey="1";//价格
                mSortValue="1";//排序
                break;
            case R.id.newTv:
                newTv.setTextColor(mView.getResources().getColor(R.color.colorPrimaryRed));
                mSortKey="5";//新品
                mSortValue="1";//排序
                break;
            case R.id.saleTv:
                saleTv.setTextColor(mView.getResources().getColor(R.color.colorPrimaryRed));
                mSortKey="4";//销售
                mSortValue="1";//排序
                break;
        }
        page=1;

        selectProduct(1, per_pag, null, null, null);


    }


    public void selectProduct(int page, String per_page, String brand, String category, String shop) {
        String keyword = et_search.getText().toString();
        pd.setVisibility(View.VISIBLE);
        mNetWork.sendGoodsList(page, per_page, brand, mView.mCategoriesId,  mView.mFilterAttr, shop, keyword, mSortKey, mSortValue, this);
    }

    @Override
    public void onSuccessListener(String requestCode, JSONObject ans) {
        mView.hideLoading();
        pd.setVisibility(View.INVISIBLE);
        switch (requestCode) {
            case NetWorkConst.PRODUCT:
                if (null == mView || mView.isFinishing())
                    return;
                if (null != mPullToRefreshLayout) {
                    dismissRefesh();
                }

                JSONArray goodsList = ans.getJSONArray(Constance.goodsList);
                if (AppUtils.isEmpty(goodsList) ||goodsList.length()==0) {
                    if (page == 1) {
                        mNullView.setVisibility(View.VISIBLE);
                    }else{
                        MyToast.show(mView, "没有更多数据了!");
                    }

                    dismissRefesh();
                    return;
                }

                mNullView.setVisibility(View.GONE);
                mNullNet.setVisibility(View.GONE);
                getDataSuccess(goodsList);
                break;
        }
    }

    @Override
    public void onFailureListener(String requestCode, JSONObject ans) {
        mView.hideLoading();
        pd.setVisibility(View.INVISIBLE);
        if (null == mView || mView.isFinishing())
            return;
        this.page--;

        if (AppUtils.isEmpty(ans)) {
            mNullNet.setVisibility(View.VISIBLE);
            mRefeshBtn.setOnClickListener(mRefeshBtnListener);
            return;
        }

        if (null != mPullToRefreshLayout) {
            mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    private void dismissRefesh() {
        mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    private void getDataSuccess(JSONArray array) {
        if (1 == page)
            goodses = array;
        else if (null != goodses) {
            for (int i = 0; i < array.length(); i++) {
                goodses.add(array.getJSONObject(i));
            }

            if (AppUtils.isEmpty(array))
                MyToast.show(mView, "没有更多内容了");
        }
        mProAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener mRefeshBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onRefresh();
        }
    };

    public void onRefresh() {
        page = 1;
        selectProduct(page, per_pag, null, null, null);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        page = 1;
        selectProduct(page, per_pag, null, null, null);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        page=page+1;
        selectProduct(page, per_pag, null, null, null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mView.isSelectGoods==true){
            showSkuDialo(position);
        }else{
            mIntent= new Intent(mView, ProductDetailHDNewActivity.class);
            int productId=goodses.getJSONObject(position).getInt(Constance.id);
            mIntent.putExtra(Constance.product,productId);
            mView.startActivity(mIntent);
        }


    }

    /**
     * 筛选
     */
    public void openClassify() {
        onRefresh();
//        IssueApplication.isClassify=true;
//        IntentUtil.startActivity(mView, ClassifyGoodsActivity.class,true);
        mIntent = new Intent(mView, ClassifyGoodsActivity.class);
        mView.startActivityForResult(mIntent, 103);
    }


    private class ProAdapter extends BaseAdapter {
        public ProAdapter() {
        }

        @Override
        public int getCount() {
            if (null == goodses)
                return 0;
            return goodses.length();
        }

        @Override
        public JSONObject getItem(int position) {
            if (null == goodses)
                return null;
            return goodses.getJSONObject(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mView, R.layout.item_gridview_fm_product, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                holder.check_iv = (ImageView) convertView.findViewById(R.id.check_iv);
                holder.textView = (TextView) convertView.findViewById(R.id.name_tv);
                holder.old_price_tv = (TextView) convertView.findViewById(R.id.old_price_tv);
                holder.price_tv = (TextView) convertView.findViewById(R.id.price_tv);
                RelativeLayout.LayoutParams lLp = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
                float h = (mScreenWidth - ConvertUtil.dp2px(mView, 45.8f)) / 2;
                lLp.height = (int) h;
                holder.imageView.setLayoutParams(lLp);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                String name=goodses.getJSONObject(position).getString(Constance.name);
                holder.textView.setText(name);
//                holder.imageView.setImageResource(R.drawable.bg_default);
                ImageLoader.getInstance().displayImage(goodses.getJSONObject(position).getJSONObject(Constance.default_photo).getString(Constance.large)
                        , holder.imageView);

                JSONArray propertieArray = goodses.getJSONObject(position).getJSONArray(Constance.properties);
                if(!AppUtils.isEmpty(propertieArray)){
                    JSONArray attrsArray = propertieArray.getJSONObject(0).getJSONArray(Constance.attrs);
                    int price = attrsArray.getJSONObject(0).getInt(Constance.attr_price);
                    double oldPrice=Double.parseDouble(goodses.getJSONObject(position).getString(Constance.price))+price;
                    double currentPrice=Double.parseDouble(goodses.getJSONObject(position).getString(Constance.current_price))+price;
                    holder.old_price_tv.setText("￥" + oldPrice);
                    holder.old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.price_tv.setText("￥" + currentPrice);
                }else{
                    holder.old_price_tv.setText("￥" + goodses.getJSONObject(position).getString(Constance.price));
                    holder.old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.price_tv.setText("￥" + goodses.getJSONObject(position).getString(Constance.current_price));
                }
                holder.check_iv.setVisibility(View.GONE);
                if(mView.isSelectGoods==true){
                    for (int i = 0; i < IssueApplication.mSelectProducts.length(); i++) {
                        String goodName = IssueApplication.mSelectProducts.getJSONObject(i).getString(Constance.name);
                        if (name.equals(goodName)) {
                            holder.check_iv.setVisibility(View.VISIBLE);
                            break;
                        }

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            ImageView check_iv;
            TextView textView;
            TextView old_price_tv;
            TextView price_tv;

        }
    }
    public void showSkuDialo(final int position) {
//    }
        if (goodses == null) {
            return;
        }
        final String[] mProperty = new String[1];
        skuDialog = UIUtils.showBottomInDialog(mView, R.layout.dialog_sku_diy, mView.getResources().getDisplayMetrics().heightPixels / 2 + UIUtils.dip2PX(250 + 60));
        final View rootview= skuDialog.findViewById(R.id.view);
        final TextView tv_dialog_price = (TextView) skuDialog.findViewById(R.id.tv_price);
        final TextView tv_dialog_name= skuDialog.findViewById(R.id.tv_name);
        final TextView tv_dialog_stock= skuDialog.findViewById(R.id.tv_stock);
        final TextView tv_dialog_has_choose= skuDialog.findViewById(R.id.tv_has_choose);
        final ImageView iv_dialog_goods = (ImageView) skuDialog.findViewById(R.id.iv_goods);
        Button btn_dialog_add_to_sc = (Button) skuDialog.findViewById(R.id.btn_add_to_shopcar);
        RelativeLayout rl_dismiss = (RelativeLayout) skuDialog.findViewById(R.id.rl_dismiss);
        final LinearLayout ll_skulist = (LinearLayout) skuDialog.findViewById(R.id.ll_skulist);
        final TextView tv_reduce = (TextView) skuDialog.findViewById(R.id.tv_reduce);
        final TextView tv_add = (TextView) skuDialog.findViewById(R.id.tv_add);
        rl_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(skuDialog !=null&& skuDialog.isShowing()) skuDialog.dismiss();
            }
        });
        final JSONArray propertiesList=goodses.getJSONObject(position).getJSONArray(Constance.properties);
        currentAttr = new int[]{0};
        currentProperty = 0;
            double tempPrice=0;
            for(int i=0;i<propertiesList.length();i++){
                if(propertiesList.getJSONObject(i).getString(Constance.name).contains("规格")){
                    currentProperty =i;
                    break;
                }
            }
            tempPrice=getLevelPrice(position, currentProperty,0);
            for(int j = 0; j<propertiesList.getJSONObject(currentProperty).getJSONArray(Constance.attrs).length(); j++){

                if(tempPrice>getLevelPrice(position, currentProperty,j)){
                    tempPrice=getLevelPrice(position, currentProperty,j);
                    currentAttr[0] =j;
                }
            }
        tv_dialog_price.setText("¥" + getLevelPrice(position,0,0));
        tv_dialog_name.setText(goodses.getJSONObject(position).getString(Constance.name));

        if(goodses.getJSONObject(position).getJSONArray(Constance.properties)!=null&&goodses.getJSONObject(position).getJSONArray(Constance.properties).length()>0)
        {
            tv_dialog_has_choose.setText("已选择 \""+goodses.getJSONObject(position).getJSONArray(Constance.properties).getJSONObject(0).getJSONArray(Constance.attrs).getJSONObject(0).getJSONObject(Constance.attr_name)+"\"");
        }

//        propertyArray = new String[propertiesList.size()];
//        for(int x=0;x<propertiesList.size();x++){
//            propertyArray[x]=propertiesList.get(x).getName();
//        }
//        tv_dialog_no.setText("商品编号：" + goodsDetail.getProductInfo().getPid());
        String imgUrl=NetWorkConst.SCENE_HOST+propertiesList.getJSONObject(0).getJSONArray(Constance.attrs).getJSONObject(currentAttr[0]).getString(Constance.img);

        ImageLoader.getInstance().displayImage(imgUrl, iv_dialog_goods);
        btn_dialog_add_to_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i< IssueApplication.mSelectProducts.length();i++){
                String selectName= IssueApplication.mSelectProducts.getJSONObject(i).getString(Constance.name);
                String selectCproperty=IssueApplication.mSelectProducts.getJSONObject(i).getString(Constance.c_property);
                String cproperty=mProperty[0];
                String name=goodses.getJSONObject(position).getString(Constance.name);
//                MyToast.show(mView,"currentProperty"+cproperty+",selectCp"+selectCproperty);
                if(selectName.equals(name)&&cproperty!=null&&selectCproperty.equals(cproperty)){
                    IssueApplication.mSelectProducts.delete(i);
                    mProAdapter.notifyDataSetChanged();
                    mView.select_num_tv.setText(IssueApplication.mSelectProducts.length() + "");
                    skuDialog.dismiss();
                    return;
                }
            }
                goodses.getJSONObject(position).add(Constance.c_property,mProperty[0]);
                String url=NetWorkConst.SCENE_HOST+propertiesList.getJSONObject(currentProperty).getJSONArray(Constance.attrs).getJSONObject(currentAttr[0]).getString(Constance.img);
                if(url==null)url=goodses.getJSONObject(position).getJSONObject(Constance.app_img).getString(Constance.img);
                goodses.getJSONObject(position).add(Constance.c_url,url);
                goodses.getJSONObject(position).add(Constance.c_position,currentAttr[0]);
            IssueApplication.mSelectProducts.add(goodses.getJSONObject(position));
            mProAdapter.notifyDataSetChanged();
            mView.select_num_tv.setText(IssueApplication.mSelectProducts.length() + "");
            skuDialog.dismiss();
            }
        });
        if (propertiesList == null || propertiesList.length() <= 0) {
            TextView tv_auto = (TextView) View.inflate(mView,R.layout.item_dialog_text,null);
            tv_auto.setText(goodses.getJSONObject(position).getString(Constance.name));
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
        final List<List<TextView>> tvListList = new ArrayList<>();
        List<LinearLayout> linearLayouts = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            final AutoLinefeedLayout autoLinefeedLayout = new AutoLinefeedLayout(mView);
            final LinearLayout linearLayout = new LinearLayout(mView);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayouts.add(linearLayout);
            final TextView textView =  (TextView) View.inflate(mView,R.layout.item_dialog_text_title,null);
            textView.setText(propertiesList.getJSONObject(currentProperty).getString(Constance.name));
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
            for (int g = 0; g < propertiesList.getJSONObject(currentProperty).getJSONArray(Constance.attrs).length(); g++) {
                final LinearLayout linearLayoutForTv = new LinearLayout(mView);
                final TextView tv_auto =  (TextView) View.inflate(mView,R.layout.item_dialog_text,null);
                tv_auto.setText(propertiesList.getJSONObject(currentProperty).getJSONArray(Constance.attrs).getJSONObject(g).getString(Constance.attr_name));
//                        tv_auto.setBackground(getDrawable(R.drawable.bg_corner_8c8e91));
//                        tv_auto.setTextColor(getResources().getColor(R.color.tv_232326));
                tv_auto.setGravity(Gravity.CENTER);
                tv_auto.setSingleLine(true);
                tv_auto.setEnabled(true);
//                    tv_auto.setTextSize(UIUtils.dip2PX(24));
                tv_auto.setPadding(UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8), UIUtils.dip2PX(8));
                final int finalY = g;
                final int finalCurrentProperty = currentProperty;
                tv_auto.setOnClickListener(new View.OnClickListener() {


                    private String mPrice;

                    @Override
                    public void onClick(View v) {
                        tvListList.get(0).get(finalY).setEnabled(false);
                        currentAttr[0] =finalY;
                        currentProperty=finalCurrentProperty;
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
                        mProperty[0] = "{\"id\":"+propertiesList.getJSONObject(finalCurrentProperty).getJSONArray(Constance.attrs).getJSONObject(finalY).getString(Constance.id)+"}";
                        String name=propertiesList.getJSONObject(finalCurrentProperty).getJSONArray(Constance.attrs).getJSONObject(finalY).getString(Constance.attr_name);
//                            tv_dialog_name.setText(name);
                        tv_dialog_has_choose.setText("已选择\""+name+"\'");
                        String imgUrl=NetWorkConst.SCENE_HOST+propertiesList.getJSONObject(finalCurrentProperty).getJSONArray(Constance.attrs).getJSONObject(finalY).getString(Constance.img);
                        ImageLoader.getInstance().displayImage(imgUrl,iv_dialog_goods);
                        double price=getLevelPrice(position,finalCurrentProperty,finalY);


                        mPrice = ""+price;
//                            mPrice=""+(Double.parseDouble(goodsDetailZsBean.getCurrent_price())+price);
                        tv_dialog_price.setText("￥" + mPrice);
                        String stock="0";
                        String goods_attr=propertiesList.getJSONObject(finalCurrentProperty).getJSONArray(Constance.attrs).getJSONObject(finalY).getString(Constance.attr_name);
                        if(propertiesList.getJSONObject(finalCurrentProperty).getJSONArray(Constance.attrs).getJSONObject(currentAttr[0]).getString(Constance.number)!=null){
                            String stockstr= propertiesList.getJSONObject(finalCurrentProperty).getJSONArray(Constance.attrs).getJSONObject(currentAttr[0]).getString(Constance.number);
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

//                        tv_stock.setText("销量："+goodsDetailZsBean.getSales_count());
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

//                if (propertyArray[currentProperty].equals(propertiesList.get(currentProperty).getAttrs().get(finalY).getAttr_name())) {
////                            currentPosition=y;
////                            break;
//                    tv_auto.setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_orange_full_oval));
//                    tv_auto.setTextColor(Color.WHITE);
//                    tv_auto.setEnabled(false);
//                } else {
//                    tv_auto.setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_efefef_10));
//                    tv_auto.setTextColor(mView.getResources().getColor(R.color.tv_333333));
//                    tv_auto.setEnabled(true);
//                }
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
        tvListList.get(0).get(currentAttr[0]).performClick();
//            sendGoShoppingCart(goodsDetailZsBean.getId()+"",mView.mProperty, Integer.parseInt(et_num.getText().toString()));
    }

    private double getLevelPrice(int p,int x,int y) {
        String levelid="";
        bocang.json.JSONObject mUser=IssueApplication.mUserObject;
        if(mUser!=null&&mUser.length()>0){
            levelid=IssueApplication.mUserObject.getString(Constance.level_id);
        }
        JSONArray attrsAry=goodses.getJSONObject(p).getJSONArray(Constance.properties).getJSONObject(x).getJSONArray(Constance.attrs);

        double price;
        if(levelid.equals("104"))
        {
            price= Double.parseDouble(attrsAry.getJSONObject(y).getString(Constance.attr_price_5));
        }else if(levelid.equals("103")){
            price=Double.parseDouble(attrsAry.getJSONObject(y).getString(Constance.attr_price_4));
        }else if(levelid.equals("102")){
            price=Double.parseDouble(attrsAry.getJSONObject(y).getString(Constance.attr_price_3));
        }else if(levelid.equals("101")){
            price=Double.parseDouble(attrsAry.getJSONObject(y).getString(Constance.attr_price_2));
        }else{
            price=Double.parseDouble(attrsAry.getJSONObject(y).getString(Constance.attr_price_1));
        }
        return price;
    }

}
