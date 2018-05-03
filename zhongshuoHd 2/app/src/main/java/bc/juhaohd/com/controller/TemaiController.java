package bc.juhaohd.com.controller;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lib.common.hxp.view.PullToRefreshLayout;
import com.lib.common.hxp.view.PullableGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.adapter.BaseAdapterHelper;
import bc.juhaohd.com.adapter.QuickAdapter;
import bc.juhaohd.com.bean.CollectGoodsBean;
import bc.juhaohd.com.bean.Default_photo;
import bc.juhaohd.com.bean.GoodsBean;
import bc.juhaohd.com.bean.GroupBuy;
import bc.juhaohd.com.bean.ScAttrs;
import bc.juhaohd.com.bean.ScProperties;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.listener.INetworkCallBack;
import bc.juhaohd.com.ui.activity.IssueApplication;
import bc.juhaohd.com.ui.activity.TemaiActivity;
import bc.juhaohd.com.ui.activity.product.ProductDetailHDNewActivity;
import bc.juhaohd.com.ui.activity.user.CollectActivity;
import bc.juhaohd.com.ui.activity.user.CollectNewActivity;
import bc.juhaohd.com.utils.ImageLoadProxy;
import bc.juhaohd.com.utils.MyShare;
import bc.juhaohd.com.utils.UIUtils;
import bocang.json.JSONArray;
import bocang.json.JSONObject;
import bocang.utils.AppDialog;
import bocang.utils.AppUtils;
import bocang.utils.MyToast;

/**
 * @author: Jun
 * @date : 2017/2/13 16:18
 * @description :
 */
public class TemaiController extends BaseController implements INetworkCallBack, PullToRefreshLayout.OnRefreshListener {
    private TemaiActivity mView;

    private JSONArray goodses;
    private PullToRefreshLayout mPullToRefreshLayout;
    private ProAdapter mProAdapter;
    private PullableGridView order_sv;
    private int page = 1;

    private View mNullView;
    private Boolean isEdit=false;
    private Boolean isCheck=false;
    private ImageView iv_edit;
    private ArrayList<Boolean> isCheckShowList=new ArrayList<>();
    private ArrayList<Boolean> isCheckList=new ArrayList<>();
    private ProgressBar pd;
    private RelativeLayout rl;
    private JSONArray goodCheckList;
    private Intent mIntent;
    private QuickAdapter goodsAdapter;
    private List<GoodsBean> goodsBeen;


    public TemaiController(TemaiActivity v) {
        mView=v;
        initView();
        initViewData();
    }

    private void initViewData() {
        page = 1;
        sendCollectProduct(page, 20);
//        pd.setVisibility(View.VISIBLE);
        mView.showLoadingPage("", R.drawable.ic_loading);
    }

    /**
     * 获取收藏列表
     * @param page
     * @param per_page
     */
    private void sendCollectProduct(int page, int  per_page) {
        mNetWork.sendGoodsList(page,"10",null,"214",null,null,null,null,null,this);
    }

    private void initView() {
        mPullToRefreshLayout = ((PullToRefreshLayout) mView.findViewById(R.id.contentView));
        mPullToRefreshLayout.setOnRefreshListener(this);

        order_sv = (PullableGridView) mView.findViewById(R.id.gridView);



        mNullView = mView.findViewById(R.id.null_view);
        iv_edit = (ImageView) mView.findViewById(R.id.iv_edit);
        pd = (ProgressBar) mView.findViewById(R.id.pd);
        rl = (RelativeLayout) mView.findViewById(R.id.rl);

        mProAdapter = new ProAdapter();

        order_sv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIntent= new Intent(mView, ProductDetailHDNewActivity.class);
                int productId =goodsBeen.get(position).getId();
                mIntent.putExtra(Constance.product, productId);
                mView.startActivity(mIntent);
            }
        });
        goodsBeen = new ArrayList<>();
        goodsAdapter = new QuickAdapter<GoodsBean>(mView, R.layout.item_gridview_goodes){
            @Override
            protected void convert(BaseAdapterHelper helper, GoodsBean item) {
                Object name=item.getName();
                if(null!=name){
                    helper.setText(R.id.tv_name,item.getName().toString());
                }
                String Token= MyShare.get(mView).getString(Constance.TOKEN);
                helper.setText(R.id.tv_price,"¥"+item.getCurrent_price());
                helper.setVisible(R.id.tv_old_price,false);
                if(!TextUtils.isEmpty(Token)) {
                    String levelid = "";
                    bocang.json.JSONObject mUser = IssueApplication.mUserObject;
                    if (mUser != null && mUser.length() > 0) {
                        levelid = IssueApplication.mUserObject.getString(Constance.level_id);
                    }
                    if (!levelid.equals("104")) {


                        int current = 0;
                        List<ScProperties> properties = item.getProperties();
                        for (int i = 0; i < properties.size(); i++) {
                            if (properties.get(i).getName().equals("规格")) {
                                current = i;
                                break;
                            }
                        }

                        helper.setText(R.id.tv_price, "代理价：¥" + UIUtils.getLevelPrice(item.getProperties(), current, 0));
                        helper.setText(R.id.tv_old_price, "原价：¥" + item.getCurrent_price());
                        TextView textView = helper.getView(R.id.tv_old_price);
                        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        helper.setVisible(R.id.tv_old_price, true);

                    }
                }
                Default_photo default_photo=item.getDefault_photo();
                if(null!=default_photo){
                    String imageUrl=item.getDefault_photo().getLarge();
                    ImageLoadProxy.displayImage(imageUrl, (ImageView) helper.getView(R.id.iv_photo));
                }
            }
        };
        order_sv.setAdapter(goodsAdapter);
    }


    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }

    public void setEdit() {
        if(AppUtils.isEmpty(goodses)){
            MyToast.show(mView,"还没有数据!");
            return;
        }
        if(isEdit){
            isEdit=false;
            iv_edit.setImageResource(R.drawable.edit);
            mProAdapter.getCheck(false, false);
            rl.setVisibility(View.GONE);
        }else{
            iv_edit.setImageResource(R.drawable.ic_ok);

            mProAdapter.getCheck(true, false);
            rl.setVisibility(View.VISIBLE);
            isEdit=true;
        }
    }

    @Override
    public void onSuccessListener(String requestCode, JSONObject ans) {
        mView.showContentView();
        pd.setVisibility(View.INVISIBLE);
        switch (requestCode){
            case NetWorkConst.PRODUCT:
                mView.hideLoading();
                if (null == mView || mView.isFinishing())
                    return;
                if (null != mPullToRefreshLayout) {
                    dismissRefesh();
                }

                JSONArray goodsList=ans.getJSONArray(Constance.goodsList);
                if (AppUtils.isEmpty(goodsList)) {
                    if (page == 1) {
                        mNullView.setVisibility(View.VISIBLE);
                    }

                    dismissRefesh();
                    return;
                }

                mNullView.setVisibility(View.GONE);
                getDataSuccess(goodsList);
                break;
            case NetWorkConst.ULIKEDPRODUCT:
                if(isLastDelete=true){
                    page = 1;
                    sendCollectProduct(page, 20);
                }
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
            return;
        }

        if (null != mPullToRefreshLayout) {
            dismissRefesh();
        }
        AppDialog.messageBox(ans.getString(Constance.error_desc));
    }


    private void getDataSuccess(JSONArray array){
//
//        if (1 == page)
//            goodses = array;
//        else if (null != goodses){
//            for (int i = 0; i < array.length(); i++) {
//                goodses.add(array.getJSONObject(i));
//            }
//
//            if(AppUtils.isEmpty(array))
//                MyToast.show(mView, "没有更多内容了");
//        }
        List<GoodsBean> temp=new ArrayList<>();
//        LogUtils.logE("temp:",array.toString());

        for (int i = 0; i < array.length(); i++) {
            try{
            temp.add(new Gson().fromJson(String.valueOf(array.getJSONObject(i)).replace("=\\",""),GoodsBean.class));
            }catch(Exception e){
                GoodsBean collectgoodsBean=new GoodsBean();
                GoodsBean goodsBean=new GoodsBean();
                goodsBean.setName(array.getJSONObject(i).getString(Constance.name));
                goodsBean.setCurrent_price(array.getJSONObject(i).getString(Constance.current_price));
                goodsBean.setDefault_photo(new Gson().fromJson(String.valueOf(array.getJSONObject(i).getJSONObject(Constance.default_photo)), Default_photo.class));
                goodsBean.setId(Integer.parseInt(array.getJSONObject(i).getString(Constance.goods_id)));
//                goodsBean.setGroup_buy(new Gson().fromJson(String.valueOf(array.getJSONObject(i).getJSONObject(Constance.group_buy)),GroupBuy.class));
                temp.add(collectgoodsBean);
            }
        }

        if (1 == page)
            goodsBeen=temp;
//            goodses = array;
        else if (null != goodsBeen&&goodsBeen.size()>0) {
//            for (int i = 0; i < array.length(); i++) {
//                goodses.add(array.getJSONObject(i));
//            }
            goodsBeen.addAll(temp);

            if (AppUtils.isEmpty(temp))
                MyToast.show(mView, "没有更多内容了");
        }
//        mProAdapter.notifyDataSetChanged();
        goodsAdapter.replaceAll(goodsBeen);

//        mProAdapter.notifyDataSetChanged();
//        mProAdapter.getCheck(false, true);
//        mProAdapter.getIsCheck(false, true);
    }

    private View.OnClickListener mRefeshBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onRefresh();
        }
    };

    public void onRefresh() {
        page = 1;
        sendCollectProduct(page, 20);
    }

    private void dismissRefesh() {
        mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        page = 1;
        sendCollectProduct(page, 20);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        sendCollectProduct(page++,20);
    }

    public void setCheckAll() {
        if(isCheck){
            isCheck=false;
            mProAdapter.getIsCheck(false, false);
        }else{
            isCheck=true;
            mProAdapter.getIsCheck(true, false);
        }
    }

    private boolean isLastDelete=false;

    /**
     * 删除收藏
     */
    public void senDeleteCollect() {
        mView.setShowDialog(true);
        mView.setShowDialog("正在删除收藏中!");
        mView.showLoading();
        isLastDelete=false;
        mProAdapter.getGoodCheck();
        for(int i=0;i<goodCheckList.length();i++){
            String id =goodCheckList.getJSONObject(i).getJSONObject(Constance.goods).getString(Constance.id);
            mNetWork.sendUnLikeCollect(id, this);
            if(i==goodCheckList.length()-1){
                isLastDelete=true;
            }
        }

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

        private void getCheck(boolean isCheck,boolean isStart){
            if(AppUtils.isEmpty(goodses)) return;
            if(isStart){
                for(int i=0;i<goodses.length();i++){
                    isCheckShowList.add(isCheck);
                }
            }else{
                for(int i=0;i<goodses.length();i++){
                    isCheckShowList.set(i, isCheck);
                }
            }
            notifyDataSetChanged();
        }

        private void getIsCheck(boolean isCheck,boolean isStart){
            if(isStart){
                for(int i=0;i<goodses.length();i++){
                    isCheckList.add(isCheck);
                }
            }else{
                for(int i=0;i<goodses.length();i++){
                    isCheckList.set(i, isCheck);
                }
            }
            notifyDataSetChanged();
        }



        private void getGoodCheck(){
            goodCheckList=new JSONArray();
            for(int i=0;i<isCheckList.size();i++){
                if(isCheckList.get(i)){
                    goodCheckList.add(goodses.get(i));
                }
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mView, R.layout.item_gv_temai, null);

                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                holder.priceTv = (TextView) convertView.findViewById(R.id.priceTv);
                holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            JSONObject goodObject = goodses.getJSONObject(position).getJSONObject(Constance.goods);
            holder.name_tv.setText("型号:" + goodObject.getString(Constance.name));
            String path = goodObject.getJSONObject(Constance.default_photo).getString(Constance.thumb);
            ImageLoader.getInstance().displayImage(path, holder.imageView);

            holder.checkbox.setVisibility(View.GONE);
            holder.checkbox.setChecked(isCheckList.get(position));
            JSONArray propertieArray= goodses.getJSONObject(position).getJSONArray(Constance.properties);
            int current=0;
            for(int i=0;i<propertieArray.length();i++){
                if(propertieArray.getJSONObject(i).getString(Constance.name).equals("规格")){
                    current=i;
                    break;
                }
            }
            if(!AppUtils.isEmpty(propertieArray) && propertieArray.length()>0){
                JSONArray attrsArray = propertieArray.getJSONObject(current).getJSONArray(Constance.attrs);
                int price = attrsArray.getJSONObject(current).getInt(Constance.attr_price);
                double currentPrice=price;
                holder.priceTv.setText("￥" +currentPrice);
            }else{
                holder.priceTv.setText("￥" +goodObject.getString(Constance.current_price));
            }

            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isCheckList.set(position,isChecked);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView name_tv;
            TextView priceTv;
            CheckBox checkbox;

        }
    }
}
