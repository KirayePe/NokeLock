package com.nokelock.nokelockbluetooth.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nokelock.nokelockbluetooth.App;
import com.nokelock.nokelockbluetooth.config.ActionConfig;
import com.sunshine.blelibrary.config.Config;
import com.sunshine.blelibrary.inter.OnConnectionListener;

import java.util.concurrent.atomic.AtomicBoolean;

public class CommandService extends Service implements OnConnectionListener {

    private BluetoothDevice mac;
    private AtomicBoolean hasFound = new AtomicBoolean(false);
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(commandReceiver, ActionConfig.initIntentFilter());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (commandReceiver!=null){
            unregisterReceiver(commandReceiver);
            commandReceiver = null;
        }
    }

    private BroadcastReceiver commandReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ActionConfig.REFRESH_ADDRESS:
                    mac = intent.getParcelableExtra("mac");
                    refreshAddress();
                    break;
                case ActionConfig.OPEN_ACTION:
                    //App.getInstance().getBleManager().openLock(new byte[]{b1, b2, b3});
                    break;
                case ActionConfig.BYTTERY_ACTION:
                    handler.sendEmptyMessageDelayed(2,300);
                    break;
                case ActionConfig.LOCK_STATUS:
                    App.getInstance().getBleManager().getLockStatus();
                    break;
                case ActionConfig.LOCK_GSM:
                    App.getInstance().getBleManager().getLockWorkStatus();
                    break;
                case ActionConfig.RESET_LOCK:
                    App.getInstance().getBleManager().resetLock();
                    break;
                case ActionConfig.LOCK_GSM_ID:
                    App.getInstance().getBleManager().getGSMId();
                    break;
                case ActionConfig.LOCK_GSM_VERSION:
                    App.getInstance().getBleManager().getGSMVersion();
                    break;
                case ActionConfig.GET_MODE:
                    App.getInstance().getBleManager().getMode();
                    break;
                case ActionConfig.SET_MODE:
                    App.getInstance().getBleManager().setMode(1);
                    break;
                case ActionConfig.SET_NORMAL_MODE:
//                    sendCommand(HexUtil.hexStringToBytes(new SetModeTxOrder(0).generateString()));
                    App.getInstance().getBleManager().setMode(0);
                    break;
                case ActionConfig.SET_RESTART_MODE:
//                    sendCommand(HexUtil.hexStringToBytes(new SetModeTxOrder(2).generateString()));
                    App.getInstance().getBleManager().setMode(2);
                    break;
                case ActionConfig.SET_SHUTDOWN_MODE:
                    App.getInstance().getBleManager().setMode(3);
                    break;
                case ActionConfig.ICCID:
                    App.getInstance().getBleManager().getICCID();
                    break;
                case ActionConfig.DEMAIN:
                    App.getInstance().getBleManager().getDemainName();
                    break;
                case ActionConfig.LOCK_IP:
                    App.getInstance().getBleManager().getLockIP();
                    break;
                case ActionConfig.RF_TEST:
                    int number = intent.getIntExtra("number", 0);
                    App.getInstance().getBleManager().RFTest(number);
                    break;
                case ActionConfig.STOP:
                    App.getInstance().getBleManager().resultStatus();
                    break;
            }
        }
    };

    private void refreshAddress(){
        Log.e(CommandService.class.getSimpleName(),"mac:"+mac.getAddress());
        if (!App.getInstance().getBleManager().isEnable()){
            App.getInstance().getBleManager().enableBluetooth();
            return;
        }
        Intent intent = new Intent(Config.UPDATE_VIEW);
        intent.putExtra("data","3");
        sendBroadcast(intent);
        App.getInstance().getBleManager().connectDevice(mac,this);
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    App.getInstance().getBleManager().getToken();
                    break;
                case 2:
                    App.getInstance().getBleManager().getBattery();
                    break;
            }
        }
    };


    @Override
    public void onConnect() {
        Intent intent = new Intent(Config.UPDATE_VIEW);
        intent.putExtra("data", "1");
        intent.putExtra("mac", mac);
        sendBroadcast(intent);
    }
    @Override
    public void onDisconnect(int state) {
        Intent intent = new Intent(Config.UPDATE_VIEW);
        intent.putExtra("data", "-2");
        sendBroadcast(intent);
    }
    @Override
    public void onServicesDiscovered(String name, String address) {
        Intent intent = new Intent(Config.UPDATE_VIEW);
        intent.putExtra("data", "2");
        sendBroadcast(intent);
        handler.sendEmptyMessageDelayed(0, 1500);
    }
}
