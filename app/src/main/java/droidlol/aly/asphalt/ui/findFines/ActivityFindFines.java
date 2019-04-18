package droidlol.aly.asphalt.ui.findFines;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidlol.aly.asphalt.R;
import droidlol.aly.asphalt.pojo.FinesResponse;
import droidlol.aly.asphalt.pojo.SearchingData;
import io.reactivex.disposables.CompositeDisposable;

public class ActivityFindFines extends AppCompatActivity implements ViewFindFines, View.OnClickListener {

    @BindView(R.id.get_fines)
    Button getFines;
    @BindView(R.id.et_first)
    EditText etFirst;
    @BindView(R.id.first)
    TextInputLayout first;
    @BindView(R.id.et_second)
    EditText etSecond;
    @BindView(R.id.second)
    TextInputLayout second;
    @BindView(R.id.et_third)
    EditText etThird;
    @BindView(R.id.third)
    TextInputLayout third;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.number)
    TextInputLayout number;
    private ProgressDialog progressDialog;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PresenterFindFines presenterFindFines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("wait..");

        presenterFindFines = new PresenterFindFines(compositeDisposable, this);
        presenterFindFines.attachView(this);
        getFines.setOnClickListener(this);

    }

    @Override
    public void onFindFinesSuccess(FinesResponse finesResponse) {
        Toast.makeText(this, finesResponse.getResult(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFindFinesFail() {
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoading(int apiCode) {
        showProgress("loading...");
    }

    @Override
    public void setLoaded(int apiCode) {
        hideProgress();
    }


    @Override
    public void onClick(View v) {

        SearchingData searchingData = new SearchingData();
        searchingData.setFirstLetter(etFirst.getText().toString());
        searchingData.setSecondLetter(etSecond.getText().toString());
        searchingData.setThirdLetter(etThird.getText().toString());
        searchingData.setNumbers(etNumber.getText().toString());
        switch (v.getId()) {
            case R.id.get_fines:
                presenterFindFines.getFines(searchingData);
        }
    }

    public void showProgress(final String message) {
        if (isFinishing()) {
            return;
        }
        runOnUiThread(() -> {
            if (!progressDialog.isShowing()) {
                progressDialog.setMessage(message);
                progressDialog.show();
            }
        });
    }

    public void hideProgress() {
        if (isFinishing()) {
            return;
        }
        runOnUiThread(() -> {
            if (null != progressDialog && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        });
    }
}
