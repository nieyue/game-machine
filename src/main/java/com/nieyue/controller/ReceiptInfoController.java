package com.nieyue.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.ReceiptInfo;
import com.nieyue.service.ReceiptInfoService;
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
 * 收货信息控制类
 * @author yy
 *
 */
@Api(tags={"receiptInfo"},value="收货信息",description="收货信息管理")
@RestController
@RequestMapping("/receiptInfo")
public class ReceiptInfoController extends BaseController<ReceiptInfo,Long> {
	@Resource
	private ReceiptInfoService receiptInfoService;
	
	/**
	 * 收货信息分页浏览
	 * @param orderName 收货信息排序数据库字段
	 * @param orderWay 收货信息排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "收货信息列表", notes = "收货信息分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
			@ApiImplicitParam(name="isDefault",value="默认为2,1是,2不是",dataType="int", paramType = "query"),
	  		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ReceiptInfo>> list(
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="isDefault",required=false)Integer isDefault,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {

			Wrapper<ReceiptInfo> wrapper=new EntityWrapper<>();
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("account_id", accountId);
			map.put("is_default", isDefault);
			wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
			StateResultList<List<ReceiptInfo>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 收货信息修改
	 * @return
	 */
	@ApiOperation(value = "收货信息修改", notes = "收货信息修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ReceiptInfo>> update(@ModelAttribute ReceiptInfo receiptInfo,HttpSession session)  {
		receiptInfo.setUpdateDate(new Date());
		StateResultList<List<ReceiptInfo>> u = super.update(receiptInfo);
		return u;
	}
	/**
	 * 收货信息增加
	 * @return 
	 */
	@ApiOperation(value = "收货信息增加", notes = "收货信息增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ReceiptInfo>> add(@ModelAttribute ReceiptInfo receiptInfo, HttpSession session) {
		receiptInfo.setCreateDate(new Date());
		receiptInfo.setUpdateDate(new Date());
		StateResultList<List<ReceiptInfo>> a = super.add(receiptInfo);
		return a;
	}
	/**
	 * 收货信息删除
	 * @return
	 */
	@ApiOperation(value = "收货信息删除", notes = "收货信息删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="receiptInfoId",value="收货信息ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ReceiptInfo>> delete(@RequestParam("receiptInfoId") Long receiptInfoId,HttpSession session)  {
		StateResultList<List<ReceiptInfo>> d = super.delete(receiptInfoId);
		return d;
	}
	/**
	 * 收货信息浏览数量
	 * @return
	 */
	@ApiOperation(value = "收货信息数量", notes = "收货信息数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
			@ApiImplicitParam(name="isDefault",value="默认为2,1是,2不是",dataType="int", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="isDefault",required=false)Integer isDefault,
			HttpSession session)  {
		Wrapper<ReceiptInfo> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("account_id", accountId);
		map.put("is_default", isDefault);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 收货信息单个加载
	 * @return
	 */
	@ApiOperation(value = "收货信息单个加载", notes = "收货信息单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="receiptInfoId",value="收货信息ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<ReceiptInfo>> loadReceiptInfo(@RequestParam("receiptInfoId") Long receiptInfoId,HttpSession session)  {
		 StateResultList<List<ReceiptInfo>> l = super.load(receiptInfoId);
		 return l;
	}
	
}
