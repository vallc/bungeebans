package net.valc.bungeebans.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.md_5.bungee.BungeeCord;
import net.valc.bungeebans.Main;

public class MySQL {
	
	private String host = "localhost";
	private int port = 3306;
	private String username = "root";
	private String password = "";
	private String database = "strengcraft";
	private Connection conn;

	public MySQL(String host, int port, String username, String password, String database) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.database = database;
	}

	public MySQL(MySQLCredentials credentials) {
		this.host = credentials.getHost();
		this.port = credentials.getPort();
		this.username = credentials.getUsername();
		this.password = credentials.getPassword();
		this.database = credentials.getDatabase();
	}

	public boolean isConnected() {
		return this.conn != null;
	}

	public void openConnection() {
		if (!isConnected()) {
			try {
				this.conn = DriverManager.getConnection(
						"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true",
						this.username, this.password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		if (isConnected()) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(String query) {
		if (isConnected()) {
			BungeeCord.getInstance().getScheduler().runAsync(Main.sharedInstance(), new Runnable() {
				@Override
				public void run() {
					try {
						PreparedStatement pst = conn.prepareStatement(query);
						pst.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public ResultSet getResult(String query) {
		if (isConnected()) {
			try {
				PreparedStatement pst = this.conn.prepareStatement(query);
				return pst.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static class MySQLCredentials {
		private String host = "";
		private int port;
		private String username = "";
		private String password = "";
		private String database = "";

		public MySQLCredentials(String host, int port, String username, String password, String database) {
			this.host = host;
			this.port = port;
			this.username = username;
			this.password = password;
			this.database = database;
		}

		public String getHost() {
			return host;
		}

		public String getDatabase() {
			return database;
		}

		public String getPassword() {
			return password;
		}

		public int getPort() {
			return port;
		}

		public String getUsername() {
			return username;
		}

		public void setDatabase(String database) {
			this.database = database;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}

}