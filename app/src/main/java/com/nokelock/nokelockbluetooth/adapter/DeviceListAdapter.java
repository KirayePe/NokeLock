package com.nokelock.nokelockbluetooth.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fitsleep.sunshinelibrary.inter.OnItemAdapterClick;
import com.nokelock.nokelockbluetooth.R;
import com.nokelock.nokelockbluetooth.bean.DeviceBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 作者: Sunshine
 * 时间: 2017/6/5.
 * 邮箱: 44493547@qq.com
 * 描述:
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceHolder> implements Comparator<DeviceBean>{

    public List<DeviceBean> deviceBeanList;
    public OnItemAdapterClick itemClickListener;
    public DeviceListAdapter(){
        deviceBeanList = new ArrayList<>();
    }

    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, null);
        return new DeviceHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(DeviceHolder holder, int position) {
        DeviceBean device = deviceBeanList.get(position);
        holder.setData(device);
    }

    @Override
    public int getItemCount() {
        return deviceBeanList.size()>20?20:deviceBeanList.size();
    }


    public void setOnItemClickListener(OnItemAdapterClick onItemClickListener){
        this.itemClickListener = onItemClickListener;
    }

    public void updateData(List<DeviceBean> bleDeviceList) {
        deviceBeanList.clear();
        deviceBeanList.addAll(bleDeviceList);
        Collections.sort(deviceBeanList, this);
        notifyDataSetChanged();
    }

    @Override
    public int compare(DeviceBean lhs, DeviceBean rhs) {
        return rhs.getRssi() - lhs.getRssi();
    }

    class DeviceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvMac;
        TextView tvRiss;
        TextView tvName;
        OnItemAdapterClick onItemListener;
        public DeviceHolder(View itemView, OnItemAdapterClick onItemClickListener) {
            super(itemView);
            this.onItemListener = onItemClickListener;
            tvMac = (TextView) itemView.findViewById(R.id.tv_mac);
            tvRiss = (TextView) itemView.findViewById(R.id.tv_riss);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public void setData(DeviceBean device) {
            tvMac.setText(device.getDevice().getAddress());
            tvRiss.setText(String.valueOf(device.getRssi()));
            tvName.setText(TextUtils.isEmpty(device.getDevice().getName())?"null":device.getDevice().getName());
        }

        @Override
        public void onClick(View v) {
            if (null!=onItemListener){
                onItemListener.onItemClick(v,deviceBeanList.get(getPosition()).getDevice());
            }
        }
    }
}
