package com.opensw.safeguard.controller.weather;

import com.opensw.safeguard.domain.weather.Weather;
import com.opensw.safeguard.domain.weather.WeatherRequestDTO;
import com.opensw.safeguard.domain.weather.WeatherResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
@Slf4j
public class WeatherController {
    @Value("${weather-serviceKey}")
    private String serviceKey;



    @PostMapping("/weather")
    public ResponseEntity<WeatherResponseDTO> weather(@RequestBody WeatherRequestDTO weatherRequestDTO){
        String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        String pageNo = "1";	//페이지 번호
        String numOfRows = "288";	//한 페이지 결과 수
        String dataType = "JSON";	//데이터 타입

        /**
         조회 날짜 -1 , 기준시간 2300 , 페이지 결과수 288하면 조회날짜의 모든 데이터를 받아 올 수 있음
         **/
        LocalDateTime now = LocalDateTime.now().minusDays(1);
        String base_date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));//조회 날짜 -1
        String base_time = "2300";	//조회하고 싶은 날짜의 시간 날짜
        String nx = weatherRequestDTO.getNx();	//x좌표
        String ny = weatherRequestDTO.getNy();	//y좌표
        try {
            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(base_date, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(base_time, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8"));
            /*
             * GET방식으로 전송해서 파라미터 받아오기
             */
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            log.info("Response code: {} ",conn.getResponseCode());
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


            double maxTemp=0;
            double minTemp=0;
            double probability_Of_Precipitation=0;
            for(int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                String category = obj.getString("category");
                if(!category.equals("TMX")&&!category.equals("TMN")&&!category.equals("POP")) {
                    continue;
                }
                Double fcstValue = obj.getDouble("fcstValue");
                switch (category) {
                    case "TMX":
                        maxTemp = fcstValue;

                        break;
                    case "TMN":

                        minTemp = fcstValue;
                        break;
                    case "POP":
                        if (fcstValue > probability_Of_Precipitation)
                            probability_Of_Precipitation = fcstValue;
                        break;
                }

            }
            Weather weather = Weather.builder()
                    .maxTemp(maxTemp)
                    .minTemp(minTemp)
                    .max_Probability_Of_Precipitation(probability_Of_Precipitation)
                    .build();
            WeatherResponseDTO dto = WeatherResponseDTO.builder()
                    .weather(weather)
                    .message("OK")
                    .build();


            return ResponseEntity.ok().body(dto);
        }
        catch (JSONException e){
            WeatherResponseDTO dto = WeatherResponseDTO.builder()
                    .weather(null)
                    .message("body정보 오류")
                    .build();
            return ResponseEntity.ok().body(dto);
        }
        catch (IOException e){
            WeatherResponseDTO dto = WeatherResponseDTO.builder()
                    .weather(null)
                    .message("날씨정보 오류")
                    .build();
            return ResponseEntity.ok().body(dto);
        }
    }
}
