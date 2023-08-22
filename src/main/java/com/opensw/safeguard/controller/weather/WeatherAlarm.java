package com.opensw.safeguard.controller.weather;

import com.opensw.safeguard.domain.DeviceToken;
import com.opensw.safeguard.service.alarm.FirebaseCloudMessageService;
import com.opensw.safeguard.service.devicetoken.DeviceTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherAlarm {

    @Value("${weather-serviceKey}")
    private String serviceKey;
    private final static String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";
    private final static String pageNo = "1";
    private final static String numOfRows = "1000";
    private final static String dataType = "JSON";
    private final WeatherStorage weatherStorage;
    private final DeviceTokenService deviceTokenService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Scheduled(fixedRate = 3600000) // 1시간마다(60 * 60 * 1000 ms)
    public void weatherFirebaseAlarm() {

        Date d = new Date();
        SimpleDateFormat f1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat f2 = new SimpleDateFormat("HH");
        String baseDate = f1.format(d);
        String baseTime = Integer.parseInt(f2.format(d))-1+"00";
        String nx = "55";
        String ny = "127";

        if (baseTime.length() == 3) {
            baseTime = "0" + baseTime;
        }

        StringBuilder stringBuilder = new StringBuilder(apiUrl);
        try {
            stringBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey);
            stringBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
            stringBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
            stringBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
            stringBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
            stringBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
            stringBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8"));
            stringBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8"));

            URL url = new URL(stringBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String data= sb.toString();

            JSONObject jObject = new JSONObject(data);
            JSONObject response = jObject.getJSONObject("response");
            JSONObject body = response.getJSONObject("body");
            JSONObject items = body.getJSONObject("items");
            JSONArray jArray = items.getJSONArray("item");

            double temperature = 0.0;

            for(int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                String category = obj.getString("category");
                if (Integer.parseInt(obj.getString("fcstTime")) > Integer.parseInt(baseTime) + 100) {
                    continue;
                }
                if(!category.equals("T1H")) {
                    continue;
                }
                Double fcstValue = obj.getDouble("fcstValue");
                switch (category) {
                    case "T1H": // 기온
                        temperature = fcstValue;
                        break;
                }
            }

            if (temperature >= 30.0 && weatherStorage.getPreviousAlarm() == false) {
                weatherStorage.updatePreviousTemperature(temperature);
                weatherStorage.updatePreviousAlarm(true);
                List<DeviceToken> findDeviceTokens = deviceTokenService.findAll();
                for (DeviceToken findDeviceToken : findDeviceTokens) {
                    firebaseCloudMessageService.sendMessageTo(findDeviceToken.getToken(), "폭염주의", "현재 " + temperature + " 이므로 주의하세요.");
                }
            } else if (temperature < 30) {
                weatherStorage.updatePreviousAlarm(false);
                weatherStorage.updatePreviousTemperature(temperature);
            }

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
