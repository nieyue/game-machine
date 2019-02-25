package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="财务",description="财务")
@TableName("finance_tb")
public class Finance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 财务id
	 */
	@ApiModelProperty(value="财务id")
	@TableId("finance_id")
	private Long financeId;
	/**
	 * 交易密码
	 */
	@ApiModelProperty(value="交易密码")
	private String password;
	/**
	 * 余额
	 */
	@ApiModelProperty(value="余额")
	private Double money;
	/**
	 * 充值金额
	 */
	@ApiModelProperty(value="充值金额")
	private Double recharge;
	/**
	 * 消费金额
	 */
	@ApiModelProperty(value="消费金额")
	private Double consume;
	/**
	 * 提现金额
	 */
	@ApiModelProperty(value="提现金额")
	private Double withdrawals;
	/**
	 * 自身总收益
	 */
	@ApiModelProperty(value="自身总收益")
	private Double selfProfit;
	/**
	 * 合伙人总收益
	 */
	@ApiModelProperty(value="合伙人总收益")
	private Double partnerProfit;
	/**
	 * 赠送金钱
	 */
	@ApiModelProperty(value="赠送金钱")
	private Double baseProfit;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value="更新时间")
	private Date updateDate;
	/**
	 * 账户id外键
	 */
	@ApiModelProperty(value="账户id外键")
	private Long accountId;

}
