package com.sample.pronunciation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Bharat.
 */
public class CameraActivity extends AppCompatActivity implements IOCRCallBack{

    // Remaining flags , prefs, orientation variables
    private File imageFile = null;
    private int animation_height = 0;
    public boolean hasContinuousAutoFocus = false;
    private boolean hasFlash = false;
    public static int cameraOrientation = 0;
    public static int currentCameraId = 0;
    private boolean rotationRequired = false;
    private String TAG = "CameraActivity";
    private Animation slide = null;

    //Camera related
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private Camera.Parameters params;

    //UI
    public FrameLayout mCameraPreviewLayout;
    public FrameLayout mCameraFunctionsLayout;
    private FloatingActionButton mCaptureButton;
    private IOCRCallBack mIOCRCallBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mIOCRCallBack = this;
        mCameraPreviewLayout = (FrameLayout) findViewById(R.id.camera_preview);
        mCameraFunctionsLayout = (FrameLayout) findViewById(R.id.camera_functions);
        mCaptureButton = (FloatingActionButton) findViewById(R.id.capture_button);
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPictureCallBack);
            }
        });
        mCameraPreview = new CameraPreview(this, mCamera);
        mCameraPreviewLayout.addView(mCameraPreview);
    }

    /**
     * Resumes the activity from onPause() state
     * @throws NullPointerException
     */
    @Override
    protected void onResume() throws NullPointerException{
        super.onResume();
        if(mCamera == null) {
            boolean status = safeCameraOpen(0);
            if(!status){

            }
        }
        mCameraPreview.setCamera(mCamera);
        params = mCamera.getParameters();
        mCameraPreview.setParameters(params);
        //setFlashSettings();
        mCamera.startPreview();
    }


    /**
     *  Releases the camera and preview in onPause()
     * @throws NullPointerException
     */
    @Override
    protected void onPause() throws NullPointerException {
        releaseCameraAndPreview();
        super.onPause();
    }


    /**
     * Stores the temp pref in the user preferences and minimises the app
     */
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed.. Back button pressed");
        releaseCameraAndPreview();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * Releases the camera and preview. Saves the temp pref in user prefs.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCameraAndPreview();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     *  Opens the camera and returns a boolean showing the status
     * @param id
     * @return boolean
     */
    private boolean safeCameraOpen(int id){
        boolean qOpened = false;
        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.d(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }
        return qOpened;
    }

    /**
     *  Releases the camera and sets the camera to null
     * @throws NullPointerException
     */
    private void releaseCameraAndPreview() throws NullPointerException{
        if(mCameraPreview!=null) {
            mCameraPreview.setCamera(null);
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }else{
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }
    }



    /**
     * Called when the image is saved from the camera
     */
    private Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            FileOutputStream fos;
            if (pictureFile == null) {
                return;
            }
            try {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, bmOptions);
                Bitmap halfBitmap = getHalfBitmap(bitmap);
                fos = new FileOutputStream(pictureFile);
                halfBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                imageFile = pictureFile;
                mCamera.stopPreview();
                new ApiRequestAsync().execute();
            } catch (IOException e) {
                Log.d(TAG, "Image taken could not be saved.." + e.toString());
            }
        }
    };

    /**
     * Returns the file where the image captured from camera is to be saved
     * @return File - where the image can be saved.
     */
    private File getOutputMediaFile(){
        File tempStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), GlobalConstants.APP_FOLDER_NAME + File.separator + GlobalConstants.APP_TEMP_FOLDER_NAME);
        if (! tempStorageDir.exists()){
            if (! tempStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
            File noMediaFile = new File(tempStorageDir + File.separator + ".nomedia");
            try {
                noMediaFile.createNewFile();
            } catch (IOException e) {
                Log.d(TAG, "Image taken could not be saved.." + e.toString());
            } catch (AssertionError ae) {
                Log.d(TAG, "noMedia file could not be created.." + ae.toString());
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile = null;
        if(tempStorageDir.getPath() != null)
            mediaFile = new File(tempStorageDir.getPath() + File.separator + "IMG_request.jpg");

        return mediaFile;
    }

    /**
     * Moves the file from .Temp folder to app folder
     * @param tempPath
     * @return
     */
    private File moveFile(String tempPath){

        File oldFile = null;
        InputStream inStream = null;
        OutputStream outStream = null;
        File newFile = null;
        try {
            oldFile = new File(tempPath);
            newFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +File.separator + GlobalConstants.APP_FOLDER_NAME + File.separator, oldFile.getName());
            inStream = new FileInputStream(oldFile);
            outStream = new FileOutputStream(newFile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0){
                outStream.write(buffer, 0, length);
            }

            inStream.close();
            outStream.close();

            oldFile.delete();
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }

        return newFile;
    }

    private Bitmap getHalfBitmap(Bitmap bitmapOrg){

        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = 200;
        int newHeight = 200;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
    }


    /************************************************************************* Setters and getters**********************************************************************/


    public void setHasContinuousAutoFocus(boolean hasContinuousAutoFocus) {
        this.hasContinuousAutoFocus = hasContinuousAutoFocus;
    }

    public void setParams(Camera.Parameters params) {
        this.params = params;
    }

    public void setRotationRequired(boolean rotationRequired) {
        this.rotationRequired = rotationRequired;
    }

    @Override
    public void getOCRCallBackResult(String response) {
        Log.i(TAG, response.toString());
    }

}
