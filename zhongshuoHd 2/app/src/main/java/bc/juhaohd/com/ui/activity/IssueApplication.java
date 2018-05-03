package bc.juhaohd.com.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.pgyersdk.crash.PgyCrashManager;
import com.sevenonechat.sdk.sdkCustomUi.UiCustomOptions;
import com.sevenonechat.sdk.sdkinfo.SdkRunningClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.common.BaseActivity;
import bc.juhaohd.com.crash.CrashHandler;
import bc.juhaohd.com.utils.ImageLoadProxy;
import bocang.json.JSONArray;
import bocang.json.JSONObject;
import bocang.view.BaseApplication;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

/**
 * @author Jun
 * @time 2017/1/6  16:06
 * @desc 全局
 */
public class IssueApplication extends BaseApplication {
    protected static Context mContext = null;
    public static List<BaseActivity> baseActivities;
    public static boolean noAd=false;
    private static DisplayImageOptions options;
    public static JSONArray mCount;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext= getApplicationContext();
        super.mInstance = this;
//        Thread.currentThread().setUncaughtExceptionHandler(new MyExceptionHander());
        initImageLoader();
        ImageLoadProxy.initImageLoader(mContext);
        PgyCrashManager.register(this);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        baseActivities = new ArrayList<>();
        noAd=false;
//        CrashHandler.getInstance().init(this,this);
        JAnalyticsInterface.init(mContext);
//        JAnalyticsInterface.initCrashHandler(mContext);
//        try{SdkRunningClient.getInstance().initAndLoginSdk(getApplicationContext(),"196417",
//                "168e5ed4-d961-4230-a72c-696343615e17",initUiCustomOptions());
//        initOption();}catch (Exception e){
//
//        }

    }

    private void initOption() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.bg_default) // resource or
                // drawable
                .showImageForEmptyUri(R.drawable.bg_default) // resource or
                // drawable
                .showImageOnFail(R.drawable.bg_default) // resource or
                // drawable
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示,推荐这种方式,省内存
                .resetViewBeforeLoading(true) // default
                .delayBeforeLoading(1000).cacheInMemory(true) // default
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // default
//                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .handler(new Handler()) // default
                .build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
////                .memoryCacheExtraOptions(480,800)//这里是缓存图片允许的最大尺寸
//                .threadPoolSize(3)//线程池内加载数量
////                .threadPriority(Thread.NORM_PRIORITY - 2)//降低线程优先级,保证UI主线程不受太大影响
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new WeakMemoryCache()) // 弱缓存，缓存bitmap的总大小没有限制，唯一不足的地方就是不稳定，缓存的图片容易被回收掉
////      .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))//建议内存设在5-10M,可以有比较好的表现
//                // You can pass your own memory cache
//                // implementation/你可以通过自己的内存缓存实现
//                .memoryCacheSize(2 * 1024 * 1024)
//                .discCacheSize(50 * 1024 * 1024)
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5 加密
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .discCacheFileCount(100)// 缓存的文件数量</span>
//                .discCache(new UnlimitedDiskCache(new File(Environment
//                .getExternalStorageDirectory()
//                + "/myApp/imgCache")))
//                // 自定义缓存路径
//                .defaultDisplayImageOptions(getImageloaderOption())//图片加载默认配置方法自定义
//                .imageDownloader(
//                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
//                .writeDebugLogs() // Remove for release app
//                .build();// 开始构建
//        ImageLoader.getInstance().init(config);
    }

    private UiCustomOptions initUiCustomOptions() {
        UiCustomOptions options = new UiCustomOptions();
        options.msgTitle_backgroudColor = R.color.green;
        return options;
    }
    public static void addActivity(BaseActivity activity){
        baseActivities.add(activity);
    }
    public  static BaseActivity getBaseActivity(int p){
          if(baseActivities!=null&&baseActivities.size()>p)
              return  baseActivities.get(p);
        else return null;
    }
    public static List<BaseActivity> getBaseActivities(){
        return baseActivities;
    }
    /**
     * 获得全局上下文
     * @return
     */
    public static Context getcontext() {
        return mContext;
    }

    //初始化网络图片缓存库
    private void initImageLoader(){
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
    public static DisplayImageOptions getImageloaderOption(){
        // resource or
// drawable
// resource or
// drawable
// resource or
// drawable
// default
// default
// default
// default
// default
// default
// default

        return options;
    }
    private class MyExceptionHander implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // Logger.i("MobileSafeApplication", "发生了异常,但是被哥捕获了..");
            //            LogUtils.d("MobileSafeApplication","发生了异常,但是被哥捕获了..");
            //并不能把异常消灭掉,只是在应用程序关掉前,来一个留遗嘱的事件
            //获取手机硬件信息
            try {
                Field[] fields = Build.class.getDeclaredFields();
                StringBuffer sb = new StringBuffer();
                for(Field field:fields){
                    String value = field.get(null).toString();
                    String name  = field.getName();
                    sb.append(name);
                    sb.append(":");
                    sb.append(value);
                    sb.append("\n");
                }
                File file=new File(getFilesDir(),"error.log");
                FileOutputStream out = new FileOutputStream(file);
                StringWriter wr = new StringWriter();
                PrintWriter err = new PrintWriter(wr);
                //获取错误信息
                ex.printStackTrace(err);
                String errorlog = wr.toString();
                sb.append(errorlog);
                out.write(sb.toString().getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //杀死页面进程
            restartApp();
        }
    }

    public static void restartApp(){
        Intent intent = new Intent(mContext,HomeShowNewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        restartApp();
    }

    /**
     * 返回桌面
     */
    public void toDesktop(){
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    public  static String mCId="";

    public static JSONObject mUserObject;

    public  static String imagePath="";

    public  static File cameraPath;

    public static  boolean isClassify=false;

    public static int mCartCount=0;

    public static int mLightIndex = 0;//点出来的灯的序号

    public static JSONArray mSelectProducts=new JSONArray();

    public static JSONArray mSelectScreens=new JSONArray();

    public static String sharePath = "";

    public static String shareRemark = "";

}
