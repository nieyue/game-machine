package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品订单
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="商品订单",description="商品订单")
@TableName("mer_order_tb")
public class MerOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品订单id
	 */
	@ApiModelProperty(value="商品订单id")
	@TableId("mer_order_id")
	private Long merOrderId;
	/**
	 * 订单号
	 */
	@ApiModelProperty(value="订单号")
	private String orderNumber;
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
	 * 订单状态，1待发货，1已发货
	 */
	@ApiModelProperty(value="订单状态，1待发货，2已发货")
	private Integer status;
	/**
	 * 账户id
	 */
	@ApiModelProperty(value="账户id")
	private Long accountId;
	/**
	 * 订单详情
	 */
	@ApiModelProperty(value="订单详情")
	@TableField(exist = false)
	private List<MerOrderDetail> merOrderDetailList;

}
