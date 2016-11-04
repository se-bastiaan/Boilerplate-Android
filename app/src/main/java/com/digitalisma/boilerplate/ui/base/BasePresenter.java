package com.digitalisma.boilerplate.ui.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Base class that implements the MvpPresenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling mvpView().
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mvpView;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
        mvpView = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public V view() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public void add(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}

