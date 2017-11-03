package maitong.com.perfect.activity.impl;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import maitong.com.perfect.R;
import maitong.com.perfect.activity.iView.IWebView;
import maitong.com.perfect.activity.presenter.IWebPresenter;


/**
 * Created by maning on 16/6/21.
 */
public class WebPresenterImpl extends BasePresenterImpl<IWebView> implements IWebPresenter {

    private Context context;

    public WebPresenterImpl(Context context, IWebView iWebView) {
        this.context = context;
        attachView(iWebView);
    }

    @Override
    public void copy(String string) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        ClipData clipData = ClipData.newPlainText("text", string);
        cm.setPrimaryClip(clipData);
        if(mView != null) {
            mView.showToast(context.getString(R.string.gank_hint_copy_success));
        }
    }

    @Override
    public void openBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

}
