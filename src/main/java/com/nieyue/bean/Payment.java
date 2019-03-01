package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付
 * @author yy
 *
 */
@Data
@ApiModel(value="支付",description="支付")
@TableName(value="payment_tb")
public class Payment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 支付id
	 */
	@ApiModelProperty(value="支付id",example="支付id")
	@TableId(value="payment_id")
	private Long paymentId;
	/**
	 * 主题
	 */
	@ApiModelProperty(value="主题",example="主题")
	private String subject;
	/**
	 * 内容
	 */
	@ApiModelProperty(value="内容",example="内容")
	private String body;
	/**
	 * 异步通知
	 */
	@ApiModelProperty(value="异步通知",example="异步通知")
	private String notifyUrl;
	/**
	 * 支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈
	 */
	@ApiModelProperty(value="支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈",example="支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈")
	private Integer type;
	/**
	 * 平台订单号
	 */
	@ApiModelProperty(value="平台订单号",example="平台订单号")
	private String  orderNumber;
	/**
	 * 金额
	 */
	@ApiModelProperty(value="金额",example="金额")
	private Double money;
	/**
	 *状态，1已下单，2成功，3失败,4异常
	 */
	@ApiModelProperty(value="状态，1已下单，2成功，3失败,4异常",example="状态，1已下单，2成功，3失败,4异常")
	private Integer status;
	/**
	 *业务类型，1充值，2提现，3退款
	 */
	@ApiModelProperty(value="业务类型，1充值，2提现，3退款",example="业务类型，1充值，2提现，3退款")
	private Integer businessType;
	/**
	 *业务Id
	 */
	@ApiModelProperty(value="业务Id",example="业务Id")
	private Long businessId;
	/**
	 *业务回调
	 */
	@ApiModelProperty(value="业务回调",example="业务回调")
	private String businessNotifyUrl;
	/**
	 *账户id,外键
	 */
	@ApiModelProperty(value="账户id,外键",example="账户id,外键")
	private Long accountId;
	/**
	 * 支付创建时间
	 */
	@ApiModelProperty(value="支付创建时间",example="支付创建时间")
	private Date createDate;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value="更新时间",example="更新时间")
	private Date updateDate;


}
