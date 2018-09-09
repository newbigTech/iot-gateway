package com.newbig.im.common.utils;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.concurrent.TimeUnit;

public class PasswordUtils {
    public static final int HASH_INTERATIONS = 512;
    public static final int SALT_SIZE = 8;

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        String plain = StringEscapeUtils.unescapeHtml4(plainPassword);
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    public static boolean validatePasswordMD5(String plainPassword, String password) {
        String passwd = Encodes.encodeBase64(Encodes.encodeHex(Digests.md5(plainPassword.getBytes())));
        return passwd.equals(password);
    }

    public static boolean validatePasswordBase64(String plainPassword, String password) {
        String passwd = Encodes.encodeBase64(Encodes.encodeHex(Digests.md5(plainPassword.getBytes())).getBytes());
        return passwd.equals(password);
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 1000; i++) {
            String a = entryptPassword("aaaa");
            System.out.println(validatePassword("aaaa", a));
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

}
