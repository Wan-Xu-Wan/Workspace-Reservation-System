package workspaceReserve;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class LoginPage extends JFrame implements Runnable{
	private JLabel username, password, systemName;
	private JButton login, reset;
	private JPasswordField pwField;
	private JTextField usernameField;
	
	PreparedStatement statement;
	ResultSet rs;
	Connection con;
	
	public LoginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.LIGHT_GRAY);;
		setupPanels();
		setSize(600, 300);
		setVisible(true);
	}

	public void setupPanels() {
		
		username = new JLabel("Username");
		password = new JLabel("Password");
		systemName = new JLabel("Worskpace Reservation System");
		systemName.setFont(new Font("Calibri", Font.BOLD, 20));
		
		login = new JButton("Login");
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				check();
				
			}
			
		});
		
		reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				usernameField.setText("");
				pwField.setText("");
				
			}
			
		});
		
		pwField = new JPasswordField();
		usernameField = new JTextField();

		
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;       
		c.gridx = 1;       
		c.gridwidth = GridBagConstraints.REMAINDER;   
		c.gridheight = 1;
		c.weighty = 0.1;
		c.gridy = 0;
		centerPanel.add(systemName,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		c.weighty = 0;
		c.gridwidth = 1;
		centerPanel.add(username,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		c.ipady = 5;
		c.ipadx = 40;
		c.gridwidth = 1;
		centerPanel.add(usernameField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4; 		    
		centerPanel.add(password, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 4; 
		c.ipady = 5;
		centerPanel.add(pwField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5; 
		c.ipady = 0;
		c.ipadx = 0;
		c.insets = new Insets(0,25,0,0);
		c.weighty = 0.1;
		centerPanel.add(login, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 5; 
		c.insets = new Insets(0,90,0,0);
		centerPanel.add(reset,c);
		
		this.add(centerPanel,BorderLayout.CENTER);
		
		
		
	}
	
	private void check() {
		String user = usernameField.getText();
		char[] temp = pwField.getPassword();
		String pw = new String(temp);
		try {
			
				Connection con = DriverManager.getConnection("jdbc:sqlite:ReservationDatabase.db");
				String sql = "Select UserId,Password,FirstName,LastName from UserDB";
				PreparedStatement statement = con.prepareStatement(sql);
				
				rs = statement.executeQuery();
			
			while(rs.next()&& !rs.isAfterLast()) {
				
					if (user.equals(rs.getString("UserId"))&& pw.equals(rs.getString("Password"))) {
						
						JOptionPane.showMessageDialog(null, "Login success");
						ReservePage r1 = new ReservePage(rs.getString("UserId"),rs.getString("FirstName"), rs.getString("LastName"));
						r1.setVisible(true);
						this.setVisible(false);
						this.dispose();
						con.close();
						return;
					} 	
			}
			JOptionPane.showMessageDialog(null, "Opps! Wrong uesername or password. Try again!");
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LoginPage login = new LoginPage();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
