package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品订单详情
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="商品订单详情",description="商品订单详情")
@TableName("mer_order_detail_tb")
public class MerOrderDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品订单详情id
	 */
	@ApiModelProperty(value="商品订单详情id")
	@TableId("mer_order_detail_id")
	private Long merOrderDetailId;
	/**
	 * 商品名称
	 */
	@ApiModelProperty(value="商品名称")
	private String merName;
	/**
	 * 封面
	 */
	@ApiModelProperty(value="封面")
	private String imgAddress;
	/**
	 * 收货地址姓名
	 */
	@ApiModelProperty(value="收货地址姓名")
	private String name;
	/**
	 * 收货地址手机号
	 */
	@ApiModelProperty(value="收货地址手机号")
	private String phone;
	/**
	 * 收货地址
	 */
	@ApiModelProperty(value="收货地址")
	private String address;
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
	 * 商品订单ID
	 */
	@ApiModelProperty(value="商品订单ID")
	private Long merOrderId;

}
