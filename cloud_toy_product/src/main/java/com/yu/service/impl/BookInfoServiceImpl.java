package com.yu.service.impl;

import com.yu.dao.BookInfoMapper;
import com.yu.dao.BookStockMapper;
import com.yu.entity.BookInfo;
import com.yu.entity.BookStock;
import com.yu.service.BookInfoService;
import com.yu.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookInfoServiceImpl implements BookInfoService {

    @Resource
    private BookInfoMapper bookInfoMapper;

    @Resource
    private BookStockMapper bookStockMapper;

    /**
     * 查询图书列表
     * @return
     */
    @Override
    public List<BookInfo> selectAll() {
        List<BookInfo> list = this.bookInfoMapper.selectAll();
        return list;
    }

    /**
     * 新增图书
     * @param bookInfo
     * @return
     */
    @Transactional
    @Override
    public R insert(BookInfo bookInfo) {
        int result = this.bookInfoMapper.insert(bookInfo);
        if(result<=0){
            return R.error("新增图书发生异常，请稍后重试");
        }
        BookStock bookStock = new BookStock();
        bookStock.setBookId(result);
        bookStock.setBookName(bookInfo.getBookName());
        bookStock.setStockNum(bookInfo.getStockNum());
        int result2 = this.bookStockMapper.insert(bookStock);
        if(result2<=0){
            return R.error("新增图书发生异常，请稍后重试");
        }
        return R.ok();
    }

    /**
     * 修改图书信息
     * @param bookInfo
     * @return
     */
    @Override
    public R updateByPrimaryKey(BookInfo bookInfo) {
        return null;
    }

    /**
     * 删除图书信息
     * @param id
     * @return
     */
    @Override
    public R deleteByPrimaryKey(int id) {
        BookInfo bookInfo = this.bookInfoMapper.selectByPrimaryKey(id);
        if(!StringUtils.isEmpty(bookInfo) && bookInfo.getState()==1){
            return R.error("有效图书不可删除");
        }
        int result = this.bookInfoMapper.deleteByPrimaryKey(id);
        if(result<=0){
            return R.error("删除失败，失败原因：【图书不存在】");
        }
        return R.ok();
    }

    /**
     * 查询图书信息
     * @param id
     * @return
     */
    @Override
    public R selectByPrimaryKey(int id) {
        BookInfo bookInfo = this.bookInfoMapper.selectByPrimaryKey(id);
        if(StringUtils.isEmpty(bookInfo)){
            return R.error("图书不存在");
        }
        return R.ok().put("result",bookInfo);
    }
}
