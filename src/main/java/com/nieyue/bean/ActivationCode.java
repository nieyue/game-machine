package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 激活码
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="激活码",description="激活码")
@TableName("activation_code_tb")
public class ActivationCode implements Serializable {

	/**
     *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 激活码id
	 */
	@ApiModelProperty(value="激活码id")
	@TableId("activation_code_id")
	private Long activationCodeId;
	/**
	 * 激活码
	 */
	@ApiModelProperty(value="激活码")
	private String code;
	/**
	 * 是否使用，1未使用，2已使用
	 */
	@ApiModelProperty(value="是否使用，1未使用，2已使用")
	private Integer isUsered;
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
	 * 账户id外键
	 */
	@ApiModelProperty(value="账户id外键")
	private Long accountId;

}