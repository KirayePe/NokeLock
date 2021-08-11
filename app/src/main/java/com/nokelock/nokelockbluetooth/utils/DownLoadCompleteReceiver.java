package com.nokelock.nokelockbluetooth.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.nokelock.nokelockbluetooth.App;

import java.io.File;

/**
 * 下载监听广播
 * Created by sunshine on 2017/3/24.
 */

public class DownLoadCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            installApk(context, id);
//            ToastUtils.showMessage("最新安装版已经下载完成！");
            Toast.makeText(App.getInstance(),"最新安装版已经下载完成！", Toast.LENGTH_LONG ).show();
        } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
//            ToastUtils.showMessage("正在下载...");
            Toast.makeText(App.getInstance(),"正在下载...", Toast.LENGTH_LONG ).show();

        }
    }

    private void installApk(Context context, long downloadApkId) {
        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadFileUri = dManager.getUriForDownloadedFile(downloadApkId);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = null;
                contentUri = FileProvider.getUriForFile(ConditionsUtils.getContext(), App.getInstance().getPackageName()+ ".fileprovider", new File(getRealPathFromURI(context,downloadFileUri)));
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            ConditionsUtils.getContext().startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Context context,Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
