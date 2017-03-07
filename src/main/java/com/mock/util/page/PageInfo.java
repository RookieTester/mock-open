package com.mock.util.page;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/11.
 */
public class PageInfo implements Serializable {

    public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize;
    private int currentPage;
    private int beginPage;
    private int endPage;
    private int prePage;
    private int nextPage;
    private int totalPage;
    private int totalCount;
    private int skipRows;
    private String order;

    private String[] orders;
    private String[] sorts;

    public static PageInfo createPageControl(HttpServletRequest request) {
        String pageIndexValue = request.getParameter("page");
        String pageRowsValue = request.getParameter("rows");
        String orderValue = request.getParameter("order");
        String sortValue = request.getParameter("sort");


        System.out.println("这是多少" + pageRowsValue);

        Integer pageIndex = 1;

        if (pageIndexValue != null && !pageIndexValue.equals("") && StringUtils.isNumeric(pageIndexValue)) {
            pageIndex = Integer.valueOf(pageIndexValue);
        }

        Integer pageRows = PageInfo.DEFAULT_PAGE_SIZE;
        if (pageRowsValue != null && !pageRowsValue.equals("") && StringUtils.isNumeric(pageRowsValue)) {
            pageRows = Integer.valueOf(pageRowsValue);
        }

        if (pageRows > 10000) {
            pageRows = 10000;
        }

        return new PageInfo(pageIndex, pageRows, orderValue, sortValue);
    }

    public int getBeginPage() {
        if (this.getTotalPage() > 10 && this.getCurrentPage() >= 6) {
            this.beginPage = this.getCurrentPage() - 5;
        } else {
            this.beginPage = 1;
        }

        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {

        if (this.getTotalPage() > 10 && this.getCurrentPage() >= 6) {
            this.endPage = this.getCurrentPage() + 4;
        } else if (this.getTotalPage() > 10) {
            this.endPage = 10;
        } else {
            this.endPage = this.getTotalPage();
        }

        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public PageInfo() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * @param currentPage
     * @param pageSize
     */
    public PageInfo(int currentPage, int pageSize, String order, String sort) {


        System.out.println("传入的是=" + pageSize);

        System.out.println("变更前=" + this.pageSize);

        this.currentPage = currentPage;
        this.pageSize = pageSize;
        System.out.println("变更后=" + this.pageSize);
        this.skipRows = this.currentPage * this.pageSize;

        if (order != null) {
            this.orders = order.split(",");
        } else if (this.order == null) {
            this.orders = "desc".split(",");
        }

        if (sort != null) {
            this.sorts = sort.split(",");
        } else if (this.order == null) {
            this.sorts = "createDate".split(",");
        }
    }

    /**
     * @param currentPage
     * @param pageSize
     */
    public PageInfo(int currentPage, int pageSize) {

        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.skipRows = this.currentPage * this.pageSize;
    }

    public PageInfo(HttpServletRequest request) {
        String pageSize = request.getParameter("showCount");
        String currentPage = request.getParameter("currentPage");

        System.out.println(pageSize);
        System.out.println(currentPage);

        this.currentPage = Integer.parseInt(currentPage);
        this.pageSize = Integer.parseInt(pageSize);
        this.skipRows = this.currentPage * this.pageSize;
    }

    public String[] getOrders() {
        return orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }

    public String[] getSorts() {
        return sorts;
    }

    public void setSorts(String[] sorts) {
        this.sorts = sorts;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getSkipRows() {
        this.skipRows = this.currentPage * this.pageSize;
        return skipRows;
    }

    public void setSkipRows(int skipRows) {
        this.skipRows = skipRows;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
