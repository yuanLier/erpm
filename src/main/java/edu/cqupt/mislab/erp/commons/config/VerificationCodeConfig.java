package edu.cqupt.mislab.erp.commons.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class VerificationCodeConfig {

    @Bean
    public Producer verificationCodeProducer(){

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        Properties properties = new Properties();

        //验证码宽度
        properties.setProperty("kaptcha.image.width","100");
        //验证码高度
        properties.setProperty("kaptcha.image.height","50");
        //验证码内容范围
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        //验证码个数
        properties.setProperty("kaptcha.textproducer.char.length","5");
        //是否有边框
        properties.setProperty("kaptcha.border","no");
        //验证码字体颜色
        properties.setProperty("kaptcha.textproducer.font.color","black");
        //验证码字体大小
        properties.setProperty("kaptcha.textproducer.font.size","30");
        //验证码所属字体样式
        properties.setProperty("kaptcha.textproducer.font.names","楷体");
        //干扰线颜色
        properties.setProperty("kaptcha.noise.color","black");
        //验证码文本字符间距
        properties.setProperty("kaptcha.textproducer.char.space","3");
        //图片样式 :阴影
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");

        Config config = new Config(properties);

        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
