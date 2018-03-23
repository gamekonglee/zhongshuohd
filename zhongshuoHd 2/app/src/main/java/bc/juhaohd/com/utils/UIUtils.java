package bc.juhaohd.com.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;

import bc.juhaohd.com.R;
import bc.juhaohd.com.ui.activity.IssueApplication;

import static bc.juhaohd.com.utils.ImageUtil.Bitmap2InputStream;
import static bc.juhaohd.com.utils.ImageUtil.drawable2Bitmap;

/**
 * @author Jun
 * @time 2016/8/19  10:37
 * @desc ${TODD}
 */
public class UIUtils {

    /**
     * 得到上下文
     * @return
     */
    public static Context getContext(){
        return IssueApplication.getcontext();
    }


//    public static String getDeviceId(){
//        return ((TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE))
//                .getDeviceId();
//    }
    DecimalFormat    df   = new DecimalFormat("######0.00");

    /**
     *mac
     * @param context
     * @return String
     */
    public static String getLocalMac(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac=info.getMacAddress();
        if(mac.equals("02:00:00:00:00:00")){
            return getInterfaceLocalmac();
        }else {
            return mac;
        }
    }



    public static String  getInterfaceLocalmac(){
        String mac="";
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iF = interfaces.nextElement();
                byte[] addr = iF.getHardwareAddress();
                if (addr == null || addr.length == 0) {
                    continue;
                }
                StringBuilder buf = new StringBuilder();
                for (byte b : addr) {
                    buf.append(String.format("%02X:", b));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                mac = buf.toString();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return mac;
    }


    /**
     * 得到Resources对象
     * @return
     */
    public static Resources getResources(){
        return getContext().getResources();
    }

    /**
     * 得到包名
     * @return
     */
    public static String getpackageName(){
        return  getContext().getPackageName();
    }

    /**
     * 得到配置的String信息
     * @param resId
     * @return
     */
    public static String getString(int resId){
        return getResources().getString(resId);
    }

    /**
     * 得到配置的String信息
     * @param resId
     * @return
     */
    public static String getString(int resId,Object ...formatAgs){
        return getResources().getString(resId,formatAgs);
    }

    /**
     * 得到配置String数组
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId){
        return getResources().getStringArray(resId);
    }
    public static int dip2PX(int dip) {
        //拿到设备密度
        float density=getResources().getDisplayMetrics().density;
        int px= (int) (dip*density+.5f);
        return px;
    }

    public static void initListViewHeight(ListView listView) {
        if(listView==null){
            return;
        }
        Adapter adapter=listView.getAdapter();
        if(adapter==null){
            return;
        }
        int count=adapter.getCount();
        int total=0;
        for(int i=0;i<count;i++){
            View view=adapter.getView(i,null,listView);
            view.measure(0,0);
            total+=view.getMeasuredHeight();
        }
        System.out.println("total:"+total);
        ViewGroup.LayoutParams layoutParams=listView.getLayoutParams();
        layoutParams.height=total;
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }
    public static void showSingleWordDialogOrange(final Context activity, String tittle, final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(activity, R.style.customDialog);
        dialog.setContentView(R.layout.dialog_layout_orange);
        TextView tv_num= (TextView) dialog.findViewById(R.id.tv_num);
        tv_num.setText(tittle);

        TextView btn = (TextView) dialog.findViewById(R.id.tv_ensure);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                dialog.dismiss();
            }
        });
        TextView cancel= (TextView) dialog.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
           /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }
    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
    public static Dialog showBottomInDialog(Activity activity, int layout_res, int height){
        Dialog dialog = new Dialog(activity,R.style.customDialog);
        dialog.setContentView(layout_res);
        dialog.setCanceledOnTouchOutside(true);
        Window win = dialog.getWindow();
        win.setGravity(Gravity.BOTTOM|Gravity.LEFT );
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = UIUtils.dip2PX((int) getResources().getDimension(R.dimen.xx790));
        lp.height = height;
        lp.x=0;
        win.setWindowAnimations(R.style.dialogButtomInStyle);
        win.setAttributes(lp);
        dialog.show();
        return  dialog;
    }

    public static InputStream Drawable2InputStream(Drawable drawable) {

        // Drawable转换成InputStream
            Bitmap bitmap = drawable2Bitmap(drawable);
            return Bitmap2InputStream(bitmap);

    }
    // 将byte[]转换成InputStream
    public InputStream Byte2InputStream(byte[] b) {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        return bais;
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
// 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
    public static Bitmap drawableToBitmap(int width,int height,Drawable drawable) {

        int w = width;
        int h =height;
//        System.out.println("Drawable转Bitmap");
        Bitmap.Config config = Bitmap.Config.RGB_565;

        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }
    public static Dialog showScDialog(Context mView, final View.OnClickListener clickListener) {
        final Dialog dialog = new Dialog(mView, R.style.customDialog);
        dialog.setContentView(R.layout.dialog_layout_add_to_choose);
        TextView btn = (TextView) dialog.findViewById(R.id.tv_ensure);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                        MyToast.show(mView.getContext(),orderId);
                clickListener.onClick(view);
                dialog.dismiss();
            }
        });
        TextView cancel= (TextView) dialog.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
           /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        return dialog;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isValidContext (Context c) {

        Activity a = (Activity) c;

        if (a.isDestroyed() || a.isFinishing()) {
            Log.i("YXH", "Activity is invalid." + " isDestoryed-->" + a.isDestroyed() + " isFinishing-->" + a.isFinishing());
            return false;
        } else {
            return true;
        }
    }

    }
