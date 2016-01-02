package com.hmjf.web.utils

import org.slf4j.MDC


/**
 * Created by jack on 16/1/2.
 */
class LoggerHelp {

    final static def DTOKEN = "DToken";
    final static def GlobalTrackId = "GlobalTrackId"
    final static def UserID = "UserID"

    def static initMDC(String dtoken){
        MDC.put(DTOKEN,dtoken)
        MDC.put(GlobalTrackId,UUID.randomUUID().toString())
    }

    def static initUserID(long uid){
        MDC.put(UserID,uid.toString())
    }

}
