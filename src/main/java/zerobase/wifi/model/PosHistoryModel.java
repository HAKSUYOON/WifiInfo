package zerobase.wifi.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import zerobase.wifi.dto.PosHistoryDto;
import zerobase.wifi.service.MariadbConnection;

public class PosHistoryModel {
	
	public static void insertHistory(String lat, String lnt) {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try {
        	connection = MariadbConnection.getConnect();
        	
        	Locale currentLocale = new Locale("KOREAN", "KOREA");
        	String pattern = "yyyyHHdd'T'HH:mm:ss";
        	SimpleDateFormat fomatter = new SimpleDateFormat(pattern, currentLocale);
        	String viewDate = fomatter.format(new Date());
        	
        	String sql = " INSERT INTO wifi_history "
        				+ " (INSERT_LAT, `INSERT_LNT`, VIEW_DATE) "
        				+ " VALUES(?, ?, ?); ";
        	
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setString(1, lat);
        	preparedStatement.setString(2, lnt);
        	preparedStatement.setString(3, viewDate);
        	
        	int affected = preparedStatement.executeUpdate();
        	
        	if (affected > 0) {
        		System.out.println(" 저장 성공 ");
        	} else {
        		System.out.println(" 저장 실패 ");
        	}
        	
        } catch (SQLException e) {
        	e.printStackTrace();        	
        } finally {	
        	MariadbConnection.close(rs, preparedStatement, connection);
        }        
	}
	
	public List<PosHistoryDto> selectHistory() {
		
		List<PosHistoryDto> list = new ArrayList<>();
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try {
        	connection = MariadbConnection.getConnect();
        	
        	String sql = " SELECT id, INSERT_LAT, `INSERT_LNT`, VIEW_DATE "
        				+ " FROM wifi_history "
        				+ " order by id desc; ";
        	
        	preparedStatement = connection.prepareStatement(sql);
        	rs = preparedStatement.executeQuery();
        	
        	while (rs.next()) {
        		PosHistoryDto posHistioryDto = new PosHistoryDto(
        				rs.getInt("id"), rs.getString("INSERT_LAT"), rs.getString("INSERT_LNT"), rs.getString("VIEW_DATE"));
        		list.add(posHistioryDto);
        	}
        	
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
        	MariadbConnection.close(rs, preparedStatement, connection);
        }        
        return list;
	}
	
	public void deleteHistory(String id) {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try {
        	connection = MariadbConnection.getConnect();
        	
        	String sql = " DELETE FROM wifi_history "
        				+ " WHERE id=?; ";
        	
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setInt(1, Integer.parseInt(id));
        	
        	int affected = preparedStatement.executeUpdate();
        	
        	if (affected > 0) {
        		System.out.println(" 삭제 성공 ");
        	} else {
        		System.out.println(" 삭제 실패 ");
        	}
        	
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
        	MariadbConnection.close(rs, preparedStatement, connection);
        }
	}
	
}