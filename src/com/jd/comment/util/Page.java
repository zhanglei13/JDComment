package com.jd.comment.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Page {
    private long pageIndex = 1;     //当前页数
    private long pageSize = 20;  //每页显示记录数
    
    private long currentRow = 0; //当前记录数
    private long rowCount = 0;       //总记录数
    
    private long pageCount=0;    //总共页数,一共有多少页
    
    private long firstPage = 1;      //首页
    private long prevPage = 1;       //上一页
    private long nextPage = 1;       //下一页
    private long lastPage = 1;       //最后一页
    
    private Page(){}
    
    public Page(int pageIndex, int pageSize){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.countCurrentRow(); //初始化当前记录数
    }
    
    /** 
     * @Author: Charles
     * @Description: 初始化（首页、上一页、当前页面、下一页、尾页）信息
     */
    public void initPage(){
        if(this.rowCount != 0){
            this.pageCount=(int)Math.ceil((rowCount+0.0)/pageSize);
            
            this.lastPage = (this.rowCount/this.pageSize + 1);
            this.nextPage = this.pageIndex + 1;
            this.prevPage = this.pageIndex - 1;
            
            if((this.pageIndex < this.firstPage) || (this.prevPage < this.firstPage)){
                this.pageIndex = this.firstPage;
                this.prevPage = this.firstPage;
                this.nextPage = this.pageIndex + 1;
            }
            if((this.pageIndex > this.lastPage) || (this.nextPage > this.lastPage)){
                this.pageIndex = this.lastPage;
                this.prevPage = this.lastPage - 1;
                this.nextPage = this.lastPage;
            }
            if(this.lastPage==1){
                this.prevPage = 1;
                this.nextPage = 1;
            }
        }
    }
    /** 
     * @Author: Charles
     * @Description: 计算出当前记录数目
     */
    public void countCurrentRow(){
        this.currentRow = (this.pageIndex - 1) * this.pageSize;
    }
    
    /** 
     * @Author: Charles
     * @Description: 设置总记录数的时候初始化所有数据
     * @param countRow void: 
     */
    public void setRowCount(long countRow) {
        this.rowCount = countRow;
        this.initPage();
        this.countCurrentRow();
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
        this.countCurrentRow();
    }

    public long getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(long currentRow) {
        this.currentRow = currentRow;
    }

    public long getRowCount() {
        return rowCount;
    }

    public long getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(long firstPage) {
        this.firstPage = firstPage;
    }

    public long getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(long prevPage) {
        this.prevPage = prevPage;
    }

    public long getNextPage() {
        return nextPage;
    }

    public void setNextPage(long nextPage) {
        this.nextPage = nextPage;
    }

    public long getLastPage() {
        return lastPage;
    }

    public void setLastPage(long lastPage) {
        this.lastPage = lastPage;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

   
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
