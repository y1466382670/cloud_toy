package com.yu.service;

import com.yu.entity.BookClassify;
import com.yu.util.R;

import java.util.List;

public interface BookClassifyService {

    /**
     * 查询所有图书分类
     * @return
     */
    List<BookClassify> selectAll();

    /**
     * 新增图书分类
     * @param bookClassify
     * @return
     */
    R insert(BookClassify bookClassify);

    /**
     * 修改图书分类
     * @param bookClassify
     * @return
     */
    R updateByPrimaryKey(BookClassify bookClassify);

    /**
     * 删除分类
     * @param id
     * @return
     */
    R deleteByPrimaryKey(int id);

    /**
     * 查询图书分类详情
     * @param id
     * @return
     */
    R selectByPrimaryKey(int id);
}
