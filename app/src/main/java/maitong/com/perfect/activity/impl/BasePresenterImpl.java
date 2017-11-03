package maitong.com.perfect.activity.impl;


import maitong.com.perfect.activity.presenter.IBasePresenter;

/**
 * Created by maning on 16/6/21.
 */
public class BasePresenterImpl<T> implements IBasePresenter {

    public T mView;

    protected void attachView(T mView) {
        this.mView = mView;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
