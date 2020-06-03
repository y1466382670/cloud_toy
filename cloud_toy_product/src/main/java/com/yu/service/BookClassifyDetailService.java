package com.yu.service;

import com.yu.entity.BookClassifyDetail;
import com.yu.util.R;

import java.util.List;

public interface BookClassifyDetailService {

    /**
     * 查询所有图书详细分类
     * @return
     */
    List<BookClassifyDetail> selectAll();

    /**
     * 新增图书详细分类
     * @param bookClassify
     * @return
     */
    R insert(BookClassifyDetail bookClassify);

    /**
     * 修改图书详细分类
     * @param bookClassify
     * @return
     */
    R updateByPrimaryKey(BookClassifyDetail bookClassify);

    /**
     * 删除详细分类
     * @param id
     * @return
     */
    R deleteByPrimaryKey(int id);

    /**
     * 查询图书详细分类详情
     * @param id
     * @return
     */
    R selectByPrimaryKey(int id);
}
