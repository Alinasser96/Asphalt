package droidlol.aly.asphalt.ui.base;

import android.content.Context;

import com.androidnetworking.error.ANError;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends BaseView> implements Presenter<V> {

    private V view;
    private final Context context;
    private final CompositeDisposable compositeDisposable;

    public BasePresenter(CompositeDisposable compositeDisposable, Context context) {
        this.compositeDisposable = compositeDisposable;
        this.context = context;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }


    @Override
    public void attachView(V view) {
        this.view = view;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void detachView() {
        view = null;
        compositeDisposable.clear();
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    protected V getView() {
        return view;
    }

    protected String getErrorMessage(Throwable throwable) {
        if (throwable instanceof ANError)
            return ((ANError) throwable).getErrorDetail();
        else {
            return throwable.getMessage();
        }

    }


}