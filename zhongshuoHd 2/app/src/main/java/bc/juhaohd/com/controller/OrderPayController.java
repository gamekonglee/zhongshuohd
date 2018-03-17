package bc.juhaohd.com.controller;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bc.juhaohd.com.R;
import bc.juhaohd.com.bean.PayResult;
import bc.juhaohd.com.cons.Constance;
import bc.juhaohd.com.cons.NetWorkConst;
import bc.juhaohd.com.listener.INetworkCallBack;
import bc.juhaohd.com.listener.INetworkCallBack02;
import bc.juhaohd.com.listener.ITwoCodeListener;
import bc.juhaohd.com.net.ApiClient;
import bc.juhaohd.com.ui.activity.IssueApplication;
import bc.juhaohd.com.ui.activity.OrderPayHomeActivity;
import bc.juhaohd.com.ui.activity.OrderPaySuccessActivity;
import bc.juhaohd.com.ui.activity.user.OrderDetailActivity;
import bc.juhaohd.com.ui.view.popwindow.WebSharePopWindow;
import bc.juhaohd.com.utils.MyShare;
import bocang.json.JSONObject;
import bocang.utils.AppDialog;
import bocang.utils.AppUtils;
import bocang.utils.LogUtils;
import bocang.utils.MyToast;
import okhttp3.Call;
import okhttp3.Response;

import static bc.juhaohd.com.cons.Constance.error_code;

/**
 * Created by DEMON on 2018/1/22.
 */
public class OrderPayController extends BaseController implements INetworkCallBack02 {
    //标记是支付
    private static final int SDK_PAY_FLAG = 1;
    private final OrderPayHomeActivity mView;
    private com.alibaba.fastjson.JSONObject mOrderObject;
    private String mOrderId;

    public Timer timer;
    private ITwoCodeListener mListener;

    @Override
    protected void handleMessage(int action, Object[] values) {

    }

    @Override
    protected void myHandleMessage(Message msg) {

    }
    public OrderPayController(OrderPayHomeActivity view){
        mView = view;
        initView();
        initData();
    }

    private void initData() {
//        LogUtils.logE("sendcheckOust",mView.consignee+","+ mView.shipping+","+mView.mobie+","+ mView.address+","+mView.idArray);
        mNetWork.sendCheckOutCart(mView.consignee, mView.shipping, mView.mobie, mView.address, mView.idArray, "", this);

    }

    private void initView() {

    }

    @Override
    public void onSuccessListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
        switch (requestCode){
            case NetWorkConst.CheckOutCart:
                LogUtils.logE("CheckOutCart",ans.toString());
                com.alibaba.fastjson.JSONObject orderObject = ans.getJSONObject(Constance.order);
                if(AppUtils.isEmpty(orderObject)){
                    MyToast.show(mView,"当前没有可支付的数据!");
                    return;
                }
                mOrderObject=orderObject;
                String order= orderObject.getString(Constance.id);
                String osn=orderObject.getString(Constance.sn);
                String total=orderObject.getString(Constance.total);
                com.alibaba.fastjson.JSONObject consin=orderObject.getJSONObject(Constance.consignee);
                String name=consin.getString(Constance.name);
                String mobie=consin.getString(Constance.mobile);
                String address=consin.getString(Constance.address);
                mView.tv_osn.setText(""+osn);
                mView.tv_money.setText("¥"+total);
                mView.tv_mobie.setText(""+mobie);
                mView.tv_name.setText(""+name);
                mView.tv_address.setText(""+address);
//                getPayOrder(order);
//                sendPayment(order, "wxpay.dmf");
                mOrderId=order;
                sendWxPay(mOrderId);
                break;
        }
    }
    private void sendWxPay(String order) {

        ApiClient.sendWxPayment(order, new Callback<String>() {
            @Override
            public String parseNetworkResponse(Response response, int id) throws Exception {
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.logE("wx_error",e.getMessage());

            }

            @Override
            public String onResponse(String response, int id) {
                LogUtils.logE("wx_success",response.toString());
                String result=response;
                if(result.contains("\"wxpay.dmf\"")){
                    result=result.substring(result.indexOf("\"wxpay.dmf\"")+11);
                }
//                LogUtils.logE("result:",result);
//                JSONObject ans=new JSONObject(result);
                org.json.JSONObject ans= null;
                try {
                    ans = new org.json.JSONObject(result);
                    org.json.JSONObject jsonObject=ans.getJSONObject(Constance.qrcode);
                    String codeUrl=jsonObject.getString(Constance.code_url);
                    final String qrcode = codeUrl.replace("\\/","/");
                    LogUtils.logE("wx_Success",codeUrl);
                    getWebView2(qrcode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getPayOrder(mOrderId);
                return null;
            }
        });
      /*  mNetWork.sendTwoWxCodePay(order, new INetworkCallBack02() {



            @Override
            public void onSuccessListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                com.alibaba.fastjson.JSONObject jsonObject=ans.getJSONObject(Constance.qrcode);
                String codeUrl=jsonObject.getString(Constance.code_url);
                final String qrcode = codeUrl;
                LogUtils.logE("wx_Success",codeUrl);
                getWebView2(qrcode);
                timer = new Timer();
                TimerTask task= new TimerTask() {
                    @Override
                    public void run() {
                        mView.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getPayOrderWx02(mOrderId);
//                                getPayOrder02(mOrderId);

                            }
                        });
                    }
                };

                //time为Date类型：在指定时间执行一次。

                //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
                //delay 为long类型：从现在起过delay毫秒执行一次。
                timer.schedule(task, 3000,3000);
            }



            @Override
            public void onFailureListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                LogUtils.logE("wxfailure",ans.toString());
            }
        });*/
    }

    private void getPayOrderWx02(String mOrderId) {
        ApiClient.sendWxPayment(mOrderId, new Callback<String>() {
            @Override
            public String parseNetworkResponse(Response response, int id) throws Exception {
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
//                LogUtils.logE("wx_error",e.getMessage());
                org.json.JSONObject ans= null;
                try {
                    ans = new org.json.JSONObject(e.getMessage());
                    int errorCode = ans.getInt(error_code);
                    if (errorCode == 404) {
                        MyToast.show(mView,"支付成功");
                        Intent intent = new Intent(mView, OrderPaySuccessActivity.class);
                        intent.putExtra(Constance.order, mOrderObject.toJSONString());
                        intent.putExtra(Constance.state, 1);
                        timer.cancel();
                        mView.setResult(200,intent);
                        mView.finish();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public String onResponse(String response, int id) {
//                LogUtils.logE("wx_success",response);
                return null;
            }
        });
      /*  mNetWork.sendTwoWxCodePay(mOrderId, new INetworkCallBack02() {
            @Override
            public void onSuccessListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                if(ans==null)return;
                LogUtils.logE("wx success:",
                        ans.toString());
                getWebView2(ans.getString(Constance.qrcode));
//                String qrcode = ans.getString(Constance.qrcode);
//                getWebView(qrcode);
//                MyToast.show(mView,"支付失败");
//                Intent intent = new Intent(mView, OrderPaySuccessActivity.class);
//                intent.putExtra(Constance.order, mOrderObject.toJSONString());
//                intent.putExtra(Constance.state, 1);
//                timer.cancel();
//                mView.setResult(200,intent);
//                mView.finish();

            }

            @Override
            public void onFailureListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                if(ans==null)return;
                int errorCode = ans.getInteger(error_code);
                if (errorCode == 404) {
                    MyToast.show(mView,"支付成功");
                    Intent intent = new Intent(mView, OrderPaySuccessActivity.class);
                    intent.putExtra(Constance.order, mOrderObject.toJSONString());
                    intent.putExtra(Constance.state, 1);
                    timer.cancel();
                    mView.setResult(200,intent);
                    mView.finish();
                }else {
//                    MyToast.show(mView,"支付失败");
//                    Intent intent = new Intent(mView, OrderPaySuccessActivity.class);
//                    intent.putExtra(Constance.order, mOrderObject.toJSONString());
//                    intent.putExtra(Constance.state, 1);
//                    timer.cancel();
//                    mView.setResult(200,intent);
//                    mView.finish();
                }

            }
        });*/
    }

    private void getWebView2(String qrcode) {
        mView.web_wv_wx.setActivity(mView);
        String html = qrcode;
        html = html.replace("200", mView.getResources().getString(R.string.code_size)+"");
        html = "<meta name=\"viewport\" content=\"width=device-width\"> " +
                "<div style=\"text-align:center\">" + html + " </div>";
//        mView.web_wv_wx.setInitialScale(25);//为25%，最小缩放等级
//        mView.web_wv_wx.loadData(html, "text/html; charset=UTF-8", null);//这种写法可以正确解析中文
        mView.web_wv_wx.loadUrl(qrcode);
    }

    @Override
    public void onFailureListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
        if(ans!=null) {
            MyToast.show(mView, ans.getString(Constance.error_desc));
        }else {
            MyToast.show(mView,"网络异常，请求失败");
        }

    }
    /**
     * 支付
     *
     * @param order
     */
    public void getPayOrder02(String order) {
        mOrderId = order;
        //TODO二维码支付
        mNetWork.sendTwoCodePay(order, new INetworkCallBack02() {
            @Override
            public void onSuccessListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {

//                LogUtils.logE("ipay success",ans.toString());
      /*          getPayOrderWx02(mOrderId);
                if(ans==null)return;
                String qrcode = ans.getString(Constance.qrcode);
                getWebView(qrcode);*/
//                MyToast.show(mView,"支付失败");
//                Intent intent = new Intent(mView, OrderPaySuccessActivity.class);
//                intent.putExtra(Constance.order, mOrderObject.toJSONString());
//                intent.putExtra(Constance.state, 1);
//                timer.cancel();
//                mView.setResult(200,intent);
//                mView.finish();

            }

            @Override
            public void onFailureListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                if(ans==null)return;
                int errorCode = ans.getInteger(error_code);
                if (errorCode == 404) {
                    MyToast.show(mView,"支付成功");
                    Intent intent = new Intent(mView, OrderPaySuccessActivity.class);
                    intent.putExtra(Constance.order, mOrderObject.toJSONString());
                    intent.putExtra(Constance.state, 1);
                    timer.cancel();
                    mView.setResult(200,intent);
                    mView.finish();
                }else {
//                    MyToast.show(mView,"支付失败");
//                    Intent intent = new Intent(mView, OrderPaySuccessActivity.class);
//                    intent.putExtra(Constance.order, mOrderObject.toJSONString());
//                    intent.putExtra(Constance.state, 1);
//                    timer.cancel();
//                    mView.setResult(200,intent);
//                    mView.finish();
                }

            }
        });
    }
    public void getWebView(String htmlValue) {
        mView.web_wv.setActivity(mView);
        String html = htmlValue;
        html = html.replace("200", mView.getResources().getString(R.string.code_size)+"");
        html = "<meta name=\"viewport\" content=\"width=device-width\"> " +
                "<div style=\"text-align:center\">" + html + " </div>";
//        mView.web_wv.setInitialScale(50);
        mView.web_wv.loadData(html, "text/html; charset=UTF-8", null);//这种写法可以正确解析中文
    }
    /**
     * 支付
     *
     * @param order
     */
    public void getPayOrder(String order) {
        mOrderId = order;
        //TODO二维码支付

        mNetWork.sendTwoCodePay(order, new INetworkCallBack02() {
            @Override
            public void onSuccessListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                final String qrcode = ans.getString(Constance.qrcode);
                LogUtils.logE("ipay_success:",qrcode);
                getWebView(qrcode.replace("\\/","/"));
//                sendWxPay(mOrderId);

                timer = new Timer();
                TimerTask task= new TimerTask() {
                    @Override
                    public void run() {
                        mView.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getPayOrderWx02(mOrderId);
                                getPayOrder02(mOrderId);

                            }
                        });
                    }
                };

                //time为Date类型：在指定时间执行一次。

                //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
                //delay 为long类型：从现在起过delay毫秒执行一次。
                timer.schedule(task, 3000,3000);

            }

            @Override
            public void onFailureListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
                int errorCode = ans.getInteger(error_code);
                if (errorCode == 404) {
                    mListener.onTwoCodeChanged("支付成功");
                }{
                    MyToast.show(mView,"支付失败");
//                    Intent intent = new Intent(mView, OrderPaySuccessActivity.class);
//                    intent.putExtra(Constance.order, mOrderObject.toJSONString());
//                    intent.putExtra(Constance.state, 1);
//                    timer.cancel();
//                    mView.setResult(200,intent);
//                    mView.finish();
                }

            }
        });
    }
    /**
     * 支付订单
     *
     * @param order
     * @param code
     */
    private void sendPayment(final String order, String code) {

        mNetWork.sendPayment(order, code, new INetworkCallBack02() {
            @Override
            public void onSuccessListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
//                LogUtils.logE("sendPay",ans.toString());

                if(ans==null)return;
//                mOrderObject = ans.getJSONObject(Constance.order);


//                WebSharePopWindow window = new WebSharePopWindow(mView, mView);
//                window.setListener(new ITwoCodeListener() {
//                    @Override
//                    public void onTwoCodeChanged(String path) {
//                        if("支付成功".equals(path)){
//                            MyToast.show(mView,"支付成功");
//                            Intent intent = new Intent(mView, OrderDetailActivity.class);
//                            intent.putExtra(Constance.order, mOrderObject.toJSONString());
//                            intent.putExtra(Constance.state, 1);
//                            mView.startActivity(intent);
//                            mView.finish();
//                        }else{
//                            AppDialog.messageBox("支付失败");
////                            mView.hideLoading();
//                            Intent intent = new Intent(mView, OrderDetailActivity.class);
//                            intent.putExtra(Constance.order, mOrderObject.toJSONString());
//                            mView.startActivity(intent);
//                            mView.finish();
//                        }
//                    }
//                });
            }

            @Override
            public void onFailureListener(String requestCode, com.alibaba.fastjson.JSONObject ans) {
//                mView.hideLoading();
                if(ans==null)return;
                MyToast.show(mView, "支付失败!");
            }
        });

    }

}
