package wh.cg.metadata;

public class Form extends Element {
	private String action;

	public Form(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
