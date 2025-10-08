package com.ruoyi.common.core.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SendvoiceUtil {
    private static final Logger log = LoggerFactory.getLogger(SendvoiceUtil.class);


    private static String Url = "http://api.vm.ihuyi.com/webservice/voice.php?method=Submit&account=APIID&password=APIKEY&mobile=MOBILE&content=CONTENT&format=json";
    public  String sendVoice(String phone,String content){
        String result = "";
        String newUrl = Url.replace("APIID", "VM58561768").
                replace("APIKEY", "ff0d809894f5a323fd6c6e9a7191a979");
        String newUrl1 = newUrl.replace("MOBILE",phone);
        String newUrl2 = newUrl1.replace("CONTENT", content);

        log.info(newUrl2);
        result = HttpUtils.sendGet(newUrl2);
        return result;



//        try {
////            client.executeMethod(method);
////
////            String SubmitResult = method.getResponseBodyAsString();
////
////            //System.out.println(SubmitResult);
////
////            Document doc = DocumentHelper.parseText(SubmitResult);
////            Element root = doc.getRootElement();
////
////            String code = root.elementText("code");
////            String msg = root.elementText("msg");
////            String voiceid = root.elementText("voiceid");
//
//            System.out.println(code);
//            System.out.println(msg);
//            System.out.println(voiceid);
//            Map<String,Object> map = new HashMap<>();
//            map.put("code",code);
//            map.put("msg",msg);
//            map.put("voiceid",voiceid);
//            return map;
//
//        } catch (HttpException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }
}
