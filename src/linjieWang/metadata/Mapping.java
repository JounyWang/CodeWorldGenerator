package wh.cg.metadata;

public class Mapping {
	private String dataSource;
	private Option[] options;

	public Mapping(String dataSource) {
		this.dataSource = dataSource;
	}

	public Mapping(Option[] options) {
		this.options = options;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Option[] getOptions() {
		return options;
	}

	public void setOptions(Option[] options) {
		this.options = options;
	}
}
