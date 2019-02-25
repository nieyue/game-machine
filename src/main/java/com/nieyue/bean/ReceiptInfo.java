package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货信息
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="收货信息",description="收货信息")
@TableName("receipt_info_tb")
public class ReceiptInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收货信息id
	 */
	@ApiModelProperty(value="收货信息id")
	@TableId("receipt_info_id")
	private Long receiptInfoId;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value="姓名")
	private String name;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value="手机号")
	private String phone;
	/**
	 * 收货地址
	 */
	@ApiModelProperty(value="收货地址")
	private String address;
	/**
	 * 默认为0不是，1是
	 */
	@ApiModelProperty(value="默认为0不是，1是")
	private Integer isDefault;
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
