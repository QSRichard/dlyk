package com.richard;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.Executor;

@MapperScan(basePackages = {"com.richard.mapper"})
@SpringBootApplication
public class DlykServerApplication implements CommandLineRunner {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DlykServerApplication.class, args);
        String[] beanNames = context.getBeanNamesForType(Executor.class);

        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        System.out.println("Hello World!");
    }


    @Override
    public void run(String... args) throws Exception {

        // Spring项目启动后，修改redisTemplate bean 修改key value的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 对象映射工具 JAVA对象和JSON对象互相转化
        ObjectMapper mapper = new ObjectMapper();

        // 设置可见性
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 激活类型
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING);

        // 设置value序列化
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(mapper, Object.class));

        // 设置hashKey序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // 设置hashValue序列化
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(mapper, Object.class));


    }

}
