package workspaceReserve;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashSet;

import javax.swing.*;

public class SearchSeat  {
	private int day, month, year, start, end;
	
	private JButton[] buttons;
	PreparedStatement insertStatement;
	ResultSet rs;
	Connection con;
	
	public SearchSeat(int day, int month, int year, int start, int end, JButton[] b) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.start = start;
		this.start = start;
		this.end = end;
		this.buttons = b;
		resetSeatColor();
		changeSeatColor();
		
		
	}
	
	private void changeSeatColor() {
		try {
			if (rs == null) {
				Connection con = DriverManager.getConnection("jdbc:sqlite:ReservationDatabase.db");
				String sql = "Select UserId,FirstName,LastName, ReserveDay,StartTime,EndTime,SeatId,ReserveMonth,ReserveYear from Reservation";
				PreparedStatement statement = con.prepareStatement(sql);
				rs = statement.executeQuery();
			}
			while(rs.next()&& !rs.isAfterLast()) {
				
					if (day == rs.getInt("ReserveDay") && month==rs.getInt("ReserveMonth") && year == rs.getInt("ReserveYear")) {
						
						if (end <= rs.getInt("StartTime") ||  start >= rs.getInt("EndTime"))  {
						   continue;
						}else {
						
							int i = rs.getInt("SeatId");
							buttons[i].setBackground(Color.red);
							buttons[i].setEnabled(false);
							
					}
				}			
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	private void resetSeatColor() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setBackground(new Color(0,153,0 ));
			buttons[i].setEnabled(true);
		}
	}

	
}
