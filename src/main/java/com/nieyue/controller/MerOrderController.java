package com.nieyue.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.MerOrder;
import com.nieyue.service.MerOrderService;
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
 * 商品订单控制类
 * @author yy
 *
 */
@Api(tags={"merOrder"},value="商品订单",description="商品订单管理")
@RestController
@RequestMapping("/merOrder")
public class MerOrderController extends BaseController<MerOrder,Long> {
	@Resource
	private MerOrderService merOrderService;
	
	/**
	 * 商品订单分页浏览
	 * @param orderName 商品订单排序数据库字段
	 * @param orderWay 商品订单排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "商品订单列表", notes = "商品订单分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="下单人",dataType="long", paramType = "query"),
	  		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<MerOrder>> list(
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {

			Wrapper<MerOrder> wrapper=new EntityWrapper<>();
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("account_id", accountId);
			wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
			StateResultList<List<MerOrder>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 商品订单修改
	 * @return
	 */
	@ApiOperation(value = "商品订单修改", notes = "商品订单修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<MerOrder>> update(@ModelAttribute MerOrder merOrder,HttpSession session)  {
		merOrder.setUpdateDate(new Date());
		StateResultList<List<MerOrder>> u = super.update(merOrder);
		return u;
	}
	/**
	 * 商品订单增加
	 * @return 
	 */
	@ApiOperation(value = "商品订单增加", notes = "商品订单增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<MerOrder>> add(@ModelAttribute MerOrder merOrder, HttpSession session) {
		merOrder.setCreateDate(new Date());
		merOrder.setUpdateDate(new Date());
		StateResultList<List<MerOrder>> a = super.add(merOrder);
		return a;
	}
	/**
	 * 商品订单删除
	 * @return
	 */
	@ApiOperation(value = "商品订单删除", notes = "商品订单删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="merOrderId",value="商品订单ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<MerOrder>> delete(@RequestParam("merOrderId") Long merOrderId,HttpSession session)  {
		StateResultList<List<MerOrder>> d = super.delete(merOrderId);
		return d;
	}
	/**
	 * 商品订单浏览数量
	 * @return
	 */
	@ApiOperation(value = "商品订单数量", notes = "商品订单数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="下单人",dataType="long", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="accountId",required=false)Long accountId,
			HttpSession session)  {
		Wrapper<MerOrder> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 商品订单单个加载
	 * @return
	 */
	@ApiOperation(value = "商品订单单个加载", notes = "商品订单单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="merOrderId",value="商品订单ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<MerOrder>> loadMerOrder(@RequestParam("merOrderId") Long merOrderId,HttpSession session)  {
		 StateResultList<List<MerOrder>> l = super.load(merOrderId);
		 return l;
	}
	
}
