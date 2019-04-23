package droidlol.aly.asphalt.remote;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.File;

import droidlol.aly.asphalt.Property;
import droidlol.aly.asphalt.app.AsphaltApp;
import droidlol.aly.asphalt.pojo.FinesResponse;
import droidlol.aly.asphalt.pojo.SearchingData;
import droidlol.aly.asphalt.util.RxUtils;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;

public class RxNetworking {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .build();

    public static Observable<FinesResponse> findFines(File propertyPhotoFile) {
        return Rx2AndroidNetworking.upload("http://85.187.140.233/ocrapi/index.php")
                .addMultipartFile("img", propertyPhotoFile)
                .setOkHttpClient(client)
                .build()
                .getObjectObservable(FinesResponse.class)
                .compose(RxUtils.applyNetworkSchedulers());
    }

}
