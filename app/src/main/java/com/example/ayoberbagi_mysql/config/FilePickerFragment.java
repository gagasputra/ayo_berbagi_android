package com.example.ayoberbagi_mysql.config;

import android.Manifest;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.ayoberbagi_mysql.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class FilePickerFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!EasyPermissions.hasPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_read_external_storage), EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List perms) {
        // Add your logic here
    }

    @Override
    public void onPermissionsDenied(int requestCode, List perms) {
        // Add your logic here
    }
}