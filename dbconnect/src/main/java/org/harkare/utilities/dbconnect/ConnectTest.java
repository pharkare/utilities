package org.harkare.utilities.dbconnect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectTest {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter SQL Server name: ");
		String server = br.readLine();
		System.out.print("Enter SQL Server Instance name: ");
		String instance = br.readLine();
		System.out.print("Enter SQL Server Database: ");
		String database = br.readLine();
		System.out.print("Enter username: ");
		String username = br.readLine();
		System.out.print("Enter password: ");
		String password = br.readLine();

		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		String url = String.format("jdbc:jtds:sqlserver://%s;instance=%s;databasename=%s;schema=dbo", server, instance, database);
		System.out.println(String.format("Trying [%s] using user [%s] password [%s]", url, username, password));
		Connection connection = DriverManager.getConnection(url, username, password);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select sysdatetime() as TimeNow;");
		while (rs.next()) {
			System.out.println(String.format("Connection Successful at %s", rs.getTimestamp("TimeNow")));
		}
	}
}
