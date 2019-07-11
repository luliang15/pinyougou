package com.pinyougou.entity;
import java.io.Serializable;
import java.util.List;

/**
 * 存放实体类
 * @program:PinYouGou01
 * @description:分页结果封装对象
 * @author:Mr.lu
 * @create:2019-07-10 15:19
 **/
/**
 * 分页结果封装对象
 * @author Steven
 *
 */
public class PageResult<T> implements Serializable {
    private long pages;//总页数
    private List<T> rows;//当前页结果

    public PageResult() {
    }

    public PageResult(long pages, List<T> rows) {
        super();
        this.pages = pages;
        this.rows = rows;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "pages=" + pages +
                ", rows=" + rows +
                '}';
    }
}
