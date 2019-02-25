package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 充值项
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="充值项",description="充值项")
@TableName("recharge_term_tb")
public class RechargeTerm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 充值项id
	 */
	@ApiModelProperty(value="充值项id")
	@TableId("recharge_term_id")
	private Long rechargeTermId;
	/**
	 * 附加标题
	 */
	@ApiModelProperty(value="附加标题")
	private String title;
	/**
	 * 充值真钱
	 */
	@ApiModelProperty(value="充值真钱")
	private Double rechargeMoney;
	/**
	 * 赠送次数
	 */
	@ApiModelProperty(value="赠送次数")
	private Integer giveNumber;
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
