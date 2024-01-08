package control;


import java.util.List;

public interface DatamartManagerBuilder {
	void updateDatamart(String event, String tableName);
	void readFromDatamart(List<String> filterParameters);
}
