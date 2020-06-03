package com.yu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yu.entity.BookClassifyDetail;
import com.yu.service.BookClassifyDetailService;
import com.yu.util.DateUtil;
import com.yu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Api(value = "BookClassifyDetailController", description = "图书详细分类")
public class BookClassifyDetailController {

    private static final Logger logger = LoggerFactory.getLogger(BookClassifyDetailController.class);

    @Autowired
    private BookClassifyDetailService bookClassifyDetailService;

    /**
     * 查询图书分类列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/book.classify.detail.selectAll", method = RequestMethod.POST)
    @ApiOperation(value = "图书详细分类.查询分类列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R selectAll(
            @ApiParam(value = "pageNo", required = true) @RequestParam(value = "pageNo", required = true) int pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) int pageSize
    ){
        logger.info("图书详细分类.查询分类列表,请求参数:pageNo={},pageSize={}",pageNo,pageSize);
        PageHelper.startPage(pageNo,pageSize);
        List<BookClassifyDetail> list = bookClassifyDetailService.selectAll();
        PageInfo<BookClassifyDetail> pageInfo = new PageInfo<>(list);
        return R.ok().put("result",pageInfo);
    }

    /**
     * 新增分类
     * @return
     */
    @RequestMapping(value = "/book.classify.detail.save", method = RequestMethod.POST)
    @ApiOperation(value = "图书详细分类.新增分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R save(
            @ApiParam(name = "bookClassifyCode", value = "大分类编码", required = true) @RequestParam(value = "bookClassifyCode", required = true) String bookClassifyCode,
            @ApiParam(name = "bookClassifyDetailCode", value = "详细分类编码", required = true) @RequestParam(value = "bookClassifyDetailCode", required = true) String bookClassifyDetailCode,
            @ApiParam(name = "bookClassifyDetailName", value = "分类名称", required = true) @RequestParam(value = "bookClassifyDetailName", required = true) String bookClassifyDetailName,
            @ApiParam(name = "sort", value = "排序", required = true) @RequestParam(value = "sort", required = true) int sort,
            @ApiParam(name = "remark", value = "备注", required = false) @RequestParam(value = "remark", required = false) String remark
    ){
        logger.info("图书详细分类.新增分类,请求参数:bookClassifyCode={}, bookClassifyDetailCode={}, bookClassifyDetailName={}, sort={}, remark={}",
                bookClassifyCode,bookClassifyDetailCode,bookClassifyDetailName,sort,remark);
        BookClassifyDetail bookClassifyDetail = new BookClassifyDetail();
        bookClassifyDetail.setBookClassifyCode(bookClassifyCode);
        bookClassifyDetail.setBookClassifyDetailCode(bookClassifyDetailCode);
        bookClassifyDetail.setBookClassifyDetailName(bookClassifyDetailName);
        bookClassifyDetail.setSort(sort);
        bookClassifyDetail.setRemark(remark);
        bookClassifyDetail.setCreateDate(DateUtil.formatDateTime(new Date()));
        return this.bookClassifyDetailService.insert(bookClassifyDetail);
    }

    /**
     * 修改分类
     * @return
     */
    @RequestMapping(value = "/book.classify.detail.modify", method = RequestMethod.POST)
    @ApiOperation(value = "图书详细分类.修改分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R updateByPrimaryKey(
            @ApiParam(name = "id", value = "主键", required = true) @RequestParam(value = "id", required = true) int id,
            @ApiParam(name = "bookClassifyCode", value = "大分类编码", required = true) @RequestParam(value = "bookClassifyCode", required = true) String bookClassifyCode,
            @ApiParam(name = "bookClassifyDetailCode", value = "详细分类编码", required = true) @RequestParam(value = "bookClassifyDetailCode", required = true) String bookClassifyDetailCode,
            @ApiParam(name = "bookClassifyDetailName", value = "分类名称", required = true) @RequestParam(value = "bookClassifyDetailName", required = true) String bookClassifyDetailName,
            @ApiParam(name = "sort", value = "排序", required = true) @RequestParam(value = "sort", required = true) int sort,
            @ApiParam(name = "remark", value = "备注", required = false) @RequestParam(value = "remark", required = false) String remark
    ){
        logger.info("图书详细分类.修改分类,请求参数:id={}, bookClassifyCode={}, bookClassifyDetailCode={}, bookClassifyDetailName={}, sort={}, remark={}",
                id, bookClassifyCode,bookClassifyDetailCode,bookClassifyDetailName,sort,remark);
        BookClassifyDetail bookClassifyDetail = new BookClassifyDetail();
        bookClassifyDetail.setId(id);
        bookClassifyDetail.setBookClassifyCode(bookClassifyCode);
        bookClassifyDetail.setBookClassifyDetailCode(bookClassifyDetailCode);
        bookClassifyDetail.setBookClassifyDetailName(bookClassifyDetailName);
        bookClassifyDetail.setSort(sort);
        bookClassifyDetail.setRemark(remark);
        bookClassifyDetail.setUpdateDate(DateUtil.formatDateTime(new Date()));
        return this.bookClassifyDetailService.updateByPrimaryKey(bookClassifyDetail);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/book.classify.detail.delete", method = RequestMethod.POST)
    @ApiOperation(value = "图书详细分类.删除分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R deleteByPrimaryKey(
            @ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) int id
    ){
        logger.info("图书详细分类.删除分类,请求参数:id={}",id);
        return this.bookClassifyDetailService.deleteByPrimaryKey(id);
    }

    /**
     * 分类详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/book.classify.detail.get", method = RequestMethod.POST)
    @ApiOperation(value = "图书详细分类.分类详情", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R selectByPrimaryKey(
            @ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) int id
    ){
        logger.info("图书详细分类.分类详情,请求参数:id={}",id);
        return this.bookClassifyDetailService.selectByPrimaryKey(id);
    }

}
