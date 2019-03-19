package tu.foxtrot.foxtrotdoorpanelmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import tu.foxtrot.foxtrotdoorpanelmobileapp.network.RetrofitClient;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi.WorkersAPI;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.Worker;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import android.util.Base64;
import java.util.List;


import static java.lang.Integer.parseInt;

/**
 * The type Creating worker activity.
 */
public class CreatingWorkerActivity extends AppCompatActivity {
    private final String TAG = "CreatingWorkerActivity";

    private EditText nameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText roomEdit;
    private EditText positionEdit;
    private EditText summaryEdit;
    private EditText phoneEdit;
    private Button createWorkerButton;

    private Button btn;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;

    /**
     * The Bitmap 1.
     */
    Bitmap bitmap1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        nameEdit = findViewById(R.id.newWorkerName);
        emailEdit = findViewById(R.id.newWorkerEmail);
        passwordEdit = findViewById(R.id.newWorkerPassword);
        roomEdit = findViewById(R.id.newWorkerRoom);
        positionEdit = findViewById(R.id.newWorkerPosition);
        summaryEdit = findViewById(R.id.newWorkerSummary);
        phoneEdit = findViewById(R.id.newWorkerPhone);

        createWorkerButton = (Button) findViewById(R.id.submitButton);

        createWorkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String room = roomEdit.getText().toString();
                String position = positionEdit.getText().toString();
                String summary = summaryEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                Worker worker = new Worker();
                worker.setRoom(room);
                worker.setName(name);
                worker.setEmail(email);
                worker.setPassword(password);
                worker.setPosition(position);
                worker.setSummary(summary);
                worker.setPhoneNumber(phone);

                if (name.equals("") || email.equals("") || password.equals("")) {
                    Toast.makeText(CreatingWorkerActivity.this,
                            "Name, Email and Password must be provided",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (bitmap1 != null) {
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 5, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    String image = Base64.encodeToString(b , Base64.DEFAULT);

                    worker.setImage(image);
                }

                createNewWorker(worker);




            }
        });

        requestMultiplePermissions();

        btn = (Button) findViewById(R.id.btn);
        imageview = (ImageView) findViewById(R.id.iv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    /**
     * Create new worker.
     *
     * @param worker the worker
     */
    public void createNewWorker(Worker worker)
    {
        WorkersAPI workersApi = RetrofitClient.getRetrofitInstance().create(WorkersAPI.class);
        Call <Worker> call= workersApi.createWorker(worker);
        Log.d(TAG, "Creating new worker request sent");

        call.enqueue(new Callback<Worker>() {
            @Override
            public void onResponse(Call<Worker> call, Response<Worker> response) {
                Worker workerResponse = response.body();

                if (workerResponse == null) {
                    Log.d(TAG, "Worker was NOT created");
                    Toast.makeText(CreatingWorkerActivity.this,
                            "Error | Worker not created", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Successfully created new worker");
                    Toast.makeText(CreatingWorkerActivity.this, "New worker <" +
                            worker.getName() + "> created", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(CreatingWorkerActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Worker> call, Throwable t) {
                Log.d(TAG, "Failed creating new worker: " + t.getMessage());
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    /**
     * Choose photo from gallary.
     */
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(CreatingWorkerActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);
                    bitmap1=bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CreatingWorkerActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            bitmap1=thumbnail;
            Toast.makeText(CreatingWorkerActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Save image string.
     *
     * @param myBitmap the my bitmap
     * @return the string
     */
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 5, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }



}






