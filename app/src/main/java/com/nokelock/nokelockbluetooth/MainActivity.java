package com.nokelock.nokelockbluetooth;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitsleep.sunshinelibrary.inter.OnItemAdapterClick;
import com.fitsleep.sunshinelibrary.utils.EncryptUtils;
import com.fitsleep.sunshinelibrary.utils.HexUtil;
import com.nokelock.nokelockbluetooth.adapter.DeviceListAdapter;
import com.nokelock.nokelockbluetooth.bean.CheckVersionBean;
import com.nokelock.nokelockbluetooth.bean.DeviceBean;
import com.nokelock.nokelockbluetooth.bean.VersionBean;
import com.nokelock.nokelockbluetooth.config.ApiManage;
import com.nokelock.nokelockbluetooth.utils.ToolsUtils;
import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.inter.OnDeviceSearchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends MPermissionsActivity implements OnItemAdapterClick {

    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.bt_scan)
    TextView btScan;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.app_version)
    TextView appVersion;
    Context context;
    private DeviceListAdapter deviceListAdapter;
    private List<DeviceBean> deviceBeanList = new ArrayList<>();
    private DeviceListAdapter adapter;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    adapter.updateData(deviceBeanList);
                    adapter.setOnItemClickListener(MainActivity.this);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        ButterKnife.bind(this);
        appVersion.setText("Version???" + ToolsUtils.getVersion(getApplicationContext()));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new DeviceListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        if (!App.getInstance().getBleManager().isEnable()) {
            App.getInstance().getBleManager().enableBluetooth();
        }
        refresh();
        byte[] encrypt = EncryptUtils.Encrypt(Config.yx_key, new byte[]{0x06, 0x01, 0x01, 0x01, 0x2D, 0x1A, 0x68, 0x3D, 0x48, 0x27, 0x1A, 0x18, 0x31, 0x6E, 0x47, 0x1A});
        System.out.println(":====:" + HexUtil.encodeHexStr(encrypt));
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                checkVersion();
            }
        }, 2000);

    }

    private void checkVersion() {


        Log.e("AppInfo", getAppInfo());
        String packName = getAppInfo();

        ApiManage.getInstance().checkVersionService().checkVersion(new CheckVersionBean(packName))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<VersionBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.toString());
                    }

                    @Override
                    public void onNext(final VersionBean versionBean) {
                        Log.e("versionBean", versionBean.toString());
                        String appVersion = ToolsUtils.getVersion(getApplicationContext());

                        if (appVersion.compareTo(versionBean.getVersion()) < 0) {
                            if (versionBean.getUrl().isEmpty()) {
                                return;
                            }

                            new AlertDialog.Builder(MainActivity.this).setTitle("??????")//?????????????????????

                                    .setMessage("???????????????" + versionBean.getVersion() + "???????????????????????????")//?????????????????????

                                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {//??????????????????
                                        @Override

                                        public void onClick(DialogInterface dialog, int which) {//???????????????????????????

                                            // TODO Auto-generated method stub
                                            downApk(versionBean.getUrl());
                                        }

                                    }).setNegativeButton("??????", new DialogInterface.OnClickListener() {//??????????????????

                                @Override
                                public void onClick(DialogInterface dialog, int which) {//????????????

                                    // TODO Auto-generated method stub

                                    Log.i("alertdialog", " ??????????????????");

                                }

                            }).show();

                        }

                    }
                });
    }

    private String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = this.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return pkName;
        } catch (Exception e) {
        }
        return null;
    }

//    @OnClick(R.id.bt_scan)
//    void scan() {
////        if (uid == null || "".equals(uid) || access_token == null || "".equals(access_token)){
////            UIHelper.goToAct(context,LoginActivity.class);
////            Toast.makeText(context,"??????????????????",Toast.LENGTH_SHORT).show();
////        }else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                int checkPermission = checkSelfPermission(Manifest.permission.CAMERA);
//                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
//                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                        requestPermissions(new String[] { Manifest.permission.CAMERA }, 100);
//                    } else {
//                        CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
//                        customBuilder.setTitle("????????????").setMessage("??????????????????????????????????????????")
//                                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                }).setPositiveButton("??????", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                                requestPermissions(new String[] { Manifest.permission.CAMERA }, 100);
//                            }
//                        });
//                        customBuilder.create().show();
//                    }
//                    return;
//                }
//            }
//            try {
//                Intent intent = new Intent();
//                intent.setClass(context, ScanCaptureAct.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("isChangeKey",false);
//                startActivityForResult(intent, 101);
//            } catch (Exception e) {
////                UIHelper.showToastMsg(context, "??????????????????,????????????????????????????????????", R.drawable.ic_error);
//                Toast.makeText(context,"??????????????????,????????????????????????????????????",Toast.LENGTH_SHORT).show();
//            }
////        }
//    }

    /**
     * ??????apk
     *
     * @param url ??????url
     */
    private void downApk(final String url) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //????????????????????????
        request.setDestinationInExternalPublicDir("NokeLockCach", url.substring(url.lastIndexOf("/") + 1));
//        //?????????????????????
//        request.setVisibleInDownloadsUi(true);
//        //???????????????????????????  ????????????<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //????????????
        long downloadId = downloadManager.enqueue(request);

    }

    @OnClick(R.id.bt_refresh)
    void refresh() {
        requestPermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
    }

    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if (requestCode == 100) {
            scanDevice();
        }
    }

    private void scanDevice() {
        deviceBeanList.clear();
        progressDialog = ProgressDialog.show(MainActivity.this, "", "loading...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                tvScan.setText(getString(R.string.scan_device) + deviceBeanList.size());
                App.getInstance().getBleManager().stopScan();
            }
        }, 3000);

        App.getInstance().getBleManager().startScan(new OnDeviceSearchListener() {
            @Override
            public void onScanDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
//                byte[] bytes = ParseLeAdvData.adv_report_parse(ParseLeAdvData.BLE_GAP_AD_TYPE_MANUFACTURER_SPECIFIC_DATA, scanRecord);
//                if (bytes==null)return;
//                if (bytes.length<2)return;
//                if (bytes[0] == 0x01 && bytes[1] == 0x02) {
//
//                }
                deviceBeanList.add(new DeviceBean(device, rssi));
                handler.sendEmptyMessage(0);
            }
        });

    }

    @Override
    public void onItemClick(Object o, BluetoothDevice device) {
        Toast.makeText(this, device.getAddress(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LockManageActivity.class);
//        Intent intent = new Intent(this, Main2Activity.class);    //??????
        intent.putExtra("mac", device);
        startActivity(intent);

    }
}
