package wh.cg.metadata;

import java.sql.Types;
import java.util.Map;

public class Column {
	private String name;
	private String remarks;
	private int type = Types.INTEGER;
	private int size;
	private boolean nullable = false;
	private String defaultValue;
	private boolean unsigned = false;
	private boolean primaryKey = false;
	private boolean autoIncrement = false;
	@Deprecated
	private Display display;
	@Deprecated
	private Element element;
	private Map<String, Display> displays;
	private Map<String, String> dictionary;

	public Column() {

	}

	public Map<String, Display> getDisplays() {
		return displays;
	}

	public void setDisplays(Map<String, Display> displays) {
		this.displays = displays;
	}

	public Map<String, String> getDictionary() {
		return this.dictionary;
	}

	public void setDictionary(Map<String, String> dictionary) {
		this.dictionary = dictionary;
	}

	public Column(String name, String remarks) {
		this.name = name;
		this.remarks = remarks;
	}

	public void setDefaultValue(String value) {
		this.defaultValue = value;
	}

	public boolean getUnsigned() {
		return unsigned;
	}

	public void setUnsigned(boolean unsigned) {
		this.unsigned = unsigned;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public boolean getAutoIncrement() {
		return autoIncrement;
	}

	@Deprecated
	public Display getDisplay() {
		return display;
	}

	@Deprecated
	public void setDisplay(Display display) {
		this.display = display;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean getPrimaryKey() {
		return this.primaryKey;
	}

	@Deprecated
	public void setElement(Element element) {
		this.element = element;
	}

	@Deprecated
	public Element getElement() {
		return this.element;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		switch (type) {
		case Types.TINYINT:
			this.size = 4;
			break;
		case Types.SMALLINT:
			this.size = 8;
			break;
		case Types.BIGINT:
			this.size = 12;
			break;
		case Types.INTEGER:
			this.size = 12;
			break;
		default:
			//
		}
	}

	public void setTypeName(String typeName) {
		String name = typeName.toLowerCase();
		if (name.equals("bigint")) {
			type = Types.BIGINT;
		} else if (name.equals("smallint")) {
			type = Types.SMALLINT;
		} else if (name.equals("tinyint")) {
			type = Types.TINYINT;
		} else if (name.equals("varchar")) {
			type = Types.VARCHAR;
		} else if (name.equals("integer")) {
			type = Types.INTEGER;
		} else if (name.equals("date")) {
			type = Types.DATE;
		} else if (name.equals("timestamp")) {
			type = Types.TIMESTAMP;
		}
	}

	public String getTypeName() {
		String typeName = null;
		switch (this.type) {
		case Types.BIGINT:
			typeName = "bigint";
			break;
		case Types.SMALLINT:
			typeName = "smallint";
			break;
		case Types.TINYINT:
			typeName = "tinyint";
			break;
		case Types.INTEGER:
			typeName = "integer";
			break;
		case Types.VARCHAR:
			typeName = "varchar";
			break;
		case Types.DATE:
			typeName = "date";
			break;
		case Types.TIMESTAMP:
			typeName = "timestamp";
			break;
		}
		return typeName;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean getNullable() {
		return this.nullable;
	}
}
