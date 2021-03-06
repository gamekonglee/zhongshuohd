package bc.juhaohd.com.ui.activity.user;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.user.UserLogController;

/**
 * @author: Jun
 * @date : 2017/1/19 17:10
 * @description :物流列表
 */
public class UserLogNewActivity extends BaseActivity {
    private UserLogController mController;
    private Button btn_save;
    public boolean isSelectLogistics=false;
    private Intent mIntent;
    private TextView tv_add;

    @Override
    protected void InitDataView() {
    }

    @Override
    protected void initController() {
        mController=new UserLogController(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_user_logistics_new);

        setColor(this, getResources().getColor(R.color.colorPrimary));
        btn_save = getViewAndClick(R.id.btn_save);
        tv_add = getViewAndClick(R.id.tv_add);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        isSelectLogistics=intent.getBooleanExtra(Constance.isSelectLogistice, false);
    }

    @Override
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
            case R.id.tv_add:
                mIntent=new Intent(this,UserLogAddNewActivity.class);
                this.startActivityForResult(mIntent, Constance.FROMLOG);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mController.ActivityResult(requestCode, resultCode, data);
    }
}
