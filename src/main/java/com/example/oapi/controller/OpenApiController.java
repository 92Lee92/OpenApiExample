package com.example.oapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

@RestController 
public class OpenApiController {
	final String serviceKey ="sF2kWpJCqvVvJF0zuavf6LC0m1P48NBstv8HshsOHBK3godYhKaIXH7mX2SoTlVaEiszDcog80DMHr3RYF6SKQ%3D%3D";
	@GetMapping("/weather")
	public String weather() {
		try {
			
			//String decodeServiceKey = "sF2kWpJCqvVvJF0zuavf6LC0m1P48NBstv8HshsOHBK3godYhKaIXH7mX2SoTlVaEiszDcog80DMHr3RYF6SKQ==";
			//String decodedServiceKey = URLEncoder.encode(decodeServiceKey,"UTF-8");
	        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
	        //urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8")+ "=" + decodedServiceKey);
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
	        urlBuilder.append("&" + URLEncoder.encode("stnId","UTF-8") + "=" + URLEncoder.encode("108", "UTF-8")); /*108 전국, 109 서울, 인천, 경기도 등 (활용가이드 하단 참고자료 참조)*/
	        urlBuilder.append("&" + URLEncoder.encode("tmFc","UTF-8") + "=" + URLEncoder.encode("202211300600", "UTF-8")); /*-일 2회(06:00,18:00)회 생성 되며 발표시각을 입력 YYYYMMDD0600 (1800)-최근 24시간 자료만 제공*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
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
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(sb.toString());
	        JsonObject responseObject = element.getAsJsonObject().get("response").getAsJsonObject();
	        JsonObject bodyObject = responseObject.get("body").getAsJsonObject();
	        JsonObject itemsObject = bodyObject.get("items").getAsJsonObject();
	        JsonArray itemArray = itemsObject.get("item").getAsJsonArray();
	        JsonObject item = itemArray.get(0).getAsJsonObject();
	        String data = item.get("wfSv").getAsString();
	        System.out.println(data);
	        
	        return data;
			}catch (Exception e) {
			 e.printStackTrace();
			 return e.getMessage();
			
		}
    }
	@GetMapping("/mountain")
	public String mountain() {
		try {
			StringBuilder urlBuilder = new StringBuilder("http://openapi.forest.go.kr/openapi/service/cultureInfoService/gdTrailInfoImgOpenAPI"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") +"="+ serviceKey); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("searchWrd","UTF-8") + "=" + URLEncoder.encode("469000401", "UTF-8")); /*469000401(예:남망산)*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
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
	        System.out.println(sb.toString());
	        return sb.toString();
		}catch(Exception e) {
			return e.getMessage();
		}
		
	}
	
	@GetMapping("/stock")
	public String stock() {
		StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo");
		try {
			
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") +"="+ serviceKey); /*Service Key*/
			
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        //urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() == 200) {
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
	        return  sb.toString();
		}catch(Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
//	@GetMapping("/energy")
	
	
}




