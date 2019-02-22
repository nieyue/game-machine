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
 * 行程
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="行程",description="行程")
@TableName("trip_tb")
public class Trip implements Serializable {

	/**
     *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 行程id
	 */
	@ApiModelProperty(value="行程id")
	@TableId("trip_id")
	private Long tripId;
	/**
	 * 类型，1车主列表，2乘客列表
	 */
	@ApiModelProperty(value="类型，1车主列表，2乘客列表")
	private Integer type;
	/**
	 * 出发地
	 */
	@ApiModelProperty(value="出发地")
	private String startAddress;
	/**
	 * 目的地
	 */
	@ApiModelProperty(value="目的地")
	private String endAddress;
	/**
	 * 途径地
	 */
	@ApiModelProperty(value="途径地")
	private String middleAddress;
	/**
	 * 是否上门接送，1是，2否
	 */
	@ApiModelProperty(value="是否上门接送，1是，2否")
	private Integer isDoor;
	/**
	 * 可带人数
	 */
	@ApiModelProperty(value="可带人数")
	private Integer personNumber;
	/**
	 * 金额/人
	 */
	@ApiModelProperty(value="金额/人")
	private Double unitPrice;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间")
	private Date createDate;
	/**
	 * 出发时间
	 */
	@ApiModelProperty(value="出发时间")
	private Date startDate;
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
	/**
	 * 账户
	 */
	@ApiModelProperty(value="账户")
	@TableField(exist = false)
	private Account account;

}