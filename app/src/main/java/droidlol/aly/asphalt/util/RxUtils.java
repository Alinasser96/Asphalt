package droidlol.aly.asphalt.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    public static <T> ObservableTransformer<T, T> applyNetworkSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Integer> observeTextSize(EditText editText) {
        return Observable.create(emitter -> {
            try {
                if (!emitter.isDisposed()) {
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            emitter.onNext(charSequence.toString().trim().length());
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            emitter.onNext(charSequence.toString().trim().length());
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

    }
}

