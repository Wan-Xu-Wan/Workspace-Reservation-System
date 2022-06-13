package workspaceReserve;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class Reserve implements Runnable {
	private int day, month, year, start, end, curSeat;
	PreparedStatement insertStatement;
	Connection con;
	private String userid, firstName, lastName;
	ResultSet rs;

	
	public Reserve(String userid, String firstName, String lastName,int day, int month, int year, int start, int end, int curSeat) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.start = start;
		this.end = end;
		this.curSeat = curSeat;
		this.userid = userid;
		this.firstName = firstName;
		this.lastName = lastName;
		
		try
		{
		    this.con = DriverManager.getConnection
		      ("jdbc:sqlite:ReservationDatabase.db");
		} catch (Exception e) {
			System.exit(0);
		}
		String insertSQL = "Insert Into Reservation ( UserId,FirstName,LastName, ReserveDay,StartTime,EndTime,SeatId,ReserveMonth,ReserveYear)"
				+ "Values(?,?,?,?,?,?,?,?,?)";
		try {
			insertStatement = con.prepareStatement(insertSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		run();
		
	}

	@Override
	public void run() {
		synchronized(con) {
			reserveSeat();
		}
		
	}
	
	public void reserveSeat() {
		try {
			
			String sql = "Select UserId,FirstName,LastName, ReserveDay,StartTime,EndTime,SeatId,ReserveMonth,ReserveYear from Reservation";
			PreparedStatement statement = con.prepareStatement(sql);
			rs = statement.executeQuery();
			
			while(rs.next()&& !rs.isAfterLast()) {
			
				if (day == rs.getInt("ReserveDay") && month==rs.getInt("ReserveMonth") && year == rs.getInt("ReserveYear")) {
					
					if (end <= rs.getInt("StartTime") ||  start >= rs.getInt("EndTime") || curSeat != rs.getInt("SeatId"))  {
					   continue;
					}else {
						JOptionPane.showMessageDialog(null, "Opps! Reserved by someone already");
						con.close();
						return;
				
				}
			}			
		}
			
			insertStatement.setString(1, userid);
			insertStatement.setString(2, firstName);
			insertStatement.setString(3, lastName);
			insertStatement.setInt(4, day);
			insertStatement.setInt(5, start);
			insertStatement.setInt(6, end);
			insertStatement.setInt(7, curSeat);
			insertStatement.setInt(8, month);
			insertStatement.setInt(9, year);
			insertStatement.execute();
			JOptionPane.showMessageDialog(null, "Reserved Successfully");
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	

}
