package com.cyj;


import com.cyj.utils.KeyUtils;
import com.cyj.utils.Sm2Util;
import org.junit.Test;

import java.util.Base64;

/**
 * @author WangJing
 * @Description Sm2Util 的测试类
 * @date 2021/11/24 16:10
 */
public class Sm2UtilTest {

    private String testStr = "chenyijun";

    java.security.PublicKey publicKey = null;
    java.security.PrivateKey privateKey = null;


    @Test
    public void test() throws Exception {
        //生成公私钥对
        String[] keys = KeyUtils.generateSmKey();

        System.out.println("原始字符串：" + testStr);
        System.out.println("公钥：" + keys[0]);
        publicKey = KeyUtils.createPublicKey(keys[0]);

        System.out.println("私钥：" + keys[1]);
        privateKey = KeyUtils.createPrivateKey(keys[1]);

        System.out.println("");


        byte[] encrypt = Sm2Util.encrypt(testStr.getBytes(), publicKey);
        String encryptBase64Str = Base64.getEncoder().encodeToString(encrypt);
        System.out.println("加密数据：" + encryptBase64Str);

        byte[] decode = Base64.getDecoder().decode(encryptBase64Str);
        byte[] decrypt = Sm2Util.decrypt(decode, privateKey);
        System.out.println("解密数据：" + new String(decrypt));

        byte[] sign = Sm2Util.signByPrivateKey(testStr.getBytes(), privateKey);
        System.out.println("数据签名：" + Base64.getEncoder().encodeToString(sign));

        boolean b = Sm2Util.verifyByPublicKey(testStr.getBytes(), publicKey, sign);
        System.out.println("数据验签：" + b);
    }
}
