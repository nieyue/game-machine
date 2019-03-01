package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 充值记录
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="充值记录",description="充值记录")
@TableName("recharge_record_tb")
public class RechargeRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 充值记录id
	 */
	@ApiModelProperty(value="充值记录id")
	@TableId("recharge_record_id")
	private Long rechargeRecordId;
	/**
	 * 支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈
	 */
	@ApiModelProperty(value="支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈付")
	private Integer type;
	/**
	 * 充值真钱
	 */
	@ApiModelProperty(value="充值真钱")
	private Double giveMoney;
	/**
	 * 赠送次数
	 */
	@ApiModelProperty(value="赠送次数")
	private Integer giveNumber;
	/**
	 * 默认为1成功，2失败
	 */
	@ApiModelProperty(value="默认为1成功，2失败")
	private Integer status;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间")
	private Date createDate;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value="更新时间")
	private Date updateDate;
	/**
	 * 账户id
	 */
	@ApiModelProperty(value="账户id")
	private Long accountId;

}
