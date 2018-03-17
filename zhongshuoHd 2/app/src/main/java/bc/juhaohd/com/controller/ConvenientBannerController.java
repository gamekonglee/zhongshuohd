package bc.juhaohd.com.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.nostra13.universalimageloader.core.ImageLoader;

import bc.juhaohd.com.R;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.controller.product.ProductDetailHDController;
import bc.juhaohd.com.ui.activity.ConvenientBannerActivity;
import bc.juhaohd.com.ui.activity.programme.ImageDetailActivity;

/**
 * Created by bocang on 18-2-9.
 */

public class ConvenientBannerController extends BaseController {

    private final ConvenientBannerActivity mView;
    private final ConvenientBanner mConvenientBanner;

    public ConvenientBannerController(ConvenientBannerActivity convenientBannerActivity) {
        mView = convenientBannerActivity;
        mConvenientBanner = (ConvenientBanner) mView.findViewById(R.id.convenientBanner);
        mConvenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, mView.paths);
        mConvenientBanner.setPageIndicator(new int[]{R.drawable.dot_unfocuse, R.drawable.dot_focuse});
//        propertiesList = productObject.getJSONArray(Constance.properties);

    }

    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }

    class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            // 你可以通过layout文件来创建，也可以像我一样用代码创建z，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            imageView.setImageResource(R.drawable.bg_default);
            ImageLoader.getInstance().displayImage(data, imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mView, ImageDetailActivity.class);
                    intent.putExtra(Constance.photo, (String) mView.paths.get(position));
                    mView.startActivity(intent);
                }
            });
        }
    }
}
