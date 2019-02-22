package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 地址
 * @author yy
 *
 */
@Data
@ApiModel(value="地址",description="地址")
@TableName("address_tb")
public class Address implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 地址id
	 */
	@ApiModelProperty(value="地址id")
	@TableId("address_id")
	private Long addressId;
	/**
	 * 类型：1出发地，2目的地
	 */
	@ApiModelProperty(value="类型：1出发地，2目的地")
	private Integer type;
	/**
	 * 城市
	 */
	@ApiModelProperty(value="城市")
	private String city;
	/**
	 * 地址地址
	 */
	@ApiModelProperty(value="地址地址")
	private String address;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间",example="创建时间")
	private Date createDate;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value="更新时间",example="更新时间")
	private Date updateDate;
}
