package com.educaTio.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiManager;
import android.util.Log;

public class AccessPoint {
	
	private WifiManager wifiManager;
	private String ssid;
	private String password;
	
	public AccessPoint(Context c, String SSID, String password) {
		this.wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		this.ssid = SSID;
		this.password = password;
	}

	
    public void createWifiAccessPoint() {
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
        Method[] wmMethods = wifiManager.getClass().getDeclaredMethods();
        boolean methodFound = false;
        for (Method method: wmMethods) {
            if (method.getName().equals("setWifiApEnabled")) {
                methodFound = true;
                WifiConfiguration netConfig = new WifiConfiguration();
                netConfig.SSID = ssid;
//                netConfig.preSharedKey = password;
//                netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                netConfig.allowedAuthAlgorithms.set(
                    WifiConfiguration.AuthAlgorithm.OPEN);
                try {
                    boolean apstatus = (Boolean) method.invoke(
                        wifiManager, netConfig, true);
                    for (Method isWifiApEnabledmethod: wmMethods) {
                        if (isWifiApEnabledmethod.getName().equals(
                                "isWifiApEnabled")) {
                            while (!(Boolean) isWifiApEnabledmethod.invoke(
                                    wifiManager)) {};
                            for (Method method1: wmMethods) {
                                if (method1.getName().equals(
                                        "getWifiApState")) {
                                    int apstate;
                                    apstate = (Integer) method1.invoke(
                                        wifiManager);
                                }
                            }
                        }
                    }
                    if (apstatus) {
                        Log.d("AccessPoint Activity",
                            "Access Point created");
                    } else {
                        Log.d("AccessPoint Activity",
                            "Access Point creation failed");
                    }

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!methodFound) {
            Log.d("AccessPoint",
                "cannot configure an access point");
        }
    }
}
