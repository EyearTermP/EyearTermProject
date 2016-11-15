package com.everychecking.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.everychecking.model.data.Result;
import com.everychecking.model.data.User;
import com.everychecking.model.data.VisualAcuity;
import com.everychecking.session.Session;
import com.jfoenix.controls.JFXListView;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

// Database �� ����� �� ����ϴ� Ŭ�����̴�.
public class Database {
	// Properties ..
	private static Connection connection = null;
	private static String url = "jdbc:mysql://localhost:3306/everychecking?useSSL=false";
	private static String id = "root";
	private static String pw = "root";
	
	private static Statement statement = null;

	// Mysql �� �ʱ�ȭ �۾��� ����� �޼����̴�.
	public static void initializing () {
		// �����ͺ��̽��� ������� �ʾҴٸ�
		if (connection == null) {
			try {
				// JVM �� ������ �����ϰ� �ڵ尡 ���� �Ǳ� �������� � JDBC ����̹��� ����� ���� ���� ���ϱ� ������ JDBC ����̹��� ���� �ε��Ѵ�.
				Class.forName("com.mysql.jdbc.Driver");
				// Mysql �� �����Ѵ�.
				connection = DriverManager.getConnection(url, id, pw);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			// ���� Connection ���κ��� Statement �� �����س���.
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Statement getStatement () {
		// Statement �� ����ִٸ�, �ʱ�ȭ�� �����Ѵ�.
		if ( statement == null ) {
			initializing();
		}
		return statement;
	}
	
	public static void createSession(int no) {
		// users ���̺��� �ش� no �� ���� row �� select �Ѵ�.
		String query = "select * from users where no=" + no;
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			if (resultSet.next()) {
				if (Session.getUser() != null) {
					System.out.println("Remove current session.");
				}
				
				// ������ �����Ѵ�.
				Session.setUser(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
				Session.setCheckModel(null);
			} else {
				// ����� �������� �ʴٸ� ���ܸ� �߻���Ų��.
				try {
					throw new Exception("User." + no + " doesn't exist");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createUser(String name, int age) {
		// DB �� user �� insert �Ѵ�.
		String query = "insert into users (name, age) values ( '" + name + "',  " + age + " );";
		
		try {
			getStatement().execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeUser(int no) {
		String query = "delete from users where no = " + no + ";";
		
		try {
			getStatement().execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<User> getUserList() {
		// ��ȯ�� user ����Ʈ
		ArrayList<User> userList = new ArrayList<User>();
		
		// users �� ��� ��Ҹ� �����´�.
		String query = "select * from users";
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			// ��� ��Ҹ� ����Ʈ�� �߰��Ѵ�.
			while (resultSet.next()) {
				userList.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
	public static void getUserList(ListView<User> listView) {
		ObservableList<User> items = listView.getItems();
		
		items.clear();

		// users �� ��� ��Ҹ� �����´�.
		String query = "select * from users";
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			// ��� ��Ҹ� ����Ʈ�� �߰��Ѵ�.
			while (resultSet.next()) {
				items.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Result> getResultList() {
		// ��ȯ�� result ����Ʈ
		ArrayList<Result> resultList = new ArrayList<Result>();
		
		// result �� ��� ��Ҹ� �����´�.
		String query = "select * from results";
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			// ��� ��Ҹ� ����Ʈ�� �߰��Ѵ�.
			while (resultSet.next()) {
				String queryUser = "select * from users where no=" + resultSet.getInt(2);
				
				try {
					ResultSet resultSetUser = getStatement().executeQuery(queryUser);
					
					if (resultSetUser.next()) {
						Date checkDay = resultSet.getDate(2);
						
						Double visualAcuityLeft = null;
						try {
							visualAcuityLeft = resultSet.getDouble(3);
						} catch (Exception e) {}
						if (resultSet.wasNull()) {
							visualAcuityLeft = null;
						}
						
						Double visualAcuityRight = null;
						try {
							visualAcuityRight = resultSet.getDouble(4);
						} catch (Exception e) {}
						if (resultSet.wasNull()) {
							visualAcuityRight = null;
						}
						
						Boolean isColorBlindness = null;
						try {
							isColorBlindness = resultSet.getBoolean(5);
						} catch (Exception e) {}
						if (resultSet.wasNull()) {
							isColorBlindness = null;
						}
						
						Boolean isAstigmatism = null;
						try {
							isAstigmatism = resultSet.getBoolean(6);
						} catch (Exception e) {}
						if (resultSet.wasNull()) {
							isAstigmatism = null;
						}
						
						Integer audioFrequency = null;
						try {
							audioFrequency = resultSet.getInt(7);
						} catch (Exception e) {}
						if (resultSet.wasNull()) {
							audioFrequency = null;
						}
						
						Integer isDysacousis = null;
						try {
							isDysacousis = resultSet.getInt(8);
						} catch (Exception e) {}
						if (resultSet.wasNull()) {
							isDysacousis = null;
						}
						
						User user = new User(resultSet.getInt(9), resultSet.getString(10), resultSet.getInt(11));
						
						Result result = new Result(user, checkDay, visualAcuityLeft, visualAcuityRight, isColorBlindness, isAstigmatism, audioFrequency, isDysacousis);
						
						resultList.add(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	public static void getResultList(JFXListView<Result> resultList) {
		ObservableList<Result> items = resultList.getItems();
		
		// result �� ��� ��Ҹ� �����´�.
		String query = "select * from results r, users u where r.user_no = u.no";
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			// ��� ��Ҹ� ����Ʈ�� �߰��Ѵ�.
			while (resultSet.next()) {
				Date checkDay = resultSet.getDate(2);
				
				Double visualAcuityLeft = null;
				try {
					visualAcuityLeft = resultSet.getDouble(3);
				} catch (Exception e) {}
				if (resultSet.wasNull()) {
					visualAcuityLeft = null;
				}
				
				Double visualAcuityRight = null;
				try {
					visualAcuityRight = resultSet.getDouble(4);
				} catch (Exception e) {}
				if (resultSet.wasNull()) {
					visualAcuityRight = null;
				}
				
				Boolean isColorBlindness = null;
				try {
					isColorBlindness = resultSet.getBoolean(5);
				} catch (Exception e) {}
				if (resultSet.wasNull()) {
					isColorBlindness = null;
				}
				
				Boolean isAstigmatism = null;
				try {
					isAstigmatism = resultSet.getBoolean(6);
				} catch (Exception e) {}
				if (resultSet.wasNull()) {
					isAstigmatism = null;
				}
				
				Integer audioFrequency = null;
				try {
					audioFrequency = resultSet.getInt(7);
				} catch (Exception e) {}
				if (resultSet.wasNull()) {
					audioFrequency = null;
				}
				
				Integer isDysacousis = null;
				try {
					isDysacousis = resultSet.getInt(8);
				} catch (Exception e) {}
				if (resultSet.wasNull()) {
					isDysacousis = null;
				}
				
				User user = new User(resultSet.getInt(9), resultSet.getString(10), resultSet.getInt(11));
				
				Result result = new Result(user, checkDay, visualAcuityLeft, visualAcuityRight, isColorBlindness, isAstigmatism, audioFrequency, isDysacousis);

				items.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertResult(VisualAcuity visualAcuity, Boolean isColorBlindness, Boolean isAstigmatism, Integer audioFrequency, Integer isDysacousis) {
		String left = null;
		String right= null;
		
		if (visualAcuity != null) {
			left = "" + visualAcuity.getLeft();
			right = "" + visualAcuity.getRight();
		}
		
		String query = "insert into results values (" + 
				Session.getUser().getNo() + ", " + 
				"date(now()), " + 
				left + ", " + 
				right + ", " + 
				isColorBlindness + ", " + 
				isAstigmatism + ", " + 
				audioFrequency + ", " + 
				isDysacousis + 
				") on duplicate key update " +
				(left==null?"":("visual_acuity_left = " + left + ", ")) +
				(right==null?"":("visual_acuity_right = " + right + ", ")) +
				(isColorBlindness==null?"":("is_color_blindness = " + isColorBlindness + ", ")) + 
				(isAstigmatism==null?"":("is_astigmatism = " + isAstigmatism + ", ")) + 
				(audioFrequency==null?"":("audio_frequency = " + audioFrequency + ", ")) + 
				(isDysacousis==null?"":("is_dysacousis = " + isDysacousis + ", ")) + 
				" check_day = date(now());";
		
		try {
			getStatement().execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
