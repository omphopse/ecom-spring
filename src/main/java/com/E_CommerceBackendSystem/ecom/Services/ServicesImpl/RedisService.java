package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

//@Service
//@Slf4j
//public class RedisService {
//
//		@Autowired
//		private RedisTemplate redisTemplate;
//		
//		public void set(String key, Object o, long ttl) {
//			try{
//	            ObjectMapper mapper = new ObjectMapper();
//	            String jsonValue=mapper.writeValueAsString(o);
//	            redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.SECONDS);
//	        }
//	        catch (Exception e){
//	            log.error("Error "+e);
//	        }
//		}
//		
////		public <T> T get(String key, Class<T> entityClass){
////	        try{
////	            Object o = redisTemplate.opsForValue().get(key);
////	            ObjectMapper objectMapper=new ObjectMapper();
////	            return objectMapper.readValue(o.toString(),entityClass);
////	        }
////	        catch (Exception e){
////	            log.error("Error "+e);
////	            return null;
////	        }
////	    }
//		
//		public <T> T get(String key, TypeReference<T> typeReference){
//		    try{
//		        Object o = redisTemplate.opsForValue().get(key);
//		        ObjectMapper objectMapper = new ObjectMapper();
//		        return objectMapper.readValue(o.toString(), typeReference);
//		    }
//		    catch (Exception e){
//		        log.error("Error "+e);
//		        return null;
//		    }
//		}
//}
@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void set(String key, Object value, long ttl) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis SET error for key: {}", key, e);
        }
    }

    public <T> T get(String key, TypeReference<T> typeReference) {
        try {
            String json = redisTemplate.opsForValue().get(key);

            if (json == null || json.isEmpty()) return null;

            return objectMapper.readValue(json, typeReference);

        } catch (Exception e) {
            log.error("Redis GET error for key: {}", key, e);
            return null;
        }
    }
}