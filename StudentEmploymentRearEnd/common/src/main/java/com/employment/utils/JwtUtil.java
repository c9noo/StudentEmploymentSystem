package com.employment.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @program: StudentEmploymentSystem
 * @ClassName JwtUtil
 * @author: c9noo
 * @create: 2023-11-07 18:56
 * @Version 1.0
 * JWT工具类
 **/
@AllArgsConstructor
@Data
public class JwtUtil {


    //有效期为60 * 60 *1000  一个小时,毫秒为单位
    public Long time;
    //设置秘钥明文
    public String key;

    public String word;

    public String jwtName;


    /**
     * 生成UUID并且返回
     * @return
     */
    public String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成jwt
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成jwt
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成jwt
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    /**
     * 创建一个令牌构建器对象
     * @param subject 用户或实体信息
     * @param ttlMillis 过期时间
     * @param uuid 唯一标识
     * @return
     */
    private JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        //定义JWT的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取JWT令牌的加密秘钥。这个秘钥用于生成JWT令牌的签名
        SecretKey secretKey = generalKey();
        //获取当前时间的毫秒数，用于设置JWT令牌的签发时间和过期时间
        long nowMillis = System.currentTimeMillis();
        //使用当前时间的毫秒数创建一个 Date 对象，表示JWT令牌的签发时间
        Date now = new Date(nowMillis);
        //如果为空，那么有效期就是1个小时
        if(ttlMillis==null){
            ttlMillis=time;
        }
        //计算JWT令牌的过期时间的毫秒数，即当前时间加上传入的过期时间
        long expMillis = nowMillis + ttlMillis;
        //使用过期时间的毫秒数创建一个 Date 对象，表示JWT令牌的过期时间
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer(word)     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(key);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
