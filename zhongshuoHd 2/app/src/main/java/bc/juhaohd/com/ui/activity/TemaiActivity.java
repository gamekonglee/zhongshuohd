package bc.juhaohd.com.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.controller.TemaiController;
import bc.juhaohd.com.controller.user.CollectController;

/**
 * @author: Jun
 * @date : 2017/2/13 16:12
 * @description :
 */
public class TemaiActivity extends BaseActivity {
    private ImageView iv_edit;
    private TemaiController mController;
    private CheckBox checkAll;
    private Button delete_bt;

    @Override
    protected void InitDataView() {

    }

    @Override
    protected void initController() {
        mController = new TemaiController(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_temai_product_new);
        iv_edit = getViewAndClick(R.id.iv_edit);
        checkAll = getViewAndClick(R.id.checkAll);
        delete_bt = getViewAndClick(R.id.delete_bt);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_edit:
                mController.setEdit();
                break;
            case R.id.checkAll:
                mController.setCheckAll();
                break;
            case R.id.delete_bt:
                mController.senDeleteCollect();
                break;
        }
    }
}
