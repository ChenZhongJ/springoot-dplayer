package com.player.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.player.service.RedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.UnknownHostException;

@Configuration
//springboot自动管理httpSession
@PropertySource(value = "classpath:/redis.properties")
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    /**
     * 连接池
     * @return
     */
    @Bean
    public JedisPool redisPoolFactory() {

        System.out.println("注入成功");

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 最小空闲数
        jedisPoolConfig.setMinIdle(minIdle);
        // 最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);

        return jedisPool;
    }

    /**
     * 配置
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        //IP地址
        jedisConnectionFactory.setHostName(host);
        //端口
        jedisConnectionFactory.setPort(port);
        //密码
        jedisConnectionFactory.setPassword(password);
        //客户端超时时间，单位毫秒
        jedisConnectionFactory.setTimeout(timeout);
        //数据库数量
        jedisConnectionFactory.setDatabase(database);

        return jedisConnectionFactory;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public RedisTemplate<String,Object> redisTemplate()throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(redisConnectionFactory());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 注入封装的redisTemplate
     * @param template
     * @return
     */
    @Bean(name = "redisUtil")
    public RedisDao redisUtil(RedisTemplate<String, Object> template){

        RedisDao redisDao = new RedisDao();

        redisDao.setRedisTemplate(template);

        return redisDao;
    }

//    /**
//     * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key,即使@Cacheable中的value属性一样，key也会不一样。
//     *
//     * @return
//     */
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                //格式化缓存key字符串
//                StringBuilder sb = new StringBuilder();
//                //追加类名
//                sb.append(target.getClass().getName());
//                //追加方法名
//                sb.append(method.getName());
//                //遍历参数并且追加
//                for (Object object : params) {
//                    sb.append(object.toString());
//                }
//                return sb;
//            }
//        };
//    }
//
//    /**
//     * 设置缓存过期时间
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @SuppressWarnings("rawtypes")
//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
//        //设置缓存过期时间(秒)
//        rcm.setDefaultExpiration(60 * 60 * 24);
//        return rcm;
//    }
//
//    /**
//     * RedisTemplate缓存操作类,类似于jdbcTemplate的一个类
//     *
//     * @param factory 通过Spring进行注入，参数在application.properties进行配置
//     * @return
//     */
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
//
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
//
//        redisTemplate.setConnectionFactory(factory);
//        //key序列化，不然会出现乱码,不能出现long类型的数据，否则会报类型转换错误
//        //自己实现ObjectRedisSerializer或者JdkSerializationRedisSerializer序列化方式
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(redisSerializer);
//        redisTemplate.setHashKeySerializer(redisSerializer);
//
//        return redisTemplate;
//    }

}
