package wh.cg.metadata;

public class Select extends Element {
	private Option[] options;
	private String dataSource;

	public Select() {

	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setOptions(Option[] options) {
		this.options = options;
	}

	public Option[] getOptions() {
		return options;
	}
}
