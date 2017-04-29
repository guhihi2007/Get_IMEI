package org.imei;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        textView.setTextIsSelectable(true);
        Log.v("gpp", "手机SKD版本：" + Build.VERSION.SDK_INT);
//        if (Build.VERSION.SDK_INT < 23) {
//            textView.setText(getPhoneInfo_down23());
//            try {
//                takeLog(getPhoneInfo_down23());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.d("gpp", getPhoneInfo_down23());
//        } else if (Build.VERSION.SDK_INT >= 23) {
//            textView.setText(getPhoneInfo_up23());
//            try {
//                takeLog(getPhoneInfo_up23());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.d("gpp", getPhoneInfo_up23());
//        }
//        Toast.makeText(this, "已生成IMEI.txt文件，请到文件管理器中查看！", Toast.LENGTH_LONG).show();
        textView.setText(getAllIMEI(getIMEI()));
//        textView.setText(getPhoneInfo_up23());
        try {
            takeLog(getAllIMEI(getIMEI()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPhoneInfo_up23() {
        String Info =
                "品牌：" + Build.MANUFACTURER + "" +
                        "\n型号：" + Build.MODEL + "" +
                        "\n安卓版本：" + Build.VERSION.RELEASE + "" +
                        "\n版本号：" + Build.DISPLAY + "" +
                        "\nIMEI:" + ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId(0) + "" +
                        "\nIMEI:" + ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId(1) + "" +
                        "\nIMEI:" + ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId(2) + "" +
                        "\nAndroidID：" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return Info;
    }

    public String getAllIMEI(List list) {
        String Info = "";
        if (list.size() == 1) {
            Info =
                    "品牌：" + Build.MANUFACTURER + "" +
                            "\n型号：" + Build.MODEL + "" +
                            "\n安卓版本：" + Build.VERSION.RELEASE + "" +
                            "\n版本号：" + Build.DISPLAY + "" +
                            "\nIMEI:" + list.get(0) + "" +
                            "\nAndroidID：" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        }
        if (list.size() == 2) {
            Info =
                    "品牌：" + Build.MANUFACTURER + "" +
                            "\n型号：" + Build.MODEL + "" +
                            "\n安卓版本：" + Build.VERSION.RELEASE + "" +
                            "\n版本号：" + Build.DISPLAY + "" +
                            "\nIMEI:" + list.get(0) + "" +
                            "\nIMEI:" + list.get(1) + "" +
                            "\nAndroidID：" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        }
        if (list.size() == 3) {
            Info =
                    "品牌：" + Build.MANUFACTURER + "" +
                            "\n型号：" + Build.MODEL + "" +
                            "\n安卓版本：" + Build.VERSION.RELEASE + "" +
                            "\n版本号：" + Build.DISPLAY + "" +
                            "\nIMEI:" + list.get(0) + "" +
                            "\nIMEI:" + list.get(1) + "" +
                            "\nIMEI:" + list.get(2) + "" +
                            "\nAndroidID：" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        }
        return Info;
    }

    public List getIMEI() {
        String imei_0 = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        String imei_1 = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId(0);
        String imei_2 = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId(1);
        List list = new ArrayList();
        String M1 = "";
        String M2 = "";
        String M3 = "";
        if (imei_0.equals(imei_1)) {
            M1 = imei_0;
        } else {
            M1 = imei_0;
            M2 = imei_1;
        }
        if (imei_0.equals(imei_2)) {
            M1 = imei_0;
        } else {
            M3 = imei_2;
        }
        if (imei_1.equals(imei_2)) {
            M2 = imei_1;
        } else {
            M3 = imei_2;
        }
        if (M1 != null)
            list.add(M1);
        if (M2 != null)
            list.add(M2);
        if (M3 != null)
            list.add(M3);
        return list;
    }


    public String getPhoneInfo_down23() {
        String Info =
                "品牌：" + Build.MANUFACTURER + "" +
                        "\n型号：" + Build.MODEL + "" +
                        "\n安卓版本：" + Build.VERSION.RELEASE + "" +
                        "\n版本号：" + Build.DISPLAY + "" +
                        "\nIMEI:" + ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId() + "" +
                        "\nAndroidID：" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return Info;
    }

    public void takeLog(String str) throws IOException {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileWriter fw = null;
        String Dir = root + "/";
        String path = "" + Dir + "/" + "IMEI.txt";
        File file = new File(path);
        if (!file.exists()) {
            Log.v("gpp", "文件不存在，开始创建");
            file.createNewFile();
            Log.v("gpp", "IMEI.txt");
        }
        if (file.exists())
            file.delete();
        try {
            fw = new FileWriter(path, true);
            fw.write(str + "\r\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}