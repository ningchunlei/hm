package com.hmjf.token;

import com.hmjf.domain.SessionUser;
import com.hmjf.utils.Encodes;
import com.hmjf.utils.encrypt.Cryptos;
import com.hmjf.utils.encrypt.MD5Util;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by ningcl on 15/9/9.
 */
public class TokenUtils {

    private final static String sign = "HerNvb3414241PQCNDL877adfqGLKE)O#OK";

    public final static String UTOKEN = "utoken";
    public final static String DTOKEN = "dtoken";

    private static byte[] signBytes = null;

    private final static ThreadLocal<SessionUser> threadUsers = new ThreadLocal<SessionUser>();

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(TokenUtils.class);

    static {
        try {
            signBytes = sign.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static SessionUser sessionUser(){
        return threadUsers.get();
    }

    public static void storeSessionUser(SessionUser user){
        threadUsers.set(user);
    }

    public static void clearSession(){
        threadUsers.set(null);
    }

    public static enum TokenType {
         LLLEGAL,TEMP,LOGIN
    }

    public static String dToken(){
        return MD5Util.md5Hex(UUID.randomUUID().toString());
    }

    // when uid=0 token is temporary,when uid>0 is regular
    public static String uToken(Long uid){
        StringBuilder sb = new StringBuilder();
        sb.append(MD5Util.md5Hex(UUID.randomUUID().toString())).append("v1").append(Encodes.longToVarHex(uid));
        String tk = sb.toString();
        try {
            sb.append('_').append(Encodes.encodeHex(Cryptos.hmacSha1(tk.getBytes("UTF-8"), signBytes)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static Long uid(String token){
        String[] ts = token.split("_");
        String uidStr = ts[0].substring(32);
        if(uidStr.startsWith("v1")){
            uidStr = uidStr.substring(2);
        }else {
            return null;
        }

        try {
            if(!Encodes.encodeHex(Cryptos.hmacSha1(ts[0].getBytes("UTF-8"),signBytes)).equals(ts[1])){
                return null;
            }
            return Encodes.varHexToLong(uidStr);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }



}
