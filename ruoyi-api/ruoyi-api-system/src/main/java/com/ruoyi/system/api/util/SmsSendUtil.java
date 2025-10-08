package com.ruoyi.system.api.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class SmsSendUtil {
    //    @Autowired
//    private YachtInfoRepository yachtInfoRepository;
//
//    @Autowired
//    private UserInformationRepository userInformationRepository;
//
//    @Autowired
//    private RoleUserRepository roleUserRepository;
//
//    @Autowired
//    private YachtCrewRepository yachtCrewRepository;
//
//    @Autowired
//    private OrderExtraRepository orderExtraRepository;
//

    //
//    @Autowired
//    private BerthRepository berthRepository;
//
//    @Autowired
//    private SHyExplainRepository sHyExplainRepository;
    private Logger logger = LoggerFactory.getLogger(getClass());

    //===============================线下出售======================================




    public String getCrewRole(int crewRoleId) {
        switch (crewRoleId) {
            case 0:
                return "其他";
            case 1:
                return "游艇管理员";
            case 2:
                return "销售经理";
            case 3:
                return "接待员";
            case 4:
                return "船长";
            case 5:
                return "水手";
            case 6:
                return "船东";
            case 7:
                return "计调";
            default:
                return "无";

        }
    }

    public static String getJidiaoStatus(int status) {
        //	//0 已售 ;1取消出售 ;2接待; 3取消接待;4预留;5取消预留;6维修;7取消维修;8线上出售;9线上取消出售
        switch (status) {
            case 0:
                return "已售";
            case 1:
                return "取消出售";
            case 2:
                return "接待";
            case 3:
                return "取消接待";
            case 4:
                return "预留";
            case 5:
                return "取消预留";
            case 6:
                return "维修";
            case 7:
                return "取消维修";
            case 8:
                return "线上出售";
            case 9:
                return "线上取消出售";
            default:
                return "无";

        }
    }


}
