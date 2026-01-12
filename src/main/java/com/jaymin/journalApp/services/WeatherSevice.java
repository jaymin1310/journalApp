package com.jaymin.journalApp.services;

import com.jaymin.journalApp.apiResponse.WeatherResponse;
import com.jaymin.journalApp.cache.AppCache;
import com.jaymin.journalApp.constant.PlaceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherSevice {
    @Value("${weather.api.key}")
    private String key;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AppCache Cache;
    @Autowired
    RedisService redisService;

    public WeatherResponse GetWeather(String city){
        WeatherResponse weatherResponse=redisService.get("weather_of_"+city,WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else{
            String url=Cache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolder.API_KEY,key).replace(PlaceHolder.CITY,city);
            // CONVERTING JSON TO POJO (DESERIALIZATION)
            ResponseEntity<WeatherResponse> response=restTemplate.exchange(url, HttpMethod.GET,null, WeatherResponse.class);
            WeatherResponse body=response.getBody();
            if(body!=null){
                redisService.set("weather_of_"+city,body,300l);
            }
            return body;
        }
        /*POST method including headers
        if you want to send post method only then send only single peram in HttpEntity
        HttpHeaders httpHeaders=new HttpHeader();
        httpHeaders.set("key","value");
        User user=User.builder.userName("Jaymin").password("jaymin").build();
        HttpEntity<User>httpEntity=new HttpEntity<>(user,httpHeaders);
        ResponseEntity<WeatherResponse> response=restTemplate.exchange(url, HttpMethod.POST,httpEntity, WeatherResponse.class);
        ..you can send header also
         */
    }
}
