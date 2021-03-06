package bc.juhaohd.com.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseFragment;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.product.DetailGoodsController;

/**
 * @author: Jun
 * @date : 2017/2/14 15:56
 * @description :
 */
public class DetailGoodsFragmemt extends BaseFragment {
    private DetailGoodsController mController;
    public String productId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fm_detail_goods, null);
    }

    @Override
    protected void initController() {
        mController=new DetailGoodsController(this);
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        productId= (String) getArguments().get(Constance.product);
    }

    @Override
    public void onStart() {
        super.onStart();
        mController.sendProductDetail();
    }
}
