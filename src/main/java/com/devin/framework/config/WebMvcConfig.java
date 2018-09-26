package com.devin.framework.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 资源文件配置加载
 * 
 * @author devin
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{

    @Bean
    public LocaleResolver localeResolver()
    {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        // 默认语言
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        slr.setCookieName("lang");
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
    }


    /**
     * fastJson相关设置
     */
    private FastJsonConfig getFastJsonConfig() {

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 在serializerFeatureList中添加转换规则
        List<SerializerFeature> serializerFeatureList = new ArrayList<SerializerFeature>();
        serializerFeatureList.add(SerializerFeature.PrettyFormat);
        serializerFeatureList.add(SerializerFeature.WriteMapNullValue);
        serializerFeatureList.add(SerializerFeature.WriteNullStringAsEmpty);
        serializerFeatureList.add(SerializerFeature.WriteNullListAsEmpty);
        serializerFeatureList.add(SerializerFeature.DisableCircularReferenceDetect);
        SerializerFeature[] serializerFeatures = serializerFeatureList.toArray(new SerializerFeature[serializerFeatureList.size()]);
        fastJsonConfig.setSerializerFeatures(serializerFeatures);

        return fastJsonConfig;
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        fastConverter.setFastJsonConfig(getFastJsonConfig());
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }
}