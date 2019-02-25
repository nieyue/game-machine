package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="商品",description="商品")
@TableName("mer_tb")
public class Mer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品id
	 */
	@ApiModelProperty(value="商品id")
	@TableId("mer_id")
	private Long merId;
	/**
	 * 名称
	 */
	@ApiModelProperty(value="名称")
	private String name;
	/**
	 * 封面
	 */
	@ApiModelProperty(value="封面")
	private String imgAddress;
	/**
	 * 状态,默认1上架,2下架
	 */
	@ApiModelProperty(value="状态,默认1上架,2下架")
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

}
