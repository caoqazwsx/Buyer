package com.zhao.buyer.globalvariable;

/**
 * Created by zhao on 2016/4/25.
 */
public class Globalvariable {
     //public static  String ServerIP = "192.168.43.140:8888";
    public static  String ServerIP = "192.168.119.1:8888";
    public static  String LocalhostIP = "10.0.2.2:8888";
    public static  String SERVER_ADDRESS = "http://"+ Globalvariable.ServerIP +"/android_Server/requestAccept.action?";
    public static String GEOCODING_SERVER_ADDRESS = "http://api.map.baidu.com/geocoder/v2/?";
     public static String PLACE_SUGGESTION_ADDRESS = "http://api.map.baidu.com/place/v2/suggestion?";
    public static String BAIDU_MAP_AK = "nQZ89oOFFGqxLoRq6STiMKFnyeA01Wmt";
    public static String BAIDU_MAP_MCODE = "B1:FE:DE:EC:EF:5A:2D:0B:33:DF:4F:85:39:C5:90:4B:EB:FA:88:92;com.zhao.buyer.activity";
    public static int ERROR = -1;
    public static boolean LOGIN_STATE = false;
    public static String ACCOUNT = null;
    public static String PASSWROD = null;

    public final static String WAIT_PAY = "WaitPay";
    public final static String WAIT_ACCEPT = "WaitAccept";
    public final static String WAIT_ARRIVED = "WaitArrived";
    public final static String WAIT_COMMENT = "WaitComment";
    public final static String WAIT_BACK = "WaitBack";
    public final static String CANCEL = "Cancel";
    public final static String FINISH = "Finish";
    public final static String CLEAR_CART = "ClearCart";


}
