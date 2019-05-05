package com.nieyue.controller;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Payment;
import com.nieyue.business.BYPayBusiness;
import com.nieyue.business.OrderBusiness;
import com.nieyue.business.WeiXinBusiness;
import com.nieyue.comments.IPCountUtil;
import com.nieyue.service.PaymentService;
import com.nieyue.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * 支付控制类
 * @author yy
 *
 */
@Api(tags={"payment"},value="支付",description="支付管理")
@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController<Payment,Long>{
	@Value("${myPugin.projectDomainUrl}")
	String projectDomainUrl;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private BYPayBusiness bYPayBusiness;
	@Autowired
	private WeiXinBusiness weiXinBusiness;

	
	/**
	 * 支付分页浏览
	 * @param orderName 支付排序数据库字段
	 * @param orderWay 支付排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "支付列表", notes = "支付分页浏览")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="orderNumber",value="订单号",dataType="string", paramType = "query"),
	  @ApiImplicitParam(name="type",value="支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="businessType",value="业务类型，1充值，2提现，3退款",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="businessId",value="业务ID",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="updateDate",value="更新时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="status",value="状态，1已下单-未支付，2支付成功，3支付失败,4异常",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	  @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="payment_id"),
	  @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody
	StateResultList<List<Payment>> browsePagingPayment(
			@RequestParam(value="orderNumber",required=false)String orderNumber,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="businessType",required=false)Integer businessType,
			@RequestParam(value="businessId",required=false)Long businessId,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="paymentId") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {

		Wrapper<Payment> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("order_number", orderNumber);
		map.put("type", type);
		map.put("business_type", businessType);
		map.put("business_id", businessId);
		map.put("account_id", accountId);
		map.put("create_date", createDate);
		map.put("update_date", updateDate);
		map.put("status", status);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Payment>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
		return rl;
	}
	/**
	 * 支付修改
	 * @return
	 */
	@ApiOperation(value = "支付修改", notes = "支付修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody
	StateResultList<List<Payment>> updatePayment(@ModelAttribute Payment payment, HttpSession session)  {
		StateResultList<List<Payment>> u= super.update(payment);
		return u;
	}
	@ApiOperation(value = "宝盈支付下单", notes = "宝盈支付下单")
	@RequestMapping("/bYPay")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query",required = true),
			@ApiImplicitParam(name="rechargeTermId",value="充值项Id",dataType="long", paramType = "query",required = true),
			@ApiImplicitParam(name="money",value="金额",dataType="double", paramType = "query",required = true),
	})
	//@ResponseBody
	public String weXinUnifiedOrder(
			@RequestParam(value="accountId")Long accountId,
			@RequestParam(value="rechargeTermId")Long rechargeTermId,
			@RequestParam(value="money")Double money,
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		Payment payment=new Payment();
		payment.setSubject("欢乐抓娃娃");
		payment.setBody("欢乐抓娃娃");
		payment.setOrderNumber(SnowflakeIdWorker.getId().toString());
		payment.setMoney(money);
		payment.setStatus(1);//已下单
		payment.setType(6);//支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈
		payment.setBusinessType(1);//业务类型，1充值，2提现，3退款
		payment.setNotifyUrl(projectDomainUrl+"/payment/bYPayNotifyUrl");//支付回调
		payment.setBusinessNotifyUrl(projectDomainUrl+"/home/user.html");//站内回调
		payment.setCreateDate(new Date());
		payment.setUpdateDate(new Date());
		payment.setAccountId(accountId);
		payment.setBusinessId(rechargeTermId);//充值项Id
		String result = bYPayBusiness.pay(payment, IPCountUtil.getIpAddr(request));
		return result;

	}
	/**
	 * 宝盈支付回调
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "宝盈支付回调", notes = "宝盈支付回调")
	@RequestMapping(value = "/bYPayNotifyUrl", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody
    String BYPayNotifyUrl(
            HttpServletRequest request, HttpSession session) {
		String pm = bYPayBusiness.bYPayNotify(request);
		return pm;
	}
	@ApiOperation(value = "微信支付统一下单", notes = "微信支付统一下单")
	@RequestMapping("/weiXinUnifiedOrder")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query",required = true),
			@ApiImplicitParam(name="rechargeTermId",value="充值项Id",dataType="long", paramType = "query",required = true),
			@ApiImplicitParam(name="money",value="金额",dataType="double", paramType = "query",required = true),
			@ApiImplicitParam(name="type",value="微信支付类型，1公众号支付，2扫码支付，3app支付,4h5支付，5小程序支付",dataType="string", paramType = "query"),
	})
	//@ResponseBody
	public String weXinUnifiedOrder(
			@RequestParam(value="type",required = false,defaultValue = "1")Integer type,
			@RequestParam(value="accountId")Long accountId,
			@RequestParam(value="rechargeTermId")Long rechargeTermId,
			@RequestParam(value="money")Double money,
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		Payment payment=new Payment();
		payment.setSubject("欢乐抓娃娃");
		payment.setBody("欢乐抓娃娃");
		payment.setOrderNumber(SnowflakeIdWorker.getId().toString());
		payment.setMoney(money);
		payment.setStatus(1);//已下单
		payment.setType(2);//支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈
		payment.setBusinessType(1);//业务类型，1充值，2提现，3退款
		payment.setNotifyUrl(projectDomainUrl+"/payment/weiXinPayNotifyUrl");//支付回调
		payment.setBusinessNotifyUrl(projectDomainUrl+"/home/user.html");//站内回调
		payment.setCreateDate(new Date());
		payment.setUpdateDate(new Date());
		payment.setAccountId(accountId);
		payment.setBusinessId(rechargeTermId);
		String result = weiXinBusiness.getPayment(payment, type, request, response);
		return result;

	}
	/**
	 * 微信支付回调
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "微信支付回调", notes = "微信支付回调")
	@RequestMapping(value = "/weiXinPayNotifyUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String weiXinPayNotifyUrl(
			HttpServletRequest request, HttpSession session) {
		String pm = weiXinBusiness.getNotifyUrl(request);
		return pm;
	}
	/**
	 * 支付增加
	 * @return 
	 */
	@ApiOperation(value = "支付增加", notes = "支付增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody
    StateResultList<List<Payment>> addPayment(@RequestBody Payment payment, HttpSession session) {
		StateResultList<List<Payment>> a = super.add(payment);
		return a;
	}
	/**
	 * 支付删除
	 * @return
	 */
	@ApiOperation(value = "支付删除", notes = "支付删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="paymentId",value="支付ID",dataType="long", paramType = "query",required=true)
		 	  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody
    StateResultList<List<Payment>>  delPayment(@RequestParam("paymentId") Long paymentId, HttpSession session)  {
		StateResultList<List<Payment>> d = super.delete(paymentId);
		return d;
	}
	/**
	 * 支付浏览数量
	 * @return
	 */
	@ApiOperation(value = "支付浏览数量", notes = "支付浏览数量")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="orderNumber",value="订单号",dataType="string", paramType = "query"),
	  @ApiImplicitParam(name="type",value="支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="businessType",value="业务类型，1充值，2提现，3退款",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="businessId",value="业务ID",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="updateDate",value="更新时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="status",value="状态，1已下单-未支付，2支付成功，3支付失败,4异常",dataType="int", paramType = "query")
	  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody
    StateResultList<List<Integer>> countAll(
			@RequestParam(value="orderNumber",required=false)String orderNumber,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="businessType",required=false)Integer businessType,
			@RequestParam(value="businessId",required=false)Long businessId,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="status",required=false)Integer status,
			HttpSession session)  {
		Wrapper<Payment> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("order_number", orderNumber);
		map.put("type", type);
		map.put("business_type", businessType);
		map.put("business_id", businessId);
		map.put("account_id", accountId);
		map.put("create_date", createDate);
		map.put("update_date", updateDate);
		map.put("status", status);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 支付单个加载
	 * @return
	 */
	@ApiOperation(value = "支付单个加载", notes = "支付单个加载")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="paymentId",value="支付ID",dataType="long", paramType="query",required=true)
	 	  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET, RequestMethod.POST})
	public  StateResultList<List<Payment>> loadPayment(@RequestParam("paymentId") Long paymentId, HttpSession session)  {
		StateResultList<List<Payment>> l = super.load(paymentId);
		return l;
	}
	
}
