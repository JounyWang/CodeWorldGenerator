package wh.cg.metadata;

import java.util.List;

public class Table {

	private String name;
	private String remarks;
	private List<Column> columns;

	public Table() {

	}

	public Table(String name, String remarks) {
		this.name = name;
		this.remarks = remarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
