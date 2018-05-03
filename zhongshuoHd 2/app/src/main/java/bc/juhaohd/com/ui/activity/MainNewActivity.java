package bc.juhaohd.com.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.MainNewController;
import bocang.utils.AppUtils;
import cn.jpush.android.api.JPushInterface;

public class MainNewActivity extends BaseActivity implements View.OnClickListener {

    public static boolean isForeground;
    private MainNewController mainNewController;
    private TextView tv_title;
    public boolean isAttrloadData;
    public boolean isTypeloadData;
    private int isTypeId = 0;
    public String filter_attr_name=" ";
    public String category="";
    public String filter_attr;
    private TextView new_tv;
    private TextView competitive_tv;
    private TextView hot_tv;
    public TextView et_search;
    private TextView tv_none_sort;
    public TextView tv_current_select;
    public  String filter_type;
    public ImageView top_iv;
    public String keyword="";
    private ImageView iv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init();
//        EventBus.getDefault().register(this);
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        if(intent.hasExtra(Constance.filter_attr_name)){
//            filter_type = intent.getStringExtra(Constance.filter_attr_name);
//        }
//        if(intent.hasExtra(Constance.keyword)){
//            keyword = intent.getStringExtra(Constance.keyword);
//        }
//        tv_title.setText(filter_attr_name);
//        mainNewController.selectProduct(1,""+20,null,null,null);
//        super.onNewIntent(intent);
//    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        //        JPushInterface.init(getApplicationContext());
        JPushInterface.setDebugMode(true);//如果时正式版就改成false
        JPushInterface.init(this);
    }

    @Override
    protected void InitDataView() {
    }

    @Override
    protected void initController() {
        mainNewController = new MainNewController(MainNewActivity.this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main_new);
        tv_title = (TextView) findViewById(R.id.tv_title);
        new_tv = (TextView) findViewById(R.id.new_tv);
        competitive_tv = (TextView) findViewById(R.id.competitive_tv);
        hot_tv = (TextView) findViewById(R.id.hot_tv);
        tv_current_select = (TextView) findViewById(R.id.tv_current_select);
        tv_none_sort = (TextView) findViewById(R.id.tv_none_sort);
        top_iv = (ImageView) findViewById(R.id.top_iv);
        et_search = (TextView) findViewById(R.id.et_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
//        et_search.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // TODO Auto-generated method stub
//                // 修改回车键功能
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    // 先隐藏键盘
//                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(MainNewActivity.this
//                                            .getCurrentFocus().getWindowToken(),
//                                    InputMethodManager.HIDE_NOT_ALWAYS);
//                    searchData(et_search.getText().toString());
//                    return true;
//                }
//
//                return false;
//            }
//        });
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainNewActivity.this,SearchActivity.class);
                intent.putExtra("hasMain",true);
                startActivityForResult(intent,300);
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData(et_search.getText().toString());
            }
        });
        new_tv.setOnClickListener(this);
        competitive_tv.setOnClickListener(this);
        hot_tv.setOnClickListener(this);
        tv_none_sort.setOnClickListener(this);
        filter_attr_name=" ";
        switch (HomeShowNewActivity.mFragmentState){
            case 0://默认
                filter_attr_name=" ";
                break;
            case 1://风格
                filter_attr_name="风格";
                break;
            case 2://类型
                filter_attr_name="类型";
                break;
            case 3://空间
                filter_attr_name="空间";
                break;

        }
        if(getIntent().hasExtra(Constance.filter_attr_name)){
            filter_type = getIntent().getStringExtra(Constance.filter_attr_name);
        }
        if(getIntent().hasExtra(Constance.keyword)){
            keyword = getIntent().getStringExtra(Constance.keyword);
        }
        tv_title.setText(filter_attr_name);

    }

    @Override
    protected void initData() {

    }
    @Override
    public void onStart() {
        super.onStart();
        isForeground=true;
        if (isAttrloadData) {
            isAttrloadData = false;
            getAttrData();

        }
        if (isTypeloadData) {
            isTypeloadData = false;
            
            selectTypeProduct(isTypeId);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground=false;
    }

    public void getAttrData() {
        if (AppUtils.isEmpty(mainNewController))
            return;

//        if (!AppUtils.isEmpty(filter_attr_name)) {
//            mainNewController.getDropDownMenuText();
//        }
        selectTypeProduct(0);

        mainNewController.page = 1;
//        setShowDialog(true);
//        setShowDialog("正在获取中..");
//        showLoading();
        mainNewController.selectProduct(mainNewController.page, "20", null, null, null);
    }


    public void searchData(String keyword) {
        this.keyword = keyword;
        mainNewController.page = 1;

//        setShowDialog(true);
//        setShowDialog("正在获取中..");
//        showLoading();
        mainNewController.selectProduct(mainNewController.page, "20", null, null, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mainNewController.onBackPressed();
    }

    public void selectTypeProduct(int type) {
        if (AppUtils.isEmpty(mainNewController))
            return;

        competitive_tv.setTextColor(Color.WHITE);
        new_tv.setTextColor(Color.WHITE);
        hot_tv.setTextColor(Color.WHITE);
        tv_none_sort.setTextColor(Color.WHITE);
        if (type == 0) {
            mainNewController.mSortKey = "";
            mainNewController.mSortValue = "";
            return;
        }

        filter_attr="";
//        filter_attr_name="类型,空间,风格";
//        if (!AppUtils.isEmpty(filter_attr_name)) {
//            mainNewController.getDropDownMenuText();
//        }

        switch (type) {
            case R.id.competitive_tv:
                competitive_tv.setTextColor(getResources().getColor(R.color.orange_theme));
                break;
            case R.id.new_tv:
                new_tv.setTextColor(getResources().getColor(R.color.orange_theme));
                break;
            case R.id.hot_tv:
                hot_tv.setTextColor(getResources().getColor(R.color.orange_theme));
                break;
            case R.id.tv_none_sort:
                tv_none_sort.setTextColor(getResources().getColor(R.color.orange_theme));
                break;
        }
        mainNewController.selectSortType(type);
    }
    //在主线程执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(Integer action) {
        if (action == Constance.CARTCOUNT) {
        }
    }

    @Override
    public void onClick(View v) {
        selectTypeProduct(v.getId());
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==300){
        mainNewController.onActivityResult(requestCode,resultCode,data);
        }
    }
}
