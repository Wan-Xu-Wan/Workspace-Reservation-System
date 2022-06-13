package workspaceReserve;



import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.*;
import java.util.*;
import java.lang.*;
import java.sql.Connection;
import java.time.*;

/* 
Floor map from "https://www.roomsketcher.com/office-design/office-floor-plans/"

*/

public class ReservePage extends JFrame {
	
	private JTabbedPane tabs;
	private JPanel leftPanel, rightPanel, tab1Panel,tab2Panel;
	//private JLayeredPane rightPanel;
	private ImagePanel imgPanel;
	private final static int width = 1000;
	private final static int height = 700;
	private int selectedDay, selectedYear, selectedMonth, selectedSeat;
	private String calendarSelected, selectedS, selectedE;
	private int selectedStartTime, selectedEndTime;
	private HashMap<JButton, Integer> seatMap = new HashMap();
	private JButton[] seatButtons;
	private JDialog reserveBt;
	private int curSeat= -2;
	private String userid, firstName, lastName;
	Connection con;

	
	public ReservePage(String userid, String firstName, String lastName) {
		this.userid = userid;
		this.firstName = firstName;
		this.lastName = lastName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.LIGHT_GRAY);
		setupPanels();
		setSize(width, height);
		setVisible(true);
		setResizable(true);
		this.con = con;
	}
	
	public void setupPanels() {
		JLabel username = new JLabel("Hello " + firstName + " ");
		username.setFont(new Font("Arial",Font.BOLD, 20));
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(width, 50));
		
		
		tabs = new JTabbedPane();
		leftPanel = new JPanel(new GridBagLayout() );
		//leftPanel.setBackground(Color.WHITE);
		leftPanel.setPreferredSize(new Dimension(200, 600));
		
		JLabel dateLabel = new JLabel("Date:");
		JTextField dateText = new JTextField(25);
		
		ImageIcon icon= new ImageIcon("src/calendarButton.png");
		Image temp = icon.getImage();
		Image newimg = temp.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
		JButton dateButton = new JButton(new ImageIcon(newimg));
		dateButton.setMargin(new Insets(0,0,0,0));
		dateButton.setBorder(null);
		dateButton.setBackground(Color.white);
		this.getContentPane().add(leftPanel);
		dateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				calendarSelected = new DatePicker().selectedDay();
				dateText.setText(calendarSelected);
				selectedMonth = (int) Integer.parseInt(calendarSelected.substring(0,2));
				selectedDay =(int) Integer.parseInt(calendarSelected.substring(3,5));
				selectedYear =(int) Integer.parseInt(calendarSelected.substring(6));
				
				int m = Calendar.getInstance().get(Calendar.MONTH)+1;
				int y = Calendar.getInstance().get(Calendar.YEAR);
				int d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				if (y > selectedYear) {
					JOptionPane.showMessageDialog(null, "Opps! Past date is chosen. Try again!");
					dateText.setText("");
				} else if (y == selectedYear) {
					if (m > selectedMonth) {
						JOptionPane.showMessageDialog(null, "Opps! Past date is chosen. Try again!");
						dateText.setText("");
					} else if (m == selectedMonth) {
						if( d > selectedDay) {
							JOptionPane.showMessageDialog(null, "Opps! Past date is chosen. Try again!");
							dateText.setText("");
						}
					}
				}
			}
			
		});
		
		
		
		JLabel startLabel = new JLabel("Start Time:");
		TimePicker startTime = new TimePicker();
		JComboBox startBox =startTime.getTimeBox();
		startBox.setBackground(Color.white);
		startBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectedS = (String) startBox.getSelectedItem();
				selectedStartTime = Integer.parseInt(selectedS.substring(0,2));
			}
			
		});
		
		
		
		JLabel endLabel = new JLabel("End Time:");
		TimePicker endTime = new TimePicker();
		JComboBox endBox =  endTime.getTimeBox();
		endBox.setBackground(Color.white);
		endBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectedE = (String) endBox.getSelectedItem();
				selectedEndTime = Integer.parseInt(selectedE.substring(0,2));
			}
			
		});
	 
		this.seatButtons = new JButton[16];
		
		for (int i = 0; i < seatButtons.length; i++) {
			int selected = i;
			seatButtons[i] = new JButton();
			seatButtons[i].setPreferredSize(new Dimension(18,34));
			seatButtons[i].setBackground(new Color(0,153,0));
			seatButtons[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (curSeat>=0) {
						seatButtons[curSeat].setBackground(new Color(0,153,0));
					}
					curSeat = seatMap.get(seatButtons[selected]);
					seatButtons[selected].setBackground(new Color(255,255,0));
					
				}
				
			});
			seatMap.put(seatButtons[i], i);
			
		}

		
		JButton search = new JButton("Search");
		search.setPreferredSize(new Dimension(10,10));
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SearchSeat(selectedDay, selectedMonth, selectedYear, selectedStartTime, selectedEndTime,seatButtons);
				
			}
			
		});
	
		
		
		JButton reserve = new JButton("Reserve");
		
		reserve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (curSeat < 0) {
					JOptionPane.showMessageDialog(null, "Please select a seat!");
					return;
				}
				if (dateText.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Wrong date!");
					return;
				}
				reserveBt = new JDialog();
				reserveBt.setModal(true);
				JLabel info = new JLabel("Reservation Details:");
				
				JPanel infoPanel = new JPanel(new GridLayout(5,1));
				infoPanel.setPreferredSize(new Dimension (200,100));
				
				JLabel nameInfo = new JLabel();
				nameInfo.setText("Name: " + firstName + " " + lastName);
				
				JLabel dayInfo = new JLabel();
				dayInfo.setText("Reserve Date: " + calendarSelected);
				JLabel timeInfo = new JLabel();
				timeInfo.setText("Reserve Period: " + selectedS + " - " + selectedE);
				JLabel seatIndex = new JLabel();
				seatIndex.setText("Reserve Seat: " + curSeat);
				
				JButton confirm = new JButton("Confirm");
				confirm.setPreferredSize(new Dimension(100, 20));
				confirm.setSize(new Dimension(50,20));
				confirm.setFont(new Font("Arial",Font.BOLD, 15));
				confirm.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						new Reserve(userid, firstName, lastName,selectedDay, selectedMonth, selectedYear, selectedStartTime, selectedEndTime,curSeat);
						reserveBt.dispose();
					}
					
				});
			
				infoPanel.add(info );
				infoPanel.add(nameInfo);
				infoPanel.add(dayInfo);
				infoPanel.add(timeInfo);
				infoPanel.add(seatIndex);
				JPanel confPanel = new JPanel();
				
				confPanel.add(confirm, BorderLayout.CENTER);

				reserveBt.add(infoPanel, BorderLayout.CENTER);
				reserveBt.add(confPanel, BorderLayout.SOUTH);
				reserveBt.pack();
				reserveBt.setVisible(true);
				
			}
			
		});
		
		GroupLayout layout2 = new GroupLayout(top);
		top.setLayout(layout2);
		layout2.setHorizontalGroup(
				layout2.createSequentialGroup()
				.addGap(450,450,450)
				.addComponent(username, 150,150,150)
				
				.addGap(200,200,200)
				.addComponent(reserve, 90,90,90)
				);
		layout2.setVerticalGroup(
				layout2.createSequentialGroup()
				.addGap(20,20,20)
				.addGroup(layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(reserve,20,20,20)
						.addComponent(username)

						)
				);
		
		
	
		this.add(top, BorderLayout.PAGE_START);
		
		
        GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.ipady = 5; 
		c.ipadx = 30;
		c.gridx = 0;       
		c.gridwidth = GridBagConstraints.REMAINDER;   
		c.gridheight = 1;
		c.weightx= 0;
		c.weighty= 0;
		c.gridy = 0;
		
		leftPanel.add(dateLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0;
		c.gridwidth = 1;
		leftPanel.add(dateText,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.weighty = 0;
		c.gridwidth = 1;
		c.ipadx = 0;
		c.ipady = 8;
		leftPanel.add(dateButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0;
		c.gridwidth = 1;
		c.ipady = 5; 
		c.ipadx = 30;
		leftPanel.add(startLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0;
		c.gridwidth = 1;
		leftPanel.add(startBox,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 0;
		c.gridwidth = 1;
		leftPanel.add(endLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 0.1;
		c.gridwidth = 1;
		leftPanel.add(endBox, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 6;
		c.weighty = 1;
		c.weightx = 0;
		c.gridwidth = 1;
		c.ipadx = 0;
		c.ipady = 3;
		c.insets= new Insets(0,0,0,20);
		search.setFont(new Font("Arial",Font.BOLD, 10));
		leftPanel.add(search, c);
		
		
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(700, height));
		
		imgPanel = new ImagePanel("src/Floor Map.PNG");
		
		
		GroupLayout layout = new GroupLayout(imgPanel);
		imgPanel.setLayout(layout);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				  .addGap(387,387,387)
				  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					  .addComponent(seatButtons[9], 38,38,38)
					  .addGroup(layout.createSequentialGroup()
							  .addComponent(seatButtons[10], 18,18,18)
							  )
					  .addGroup(layout.createSequentialGroup()
							  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									  .addComponent(seatButtons[0], 18,18,18)
									  .addComponent(seatButtons[4], 18,18,18)
                      
									  )
					  		  .addGap(3,3,3)
					  		  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					  				  .addComponent(seatButtons[1], 18,18,18)
					  				  .addComponent(seatButtons[5], 18,18,18)
					  				  .addComponent(seatButtons[11], 18,18,18)
					  				  )
					  		  .addGap(56,56,56)
					  		  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					  				  .addComponent(seatButtons[8], 38,38,38)
					  				  .addGroup(layout.createSequentialGroup()
					  						  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					  								  .addComponent(seatButtons[2], 18,18,18)
					  								  .addComponent(seatButtons[6], 18,18,18)
					  								  .addComponent(seatButtons[12],18,18,18)
					  								  .addComponent(seatButtons[14],18,18,18)
					  								  )
					  						  .addGap(3,3,3)
					  						  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					  								  .addComponent(seatButtons[3], 18,18,18)
					  								  .addComponent(seatButtons[7], 18,18,18)
					  								  .addComponent(seatButtons[13],18,18,18)
					  								  .addComponent(seatButtons[15],18,18,18)
					  								  )
                  
                	  
                  
						
		)))));
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				 .addGap(30, 30, 30)
				 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				        .addComponent(seatButtons[0], 34,34,34)
				        .addComponent(seatButtons[1], 34,34,34)
				        .addComponent(seatButtons[2], 34,34,34)
				        .addComponent(seatButtons[3], 34,34,34))
				.addGap(6, 6, 6)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)      
						.addComponent(seatButtons[4], 34,34,34)
						.addComponent(seatButtons[5], 34,34,34)
						.addComponent(seatButtons[6], 34,34,34)
						.addComponent(seatButtons[7], 34,34,34))
				.addGap(5, 5, 5)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(seatButtons[8], 18,18,18))
				.addGap(40, 40, 40)
				
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addGroup(layout.createSequentialGroup()
								.addComponent(seatButtons[9], 18,18,18)
								.addGap(3,3,3)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(seatButtons[10],34,34,34)
										.addComponent(seatButtons[11],34,34,34)
										)
								
								)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(seatButtons[12],34,34,34)
										.addComponent(seatButtons[13],34,34,34)
										)
								.addGap(9,9,9)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(seatButtons[14],34,34,34)
										.addComponent(seatButtons[15],34,34,34)
										)
								)
				 )		
						
		);
		
		rightPanel.add(imgPanel);
		
		tab1Panel = new JPanel();
		tab1Panel.add(leftPanel, BorderLayout.PAGE_START);
		tab1Panel.add(rightPanel, BorderLayout.CENTER);
		
		tab2Panel = new JPanel();
		
		ShowReservation RP = new ShowReservation(userid, firstName, lastName, tab2Panel);
		
		

		tab2Panel.add(RP, BorderLayout.CENTER);
		tabs.addTab("Reserve", tab1Panel);
		tabs.addTab("My Reservation", tab2Panel);
		
		tabs.addChangeListener(new ChangeListener() {
 
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				int x = tabs.getSelectedIndex();
				if (x == 1) {
					tab2Panel.removeAll();
					ShowReservation RP = new ShowReservation(userid, firstName, lastName,tab2Panel);
					tab2Panel.add(RP, BorderLayout.CENTER);
					
				}
			}
			
		});
		
		
		this.add(tabs, BorderLayout.CENTER);
	}
	
	
	public static void main(String[] args) {
		ReservePage reserveUser1 = new ReservePage("user1", "John", "Smith");
	}

}
