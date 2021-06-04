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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SubjectsWindow extends JFrame {

	private JPanel contentPane;
	JList<Subject> available_subjects;
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
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<Subject> selected_subjects = available_subjects.getSelectedValuesList();
				
				
					try {
						for(Subject subject: selected_subjects) {
							String insert_query = "INSERT INTO student_subject(subject_id, student_id) \r\n"
							+ "	VALUES ((SELECT subject_id FROM subject WHERE subject_code=?), ?);";
							PreparedStatement inset_stmt = con.prepareStatement(insert_query);
							inset_stmt.setString(1, subject.getCode());
							inset_stmt.setInt(2, account_id);
							inset_stmt.executeUpdate();
						}
						
						// Reload Student Dash board and dispose
						
						StudentDashboard dashboard = new StudentDashboard(account_id);
						dashboard.setVisible(true);
						dispose();
					}
					catch(SQLException ex) {
						System.out.println(ex);
					}		
			}
		});
		btnConfirm.setBounds(10, 247, 89, 23);
		contentPane.add(btnConfirm);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					StudentDashboard dashboard = new StudentDashboard(account_id);
					dashboard.setVisible(true);
					dispose();
				}
				catch(SQLException ex) {
					System.out.println(ex);
				}
			}
		});
		btnCancel.setBounds(335, 247, 89, 23);
		contentPane.add(btnCancel);
		
		showAvailableSubjects(account_id);
		
		
	}
	
	private void showAvailableSubjects(int account_id) throws SQLException {
		// JList Components
				available_subjects = new JList<Subject>();
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
				
				available_subjects.setModel(model);
				available_subjects.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				
				subjects_results.close();
				subjects_stmt.close();
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 69, 414, 159);
				contentPane.add(scrollPane);
				scrollPane.setViewportView(available_subjects);
	}
}
