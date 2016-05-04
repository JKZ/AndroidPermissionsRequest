package me.jkz.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KevinZhong on 16/3/31.
 */
public class PermissionsUtils {

    public static String[] PERMISSIONS_NEED_CHECK = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
    };

    public static HashMap<String, Boolean> PERMISSIONS_CHECKED = new HashMap<String, Boolean>();

    /**
     * Check permissions are granted or not and request.
     *
     * @param activity Activity
     * @return true if not granted or need to request permissions
     */
    public static boolean checkAndRequestPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }

        boolean isNeeded = false;
        List<String> shouldShowRationalePermissions = new ArrayList<>();
        for (String p : PERMISSIONS_NEED_CHECK) {
            if (ActivityCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, p)) {
                    shouldShowRationalePermissions.add(p);
                }
                PERMISSIONS_CHECKED.put(p, false);
                isNeeded = true;
            } else {
                PERMISSIONS_CHECKED.put(p, true);
            }
        }

        ActivityCompat.requestPermissions(activity, PERMISSIONS_NEED_CHECK, 2);

        return isNeeded;
    }

    /**
     * Call this method when {@link Activity#onRequestPermissionsResult)
     *
     * @param requestCode  pass from {@link Activity#onRequestPermissionsResult)
     * @param permissions  pass from {@link Activity#onRequestPermissionsResult)
     * @param grantResults pass from {@link Activity#onRequestPermissionsResult)
     * @return true if all are granted
     */
    public static boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 2) {
            for (int i = 0; i < permissions.length; i++) {
                String p = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    PERMISSIONS_CHECKED.put(p, true);
                }
            }

            boolean isGranted = true;
            for (String key : PERMISSIONS_CHECKED.keySet()) {
                if (!PERMISSIONS_CHECKED.get(key)) {
                    isGranted = false;
                }
            }

            return isGranted;
        }
        return true;
    }
}
