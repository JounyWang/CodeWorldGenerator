package wh.cg.metadata.builder;

import wh.cg.metadata.Display;
import wh.cg.metadata.Element;
import wh.cg.metadata.Mapping;
import wh.cg.metadata.Option;

public class DisplayBuilder {
	private Display display = new Display();

	public DisplayBuilder width(String width) {
		display.setWidth(width);
		return this;
	}

	public DisplayBuilder mapping(String dataSource) {
		display.setMapping(new Mapping(dataSource));
		return this;
	}

	public DisplayBuilder mapping(Option[] options) {
		display.setMapping(new Mapping(options));
		return this;
	}

	public DisplayBuilder options(Option[] options) {
		display.setOptions(options);
		return this;
	}

	public DisplayBuilder element(Element element) {
		display.setElement(element);
		return this;
	}

	public DisplayBuilder style(String style) {
		display.setStyle(style);
		return this;
	}

	public Display build() {
		return display;
	}
}
