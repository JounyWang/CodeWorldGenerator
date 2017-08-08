package wh.cg.metadata.builder;

import wh.cg.metadata.Column;
import wh.cg.metadata.Display;
import wh.cg.metadata.Element;

public class ColumnBuilder {

	private Column column = new Column();

	public ColumnBuilder name(String name) {
		column.setName(name);
		return this;
	}

	public ColumnBuilder primaryKey(boolean primaryKey) {
		column.setPrimaryKey(primaryKey);
		return this;
	}

	public ColumnBuilder autoIncrement(boolean autoIncrement) {
		column.setAutoIncrement(autoIncrement);
		return this;
	}

	public ColumnBuilder unsigned(boolean unsigned) {
		column.setUnsigned(unsigned);
		return this;
	}

	public ColumnBuilder display(Display display) {
		column.setDisplay(display);
		return this;
	}

	public ColumnBuilder nullable(boolean nullable) {
		column.setNullable(nullable);
		return this;
	}

	public ColumnBuilder defaultValue(String defaultValue) {
		column.setDefaultValue(defaultValue);
		return this;
	}

	public ColumnBuilder type(int type) {
		column.setType(type);
		return this;
	}

	public ColumnBuilder typeName(String typeName) {
		column.setTypeName(typeName);
		return this;
	}

	public ColumnBuilder size(int size) {
		column.setSize(size);
		return this;
	}

	public ColumnBuilder remarks(String remarks) {
		column.setRemarks(remarks);
		return this;
	}

	public ColumnBuilder element(Element element) {
		column.setElement(element);
		return this;
	}

	public Column build() {
		return column;
	}
}
