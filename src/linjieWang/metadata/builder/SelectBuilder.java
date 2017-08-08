package wh.cg.metadata.builder;

import wh.cg.metadata.Option;
import wh.cg.metadata.Select;

public class SelectBuilder {

	private Select select = new Select();

	public SelectBuilder setOptions(Option[] options) {
		select.setOptions(options);
		return this;
	}

	public SelectBuilder dataSource(String dataSource) {
		select.setDataSource(dataSource);
		return this;
	}

	public Select build() {
		return select;
	}
}
