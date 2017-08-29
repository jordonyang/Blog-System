package com.yang.www.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {
    public static String encode(String str,String salt){
        return new Md5Hash(str,salt).toString();
    }

    public static void main(String[] args) {
        System.out.println(CryptographyUtil.encode("1","blog"));;
    }
}
