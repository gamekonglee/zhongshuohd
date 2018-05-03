package bc.juhaohd.com.net;

import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pgyersdk.crash.PgyCrashManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.ui.activity.IssueApplication;
import bc.juhaohd.com.utils.FileUtil;
import bc.juhaohd.com.utils.ImageUtil;
import bc.juhaohd.com.utils.MyShare;
import bc.juhaohd.com.utils.NetworkStateManager;
import bc.juhaohd.com.utils.UIUtils;
import bocang.utils.LogUtils;
import bocang.utils.MyToast;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.provider.ContactsContract.CommonDataKinds.StructuredName.PREFIX;

/**
 * Created by bocang on 18-2-2.
 */

public class ApiClient  {
    /**
     * 检查网络是否联网
     */
    public static boolean hashkNewwork() {

        boolean hasNetwork = NetworkStateManager.instance().isNetworkConnected();
        if (!hasNetwork) {
            Toast.makeText(IssueApplication.getInstance(), "您的网络连接已中断", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static void sendPayment(String order, final Callback callback) {
        if(!hashkNewwork()){
            return;
        }

//        responseListener.onStarted();
        Map<String ,String> map=new HashMap<>();
        map.put("order",order);
        String url= NetWorkConst.TWO_CODE_Pay;
        String token= MyShare.get(UIUtils.getContext()).getString(Constance.TOKEN);

        OkHttpUtils.post()
                .url(url)
                .addHeader("X-bocang-Authorization",token)
                .addParams("order", order)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(okhttp3.Response response, int i1) throws Exception {
                            String jsonRes=response.body().string();
//                        callback.onResponse(advertBeanList,i1);
                        return jsonRes;
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int i) {
                        callback.onError(call,e,i);
                    }

                    @Override
                    public String onResponse(Object o, int i) {
                        callback.onResponse(o,i);

                        return null;
                    }
                });
        //        requestQueue.add(simplePostRequest);

    }
    public static void sendWxPayment(String order, final Callback<String> callback) {
        if(!hashkNewwork()){
            return;
        }

//        responseListener.onStarted();
        Map<String ,String> map=new HashMap<>();
        map.put("order",order);
        String url= NetWorkConst.TWO_CODE_Pay;
        String token= MyShare.get(UIUtils.getContext()).getString(Constance.TOKEN);

        OkHttpUtils.post()
                .url(url)
                .addHeader("X-bocang-Authorization",token)
                .addParams("order", order)
                .addParams("code","wxpay.dmf")
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(okhttp3.Response response, int i1) throws Exception {
                        String jsonRes=response.body().string();
//                        callback.onResponse(advertBeanList,i1);

                        return jsonRes;
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int i) {
                        callback.onError(call,e,i);
                    }

                    @Override
                    public String onResponse(Object o, int i) {
                        callback.onResponse(String.valueOf(o),i);

                        return null;
                    }
                });
        //        requestQueue.add(simplePostRequest);

    }

    public static void sendUser( final Callback<String> callback) {
        if(!hashkNewwork()){
            return;
        }

//        responseListener.onStarted();
//        Map<String ,String> map=new HashMap<>();
        String token= MyShare.get(UIUtils.getContext()).getString(Constance.TOKEN);
        if(TextUtils.isEmpty(token))return;
        OkHttpUtils.post()
                .url(NetWorkConst.PROFILE)
                .addHeader("X-bocang-Authorization",token)
//                .addParams("order", order)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(okhttp3.Response response, int i1) throws Exception {
                        String jsonRes=response.body().string();
//                        LogUtils.logE("okhttp3:",jsonRes);

//                        callback.onResponse(advertBeanList,i1);
                        return jsonRes;
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int i) {
                        callback.onError(call,e,i);
                    }

                    @Override
                    public String onResponse(Object o, int i) {
                        callback.onResponse((String) o,i);

                        return null;
                    }
                });
    }

    public static void sendAddress(int userId, final Callback<String> callback) {
        if(!hashkNewwork()){
            return;
        }

//        responseListener.onStarted();
//        Map<String ,String> map=new HashMap<>();
        String token= MyShare.get(UIUtils.getContext()).getString(Constance.TOKEN);
        if(TextUtils.isEmpty(token))return;
        OkHttpUtils.post()
                .url( NetWorkConst.USER_SHOP_ADDRESS + userId)
                .addHeader("X-bocang-Authorization",token)
//                .addParams("order", order)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(okhttp3.Response response, int i1) throws Exception {
                        String jsonRes=response.body().string();
//                        LogUtils.logE("okhttp3:",jsonRes);

//                        callback.onResponse(advertBeanList,i1);
                        return jsonRes;
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int i) {
                        callback.onError(call,e,i);
                    }

                    @Override
                    public String onResponse(Object o, int i) {
                        callback.onResponse((String) o,i);

                        return null;
                    }
                });
    }
    public static void upLoadFile(File file, String url, Map<String, String> paramsMap, String imageName, final Callback<String> callback) {
        if(!hashkNewwork()){
            return;
        }
//        File filet=new File(IssueApplication.getcontext().getCacheDir()+"/"+imageName);
////        LogUtils.logE("file:",filet.getAbsolutePath());
//        OutputStream os = null;
//        try {
//            os = new FileOutputStream(filet);
//
//            int bytesRead = 0;
//            byte[] buffer = new byte[8192];
//            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            os.close();
//            inputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String targetPath = IssueApplication.getcontext().getCacheDir()+"/"+ SystemClock.currentThreadTimeMillis()+".jpg";
        //调用压缩图片的方法，返回压缩后的图片path
        final String compressImage = ImageUtil.compressImage(file.getAbsolutePath(), targetPath, 40);
        final File compressedPic = new File(compressImage);
//        LogUtils.logE("compressfile:",compressedPic.getAbsolutePath());
//        try {
//           LogUtils.logE("uplooad:","compress"+compressedPic.exists()+",filesize:"+ FileUtil.getFileSize(file)+","+FileUtil.getFileSize(compressedPic));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name",paramsMap.get("name"))
                .addFormDataPart("goods_id",paramsMap.get("goods_id"))
                .addFormDataPart("content",paramsMap.get("content"))
                .addFormDataPart("style",paramsMap.get("style"))
                .addFormDataPart("space",paramsMap.get("space"))
                .addFormDataPart("parent_id",paramsMap.get("parent_id"))
                .addFormDataPart("image", imageName,
                        RequestBody.create(MediaType.parse("image/png"), compressedPic));

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-bocang-Authorization",MyShare.get(UIUtils.getContext()).getString(Constance.TOKEN))
                .addHeader("Content-Disposition"," form-data; name="+paramsMap.get("name"))
                .addHeader("Charset","utf-8")
                .addHeader("connection","keep-alive")
                .addHeader("Content-Type", "multipart/form-data" + ";boundary=" + UUID.randomUUID().toString())
                .post(requestBody)
                .build();
        OkHttpClient mOkHttpClent = new OkHttpClient();
        Call call = mOkHttpClent.newCall(request);
       try {
           call.enqueue(new okhttp3.Callback() {
               @Override
               public void onFailure(Call call, IOException e) {
//                LogUtils.logE("failed",e.getMessage());
                   callback.onError(call,e,0);

               }

               @Override
               public void onResponse(Call call, Response response) throws IOException {

//                LogUtils.logE("onResponse:",response.body().string());
                   callback.onResponse(response.body().string(),0);
               }
           });
       }catch (Exception e){
           PgyCrashManager.reportCaughtException(IssueApplication.getContext(), e);
       }

    }

//    public static void SendRequest(String url, final Callback callback){
//        if(!hashkNewwork()){
//            return;
//        }
//
////        responseListener.onStarted();
////        Map<String ,String> map=new HashMap<>();
//        String token= MyShare.get(UIUtils.getContext()).getString(Constance.TOKEN);
//        OkHttpUtils.post()
//                .url(url)
//                .addHeader("X-bocang-Authorization",token)
//                .build()
//                .execute(new Callback() {
//                    @Override
//                    public Object parseNetworkResponse(okhttp3.Response response, int i1) throws Exception {
//                        String jsonRes=response.body().string();
//                        LogUtils.logE("okhttp3:",jsonRes);
//
////                        callback.onResponse(advertBeanList,i1);
//                        return jsonRes;
//                    }
//
//                    @Override
//                    public void onError(okhttp3.Call call, Exception e, int i) {
//                        callback.onError(call,e,i);
//                    }
//
//                    @Override
//                    public void onResponse(Object o, int i) {
//                        callback.onResponse((String) o,i);
//
//                    }
//                });
//    }
}
