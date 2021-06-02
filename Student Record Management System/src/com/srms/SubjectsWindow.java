package com.srms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class SubjectsWindow extends JFrame {

	private JPanel contentPane;
	private DBConnect db = new DBConnect();
	private Connection con = db.getConnection();

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public SubjectsWindow(int account_id) throws SQLException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Available Subjects");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 44, 414, 14);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 69, 414, 159);
		contentPane.add(scrollPane);
		
		// JList Components
		JList<Subject> list = new JList<>();
		DefaultListModel<Subject> model = new DefaultListModel<>();
		
		// Populate list using database
		String query = "SELECT subject.subject_code, subject.subject_name FROM subject\r\n"
				+ "	INNER JOIN student ON student.course_id=subject.course_id\r\n"
				+ "	WHERE NOT EXISTS (SELECT * FROM student_subject WHERE subject.subject_id = student_subject.subject_id\r\n"
				+ "                     AND student_id=?)\r\n"
				+ "    AND student.student_id=?;";
		PreparedStatement subjects_stmt = con.prepareStatement(query);
		subjects_stmt.setInt(1, account_id);
		subjects_stmt.setInt(2, account_id);
		ResultSet subjects_results = subjects_stmt.executeQuery();
		
		while (subjects_results.next()) {
			String subject_code = subjects_results.getString(1);
			String subject_name = subjects_results.getString(2);
			
			model.addElement(new Subject(subject_name, subject_code));
		}
		
		list.setModel(model);
		
		subjects_results.close();
		subjects_stmt.close();
		
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.setBounds(10, 247, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setBounds(335, 247, 89, 23);
		contentPane.add(btnNewButton_1);
	}
}
