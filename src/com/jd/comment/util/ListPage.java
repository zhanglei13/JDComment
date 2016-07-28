package com.jd.comment.util;

import java.util.List;

public class ListPage<T> {
    
    private List<T> list = null;
    private Page page = null;
    
    public ListPage(){}
    
    public ListPage(List<T> list, Page page){
        this.list = list;
        this.page = page;
    }
    
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
}