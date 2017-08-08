package wh.cg.loader.psms;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wh.cg.loader.Loader;
import wh.cg.metadata.Column;
import wh.cg.metadata.DateMask;
import wh.cg.metadata.Display;
import wh.cg.metadata.Element;
import wh.cg.metadata.Hidden;
import wh.cg.metadata.Link;
import wh.cg.metadata.Select;
import wh.cg.metadata.Table;
import wh.cg.metadata.TextArea;
import wh.cg.metadata.TextField;

public class JSONSchemaDataLoader implements Loader {
	private static final Logger logger = Logger.getLogger(Loader.class.getName());

	private String path;

	public JSONSchemaDataLoader(String path) {
		this.path = path;
	}

	private Element parseDisplay(JSONObject _display) {
		Element element = null;
		String _tagName, _type;
		if (_display.containsKey("tagName") && _display.containsKey("type")) {
			_tagName = _display.getString("tagName");
			_type = _display.getString("type");
			if (_display.containsKey("attributes")) {
				JSONObject _attributes = _display.getJSONObject("attributes");
				element.setAttributes(_attributes);
			}
		}
		return element;
	}

	private Map<String, String> parseAttributes(JSONObject _attributes) {
		Map<String, String> attributes = new HashMap<String, String>();
		String key;
		for (Iterator<String> it = _attributes.keys(); it.hasNext();) {
			key = it.next();
			attributes.put(key, _attributes.getString(key));
		}
		return attributes;
	}

	private Map<String, Display> parseDisplays(JSONObject _displays) {
		Map<String, Display> displays = new HashMap<String, Display>();
		for (Iterator<String> it2 = _displays.keys(); it2.hasNext();) {
			String key2 = it2.next();
			JSONObject _display = _displays.getJSONObject(key2);
			Display display = new Display();
			Element element = null;
			if (_display.containsKey("element")) {
				String _className = _display.getString("element");
				if ("Select".equalsIgnoreCase(_className)) {
					element = new Select();
				} else if ("TextField".equalsIgnoreCase(_className)) {
					element = new TextField();
				} else if ("DatePicker".equalsIgnoreCase(_className)) {
					element = new DateMask();
				} else if ("DateMask".equalsIgnoreCase(_className)) {
					element = new DateMask();
				} else if ("Hidden".equalsIgnoreCase(_className)) {
					element = new Hidden();
				} else if ("TextArea".equalsIgnoreCase(_className)) {
					element = new TextArea();
				} else if ("TextField".equalsIgnoreCase(_className)) {
					element = new TextField();
				} else if ("Link".equalsIgnoreCase(_className)) {
					element = new Link();
				} else {
					element = parseDisplay(_display);
				}
				display.setElement(element);
			}
			if (_display.containsKey("text")) {
				display.setText(_display.getString("text"));
			}
			if (_display.containsKey("attributes")) {
				JSONObject _attributes = _display.getJSONObject("attributes");
				display.setAttributes(parseAttributes(_attributes));
			}
			displays.put(key2, display);
		}
		return displays;
	}

	private Map<String, String> parseDictionary(String dict) {
		// logger.info(dict);
		String regExp = "(\\d+)\\:\"([^\"]+)\"(,?)";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(dict);
		Map<String, String> dictionary = new HashMap<String, String>();
		String matches, key, value;
		while (matcher.find()) {
			matches = matcher.group();
			key = matches.replaceAll(regExp, "$1");
			value = matches.replaceAll(regExp, "$2");
			// logger.info(key + ": " + value);
			dictionary.put(key, value);
		}
		return dictionary;
	}

	@Override
	public List<Table> load() {
		File dir = new File(path);
		List<Table> tables = new ArrayList<Table>();
		if (dir.exists() && dir.isDirectory()) {
			String[] fileNames = dir.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".json");
				}
			});
			for (String fileName : fileNames) {
				File file = new File(dir, fileName);
				StringWriter sw = new StringWriter();
				try {
					IOUtils.copy(new FileReader(file), sw);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String jsonString = sw.toString();

				JSONObject _table = JSONObject.fromObject(jsonString);
				Table table = new Table();
				table.setName(_table.getString("title"));
				table.setRemarks(_table.getString("description"));
				List<Column> columns = new ArrayList<Column>();
				JSONObject _columns = _table.getJSONObject("properties");
				for (Iterator<String> it = _columns.keys(); it.hasNext();) {
					String key = it.next();
					JSONObject _column = (JSONObject) _columns.get(key);
					Column column = new Column();
					column.setName(key);
					if (_column.containsKey("description")) {
						String description = _column.getString("description");
						String remarks = null, dictionaryString = null;
						String regExp = "([^\\s]+)(\\s*)\\{(.+)\\}";
						if (description.matches(regExp)) {
							remarks = description.replaceAll(regExp, "$1");
							dictionaryString = description.replaceAll(regExp, "$3");
							logger.info(remarks);
							logger.info(dictionaryString);
							column.setDictionary(parseDictionary(dictionaryString));
						} else {
							remarks = description;
						}
						column.setRemarks(remarks);
					}

					String _type = _column.getString("type");
					if ("number".equals(_type)) {
						if (_column.containsKey("enum")) {
							column.setType(Types.TINYINT);
						} else {
							column.setType(Types.BIGINT);
						}
					} else if ("string".equals(_type)) {
						if (_column.containsKey("pattern")) {
							String _pattern = _column.getString("pattern");
							if (_pattern.equals("^/\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}/$")) {
								column.setType(Types.DATE);
							}
						} else {
							column.setType(Types.VARCHAR);
							column.setSize(255);
						}
					}
					column.setTypeName(_column.getString("type"));
					if (_column.containsKey("primaryKey")) {
						column.setPrimaryKey(_column.getBoolean("primaryKey"));
						column.setType(Types.BIGINT);
					}
					if (_column.containsKey("nullable")) {
						column.setNullable(_column.getBoolean("nullable"));
					}
					if (_column.containsKey("defaultValue")) {
						column.setDefaultValue(_column.getString("defaultValue"));
					} else if (column.getPrimaryKey()) {
						column.setDefaultValue("0");
					}
					if (_column.containsKey("autoIncrement"))
						column.setAutoIncrement(_column.getBoolean("autoIncrement"));
					if (_column.containsKey("unsigned"))
						column.setUnsigned(_column.getBoolean("unsigned"));
					if (_column.containsKey("size"))
						column.setSize(_column.getInt("size"));
					if (_column.containsKey("displays")) {
						JSONObject _displays = _column.getJSONObject("displays");
						Map<String, Display> displays = parseDisplays(_displays);
						column.setDisplays(displays);
						columns.add(column);
					}
					table.setColumns(columns);
				}
				JSONArray _required = _table.getJSONArray("required");
				for (int i = 0; i < _required.size(); i++) {
					String key = _required.getString(i);
					for (Column column : columns) {
						if (column.getName().equals(key)) {
							column.setPrimaryKey(true);
							break;
						}
					}
				}
				tables.add(table);
			}
		} else {
			logger.warning(path + " does NOT exsit.");
		}
		return tables;
	}
}
