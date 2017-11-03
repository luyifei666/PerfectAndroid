package maitong.com.perfect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import maitong.com.perfect.R;
import maitong.com.perfect.base.BaseFragment;

/**
 * Created by tony on 15/8/17.
 */
public class FamilyFragment extends BaseFragment {

    public final static String HAS_NEW_NEWS = "HAS_NEW_NEWS";
    public final static String UPDATE_NEWS_DOT = "UPDATE_NEWS_DOT";

    private Context context;

    private static FamilyFragment sFragment;

    public static FamilyFragment newInstance() {
        if (sFragment == null) {
            sFragment = new FamilyFragment();
        }
        return sFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contextView = inflater.inflate(R.layout.fragment_family, container, false);
        context = getActivity();
        initUI(contextView);
        initData();
        return contextView;
    }

    @Override
    protected void initUI(View view) {
        updateSubTitleBar(view, "", -1, null);
        setleftTitleBarBtnVisable(view, View.GONE);
//
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
    }

}
