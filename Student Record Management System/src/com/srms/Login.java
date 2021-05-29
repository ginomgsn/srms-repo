package com.srms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	
	private String username;
	private String password;
	
	private DBConnect con = new DBConnect();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 218, 185);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(400, 200));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("UMAK Records");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 182, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(10, 50, 66, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(10, 75, 66, 14);
		contentPane.add(lblNewLabel_2);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(78, 47, 114, 20);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = tfUsername.getText();
				password = pfPassword.getText();
				
				try {
					String query = "SELECT * FROM accounts WHERE account_username=? and account_password=?;";
					PreparedStatement stmt = con.getConnection().prepareStatement(query);
					stmt.setString(1, username);
					stmt.setString(2, password);
					ResultSet results = stmt.executeQuery();
					
					if(results.next()) {
						int account_id = results.getInt(1);
						System.out.println("Logged in!");
						dispose();
						StudentDashboard frame = new StudentDashboard(account_id);
						frame.setVisible(true);
						results.close();
						stmt.close();
					}
					else System.out.println("Invalid user or pass!");								
				}
				catch(Exception ex) {
					System.out.println(ex);
				}
			}
		});
		btnLogin.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogin.setBounds(53, 113, 89, 23);
		contentPane.add(btnLogin);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(78, 72, 114, 20);
		contentPane.add(pfPassword);
	}
}
