package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告
 * @author 聂跃
 * @date 2017年4月12日
 */
@Data
@ApiModel(value="广告",description="广告")
@TableName("ad_tb")
public class Ad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 广告id
	 */
	@ApiModelProperty(value="广告id")
	@TableId("ad_id")
	private Long adId;
	/**
	 * 名称
	 */
	@ApiModelProperty(value="名称")
	private String name;
	/**
	 * 单号
	 */
	@ApiModelProperty(value="单号")
	private String orderName;
	/**
	 * 分类
	 */
	@ApiModelProperty(value="分类")
	private String typeName;
	/**
	 * 图文链接
	 */
	@ApiModelProperty(value="图文链接")
	private String link;
	/**
	 * 广告标题
	 */
	@ApiModelProperty(value="广告标题")
	private String title;
	/**
	 * 广告内容
	 */
	@ApiModelProperty(value="广告内容")
	private String content;
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value="开始时间")
	private String startDate;
	/**
	 * 发送时段
	 */
	@ApiModelProperty(value="发送时段")
	private String sendDate;
	/**
	 * 发送速度
	 */
	@ApiModelProperty(value="发送速度")
	private String sendSpeed;
	/**
	 * 计划发送量
	 */
	@ApiModelProperty(value="计划发送量")
	private String sendNumber;
	/**
	 * 已完成发送量
	 */
	@ApiModelProperty(value="已完成发送量")
	private String finishSendNumber;
	/**
	 * 广告状态
	 */
	@ApiModelProperty(value="广告状态")
	private String status;
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
