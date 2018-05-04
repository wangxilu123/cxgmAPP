package com.cxgm.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.cxgm.domain.RedisKeyDto;
import com.cxgm.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {
	
	@Resource  
    RedisTemplate<String, String> redisTemplate;
	
	@Override
    public void addData(final RedisKeyDto redisKeyDto) {
		redisTemplate.opsForValue().set(redisKeyDto.getKeys(), redisKeyDto.getValues());
    }

    @Override
    public void delete(RedisKeyDto redisKeyDto) {
        redisTemplate.delete(redisKeyDto.getKeys());
    }

    @Override
    public RedisKeyDto redisGet(RedisKeyDto redisKeyDto) {
    	RedisKeyDto re=new RedisKeyDto();
    	Object ob = redisTemplate.opsForValue().get(redisKeyDto.getKeys());
    	if(null != ob){
            re.setKeys(redisKeyDto.getKeys());
            re.setValues((String)ob);
            return re;
    	}
    	return null;
    	
    }

    @Override
    public void addRedisData(RedisKeyDto redisKeyDto, int outTime) {
    	addData(redisKeyDto);
    	redisTemplate.expire(redisKeyDto.getKeys(), outTime, TimeUnit.MILLISECONDS);
    }

}
