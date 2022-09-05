package com.demo.commonutil.utilits;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 查询参数
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
    private int page = 1;
    //每页条数
    private int limit = 10;
    //每页条数
    private String sortColumn = "";
    //每页条数
    private String sortAsc = "";

    public Query(Map<String, Object> params){
    	Map<String, Object> newParams = new HashMap<String, Object>();
    	
        Set<String> keySet2 = params.keySet();
        Iterator<String> iterator = keySet2.iterator();
        while(iterator.hasNext()){
        	String key = iterator.next();
        	Object object = params.get(key);
        	if(object==null || object.toString().trim().equals("")){
        	}else{
        		newParams.put(key, object);
        	}
        }
    	
    	
        this.putAll(newParams);
        //分页参数
        if(newParams.get("page")!=null) {
            this.page = Integer.parseInt(newParams.get("page").toString());
        }
        if(newParams.get("limit")!=null) {
            this.limit = Integer.parseInt(newParams.get("limit").toString());
        }
        if(newParams.get("sortColumn")!=null) {
            this.setSortColumn(newParams.get("sortColumn").toString());
        }
        if(newParams.get("sortColumn")!=null) {
            this.setSortAsc(newParams.get("sortAsc").toString());
        }
        this.remove("page");
        this.remove("limit");
        this.remove("sortColumn");
        this.remove("sortAsc");
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


	public String getSortColumn() {
		return sortColumn;
	}


	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}


	public String getSortAsc() {
		return sortAsc;
	}


	public void setSortAsc(String sortAsc) {
		this.sortAsc = sortAsc;
	}
}
