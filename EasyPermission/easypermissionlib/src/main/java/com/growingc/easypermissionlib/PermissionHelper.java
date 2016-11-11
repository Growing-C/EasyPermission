package com.growingc.easypermissionlib;

import android.Manifest;

/**
 * This class is for convenient use of different permission request.
 *
 *
 * Created by RB-cgy on 2016/11/11.
 */
public class PermissionHelper {
    public static final int REQUEST_CAMERA_PERMISSION = 1;//requestCode of camera permission operation,you can define it yourself.


    /**
     * convenient method to request Camera permission
     *
     * @param object should be instance of Activity or Fragment .
     * @see PermissionManager#doRequestPermissions(Object, int, String...)
     */
    public static void requestCameraPermission(Object object) {
        PermissionManager.doRequestPermissions(object, REQUEST_CAMERA_PERMISSION, new String[]{Manifest.permission.CAMERA});
    }
}
