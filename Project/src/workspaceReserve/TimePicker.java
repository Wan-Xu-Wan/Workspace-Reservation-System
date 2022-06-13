package workspaceReserve;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.*;

public class TimePicker {
	private String selectedTime = "";
	private int selectedHour;
	private JPanel timePanel;
	private JComboBox timeBox;
	
	public TimePicker() {
		timePanel = new JPanel();
		setupTimePickerPanel();
		//jf.add(timePanel);
	}

	public void setupTimePickerPanel() {
		timeBox = new JComboBox();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 1);
		for (int i = 1; i <= 24; i++) {
			if (i <= 9) {
				timeBox.addItem("0"+ i + " : 00");
			} else {
				timeBox.addItem(""+ i + " : 00");
			}
		}
		timeBox.addActionListener(new ActionListener() {
 
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedTime = (String) timeBox.getSelectedItem();
				
			}
	
		});
	
		timePanel.add(timeBox);
	}
	public int getSelectedHour() {
		selectedTime = (String) timeBox.getSelectedItem();
		this.selectedHour = Integer.parseInt(selectedTime.substring(0,2));
		return this.selectedHour;
	}
	public JPanel getTimePanel() {
		return this.timePanel;
	}
	public JComboBox getTimeBox() {
		return this.timeBox;
	}
	public String selectedTime() {
		selectedTime = (String) timeBox.getSelectedItem();
		return selectedTime;
		
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    TimePicker timeP = new TimePicker();
	    frame.add(timeP.getTimePanel());
	    //frame.pack();
	    frame.setVisible(true);
	    
	}

}
