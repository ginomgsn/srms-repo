package com.srms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class StudentDashboard extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private int account_id;
	private DBConnect con = new DBConnect();
	private String name;
	private String firstname;
	private String section;
	private ArrayList<Subject> subjects = new ArrayList<Subject>();
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public StudentDashboard(int account_id) throws SQLException {
		this.account_id = account_id;
		
		//Get Student Information using Account ID
		String query = "SELECT * FROM students WHERE student_id=?;";
		PreparedStatement stmt = con.getConnection().prepareStatement(query);
		stmt.setInt(1, account_id);
		ResultSet results = stmt.executeQuery();
		
		if (results.next()) {
			name = results.getString(2);
			section = results.getString(3);
		}
		
		//Get First name for Welcome Message
		firstname = name.contains(" ") ? name.split(" ")[0] : name;
		
		//Get Student Subjects ID
		String subjects_query = "SELECT subjects.subject_code, subjects.subject_name "
				+ "FROM subjects INNER JOIN student_subjects "
				+ "ON subjects.subject_id=student_subjects.subject_id "
				+ "WHERE student_subjects.student_id=?;";
		PreparedStatement subjects_stmt = con.getConnection().prepareStatement(subjects_query);
		subjects_stmt.setInt(1, account_id);
		ResultSet subjects_results = subjects_stmt.executeQuery();
		
		while (subjects_results.next()) {
			String subject_code = subjects_results.getString(1);
			String subject_name = subjects_results.getString(2);
			
			subjects.add(new Subject(subject_name, subject_code));
		}
		
		Object columns[] = {"Subject Code", "Subject Name"};
		Object rows[][] = new Object[subjects.size()][2];
		
		if (subjects.size() > 0) {
			int i = 0;
			for (Subject subject: subjects) {
				rows[i][0] = subject.getCode();
				rows[i][1] = subject.getName();
				i++;
			}
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcome = new JLabel("Hi " + firstname + "!");
		lblWelcome.setBounds(10, 11, 93, 22);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblWelcome);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setBounds(10, 44, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Section:");
		lblNewLabel_2.setBounds(10, 69, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblName = new JLabel(name);
		lblName.setBounds(57, 44, 185, 14);
		contentPane.add(lblName);
		
		JLabel lblNewLabel = new JLabel(section);
		lblNewLabel.setBounds(66, 69, 176, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_3 = new JLabel("Subjects");
		lblNewLabel_3.setBounds(10, 94, 588, 14);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 119, 588, 151);
		contentPane.add(scrollPane);
		
		
		table = new JTable(rows, columns);{
			
		}
		
		
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setRowSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setMinWidth(300);
	}
}
