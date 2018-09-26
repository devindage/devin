package com.devin.framework.web.page;

import com.devin.common.utils.ServletUtils;
import com.devin.common.constant.Constants;

/**
 * 表格数据处理
 * 
 * @author devin
 */
public class TableSupport
{
    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        pageDomain.setIsTransfer(ServletUtils.getParameter(Constants.IS_TRANSFER));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }

}
