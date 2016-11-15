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

// Database 를 사용할 때 사용하는 클래스이다.
public class Database {
	// Properties ..
	private static Connection connection = null;
	private static String url = "jdbc:mysql://localhost:3306/everychecking?useSSL=false";
	private static String id = "root";
	private static String pw = "root";
	
	private static Statement statement = null;

	// Mysql 의 초기화 작업을 기술한 메서드이다.
	public static void initializing () {
		// 데이터베이스와 연결되지 않았다면
		if (connection == null) {
			try {
				// JVM 이 동작을 시작하고 코드가 실행 되기 전까지는 어떤 JDBC 드라이버가 사용이 될지 알지 못하기 때문에 JDBC 드라이버를 동적 로딩한다.
				Class.forName("com.mysql.jdbc.Driver");
				// Mysql 과 연결한다.
				connection = DriverManager.getConnection(url, id, pw);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			// 현재 Connection 으로부터 Statement 를 생성해낸다.
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Statement getStatement () {
		// Statement 가 비어있다면, 초기화를 진행한다.
		if ( statement == null ) {
			initializing();
		}
		return statement;
	}
	
	public static void createSession(int no) {
		// users 테이블에서 해당 no 를 가진 row 를 select 한다.
		String query = "select * from users where no=" + no;
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			if (resultSet.next()) {
				if (Session.getUser() != null) {
					System.out.println("Remove current session.");
				}
				
				// 세선을 생성한다.
				Session.setUser(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
				Session.setCheckModel(null);
			} else {
				// 결과가 존재하지 않다면 예외를 발생시킨다.
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
		// DB 에 user 를 insert 한다.
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
		// 반환할 user 리스트
		ArrayList<User> userList = new ArrayList<User>();
		
		// users 의 모든 요소를 가져온다.
		String query = "select * from users";
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			// 모든 요소를 리스트에 추가한다.
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

		// users 의 모든 요소를 가져온다.
		String query = "select * from users";
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			// 모든 요소를 리스트에 추가한다.
			while (resultSet.next()) {
				items.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Result> getResultList() {
		// 반환할 result 리스트
		ArrayList<Result> resultList = new ArrayList<Result>();
		
		// result 의 모든 요소를 가져온다.
		String query = "select * from results";
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			// 모든 요소를 리스트에 추가한다.
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
		
		// result 의 모든 요소를 가져온다.
		String query = "select * from results r, users u where r.user_no = u.no";
		
		try {
			ResultSet resultSet = getStatement().executeQuery(query);
			
			// 모든 요소를 리스트에 추가한다.
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
