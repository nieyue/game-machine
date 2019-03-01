package com.nieyue.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.*;
import com.nieyue.bean.Number;
import com.nieyue.service.*;
import com.nieyue.util.Arith;
import com.nieyue.util.HttpClientUtil;
import com.nieyue.util.MyDESutil;
import com.nieyue.util.MyDom4jUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 宝盈支付
 */
@Configuration
public class BYPayBusiness {
    @Autowired
    PaymentService paymentService;
    @Autowired
    FinanceService financeService;
    @Autowired
    RechargeTermService rechargeTermService;
    @Autowired
    NumberService numberService;
    @Autowired
    RechargeRecordService rechargeRecordService;
    //商户id
    String mchid="1035";
    //秘钥
    String secret="ac9400830ef2d30851e1e0dcae68424b";
    //请求url
    String postUrl="http://pay.bypay1688.com/pay/Preorder/alipay";
    /**
     * 支付
     * @param payment 支付
     * @param ip ip地址
     * @return url
     */
    public String pay(
            Payment payment,
            String ip
    ){
       /* JSONObject jsonObject=new JSONObject();
        jsonObject.put("cpid",mchid);
        jsonObject.put("amount",payment.getMoney());
        jsonObject.put("product","huanlezhuabaobao");
        jsonObject.put("orderid",payment.getOrderNumber());
        jsonObject.put("describe",payment.getOrderNumber());
        jsonObject.put("synurl",payment.getNotifyUrl());
        jsonObject.put("jumpurl",payment.getBusinessNotifyUrl());
        jsonObject.put("sign",getSign(jsonObject));
        jsonObject.put("ip",ip);*/
        Map<String,String> map=new HashMap<>();
        map.put("cpid",mchid);
        map.put("amount",payment.getMoney().toString());
        map.put("product","huanlezhuabaobao");
        map.put("orderid",payment.getOrderNumber());
        map.put("describe",payment.getOrderNumber());
        map.put("synurl",payment.getNotifyUrl());
        map.put("jumpurl",payment.getBusinessNotifyUrl());
        map.put("sign",getSign(map));
        map.put("ip",ip);
        String result = HttpClientUtil.doPostForm(postUrl, map);
        JSONObject jo = JSONObject.fromObject(result);
        if(String.valueOf(jo.get("status")).equals("0")){
            boolean b = paymentService.add(payment);
            if(b){
                return jo.get("payurl").toString();
            }
        }
        return null;
    }

    /**
     * 支付签名
     * @return
     */
    public String getSign(Map<String,String> map){
        StringBuilder sb=new StringBuilder();
        sb.append(map.get("amount")+"|");
        sb.append(map.get("cpid")+"|");
        sb.append(map.get("describe")+"|");
        sb.append(map.get("orderid")+"|");
        sb.append(map.get("product")+"|");
        sb.append(map.get("synurl")+"|");
        sb.append(secret);
        String sign = MyDESutil.getOriginMD5(sb.toString());
        return sign;
    }
/*
    public String getSign(JSONObject jsonObject){
        StringBuilder sb=new StringBuilder();
        sb.append(jsonObject.get("amount")+"|");
        sb.append(jsonObject.get("cpid")+"|");
        sb.append(jsonObject.get("describe")+"|");
        sb.append(jsonObject.get("orderid")+"|");
        sb.append(jsonObject.get("product")+"|");
        sb.append(jsonObject.get("synurl")+"|");
        sb.append(secret);
        String sign = MyDESutil.getOriginMD5(sb.toString());
        return sign;
    }
*/
    /**
     * 回调通知
     * @return
     */
    public String bYPayNotify(HttpServletRequest request){
        Payment payment= null;
        try {
            //获取支付宝POST过来反馈信息
           /* Map<String,String> params = new HashMap<String,String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }*/
            BufferedReader br = request.getReader();
            String str, wholeStr = "";
            while((str = br.readLine()) != null){
                wholeStr += str;
            }
            JSONObject params = JSONObject.fromObject(wholeStr);

            String orderId = String.valueOf( params.get("orderId"));
            Double amount = Double.valueOf(String.valueOf( params.get("amount"))) ;
            String cpid = String.valueOf(  params.get("cpid"));
            String pay_time = String.valueOf( params.get("pay_time"));
            String pay_sign = String.valueOf( params.get("pay_sign"));
            String pay_msg = String.valueOf( params.get("pay_msg"));
            //验证签名
            StringBuilder sb=new StringBuilder();
            sb.append(amount.toString()+"|");
            sb.append(cpid+"|");
            sb.append(orderId+"|");
            sb.append(pay_time+"|");
            sb.append(secret);
            String sign = MyDESutil.getOriginMD5(sb.toString());
            if(!pay_sign.equals(sign)){
                return "FAIL   sign error";
            }
            if(!pay_msg.equals("SUCCESS")){
                return "FAIL   payment status wrong";
            }
            if(pay_sign.equals(sign)&&pay_msg.equals("SUCCESS")){
                Date payTime = new Date(Long.valueOf(pay_time)*1000);
                Wrapper<Payment> wrapper=new EntityWrapper<>();
                Map<String,Object> map=new HashMap<>();
                map.put("order_number", orderId);
                wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
                List<Payment> paymentlist = paymentService.simplelist(wrapper);
                if(paymentlist.size()==1){
                    payment=paymentlist.get(0);
                    //已经处理过了
                    if(payment.getStatus()!=1){
                        return "SUCCESS";
                    }
                    //支付成功
                    payment.setStatus(2);//成功
                    paymentService.update(payment);
                    //业务处理
                    //1金额增加
                    Wrapper<Finance> wrapper2=new EntityWrapper<>();
                    Map<String,Object> map2=new HashMap<>();
                    map2.put("account_id", payment.getAccountId());
                    wrapper2.allEq(MyDom4jUtil.getNoNullMap(map2));
                    List<Finance> financeList = financeService.simplelist(wrapper2);
                    if(financeList.size()==1){
                        Finance finance = financeList.get(0);
                        finance.setUpdateDate(new Date());
                        finance.setRecharge(Arith.add(finance.getRecharge(),amount));
                        financeService.update(finance);
                    }
                    //2充值记录
                    RechargeRecord rechargeRecord = new RechargeRecord();
                    rechargeRecord.setCreateDate(new Date());
                    rechargeRecord.setUpdateDate(new Date());
                    rechargeRecord.setType(6);//支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈
                    rechargeRecord.setGiveMoney(amount);
                    //获取充值项
                    RechargeTerm rechargeTerm = rechargeTermService.load(payment.getBusinessId());
                    if(rechargeTerm==null){
                        payment.setStatus(3);//失败
                        paymentService.update(payment);
                        return "FAIL";
                    }
                    rechargeRecord.setGiveNumber(rechargeTerm.getGiveNumber());
                    rechargeRecord.setStatus(1);//1成功，2失败
                    rechargeRecord.setAccountId(payment.getAccountId());
                    rechargeRecordService.add(rechargeRecord);
                    //3数量增加
                    Wrapper<Number> wrapper3=new EntityWrapper<>();
                    Map<String,Object> map3=new HashMap<>();
                    map3.put("account_id", payment.getAccountId());
                    wrapper2.allEq(MyDom4jUtil.getNoNullMap(map3));
                    List<Number> numberList = numberService.simplelist(wrapper3);
                    if(numberList.size()==1){
                        Number number = numberList.get(0);
                        number.setBuyNumber(number.getBuyNumber()+rechargeRecord.getGiveNumber());
                        number.setSurplusNumber(number.getSurplusNumber()+rechargeRecord.getGiveNumber());
                        numberService.update(number);
                    }
                    return "SUCCESS";
                }else{
                    payment.setStatus(3);//失败
                    paymentService.update(payment);
                }

            }
        } catch (Exception e) {
            if(payment==null){
                return "FAIL";
            }
            payment.setStatus(4);//异常
            paymentService.update(payment);
        }
        return "FAIL";

    }
    }

