package zerobase.wifi.service;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

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
			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
			urlBuilder.append("/" +  URLEncoder.encode("705369666c62617739306a524b4a56","UTF-8") );
			urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") );
			urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode("1","UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode("1","UTF-8"));
			
			
			URL url = new URL(urlBuilder.toString());
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
    	int startPage = 1;
    	int endPage = 1;
    	int totalCount = totalCount();
    	int cnt = 0;
    	
    	try {
    		
    		for (int i=0; i <= totalCount / 1000; i++) {
    			startPage = 1 + (1000*i);
    			endPage = (i+1) * 1000;
    			
    			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
    			urlBuilder.append("/" +  URLEncoder.encode("705369666c62617739306a524b4a56","UTF-8") );
    			urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") );
    			urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
    			urlBuilder.append("/" + URLEncoder.encode(Integer.toString(startPage),"UTF-8"));
    			urlBuilder.append("/" + URLEncoder.encode(Integer.toString(endPage),"UTF-8"));
    			
    			
    			URL url = new URL(urlBuilder.toString());
        		OkHttpClient okHttpClient = new OkHttpClient();
        		
        		Request.Builder builder = new Request.Builder().url(url).get();
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