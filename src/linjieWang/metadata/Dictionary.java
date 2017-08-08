package wh.cg.metadata;

import java.util.Map;

public class Dictionary {
	private String dataSource;
	private Map<String, String> keyValuePairs;

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, String> getKeyValuePairs() {
		return keyValuePairs;
	}

	public void setKeyValuePairs(Map<String, String> keyValuePairs) {
		this.keyValuePairs = keyValuePairs;
	}
}
