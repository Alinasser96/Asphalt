package droidlol.aly.asphalt.remote;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import droidlol.aly.asphalt.app.AsphaltApp;
import droidlol.aly.asphalt.pojo.FinesResponse;
import droidlol.aly.asphalt.pojo.SearchingData;
import droidlol.aly.asphalt.util.RxUtils;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;

public class RxNetworking {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .build();

    public static Observable<FinesResponse> findFines(SearchingData searchingData) {
        return Rx2AndroidNetworking.post("http://85.187.140.233/api/index.php")
                .addBodyParameter("first_letter", searchingData.getFirstLetter())
                .addBodyParameter("second_letter", searchingData.getSecondLetter())
                .addBodyParameter("third_letter", searchingData.getThirdLetter())
                .addBodyParameter("numbers", searchingData.getNumbers())
                .setOkHttpClient(client)
                .build()
                .getObjectObservable(FinesResponse.class)
                .compose(RxUtils.applyNetworkSchedulers());
    }

}
