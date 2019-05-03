package edu.cqupt.mislab.erp.commons.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author chuyunfei
 * @description 
 * @date 12:28 2019/5/3
 **/

@Configuration
public class VerificationCodeConfig {

    @Bean
    public Producer verificationCodeProducer(){

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        Properties properties = new Properties();

        //验证码宽度
        properties.setProperty("kaptcha.image.width","110");
        //验证码高度
        properties.setProperty("kaptcha.image.height","58");
        //验证码内容范围
        properties.setProperty("kaptcha.textproducer.char.string","234679ACDEFGHJKMNPQRTUVWXYZ");
        //验证码个数
        properties.setProperty("kaptcha.textproducer.char.length","4");
        //是否有边框
        properties.setProperty("kaptcha.border","yes");
        //验证码字体颜色
        properties.setProperty("kaptcha.textproducer.font.color","black");
        //验证码字体大小
        properties.setProperty("kaptcha.textproducer.font.size","32");
        //验证码所属字体样式
        properties.setProperty("kaptcha.textproducer.font.names","楷体");
        //干扰线颜色
        properties.setProperty("kaptcha.noise.color","black");
        //验证码文本字符间距
        properties.setProperty("kaptcha.textproducer.char.space","4");
        //图片样式 :阴影
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");

        Config config = new Config(properties);

        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
