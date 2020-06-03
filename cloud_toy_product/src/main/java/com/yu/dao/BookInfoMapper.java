package com.yu.dao;

import com.yu.entity.BookInfo;
import java.util.List;

public interface BookInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Wed Jun 03 09:38:43 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Wed Jun 03 09:38:43 CST 2020
     */
    int insert(BookInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Wed Jun 03 09:38:43 CST 2020
     */
    BookInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Wed Jun 03 09:38:43 CST 2020
     */
    List<BookInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Wed Jun 03 09:38:43 CST 2020
     */
    int updateByPrimaryKey(BookInfo record);
}