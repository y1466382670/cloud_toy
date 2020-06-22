package com.yu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yu.entity.BookClassify;
import com.yu.service.BookClassifyService;
import com.yu.util.DateUtil;
import com.yu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api(value = "BookClassifyController", description = "图书大分类")
public class BookClassifyController {

    private static final Logger logger = LoggerFactory.getLogger(BookClassifyController.class);

    @Autowired
    private BookClassifyService bookClassifyService;

    /**
     * 查询图书分类列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/book.classify.selectAll", method = RequestMethod.POST)
    @ApiOperation(value = "图书大分类.查询分类列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R selectAll(
            @ApiParam(value = "pageNo", required = true) @RequestParam(value = "pageNo", required = true) int pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) int pageSize,
            @ApiParam(name = "bookClassifyName", value = "分类名称", required = false) @RequestParam(value = "bookClassifyName", required = false) String bookClassifyName,
            @ApiParam(name = "bookClassifyCode", value = "分类编码", required = false) @RequestParam(value = "bookClassifyCode", required = false) String bookClassifyCode
    ){
        logger.info("图书大分类.查询分类列表,请求参数:pageNo={},pageSize={}",pageNo,pageSize);
        PageHelper.startPage(pageNo,pageSize);
        List<BookClassify> list = bookClassifyService.selectAll(bookClassifyName,bookClassifyCode);
        PageInfo<BookClassify> pageInfo = new PageInfo<>(list);
        return R.ok().put("result",pageInfo);
    }

    /**
     * 新增分类
     * @return
     */
    @RequestMapping(value = "/book.classify.save", method = RequestMethod.POST)
    @ApiOperation(value = "图书大分类.新增分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R save(
            @ApiParam(name = "bookClassifyName", value = "分类名称", required = true) @RequestParam(value = "bookClassifyName", required = true) String bookClassifyName,
            @ApiParam(name = "bookClassifyCode", value = "分类编码", required = true) @RequestParam(value = "bookClassifyCode", required = true) String bookClassifyCode,
            @ApiParam(name = "sort", value = "排序", required = true) @RequestParam(value = "sort", required = true) int sort,
            @ApiParam(name = "remark", value = "备注", required = false) @RequestParam(value = "remark", required = false) String remark
    ){
        logger.info("图书大分类.新增分类,请求参数:bookClassifyName={}, bookClassifyCode={}, sort={}, remark={}",bookClassifyName,bookClassifyCode,sort,remark);
        BookClassify bookClassify = new BookClassify();
        bookClassify.setBookClassifyName(bookClassifyName);
        bookClassify.setBookClassifyCode(bookClassifyCode);
        bookClassify.setSort(sort);
        bookClassify.setRemark(remark);
        bookClassify.setCreateDate(DateUtil.formatDateTime(new Date()));
        return this.bookClassifyService.insert(bookClassify);
    }

    /**
     * 修改分类
     * @return
     */
    @RequestMapping(value = "/book.classify.modify", method = RequestMethod.POST)
    @ApiOperation(value = "图书大分类.修改分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R updateByPrimaryKey(
            @ApiParam(name = "id", value = "主键", required = true) @RequestParam(value = "id", required = true) int id,
            @ApiParam(name = "bookClassifyName", value = "分类名称", required = true) @RequestParam(value = "bookClassifyName", required = true) String bookClassifyName,
            @ApiParam(name = "bookClassifyCode", value = "分类编码", required = true) @RequestParam(value = "bookClassifyCode", required = true) String bookClassifyCode,
            @ApiParam(name = "sort", value = "排序", required = true) @RequestParam(value = "sort", required = true) int sort,
            @ApiParam(name = "remark", value = "备注", required = false) @RequestParam(value = "remark", required = false) String remark
    ){
        logger.info("图书大分类.修改分类,请求参数:id={}, bookClassifyName={}, bookClassifyCode={}, sort={}, remark={}",id,bookClassifyName,bookClassifyCode,sort,remark);
        BookClassify bookClassify = new BookClassify();
        bookClassify.setId(id);
        bookClassify.setBookClassifyName(bookClassifyName);
        bookClassify.setBookClassifyCode(bookClassifyCode);
        bookClassify.setSort(sort);
        bookClassify.setRemark(remark);
        bookClassify.setUpdateDate(DateUtil.formatDateTime(new Date()));
        return this.bookClassifyService.updateByPrimaryKey(bookClassify);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/book.classify.delete", method = RequestMethod.POST)
    @ApiOperation(value = "图书大分类.删除分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R deleteByPrimaryKey(
            @ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) int id
    ){
        logger.info("图书大分类.删除分类,请求参数:id={}",id);
        return this.bookClassifyService.deleteByPrimaryKey(id);
    }

    /**
     * 分类详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/book.classify.get", method = RequestMethod.POST)
    @ApiOperation(value = "图书大分类.分类详情", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R selectByPrimaryKey(
            @ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) int id
    ){
        logger.info("图书大分类.分类详情,请求参数:id={}",id);
        return this.bookClassifyService.selectByPrimaryKey(id);
    }

}
