package bc.juhaohd.com.ui.activity.programme;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.bean.Programme;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.programme.ProgrammeController;
import bc.juhaohd.com.controller.programme.ProgrammeNewController;
import bc.juhaohd.com.ui.fragment.CartFragment;
import bc.juhaohd.com.ui.fragment.SceneHDFragment;

/**
 * @author: Jun
 * @date : 2017/2/16 14:46
 * @description :购物车
 */
public class ScreenActivity extends BaseActivity {
//    private ProgrammeNewController mController;
    public List<Programme> mProgrammes;
    //    private ShoppingCartController mController;

    @Override
    protected void InitDataView() {

    }

    @Override
    protected void initController() {
//        mController = new ProgrammeNewController(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_screen_new);
        //必需继承FragmentActivity,嵌套fragment只需要这行代码

        SceneHDFragment cartFragment=new SceneHDFragment();
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(Constance.product, true);
//        cartFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container,cartFragment).commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
