package workspaceReserve;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.*;

public class DatePicker {
	  private JLabel middle;
	  private final static String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
	  private final static int width = 450, height = 200;
	  private String weekDay="";
	  private JDialog frame;
	  private JButton[] buttons = new JButton[49];
	  private int month = Calendar.getInstance().get(Calendar.MONTH);
	  private int year = Calendar.getInstance().get(Calendar.YEAR);
	 
	  
	  public DatePicker() {
		  middle = new JLabel();
		  weekDay ="";
		  frame = new JDialog();
		  frame.setModal(true);
		  setupDatePicker();
	  }
	
	  public void setupDatePicker() {
		  JPanel DayMatrix = new JPanel(new GridLayout(7,7));
		  DayMatrix.setPreferredSize(new Dimension(width, height));
		  Calendar now = Calendar.getInstance();
		  int day = now.get(Calendar.DAY_OF_MONTH);
		  now.set(Calendar.DAY_OF_MONTH, 1);
		  int maxday = now.getActualMaximum(Calendar.DATE);
		  int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
		  
		  for (int i = 0; i <buttons.length; i++) {
			  int selected = i;
			  buttons[i] = new JButton();
			  buttons[i].setFocusPainted(false);
			  buttons[i].setFont(new Font("Arial", Font.PLAIN, 10));
			  buttons[i].setBackground(Color.WHITE);
			  		  
			  if (i > 6 && i>=dayOfWeek+6 && i < dayOfWeek+ maxday+6) {
				  int curDay = i-6-dayOfWeek+1;
				  buttons[i].setText("" + curDay);
				  buttons[i].setForeground(Color.BLACK);
				  buttons[i].addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							weekDay = buttons[selected].getActionCommand();
							frame.dispose();
						}
						  
					  });
			  }
			  
			  if (i<= 6) {
				  buttons[i].setText(weekDays[i]);
				  buttons[i].setForeground(Color.BLUE);
			  }
			  DayMatrix.add(buttons[i]);
		  }
		  JPanel MonthYear = new JPanel(new FlowLayout());
		  JButton prev = new JButton("<<");
		  prev.addActionListener((ActionListener) new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					month--;
					calendarPanel();
				}
				  
			  });
		  
		  JButton next = new JButton(">>");
		  next.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				month++;
				calendarPanel();
			}
			  
		  });
		  SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM YYYY");
		  middle.setText(dateFormat.format(now.getTime()));
		  MonthYear.add(prev,FlowLayout.LEFT);
		  MonthYear.add(middle,FlowLayout.CENTER);
		  MonthYear.add(next, FlowLayout.RIGHT);
		  frame.add(DayMatrix, BorderLayout.CENTER);
		  frame.add(MonthYear, BorderLayout.SOUTH);
		  frame.pack();
		  frame.setVisible(true);
		  
	  }
	 
	  public void calendarPanel() {
		  for (int i = 7; i < buttons.length; i++) {
			  buttons[i].setText("");
		  }
		  Calendar now = Calendar.getInstance();
		  now.set(year, month,1);
		  int dayWeek = now.get(Calendar.DAY_OF_WEEK);
		  int dayMonth = now.getActualMaximum(Calendar.DATE);
		  for (int i = 6+dayWeek, date =1; date <= dayMonth; i++, date++) {
			  int selected = i;
			  buttons[i].setText("" + date);
			  buttons[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						weekDay = buttons[selected].getActionCommand();
						frame.dispose();
					}
					 
				  });
		  }
		  SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM YYYY");
		  middle.setText(dateFormat.format(now.getTime()));
	  }
	  
	  public String selectedDay() {
		  if (weekDay == "") return weekDay;
		  
		  Calendar now = Calendar.getInstance();
		  now.set(year, month, Integer.parseInt(weekDay) );
		  SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		  return dateFormat.format(now.getTime());
	  }
	  public int getYear() {
		  return this.year;
	  }
	  
	  public static void main(String[] args) {
		  JLabel date = new JLabel("Date:");
	      final JTextField text = new JTextField(20);
	      JButton hit = new JButton("hit");
	      hit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent ae) {
	            	DatePicker selected = new DatePicker();
	                text.setText(selected.selectedDay());
	            }
	        });
	      JPanel panel = new JPanel();
	      panel.add(date);
	      panel.add(text);
	      panel.add(hit);
	      JFrame jf = new JFrame();
	      jf.getContentPane().add(panel);
	      jf.pack();
	      jf.setVisible(true);
	      
	  }

}
