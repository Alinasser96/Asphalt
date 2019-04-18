package droidlol.aly.asphalt.ui.findFines;

import android.content.Context;

import droidlol.aly.asphalt.pojo.FinesResponse;
import droidlol.aly.asphalt.pojo.SearchingData;
import droidlol.aly.asphalt.remote.RxNetworking;
import droidlol.aly.asphalt.ui.base.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;

public class PresenterFindFines extends BasePresenter<ViewFindFines> {
    public PresenterFindFines(CompositeDisposable compositeDisposable, Context context) {
        super(compositeDisposable, context);
    }

    public void getFines(SearchingData searchingData){
        RxNetworking.findFines(searchingData)
                .doOnSubscribe(disposable -> getView().setLoading(150))
                .doFinally(() -> getView().setLoaded(150))
                .subscribe(finesResponse -> {
                            getView().onFindFinesSuccess(finesResponse);
                        }
                        , throwable -> {
                            getView().setLoaded(150);
                            getView().onFindFinesFail();
                        });

    }
}
