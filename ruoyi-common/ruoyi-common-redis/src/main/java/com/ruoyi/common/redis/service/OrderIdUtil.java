package com.ruoyi.common.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 生成订单id
 */
@Component
public class OrderIdUtil {

    @Autowired
    private RedisService redisService;

    //初始值
    private static final Integer ORDER_INIT = 0;
    //订单缓存key
    private static final String ORDER_KEY = "HY_ORDER_KEY";

    private static final String WITHDW_KEY = "DY_WITHDW_KEY";
    //订单ID前缀
    private static final String ORDER_ID_PREFIX = "Y";

    private static final String WITHDW_ID_PREFIX = "T";
    //时间格式化
    public static final String DATE_FORMAT = "yyyyMMdd";
    private static final int MAXLEN = 5;


    /**
     * 通过key获取自增并设定过期时间
     *
     * @param key
     * @param  time 过期时间
     * @return
     */
    public String generateId(String key, Long time) {
        try {
            long orderId = redisService.incr(key,1);
            if (orderId == 1) {
                redisService.expire(key, time);
            }
            int len = String.valueOf(orderId).length();
            StringBuffer sb = new StringBuffer();
            if (len < MAXLEN) {
                for (int i = 0; i < (MAXLEN - len); i++) {
                    sb.append("0");
                }
                sb.append(orderId);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
}

    public String generateId(String key, Long time,int maxlen) {
        try {
            long orderId = redisService.incr(key,1);
            if (orderId == 1) {
                redisService.expire(key, time);
            }
            int len = String.valueOf(orderId).length();
            StringBuffer sb = new StringBuffer();
            if (len < maxlen) {
                for (int i = 0; i < (maxlen - len); i++) {
                    sb.append("0");
                }
                sb.append(orderId);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("generateId  is failed.");
        }
    }

    public String generateId(String key) {
        try {
            long orderId = redisService.incr(key,1);
            if (orderId == 1) {
                Long time = getRemainSecondsOneDay(new Date());
                redisService.expire(key, time);
            }
            int len = String.valueOf(orderId).length();
            StringBuffer sb = new StringBuffer();
            if (len < MAXLEN) {
                for (int i = 0; i < (MAXLEN - len); i++) {
                    sb.append("0");
                }
                sb.append(orderId);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public long getRemainSecondsOneDay(Date currentDate) {
        //使用plusDays加传入的时间加1天，将时分秒设置成0
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                        ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        //使用ChronoUnit.SECONDS.between方法，传入两个LocalDateTime对象即可得到相差的秒数
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return seconds;
    }
    public long getRemainSecondsOneDay(Date currentDate,Date target) {
        //使用plusDays加传入的时间加1天，将时分秒设置成0
        LocalDateTime midnight = LocalDateTime.ofInstant(target.toInstant(),
                        ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        //使用ChronoUnit.SECONDS.between方法，传入两个LocalDateTime对象即可得到相差的秒数
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return seconds;
    }

    /**
     * 获取唯一订单号
     *
     * @return
     */
    public String genOrderId() {
        String id = this.generateId(ORDER_KEY);
        return format(id, ORDER_ID_PREFIX);
    }

    public String genWithdrId() {
        String id = this.generateId(WITHDW_KEY);
        return format(id, WITHDW_ID_PREFIX);
    }

    /**
     * 过期时间每天凌晨
     *
     * @return
     */
    public Date getExpireAtData() {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        // 时
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        calendar.set(Calendar.MINUTE, 0);
        // 秒
        calendar.set(Calendar.SECOND, 0);
        // 毫秒
        calendar.set(Calendar.MILLISECOND, 0);

        date = calendar.getTime();
        return date;
    }


    /**
     * 格式化时间
     *
     * @param format 时间格式
     * @return
     */
    public String getDateFormat(String format) {
        DateFormat df = new SimpleDateFormat(null == format || "".equals(format) ? DATE_FORMAT : format);
        return df.format(new Date());
    }


    /**
     * 自定义ID格式
     *
     * @param id
     * @param prefix 前缀
     * @return
     */
    private String format(String id, String prefix) {
        StringBuffer sb = new StringBuffer();
        if (null != prefix && !"".equals(prefix)) {
            sb.append(prefix);
        }
        sb.append(getDateFormat(null));//当前时间戳

        String strId = sb.toString() + id;
        return strId;
    }

}
