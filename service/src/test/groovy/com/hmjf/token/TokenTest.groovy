package com.hmjf.token

import com.hmjf.utils.Encodes
import org.junit.Assert
import org.junit.Test

/**
 * Created by jack on 16/1/2.
 */
class TokenTest {

    @Test
    def void longToVarHex(){
        long expect = Long;
        def tmp = Encodes.longToVarHex(expect);
        System.out.println(tmp)
        long ret = Encodes.varHexToLong(tmp);
        System.out.println(ret)
        Assert.assertEquals(expect,ret)
    }

}
