package com.sunshine.blelibrary.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.fitsleep.sunshinelibrary.utils.ConvertUtils;
import com.fitsleep.sunshinelibrary.utils.EncryptUtils;
import com.fitsleep.sunshinelibrary.utils.Logger;
import com.fitsleep.sunshinelibrary.utils.ToastUtils;
import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.dispose.impl.AQ;
import com.sunshine.blelibrary.dispose.impl.Alert;
import com.sunshine.blelibrary.dispose.impl.Battery;
import com.sunshine.blelibrary.dispose.impl.CloseBatteryBox;
import com.sunshine.blelibrary.dispose.impl.CloseLock;
import com.sunshine.blelibrary.dispose.impl.DataPull;
import com.sunshine.blelibrary.dispose.impl.GetDemainNameFirst;
import com.sunshine.blelibrary.dispose.impl.GetDemainNameSecond;
import com.sunshine.blelibrary.dispose.impl.GetGSMId;
import com.sunshine.blelibrary.dispose.impl.GetGSMVersion;
import com.sunshine.blelibrary.dispose.impl.GetICCID;
import com.sunshine.blelibrary.dispose.impl.GetLockIP;
import com.sunshine.blelibrary.dispose.impl.GetLockWorkStatusHandler;
import com.sunshine.blelibrary.dispose.impl.GetMode;
import com.sunshine.blelibrary.dispose.impl.LockResult;
import com.sunshine.blelibrary.dispose.impl.LockStatus;
import com.sunshine.blelibrary.dispose.impl.OpenBatteryBox;
import com.sunshine.blelibrary.dispose.impl.OpenLock;
import com.sunshine.blelibrary.dispose.impl.Password;
import com.sunshine.blelibrary.dispose.impl.RFTest;
import com.sunshine.blelibrary.dispose.impl.ReplacementBattery;
import com.sunshine.blelibrary.dispose.impl.ResultStatusHanlder;
import com.sunshine.blelibrary.dispose.impl.SetConnectionMode;
import com.sunshine.blelibrary.dispose.impl.SetMode;
import com.sunshine.blelibrary.dispose.impl.StatusBattery;
import com.sunshine.blelibrary.dispose.impl.TY;
import com.sunshine.blelibrary.dispose.impl.Token;
import com.sunshine.blelibrary.dispose.impl.UpdateName;
import com.sunshine.blelibrary.dispose.impl.UpdateVersion;
import com.sunshine.blelibrary.dispose.impl.UpdateWake;
import com.sunshine.blelibrary.inter.IBLE;
import com.sunshine.blelibrary.inter.OnConnectionListener;
import com.sunshine.blelibrary.inter.OnDeviceSearchListener;
import com.sunshine.blelibrary.mode.AlertTxOrder;
import com.sunshine.blelibrary.mode.BatteryTxOrder;
import com.sunshine.blelibrary.mode.CloseBatteryTxOrder;
import com.sunshine.blelibrary.mode.DataPullTxOrder;
import com.sunshine.blelibrary.mode.FindCarTxOrder;
import com.sunshine.blelibrary.mode.GetDemainNameTxOrder;
import com.sunshine.blelibrary.mode.GetGSMIdTxOrder;
import com.sunshine.blelibrary.mode.GetGSMVersionTxOrder;
import com.sunshine.blelibrary.mode.GetICCIDTxOrder;
import com.sunshine.blelibrary.mode.GetIPTxOrder;
import com.sunshine.blelibrary.mode.GetLockStatusTxOrder;
import com.sunshine.blelibrary.mode.GetLockWorkStatus;
import com.sunshine.blelibrary.mode.GetModeTxOrder;
import com.sunshine.blelibrary.mode.GetTokenTxOrder;
import com.sunshine.blelibrary.mode.OpenBatteryTxOrder;
import com.sunshine.blelibrary.mode.OpenLockTxOrder;
import com.sunshine.blelibrary.mode.RFTestTxOrder;
import com.sunshine.blelibrary.mode.ReplacementBatteryTxOrder;
import com.sunshine.blelibrary.mode.ResetAQTxOrder;
import com.sunshine.blelibrary.mode.ResultStatusTxOrder;
import com.sunshine.blelibrary.mode.SetConnectionModeTxOrder;
import com.sunshine.blelibrary.mode.SetModeTxOrder;
import com.sunshine.blelibrary.mode.StatusBatteryTxOrder;
import com.sunshine.blelibrary.mode.TxOrder;
import com.sunshine.blelibrary.mode.UpdateWakeTxOrder;
import com.sunshine.blelibrary.mode.resetLockTxOrder;
import com.sunshine.blelibrary.utils.GlobalParameterUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.fitsleep.sunshinelibrary.utils.EncryptUtils.Encrypt;

/**
 * 作者：LiZhao
 * 时间：2017.2.8 11:48
 * 邮箱：44493547@qq.com
 * 备注：
 */
public class AndroidBle implements IBLE {

    private static final String TAG = AndroidBle.class.getSimpleName();
    private Context context;
    private final BluetoothAdapter mBluetoothAdapter;
    private OnDeviceSearchListener mOnDeviceSearchListener;
    private BluetoothGatt mBluetoothGatt;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private OnConnectionListener mOnConnectionListener;
    private BluetoothGattCharacteristic read_characteristic;
    private BluetoothGattCharacteristic write_characteristic;
    private List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
    private Token mToken;

    private BluetoothGattCharacteristic OAD_READ;
    private BluetoothGattCharacteristic OAD_WRITE;

    public AndroidBle(Context context) {
        this.context = context;
        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            return;
        }
        GlobalParameterUtils.getInstance().setContext(context.getApplicationContext());
        mToken = new Token();
        Battery battery = new Battery();
        OpenLock openLock = new OpenLock();
        TY ty = new TY();
        CloseLock closeLock = new CloseLock();
        LockStatus lockStatus = new LockStatus();
        Password password = new Password();
        LockResult lockResult = new LockResult();
        AQ aq = new AQ();
        UpdateVersion updateVersion = new UpdateVersion();
        GetMode getMode = new GetMode();
        SetMode setMode = new SetMode();
        GetLockWorkStatusHandler getLockWorkStatusHandler = new GetLockWorkStatusHandler();
        GetGSMId getGSMId = new GetGSMId();
        GetGSMVersion getGSMVersion = new GetGSMVersion();
        UpdateName updateName = new UpdateName();
        GetICCID getICCID = new GetICCID();
        GetLockIP getLockIP = new GetLockIP();
        GetDemainNameFirst demainNameFirst = new GetDemainNameFirst();
        GetDemainNameSecond demainNameSecond = new GetDemainNameSecond();
        RFTest rfTest = new RFTest();
        ResultStatusHanlder resultStatusHanlder = new ResultStatusHanlder();
        Alert alert = new Alert();
        DataPull dataPull = new DataPull();
        OpenBatteryBox openBatteryBox = new OpenBatteryBox();
        CloseBatteryBox closeBatteryBox = new CloseBatteryBox();
        StatusBattery statusBattery = new StatusBattery();
        UpdateWake updateWake = new UpdateWake();
        ReplacementBattery replacementBattery = new ReplacementBattery();
        SetConnectionMode setConnectionMode = new SetConnectionMode();

        mToken.nextHandler = battery;
        battery.nextHandler = openLock;
        openLock.nextHandler = ty;
        ty.nextHandler = closeLock;
        closeLock.nextHandler = lockStatus;
        lockStatus.nextHandler = password;
        password.nextHandler = lockResult;
        lockResult.nextHandler = aq;
        aq.nextHandler = updateVersion;
        updateVersion.nextHandler = getMode;
        getMode.nextHandler = setMode;
        setMode.nextHandler = getLockWorkStatusHandler;
        getLockWorkStatusHandler.nextHandler = getGSMId;
        getGSMId.nextHandler = getGSMVersion;
        getGSMVersion.nextHandler = updateName;
        updateName.nextHandler = getLockIP;
        getLockIP.nextHandler = demainNameFirst;
        demainNameFirst.nextHandler = demainNameSecond;
        demainNameSecond.nextHandler = getICCID;
        getICCID.nextHandler = rfTest;
        rfTest.nextHandler = resultStatusHanlder;
        resultStatusHanlder.nextHandler = alert;
        alert.nextHandler = dataPull;
        dataPull.nextHandler = openBatteryBox;
        openBatteryBox.nextHandler = closeBatteryBox;
        closeBatteryBox.nextHandler = statusBattery;
        statusBattery.nextHandler = updateWake;
        updateWake.nextHandler = replacementBattery;
        replacementBattery.nextHandler = setConnectionMode;
    }

    @Override
    public boolean enableBluetooth() {
        return mBluetoothAdapter.enable();
    }

    @Override
    public boolean disableBluetooth() {
        return mBluetoothAdapter.disable();
    }

    @Override
    public boolean isEnable() {
        return mBluetoothAdapter.isEnabled();
    }

    @Override
    public void startScan(OnDeviceSearchListener onDeviceSearchListener) {
        bluetoothDeviceList.clear();
        if (mBluetoothAdapter == null) return;
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        this.mOnDeviceSearchListener = onDeviceSearchListener;
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    @Override
    public void stopScan() {
        if (mBluetoothAdapter == null) return;
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    @Override
    public boolean connect(String address, OnConnectionListener onConnectionListener) {
        if (TextUtils.isEmpty(address) || mBluetoothAdapter == null || null == onConnectionListener)
            return false;
        this.mOnConnectionListener = onConnectionListener;

        BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
        if (null == bluetoothDevice) {
            return false;
        }
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        mBluetoothGatt = bluetoothDevice.connectGatt(context, false, mBluetoothGattCallback);
        return true;
    }

    @Override
    public boolean connectDevice(BluetoothDevice device, OnConnectionListener onConnectionListener) {
        if (device == null || onConnectionListener == null) return false;
        this.mOnConnectionListener = onConnectionListener;
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        mBluetoothGatt = device.connectGatt(context, false, mBluetoothGattCallback);
        return true;
    }


    @Override
    public void writeByte(byte[] bytes) {
        if (mBluetoothGatt == null || write_characteristic == null) {
            return;
        }

        byte[] miwen = null;
        switch (GlobalParameterUtils.getInstance().getLockType()) {
            case MTS:
                miwen = Encrypt(bytes, Config.key);
                break;
            case YXS:
                miwen = Encrypt(bytes, Config.yx_key);
                break;
        }
        if (miwen != null) {
            write_characteristic.setValue(miwen);
            mBluetoothGatt.writeCharacteristic(write_characteristic);
            Logger.e(AndroidBle.class.getSimpleName(), ConvertUtils.bytes2HexString(bytes));
        }

    }

    @Override
    public boolean getToken() {
        return writeObject(new GetTokenTxOrder());
    }

    @Override
    public boolean getBattery() {
        return writeObject(new BatteryTxOrder());
    }

    @Override
    public boolean openLock(byte[] bytes) {
        return writeObject(new OpenLockTxOrder(bytes));
    }

    @Override
    public boolean resetLock() {
        return writeObject(new resetLockTxOrder());
    }

    @Override
    public boolean getLockStatus() {
        return writeObject(new GetLockStatusTxOrder());
    }

    @Override
    public boolean setPassword() {
        return false;
    }

    @Override
    public boolean setKey() {
        return false;
    }

    @Override
    public boolean getMode() {
        return writeObject(new GetModeTxOrder());
    }

    @Override
    public boolean getLockWorkStatus() {
        return writeObject(new GetLockWorkStatus());
    }

    @Override
    public boolean setMode(int position) {
        return writeObject(new SetModeTxOrder(position));
    }

    @Override
    public boolean resultStatus() {
        return writeObject(new ResultStatusTxOrder());
    }

    @Override
    public boolean getGSMVersion() {
        return writeObject(new GetGSMVersionTxOrder());
    }

    @Override
    public boolean getGSMId() {
        return writeObject(new GetGSMIdTxOrder());
    }

    @Override
    public boolean getLockIP() {
        return writeObject(new GetIPTxOrder());
    }

    @Override
    public boolean getDemainName() {
        return writeObject(new GetDemainNameTxOrder());
    }

    @Override
    public boolean getICCID() {
        return writeObject(new GetICCIDTxOrder());
    }

    @Override
    public boolean updateVersion() {
        return false;
    }

    @Override
    public boolean RFTest(int number) {
        return writeObject(new RFTestTxOrder(number));
    }

    @Override
    public boolean writeWrite(byte[] bytes) {
        if (mBluetoothGatt == null || OAD_WRITE == null) {
            return false;
        }
        OAD_WRITE.setValue(bytes);
        Logger.e(AndroidBle.class.getSimpleName(), ConvertUtils.bytes2HexString(bytes));
        return mBluetoothGatt.writeCharacteristic(OAD_WRITE);
    }

    @Override
    public boolean writeRead(byte[] bytes) {
        if (mBluetoothGatt == null || OAD_READ == null) {
            return false;
        }
        OAD_READ.setValue(bytes);
        Logger.e(AndroidBle.class.getSimpleName(), ConvertUtils.bytes2HexString(bytes));
        return mBluetoothGatt.writeCharacteristic(OAD_READ);
    }


    @Override
    public void resetPasswordAndAQ() {
        writeObject(new ResetAQTxOrder());
    }

    @Override
    public boolean alert(byte bytes) {
        return writeObject(new AlertTxOrder(bytes));
    }

    @Override
    public boolean dataPull(byte[] bytes) {
        return writeObject(new DataPullTxOrder(bytes));
    }

    @Override
    public boolean openBatteryBox() {
        return writeObject(new OpenBatteryTxOrder());
    }

    @Override
    public boolean closeBatteryBox() {
        return writeObject(new CloseBatteryTxOrder());
    }

    @Override
    public boolean statusBatteryBox() {
        return writeObject(new StatusBatteryTxOrder());
    }

    @Override
    public void disconnect() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.disconnect();

    }

    @Override
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;

    }

    @Override
    public boolean refreshCache() {
        if (mBluetoothGatt != null) {
            try {
                BluetoothGatt localBluetoothGatt = mBluetoothGatt;
                Method localMethod = localBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
                if (localMethod != null) {
                    boolean bool = ((Boolean) localMethod.invoke(localBluetoothGatt, new Object[0])).booleanValue();
                    return bool;
                }
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean findCar() {
        return writeObject(new FindCarTxOrder());
    }

    @Override
    public boolean updateWake(int num, int minute, int hour, int time) {
        return writeObject(new UpdateWakeTxOrder(num, minute, hour, time));
    }

    @Override
    public boolean ReplacementBattery() {
        return writeObject(new ReplacementBatteryTxOrder());

    }

    @Override
    public boolean setConnectionMode(int mode) {
        return writeObject(new SetConnectionModeTxOrder(mode));
    }

    @Override
    public void resetBluetoothAdapter() {
        mBluetoothAdapter.disable();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBluetoothAdapter.enable();
            }
        }, 2000);
    }


    /**
     * 写入指令
     *
     * @param txOrder 发送指令对象
     * @return 是否成功
     */
    private boolean writeObject(TxOrder txOrder) {
        if (mBluetoothGatt == null || write_characteristic == null) {
            return false;
        }

        byte[] miwen = null;
        switch (GlobalParameterUtils.getInstance().getLockType()) {
            case MTS:
                miwen = Encrypt(ConvertUtils.hexString2Bytes(txOrder.generateString()), Config.key);
                break;
            case YXS:
                miwen = Encrypt(ConvertUtils.hexString2Bytes(txOrder.generateString()), Config.yx_key);
                break;
        }
        if (miwen != null) {
            write_characteristic.setValue(miwen);
            Logger.e("发送：", txOrder.generateString());
            return mBluetoothGatt.writeCharacteristic(write_characteristic);
        }
        return false;
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            if (null != mOnDeviceSearchListener) {
                if (!bluetoothDeviceList.contains(device)) {
                    bluetoothDeviceList.add(device);
                    mOnDeviceSearchListener.onScanDevice(device, rssi, scanRecord);
                }
            }
        }
    };
    private BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (status == 133) {
                gatt.disconnect();
            }

            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    gatt.discoverServices();
                    if (null != mOnConnectionListener) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mOnConnectionListener.onConnect();
                            }
                        });
                    }
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    if (null != mOnConnectionListener) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mOnConnectionListener.onDisconnect(Config.DISCONNECT);
                            }
                        });
                    }
                    gatt.close();
                    break;
            }
        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (GlobalParameterUtils.getInstance().isUpdate()) {
                    BluetoothGattService service = gatt.getService(Config.OAD_SERVICE_UUID);
                    if (null != service) {
                        OAD_READ = service.getCharacteristic(Config.OAD_READ_UUID);
                        OAD_WRITE = service.getCharacteristic(Config.OAD_WRITE_UUID);
                        OAD_WRITE.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                    }
                } else {
                    BluetoothGattService service = gatt.getService(Config.bltServerUUID);
                    if (null != service) {
                        read_characteristic = service.getCharacteristic(Config.readDataUUID);
                        write_characteristic = service.getCharacteristic(Config.writeDataUUID);
                        int properties = read_characteristic.getProperties();
                        if ((properties | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            gatt.setCharacteristicNotification(read_characteristic, true);
                            BluetoothGattDescriptor descriptor = read_characteristic.getDescriptor(Config.CLIENT_CHARACTERISTIC_CONFIG);
                            if (null != descriptor) {
                                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                gatt.writeDescriptor(descriptor);
                            }
                        }
                    }
                }
                if (null != mOnConnectionListener) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mOnConnectionListener.onServicesDiscovered(TextUtils.isEmpty(gatt.getDevice().getName()) ? "null" : gatt.getDevice().getName(), gatt.getDevice().getAddress());
                        }
                    });
                }
            }
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            GlobalParameterUtils.getInstance().setBusy(false);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            GlobalParameterUtils.getInstance().setBusy(false);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (GlobalParameterUtils.getInstance().isUpdate()) {
                GlobalParameterUtils.getInstance().setBusy(false);
            } else {
                byte mingwen[] = null;
                try {
                    byte[] values = characteristic.getValue();
                    byte[] x = new byte[16];
                    System.arraycopy(values, 0, x, 0, 16);
                    switch (GlobalParameterUtils.getInstance().getLockType()) {
                        case MTS:
                            mingwen = EncryptUtils.Decrypt(x, Config.key);
                            break;
                        case YXS:
                            mingwen = EncryptUtils.Decrypt(x, Config.yx_key);
                            break;
                    }
                    mToken.handlerRequest(ConvertUtils.bytes2HexString(mingwen));
                    Logger.e(TAG, "返回：" + ConvertUtils.bytes2HexString(mingwen));
                } catch (Exception e) {
                    String hex = ConvertUtils.bytes2HexString(mingwen);
                    if (hex.startsWith("0524")) {
                        Logger.e(TAG, "0524返回：" + ConvertUtils.bytes2HexString(mingwen));
                        byte[] bytes = ConvertUtils.hexString2Bytes(hex);
                        int lenght = Integer.parseInt(hex.substring(4, 6), 16);
                        if (lenght > 13) {
                            lenght = 13;
                        }
                        String version = hex.substring(6, 6 + (lenght * 2));
                        GlobalParameterUtils.getInstance().sendBroadcast(Config.GSM_VERSION_ACTION, version);
                    } else if (hex.startsWith("04020100")) {
                        ToastUtils.showMessage("修改成功");
                    } else {
                        Logger.e(TAG, "没有该指令：" + ConvertUtils.bytes2HexString(mingwen));
                    }

                }
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            GlobalParameterUtils.getInstance().setBusy(false);
        }
    };

}
