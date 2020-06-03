package com.yu.service;

import com.yu.entity.BookInfo;
import com.yu.util.R;

import java.util.List;

public interface BookInfoService {

    /**
     * 查询图书列表
     * @return
     */
    List<BookInfo> selectAll();

    /**
     * 新增图书
     * @param bookInfo
     * @return
     */
    R insert(BookInfo bookInfo);

    /**
     * 修改图书信息
     * @param bookInfo
     * @return
     */
    R updateByPrimaryKey(BookInfo bookInfo);

    /**
     * 删除图书
     * @param id
     * @return
     */
    R deleteByPrimaryKey(int id);

    /**
     * 查询图书详情
     * @param id
     * @return
     */
    R selectByPrimaryKey(int id);
}
