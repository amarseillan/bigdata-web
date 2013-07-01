package bigdata.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bigdata.model.BarChart;
import bigdata.model.PieChart;
import bigdata.model.RealTimeChart;
import bigdata.model.RealTimeChartBuilder;

public class MySqlMetricDAO {

	static private MySqlMetricDAO instance;

	private String connectionString;
	private String user;

	private String password;

	synchronized static public MySqlMetricDAO getInstance() {
		if (instance == null) {
			instance = new MySqlMetricDAO();
		}
		return instance;
	}

	private MySqlMetricDAO() {
	}

	public RealTimeChart getMetrics(String metricID) {
		RealTimeChartBuilder builder = new RealTimeChartBuilder();
		Connection connection = null;
		try {
			connection = this.connect();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM " + metricID
					 + ";");
			while (rs.next()) {
				builder.addValue(rs.getString("metric_key"), rs.getLong("minute") * 60000, rs.getInt("quantity"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		builder.setTitle(metricID);
		return builder.prepareChartWithLimit(10);

	}
	
	private RealTimeChart getAvgDurationMetrics(String metric) {
		RealTimeChartBuilder builder = new RealTimeChartBuilder();
		Connection connection = null;
		try {
			connection = this.connect();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM " + metric
					 + ";");
			while (rs.next()) {
				String[] date = rs.getString("date").split("-");
				Date d = new Date(Integer.valueOf(date[0]), Integer.valueOf(date[1]),
						Integer.valueOf(date[2]));
				builder.addValue(rs.getString("name"), d.getTime(), rs.getInt("hits"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		builder.setTitle(metric);
		return builder.prepareChartWithLimit(10);
	}
	
	private BarChart getBarChartChannels(String tableName) {
		BarChart chart = new BarChart();
		Connection connection = null;
		try {
			connection = this.connect();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName
					 + " order by hits desc;");
			while (rs.next()) {
				chart.addItem(rs.getString("name"), rs.getInt("hits"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return chart;
	}
	
	private BarChart getBarChartChannelsForAds(String tableName) {
		BarChart chart = new BarChart();
		Connection connection = null;
		try {
			connection = this.connect();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName
					 + " order by hits desc;");
			while (rs.next()) {
				chart.addItem(rs.getString("channel_id"), rs.getInt("hits"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return chart;
	}
	
	private PieChart getPieChartChannels(String tableName) {
		PieChart chart = new PieChart();
		Connection connection = null;
		try {
			connection = this.connect();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName
					 + ";");
			while (rs.next()) {
				chart.addItem(rs.getString("name"), rs.getInt("hits"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return chart;
	}
	
	public BarChart getTop10Channels() {
		return getBarChartChannels("top10channels");
	}
	
	public BarChart getTop10Categories() {
		return getBarChartChannels("top10categories");
	}
	
	public BarChart getTopChannelAds() {
		return getBarChartChannelsForAds("ads_per_channel");
	}
	
	public RealTimeChart getAvgDurationChannel() {
		return getAvgDurationMetrics("avg_duration_channel");
	}
	
	
	public RealTimeChart getAvgDurationCategory() {
		return getAvgDurationMetrics("avg_duration_category");
	}
	
	public PieChart getAudiencePerType() {
		return getPieChartChannels("audience_per_type");
	}
	
	public PieChart getAudiencePerFamilyGroup() {
		return getPieChartChannels("audience_per_fg");
	}
	

	private Connection connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;

		conn = DriverManager.getConnection(connectionString, user, password);

		return conn;
	}

	public void setConnectionString(String connectionString) {
		System.out.println("asd");
		this.connectionString = connectionString;
	}

	public void setUser(String user) {
		System.out.println(user);
		this.user = user;
	}

	public void setPassword(String password) {
		System.out.println("asd");
		this.password = password;
	}
}
