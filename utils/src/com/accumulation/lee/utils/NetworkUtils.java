package com.accumulation.lee.utils;

/**
 * Created by liyong on 15/5/6.
 */

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;


public  class NetworkUtils {

    /**
     * 获得本机ip，没有联网 return null
     *
     * @return
     */
    public static String getIpAddress() {
        try {
            String ipv4;
            List<NetworkInterface> nilist = Collections.list(NetworkInterface
                    .getNetworkInterfaces());
            for (NetworkInterface ni : nilist) {
                List<InetAddress> ialist = Collections.list(ni
                        .getInetAddresses());
                for (InetAddress address : ialist) {
                    if (!address.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(ipv4 = address
                            .getHostAddress())) {
                        return ipv4;
                    }
                }

            }

        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取wifi ip地址
     *
     * @param mContext
     * @return
     */
    public static String getWifiIpAddress(Context mContext) {
        WifiInfo localWifiInfo = null;
        if (mContext != null) {
            localWifiInfo = ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE))
                    .getConnectionInfo();
            if (localWifiInfo != null) {
                String str = convertIntToIp(localWifiInfo.getIpAddress());
                return str;
            }
        }
        return "";
    }

    private static String convertIntToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "."
                + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
    }

    /**
     * Get network type
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? -1 : networkInfo.getType();
    }

    /**
     * 获取手机网络类型名称
     * @param networkType
     * @param mnc Mobile NetworkCode，移动网络码，共2位
     * @return
     */
    public static String getNetWorkName(int networkType,String mnc) {
        if (networkType == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
            return "Network type is unknown";
        } else if (networkType == TelephonyManager.NETWORK_TYPE_CDMA) {
            return "电信2G";
        } else if (networkType == TelephonyManager.NETWORK_TYPE_EVDO_0) {
            return "电信3G";
        } else if (networkType == TelephonyManager.NETWORK_TYPE_GPRS || networkType == TelephonyManager.NETWORK_TYPE_EDGE) {
            if ("00".equals(mnc) || "02".equals(mnc)) {
                return "移动2G";
            } else if ("01".equals(mnc)) {
                return "联通2G";
            }
        } else if (networkType == TelephonyManager.NETWORK_TYPE_UMTS || networkType == TelephonyManager.NETWORK_TYPE_HSDPA) {
            return "联通3G";
        }
        return null;
    }

    /**
     * 检测网络状态
     * @param context
     * @return
     */
    public static boolean checkNetworkStatus(Context context){
        boolean resp = false;
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connMgr.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isAvailable()) {
            resp = true;
        }
        return resp;
    }

    /**
     * 检测gps状态
     * @param context
     * @return
     */
    public static boolean checkGPSStatus(Context context){
        boolean resp = false;
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            resp = true;
        }
        return resp;
    }

    /**
     * 判断谷歌地图是否可用,某些国行的手机不支持谷歌地图的服务
     * @return
     */
    public static boolean googleMapAvailable() {
        boolean available = false;
        try{
            Class.forName("com.google.android.maps.MapActivity");
            available = true;
        } catch (Exception e)  {
        }
        return available;
    }

    public static boolean isWiFiActive(Context context) {
        WifiManager wm=null;
        try{
            wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(wm==null || wm.isWifiEnabled()==false) return false;

        return true;
    }

}