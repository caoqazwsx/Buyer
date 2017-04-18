package com.zhao.buyer.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhao on 2016/9/9.
 */
public class StringHelper {

    /**
     * 是否是Emoji表情符
     * @param string
     * @return
     */
    public static boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    /**
     * 字符集编码
     * @param encoded
     * @return
     */
    public static String encode(String encoded){
        String res = encoded;
        try {
            res = URLEncoder.encode(encoded,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 字符集解码
     * @param decoded
     * @return
     */

    public static String decode(String decoded){
        String res = decoded;
        try {
            res = URLDecoder.decode(decoded,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public static String jidToUsername(String jid){
        if(jid != null){
            if(jid.contains("@")){
                return  jid.substring(0,jid.indexOf("@"));
            }else {
                return jid;
            }
        }
        return "";
    }

    public static boolean isEmpty(String str){
        return str == null || str.equals("");
    }

    public static String reduceString(String strlocation, int maxLength){
        String res = strlocation;
        if(strlocation.length() > maxLength){
            char[] tem = res.toCharArray();
            res = String.copyValueOf(tem,0,maxLength);
            res +="...";
        }
        return res;
    }
}
