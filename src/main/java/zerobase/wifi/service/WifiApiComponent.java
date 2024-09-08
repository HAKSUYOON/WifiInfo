package zerobase.wifi.service;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static zerobase.wifi.model.WifiInfoModel.insertWifiInfo;;

public class WifiApiComponent {
	
	public static int totalCount() throws IOException {
		int cnt = 0;
		
		try {
			String url = "http://openapi.seoul.go.kr:8088/705369666c62617739306a524b4a56/json/TbPublicWifiInfo/1/1";
			OkHttpClient okHttpClient = new OkHttpClient();
			
			Request.Builder builder = new Request.Builder().url(url).get();
			Request request = builder.build();
			
			Response response = okHttpClient.newCall(request).execute();
			
			if (response.isSuccessful()) {
				ResponseBody responseBody = response.body();
				
				if (responseBody != null) {
					JsonElement jsonElement = JsonParser.parseString(responseBody.string());
					
					cnt = jsonElement.getAsJsonObject().get("TbPublicWifiInfo").getAsJsonObject().get("list_total_count").getAsInt();
					
					System.out.println("list_total_count: " + cnt);
				} 
			} else {
				System.out.println("Error Occurred" + response.code());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;		
	}
	
    public static int getWipiInfoList() throws IOException {
    	int startPage = 0;
    	int endPage = 0;
    	int totalCount = totalCount();
    	int cnt = 0;
    	StringBuilder sb = new StringBuilder();
    	
    	try {
    		
    		for (int i=0; i < totalCount / 1000; i++) {
    			startPage = 1 + (1000*i);
    			endPage = (i+1) * 1000;
    			
    			String url = "http://openapi.seoul.go.kr:8088/705369666c62617739306a524b4a56/json/TbPublicWifiInfo/";
    			sb.append(url).append(startPage).append("/").append(endPage);
        		OkHttpClient okHttpClient = new OkHttpClient();
        		
        		Request.Builder builder = new Request.Builder().url(sb.toString()).get();
    			Request request = builder.build();
    			
    			Response response = okHttpClient.newCall(request).execute();
    			
    			if (response.isSuccessful()) {
    				ResponseBody responseBody = response.body();
    				
    				if (responseBody != null) {
    					JsonElement jsonElement = JsonParser.parseString(responseBody.string());
    					
    					JsonArray jsonArray = jsonElement.getAsJsonObject().get("TbPublicWifiInfo").getAsJsonObject().get("row").getAsJsonArray();
    					
    					cnt += insertWifiInfo(jsonArray);
    					
    					System.out.println("insert_wifiInfo_count: " + cnt);
    				} 
    			} else {
    				System.out.println("Error Occurred" + response.code());
    			}
    		} 		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

        return cnt;
    }
    
}