package com.yu.service.impl;

import com.yu.dao.BookClassifyDetailMapper;
import com.yu.entity.BookClassifyDetail;
import com.yu.service.BookClassifyDetailService;
import com.yu.util.R;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookClassifyDetailServiceImpl implements BookClassifyDetailService {

    @Resource
    private BookClassifyDetailMapper bookClassifyDetailMapper;

    /**
     * 查询所有图书分类
     * @return
     * @param bookClassifyCode
     * @param bookClassifyDetailCode
     * @param bookClassifyDetailName
     */
    @Override
    public List<BookClassifyDetail> selectAll(String bookClassifyCode, String bookClassifyDetailCode, String bookClassifyDetailName) {
        List<BookClassifyDetail> list = this.bookClassifyDetailMapper.selectAll(bookClassifyCode,bookClassifyDetailCode,bookClassifyDetailName);
        return list;
    }

    /**
     * 新增图书详细分类
     * @param bookClassifyDetail
     * @return
     */
    @Override
    public R insert(BookClassifyDetail bookClassifyDetail) {
        //根据详细分类编码查询
        BookClassifyDetail bookClassifyDetailCode = this.bookClassifyDetailMapper.selectByBookClassifyDetailCode(bookClassifyDetail.getBookClassifyDetailCode());
        if(!StringUtils.isEmpty(bookClassifyDetailCode)){
            return R.error("详细分类编码已存在，请重新输入");
        }
        //根据详细分类名称查询
        BookClassifyDetail bookClassifyDetailName = this.bookClassifyDetailMapper.selectByBookClassifyDetailName(bookClassifyDetail.getBookClassifyDetailName());
        if(!StringUtils.isEmpty(bookClassifyDetailName)){
            return R.error("详细分类名称已存在，请重新输入");
        }
        int result = this.bookClassifyDetailMapper.insert(bookClassifyDetail);
        if(result<=0){
            return R.error();
        }
        return R.ok();
    }

    /**
     * 修改图书详细分类
     * @param bookClassifyDetail
     * @return
     */
    @Override
    public R updateByPrimaryKey(BookClassifyDetail bookClassifyDetail) {
        int result = this.bookClassifyDetailMapper.updateByPrimaryKey(bookClassifyDetail);
        if(result<=0){
            return R.error();
        }
        return R.ok();
    }

    /**
     * 删除详细分类
     * @param id
     * @return
     */
    @Override
    public R deleteByPrimaryKey(int id) {
        int result = this.bookClassifyDetailMapper.deleteByPrimaryKey(id);
        if(result<=0){
            return R.error("删除失败,失败原因:【信息不存在】");
        }
        return R.ok();
    }

    /**
     * 查询图书详细分类详情
     * @param id
     * @return
     */
    @Override
    public R selectByPrimaryKey(int id) {
        BookClassifyDetail bookClassifyDetail = this.bookClassifyDetailMapper.selectByPrimaryKey(id);
        if(StringUtils.isEmpty(bookClassifyDetail)){
            return R.error("分类信息不存在");
        }
        return R.ok().put("result", bookClassifyDetail);
    }
}
