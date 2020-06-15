package com.yu.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.yu.alipay.AlipayConfig;
import com.yu.util.DateUtil;
import com.yu.util.R;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
public class ALiPayController {

    private final static Logger logger = LoggerFactory.getLogger(ALiPayController.class);

    /**
     * alipay.trade.page.pay(统一收单下单并支付页面接口)
     * 文档地址：https://opendocs.alipay.com/apis/api_1/alipay.trade.page.pay/
     */
    @RequestMapping(value = "/aliPagePay", method = RequestMethod.POST)
    @ApiOperation(value = "电脑网站支付", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R aliPagePay(){
        String outtradeno = DateUtil.getDateId();
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.page.pay
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        //订单描述(非必填)
        model.setBody("我是测试数据");
        //商品的标题/交易标题/订单标题/订单关键字等(必填)
        model.setSubject("网站支付测试Java");
        //商户网站唯一订单号(必填)
        model.setOutTradeNo(outtradeno);
        //绝对超时时间(非必填)
        model.setTimeoutExpress("30m");
        //订单总金额(必填)
        model.setTotalAmount("0.01");
        //销售产品码，与支付宝签约的产品码名称(必填) 目前仅支持FAST_INSTANT_TRADE_PAY
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);
        //回调地址
        request.setNotifyUrl("http://127.0.0.1:9000/aliAppPayNotify");
        AlipayTradePagePayResponse response = null;
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            response = AlipayConfig.getInstance().pageExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.ok().put("result",response);
    }

    /**
     * alipay.trade.app.pay(APP支付接口2.0)
     * 文档地址：https://opendocs.alipay.com/apis/api_1/alipay.trade.app.pay
     */
    @RequestMapping(value = "/aliAppPay", method = RequestMethod.POST)
    @ApiOperation(value = "APP支付接口2.0", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R aliAppPay(){
        String outtradeno = DateUtil.getDateId();
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //商品描述(非必填)
        model.setBody("我是测试数据");
        //商品的标题/交易标题/订单标题/订单关键字等(必填)
        model.setSubject("App支付测试Java");
        //商户网站唯一订单号(必填)
        model.setOutTradeNo(outtradeno);
        //绝对超时时间(非必填)
        model.setTimeoutExpress("30m");
        //订单总金额(必填)
        model.setTotalAmount("0.01");
        //销售产品码，商家和支付宝签约的产品码(非必填)
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        //回调地址
        request.setNotifyUrl("http://127.0.0.1:9000/aliAppPayNotify");

        AlipayTradeAppPayResponse response = null;
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            response = AlipayConfig.getInstance().sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.ok().put("result",response);
    }

    /**
     * alipay.trade.query(统一收单线下交易查询)
     * 文档地址：https://opendocs.alipay.com/apis/api_1/alipay.trade.query
     * @param outTradeNo 订单支付时传入的商户订单号
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/alipayTradeQuery", method = RequestMethod.POST)
    @ApiOperation(value = "统一收单线下交易查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void alipayTradeQuery(
            @RequestParam(value = "outTradeNo") String outTradeNo
    ) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        AlipayTradeQueryResponse response = AlipayConfig.getInstance().execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            System.out.println(response.toString());
        } else {
            System.out.println("调用失败");
        }
    }

    @RequestMapping(value = "/aliAppPayNotify", method = RequestMethod.POST)
    @ApiOperation(value = "APP支付回调地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void aliAppPayNotify(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
        logger.info("支付宝成功回调了");
        //将异步通知中收到的所有参数都存放到map中  通过request.getParameterMap()获取
        Map<String, String> paramsMap = new HashMap<>();
        Map requestParams = request.getParameterMap();

        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            paramsMap.put(name, valueStr);
        }
        //调用SDK验证签名,记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, AlipayConfig.ALIPAY_PUBLIC_KEY, "utf-8", "RSA2");
        if (signVerified) {
            String orderno = paramsMap.get("out_trade_no");
            logger.info("支付宝支付成功:"+orderno);
        }else {
            logger.info("支付宝成功回调验签失败");
        }
    }

}
