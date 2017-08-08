package wh.cg;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import wh.cg.metadata.Column;
import wh.cg.metadata.Table;

public class JDBCDataLoader {

	private String user = "root";
	private String password = "123456";
	private String url = "jdbc:mysql://localhost/xituwa";

	public List<Table> load() {
		List<Table> tables = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			tables = getMetadata(conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return tables;
	}

	private List<Table> getMetadata(Connection conn) throws SQLException {
		List<Table> tables = null;
		DatabaseMetaData metadata = conn.getMetaData();
		String catalog = conn.getCatalog();
		tables = getTables(metadata, catalog);
		for (Table table : tables) {
			Set<String> primaryKeys = getPrimaryKeys(metadata, catalog, table.getName());
			List<Column> columns = getColumns(metadata, catalog, table.getName());
			for (Column column : columns) {
				if (primaryKeys.contains(column.getName())) {
					column.setPrimaryKey(true);
				}
			}
			table.setColumns(columns);
		}
		return tables;

	}

	private Set<String> getPrimaryKeys(DatabaseMetaData metadata, String catalog, String table) throws SQLException {
		Set<String> primaryKeys = new HashSet<String>();
		ResultSet rs = metadata.getPrimaryKeys(catalog, null, table);
		String column = null;
		try {
			while (rs.next()) {
				column = rs.getString("COLUMN_NAME");
				primaryKeys.add(column);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
		return primaryKeys;

	}

	private List<Table> getTables(DatabaseMetaData metadata, String catalog) throws SQLException {
		List<Table> tables = new ArrayList<Table>();
		Table table;
		String name = null, remarks = null;
		ResultSet rs = null;
		try {
			rs = metadata.getTables(catalog, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				name = rs.getString("TABLE_NAME");
				remarks = rs.getString("REMARKS");
				table = new Table();
				table.setName(name);
				table.setRemarks(remarks);
				tables.add(table);
			}
			rs.close();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
		return tables;
	}

	private List<Column> getColumns(DatabaseMetaData metadata, String catalog, String table) throws SQLException {
		List<Column> columns = new ArrayList<Column>();
		Column column;
		ResultSet rs = null;
		try {
			rs = metadata.getColumns(catalog, null, table, null);
			while (rs.next()) {
				column = new Column();
				column.setName(rs.getString("COLUMN_NAME"));
				column.setRemarks(rs.getString("REMARKS"));
				column.setType(rs.getInt("DATA_TYPE"));
				column.setNullable(rs.getBoolean("NULLABLE"));
				column.setSize(rs.getInt("COLUMN_SIZE"));
				columns.add(column);
			}
			rs.close();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
		return columns;
	}
}
