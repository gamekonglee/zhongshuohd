package bc.juhaohd.com.ui.activity.buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.bean.CartGoods;
import bc.juhaohd.com.bean.ScGoods;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.buy.ConfirmOrderController;
import bc.juhaohd.com.ui.activity.EditValueActivity;
import bc.juhaohd.com.ui.activity.IssueApplication;
import bc.juhaohd.com.ui.view.ShowDialog;
import bc.juhaohd.com.utils.UIUtils;
import bocang.json.JSONArray;
import bocang.json.JSONObject;
import bocang.utils.LogUtils;

/**
 * @author: Jun
 * @date : 2017/2/24 16:51
 * @description :
 */
public class ConfirmOrderNewActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private ConfirmOrderController mController;
    public List<ScGoods> goodsList;
//    public RelativeLayout address_rl;
    private TextView money_tv, settle_tv;
    private RadioGroup group;
    private View logistic_type_rl;
    public   JSONObject mAddressObject;
    private Intent mIntent;
    private TextView totla_money_tv;

    @Override
    protected void InitDataView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IssueApplication.addActivity(this);
    }

    @Override
    protected void initController() {
        mController = new ConfirmOrderController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mController.sendAddressList();
    }
    DecimalFormat df   = new DecimalFormat("######0.00");
    @Override
    protected void initView() {
        setContentView(R.layout.activity_confirm_order_new);
//        address_rl = getViewAndClick(R.id.address_rl);
        settle_tv = getViewAndClick(R.id.settle_tv);
        money_tv = (TextView) findViewById(R.id.summoney_tv);
        totla_money_tv = (TextView) findViewById(R.id.totla_money_tv);
        money_tv.setText("￥" + df.format(money) + "");
        totla_money_tv.setText("￥" + df.format(money) + "");
        group = (RadioGroup)this.findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(this);
        logistic_type_rl = findViewById(R.id.logistic_type_rl);
        logistic_type_rl = getViewAndClick(R.id.logistic_type_rl);

    }

    public  double money = 0;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null) {
            bundle.setClassLoader(getClass().getClassLoader());
//            goodsList = bundle.getParcelableArrayList(Constance.goods);
            String goodsStr=bundle.getString(Constance.goods);
            goodsList=new ArrayList<>();
            try {
                org.json.JSONArray jsonArray=new org.json.JSONArray(goodsStr);
//                LogUtils.logE("JSONARRAY:",jsonArray.toString());
                for(int i=0;i<jsonArray.length();i++){
                    goodsList.add(new Gson().fromJson(jsonArray.get(i).toString(),ScGoods.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mAddressObject = (JSONObject) bundle.getSerializable(Constance.address);
            money = bundle.getDouble(Constance.money,0.0);
        }
    }

    @Override
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.address_rl:
                mController.selectAddress();
                break;
            case R.id.settle_tv:
                mController.settleOrder();
                break;
            case R.id.logistic_type_rl:
                mController.selectLogistic();
                break;
            case R.id.remark_ll:
                mIntent = new Intent(this, EditValueActivity.class);
                mIntent.putExtra(Constance.TITLE, "订单备注");
                mIntent.putExtra(Constance.CODE, 007);
                startActivityForResult(mIntent, 007);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mController.ActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mController.selectCheckType(group.getCheckedRadioButtonId());
    }

    public void goBack(View v){
        ShowDialog mDialog=new ShowDialog();
        mDialog.show(this, "提示", "是否退出当前的确定订单?", new ShowDialog.OnBottomClickListener() {
            @Override
            public void positive() {
                List<BaseActivity> baseActivities=IssueApplication.getBaseActivities();
                if(baseActivities!=null&&baseActivities.size()>0)baseActivities.remove(0);
                finish();
            }

            @Override
            public void negtive() {

            }
        });
    }
}
