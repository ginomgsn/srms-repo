package com.srms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
	static ArrayList<Subject> subjects = new ArrayList<>();
	JList<Subject> subjectList;
	private DBConnect db = new DBConnect();
	private Connection con = db.getConnection();
	String timeStart;
	String timeEnd;
	String day;

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
		
		JButton btnAddSubject = new JButton("Add Subject");
		btnAddSubject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

					try {
							String insert_query = "INSERT INTO `student_subject`(`student_id`, `subject_id`, `schedule_id`) \r\n"
									+ "VALUES (?,(SELECT subject.subject_id FROM subject WHERE subject.subject_code=?),\r\n"
									+ "       (SELECT schedule.schedule_id FROM schedule WHERE schedule.schedule_time_start=?\r\n"
									+ "       AND schedule.schedule_time_end=? AND schedule.schedule_day=?))";
							PreparedStatement insert_stmt = con.prepareStatement(insert_query);
							insert_stmt.setInt(1, account_id);
							insert_stmt.setString(2, subjectList.getSelectedValue().getCode());
							insert_stmt.setString(3, timeStart);
							insert_stmt.setString(4, timeEnd);
							insert_stmt.setString(5, day);
							insert_stmt.executeUpdate();
							
							insert_stmt.close();
		
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
		btnAddSubject.setBounds(10, 247, 117, 23);
		contentPane.add(btnAddSubject);
		
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
				subjectList = new JList<Subject>();
				DefaultListModel<Subject> subjectModel = new DefaultListModel<>();
				
				// TODO fix this subject view
				
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
					
					subjectModel.addElement(new Subject(subject_name, subject_code));
				}
				
				subjectList.setModel(subjectModel);
				subjectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				subjects_results.close();
				subjects_stmt.close();
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 69, 231, 159);
				contentPane.add(scrollPane);
				scrollPane.setViewportView(subjectList);
				
				JScrollPane scrollPane_1 = new JScrollPane();
				scrollPane_1.setBounds(251, 69, 173, 159);
				contentPane.add(scrollPane_1);
				
				// SHOW SCHEDULES WHEN SELECTING A SUBJECT
				JList<Schedule> scheduleList = new JList<>();
				
				subjectList.addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						// TODO Auto-generated method stub
						DefaultListModel<Schedule> scheduleModel = new DefaultListModel<>();
						
						try {
							String subjectCode = subjectList.getSelectedValue().getCode();
							String scheduleQuery = "SELECT schedule.schedule_time_start, schedule.schedule_time_end, schedule.schedule_day\r\n"
									+ "FROM subject_schedule\r\n"
									+ "INNER JOIN schedule ON schedule.schedule_id=subject_schedule.schedule_id\r\n"
									+ "INNER JOIN subject ON subject.subject_id=subject_schedule.subject_id\r\n"
									+ "WHERE subject.subject_id=(SELECT subject.subject_id FROM subject WHERE subject.subject_code=?);";
							PreparedStatement schedulePS = con.prepareStatement(scheduleQuery);
							schedulePS.setString(1, subjectCode);
							ResultSet scheduleResult = schedulePS.executeQuery();
							
							while (scheduleResult.next()) {	
								String timeStart = scheduleResult.getString(1);
								String timeEnd = scheduleResult.getString(2);
								String day = scheduleResult.getString(3);
								
								scheduleModel.addElement(new Schedule(timeStart, timeEnd, day));
							}
							
							scheduleResult.close();
							schedulePS.close();
						}
						catch(SQLException ex) {
							System.out.println(ex);
						}
						
						scheduleList.setModel(scheduleModel);
						scheduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
					}
					
				});
				
				scrollPane_1.setViewportView(scheduleList);
				
				scheduleList.addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						// TODO Auto-generated method stub
						timeStart = scheduleList.getSelectedValue().getTimeStart();
						timeEnd = scheduleList.getSelectedValue().getTimeEnd();
						day = scheduleList.getSelectedValue().getDay();
					}
					
				});
				
				
	}
}
