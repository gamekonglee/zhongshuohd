package bc.juhaohd.com.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.ui.view.PullUpToLoadMore;

/**
 * @author: Jun
 * @date : 2017/3/24 11:37
 * @description :
 */
public class Testactivity extends BaseActivity {

    private byte[] byte64;
    private String imageBase64;
    private SharedPreferences sPreferences;
    private ImageView iv;
    private ByteArrayInputStream bais;

    @Override
    protected void InitDataView() {

    }

    @Override
    protected void initController() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.fm_product_introduce);
        iv = findViewById(R.id.iv);
        sPreferences = getSharedPreferences("share", Context.MODE_PRIVATE);
        imageBase64 = sPreferences.getString("image", "");
        byte64 = Base64.decode(imageBase64, 0);
        bais = new ByteArrayInputStream(byte64);
        Bitmap bitmap = BitmapFactory.decodeStream(bais);
        iv.setImageBitmap(bitmap);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBase64 = sPreferences.getString("image2", "");
                byte64 = Base64.decode(imageBase64, 0);
                ByteArrayInputStream bais2 = new ByteArrayInputStream(byte64);
                Bitmap bitmap2 = BitmapFactory.decodeStream(bais2);
                iv.setImageBitmap(bitmap2);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
