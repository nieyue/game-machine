package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 联系
 * @author yy
 *
 */
@Data
@ApiModel(value="联系",description="联系")
@TableName("contact_tb")
public class Contact implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 联系id
	 */
	@ApiModelProperty(value="联系id")
	@TableId("contact_id")
	private Long contactId;
	/**
	 * 联系地址
	 */
	@ApiModelProperty(value="联系地址")
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
