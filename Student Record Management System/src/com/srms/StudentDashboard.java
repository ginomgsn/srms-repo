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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class StudentDashboard extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private int account_id;
	private DBConnect db = new DBConnect();
	private Connection con = db.getConnection(); 
	private String firstname, section, lastname, contact, course, department;
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
		String query = "SELECT *, course.course_name, department.department_name \r\n"
				+ "	FROM ((student INNER JOIN course ON course.course_id=student.course_id)\r\n"
				+ "    INNER JOIN department ON course.department_id=department.department_id)\r\n"
				+ "    WHERE student_id=?;";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, account_id);
		ResultSet results = stmt.executeQuery();
		
		if (results.next()) {
			firstname = results.getString(2);
			lastname = results.getString(3);
			section = results.getString(4);
			contact = results.getString(5);
			course = results.getString(8);
			department = results.getString(11);
		}
		
		//Get Student Subjects ID
		String subjects_query = "SELECT subject.subject_code, subject.subject_name "
				+ "FROM subject INNER JOIN student_subject "
				+ "ON subject.subject_id=student_subject.subject_id "
				+ "WHERE student_subject.student_id=?;";
		PreparedStatement subjects_stmt = con.prepareStatement(subjects_query);
		subjects_stmt.setInt(1, account_id);
		ResultSet subjects_results = subjects_stmt.executeQuery();
		
		while (subjects_results.next()) {
			String subject_code = subjects_results.getString(1);
			String subject_name = subjects_results.getString(2);
			
			subjects.add(new Subject(subject_name, subject_code));
		}
		
		Object columns[] = {"Subject Code", "Subject Name"};
		Object rows[][];
		
		
		if (subjects.size() > 0) {
			rows = new Object[subjects.size()][2];
			
			int i = 0;
			for (Subject subject: subjects) {
				rows[i][0] = subject.getCode();
				rows[i][1] = subject.getName();
				i++;
			}
			
			table = new JTable(rows, columns);
		}
		else {
			rows = new Object[0][2];
			table = new JTable(rows, columns);
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcome = new JLabel("Hi " + firstname + "!");
		lblWelcome.setBounds(10, 11, 258, 22);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblWelcome);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setBounds(10, 44, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Section:");
		lblNewLabel_2.setBounds(10, 69, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblName = new JLabel(firstname + " " + lastname);
		lblName.setBounds(79, 44, 189, 14);
		contentPane.add(lblName);
		
		JLabel lblNewLabel = new JLabel(section);
		lblNewLabel.setBounds(79, 69, 154, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("Course:");
		lblNewLabel_4.setBounds(10, 94, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("Subjects");
		lblNewLabel_3.setBounds(10, 159, 588, 14);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 184, 588, 151);
		contentPane.add(scrollPane);
		
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setRowSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JLabel lblCourse = new JLabel(course);
		lblCourse.setBounds(79, 94, 236, 14);
		contentPane.add(lblCourse);
		
		JLabel lblNewLabel_5 = new JLabel("Contact #:");
		lblNewLabel_5.setBounds(10, 119, 71, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblContact = new JLabel(contact);
		lblContact.setBounds(79, 119, 154, 14);
		contentPane.add(lblContact);
		
		JButton btnAddSubject = new JButton("Add Subject");
		btnAddSubject.setBounds(10, 367, 112, 23);
		contentPane.add(btnAddSubject);
		
		JButton btnLogOut = new JButton("Logout");
		btnLogOut.setBounds(509, 367, 89, 23);
		contentPane.add(btnLogOut);
		
		// Set Button Function
		btnAddSubject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Show Subjects
				//SubjectsWindow subjectsWindow = new SubjectsWindow();
			}
			
		});
		
		btnLogOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setMinWidth(300);
	}
}
