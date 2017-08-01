package austin.com.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * 带进度条的WebView
 * 使用方法:
 * WebFragment fragment = WebFragment.newInstance("http://www.baidu.com");
   getSupportFragmentManager().beginTransaction().add(R.id.webViewContainer, fragment, "web").commit();


 网页回退方案
 @Override
 public void onBackPressed() {
    if(fragment.onBackPressed()){
    }else{
        super.onBackPressed();
    }
 }
 */
public class WebFragment extends Fragment {

    private WebView webView;
    private String mUrl;
    private ProgressBar progressBar;

    public WebFragment() {
    }


    public static WebFragment newInstance(String url) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUrl = arguments.getString("url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout rootView = new RelativeLayout(getActivity());
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //init webview
        webView = new WebView(getActivity());
        webView.loadUrl(mUrl);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.addView(webView);
        initWebView(webView);

        //init progressbar
        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8);
        progressBar.setLayoutParams(layoutParams);
        ClipDrawable d = new ClipDrawable(new ColorDrawable(Color.BLUE), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        progressBar.setProgressDrawable(d);
        rootView.addView(progressBar);
        return rootView;
    }

    private void initWebView(WebView mWebView) {
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.requestFocus();
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setHorizontalScrollbarOverlay(true);
        mWebView.setHorizontalScrollBarEnabled(true);
        mWebView.requestFocusFromTouch();//支持获取手势焦点，输入用户名、密码或其他
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        //webview属性的设置
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        /*
          用WebView显示图片，可使用这个参数 设置网页布局类型：
          LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
          LayoutAlgorithm.SINGLE_COLUMN : 适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setJavaScriptEnabled(true);  //支持js
//        webSettings.setPluginsEnabled(true);  //支持插件
//        webSettings.setPluginState(WebSettings.PluginState.ON);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        //webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setGeolocationEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    progressBar.setVisibility(GONE);
                } else {
                    if (progressBar.getVisibility() == GONE)
                        progressBar.setVisibility(VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    /**
     *
     * @return true, 表示webview有返回的页面,Activity不需要再做操作
     */
    public boolean onBackPressed(){
        if (webView!=null && webView.canGoBack()) {
            webView.goBack();//返回上一页面
            return true;
        }else{
            return false;
        }
    }



}
