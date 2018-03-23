package bc.juhaohd.com.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.greendao.DaoMaster;
import com.example.anonymous.greendao.DaoSession;
import com.example.anonymous.greendao.GdAttrBeanDao;
import com.example.anonymous.greendao.GdAttrBeanListDao;
import com.example.anonymous.greendao.GdGoodsBeanDao;
import com.google.gson.Gson;
import com.lib.common.hxp.view.PullToRefreshLayout;
import com.lib.common.hxp.view.PullableGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.adapter.BaseAdapterHelper;
import bc.juhaohd.com.adapter.QuickAdapter;
import bc.juhaohd.com.bean.AttrBean;
import bc.juhaohd.com.bean.Attr_list;
import bc.juhaohd.com.bean.Default_photo;
import bc.juhaohd.com.bean.GdAttrBean;
import bc.juhaohd.com.bean.GdAttrBeanList;
import bc.juhaohd.com.bean.GdGoodsBean;
import bc.juhaohd.com.bean.GoodsBean;
import bc.juhaohd.com.bean.GoodsDetailZsBean;
import bc.juhaohd.com.bean.GroupBuy;
import bc.juhaohd.com.bean.ScAttrs;
import bc.juhaohd.com.bean.ScProperties;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.listener.INetworkCallBack;
import bc.juhaohd.com.net.ApiClient;
import bc.juhaohd.com.ui.activity.IssueApplication;
import bc.juhaohd.com.ui.activity.MainNewActivity;
import bc.juhaohd.com.ui.activity.product.ProductDetailHDNewActivity;
import bc.juhaohd.com.utils.ImageLoadProxy;
import bc.juhaohd.com.utils.UIUtils;
import bocang.json.JSONArray;
import bocang.json.JSONObject;
import bocang.utils.AppDialog;
import bocang.utils.AppUtils;
import bocang.utils.MyToast;

/**
 * @author: Jun
 * @date : 2017/3/17 10:24
 * @description :
 */
public class MainNewController extends BaseController implements INetworkCallBack, PullToRefreshLayout.OnRefreshListener, View.OnClickListener {
    private TextView unMessageReadTv;
    private MainNewActivity mView;
    private String mAppVersion;
    private TextView tv_select_choose;
    private ListView lv_type;
    private TextView tv_current_select;
    private TextView tv_none_sort;
    private RelativeLayout rl_search;
    private TextView et_search;
    private PullToRefreshLayout mFilterContentView;
    private PullableGridView gridView;
    private QuickAdapter<Attr_list> typeAdapter;
    private QuickAdapter<GoodsBean> goodsAdapter;
    private List<Attr_list> attrBeen;
    private List<GoodsBean> goodsBeen;
    public int page;
    private ProgressBar pd;
    public String mSortKey;
    public String mSortValue;
    private boolean isScrollToTop;
    private int index;
    private int currentPosition=0;
    private List<AttrBean> attrAllBean;
    private int currentTitlePosition;
    private ListView lv_filter;
    private QuickAdapter<AttrBean> attrAlladapter;
    private TextView tv_reset;
    private TextView tv_ensure;
    private LinearLayout ll_filter;
    private boolean[][] filterArray;
    private String current_three_filter;
    private ProgressDialog progressDialog;
    private boolean request;

    public MainNewController(MainNewActivity v) {
        mView = v;
        initView();
        initViewData();
    }


    private void initViewData() {
        page = 1;
//        boolean initFilterDropDownView =  true;

//        pd.setVisibility(View.VISIBLE);
        if (mView.isAttrloadData) {
            return;
        }
        if (mView.isTypeloadData) {
            return;
        }
        if(!TextUtils.isEmpty(mView.filter_type)){
            tv_current_select.setText(mView.filter_type);
        }
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mView, Constance.db_mydb, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GdAttrBeanDao attrBeanDao=daoSession.getGdAttrBeanDao();
        GdAttrBeanListDao beanListDao=daoSession.getGdAttrBeanListDao();
        GdGoodsBeanDao gdGoodsBeanDao=daoSession.getGdGoodsBeanDao();
        List<GdAttrBean> attrBeans=attrBeanDao.loadAll();
        List<GdAttrBeanList> gdAttrBeanListList=beanListDao.loadAll();

        List<GdGoodsBean> gdGoodsBeans;

        if(attrBeans==null||attrBeans.size()==0||gdAttrBeanListList==null||gdAttrBeanListList.size()==0){
            request=false;
                sendAttrList();
        }else {
            for(int i=0;i<gdAttrBeanListList.size();i++){
                for(int j=0;j<gdAttrBeanListList.size();j++){
                    if(gdAttrBeanListList.get(i).getAttr_bean_index()==gdAttrBeanListList.get(j).getAttr_bean_index()&&gdAttrBeanListList.get(i).getAttr_value().equals(gdAttrBeanListList.get(j).getAttr_value())&&i!=j){
                        beanListDao.deleteByKey(gdAttrBeanListList.get(j).getAlid());
                    }

                }
            }
            for(GdAttrBean temp :attrBeans){
                AttrBean attrBean=new AttrBean();
                attrBean.setFilter_attr_name(temp.getFilter_attr_name());
                attrBean.setIndex(temp.getIndex());
                List<Attr_list> attr_lists=new ArrayList<>();
                for(GdAttrBeanList abList: gdAttrBeanListList){
                    if(abList.getAttr_bean_index()==temp.getIndex()){
                        Attr_list attr_list=new Attr_list();
                        attr_list.setAttr_value(abList.getAttr_value());
                        attr_list.setId(abList.getId());
                        attr_lists.add(attr_list);
                    }
                }
                attrBean.setAttr_list(attr_lists);
                attrAllBean.add(attrBean);
            }

            if(mView.filter_attr_name.equals("类型")){
                index=0;
            }else if(mView.filter_attr_name.equals("空间")){
                index=1;
            }else if(mView.filter_attr_name.equals("风格")){
                index=3;
            }
            for(int i=0;i<attrBeen.size();i++){
                for(int j=0;j<attrBeen.size();j++){
                    if(attrBeen.get(i).getId()==attrBeen.get(j).getId()&&i!=j){
                        attrBeen.remove(j);
                        beanListDao.deleteByKey(gdAttrBeanListList.get(j).getAlid());
                    }
                }
            }
            for(int i=0;i<attrAllBean.size();i++){
                for(int j=0;j<attrAllBean.size();j++){
                    if(attrAllBean.get(i).getFilter_attr_name().equals(attrAllBean.get(j).getFilter_attr_name())&&i!=j){
                        attrAllBean.remove(j);
                        attrBeanDao.deleteByKey(attrBeans.get(j).getAid());
                    }
                }
            }
            boolean hasFilterType=false;
            for(int i=0;i<attrAllBean.size();i++){
                if(attrAllBean.get(i).getIndex()==index){
                    currentTitlePosition=i;
                    List<Attr_list> attr_list=attrAllBean.get(i).getAttr_list();
                    for(int j=0;j<attr_list.size();j++){

                        attrBeen.add(attr_list.get(j));
                        if(attrBeen.size()>0&&!TextUtils.isEmpty(mView.filter_type)&&currentTitlePosition==i
                                &&attrBeen.get(attrBeen.size()-1).getAttr_value().equals(mView.filter_type)){
                            currentPosition=j;
                            hasFilterType=true;
                        }
                    }
                }
            }

            if(hasFilterType){
                String filter = "";
                for (int x = 0; x < index + 1; x++) {
                    if (x == index) {
                        filter += attrAllBean.get(currentTitlePosition).getAttr_list().get(currentPosition).getId();
                    } else {
                        filter += "0.";
                    }
                }
                mView.filter_attr = filter;
//                pd.setVisibility(View.VISIBLE);
                page=1;
                isScrollToTop=true;

            filterArray = new boolean[attrAllBean.size()][];
            for(int i = 0; i< filterArray.length; i++){
                filterArray[i]=new boolean[attrAllBean.get(i).getAttr_list().size()];
            }
            goodsBeen=new ArrayList<>();
                if(mView.filter_attr!=null){
                    gdGoodsBeans=gdGoodsBeanDao.queryBuilder().where(GdGoodsBeanDao.Properties.Filter.eq(mView.filter_attr)).list();
                }else {
                    gdGoodsBeans=gdGoodsBeanDao.loadAll();
                }
            for(GdGoodsBean temp:gdGoodsBeans){
                GoodsBean goodsBean=new GoodsBean();
                goodsBean.setId(temp.getId());
                goodsBean.setName(temp.getName());
                Default_photo default_photo=new Default_photo();
                default_photo.setThumb(temp.getOrignalImg());
                goodsBean.setDefault_photo(default_photo);
                goodsBean.setCurrent_price(temp.getCurrentPrice());
                if(mView.filter_attr!=null) {
                    if (temp.getFilter() != null && temp.getFilter().equals(mView.filter_attr)) {
                        goodsBeen.add(goodsBean);
                    }
                }else {
                goodsBeen.add(goodsBean);
                }
            }
            for(int i=0;i<goodsBeen.size();i++){
                for(int j=0;j<goodsBeen.size();j++){
                    if(j==goodsBeen.size())j--;
                    if(i==goodsBeen.size())i--;
                    if(goodsBeen                                                                                                                                                                                  .get(j).getId()==goodsBeen.get(i).getId()&&j!=i){
                        goodsBeen.remove(j);
                        gdGoodsBeanDao.deleteByKey(gdGoodsBeans.get(j).getGid());
                    }
                }
            }
            if(goodsBeen==null||goodsBeen.size()==0){
                request=false;
             sendAttrList();
            }else {
            typeAdapter.replaceAll(attrBeen);
            attrAlladapter.replaceAll(attrAllBean);
            goodsAdapter.replaceAll(goodsBeen);
            goodsAdapter.notifyDataSetChanged();
            request=false;
            }
        }else {
                if(mView.keyword!=null){
                    request=false;
                    sendAttrList();
                }else {
                MyToast.show(mView, "数据查询不到");
                }

            }
        }
//        selectProduct(page, "20", null, null, null);
    }
    public void sendAttrList() {
        if(UIUtils.isValidContext(mView))progressDialog = ProgressDialog.show(mView,"","加载中");
        mNetWork.sendAttrList("yes", this);

    }
    private void initView() {
        tv_select_choose = (TextView) mView.findViewById(R.id.tv_select_choose);
        lv_type = (ListView) mView.findViewById(R.id.lv_type);
        tv_current_select = (TextView) mView.findViewById(R.id.tv_current_select);
        tv_none_sort = (TextView) mView.findViewById(R.id.tv_none_sort);
        rl_search = (RelativeLayout) mView.findViewById(R.id.rl_search);
        mView.et_search.setText(mView.keyword);
        mFilterContentView = (PullToRefreshLayout) mView.findViewById(R.id.mFilterContentView);
        mFilterContentView.setOnRefreshListener(this);
        gridView = (PullableGridView) mView.findViewById(R.id.gridView);
        pd = (ProgressBar) mView.findViewById(R.id.pd);
        ll_filter = (LinearLayout) mView.findViewById(R.id.ll_filter);
        View view1=mView.findViewById(R.id.view_diss);
        View view2=mView.findViewById(R.id.view_diss2);
        lv_filter = (ListView) mView.findViewById(R.id.lv_fitler);
        tv_reset = (TextView) mView.findViewById(R.id.tv_reset);
        tv_ensure = (TextView) mView.findViewById(R.id.tv_ensure);
        mView.top_iv.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        tv_ensure.setOnClickListener(this);
        view2.setOnClickListener(this);
        view1.setOnClickListener(this);
        tv_select_choose.setOnClickListener(this);
        attrAllBean = new ArrayList<>();
        attrBeen = new ArrayList<>();
        goodsBeen = new ArrayList<>();
        currentPosition=0;
        currentTitlePosition = 0;
//        unMessageReadTv = (TextView) mView.findViewById(R.id.unMessageReadTv);
        typeAdapter = new QuickAdapter<Attr_list>(mView, R.layout.item_attr){

            @Override
            protected void convert(BaseAdapterHelper helper, Attr_list item) {
            helper.setText(R.id.tv_attr,""+item.getAttr_value());
                TextView ll_bg=helper.getView(R.id.tv_attr);
            if(helper.getPosition()==currentPosition){
//                    ll_bg.setBackgroundColor(Color.TRANSPARENT);
                    ll_bg.setBackground(mView.getResources().getDrawable(R.drawable.bg_corner_orange_full));

                }else {
                ll_bg.setBackground(null);
//                ll_bg.setBackgroundColor(mView.getResources().getColor(R.color.bg_item_normal));
                }
            }
        };
        goodsAdapter = new QuickAdapter<GoodsBean>(mView, R.layout.item_gridview_goodes){
            @Override
            protected void convert(BaseAdapterHelper helper, GoodsBean item) {
                Object name=item.getName();
                if(null!=name){
                    helper.setText(R.id.tv_name,item.getName().toString());
                }

//                if(item.getProperties()!=null&&item.getProperties().size()>0&&item.getProperties().get(0)!=null&&item.getProperties().get(0).getScAttrs()!=null&&item.getProperties().get(0).getScAttrs().size()>0){
//                    helper.setText(R.id.tv_price," "+getLevelPrice(helper.getPosition(),0,0));
//                }else {
//                }
                helper.setText(R.id.tv_price," "+item.getCurrent_price());
                Default_photo default_photo=item.getDefault_photo();
//                String url=item.getOriginal_img();
                if(null!=default_photo){
                    String imageUrl=item.getDefault_photo().getThumb();
//                    Log.e("imageUrl",NetWorkConst.SCENE_HOST+imageUrl);
                    ImageLoader.getInstance().displayImage(imageUrl, (ImageView) helper.getView(R.id.iv_photo),IssueApplication.getImageloaderOption());
                }else {
//                    Log.e("image","ddefault==null");
                }
            }
        };
        attrAlladapter = new QuickAdapter<AttrBean>(mView, R.layout.item_attr_all){

            @Override
            protected void convert(final BaseAdapterHelper basehelper, AttrBean item) {
                basehelper.setText(R.id.tv_title,item.getFilter_attr_name());
                final GridView gridView=basehelper.getView(R.id.gv_item);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        for(int i=0;i<filterArray[basehelper.getPosition()].length;i++){
                            filterArray[basehelper.getPosition()][i]=false;
                        }
                        filterArray[basehelper.getPosition()][position]=true;
                        ((QuickAdapter)gridView.getAdapter()).notifyDataSetChanged();
                        editFilter(basehelper.getPosition(),position);

                    }
                });

                gridView.setAdapter(new QuickAdapter<Attr_list>(mView,R.layout.item_text) {
                    @Override
                    protected void convert(BaseAdapterHelper helper, Attr_list item) {
                        TextView textView=helper.getView(R.id.textview);
                        if(filterArray[basehelper.getPosition()][helper.getPosition()]){
                            textView.setSelected(true);
                            helper.setVisible(R.id.iv_delete,true);
                        }else {
                            textView.setSelected(false);
                            helper.setVisible(R.id.iv_delete,false);
                        }
                        helper.setText(R.id.textview,item.getAttr_value());
                    }
                });
                ((QuickAdapter)gridView.getAdapter()).replaceAll(item.getAttr_list());
                ViewGroup.LayoutParams layoutParams= gridView.getLayoutParams();
                double count=item.getAttr_list().size();
                if(count>0){
                    int row= (int) Math.ceil(count/3.0);
                    layoutParams.height=UIUtils.dip2PX(68*row)+UIUtils.dip2PX(30*(row-1));
                    gridView.setLayoutParams(layoutParams);
                }
            }
        };
        lv_type.setAdapter(typeAdapter);
        gridView.setAdapter(goodsAdapter);
        lv_filter.setAdapter(attrAlladapter);

        lv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                request=false;
                currentPosition = position;
                typeAdapter.notifyDataSetChanged();
                tv_current_select.setText(""+attrBeen.get(position).getAttr_value());
//                mView.category=attrBeen.get(position).getAttr_value();
                if(null==attrAllBean||attrAllBean.size()<=0){
                    sendAttrList();
                    return;
                }
                String filter = "";
                for (int i = 0; i < index + 1; i++) {
                    if (i == index) {
                        filter += attrAllBean.get(currentTitlePosition).getAttr_list().get(position).getId();
                    } else {
                        filter += "0.";
                    }
                }
                mView.filter_attr = filter;
                if (AppUtils.isEmpty(mView.filter_attr))
                    return;
//                pd.setVisibility(View.VISIBLE);
                page=1;
                isScrollToTop=true;
                selectProduct(page, "20", null, null, null);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(mView, ProductDetailHDNewActivity.class);
//        int productId = goodses.getJSONObject(position).getInt(Constance.id);
                if(goodsBeen==null||goodsBeen.size()<=0){
                    return;
                }
                int productId = goodsBeen.get(position).getId();
                mIntent.putExtra(Constance.product, productId);
                mView.startActivity(mIntent);
            }
        });
    }

    private double getLevelPrice(int x,int y,int z) {
        int levelid=0;
        bocang.json.JSONObject mUser= IssueApplication.mUserObject;
        if(mUser!=null&&mUser.length()>0){
            levelid=IssueApplication.mUserObject.getInt(Constance.level_id);
        }
        if(x>=goodsBeen.size()||y>=goodsBeen.get(x).getProperties().size())
        {
            return 0.0;
        }
        List<ScAttrs> attrsArray=goodsBeen.get(x).getProperties().get(y).getScAttrs();
        double price;
        if(levelid==104)
        {
            price= Double.parseDouble(attrsArray.get(z).getAttr_price_5());
        }else if(levelid==103){
            price= Double.parseDouble(attrsArray.get(z).getAttr_price_4());
        }else if(levelid==102){
            price= Double.parseDouble(attrsArray.get(z).getAttr_price_3());
        }else if(levelid==101){
            price= Double.parseDouble(attrsArray.get(z).getAttr_price_2());
        }else{
            price= Double.parseDouble(attrsArray.get(z).getAttr_price_1());
        }
        price= Double.parseDouble(attrsArray.get(z).getAttr_price_5());
        return price;
    }

    private void editFilter(int index, int itemPosition) {

        current_three_filter = "";
//        for (int i = 0; i < 3; i++) {
//            if (i == attrAllBean.get(index).getIndex()) {
//                filter += attrAllBean.get(index).getAttr_list().get(itemPosition).getId();
//            } else {
//                if(i==5){
//                    filter += "0";
//                }else {
//                    filter += "0.";
//                }
//            }
//        }
        for(int i=0;i<filterArray.length;i++){
            boolean hasSelect=false;
            for(int j=0;j<filterArray[i].length;j++){
                if(filterArray[i][j]){
                    hasSelect=true;
                    if(i==filterArray.length-1){
                        current_three_filter +=attrAllBean.get(i).getAttr_list().get(j).getId();
                    }else {
                        current_three_filter +=attrAllBean.get(i).getAttr_list().get(j).getId()+".";
                    }
                    break;
                }
            }
            if(!hasSelect){
                if(i==filterArray.length-1){
                    current_three_filter +="0";
                }else {
                    current_three_filter +="0.";
                }
            }
        }
//        Toast.makeText(mView, "filter:"+filter, Toast.LENGTH_SHORT).show();
//        for (int i = 0; i < mFilterList.length(); i++) {
//            if (i == mFilterList.length() - 1) {
//                filter += mAdapter.mAttrList.get(i).getId();
//            } else {
//                filter += mAdapter.mAttrList.get(i).getId() + ".";
//            }
//        }

        mView.filter_attr = current_three_filter;

    }

    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }
    /**
     * 产品查询
     *
     * @param page
     * @param per_page
     * @param brand
     * @param category
     * @param shop
     */
    public void selectProduct(int page, String per_page, String brand, String category, String shop) {
//        pd.setVisibility(View.VISIBLE);
        if(!request){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mView, Constance.db_mydb, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GdGoodsBeanDao gdGoodsBeanDao=daoSession.getGdGoodsBeanDao();
        List<GdGoodsBean> gdGoodsBeans = null;
        if(mView.filter_attr!=null){
            gdGoodsBeans=gdGoodsBeanDao.queryBuilder().where(GdGoodsBeanDao.Properties.Filter.eq(mView.filter_attr)).list();
        }else if (mSortKey != null && mSortValue != null) {
            gdGoodsBeans=gdGoodsBeanDao.queryBuilder().where(GdGoodsBeanDao.Properties.SortKey.eq(mSortKey),GdGoodsBeanDao.Properties.SortValue.eq(mSortValue)).list();
        }else {
            gdGoodsBeans=gdGoodsBeanDao.loadAll();
        }
        if(gdGoodsBeans==null||gdGoodsBeans.size()==0){
            pd.setVisibility(View.VISIBLE);
            mNetWork.sendGoodsList(page, per_page, brand, mView.category, mView.filter_attr, shop, mView.keyword, mSortKey, mSortValue, this);
        }else {
//            pd.setVisibility(View.VISIBLE);
            if (mView.filter_attr_name.equals("类型")) {
                index = 0;
            } else if (mView.filter_attr_name.equals("空间")) {
                index = 1;
            } else if (mView.filter_attr_name.equals("风格")) {
                index = 3;
            }
            String filter = "";
            for (int x = 0; x < index + 1; x++) {
                if (x == index) {
                    filter += attrAllBean.get(currentTitlePosition).getAttr_list().get(currentPosition).getId();
                } else {
                    filter += "0.";
                }
            }
            mView.filter_attr = filter;
//                pd.setVisibility(View.VISIBLE);
            page = 1;
            isScrollToTop = true;
            filterArray = new boolean[attrAllBean.size()][];
            for (int i = 0; i < filterArray.length; i++) {
                filterArray[i] = new boolean[attrAllBean.get(i).getAttr_list().size()];
            }
            goodsBeen = new ArrayList<>();

            for (GdGoodsBean temp : gdGoodsBeans) {

                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setId(temp.getId());
                goodsBean.setName(temp.getName());
                goodsBean.setOriginal_img(temp.getOrignalImg());
                goodsBean.setCurrent_price(temp.getCurrentPrice());
                Default_photo default_photo = new Default_photo();
                default_photo.setThumb(temp.getOrignalImg());
                goodsBean.setDefault_photo(default_photo);
                if (mView.filter_attr != null) {

                    if (temp.getFilter() != null && mView.filter_attr.equals(temp.getFilter())) {
//                        Log.e("temp_filter", temp.getFilter() + "");
                        goodsBeen.add(goodsBean);
                    }
                } else if (mSortKey != null && mSortValue != null) {
                    if (temp.getSortKey().equals(mSortKey) && temp.getSortValue().equals(mSortValue)) {
                        goodsBeen.add(goodsBean);
                    }
                } else {
                    goodsBeen.add(goodsBean);
                }
            }
            if (goodsBeen != null && goodsBeen.size() > 0) {
                if(mView.keyword.equals("")) {
                    for (int i = 0; i < goodsBeen.size(); i++) {
                        for (int j = 0; j < goodsBeen.size(); j++) {
                            if (j == goodsBeen.size()) j--;
                            if (i == goodsBeen.size()) i--;
                            if (goodsBeen.get(j).getId() == goodsBeen.get(i).getId() && j != i) {
                                goodsBeen.remove(j);
                                gdGoodsBeanDao.deleteByKey(gdGoodsBeans.get(j).getGid());
                            }
                        }
                    }
                    goodsAdapter.replaceAll(goodsBeen);
                    goodsAdapter.notifyDataSetChanged();
                }else {
                    mNetWork.sendGoodsList(page, per_page, brand, mView.category, mView.filter_attr, shop, mView.keyword, mSortKey, mSortValue, this);
                }
            } else {
                pd.setVisibility(View.VISIBLE);
                mNetWork.sendGoodsList(page, per_page, brand, mView.category, mView.filter_attr, shop, mView.keyword, mSortKey, mSortValue, this);
            }
        }
        }else {
                pd.setVisibility(View.VISIBLE);
                mNetWork.sendGoodsList(page, per_page, brand, mView.category, mView.filter_attr, shop, mView.keyword, mSortKey, mSortValue, this);
            }
    }

    /**
     * 产品查询by key
     *
     */
    public void selectProductByKey(String key) {
        pd.setVisibility(View.VISIBLE);
        mNetWork.sendGoodsList(page, ""+20, "", mView.category, mView.filter_attr, "",key, mSortKey, mSortValue, this);
    }


    @Override
    public void onSuccessListener(String requestCode, JSONObject ans) {
        switch (requestCode) {
            case NetWorkConst.PRODUCT:

                if(UIUtils.isValidContext(mView)&&progressDialog!=null&&progressDialog.isShowing())progressDialog.dismiss();
                pd.setVisibility(View.GONE);
                //                if (null == mView || mView.getActivity().isFinishing())
                //                    return;

                if (null != mFilterContentView) {
                    dismissRefesh();
                }
                JSONArray goodsList = ans.getJSONArray(Constance.goodsList);

                if (AppUtils.isEmpty(goodsList)) {
                    if (page == 1) {
                        mView.keyword="";
                        MyToast.show(mView, "数据查询不到");
//                        goodses = new JSONArray();
                        goodsBeen=new ArrayList<>();
                    } else {
                        MyToast.show(mView, "数据已经到底啦!");
                    }
                    //


                    dismissRefesh();
                    return;
                }
                getDataSuccess(goodsList);
                DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mView, Constance.db_mydb, null);
                DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
                DaoSession daoSession = daoMaster.newSession();
                GdAttrBeanDao attrBeanDao=daoSession.getGdAttrBeanDao();
                GdAttrBeanListDao beanListDao=daoSession.getGdAttrBeanListDao();
                GdGoodsBeanDao gdGoodsBeanDao=daoSession.getGdGoodsBeanDao();
                attrBeanDao.deleteAll();
                beanListDao.deleteAll();
                for(AttrBean attrBean:attrAllBean){
                    GdAttrBean gdAttrBean=new GdAttrBean();
                    gdAttrBean.setIndex(attrBean.getIndex());
                    gdAttrBean.setAttr_list_index(attrBean.getIndex());
                    gdAttrBean.setFilter_attr_name(attrBean.getFilter_attr_name());
                    attrBeanDao.insert(gdAttrBean);
                    List<Attr_list> attr_lists=attrBean.getAttr_list();
                    for( Attr_list list:attr_lists){
                        GdAttrBeanList beanList=new GdAttrBeanList();
                        beanList.setAttr_bean_index(attrBean.getIndex());
                        beanList.setAttr_value(list.getAttr_value());
                        beanList.setId(list.getId());
                        beanListDao.insert(beanList);
                    }

                }
//                if(page==1){
//                    gdGoodsBeanDao.deleteAll();
//                }
                for(GoodsBean bean:goodsBeen){
                GdGoodsBean gdGoodsBean=new GdGoodsBean();
                gdGoodsBean.setId(bean.getId());
                gdGoodsBean.setCurrentPrice(bean.getCurrent_price());
                gdGoodsBean.setName((String) bean.getName());
                gdGoodsBean.setOrignalImg(bean.getDefault_photo().getThumb());
                Log.e("insert_filter",mView.filter_attr+"");
                gdGoodsBean.setFilter(mView.filter_attr);
                gdGoodsBean.setSortKey(mSortKey);
                gdGoodsBean.setSortValue(mSortValue);
                gdGoodsBeanDao.insert(gdGoodsBean);
                }

//                sendAttrList();

                break;
            case NetWorkConst.ATTRLIST:

                JSONArray sceneAllAttrs = ans.getJSONArray(Constance.goods_attr_list);
                if(mView.filter_attr_name.equals("类型")){
                    index=0;
                }else if(mView.filter_attr_name.equals("空间")){
                    index=1;
                }else if(mView.filter_attr_name.equals("风格")){
                    index=3;
                }
                attrAllBean=new ArrayList<>();
                attrBeen=new ArrayList<>();
                boolean hasFilterType=false;
                for(int i=0;i<sceneAllAttrs.length();i++){
                    if(sceneAllAttrs.getJSONObject(i).getInt(Constance.index)==index){
                        currentTitlePosition=i;
                        JSONArray jsonArray=sceneAllAttrs.getJSONObject(i).getJSONArray(Constance.attr_list);
                        for(int j=0;j<jsonArray.length();j++){

                            attrBeen.add(new Gson().fromJson(String.valueOf(jsonArray.getJSONObject(j)),Attr_list.class));
                            if(!TextUtils.isEmpty(mView.filter_type)&&currentTitlePosition==i
                                    &&attrBeen.get(attrBeen.size()-1).getAttr_value().contains(mView.filter_type)){
                                    currentPosition=j;
                                    hasFilterType=true;
                            }
                        }
                    }
                    attrAllBean.add(new Gson().fromJson(String.valueOf(sceneAllAttrs.getJSONObject(i)),AttrBean.class));
                }

                if(hasFilterType){
                    String filter = "";
                    for (int x = 0; x < index + 1; x++) {
                        if (x == index) {
                            filter += attrAllBean.get(currentTitlePosition).getAttr_list().get(currentPosition).getId();
                        } else {
                            filter += "0.";
                        }
                    }
                    mView.filter_attr = filter;
//                    pd.setVisibility(View.VISIBLE);
                    page=1;
                    isScrollToTop=true;
                }
                filterArray = new boolean[attrAllBean.size()][];
                for(int i = 0; i< filterArray.length; i++){
                    filterArray[i]=new boolean[attrAllBean.get(i).getAttr_list().size()];
                }

                selectProduct(page, "20", null, null, null);


                typeAdapter.replaceAll(attrBeen);
                attrAlladapter.replaceAll(attrAllBean);
                break;
        }
    }

    private void getDataSuccess(JSONArray array) {
        List<GoodsBean> temp=new ArrayList<>();
//        LogUtils.logE("temp:",array.toString());

        for (int i = 0; i < array.length(); i++) {
            try {

                temp.add(new Gson().fromJson(String.valueOf(array.getJSONObject(i)).replace("=\\",""),GoodsBean.class));
            }catch(Exception e){
                GoodsBean goodsBean=new GoodsBean();
                goodsBean.setName(array.getJSONObject(i).getString(Constance.name));
                goodsBean.setOriginal_img(array.getJSONObject(i).getString(Constance.original_img));
                goodsBean.setCurrent_price(array.getJSONObject(i).getString(Constance.current_price));
                goodsBean.setDefault_photo(new Gson().fromJson(String.valueOf(array.getJSONObject(i).getJSONObject(Constance.default_photo)), Default_photo.class));
                goodsBean.setId(Integer.parseInt(array.getJSONObject(i).getString(Constance.id)));
                goodsBean.setGroup_buy(new Gson().fromJson(String.valueOf(array.getJSONObject(i).getJSONObject(Constance.group_buy)),GroupBuy.class));
                JSONArray jsonArray=array.getJSONObject(i).getJSONArray(Constance.properties);
                List<ScProperties> temps=new ArrayList<>();
                for(int x=0;x<jsonArray.length();x++){
//                    ScProperties scProperties=(new Gson().fromJson(String.valueOf(array.getJSONObject(i).getJSONObject(Constance.properties)),ScProperties.class));
                    temps.add(new Gson().fromJson(String.valueOf(jsonArray.getJSONObject(x)),ScProperties.class));
                }
                goodsBean.setProperties(temps);
                temp.add(goodsBean);
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
        for(int i=0;i<goodsBeen.size();i++){
            for(int j=0;j<goodsBeen.size();j++){
                if(j==goodsBeen.size())j--;
                if(i==goodsBeen.size())i--;
                if(goodsBeen.get(j).getId()==goodsBeen.get(i).getId()&&j!=i){
                    goodsBeen.remove(j);
                }
            }
        }
        goodsAdapter.replaceAll(goodsBeen);
        if(isScrollToTop)
        {
            gridView.setSelection(0);
            gridView.smoothScrollToPositionFromTop(0,0);
            isScrollToTop=false;
        }
    }
    @Override
    public void onFailureListener(String requestCode, JSONObject ans) {
        if(UIUtils.isValidContext(mView)&&progressDialog!=null&&progressDialog.isShowing())progressDialog.dismiss();
        pd.setVisibility(View.GONE);
        if (null == mView || mView.isFinishing())
            return;
        if (AppUtils.isEmpty(ans)) {
            AppDialog.messageBox(UIUtils.getString(R.string.server_error));
            return;
        }
        this.page--;
        if (null != mFilterContentView) {
            dismissRefesh();
        }
    }
    private void dismissRefesh() {
        mFilterContentView.refreshFinish(PullToRefreshLayout.SUCCEED);
        mFilterContentView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }
    public void selectSortType(int type) {

        switch (type) {
            case R.id.competitive_tv:
                mSortKey = "3";//精品
                mSortValue = "1";//排序
                break;
            case R.id.new_tv:
                mSortKey = "5";//新品
                mSortValue = "1";//排序
                break;
            case R.id.hot_tv:
                mSortKey = "4";//热销
                mSortValue = "1";//排序
                break;
            case R.id.tv_none_sort:
                mSortKey="";//综合
                mSortValue="";//综合
                break;
        }
        page = 1;
        selectProduct(1, "20", null, null, null);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        page = 1;
        request = true;
//        initFilterDropDownView = false;
        selectProduct(page, "20", null, null, null);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//        initFilterDropDownView = false;
        if(goodsBeen==null||goodsBeen.size()==0){
            page=1;
        }else {
        int pageRow;
        if(goodsBeen.size()%20==0){
            pageRow=goodsBeen.size()/20;
        }else {
            pageRow=goodsBeen.size()/20+1;
        }
        page = pageRow + 1;
        }
        request=true;
        selectProduct(page, "20", null, null, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_iv:
                gridView.smoothScrollToPositionFromTop(0,0);
                if(goodsAdapter!=null)goodsAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_select_choose:
                if(attrAllBean==null||attrAllBean.size()<=0){
                    Toast.makeText(mView, "加载中，请稍等", Toast.LENGTH_SHORT).show();
                    return;
                }
                ll_filter.setVisibility(View.VISIBLE);
                tv_select_choose.setSelected(true);
                break;
            case R.id.view_diss:
            case R.id.view_diss2:
                ll_filter.setVisibility(View.GONE);
                tv_select_choose.setSelected(false);
                break;
            case R.id.tv_reset:
               for(int i=0;i<filterArray.length;i++){
                   for(int j=0;j<filterArray[i].length;j++){
                       filterArray[i][j]=false;
                   }
               }
                if(attrAlladapter!=null){
                    attrAlladapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_ensure:
                ll_filter.setVisibility(View.GONE);
                tv_select_choose.setSelected(false);
                page=1;
                selectProductByFilter(page,"20",null,null,null);
                break;
        }
    }

    private void selectProductByFilter(int page, String s, Object o, Object o1, Object o2) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mView, Constance.db_mydb, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GdGoodsBeanDao gdGoodsBeanDao=daoSession.getGdGoodsBeanDao();
        List<GdGoodsBean> gdGoodsBeans=gdGoodsBeanDao.loadAll();
        if(gdGoodsBeans==null||gdGoodsBeans.size()==0){
            pd.setVisibility(View.VISIBLE);
            mNetWork.sendGoodsList(page, "20", null, mView.category, mView.filter_attr, null, mView.keyword, mSortKey, mSortValue, this);
        }else {
//            pd.setVisibility(View.VISIBLE);
            page=1;
            isScrollToTop=true;
//            filterArray = new boolean[attrAllBean.size()][];
//            for(int i = 0; i< filterArray.length; i++){
//                filterArray[i]=new boolean[attrAllBean.get(i).getAttr_list().size()];
//            }
            goodsBeen=new ArrayList<>();

            for(GdGoodsBean temp:gdGoodsBeans){

                GoodsBean goodsBean=new GoodsBean();
                goodsBean.setId(temp.getId());
                goodsBean.setName(temp.getName());
                goodsBean.setOriginal_img(temp.getOrignalImg());
                goodsBean.setCurrent_price(temp.getCurrentPrice());
                Default_photo default_phot=new Default_photo();
                default_phot.setThumb(temp.getOrignalImg());
                goodsBean.setDefault_photo(default_phot);
                if(mView.filter_attr!=null){
                    if (temp.getFilter()!=null&&mView.filter_attr.equals(temp.getFilter())) {
                        goodsBeen.add(goodsBean);
                    }
                }else {
                    goodsBeen.add(goodsBean);
                }
            }
            if(goodsBeen!=null&&goodsBeen.size()>0){
                for(int i=0;i<goodsBeen.size();i++){
                    for(int j=0;j<goodsBeen.size();j++){
                        if(j==goodsBeen.size())j--;
                        if(i==goodsBeen.size())i--;
                        if(goodsBeen.get(j).getId()==goodsBeen.get(i).getId()&&j!=i){
                            goodsBeen.remove(j);
                            gdGoodsBeanDao.deleteByKey(gdGoodsBeans.get(j).getGid());
                        }
                    }
                }
                goodsAdapter.replaceAll(goodsBeen);
                goodsAdapter.notifyDataSetChanged();
            }else {
                pd.setVisibility(View.VISIBLE);
                mNetWork.sendGoodsList(page, "20", null, mView.category, mView.filter_attr, null, mView.keyword, mSortKey, mSortValue, this);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            String key=data.getStringExtra("key");
            if(key!=null){
                mView.keyword=key;
                selectProductByKey(key);
            }
    }

}
