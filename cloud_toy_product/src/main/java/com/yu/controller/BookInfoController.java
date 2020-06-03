package com.yu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yu.entity.BookInfo;
import com.yu.service.BookInfoService;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Api(value = "BookInfoController", description = "图书信息")
@RestController
public class BookInfoController {

    private static final Logger logger = LoggerFactory.getLogger(BookClassifyDetailController.class);

    @Autowired
    private BookInfoService bookInfoService;

    /**
     * 查询图书列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/book.info.selectAll", method = RequestMethod.POST)
    @ApiOperation(value = "图书信息.查询图书列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R selectAll(
            @ApiParam(value = "pageNo", required = true) @RequestParam(value = "pageNo", required = true) int pageNo,
            @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) int pageSize
    ){
        logger.info("图书信息.查询图书列表,请求参数:pageNo={},pageSize={}",pageNo,pageSize);
        PageHelper.startPage(pageNo,pageSize);
        List<BookInfo> list = bookInfoService.selectAll();
        PageInfo<BookInfo> pageInfo = new PageInfo<>(list);
        return R.ok().put("result",pageInfo);
    }

    /**
     * 新增图书
     * @return
     */
    @RequestMapping(value = "/book.info.save", method = RequestMethod.POST)
    @ApiOperation(value = "图书信息.新增图书", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R save(
            @ApiParam(name = "bookClassifyCode", value = "大分类编码", required = true) @RequestParam(value = "bookClassifyCode", required = true) String bookClassifyCode,
            @ApiParam(name = "bookClassifyDetailCode", value = "详细分类编码", required = true) @RequestParam(value = "bookClassifyDetailCode", required = true) String bookClassifyDetailCode,
            @ApiParam(name = "bookName", value = "图书名称", required = true) @RequestParam(value = "bookName", required = true) String bookName,
            @ApiParam(name = "bookAuthor", value = "图书作者", required = true) @RequestParam(value = "bookAuthor", required = true) String bookAuthor,
            @ApiParam(name = "bookNote", value = "简介", required = true) @RequestParam(value = "bookNote", required = true) String bookNote,
            @ApiParam(name = "bookPublishing", value = "出版社", required = true) @RequestParam(value = "bookPublishing", required = true) String bookPublishing,
            @ApiParam(name = "bookBarCode", value = "简介", required = true) @RequestParam(value = "bookBarCode", required = true) String bookBarCode,
            @ApiParam(name = "bookPrice", value = "价格", required = true) @RequestParam(value = "bookPrice", required = true) BigDecimal bookPrice,
            @ApiParam(name = "stockNum", value = "库存数量", required = true) @RequestParam(value = "stockNum", required = true) int stockNum,
            @ApiParam(name = "sort", value = "排序", required = true) @RequestParam(value = "sort", required = true) int sort
    ){
        logger.info("图书信息.新增图书,请求参数:bookClassifyCode={}, bookClassifyDetailCode={}, bookName={}, bookAuthor={}, bookNote={},bookPublishing={}," +
                        "bookBarCode={},bookPrice={},sort={}",
                bookClassifyCode,bookClassifyDetailCode,bookName,bookAuthor,bookNote,bookPublishing,bookBarCode,bookPrice,sort);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookClassifyCode(bookClassifyCode);
        bookInfo.setBookClassifyDetailCode(bookClassifyDetailCode);
        bookInfo.setBookName(bookName);
        bookInfo.setBookAuthor(bookAuthor);
        bookInfo.setBookNote(bookNote);
        bookInfo.setBookPublishing(bookPublishing);
        bookInfo.setBookBarCode(bookBarCode);
        bookInfo.setBookPrice(bookPrice);
        bookInfo.setStockNum(stockNum);
        bookInfo.setSort(sort);
        bookInfo.setState(1);
        bookInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return this.bookInfoService.insert(bookInfo);
    }

    /**
     * 新增图书
     * @return
     */
    @RequestMapping(value = "/book.info.modify", method = RequestMethod.POST)
    @ApiOperation(value = "图书信息.修改图书", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R updateByPrimaryKey(
            @ApiParam(name = "id", value = "主键", required = true) @RequestParam(value = "id", required = true) int id,
            @ApiParam(name = "bookClassifyCode", value = "大分类编码", required = true) @RequestParam(value = "bookClassifyCode", required = true) String bookClassifyCode,
            @ApiParam(name = "bookClassifyDetailCode", value = "详细分类编码", required = true) @RequestParam(value = "bookClassifyDetailCode", required = true) String bookClassifyDetailCode,
            @ApiParam(name = "bookName", value = "图书名称", required = true) @RequestParam(value = "bookName", required = true) String bookName,
            @ApiParam(name = "bookAuthor", value = "图书作者", required = true) @RequestParam(value = "bookAuthor", required = true) String bookAuthor,
            @ApiParam(name = "bookNote", value = "简介", required = true) @RequestParam(value = "bookNote", required = true) String bookNote,
            @ApiParam(name = "bookPublishing", value = "出版社", required = true) @RequestParam(value = "bookPublishing", required = true) String bookPublishing,
            @ApiParam(name = "bookBarCode", value = "简介", required = true) @RequestParam(value = "bookBarCode", required = true) String bookBarCode,
            @ApiParam(name = "bookPrice", value = "价格", required = true) @RequestParam(value = "bookPrice", required = true) BigDecimal bookPrice,
            @ApiParam(name = "sort", value = "排序", required = true) @RequestParam(value = "sort", required = true) int sort
    ){
        logger.info("图书信息.修改图书,请求参数:id={},bookClassifyCode={}, bookClassifyDetailCode={}, bookName={}, bookAuthor={}, bookNote={},bookPublishing={}," +
                        "bookBarCode={},bookPrice={},sort={}",
                id,bookClassifyCode,bookClassifyDetailCode,bookName,bookAuthor,bookNote,bookPublishing,bookBarCode,bookPrice,sort);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(id);
        bookInfo.setBookClassifyCode(bookClassifyCode);
        bookInfo.setBookClassifyDetailCode(bookClassifyDetailCode);
        bookInfo.setBookName(bookName);
        bookInfo.setBookAuthor(bookAuthor);
        bookInfo.setBookNote(bookNote);
        bookInfo.setBookPublishing(bookPublishing);
        bookInfo.setBookBarCode(bookBarCode);
        bookInfo.setBookPrice(bookPrice);
        bookInfo.setSort(sort);
        bookInfo.setUpdateDate(DateUtil.formatDateTime(new Date()));
        return this.bookInfoService.updateByPrimaryKey(bookInfo);
    }

    /**
     * 删除图书
     * @param id
     * @return
     */
    @RequestMapping(value = "/book.info.delete", method = RequestMethod.POST)
    @ApiOperation(value = "图书信息.删除图书", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R deleteByPrimaryKey(
            @ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) int id
    ){
        logger.info("图书信息.删除图书,请求参数:id={}",id);
        return this.bookInfoService.deleteByPrimaryKey(id);
    }

    /**
     * 分类详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/book.info.get", method = RequestMethod.POST)
    @ApiOperation(value = "图书信息.图书详情", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R selectByPrimaryKey(
            @ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) int id
    ){
        logger.info("图书信息.图书详情,请求参数:id={}",id);
        return this.bookInfoService.selectByPrimaryKey(id);
    }

}
