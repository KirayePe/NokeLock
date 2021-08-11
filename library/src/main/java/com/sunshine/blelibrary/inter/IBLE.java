package com.sunshine.blelibrary.inter;

import android.bluetooth.BluetoothDevice;

/**
 * 作者：LiZhao
 * 时间：2017.2.8 11:29
 * 邮箱：44493547@qq.com
 * 备注：BLE操作接口
 */
public interface IBLE {

    /**
     * 开启蓝牙
     *
     * @return 成功或失败
     */
    boolean enableBluetooth();
    /**
     * 关闭蓝牙
     *
     * @return 成功或失败
     */
    boolean disableBluetooth();

    /**
     * 重启蓝牙
     */
    void resetBluetoothAdapter();

    /**
     * 蓝牙是否开启
     *
     * @return 成功或失败
     */
    boolean isEnable();

    /**
     * @title 扫描设备
     * @param onDeviceSearchListener 搜索设备回调
     */
    void startScan(OnDeviceSearchListener onDeviceSearchListener);

    /**
     * 停止扫描
     */
    void stopScan();

    /**
     * 链接设备
     * @param address 设备地址
     * @param onConnectionListener 链接状态回调
     */
    boolean connect(String address,OnConnectionListener onConnectionListener);

    /**
     * 链接设备
     * @param device 设备
     * @param onConnectionListener 链接状态回调
     */
    boolean connectDevice(BluetoothDevice device,OnConnectionListener onConnectionListener);

    /**
     * 写指令 指令byte
     * @param bytes 指令数组
     */
    void writeByte(byte[] bytes);

    /**
     * 获取Token
     * @return 是否成功
     */
    boolean getToken();

    /**
     * @title 获取电量
     * @return 是否成功
     */
    boolean getBattery();

    /**
     * 开锁
     * @return 是否成功
     * @param bytes
     */
    boolean openLock(byte[] bytes);

    /**
     * 复位
     * @return 是否成功
     */
    boolean resetLock();
    /**
     * 获取锁状态
     * @return 是否成功
     */
    boolean getLockStatus();

    /**
     * 修改密码
     * @return 是否成功
     */
    boolean setPassword();

    /**
     * 修改密钥
     * @return 是否成功
     */
    boolean setKey();

    /**
     * 获取工作模式
     * @return 是否成功
     */
    boolean getMode();
    /**
     * 获取工作状态
     * @return 是否成功
     */
    boolean getLockWorkStatus();

    /**
     * 设置工作模式
     * @return 是否成功
     */
    boolean setMode(int position);

    boolean resultStatus();

    /**
     * 获取GSM版本信息
     * @return 是否成功
     */
    boolean getGSMVersion();

    /**
     * 获取GSM ID信息
     * @return 是否成功
     */
    boolean getGSMId();

    /**
     * 获取锁IP地址
     * @return 是否成功
     */
    boolean getLockIP();

    /**
     * 获取锁域名
     * @return 是否成功
     */
    boolean getDemainName();

    /**
     * RF 测试
     * @return 是否成功
     */
    boolean RFTest(int number);

    boolean getICCID();

    /**
     * oad模式
     * @return 是否成功
     */
    boolean updateVersion();
    /**
     * oad升级时写写入的特征值
     * @param bytes 指令数组
     * @return 是否成功
     */
    boolean writeWrite(byte[] bytes);

    /**
     * oad升级时写读取的特征值
     * @param bytes 指令数组
     * @return 是否成功
     */
    boolean writeRead(byte[] bytes);

    /**
     * 重置密钥和密码
     */
    void resetPasswordAndAQ();

    /**
     * 强制报警
     * @param bytes
     * @return
     */
    boolean alert(byte bytes);

    /**
     * 数据透传
     * @param bytes
     * @return
     */
    boolean dataPull(byte[] bytes);

    /**
     * 开启电池仓
     * @return
     */
    boolean openBatteryBox();

    /**
     * 关闭电池仓
     * @return
     */
    boolean closeBatteryBox();

    /**
     * 检测电池仓状态
     * @return
     */
    boolean statusBatteryBox();

    /**
     * 断开链接
     */
    void disconnect();

    /**
     * gatt关闭
     */
    void close();

    boolean refreshCache();

    /**
     * 寻车功能
     * @return
     */
    boolean findCar();
    /**
     * 修改唤醒
     * @return
     */
    boolean updateWake(int num, int minute, int hour, int time);

    /**
     * 更换电池
     * @return
     */
    boolean ReplacementBattery();

    /**
     * 设置连接模式
     *
     * @param mode 1 长连接模式  0短连接模式
     * @return
     */
    boolean setConnectionMode(int mode);
}
