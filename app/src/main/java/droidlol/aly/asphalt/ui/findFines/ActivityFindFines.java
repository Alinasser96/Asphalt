package droidlol.aly.asphalt.ui.findFines;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidlol.aly.asphalt.R;
import droidlol.aly.asphalt.pojo.FinesResponse;
import droidlol.aly.asphalt.util.CommonUtil;
import io.reactivex.disposables.CompositeDisposable;

public class ActivityFindFines extends AppCompatActivity implements ViewFindFines, View.OnClickListener {

    private static final int REQUEST_IMAGE = 100;
    private static final int STORAGE = 1;
    private static File capturedImageFile;
    @BindView(R.id.imageView)
    ImageView plateImageView;
    @BindView(R.id.get_fines_button)
    Button getFinesButton;
    @BindView(R.id.fines_textView)
    TextView resultTextView;
    @BindView(R.id.plate_data_textView)
    TextView plateDataTextView;

    private Bitmap bitmap;

    private ProgressDialog progressDialog;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PresenterFindFines presenterFindFines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //progress dialog, textViews, buttons and presenter initialization
        init();
    }

    private void init() {

        //progressDialog initialization
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("wait..");

        //presenter initialization
        presenterFindFines = new PresenterFindFines(compositeDisposable, this);
        presenterFindFines.attachView(this);

        //textViews initialization
        resultTextView.setText(getString(R.string.press_to_get_fines));

        //buttons initialization
        getFinesButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                //scale down the photo
                Uri uri = Uri.fromFile(capturedImageFile);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                File scaledDownCapturedImageFile = CommonUtil.scaleDown(capturedImageFile, 300, false, bitmap);
                //get fines from api
                presenterFindFines.getFines(scaledDownCapturedImageFile);
                //set plate img in imageView
                Picasso.get().load(uri).into(plateImageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void checkPermission() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            Toast.makeText(this, "Storage access needed to manage the picture.", Toast.LENGTH_LONG).show();
            String[] params = permissions.toArray(new String[0]);
            ActivityCompat.requestPermissions(this, params, STORAGE);
        } else { // We already have permissions, so handle as normal
            takePicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE: {
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for WRITE_EXTERNAL_STORAGE
                boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if (storage) {
                    // permission was granted, yay!
                    takePicture();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Storage permission is needed to analyse the picture.", Toast.LENGTH_LONG).show();
                }
            }
            default:
                break;
        }
    }


    public void takePicture() {
        // Use a folder to store all results
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        10);
                return;
            }
        }
        File folder = new File(Environment.getExternalStorageDirectory() + "/OpenALPR/");
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Generate the path for the next photo
        String name = CommonUtil.dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss");
        capturedImageFile = new File(folder, name + ".jpg");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(capturedImageFile));
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    public void onFindFinesSuccess(FinesResponse finesResponse) {
        resultTextView.setText(CommonUtil.formatStrings(getString(R.string.your_fines), finesResponse.getResult()));
        plateDataTextView.setText(finesResponse.getPlateData());
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_fines_button:
                checkPermission();
                break;
        }
    }
}