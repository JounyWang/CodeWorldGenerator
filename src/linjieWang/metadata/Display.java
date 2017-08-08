package wh.cg.metadata;

import java.util.Map;

public class Display {
	@Deprecated
	private String width;
	@Deprecated
	private String style;
	@Deprecated
	private Option[] options;
	@Deprecated
	private Mapping mapping;
	private Element element;
	protected Map<String, String> attributes;
	private String text;

	public Display() {

	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}
	
	public Option[] getOptions() {
		return options;
	}

	public void setOptions(Option[] options) {
		this.options = options;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Display(String width) {
		this.width = width;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
