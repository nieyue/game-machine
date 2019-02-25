package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡片
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="卡片",description="卡片")
@TableName("card_tb")
public class Card implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 卡片id
	 */
	@ApiModelProperty(value="卡片id")
	@TableId("card_id")
	private Long cardId;
	/**
	 * 面料卡张数
	 */
	@ApiModelProperty(value="面料卡张数")
	private Integer cardNumber1;
	/**
	 * 五金卡张数
	 */
	@ApiModelProperty(value="五金卡张数")
	private Integer cardNumber2;
	/**
	 * 手挽卡张数
	 */
	@ApiModelProperty(value="手挽卡张数")
	private Integer cardNumber3;
	/**
	 * 袋身卡张数
	 */
	@ApiModelProperty(value="袋身卡张数")
	private Integer cardNumber4;
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
	 * 商品id外键
	 */
	@ApiModelProperty(value="商品id外键")
	private Long merId;
	/**
	 * 账户id外键
	 */
	@ApiModelProperty(value="账户id外键")
	private Long accountId;

}
