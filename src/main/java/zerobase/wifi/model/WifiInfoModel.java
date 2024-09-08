package zerobase.wifi.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import zerobase.wifi.dto.WifiInfoDto;
import zerobase.wifi.service.MariadbConnection;
import static zerobase.wifi.model.PosHistoryModel.insertHistory;

public class WifiInfoModel {
	
	public WifiInfoModel() {}
	
	public static int insertWifiInfo(JsonArray jsonArray) {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        int count = 0;
        
        try {
        	connection = MariadbConnection.getConnect();
        	connection.setAutoCommit(false);
        	
        	String sql = " INSERT INTO wifi_info "
	        			+ " (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2 "
	        			+ " , X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR "
	        			+ " , X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT "
	        			+ " , WORK_DTTM )"
	        			+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); " ;
        	
        	for (int i = 0; i < jsonArray.size(); i++) {
        		
        		JsonObject db = (JsonObject)jsonArray.get(i).getAsJsonObject();
        	
        		preparedStatement = connection.prepareStatement(sql);
        		preparedStatement.setString(1, db.get("X_SWIFI_MGR_NO").getAsString());
        		preparedStatement.setString(2, db.get("X_SWIFI_WRDOFC").getAsString());
        		preparedStatement.setString(3, db.get("X_SWIFI_MAIN_NM").getAsString());
        		preparedStatement.setString(4, db.get("X_SWIFI_ADRES1").getAsString());
        		preparedStatement.setString(5, db.get("X_SWIFI_ADRES2").getAsString());
        		preparedStatement.setString(6, db.get("X_SWIFI_INSTL_FLOOR").getAsString());
        		preparedStatement.setString(7, db.get("X_SWIFI_INSTL_TY").getAsString());
        		preparedStatement.setString(8, db.get("X_SWIFI_INSTL_MBY").getAsString());
        		preparedStatement.setString(9, db.get("X_SWIFI_SVC_SE").getAsString());
        		preparedStatement.setString(10, db.get("X_SWIFI_CMCWR").getAsString());
        		preparedStatement.setString(11, db.get("X_SWIFI_CNSTC_YEAR").getAsString());
        		preparedStatement.setString(12, db.get("X_SWIFI_INOUT_DOOR").getAsString());
        		preparedStatement.setString(13, db.get("X_SWIFI_REMARS3").getAsString());
        		preparedStatement.setString(14, db.get("LAT").getAsString());
        		preparedStatement.setString(15, db.get("LNT").getAsString());
        		preparedStatement.setString(16, db.get("WORK_DTTM").getAsString());
        		
        		preparedStatement.addBatch();
        		preparedStatement.clearParameters();
        		
        		if((i+1) % 1000 == 0) {
        			int[] result = preparedStatement.executeBatch();
        			count += result.length;
        			connection.commit();
        		}
        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        	
        	try {
        		connection.rollback();
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        } finally {	
        	MariadbConnection.close(rs, preparedStatement, connection);
        }       
        return count;
	}
	
	public List<WifiInfoDto> selectNearWifiInfo(String lat, String lnt) {
		
		List<WifiInfoDto> list = new ArrayList<>();
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try {
        	
        	connection = MariadbConnection.getConnect();
        	
        	String sql = " SELECT id, X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1 "
	        			+ " , X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE "
	        			+ " , X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT "
	        			+ " , LNT, WORK_DTTM "
	        			+ " , round(6371*acos(cos(radians(LAT))*cos(radians(?))*(cos(radians(?))*cos(radians(LNT))+sin(radians(?))*sin(radians(LNT))) +sin(rad(LAT))*sin(radians(?))), 4) as distance "
	        			+ " FROM wifi_info "
	        			+ " order by distance "
	        			+ " limit 20; ";
        	
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setDouble(1, Double.parseDouble(lat));
        	preparedStatement.setDouble(2, Double.parseDouble(lnt));
        	preparedStatement.setDouble(3, Double.parseDouble(lnt));
        	preparedStatement.setDouble(4, Double.parseDouble(lat));
        	
        	rs = preparedStatement.executeQuery();
        	
        	while (rs.next()) {
        		WifiInfoDto wifiInfoDto = WifiInfoDto.builder()
        				.distance(rs.getDouble("distance"))
        				.x_swifi_mgr_no(rs.getString("X_SWIFI_MGR_NO"))
        				.x_swifi_wrdofc(rs.getString("X_SWIFI_WRDOFC"))
        				.x_swifi_main_nm(rs.getString("X_SWIFI_MAIN_NM"))
        				.x_swifi_adres1(rs.getString("X_SWIFI_ADRES1"))
        				.x_swifi_adres2(rs.getString("X_SWIFI_ADRES2"))
        				.x_swifi_instl_floor(rs.getString("X_SWIFI_INSTL_FLOOR"))
        				.x_swifi_instl_ty(rs.getString("X_SWIFI_INSTL_TY"))
        				.x_swifi_instl_mby(rs.getString("X_SWIFI_INSTL_MBY"))
        				.x_swifi_svc_se(rs.getString("X_SWIFI_SVC_SE"))
        				.x_swifi_cmcwr(rs.getString("X_SWIFI_CMCWR"))
        				.x_swifi_cnstc_year(rs.getString("X_SWIFI_CNSTC_YEAR"))
        				.x_swifi_inout_door(rs.getString("X_SWIFI_INOUT_DOOR"))
        				.x_swifi_remars3(rs.getString("X_SWIFI_REMARS3"))
        				.lat(rs.getString("LAT"))
        				.lnt(rs.getString("LNT"))
        				.work_dttm(rs.getString("WORK_DTTM"))
        				.build();
        		
        		list.add(wifiInfoDto);
        	}
        	
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
        	MariadbConnection.close(rs, preparedStatement, connection);
        }
        insertHistory(lat, lnt);
        
        return list;
        
	}
	
}