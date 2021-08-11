package com.nokelock.nokelockbluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fitsleep.sunshinelibrary.utils.HexUtil;
import com.fitsleep.sunshinelibrary.utils.ToastUtils;
import com.fitsleep.sunshinelibrary.utils.ToolsUtils;
import com.nokelock.nokelockbluetooth.config.ActionConfig;
import com.nokelock.nokelockbluetooth.service.CommandService;
import com.nokelock.nokelockbluetooth.utils.OkHttpClientManager;
import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.Request;

public class LockManageActivity extends MPermissionsActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_battery)
    TextView tvBattery;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.tv_cz)
    TextView tvCz;
    @BindView(R.id.open_count)
    TextView openCount;
    @BindView(R.id.tv_gsm_id)
    TextView tvGsmId;
    @BindView(R.id.tv_gsm_version)
    TextView tvGsmVersion;
    @BindView(R.id.tv_lock_status)
    TextView tvLockStatus;
    @BindView(R.id.tv_lock_ip)
    TextView tvLockIp;
    @BindView(R.id.tv_demain_name)
    TextView tvDemainName;
    @BindView(R.id.tv_icc_id)
    TextView tvIccId;
    @BindView(R.id.bt_open)
    Button btOpen;
    @BindView(R.id.bt_auto)
    CheckBox btAuto;
    @BindView(R.id.bt_set_mode)
    Button btSetMode;
    @BindView(R.id.bt_lock_status)
    Button btLockStatus;
    @BindView(R.id.bt_get_gps_version)
    Button btGetGpsVersion;
    @BindView(R.id.bt_close)
    Button btClose;
    @BindView(R.id.bt_status)
    Button btStatus;
    @BindView(R.id.bt_demain)
    Button btDemain;
    @BindView(R.id.bt_lock_ip)
    Button btLockIp;
    @BindView(R.id.bt_iccid)
    Button btIccid;
    @BindView(R.id.app_version)
    TextView appVersion;
    @BindView(R.id.bt_shutdown)
    Button btShutdown;
    @BindView(R.id.bt_open_battery)
    Button btOpenBattery;
    @BindView(R.id.bt_close_battery)
    Button btCloseBattery;
    @BindView(R.id.bt_status_battery)
    Button btStatusBattery;
    private StringBuffer stringBuffer;
    private BluetoothDevice mac;
    private int count = 0;
    private boolean GPRS_TX = false;
    private int modeType = 0; // 0 正常  1 运输模式  2 重启  3关机
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_manage);
        ButterKnife.bind(this);
        stringBuffer = new StringBuffer();
        registerReceiver(broadcastReceiver, Config.initFilter());
        initBLE();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }

        if (comm != null) {
            unbindService(comm);
            comm = null;
        }

        App.getInstance().getBleManager().close();
    }

    private void initBLE() {
        appVersion.setText("Version：" + ToolsUtils.getVersion(getApplicationContext()));
        if (!App.getInstance().getBleManager().isEnable()) {
            App.getInstance().getBleManager().enableBluetooth();
        }
        bindService(new Intent(this, CommandService.class), comm, Context.BIND_AUTO_CREATE);
        mac = getIntent().getParcelableExtra("mac");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActionConfig.REFRESH_ADDRESS);
                intent.putExtra("mac", mac);
                sendBroadcast(intent);
            }
        }, 500);
    }

    private ServiceConnection comm = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            switch (intent.getAction()) {
                case Config.TOKEN_ACTION:
                    ((TextView) findViewById(R.id.tv_version)).setText(getString(R.string.device_version_number) + GlobalParameterUtils.getInstance().getVersion());
                    sendBroadcast(new Intent(ActionConfig.BYTTERY_ACTION));
                    break;
                case Config.BATTERY_ACTION:
                    if (TextUtils.isEmpty(data)) {
                        tvCz.setText(R.string.battery_fail);
                    } else {
                        tvCz.setText(R.string.battery_success);
                        String[] split = data.split(",");
                        if (split.length == 2) {
                            tvBattery.setText(getString(R.string.battery) + Integer.parseInt(split[0], 16) + "," + Integer.parseInt(split[1], 16));
                        } else if (split.length == 4) {
                            String voltage = split[3] + split[2];
                            tvBattery.setText(getString(R.string.battery) + Integer.parseInt(split[0], 16) + "," + Integer.parseInt(split[1], 16) + "\n当前电压：" + Integer.parseInt(voltage, 16));
                        } else {
                            tvBattery.setText(getString(R.string.battery) + Integer.parseInt(data, 16));
                        }
                    }
                    sendBroadcast(new Intent(ActionConfig.LOCK_GSM));
                    break;
                case Config.OPEN_ACTION:
                    if (TextUtils.isEmpty(data)) {
                        tvCz.setText(R.string.open_fail);
                    } else {
                        count++;
                        tvCz.setText(R.string.open_success);
                        openCount.setText(getString(R.string.open_count) + count);
                    }
                    break;
                case Config.LOCK_STATUS_ACTION:
                    if (TextUtils.isEmpty(data)) {
                        tvCz.setText(R.string.closeed);
                    } else {
                        tvCz.setText(R.string.opened);
                    }
                    break;
                case Config.LOCK_RESULT:
                    if (TextUtils.isEmpty(data)) {
                        tvCz.setText(R.string.Lock_timeout);
                    } else {
                        tvCz.setText(R.string.Lock_success);
                        if (btAuto.isChecked()) {
                            (new Handler()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    sendBroadcast(new Intent(ActionConfig.OPEN_ACTION));
                                }
                            }, 10000);
                        }
                    }
                    break;
                case Config.CLOSE_ACTION:
                    if (TextUtils.isEmpty(data)) {
                        tvCz.setText(R.string.reset_fail);
                    } else {
                        tvCz.setText(R.string.reset_lock);

                    }
                    break;
                case Config.GSM_ID_ACTION:
                    if (!TextUtils.isEmpty(data)) {
                        tvGsmId.setText("GSM ID：" + data);
                        sendBroadcast(new Intent(ActionConfig.LOCK_GSM_VERSION));
                    }
                    break;
                case Config.GSM_VERSION_ACTION:
                    if (!TextUtils.isEmpty(data)) {
                        tvGsmVersion.setText(getString(R.string.gsm_version) + HexUtil.convertHexToString(data));
                    }
                    break;
                case Config.BLE_DATA:
                    ToastUtils.showMessage("token error");
                    break;
                case Config.UPDATE_VIEW:
                    handlerViewData(data);
                    break;
                case Config.GEt_LOCK_WORK_STATUS:
                    gsmInfo(data);
                    if (btAuto.isChecked()) {
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sendBroadcast(new Intent(ActionConfig.OPEN_ACTION));
                            }
                        }, 1000);
                    }
                    break;
                case Config.SET_MODE:
                    if (TextUtils.isEmpty(data)) {
                        if (modeType == 3) {
                            tvCz.setText("当前操作：设置关机失败");
                            return;
                        } else if (modeType == 2) {
                            tvCz.setText("当前操作：设置重启失败");
                            return;
                        }
                        tvCz.setText(R.string.mode_fail);
                    } else {
                        if (modeType == 3) {
                            tvCz.setText("当前操作：设置关机成功");
                            return;
                        } else if (modeType == 2) {
                            tvCz.setText("当前操作：设置重启成功");
                            return;
                        }
                        tvCz.setText(R.string.mode_ok);
                    }
                    break;
                case Config.GET_MODE:
                    if (TextUtils.isEmpty(data)) {
                        tvMode.setText("工作模式：运输模式");
                    } else {
                        tvMode.setText("工作模式：正常模式");
                    }

                    break;
                case Config.GET_ICC_ID:
                    int len3 = HexStringToInt(data.substring(4, 6));
                    String idStr = data.substring(6, 6 + 2 * len3);
                    tvIccId.setText("ICCID：" + idStr);
                    break;
                case Config.GET_LOCKIP:
                    StringBuffer sbu = new StringBuffer();
                    sbu.append("IP：");
                    for (int i = 0; i < 4; i++) {
                        String str = data.substring(6 + i * 2, 8 + i * 2);
                        int a = HexStringToInt(str);
                        if (i == 0) {
                            sbu.append(Integer.toString(a));
                        } else {
                            sbu.append(".");
                            sbu.append(Integer.toString(a));
                        }
                    }
                    tvLockIp.setText(sbu.toString());
                    break;
                case Config.GET_DEMAINNAME_FIRST:
                    Log.e("2222222", data);
                    int len = HexStringToInt(data.substring(4, 6));
                    StringBuffer daminSbu = new StringBuffer();
                    daminSbu.append(getString(R.string.lock_damin));
                    for (int i = 0; i < len; i++) {
                        String str = data.substring(6 + i * 2, 8 + i * 2);
                        int a = Integer.parseInt(str, 16);
                        if (i == 0) {
                            daminSbu.append((char) a);
                        } else {
                            daminSbu.append((char) a);
                        }
                    }
                    tvDemainName.setText(daminSbu.toString());
                    break;
                case Config.GET_DEMAINNAME_SECOND:
                    Log.e("2222222", data);
                    int len2 = HexStringToInt(data.substring(4, 6));
                    StringBuffer daminSbu2 = new StringBuffer();
                    for (int i = 0; i < len2; i++) {
                        String str = data.substring(6 + i * 2, 8 + i * 2);
                        int a = HexStringToInt(str);
                        daminSbu2.append((char) a);
                    }
                    tvDemainName.setText(tvDemainName.getText() + daminSbu2.toString());
                    break;
                case Config.RESULT_STATUS:
                    if (TextUtils.isEmpty(data)) {
                        ToastUtils.showMessage("设置还车状态失败");
                        tvCz.setText("当前操作：还车指令失败");
                    } else {
                        ToastUtils.showMessage("设置还车状态成功");
                        tvCz.setText("当前操作：还车指令成功");
                    }
                    break;
                case Config.ALERT_ACTION:
                    if (TextUtils.isEmpty(data)) {
                        tvCz.setText("当前操作：强制报警失败");
                    } else {
                        tvCz.setText("当前操作：强制报警成功");
                    }
                    break;
                case Config.DATA_PULL:
                    tvCz.setText("当前操作：透传成功" + data);
                    break;
                case Config.RF_ID:
                    if (!TextUtils.isEmpty(data)) {
                        tvName.setText("RFID:" + data);

                        try {
                            postServer(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Config.OPEN_BATTERY_ACTION:
                    if (!TextUtils.isEmpty(data)){
                        tvCz.setText("当前操作：打开电池仓成功");
                    }else {
                        tvCz.setText("当前操作：打开电池仓失败");
                    }
                    break;
                case Config.CLOSE_BATTERY_ACTION:
                    if (!TextUtils.isEmpty(data)){
                        tvCz.setText("当前操作：关闭电池仓成功");
                    }else {
                        tvCz.setText("当前操作：关闭电池仓失败");
                    }
                    break;
                case Config.STATUS_BATTERY_ACTION:
                    if (!TextUtils.isEmpty(data)){
                        tvCz.setText("当前操作：电池仓已开启");
                    }else {
                        tvCz.setText("当前操作：电池仓已关闭");
                    }
                    break;
                case Config.FIND_CAR:
                    if (!TextUtils.isEmpty(data)){
                        tvCz.setText("当前操作：寻车指令发送成功");
                    }else {
                        tvCz.setText("当前操作：寻车指令发送失败");
                    }
                    break;
                case Config.SET_CONNECTION_MODE:
                    if (TextUtils.isEmpty(data)) {
                        tvCz.setText("当前操作：连接模式设置成功");
                    } else {
                        tvCz.setText("当前操作：连接模式设置失败");
                    }
                    break;

            }
        }
    };

    /**
     * 上传给服务器
     *
     * @param data
     */
    private void postServer(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", data);
        jsonObject.put("userid", "userid");
        OkHttpClientManager.postJson("http://120.24.160.3:8888", new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("onFailure", "onFailure: ", e);
            }

            @Override
            public void onResponse(String response) {
                Log.d("onResponse", "postServer: " + response);
            }
        }, jsonObject);
    }

    private int HexStringToInt(String hexString) {
        return Integer.parseInt(hexString, 16);
    }

    /**
     * 搜索连接
     *
     * @param status 状态码
     */
    private void handlerViewData(final String status) {
        switch (status) {
            case "-2"://连接失败
                tvCz.setText(R.string.connect_fail);
                tvStatus.setText(R.string.disconnect_dk);
                break;
            case "-1"://未找到设备
                tvCz.setText(R.string.no_search);
                break;
            case "0"://找到设备
                tvCz.setText(R.string.search_device);
                break;
            case "1"://发起连接
                tvAddress.setText("MAC：" + mac.getAddress());
                tvCz.setText(R.string.start_connect);
                break;
            case "2"://连接成功
                tvCz.setText(R.string.connect_ok);
                tvStatus.setText(R.string.connected);
                break;
            case "3"://开始搜索
                tvCz.setText(R.string.started_serach);
                break;
        }

    }

    /**
     * gsm信息
     *
     * @param data 数据
     */
    private void gsmInfo(String data) {
        Log.e(LockManageActivity.class.getSimpleName(), data);
        if (!TextUtils.isEmpty(data) && data.length() % 2 == 0) {
            stringBuffer.setLength(0);
            //052208R1R2R3R4R5R6R7R8
            byte[] bytes = HexUtil.hexStringToBytes(data);
            String tString = Integer.toBinaryString((bytes[3] & 0xFF) + 0x100).substring(1);
            String lockStatus = tString.substring(tString.length() - 1, tString.length()).equals("0") ? getString(R.string.open) : getString(R.string.close);
            String zd = tString.substring(tString.length() - 2, tString.length() - 1).equals("0") ? getString(R.string.normal) : getString(R.string.malfunction);
            String zdStatus = tString.substring(tString.length() - 3, tString.length() - 2).equals("0") ? getString(R.string.still) : getString(R.string.vibration);
            String cdStatus = tString.substring(tString.length() - 4, tString.length() - 3).equals("0") ? getString(R.string.discharge) : getString(R.string.charge);
            stringBuffer.append(getString(R.string.word_status_and_lock_status) + lockStatus + getString(R.string.vibration_function) + zd + getString(R.string.vibration_status) + zdStatus + getString(R.string.Charge_and_discharge_state) + cdStatus);
            int r2 = Integer.parseInt(data.substring(8, 10), 16);
            stringBuffer.append(getString(R.string.enter_battery) + r2);
            int r3 = Integer.parseInt(data.substring(10, 12), 16);
            switch (r3) {
                case 0:
                    GPRS_TX = false;
                    stringBuffer.append(getString(R.string.gsm_state_off_power));
                    break;
                case 1:
                    GPRS_TX = false;
                    stringBuffer.append(getString(R.string.gsm_state_search_SIM));
                    break;
                case 2:
                    GPRS_TX = false;
                    stringBuffer.append(getString(R.string.gsm_state_register));
                    break;
                case 3:
                    GPRS_TX = false;
                    stringBuffer.append(getString(R.string.gsm_state_init_message));
                    break;
                case 4:
                    GPRS_TX = false;
                    stringBuffer.append(getString(R.string.gsm_state_search_gprs));
                    break;
                case 5:
                    GPRS_TX = false;
                    stringBuffer.append(getString(R.string.gsm_state_connect_ppp));
                    break;
                case 6:
                    GPRS_TX = false;
                    stringBuffer.append(getString(R.string.gsm_state_connect_tcp));
                    break;
                case 7:
                    GPRS_TX = true;
                    stringBuffer.append(getString(R.string.gsm_state_communication));
                    break;
                case 254:
                    GPRS_TX = false;
                    stringBuffer.append(getString(R.string.gsm_state_power_on));
                    break;
                case 255:
                    GPRS_TX = false;
                    stringBuffer.append(getString(R.string.gsm_state_malfunction));
                    break;
            }
            int r4 = Integer.parseInt(data.substring(12, 14), 16);
            int r5 = Integer.parseInt(data.substring(14, 16), 16);
            stringBuffer.append(getString(R.string.gprs_on_line_time) + r4 + getString(R.string.gprs_signal_strength) + r5);
            int r6 = Integer.parseInt(data.substring(16, 18), 16);
            switch (r6) {
                case 0:
                    stringBuffer.append(getString(R.string.gps_state_off_power));
                    break;
                case 1:
                    stringBuffer.append(getString(R.string.gps_state_unlocation));
                    break;
                case 2:
                    stringBuffer.append(getString(R.string.gps_state_location));
                    break;
                case 255:
                    stringBuffer.append(getString(R.string.gps_state_malfunction));
                    break;
            }
            int r7 = Integer.parseInt(data.substring(18, 20), 16);
            int r8 = Integer.parseInt(data.substring(20, 22), 16);
            stringBuffer.append(getString(R.string.gps_location_time) + r7 + getString(R.string.gps_get_star_number) + r8);
            tvLockStatus.setText(stringBuffer.toString());

        }
    }


    @OnClick(R.id.bt_open)
    void open() {
        Random random = new Random();
        int b1 = random.nextInt(99);
        int b2 = random.nextInt(99);
        int b3 = random.nextInt(99);
        String b1Hex = Integer.toHexString(b1);
        String b2Hex = Integer.toHexString(b2);
        String b3Hex = Integer.toHexString(b3);
        String number = b1Hex + b2Hex + b3Hex;
        tvName.setText("流水号：" + number);
        App.getInstance().getBleManager().openLock(new byte[]{(byte) b1, (byte) b2, (byte) b3});
    }

    @OnClick(R.id.bt_close)
    void close() {
        sendBroadcast(new Intent(ActionConfig.RESET_LOCK));
    }

    @OnClick(R.id.bt_status)
    void status() {
        sendBroadcast(new Intent(ActionConfig.LOCK_STATUS));
    }

    @OnClick(R.id.bt_lock_status)
    void setBtLockStatus() {
        sendBroadcast(new Intent(ActionConfig.LOCK_GSM));
    }

    @OnClick(R.id.bt_get_gps_version)
    void getGSM() {
        sendBroadcast(new Intent(ActionConfig.LOCK_GSM_ID));
    }

    @OnClick(R.id.bt_set_mode)
    void setMode() {
        modeType = 1;
        sendBroadcast(new Intent(ActionConfig.SET_MODE));
    }

    @OnClick(R.id.bt_stop)
    void stop() {
        sendBroadcast(new Intent(ActionConfig.STOP));
    }

    @OnClick(R.id.bt_iccid)
    void iccid() {
        sendBroadcast(new Intent(ActionConfig.ICCID));
    }

    @OnClick(R.id.bt_demain)
    void deMain() {
        sendBroadcast(new Intent(ActionConfig.DEMAIN));
    }

    @OnClick(R.id.bt_lock_ip)
    void lockIp() {
        sendBroadcast(new Intent(ActionConfig.LOCK_IP));
    }

    @OnClick(R.id.bt_shutdown)
    public void onViewClicked() {
        modeType = 3;
        sendBroadcast(new Intent(ActionConfig.SET_SHUTDOWN_MODE));
    }

    @OnClick(R.id.bt_reset)
    public void onBtResetClicked() {
        modeType = 2;
        sendBroadcast(new Intent(ActionConfig.SET_RESTART_MODE));
    }

    @OnClick(R.id.bt_query)
    public void onBtQueryClicked() {
        sendBroadcast(new Intent(ActionConfig.GET_MODE));
    }

    @OnClick(R.id.bt_pull)
    void pull() {
        byte[] bytes = new byte[16];
        bytes[0] = 0x54;
        for (int i = 0; i < bytes.length - 1; i++) {
            bytes[i + 1] = (byte) (i + 1);
        }
        App.getInstance().getBleManager().writeByte(bytes);
    }

    @OnClick(R.id.bt_find_car)
    void findCar(){
        App.getInstance().getBleManager().findCar();
    }

    @OnClick(R.id.bt_alert)
    void alert() {
        byte b = (byte) new Random().nextInt(99);
        App.getInstance().getBleManager().alert(b);
    }

    @OnClick({R.id.bt_open_battery, R.id.bt_close_battery, R.id.bt_status_battery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_open_battery:
                App.getInstance().getBleManager().openBatteryBox();
                break;
            case R.id.bt_close_battery:
                App.getInstance().getBleManager().closeBatteryBox();
                break;
            case R.id.bt_status_battery:
                App.getInstance().getBleManager().statusBatteryBox();
                break;
        }
    }

    @OnClick({R.id.bt_connection_mode_long, R.id.bt_connection_mode_short})
    public void connectionModeChange(View view) {
        switch (view.getId()) {
            case R.id.bt_connection_mode_long:
                App.getInstance().getBleManager().setConnectionMode(1);
                break;
            case R.id.bt_connection_mode_short:
                App.getInstance().getBleManager().setConnectionMode(0);
                break;
        }
    }
}
