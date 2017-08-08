package wh.cg.metadata;

public class Option {
	private String name;
	private String value;
	private boolean selected;

	public Option(String value, String name) {
		this.name = name;
		this.value = value;
		this.selected = false;
	}

	public Option(String value, String name, boolean selected) {
		this.name = name;
		this.value = value;
		this.selected = selected;
	}

	public boolean getSelected() {
		return selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
