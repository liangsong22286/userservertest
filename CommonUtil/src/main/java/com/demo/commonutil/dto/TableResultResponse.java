package com.demo.commonutil.dto;

import java.util.List;

/**
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class TableResultResponse<T> extends BaseResponse {

    TableData<T> data;

    public TableResultResponse(long total, List<T> rows) {
        this.data = new TableData<T>(total, rows);
    }

    public TableResultResponse() {
        this.data = new TableData<T>();
    }

    public TableResultResponse<T> total(int total) {
        this.data.setTotal(total);
        return this;
    }

    public TableResultResponse<T> total(List<T> rows) {
        this.data.setRows(rows);
        return this;
    }

    public TableResultResponse<T> code(int code) {
    	setCode(code);
    	return this;
    }
    public TableResultResponse<T> success(boolean success) {
        this.setSuccess(success);
        return this;
    }

    public TableResultResponse<T> msg(String msg) {
        this.setMsg(msg);
        return this;
    }
    
    
    public TableData<T> getData() {
        return data;
    }

    public void setData(TableData<T> data) {
        this.data = data;
    }

    public class TableData<T> {
        long total;
        List<T> rows;

        public TableData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }

        public TableData() {
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }
    }
}
