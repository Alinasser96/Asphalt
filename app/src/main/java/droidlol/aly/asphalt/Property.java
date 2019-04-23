package droidlol.aly.asphalt;

/**
 * Created by bishoy on 9/26/16.
 */

public class Property<T> {

    private T t;
    private OnChangeListener<T> onChangeListener;

    public Property() {

    }

    public Property(T t) {
        this.t = t;
    }

    public void set(T tNew) {
        T tOld = t;
        t = tNew;
        if (onChangeListener != null) {
            boolean newValue = true;
//            if (tOld != null && tNew != null)
//                if (tOld.equals(tNew))
//                    newValue = false;
            if (newValue)
                onChangeListener.onChange(tOld, tNew);
        }


    }

    public T get() {
        return this.t;
    }

    public void fireChange(T t) {
        onChangeListener.onChange(null, t);
    }

    public void setOnChangeListener(OnChangeListener<T> onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void clear() {
        this.t = null;
    }

    public interface OnChangeListener<T> {
        void onChange(T tOld, T tNew);
    }

}