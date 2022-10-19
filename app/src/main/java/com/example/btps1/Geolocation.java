package com.example.btps1;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Message;

import android.os.Handler;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class    Geolocation {

    public static void getAddress(final String locationAddress, final Context context, final Handler handler){
        Thread thread=new Thread(){
            @Override
            public void run() {
                Geocoder geocoder=new Geocoder(context, Locale.getDefault());
                String result=null;
                try{
                    List addressList=geocoder.getFromLocationName(locationAddress,1);
                    if(addressList!=null && addressList.size()>0){
                        Address address=(Address) addressList.get(0);
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append(address.getLatitude()).append(",");
                        stringBuilder.append(address.getLongitude());
                        result=stringBuilder.toString();
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    Message message=Message.obtain();
                    message.setTarget(handler);
                    if(result!=null){
                        message.what=1;
                        Bundle bundle=new Bundle();
                        bundle.putString("address",result);
                        message.setData(bundle);
                    }else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", "23.5880,72.3693");
                        message.setData(bundle);
                    }
                    message.sendToTarget();

                }
            }
        };
        thread.start();
    }
}
