package bc.juhaohd.com.ui.activity;

import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;

import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.controller.ConvenientBannerController;

/**
 * Created by bocang on 18-2-9.
 */

public class ConvenientBannerActivity extends BaseActivity {
    public List paths;
    private ConvenientBannerController mController;

    @Override
    protected void InitDataView() {

    }

    @Override
    protected void initController() {
        mController = new ConvenientBannerController(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_banner);

    }

    @Override
    protected void initData() {
        paths=getIntent().getStringArrayListExtra("path");

    }

    @Override
    protected void onViewClick(View v) {

    }
}
