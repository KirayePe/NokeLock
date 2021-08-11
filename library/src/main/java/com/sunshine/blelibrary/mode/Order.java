package com.sunshine.blelibrary.mode;

import com.sunshine.blelibrary.exception.InvalidTypeException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 指令基类
 * Created by sunshine on 2017/2/24.
 */

public class Order {

    final private TYPE type;
    /**
     * 数据列表
     */
    final private List<Byte> datas;
    public Order(TYPE type) {
        this.type = type;
        this.datas = new ArrayList<Byte>();
    }

    public enum TYPE{
        /**
         * 获取token
         */
        GET_TOKEN(0x0601),
        /**
         * 开锁
         */
        OPEN_LOCK(0x0501),
        /**
         * 获取电量
         */
        GET_BATTERY(0x0201),
        /**
         * 获取锁状态
         */
        LOCK_STATUS(0x050e),
        /**
         * 复位
         */
        RESET_LOCK(0x050c),
        /**
         * 重置密码1
         */
        RESET_PASSWORD(0x0503),
        /**
         * 重置密码2
         */
        RESET_PASSWORD2(0X0504),
        /**
         * 重置密钥
         */
        RESET_AQ(0x0a01),
        /**
         * 固件升级
         */
        UPDATE_VERSION(0x0301),
        /**
         * 获取工作模式
         */
        GET_MODE(0x0520),
        /**
         * 设置工作模式
         */
        SET_MODE(0x0521),
        /**
         * 获取锁的工作状态
         */
        GET_LOCK_STATUS(0x0522),
        /**
         * 设置还车状态
         */
        RESULT_STATUS(0x0514),
        /**
         * 获取GSM ID
         */
        GET_GSM_ID(0x0523),
        /**
         * 获取GSM版本
         */
        GET_GSM_VERSION(0x0524),

        /**
         * 获取IP
         */
        GET_LOCK_IP(0x0532),

        /**
         * RF测试指令
         */
        RF_TEST(0x5401),

        /**
         * 获取域名demain
         */
        GET_DEMAIN_NAME(0x0530),
        /**
         * 获取iccid
         */
        GET_ICC_ID(0x0528),
        /**
         * 强制报警
         */
        ALERT(0x2001),
        /**
         * 数据透传
         */
        DATA_PULL(0x5401),
        /**
         * 开启电池仓
         */
        OPEN_BATTERY(0x1001),
        /**
         * 关闭电池仓
         */
        CLOSE_BATTERY(0x1003),
        /**
         * 获取电池仓状态
         */
        STATUS_BATTERY(0x1005),
        /**
         * 寻车
         */
        FIND_CAR(0x0516),
        /**
         * RFID显示
         */
        RF_ID(0x0577),
        /**
         * 获取电池仓状态
         */
        REPLACEMENT_BATTERY(0x6101),
        /**
         * 修改唤醒次数和时间
         */
        UPDATE_WAKE(0x0563),
        /**
         * 设置连接模式
         */
        SET_CONNECTION_MODE(0x0701);

        final int value;

        TYPE(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }

        /**
         * 传入字符串，得到相应的指令类型
         *
         * @param typeStr 类型字符串
         * @throws InvalidTypeException
         * @return TYPE
         */
        public static TYPE valueOfType(String typeStr)  throws InvalidTypeException{
            try {
                int value = Integer.valueOf(typeStr, 16);
                for (TYPE type : TYPE.values()) {
                    if (type.getValue() == value) {
                        return type;
                    }
                }
            } catch (Exception e) {
                throw new InvalidTypeException();
            }
            throw new InvalidTypeException();
        }
    }

    /**
     * 获取指令类型
     * @return TYPE
     */
    public TYPE getType() {
        return type;
    }
    /**
     * 根据position获取数据
     *
     * @param position 位置
     * @return byte
     */
    public byte get(int position) {
        return datas.get(position);
    }
    /**
     * 添加数据到最后的位置
     *
     * @param data
     * @return void
     */
    protected void add(byte data) {
        datas.add(data);
    }
    /**
     * 添加数据 到指定的位置
     * @Title: add
     * @param position
     * @param data
     * @return void
     */
    protected void add(int position, byte data) {
        datas.add(position, data);
    }
    /**
     * 添加所给的数组
     * @Title: add
     * @param dataArray
     * @return void
     */
    protected void add(byte... dataArray) {
        if (dataArray != null) {
            for (int i = 0; i < dataArray.length; i++) {
                add(dataArray[i]);
            }
        }
    }
    /**
     * 添加所给的数组到指定的位置
     * @Title: add
     * @param position
     * @param dataArray
     * @return void
     */
    protected void add(int position, byte... dataArray) {
        if (dataArray != null) {
            for (int i = 0; i < dataArray.length; i++) {
                add(position + i, dataArray[i]);
            }
        }
    }
    /**
     * 从数据列表中移除指定位置的数据
     * @Title: remove
     * @param position
     * @return void
     */
    protected void remove(int position) {
        datas.remove(position);
    }
    /**
     * 设置指定位置的数据
     * @Title: set
     * @param position
     * @param value
     * @return void
     */
    protected void set(int position, byte value) {
        datas.set(position, value);
    }
    /**
     * 数据列表的长度
     * @Title: size
     * @return int
     */
    public int size() {
        return datas.size();
    }
    /**
     * 清空数据列表
     * @Title: clear
     * @return void
     */
    protected void clear() {
        datas.clear();
    }
    /**
     * 添加所给的数据列表
     * @Title: addAll
     * @param collection
     * @return void
     */
    protected void addAll(Collection<? extends Byte> collection) {
        datas.addAll(collection);
    }
    /**
     * 获取数据列表
     * @Title: getDatas
     * @return List<Byte>
     */
    protected List<Byte> getDatas() {
        return datas;
    }
    /**
     * 将byte转换为16进制字符串
     * @Title: formatByte2HexStr
     * @param value
     * @return String
     */
    public static String formatByte2HexStr(byte value) {
        return String.format("%02X", value);
    }
    @Override
    public String toString() {
        return "Order [type=" + type + ", datas=" + datas + "]";
    }
}
