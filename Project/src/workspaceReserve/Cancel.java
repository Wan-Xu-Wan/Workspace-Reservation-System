package workspaceReserve;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Cancel implements Runnable {
	
	private int day, month, year, start, end, curSeat;
	PreparedStatement insertStatement;
	Connection con;
	private String userid, firstName, lastName;
	ResultSet rs;
	
	public Cancel(String userid, String firstName, String lastName,int day, int month, int year, int start, int end, int curSeat) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.start = start;
		this.end = end;
		this.curSeat = curSeat;
		this.userid = userid;
		this.firstName = firstName;
		this.lastName = lastName;
		 try {
			this.con = DriverManager.getConnection
				      ("jdbc:sqlite:ReservationDatabase.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		run();
	
	}
	public void delete() {
		try
		{
		    String sql = "delete from Reservation where UserId = ? AND ReserveDay = ? AND StartTime = ? AND ReserveMonth = ? AND ReserveYear = ? AND SeatId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, userid);
			statement.setInt(2, day);
			statement.setInt(3, start);
			statement.setInt(4, month);
			statement.setInt(5, year);
			statement.setInt(6, curSeat);
		
			statement.execute();
			JOptionPane.showMessageDialog(null, "Cancelled Successfully");
			con.close();
			
		} catch (Exception e) {
			System.exit(0);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(con) {
			delete();
		}
	}

}
