package maitong.com.perfect.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import butterknife.Bind;
import butterknife.ButterKnife;
import maitong.com.perfect.R;
import maitong.com.perfect.activity.iView.IWebView;
import maitong.com.perfect.activity.impl.WebPresenterImpl;
import maitong.com.perfect.base.BaseActivity;
import maitong.com.perfect.utils.IntentUtils;
import maitong.com.perfect.utils.MySnackbar;
import maitong.com.perfect.utils.NetUtils;

/**
 * 展示网页的
 */
public class WebActivity extends BaseActivity implements IWebView {

    private static final String TAG = WebActivity.class.getSimpleName();

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.rootView)
    LinearLayout rootView;

    //标题
    private String flagTitle;
    private String titleContent;
    private String url;

    private WebPresenterImpl webPresenter;

    private static String jsStyle = "javascript:(function(){\n" +
            "\t\t   document.body.style.backgroundColor=\"%s\";\n" +
            "\t\t    document.body.style.color=\"%s\";\n" +
            "\t\t\tvar as = document.getElementsByTagName(\"a\");\n" +
            "\t\tfor(var i=0;i<as.length;i++){\n" +
            "\t\t\tas[i].style.color = \"%s\";\n" +
            "\t\t}\n" +
            "\t\tvar divs = document.getElementsByTagName(\"div\");\n" +
            "\t\tfor(var i=0;i<divs.length;i++){\n" +
            "\t\t\tdivs[i].style.backgroundColor = \"%s\";\n" +
            "\t\t}\n" +
            "\t\t})()";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        webPresenter = new WebPresenterImpl(this, this);

        initIntent();

        initMyToolBar();

        initWebView();

    }

    private void initMyToolBar() {
        String title;
        if (TextUtils.isEmpty(flagTitle)) {
            title = titleContent;
        } else {
            title = flagTitle + "+" + titleContent;
        }
//        int currentSkinType = SkinManager.getCurrentSkinType(this);
//        if (SkinManager.THEME_DAY == currentSkinType) {
//            initToolBar(toolbar, title, R.drawable.gank_ic_back_white);
//        } else {
//            initToolBar(toolbar, title, R.drawable.gank_ic_back_night);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_copy:
                webPresenter.copy(url);
                break;
            case R.id.action_open:
                webPresenter.openBrowser(url);
                break;
            case R.id.action_share:
                IntentUtils.startAppShareText(WebActivity.this, "GankMM链接分享", "GankMM链接分享：" + url);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initIntent() {
        Intent intent = getIntent();
        flagTitle = intent.getStringExtra(IntentUtils.WebTitleFlag);
        titleContent = intent.getStringExtra(IntentUtils.WebTitle);
        url = intent.getStringExtra(IntentUtils.WebUrl);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        //设置背景色
        webView.setBackgroundColor(0);
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        //自适应屏幕
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webView.getSettings().setDisplayZoomControls(false);
        //设置缩放比例：最小25
        webView.setInitialScale(100);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (NetUtils.hasNetWorkConection(this)) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);   // 根据cache-control决定是否从网络上取数据。
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);   //优先加载缓存
        }

        //////////////////////////////
        webView.loadUrl(url);
        //设置了默认在本应用打开，不设置会用浏览器打开的
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //设置webView
                String backgroudColor = "#00000000";//透明
                String fontColor = "#000000";//字体
                String urlColor = "#50cccccc";
//                SkinManager.setupWebView(webView, backgroudColor, fontColor, urlColor);
                if (view != null) {
                    view.setBackgroundColor(0);
//                    if (getCurrentSkinType(MyApplication.getIntstance()) == THEME_NIGHT) {
                        String js = String.format(jsStyle, backgroudColor, fontColor, urlColor, backgroudColor);
                        webView.loadUrl(js);
//                    }
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    progressbar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    if (progressbar.getVisibility() == View.GONE) {
                        progressbar.setVisibility(View.VISIBLE);
                    }
                    progressbar.setProgress(newProgress);
                }
            }
        });

        webView.setDownloadListener(new MyWebViewDownLoadListener());
    }


    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        //保证了webView退出后不再有声音
        webView.reload();
    }


    @Override
    protected void onDestroy() {
        webPresenter.detachView();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            rootView.removeView(webView);
            webView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public void showToast(String msg) {
//        MySnackbar.makeSnackBarBlack(toolbar, msg);
    }

    @Override
    public void showBaseProgressDialog(String msg) {
        showProgressDialog();
    }

    @Override
    public void hideBaseProgressDialog() {
        dissmissProgressDialog();
    }

}
