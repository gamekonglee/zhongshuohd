package bc.juhaohd.com.controller.programme;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.lib.common.hxp.view.ListViewForScrollView;
import com.lib.common.hxp.view.PullToRefreshLayout;
import com.lib.common.hxp.view.PullableGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bc.juhao.com.ui.view.HorizontalListView;
import bc.juhaohd.com.R;
import bc.juhaohd.com.adapter.BaseAdapterHelper;
import bc.juhaohd.com.adapter.QuickAdapter;
import bc.juhaohd.com.bean.Programme;
import bc.juhaohd.com.bean.ScGoods;
import bc.juhaohd.com.bean.ScProduct;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.controller.BaseController;
import bc.juhaohd.com.controller.buy.CartController;
import bc.juhaohd.com.listener.INetworkCallBack;
import bc.juhaohd.com.ui.activity.programme.ImageDetailActivity;
import bc.juhaohd.com.ui.activity.programme.ScreenActivity;
import bc.juhaohd.com.ui.adapter.MatchItemAdapter;
import bc.juhaohd.com.ui.adapter.ProgrammeDropMenuAdapter;
import bc.juhaohd.com.ui.fragment.ProgrammeFragment;
import bc.juhaohd.com.utils.ShareUtil;
import bc.juhaohd.com.utils.UIUtils;
import bocang.json.JSONArray;
import bocang.json.JSONObject;
import bocang.utils.AppUtils;
import bocang.utils.LogUtils;
import bocang.utils.MyToast;

import static bc.juhaohd.com.utils.UIUtils.getResources;

/**
 * @author: Jun
 * @date : 2017/3/10 17:49
 * @description :
 */
public class ProgrammeController extends BaseController implements INetworkCallBack, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, OnFilterDoneListener {
    private ProgrammeFragment mView;
    private DropDownMenu dropDownMenu;
    private PullToRefreshLayout mPullToRefreshLayout;
    private ProAdapter mProAdapter;
    private PullableGridView order_sv;
    public int page = 1;
    private int per_page = 20;
    public JSONArray mSchemes;
    private boolean initFilterDropDownView;
    private ProgressBar pd;
    private String mStyle;
    private String mSpace;
    private int mDeleteIndex = 0;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private ProgressDialog pdl;
    private boolean isLastDelete;
    private List<ScGoods> goodsTotalList;
    private List<ScGoods> goods;
    private int count;
    private CheckBox checkAll;
    private CartAdapter goodsAdapter;


    public ProgrammeController(ProgrammeFragment v) {
        mView = v;
        initView();
        initViewData();
    }



    private void initViewData() {
        mView.showLoadingPage("", R.drawable.ic_loading);
        initFilterDropDownView = true;
        setropownMenuData();
        if (initFilterDropDownView)//重复setMenuAdapter会报错
            initFilterDropDownView(mView.mProgrammes);
    }


    private void setropownMenuData() {
        mView.mProgrammes = new ArrayList<>();
        String[] styleArrs = UIUtils.getStringArr(R.array.style);
        String[] spaceArrs = UIUtils.getStringArr(R.array.space);
        Programme programme = new Programme();
        programme.setAttr_name(UIUtils.getString(R.string.style_name));
        programme.setAttrVal(Arrays.asList(styleArrs));
        mView.mProgrammes.add(programme);
        Programme programme2 = new Programme();
        programme2.setAttr_name(UIUtils.getString(R.string.splace_name));
        programme2.setAttrVal(Arrays.asList(spaceArrs));
        mView.mProgrammes.add(programme2);
    }

    private void initView() {
        dropDownMenu = (DropDownMenu) mView.getView().findViewById(R.id.dropDownMenu);
        mPullToRefreshLayout = ((PullToRefreshLayout) mView.getView().findViewById(R.id.mFilterContentView));
        mPullToRefreshLayout.setOnRefreshListener(this);
        order_sv = (PullableGridView) mView.getView().findViewById(R.id.gridView);
        mProAdapter = new ProAdapter();
        order_sv.setAdapter(mProAdapter);
        order_sv.setOnItemClickListener(this);
        pd = (ProgressBar) mView.getView().findViewById(R.id.pd2);
        //        mNullView = mView.getActivity().findViewById(R.id.null_view);
        //        mNullNet = mView.getActivity().findViewById(R.id.null_net);
        //        mRefeshBtn = (Button) mNullNet.findViewById(R.id.refesh_btn);
        //        mNullNetTv = (TextView) mNullNet.findViewById(R.id.tv);
        //        mNullViewTv = (TextView) mNullView.findViewById(R.id.tv);
        //        go_btn = (Button) mNullView.findViewById(R.id.go_btn);
    }

    private List<Integer> itemPosList = new ArrayList<>();//有选中值的itemPos列表，长度为3

    private void initFilterDropDownView(List<Programme> programmes) {
        if (itemPosList.size() < programmes.size()) {
            itemPosList.add(0);
            itemPosList.add(0);
        }
        ProgrammeDropMenuAdapter dropMenuAdapter = new ProgrammeDropMenuAdapter(mView.getActivity(), programmes, itemPosList, this);
        dropDownMenu.setMenuAdapter(dropMenuAdapter);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }


    public void sendFangAnList() {

        mPullToRefreshLayout.isMove = false;
        mNetWork.sendFangAnList(page, per_page, mStyle, mSpace, this);
    }

    @Override
    public void onSuccessListener(String requestCode, JSONObject ans) {
        mView.hideLoading();
        mView.showContentView();
        //        go_btn.setVisibility(View.GONE);
        switch (requestCode) {
            case NetWorkConst.FANGANLIST:
//                LogUtils.logE("programme",ans.toString());
                if (null == mView || mView.getActivity()==null||mView.getActivity().isFinishing())
                    return;

                if (null != mPullToRefreshLayout) {
                    dismissRefesh();
                }
                JSONArray goodsList = ans.getJSONArray(Constance.fangan);
                if (AppUtils.isEmpty(goodsList)) {
                    if (page == 1) {
                        //                        mNullView.setVisibility(View.VISIBLE);
                        mPullToRefreshLayout.isMove = true;
                        //                        go_btn.setVisibility(View.VISIBLE);
                        //                        go_btn.setOnClickListener(new View.OnClickListener() {
                        //                            @Override
                        //                            public void onClick(View v) {
                        //                                IntentUtil.startActivity(mView.getActivity(), DiyActivity.class,false);
                        //                            }
                        //                        });
                    }
                    mSchemes = new JSONArray();
                    dismissRefesh();
                    pd.setVisibility(View.GONE);
                    return;
                }
                //                mNullView.setVisibility(View.GONE);
                //                mNullNet.setVisibility(View.GONE);
                getDataSuccess(goodsList);
                pd.setVisibility(View.GONE);
                break;
            case NetWorkConst.FANGANDELETE:
                mView.showContentView();
                mSchemes.delete(mDeleteIndex);
                mProAdapter.notifyDataSetChanged();
                //                sendFangAnList();
                break;

        }
    }

    @Override
    public void onFailureListener(String requestCode, JSONObject ans) {
        if (null == mView || mView.getActivity().isFinishing())
            return;
        if (AppUtils.isEmpty(ans)) {
            //            AppDialog.messageBox(UIUtils.getString(R.string.server_error));
            //            mNullNet.setVisibility(View.VISIBLE);
            mPullToRefreshLayout.isMove = true;
            //            mRefeshBtn.setOnClickListener(mRefeshBtnListener);
            return;
        }
        //        go_btn.setVisibility(View.GONE);
        this.page--;
        if (null != mPullToRefreshLayout) {
            dismissRefesh();
        }
    }

    private void dismissRefesh() {
        mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    private View.OnClickListener mRefeshBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onRefresh();
        }
    };

    public void onRefresh() {
        pd.setVisibility(View.VISIBLE);
        page = 1;
        sendFangAnList();
    }

    private void getDataSuccess(JSONArray array) {
        if (1 == page)
            mSchemes = array;
        else if (null != mSchemes) {
            for (int i = 0; i < array.length(); i++) {
                mSchemes.add(array.getJSONObject(i));
            }

            if (AppUtils.isEmpty(array))
                MyToast.show(mView.getActivity(), "没有更多内容了");
        }
        mProAdapter.notifyDataSetChanged();
    }

    public void ActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onFilterDone(int titlePos, int itemPos, String itemStr) {
        dropDownMenu.close();
        itemStr = mView.mProgrammes.get(titlePos).getAttr_name();
        dropDownMenu.setPositionIndicatorText(titlePos, itemStr);

        if (titlePos < itemPosList.size())
            itemPosList.remove(titlePos);
        itemPosList.add(titlePos, itemPos);
        if (titlePos == 0) {
            mStyle = mView.mProgrammes.get(titlePos).getAttrVal().get(itemPos);
        } else if (titlePos == 1) {
            mSpace = mView.mProgrammes.get(titlePos).getAttrVal().get(itemPos);
        }

        if (itemPos == 0) {
            mStyle = "";
            mSpace = "";
        }
        //        pd.setVisibility(View.VISIBLE);
        page = 1;
        pd.setVisibility(View.VISIBLE);
        sendFangAnList();

    }

    public void onBackPressed() {
        dropDownMenu.close();
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        pd.setVisibility(View.VISIBLE);
        page = 1;
        initFilterDropDownView = false;
        sendFangAnList();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        pd.setVisibility(View.VISIBLE);
        initFilterDropDownView = false;
        page = page + 1;

        sendFangAnList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //        String path= NetWorkConst.SCENE_HOST+
        //                goodses.getJSONObject(position).getJSONObject(Constance.scene).getString(Constance.original_img);
        //        mIntent=new Intent();
        //        mIntent.putExtra(Constance.SCENE, path);
        //        mView.setResult(Constance.FROMDIY02, mIntent);//告诉原来的Activity 将数据传递给它
        //        mView.finish();//一定要调用该方法 关闭新的AC 此时 老是AC才能获取到Itent里面的值
    }


    public void sendDeleteFangan(int id) {
        mView.setShowDialog(true);
        mView.setShowDialog("删除中...");
        mView.showLoading();
        mNetWork.sendDeleteFangan(id, this);
    }


    private class ProAdapter extends BaseAdapter {
        public ProAdapter() {
        }

        @Override
        public int getCount() {
            if (null == mSchemes)
                return 0;
            return mSchemes.length();
        }

        @Override
        public JSONObject getItem(int position) {
            if (null == mSchemes)
                return null;
            return mSchemes.getJSONObject(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mView.getActivity(), R.layout.item_match, null);

                holder = new ViewHolder();
                holder.close_iv = (ImageView) convertView.findViewById(R.id.close_iv);
                holder.share_iv = (ImageView) convertView.findViewById(R.id.share_iv);
                holder.match_iv = (ImageView) convertView.findViewById(R.id.match_iv);
                holder.match_name_tv = (TextView) convertView.findViewById(R.id.match_name_tv);
                holder.match_name02_tv = (TextView) convertView.findViewById(R.id.match_name02_tv);
                holder.horizon_listview = (HorizontalListView) convertView.findViewById(R.id.horizon_listview);
                holder.tv_add_to_sc=convertView.findViewById(R.id.tv_add_to_sc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final JSONObject jsonObject = mSchemes.getJSONObject(position);
            String style = jsonObject.getString(Constance.style);
            String space = jsonObject.getString(Constance.space);
            final String path = NetWorkConst.SCENE_HOST + jsonObject.getString(Constance.path);
            holder.match_name_tv.setText(URLDecoder.decode(jsonObject.getString(Constance.name)));
            holder.match_name02_tv.setText(style + "/" + space);
            ImageLoader.getInstance().displayImage(path
                    , holder.match_iv);
            JSONArray jsonArray = jsonObject.getJSONArray(Constance.goodsInfo);

            holder.close_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDeleteIndex = position;
                    sendDeleteFangan(jsonObject.getInt(Constance.id));
                }
            });

            //分享
            holder.share_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertView(null, null, "取消", null,
                            new String[]{"复制链接", "分享图片", "分享链接"},
                            mView.getContext(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            switch (position) {
                                case 0:
                                    if (!mView.isToken()) {
                                        ClipboardManager cm = (ClipboardManager) mView.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                        cm.setText(path);
                                    }
                                    break;
                                case 1:
                                    String title1 = "来自 " + URLDecoder.decode(jsonObject.getString(Constance.name)) + " 方案的分享";
                                    ShareUtil.showShare01(mView.getActivity(), title1, "1", path);
                                    break;
                                case 2:
                                    String title = "来自 " + URLDecoder.decode(jsonObject.getString(Constance.name)) + " 方案的分享";
                                    ShareUtil.showShare(mView.getActivity(), title, path, path);
                                    break;
                            }
                        }
                    }).show();


                }
            });

            MatchItemAdapter matchItemAdapter = new MatchItemAdapter(mView.getActivity(), jsonArray);
            holder.horizon_listview.setAdapter(matchItemAdapter);


            holder.match_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mView.getActivity(), ImageDetailActivity.class);
                    intent.putExtra(Constance.photo, path);
                    mView.getActivity().startActivity(intent);
                }
            });
            holder.tv_add_to_sc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsTotalList = new ArrayList<>();
                    JSONArray goodsinfo=jsonObject.getJSONArray(Constance.goodsInfo);
                    for(int i=0;i<goodsinfo.length();i++){
                        ScGoods scGoods=new ScGoods();
                        scGoods.setAmount(1);
                        scGoods.setPrice(goodsinfo.getJSONObject(i).getString(Constance.price));
                        scGoods.setId(goodsinfo.getJSONObject(i).getInt(Constance.id));
                        ScProduct scProduct=new ScProduct();
                        scProduct.setOriginal_img(goodsinfo.getJSONObject(i).getString(Constance.original_img));
                        scProduct.setName(goodsinfo.getJSONObject(i).getString(Constance.name));
                        scGoods.setProduct(scProduct);
                        goodsTotalList.add(scGoods);
                    }
                    Dialog dialog=UIUtils.showScDialog(mView.getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean atleast=false;
                            for (int i=0;i<isCheckList.size();i++){
                                if(isCheckList.get(i)){
                                    atleast=true;
                                    break;
                                }
                            }
                            if(!atleast){
                                Toast.makeText(mView.getActivity(), "请输入购买数量", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            goodsAdapter.getCartGoodsCheck();
                            for(int j=0;j<goods.size();j++){
//                            sendGoShoppingCart(goods.get(j).getId()+"",mView.mProperty, Integer.parseInt(et_sku_num.getText().toString()));
//                            mNetWork.sendShoppingCart(goods.get(j).getId()+"");
                            }
                        }
                    });
                    ListViewForScrollView mListView =  dialog.findViewById(R.id.cart_lv);
                    checkAll = (CheckBox) dialog.findViewById(R.id.checkAll);
                    goodsAdapter = new CartAdapter(mView.getActivity(), R.layout.item_lv_cart_order);
                    mListView.setAdapter(goodsAdapter);
                    goodsAdapter.replaceAll(goodsTotalList);
                    goodsAdapter.notifyDataSetChanged();
                    goodsAdapter.addIsCheckAll(false);
                    goodsAdapter.getTotalMoney();
                    checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            setCkeckAll(isChecked);
                        }
                    });
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView close_iv, match_iv, share_iv;
            HorizontalListView horizon_listview;
            TextView match_name_tv, match_name02_tv;
            TextView tv_add_to_sc;
        }
    }

    DecimalFormat df   = new DecimalFormat("######0.00");
    private ArrayList<Boolean> isCheckList = new ArrayList<>();
    public class CartAdapter extends QuickAdapter<ScGoods> {




        public CartAdapter(Context context, int layoutResId) {
            super(context, layoutResId);
            // 设置图片下载期间显示的图片
// 设置图片Uri为空或是错误的时候显示的图片
// 设置图片加载或解码过程中发生错误显示的图片
// .showImageOnFail(R.drawable.ic_error)
// 设置下载的图片是否缓存在内存中
// 设置下载的图片是否缓存在SD卡中
// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
// 是否考虑JPEG图像EXIF参数（旋转，翻转）
// 设置图片可以放大（要填满ImageView必须配置memoryCacheExtraOptions大于Imageview）
// .displayer(new FadeInBitmapDisplayer(100))//
// 图片加载好后渐入的动画时间
// 构建完成
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
                    .build();

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
            imageLoader.displayImage(NetWorkConst.SCENE_HOST+item.getProduct().getOriginal_img()
                    , imageView, options);
            helper.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pdl==null){
                        pdl = ProgressDialog.show(mView.getActivity(),"","正在删除");
                    }else {
                        if(!pdl.isShowing())pdl.show();
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
            final EditText numTv=helper.getView(R.id.et_num);
            numTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCountDialo(helper.getPosition(),item.getAmount());
                }
            });
            helper.setChecked(R.id.checkbox,isCheckList.get(helper.getPosition()));
            helper.setOnClickListener(R.id.rightTv, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pdl=ProgressDialog.show(mView.getActivity(),"","正在处理中...");
                    sendUpdateCart(helper.getPosition(),(item.getAmount()+1));
                }
            });
            helper.setOnClickListener(R.id.leftTv, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getAmount()==1){
                        MyToast.show(mView.getActivity(),"亲,已经到底啦!");
                        return;
                    }
                    pdl=ProgressDialog.show(mView.getActivity(),"","正在处理中...");
                    sendUpdateCart(helper.getPosition(),(item.getAmount()-1));
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
            goods = new ArrayList<>();
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
//                money_tv.setText("￥" +0 + "");
//                money_suply_tv.setText("￥" +0 + "");
//                tv_count.setText(""+0);
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
//            mMoney=isSumMoney;
//            money_tv.setText("￥" +df.format(isSumMoney) + "");
//            money_suply_tv.setText("￥" +df.format(isSumMoney) + "");
//            tv_count.setText(""+ count);
        }


    }

    private void deleteShoppingCart(String id) {

    }

    public void setCkeckAll(Boolean isCheck) {
        if (goodsAdapter == null || goodsAdapter.isEmpty()) {
            return;
        }
        goodsAdapter.setIsCheckAll(isCheck);
        goodsAdapter.getTotalMoney();
        goodsAdapter.notifyDataSetChanged();
    }
    private void showCountDialo(final int position, int count) {
        final Dialog dialog=new Dialog(mView.getActivity(),R.style.customDialog);
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
        TextView tv_cancel= (TextView) dialog.findViewById(R.id.tv_cancel);
        TextView tv_ensure= (TextView) dialog.findViewById(R.id.tv_ensure);
        et_num_dialog.setText(""+count);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_num_dialog.getText()))
                {MyToast.show(mView.getActivity(),"商品数量要大于0");
                    return;}
                int  count= Integer.parseInt(et_num_dialog.getText().toString().trim());
                if(count<1){
                    MyToast.show(mView.getActivity(),"商品数量要大于0");
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
                {MyToast.show(mView.getActivity(),"商品数量要大于0");
                    return;}
                int  count= Integer.parseInt(et_num_dialog.getText().toString().trim());
                if(count<1){
                    MyToast.show(mView.getActivity(),"商品数量要大于0");
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
                dialog.dismiss();
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_num_dialog.getText()))
                {MyToast.show(mView.getActivity(),"商品数量要大于0");
                    return;}
                int  count= Integer.parseInt(et_num_dialog.getText().toString().trim());
                if(count<1){
                    MyToast.show(mView.getActivity(),"商品数量要大于0");
                    return;
                }
//                updateCount(goodsTotalList.get(position).getId(),count,beforeCount);
//                goodsList.get(position).setCount(count);
                sendUpdateCart(position,count);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void sendUpdateCart(int position, int count) {
        goodsTotalList.get(position).setAmount(count);
        goodsAdapter.replaceAll(goodsTotalList);
        goodsAdapter.notifyDataSetChanged();
    }
}
