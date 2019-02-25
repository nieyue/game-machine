package com.nieyue.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.MerOrderDetail;
import com.nieyue.service.MerOrderDetailService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品订单详情控制类
 * @author yy
 *
 */
@Api(tags={"merOrderDetail"},value="商品订单详情",description="商品订单详情管理")
@RestController
@RequestMapping("/merOrderDetail")
public class MerOrderDetailController extends BaseController<MerOrderDetail,Long> {
	@Resource
	private MerOrderDetailService merOrderDetailService;

	/**
	 * 商品订单详情分页浏览
	 * @param orderName 商品订单详情排序数据库字段
	 * @param orderWay 商品订单详情排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "商品订单详情列表", notes = "商品订单详情分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="status",value="订单状态，1待发货，2已发货",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="merOrderId",value="商品订单ID",dataType="long", paramType = "query"),
			@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
			@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
			@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	})
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<MerOrderDetail>> list(
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="merOrderId",required=false)Long merOrderId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {

		Wrapper<MerOrderDetail> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("status", status);
		map.put("mer_order_id", merOrderId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<MerOrderDetail>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
		return rl;
	}
	/**
	 * 商品订单详情修改
	 * @return
	 */
	@ApiOperation(value = "商品订单详情修改", notes = "商品订单详情修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<MerOrderDetail>> update(@ModelAttribute MerOrderDetail merOrderDetail,HttpSession session)  {
		merOrderDetail.setUpdateDate(new Date());
		StateResultList<List<MerOrderDetail>> u = super.update(merOrderDetail);
		return u;
	}
	/**
	 * 商品订单详情增加
	 * @return 
	 */
	@ApiOperation(value = "商品订单详情增加", notes = "商品订单详情增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<MerOrderDetail>> add(@ModelAttribute MerOrderDetail merOrderDetail, HttpSession session) {
		merOrderDetail.setCreateDate(new Date());
		merOrderDetail.setUpdateDate(new Date());
		StateResultList<List<MerOrderDetail>> a = super.add(merOrderDetail);
		return a;
	}
	/**
	 * 商品订单详情删除
	 * @return
	 */
	@ApiOperation(value = "商品订单详情删除", notes = "商品订单详情删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="merOrderDetailId",value="商品订单详情ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<MerOrderDetail>> delete(@RequestParam("merOrderDetailId") Long merOrderDetailId,HttpSession session)  {
		StateResultList<List<MerOrderDetail>> d = super.delete(merOrderDetailId);
		return d;
	}
	/**
	 * 商品订单详情浏览数量
	 * @return
	 */
	@ApiOperation(value = "商品订单详情数量", notes = "商品订单详情数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="status",value="订单状态，1待发货，2已发货",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="merOrderId",value="商品订单ID",dataType="long", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="merOrderId",required=false)Long merOrderId,
			HttpSession session)  {
		Wrapper<MerOrderDetail> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("status", status);
		map.put("mer_order_id", merOrderId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 商品订单详情单个加载
	 * @return
	 */
	@ApiOperation(value = "商品订单详情单个加载", notes = "商品订单详情单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="merOrderDetailId",value="商品订单详情ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<MerOrderDetail>> loadMerOrderDetail(@RequestParam("merOrderDetailId") Long merOrderDetailId,HttpSession session)  {
		 StateResultList<List<MerOrderDetail>> l = super.load(merOrderDetailId);
		 return l;
	}
	
}
