package com.olek.testify.activity;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.olek.testify.R;
import com.olek.testify.Utils.JSONParser;
import com.olek.testify.Utils.RandomString;
import com.olek.testify.Utils.TestParser;
import com.olek.testify.activity.NexusFragments.TestFormFragment;
import com.olek.testify.model.Answer;
import com.olek.testify.model.Task;
import com.olek.testify.model.Test;
import com.olek.testify.ocr.IOCRCallBack;
import com.olek.testify.ocr.OCRAsyncTask;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TestCameraActivity extends AppCompatActivity implements IOCRCallBack {

    @BindView(R.id.camera_image_view)
    ImageView cameraImageView;

    @BindView(R.id.ocr_text_view)
    TextView ocrTextView;

    OCRAsyncTask ocrAsyncTask;


    String mCurrentPhotoPath;
    private File mOriginalFile;
    private File mCompressedFile;
    private Bitmap compressedBitmap;

    private File createImageFile() throws IOException {
        // Create an image file name
        RandomString rs = new RandomString(5);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + rs.nextString() + "_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String COMPRESSED_IMAGE_DATA = "compressed_image_bundle";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE){
            if (requestCode != RESULT_OK){
                if (mCurrentPhotoPath != null) {
                    try {
                        Bitmap bitmap = rotateAndScaleBitmap(Uri.fromFile(mOriginalFile), 1280, 960);

                        File compressedFile = createImageFile();
                        mCompressedFile = compressedFile;
                        FileOutputStream out = new FileOutputStream(compressedFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                        cameraImageView.setImageBitmap(bitmap);
                        compressedBitmap = bitmap;



                        OCRAsyncTask ocrAsyncTask = new OCRAsyncTask(TestCameraActivity.this, "fd57163da188957", compressedFile, "eng", this);
                        //TestCameraActivity.this, mOriginalFile, false,"https://image.ibb.co/nBTnA5/test.png" ,"eng", this
                        ocrAsyncTask.execute();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Bitmap rotateAndScaleBitmap(final Uri selectedImageUri, int newWidth, int newHeight) {
        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

            ExifInterface exif = new ExifInterface(selectedImageUri.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            int angle = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                default:
                    angle = 0;
                    break;
            }
            Matrix mat = new Matrix();

            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            mat.postScale(scaleWidth, scaleHeight);

            /*
            if (angle == 0 && bm.getWidth() > bm.getHeight())
                mat.postRotate(90);
            else
                mat.postRotate(angle);
                */

            mat.postRotate(angle);


            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);

        } catch (IOException e) {
            Log.e("", "-- Error in setting image");
        } catch (OutOfMemoryError oom) {
            Log.e("", "-- OOM Error in setting image");
        }
        return null;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                mOriginalFile = photoFile;
            } catch (IOException ex) {
                // Error occurred while creating the File


            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.olek.testify.provider",
                        photoFile);
                this.getApplicationContext().grantUriPermission("com.olek.testify", photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION );
                takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                getApplicationContext().grantUriPermission(
                        "com.google.android.GoogleCamera",
                        photoURI,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION
                );

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @BindView(R.id.take_pic_button)
    Button takePictureButton;


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //savedInstanceState.putBoolean(BUNDLE_NEW_STATE, firstRun);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //firstRun = savedInstanceState.getBoolean(BUNDLE_NEW_STATE);

    }

    public static final String BUNDLE_NEW_STATE = "isActivityNew";

    boolean firstRun;

    Test mTest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        mTest = (Test) bundle.get(TestFormFragment.EXTRA_TEST_KEY);

        if(true){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            askForPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
            askForPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXST);
            askForPermission(android.Manifest.permission.CAMERA,CAMERA);

            takePictureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                }
            });
        } else {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(mCompressedFile.getAbsolutePath(),bmOptions);
            cameraImageView.setImageBitmap(bitmap);
        }




    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(TestCameraActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(TestCameraActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(TestCameraActivity.this, new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(TestCameraActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    static final Integer LOCATION = 0x1;
    static final Integer CALL = 0x2;
    static final Integer WRITE_EXST = 0x3;
    static final Integer READ_EXST = 0x4;
    static final Integer CAMERA = 0x5;
    static final Integer ACCOUNTS = 0x6;
    static final Integer GPS_SETTINGS = 0x7;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    public static int getDominantColor(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }

    @Override
    public void getOCRCallBackResult(String response) {
        Log.d(TestCameraActivity.class.getSimpleName(), response);


        // Parse Json

        Map<Task, List<Answer>> taskListMap = null;

        try {

            List<String> result = JSONParser.parseJSON(response);

            StringBuilder sb = new StringBuilder();
            for(String s: result){
                sb.append(s + "\n");
            }

            ocrTextView.setText(sb.toString());

            taskListMap = TestParser.parseTask(result);

            //if (taskListMap == null){
             //   return;
            //}

            StringBuilder sbParsed = new StringBuilder();

            if (taskListMap.size() != 0)
            for(Task task : taskListMap.keySet()){
                sbParsed.append(task.toString() + "\n--------------------------------\n");
               for(int i = 0; i < taskListMap.get(task).size(); i++){
                   sbParsed.append(taskListMap.get(task).get(i) + "\n");
               }

               sbParsed.append("\n--------------------------------\n");
            }

            ocrTextView.append(sbParsed.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //jsonArray.getJSONArray("ParsedResults");



        cameraImageView.setBackgroundColor(getDominantColor(compressedBitmap));

        final Map<Task, List<Answer>> finalMap = taskListMap;

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent markCorrectAnswers = new Intent(TestCameraActivity.this, SelectCorrectAnswersActivity.class);
                    markCorrectAnswers.putExtra(SelectCorrectAnswersActivity.KEY_HASHMAP_RESULTS, (Serializable)finalMap);
                    markCorrectAnswers.putExtra(TestFormFragment.EXTRA_TEST_KEY, mTest);
                    startActivity(markCorrectAnswers);

            }
        });

        takePictureButton.setText("MARK CORRECT");

    }
}
