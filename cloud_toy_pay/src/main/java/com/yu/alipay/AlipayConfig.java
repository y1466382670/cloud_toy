package com.yu.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {
    /**
     * 应用号
     */
    public static String APP_ID = "2016102200737132";//你的沙箱中appId
    /**
     * 商户的私钥
     */
    public static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCWnKV9EucY0oE5RRK5X3woLnKKjtJfvofptfdSCFkuTqg31wBxYIl1NqV+MZtQUxSG9rsFRF/pOyVCeCu544ie0Vf+UuVXTl7R7OzCRodlpZRUb9YofRXQmw24/WCAshyEtS4puE00wqw7UdyaeiGlOxZsOCW7h0SnehDUjiSmqcgDKoW1xWJPQv9793Ng8XCeY/SF5ITOn/mxcIuUeTvdXoU4sdGaBZ6SrV7iiRaLJM4ZzWtqahxdhXmYBDXdLjbfsG1PkfUiKBgMhoYj2Y2QZ4KXdG7tOrZgfiS7Mw+bSGEQl3a2bXYgZ0akJ5LpPboHo1tcAJVWS4fQ16Nd1MPXAgMBAAECggEBAIp5JZIUMhNZX2QnRKrYxo8u/TK1U2iiBdXz7psmf9Aak4EblRWom1kAUDHxLAozCHl6GcMi9Ue7kGtHj3ROZYsXkF6NGr0lmfpKaffI3q2vCeyEEpORReBQ3tIJa5OHrr640eObxqhOoAm+HySMS6jd5aTEDrUXDiEpEpZKxhnQL61I8R0U4LeqiyDoBOHwL23M6yR8flmmdn/BCoa7zrytf3LWb9Nmcs912iZiTOn8eZGDyEcOOWK8J5AqT5S2QCzq8OnfwdPU092LKcT1seP4kX8YwVlHErLotP4cZrKOeJ6L1UnXBfmu16ohCL+4jn7thI2LU47Qh8dW/3y3CTECgYEA0+eSOSXmYHP2no1zSWhAC+rUGqLQwg/5wPhx/iIEJaIwsguiGpJpSA65Hem70O9pvMcJkuYC7pVOpKNEYT8kr/56WZ6SAEG3YEeMuMiMFybuc7uNGuRFV2u7fIOjufm1AM4q5aSCbar5Lb8p/Tni0Hb39BeSVBCFHnnl0X+8cS8CgYEAtfPvjioo0KFC4BhOElC8keTZO71B8rPSBRWH8WEuXMtVViEnLEeBHtZ57wg9R+9yrFnTIP/J/tFwm4FG4u0yskY5xzaEnuQOrZ9HGrO7LvxcxNDOILuehnJuuwA7moxMRUFWuavNKoVkkxsbPHyILi2YnS6RXopnCCuTZBOBndkCgYBcei5FRHYC1/jj/wU2Hwn8aXJBYZbLal9ryMEu0DeRGPQ5b3xCLm1F5i8Bj/TIXQN/QDm8YycaagyVKy93ZTAkomFQ5TQAaq2VlgQizFre672awln1R3O/kCOVVnnIeuJ0RIVSDPZi1//A6DFx8qY6pgDWBMd2anM99qLGSAG8PwKBgDo3j5l0IdUHE+exvyEbFgq0J1F276fhIzyfM/zmdDgakvZY6zi+tDCSvTbe8wSWN6Gvp97yGMZgauBGj9DBxivGTmxQJETWgV/KC60efrC87E3I5XsRrNoluQo6O9fqNekey8nij2WggpqS3LcQvV3RFviL0tVmAStbDfDCUQGZAoGABm0NWrsyGWGs0mxTCoGP+9H3SD9gNl1XgT33H7uKwij47hH2eIElTef17Xfqy9qsWZNQtyBAhmImx65Th3q9STJitMb3ZQxFbcx08/swb/HN6G3mkNGRyjCNIggVaud5pqx8lozFEjbdCilGD4wWIgPdoXNVjR0YjfWOb8p4qoU=";
    /**
     * 编码
     */
    public static String CHARSET = "UTF-8";
    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg3dtlCdo2XfSSN3EXwQ8QZkSLSXCaSDwK4w8th+DBBgcR3cbQJtR2QYtduHMIvvJAVW7qCG1aCMyicYndY9z1opxgioBYnhwUUdTb4OtUKOKOD1310npZBKEPdc1inwa+m2v/2VPBWy/oTlQb3nbjzIFgGRX8q+a15HnNmuZfMTYhkXlEFioifiyLqkj5QO078Zt8RJS0t0TsP0vjgQkLbwvXxKg9IX5aC55HQYfu/l7akFz8UoxwaHetG2xTcXMQDr8eTdbgpWVerPzMaspyjhQrvbP1G5CR2PrkAJJrJYfQKYg46jA+nqyw2Jyf0tH+49/RLzRaadK/NtT/lTHIwIDAQAB";
    /**
     * 支付宝网关地址
     */
    private static String GATEWAY = "https://openapi.alipaydev.com/gateway.do";
    /**
     * 成功付款回调
     */
    public static String PAY_NOTIFY = "你的回调地址";
    /**
     * 支付成功回调
     */
    public static String REFUND_NOTIFY = "你的回调地址";
    /**
     * 前台通知地址
     */
    public static String RETURN_URL = "你的回调地址";
    /**
     * 参数类型
     */
    public static String PARAM_TYPE = "json";
    /**
     * 成功标识
     */
    public static final String SUCCESS_REQUEST = "TRADE_SUCCESS";
    /**
     * 交易关闭回调(当该笔订单全部退款完毕,则交易关闭)
     */
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    /**
     * 收款方账号
     */
    public static final String SELLER_ID = "15033675513";
    /**
     * 支付宝请求客户端入口
     */
    private volatile static AlipayClient alipayClient = null;

    /**
     * 不可实例化
     */
    public AlipayConfig(){};

    /**
     * 双重锁单例
     * @return 支付宝请求客户端实例
     */
    public static AlipayClient getInstance(){
        if (alipayClient == null){
            synchronized (AlipayConfig.class){
                if (alipayClient == null){
                    alipayClient = new DefaultAlipayClient(GATEWAY,APP_ID,APP_PRIVATE_KEY,PARAM_TYPE,CHARSET,ALIPAY_PUBLIC_KEY,"RSA2");
                }
            }
        }
        return alipayClient;
    }

}
