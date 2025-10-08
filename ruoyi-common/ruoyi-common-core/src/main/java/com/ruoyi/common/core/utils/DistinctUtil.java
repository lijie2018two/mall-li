package com.ruoyi.common.core.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DistinctUtil {
    private static final String API_KEY = "fb92df8c7e1c98289373d96f44fd02b1";
    private static final String BASE_URL = "https://restapi.amap.com/v3/direction/driving";
    private static final String IP_BASE_URL = "https://restapi.amap.com/v3/geocode/regeo";
    private static final Logger log = LoggerFactory.getLogger(DistinctUtil.class);


    public static String getDistance(String origin, String destination) throws Exception {

        System.out.println("接搜的经纬度"+origin+"目的地"+destination);
        String url = BASE_URL + "?key=" + API_KEY +
                "&origin=" + origin +
                "&destination=" + destination +
                "&output=json";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();


        // 使用Fastjson解析JSON字符串
        JSONObject jsonResponse = JSONObject.parseObject(response.toString());
        log.info("获取到的距离信息"+jsonResponse);
        // 获取路径信息并提取距离
        JSONObject routes = jsonResponse.getJSONObject("route");
        if (oConvertUtils.isEmpty(routes)){
            return "未获取到距离";
        }


        JSONArray path = routes.getJSONArray("paths");
        JSONObject jsonObject = path.getJSONObject(0);
        String distance = jsonObject.get("distance").toString();
        double number = Double.parseDouble(distance) / 1000;
        BigDecimal numbers = new BigDecimal(number);
        double number1 = numbers.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("距离：" + number + "公里");
        return Double.toString(number1);
    }

    public static String getIpCity(String origin) throws Exception {

        String url = IP_BASE_URL + "?key=" + API_KEY +
                "&location=" + origin +
                "&output=json";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        // 使用Fastjson解析JSON字符串
        JSONObject jsonResponse = JSONObject.parseObject(response.toString());

        // 获取路径信息并提取距离
        JSONObject regeocode = jsonResponse.getJSONObject("regeocode");
        JSONObject path = regeocode.getJSONObject("addressComponent");
        String city = path.get("city").toString();
        if ("[]".equals(city)){
            city = path.get("district").toString();
        }
        return city;
    }


    /// 计算距离
    public static Map<String,Double> getMap(double dis, double longitude, double latitude) {
        HashMap<String,Double> map = new HashMap<>();
        try {
            double r = 6371;//地球半径千米
            double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(latitude*Math.PI/180));
            dlng = dlng*180/Math.PI;//角度转为弧度
            double dlat = dis/r;
            dlat = dlat*180/Math.PI;
            double minlat =latitude-dlat;
            double maxlat = latitude+dlat;
            double minlng = longitude -dlng;
            double maxlng = longitude + dlng;
            map.put("minlat", minlat);
            map.put("maxlat", maxlat);
            map.put("minlng", minlng);
            map.put("maxlng", maxlng);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 方法四：（高德地图计算方法）
     *
     * @param longitude1 第一点的经度
     * @param latitude1  第一点的纬度
     * @param longitude2 第二点的经度
     * @param latitude2  第二点的纬度
     * @return 返回的距离，单位m
     */
    public static Double getDistance4(double longitude1, double latitude1, double longitude2, double latitude2) {
        if (longitude1 == 0 || latitude1 == 0 || latitude2 == 0 || longitude2 == 0) {
            return -1.0;
        }
        longitude1 *= 0.01745329251994329;
        latitude1 *= 0.01745329251994329;
        longitude2 *= 0.01745329251994329;
        latitude2 *= 0.01745329251994329;
        double var1 = Math.sin(longitude1);
        double var2 = Math.sin(latitude1);
        double var3 = Math.cos(longitude1);
        double var4 = Math.cos(latitude1);
        double var5 = Math.sin(longitude2);
        double var6 = Math.sin(latitude2);
        double var7 = Math.cos(longitude2);
        double var8 = Math.cos(latitude2);
        double[] var10 = new double[3];
        double[] var20 = new double[3];
        var10[0] = var4 * var3;
        var10[1] = var4 * var1;
        var10[2] = var2;
        var20[0] = var8 * var7;
        var20[1] = var8 * var5;
        var20[2] = var6;

        double distance = Math.asin(Math.sqrt((var10[0] - var20[0]) * (var10[0] - var20[0]) + (var10[1] - var20[1]) * (var10[1] - var20[1]) + (var10[2] - var20[2]) * (var10[2] - var20[2])) / 2.0) * 1.27420015798544E7;
        double distancekm = distance/1000;
        // 结果四舍五入 保留1位小数
        return new BigDecimal(distancekm).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }


    /**
     * 地球平均半径（单位：米）
     */
    private static final double EARTH_AVG_RADIUS = 6371000;

    /**
     * 经纬度转化为弧度(rad)
     *
     * @param d 经度/纬度
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 方法三：（基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多。）
     *
     * @param longitude1 第一点的经度
     * @param latitude1  第一点的纬度
     * @param longitude2 第二点的经度
     * @param latitude2  第二点的纬度
     * @return 返回的距离，单位m
     */
    public static double getDistance2(double longitude1, double latitude1, double longitude2, double latitude2) {
        double radLat1 = rad(latitude1);
        double radLat2 = rad(latitude2);
        double a = radLat1 - radLat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_AVG_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        return s;
    }




}
