package com.jaymin.journalApp.services;

import com.jaymin.journalApp.apiResponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherSevice {
    private static final String key="049396ba38718c2c3c030c4c95488391";
    private static final String api="http://api.weatherstack.com/current?access_key=KEY&query=CITY";

    @Autowired
    RestTemplate restTemplate;

    public WeatherResponse GetWeather(String city){
        String url=api.replace("KEY",key).replace("CITY",city);
        //CONVERTING JAVA TO POJO->DESERIALIZATION
        ResponseEntity<WeatherResponse> response=restTemplate.exchange(url, HttpMethod.GET,null, WeatherResponse.class);
        return response.getBody();
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
