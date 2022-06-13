package workspaceReserve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import java.util.List;


public class ShowReservation extends JPanel{
	private int day, month, year, start, end, curSeat;
	PreparedStatement insertStatement;
	Connection con;
	private String userid, firstName, lastName;
	ResultSet rs;
	private int maxlength = 5, count = 0;
	private JButton[] buttons = new JButton[5];
	private List<ArrayList<Integer>> infoList = new ArrayList<ArrayList<Integer>>();
	private String name;
	private JPanel tab;
	
	public ShowReservation(String userid, String firstName, String lastName, JPanel tab) {
		this.userid= userid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setPreferredSize(new Dimension(500, 580));
		this.tab = tab;

		setupList();
		
		try {
			addLabels();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void setupList() {
		
		this.setPreferredSize(new Dimension(500, 580));
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		Calendar now = Calendar.getInstance();
		JLabel title = new JLabel("Next 5 upcoming reservations: ");
		JLabel blank = new JLabel("      ");
		this.add(title);
		this.add(blank);
		
		int nowM = Calendar.getInstance().get(Calendar.MONTH)+1;
		int nowY = Calendar.getInstance().get(Calendar.YEAR);
		int nowD = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int nowH = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		

		
		try {
			con = DriverManager.getConnection("jdbc:sqlite:ReservationDatabase.db");
			String sql = "Select UserId,FirstName,LastName, ReserveDay,StartTime,EndTime,SeatId,ReserveMonth,ReserveYear from Reservation";
			PreparedStatement statement = con.prepareStatement(sql);
			rs = statement.executeQuery();
			
			
			//name = "Name: "+ rs.getString("FirstName") + " "+ rs.getString("LastName");
			while(rs.next()&& !rs.isAfterLast() ) {
				
				if (userid.equals(rs.getString("UserId")) ) {
					
					int month = rs.getInt("ReserveMonth");
					int year = rs.getInt("ReserveYear");
					int day = rs.getInt("ReserveDay");
					int start = rs.getInt("StartTime");
					int end = rs.getInt("EndTime");
					curSeat = rs.getInt("SeatId");
					if (year > nowY) {
						addinfo(year, month, day, start, end, curSeat);
						
					} else if (year == nowY) {
						if (month > nowM) {
							addinfo(year, month, day, start, end, curSeat);
						} else if (month == nowM) {
							if (day > nowD) {
								addinfo(year, month, day, start, end, curSeat);
							}else if (day == nowD) {
								if (end > nowH) {
									addinfo(year, month, day, start, end, curSeat);
								}
							}
						}
					}
					
				}			
			}
			
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void addLabels() throws SQLException {
		
		Collections.sort(infoList, new Comparator<ArrayList<Integer>>() {

			@Override
			public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
				for (int i = 0; i < o1.size(); i++) {
				      int c = o1.get(i).compareTo(o2.get(i));
				      if (c != 0) {
				        return c;
				      }
				    }
				    return 0;
			}
		
		});
		for (ArrayList<Integer> temp : infoList) {
			if (count < 5) {
				
				JLabel nameTemp = new JLabel("Name: "+ firstName + " " + lastName);
				this.add(nameTemp);
				
				JLabel reserveDate = new JLabel("Reserved Date: " + temp.get(1) + "-" + temp.get(2) + "-" + temp.get(0));
				this.add(reserveDate);
				JLabel reserveTime = new JLabel("Reserved Time Period: " + temp.get(3)+ ":00 - " + temp.get(4) + ":00");
				this.add(reserveTime);
				JLabel reserveSeat = new JLabel("Reserved Seat ID: " + temp.get(5));
				
				this.add(reserveSeat);
				buttons[count] = new JButton("Cancel");
				buttons[count].setPreferredSize(new Dimension(100,20));
				buttons[count].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						int res = JOptionPane.showConfirmDialog(null, "Please confirm to cancel the reservation",
								"Please click on confirm to cancel the reservation", JOptionPane.YES_NO_OPTION );
						if (res == 0 ) {
							new Cancel(userid, firstName, lastName, temp.get(2),temp.get(1), temp.get(0),temp.get(3), temp.get(4),temp.get(5));
							
						}
						
					}
				
				});
				this.add(buttons[count]);
				JLabel blank = new JLabel("      ");
				this.add(blank);
				
				count++;
			}
			
		}
		
	}
	
	
	
	private void addinfo(int year, int month, int day, int start, int end, int seat) {
		ArrayList<Integer> temp = new ArrayList();
		temp.add(year);
		temp.add(month);
		temp.add(day);
		temp.add(start);
		temp.add(end);
		temp.add(seat);
		infoList.add(temp);
	}
	
	public static void main(String[] args) {
		  
	      ShowReservation panel = new ShowReservation("user1","John", "Smith", new JPanel());

	      JFrame jf = new JFrame();
	      jf.getContentPane().add(panel);
	      jf.pack();
	      jf.setVisible(true);
	      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  }


}
