package com.yu.service.impl;

import com.yu.dao.BookClassifyMapper;
import com.yu.entity.BookClassify;
import com.yu.service.BookClassifyService;
import com.yu.util.R;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookClassifyServiceImpl implements BookClassifyService {

    @Resource
    private BookClassifyMapper bookClassifyMapper;

    /**
     * 查询所有图书分类
     * @return
     * @param bookClassifyName
     * @param bookClassifyCode
     */
    @Override
    public List<BookClassify> selectAll(String bookClassifyName, String bookClassifyCode) {
        List<BookClassify> list = this.bookClassifyMapper.selectAll(bookClassifyName, bookClassifyCode);
        return list;
    }

    /**
     * 新增图书分类
     * @param bookClassify
     * @return
     */
    @Override
    public R insert(BookClassify bookClassify) {
        //根据分类编码查询
        BookClassify bookClassifyCode = this.bookClassifyMapper.selectByBookClassifyCode(bookClassify.getBookClassifyCode());
        if(!StringUtils.isEmpty(bookClassifyCode)){
            return R.error("分类编码已存在，请重新输入");
        }
        //根据分类名称查询
        BookClassify bookClassifyName = this.bookClassifyMapper.selectByBookClassifyName(bookClassify.getBookClassifyName());
        if(!StringUtils.isEmpty(bookClassifyName)){
            return R.error("分类名称已存在，请重新输入");
        }
        int result = this.bookClassifyMapper.insert(bookClassify);
        if(result<=0){
            return R.error();
        }
        return R.ok();
    }

    /**
     * 修改图书分类
     * @param bookClassify
     * @return
     */
    @Override
    public R updateByPrimaryKey(BookClassify bookClassify) {
        int result = this.bookClassifyMapper.updateByPrimaryKey(bookClassify);
        if(result<=0){
            return R.error();
        }
        return R.ok();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @Override
    public R deleteByPrimaryKey(int id) {
        int result = this.bookClassifyMapper.deleteByPrimaryKey(id);
        if(result<=0){
            return R.error("删除失败,失败原因:【信息不存在】");
        }
        return R.ok();
    }

    /**
     * 查询图书分类详情
     * @param id
     * @return
     */
    @Override
    public R selectByPrimaryKey(int id) {
        BookClassify bookClassify = this.bookClassifyMapper.selectByPrimaryKey(id);
        if(StringUtils.isEmpty(bookClassify)){
            return R.error("分类信息不存在");
        }
        return R.ok().put("result", bookClassify);
    }
}
