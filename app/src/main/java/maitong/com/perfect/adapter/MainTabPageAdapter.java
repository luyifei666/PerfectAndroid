package maitong.com.perfect.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

import maitong.com.perfect.fragment.SchoolFragment;

/**
 * ViewPager适配器
 */
public class MainTabPageAdapter extends FragmentStatePagerAdapter {
    List<Fragment> mFragments;

    public MainTabPageAdapter(FragmentManager fm,List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }
    public MainTabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        return mFragments.get(position);
        Fragment fragment = null;
        Log.e("lyf","position = " + position);
        switch (position) {
            case 0:
                fragment = SchoolFragment.newInstance();
                break;
            case 1:
//                fragment = SchoolFragment.newInstance();
                break;
            case 2:
//                fragment = SchoolFragment.newInstance();
                break;
            case 3:
//                fragment = SchoolFragment.newInstance();
                break;
            case 4:
//                fragment = SchoolFragment.newInstance();
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position + "";
    }

    @Override
    public int getCount() {
        return 5;
//        return mFragments.size();
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        //do nothing here! no call to super.restoreState(arg0, arg1);
    }

}