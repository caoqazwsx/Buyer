package com.zhao.buyer.util;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.zhao.buyer.common.APPCONST;
import com.zhao.buyer.common.URLCONST;
import com.zhao.buyer.callback.HttpCallback;
import com.zhao.buyer.callback.JsonCallback;
import com.zhao.buyer.model.JsonModel;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhao on 2016/4/16.
 */

public class HttpUtil {


    public static void sendBitmapGetRequest(final String address, final HttpCallback callback){
        new Thread(new Runnable() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-type", "application/octet-stream");
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    connection.setRequestProperty("contentType", "utf-8");

                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);

                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e("Http", "网络错误异常！!!!");
                    }
                    Log.d("Http", "connection success");
                    if(callback != null){
                        callback.onFinish(in);
                    }
                }catch (Exception e) {
                    Log.e("Http", e.toString());
                    if (callback != null) {
                        callback.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


    public static void sendGetRequest(final String address, final HttpCallback callback){
        new Thread(new Runnable() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-type", "text/html");
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    connection.setRequestProperty("contentType", "utf-8");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e("Http", "网络错误异常！!!!");
                    }
                    Log.d("Http", "connection success");
                    if(callback != null){
                        callback.onFinish(in);
                    }
                }catch (Exception e) {
                    Log.e("Http", e.toString());
                    if (callback != null) {
                        callback.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendPostRequest(final String address, final String output, final HttpCallback callback){
        new Thread(new Runnable() {
            HttpURLConnection connection = null;
            @Override
            public void run() {
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
//                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    if(output != null){
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeBytes(output);
                    }
                    InputStream in = connection.getInputStream();
                    if(callback != null){
                        callback.onFinish(in);
                    }
                }catch (Exception e) {
                    if (callback != null) {
                        callback.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static String makeURL(String p_url, Map<String,Object> params) {
        StringBuilder url = new StringBuilder(p_url);
        Log.d("http",p_url);
        if(url.indexOf("?")<0)
            url.append('?');
        for(String name : params.keySet()){
            Log.d("http",name + "=" + params.get(name));
            url.append('&');
            url.append(name);
            url.append('=');
            try {
                if(URLCONST.isRSA){
                    if(name.equals("token")){
                        url.append(String.valueOf(params.get(name)));
                    }else {
                        url.append(StringHelper.encode(Base64.encodeToString(RSAUtilV2.encryptByPublicKey(String.valueOf(params.get(name)).getBytes(), APPCONST.publicKey),Base64.DEFAULT).replace("\n","")));
                    }
                }else {
                    url.append(String.valueOf(params.get(name)));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //不做URLEncoder处理
//			try {
//				url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }
        return url.toString().replace("?&", "?");
    }

    public static String makeURLNoRSA(String p_url, Map<String,Object> params) {
        StringBuilder url = new StringBuilder(p_url);
        Log.d("http",p_url);
        if(url.indexOf("?")<0)
            url.append('?');
        for(String name : params.keySet()){
            Log.d("http",name + "=" + params.get(name));
            url.append('&');
            url.append(name);
            url.append('=');
            url.append(String.valueOf(params.get(name)));
            //不做URLEncoder处理
//			try {
//				url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }
        return url.toString().replace("?&", "?");
    }

    public static String makePostOutput(Map<String, Object> params) {
        StringBuilder output = new StringBuilder();
        Iterator<String> it = params.keySet().iterator();
        while (true) {
            String name = it.next();
            Log.d("http",name + "=" + params.get(name));
            output.append(name);
            output.append('=');
            try {
                if(URLCONST.isRSA){
                    if(name.equals("token")){
                        output.append(String.valueOf(params.get(name)));
                    }else {
                        output.append(StringHelper.encode(Base64.encodeToString(RSAUtilV2.encryptByPublicKey(String.valueOf(params.get(name)).getBytes(),APPCONST.publicKey),Base64.DEFAULT).replace("\n","")));
                    }
                }else {
                    output.append(String.valueOf(params.get(name)));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if (!it.hasNext()) {
                break;
            }
            output.append('&');
            //不做URLEncoder处理
//			try {
//				url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }
        return output.toString();
    }

    public static void uploadFileRequest(final String actionUrl,
                                       final  String[] filePaths,
                                   final Map<String,Object> params,
                                         final JsonCallback callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                uploadFile(actionUrl,params,filePaths,callback);
            }

        }).start();

    }
    /**
     * 多文件上传的方法
     * @param actionUrl：上传的路径
     * @param uploadFilePaths：需要上传的文件路径，数组
     * @return
     */
   private static void uploadFile(String actionUrl, Map<String,Object> params, String[] uploadFilePaths, JsonCallback callback) {
       String end = "\r\n";
       String twoHyphens = "--";
       String boundary = "*****";
       String CHARSET = "utf-8"; //设置编码
       DataOutputStream ds = null;
       InputStream inputStream = null;
       InputStreamReader inputStreamReader = null;
       BufferedReader reader = null;
       StringBuffer resultBuffer = new StringBuffer();
       String tempLine = null;
       try {
           Log.i("http","开始上传文件");
           // 统一资源
           URL url = new URL(makeURL(actionUrl,params));
           // 连接类的父类，抽象类
           URLConnection urlConnection = url.openConnection();
           urlConnection.setConnectTimeout(60000);
           urlConnection.setReadTimeout(60000);
           // http的连接类
           HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
           // 设置是否从httpUrlConnection读入，默认情况下是true;
           httpURLConnection.setDoInput(true);
           // 设置是否向httpUrlConnection输出
           httpURLConnection.setDoOutput(true);
           // Post 请求不能使用缓存
           httpURLConnection.setUseCaches(false);
           // 设定请求的方法，默认是GET
           httpURLConnection.setRequestMethod("POST");
           // 设置字符编码连接参数
           httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
           // 设置字符编码
           httpURLConnection.setRequestProperty("Charset", "UTF-8");
           // 设置请求内容类型
           httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
           // 设置DataOutputStream
           ds = new DataOutputStream(httpURLConnection.getOutputStream());
           for (int i = 0; i < uploadFilePaths.length; i++) {
               String uploadFile = uploadFilePaths[i];
               String filename = uploadFile.substring(uploadFile.lastIndexOf("/") + 1);
               //设置参数
               StringBuffer sb = new StringBuffer();
               sb.append(end);
               sb.append(twoHyphens + boundary + end);
               sb.append("Content-Disposition: form-data; " + "name=\"file" + i + "\";filename=\"" + filename + "\"" + end);
               sb.append("Content-Type: application/octet-stream; charset="+CHARSET+end);
               sb.append(end);
               Log.i("http","参数："+sb.toString());
               //写入文件数据
               ds.write(sb.toString().getBytes());
               FileInputStream fStream = new FileInputStream(uploadFile);
               int bufferSize = 1024;
               byte[] buffer = new byte[bufferSize];
               int length = -1;
               int total = 0;
               while ((length = fStream.read(buffer)) != -1) {
                   ds.write(buffer, 0, length);
                   total += length;
               }
               Log.i("http","文件的大小："+total);
               ds.writeBytes(end);
               /* close streams */
               fStream.close();
           }
           ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
           /* close streams */
           ds.flush();
           if (httpURLConnection.getResponseCode() >= 300) {
               callback.onError(new Exception(
                       "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode()));
//               throw new Exception(
//                       "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
           }
           if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
               inputStream = httpURLConnection.getInputStream();
               inputStreamReader = new InputStreamReader(inputStream);
               reader = new BufferedReader(inputStreamReader);
               tempLine = null;
               resultBuffer = new StringBuffer();
               while ((tempLine = reader.readLine()) != null) {
                   resultBuffer.append(tempLine);
                   resultBuffer.append("\n");
               }
               Log.i("http",resultBuffer.toString());
               if (callback != null){
                   Gson gson = new Gson();
                   JsonModel jsonModel = gson.fromJson(resultBuffer.toString(), JsonModel.class);
                   if(URLCONST.isRSA && !StringHelper.isEmpty(jsonModel.getResult())) {
                       jsonModel.setResult(StringHelper.decode(new String(RSAUtilV2.decryptByPrivateKey(Base64.decode(jsonModel.getResult().replace("\n",""),Base64.DEFAULT),APPCONST.privateKey))));
                   }
                   callback.onFinish(jsonModel);
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (ds != null) {
               try {
                   ds.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if (reader != null) {
               try {
                   reader.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if (inputStreamReader != null) {
               try {
                   inputStreamReader.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if (inputStream != null) {
               try {
                   inputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
   }



}
