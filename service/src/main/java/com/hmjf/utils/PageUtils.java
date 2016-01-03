package com.hmjf.utils;

import com.hmjf.domain.Page;
import com.hmjf.domain.PageList;

import java.util.List;

/**
 * Created by jack on 16/1/3.
 */
public class PageUtils {

    public static <T> PageList<T> page(List<T> list,long total,int pageIndex,int pageSize,int pageCount){
        PageList<T> pageList = new PageList();
        Page page = new Page();
        page.setPageCount(pageCount);
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalCount(total);
        pageList.setList(list);
        pageList.setPage(page);

        return pageList;
    }

    public static <T> PageList<T> page(org.springframework.data.domain.Page data,int pageIndex,int pageSize){
        return page(data.getContent(),data.getTotalElements(),pageIndex,pageSize,data.getTotalPages());
    }

}
