package com.yu.dao;

import com.yu.entity.BookClassifyDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookClassifyDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_classify_detail
     *
     * @mbggenerated Wed Jun 03 11:20:01 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_classify_detail
     *
     * @mbggenerated Wed Jun 03 11:20:01 CST 2020
     */
    int insert(BookClassifyDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_classify_detail
     *
     * @mbggenerated Wed Jun 03 11:20:01 CST 2020
     */
    BookClassifyDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_classify_detail
     *
     * @mbggenerated Wed Jun 03 11:20:01 CST 2020
     * @param bookClassifyCode 大分类编码
     * @param bookClassifyDetailCode 小分类编码
     * @param bookClassifyDetailName 小分类名称
     */
    List<BookClassifyDetail> selectAll(@Param("bookClassifyCode") String bookClassifyCode,
                                       @Param("bookClassifyDetailCode") String bookClassifyDetailCode,
                                       @Param("bookClassifyDetailName") String bookClassifyDetailName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_classify_detail
     *
     * @mbggenerated Wed Jun 03 11:20:01 CST 2020
     */
    int updateByPrimaryKey(BookClassifyDetail record);

    /**
     * 依据图书详细分类编码查询
     * @param bookClassifyDetailCode
     * @return
     */
    BookClassifyDetail selectByBookClassifyDetailCode(@Param("bookClassifyDetailCode") String bookClassifyDetailCode);

    /**
     * 依据图书详细分类名称查询
     * @param bookClassifyDetailName
     * @return
     */
    BookClassifyDetail selectByBookClassifyDetailName(@Param("bookClassifyDetailName") String bookClassifyDetailName);
}
