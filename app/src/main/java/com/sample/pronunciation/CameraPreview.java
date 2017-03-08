package com.sample.pronunciation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Amogh on 6/29/2015.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private String TAG = "CameraPreview";

    private int TYPE_PREVIEW = 0;
    private int TYPE_PICTURE = 1;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private Context mContext = null;
    private Activity parentActivity = null;
    private  int bottombar_height = 0;

    //Constructor that obtains context and camera
    public CameraPreview(Context context, Camera camera) {
        super(context);
        Log.d(TAG, "CameraPreview constructor");
        this.parentActivity = (Activity) context;
        this.mCamera = camera;
        this.mContext = context;
        this.mSurfaceHolder = this.getHolder();
        this.mSurfaceHolder.addCallback(this); // we get notified when underlying surface is created and destroyed
        this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //this is a deprecated method, is not requierd after 3.0
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) throws NullPointerException{
        Log.d(TAG, "surfaceCreated");
        if(mCamera != null) {
            try {
                Log.d(TAG, "Surface Created.");
                setParameters(mCamera.getParameters());
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.e(TAG, "startPreview failed. CameraPreview.surfaceCreated() failed.");
            }
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) throws NullPointerException{
        Log.d(TAG, "Surface destroyed");
        if(mCamera != null)
            mCamera.stopPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) throws NullPointerException{
        Log.d(TAG, "Surface Changed");
        try {
            setParameters(mCamera.getParameters());
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.e(TAG, "Start Preview failed. CameraPreview.surfaceChanged() failed");
        }
    }

    /**
     * Stops the preview, releases the camera and sets the camera to null
     */
    private void stopPreviewAndFreeCamera() throws NullPointerException{
        Log.d(TAG, "stopPreviewAndFreeCamera().. Stopping the preview and free the camera instance");
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * Sets the camera object with the parameter
     * @param camera
     * @throws NullPointerException
     */
    public void setCamera(Camera camera) throws NullPointerException{
        Log.d(TAG, "setCamera().. Set the camera instance and preview");
        if (mCamera == camera) { return; }
        stopPreviewAndFreeCamera();
        mCamera = camera;

        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //mCamera.startPreview();
        }
    }

    /**
     * Sets the parameters for the camera. Also saves it in the CameraActivity
     * @param params
     */
    public void setParameters(Camera.Parameters params){
        List<String> focusModes = params.getSupportedFocusModes();
        if(focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            ((CameraActivity)parentActivity).setHasContinuousAutoFocus(true);
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(CameraActivity.currentCameraId, cameraInfo);
        int orientation = cameraInfo.orientation;
        List<Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        List<Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();

        // Get window dimensions
        Display display = ((CameraActivity)mContext).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int w = size.x;
        int h = size.y;
        //Point tempOptimalPreviewSize = getOptimalPreviewSize(previewSizes, w, h);
        Camera.Size optimalPreviewSize = getOptimalPreviewSize(parentActivity, previewSizes, (double) h/w);

        int displayRotation = display.getRotation();
        CameraActivity.cameraOrientation = orientation;
        int degrees = 0;

        switch (displayRotation) {
            case Surface.ROTATION_0 : degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }
        int result = (CameraActivity.cameraOrientation - degrees + 360) % 360;
        mCamera.setDisplayOrientation(result);
        params.setPreviewSize(optimalPreviewSize.width, optimalPreviewSize.height);
//        // Get optimal picture sizes/
        Camera.Size optimalPictureSize = getOptimalSize(pictureSizes, 720, 1280, TYPE_PICTURE);
        if(orientation == 90 || orientation == 270) {
            params.setPictureSize(optimalPictureSize.width, optimalPictureSize.height);
        } else {
            ((CameraActivity)mContext).setRotationRequired(true);
            params.setPictureSize(optimalPictureSize.width, optimalPictureSize.height);
        }

        mCamera.setParameters(params);
        ((CameraActivity)mContext).setParams(params);
    }

    /**
     *  Returns the best picture size for the camera
     * @param sizes
     * @param w
     * @param h
     * @return
     */
    private Camera.Size getOptimalSize(List<Camera.Size> sizes, int w, int h, int type) {

        List<Double> ratios = new ArrayList<>();
        double targetRatio = (double) h/w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
//        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.height / size.width;
            if (Math.abs(ratio - targetRatio) > 1) {
                ratio = (double) size.width / size.height;
            }
            ratios.add(Math.abs(ratio - targetRatio));
        }

        double maxRatio = Double.MAX_VALUE;
        if(type == TYPE_PREVIEW) {
            for(int j = 0; j < ratios.size(); j++) {
                if(ratios.get(j) < maxRatio) {
                    optimalSize = sizes.get(j);
                    maxRatio = ratios.get(j);
                }
            }
        }
        else {
            for(int j = ratios.size()-1; j >= 0; j--) {
                if((sizes.get(j).height == h && sizes.get(j).width == w) ||
                        sizes.get(j).height == w && sizes.get(j).width == h ) {
                    optimalSize = sizes.get(j);
                    break;
                }
                if(ratios.get(j) < maxRatio) {
                    optimalSize = sizes.get(j);
                    maxRatio = ratios.get(j);
                }
            }
        }

        return optimalSize;
    }

    /**
     * Returns the size of the default display
     * @param activity
     * @param size
     * @return
     */
    public static Point getDefaultDisplaySize(Activity activity, Point size) {
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size;
    }

    /**
     * Given the height and width of the screen and the list of supported preview sizes, returns the
     * best preview size which maintains the aspect ratio.
     * @param currentActivity
     * @param sizes
     * @param targetRatio
     * @return
     */
    public Camera.Size getOptimalPreviewSize(Activity currentActivity,
                                             List<Camera.Size> sizes, double targetRatio) {
        // Use a very small tolerance because we want an exact match.
        final double ASPECT_TOLERANCE = 0.01;
        if (sizes == null) return null;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        ArrayList<Double> ratios = new ArrayList<>();
        ArrayList<Double> differences = new ArrayList<>();
        Point point = getDefaultDisplaySize(currentActivity, new Point());
        int targetHeight = Math.min(point.x, point.y);
        int totalHeight = point.y;
        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            ratios.add(ratio);
            differences.add(Math.abs(ratio - targetRatio));
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        // Cannot find the one match the aspect ratio. This should not happen.
        // Ignore the requirement.
        if (optimalSize == null) {
            Log.d(TAG, "No preview size match the aspect ratio");
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, optimalSize.width);
            int captureLayoutHeight = totalHeight - optimalSize.width - getStatusBarHeight();
            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (65 * scale + 0.5f);
            FrameLayout.LayoutParams linear_lp;
            if(captureLayoutHeight < 65) {
                bottombar_height = pixels;
                linear_lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, pixels);
            }else{
                bottombar_height = captureLayoutHeight;
                linear_lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, captureLayoutHeight);
            }
            linear_lp.gravity = Gravity.BOTTOM;
//            ((CameraActivity) parentActivity).getCaptureLinearLayout().setLayoutParams(linear_lp);
//            ((CameraActivity) parentActivity).getCaptureLinearLayout().setBackgroundColor(getResources().getColor(R.color.tut_background_light));
//            ((CameraActivity) parentActivity).getCameraFrameLayout().setLayoutParams(lp);
//            ((CameraActivity) parentActivity).getImageFunctionsLayout().setLayoutParams(linear_lp);
//            ((CameraActivity) parentActivity).getImageFunctionsLayout().setBackgroundColor(getResources().getColor(R.color.tut_background_light));
        }

        return optimalSize;
    }

    public int getBottombar_height(){
        return bottombar_height;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
