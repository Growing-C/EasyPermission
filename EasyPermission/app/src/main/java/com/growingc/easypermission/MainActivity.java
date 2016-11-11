package com.growingc.easypermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.growingc.easypermissionlib.PermissionDenied;
import com.growingc.easypermissionlib.PermissionDialog;
import com.growingc.easypermissionlib.PermissionGranted;
import com.growingc.easypermissionlib.PermissionHelper;
import com.growingc.easypermissionlib.PermissionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_camera://open camera with permission check
                //since camera permission is listed to be dangerous permission in android M,
                // we should request camera permission before we start the system camera.also compatible with android versions below 23

//                ---------------------normal use--------------------------
                //this seems inconvenient , but if you want to check permission yourself ,you can do this way
                if (PermissionChecker.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    PermissionHelper.requestCameraPermission(MainActivity.this);
                }
//                ------------------------end-------------------------

                //if you don't want to check permission yourself , just invoke one of the two methods below ,everything will be done in the api,
                // what you  should do is just do the action which need the permission in the method with an @ interface  ' @PermissionGranted'
//                PermissionManager.requestPermissions(MainActivity.this, PermissionHelper.REQUEST_CAMERA_PERMISSION, new String[]{Manifest.permission.CAMERA});
//                PermissionHelper.requestCameraPermission(MainActivity.this);//this method is more convenient ,isn't it?
                break;
            case R.id.open_camera1://open camera without permission check
                //if your app do not have camera permission, it may crash.
                openCamera();
                break;
            default:
                break;
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.onRequestPermissionsResult(MainActivity.this, requestCode, permissions, grantResults);
    }

    /**
     * called when camera permission is granted
     */
    @PermissionGranted(requestCode = PermissionHelper.REQUEST_CAMERA_PERMISSION)
    public void cameraPermissionGranted() {
        openCamera();
    }

    /**
     * called when camera permission is denied
     */
    @PermissionDenied(requestCode = PermissionHelper.REQUEST_CAMERA_PERMISSION)
    public void cameraPermissionDenied() {
        new PermissionDialog(MainActivity.this, getString(R.string.alert_dialog_msg_camera)).show();
    }
}
