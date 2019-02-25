package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 次数
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="次数",description="次数")
@TableName("number_tb")
public class Number implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 次数id
	 */
	@ApiModelProperty(value="次数id")
	@TableId("number_id")
	private Long numberId;
	/**
	 * 昵称
	 */
	@ApiModelProperty(value="昵称")
	private String nickname;
	/**
	 * 图像
	 */
	@ApiModelProperty(value="图像")
	private String icon;
	/**
	 * 免费次数
	 */
	@ApiModelProperty(value="免费次数")
	private Integer freeNumber;
	/**
	 * 购买次数
	 */
	@ApiModelProperty(value="购买次数")
	private Integer buyNumber;
	/**
	 * 使用次数
	 */
	@ApiModelProperty(value="使用次数")
	private Integer useNumber;
	/**
	 * 剩余次数
	 */
	@ApiModelProperty(value="剩余次数")
	private Integer surplusNumber;
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
