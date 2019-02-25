package com.nieyue.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.RechargeRecord;
import com.nieyue.service.RechargeRecordService;
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
 * 充值记录控制类
 * @author yy
 *
 */
@Api(tags={"rechargeRecord"},value="充值记录",description="充值记录管理")
@RestController
@RequestMapping("/rechargeRecord")
public class RechargeRecordController extends BaseController<RechargeRecord,Long> {
	@Resource
	private RechargeRecordService rechargeRecordService;
	
	/**
	 * 充值记录分页浏览
	 * @param orderName 充值记录排序数据库字段
	 * @param orderWay 充值记录排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "充值记录列表", notes = "充值记录分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
			@ApiImplicitParam(name="status",value="默认为1成功，2失败",dataType="int", paramType = "query"),
	  		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<RechargeRecord>> list(
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {

			Wrapper<RechargeRecord> wrapper=new EntityWrapper<>();
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("account_id", accountId);
			map.put("status", status);
			wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
			StateResultList<List<RechargeRecord>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 充值记录修改
	 * @return
	 */
	@ApiOperation(value = "充值记录修改", notes = "充值记录修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<RechargeRecord>> update(@ModelAttribute RechargeRecord rechargeRecord,HttpSession session)  {
		rechargeRecord.setUpdateDate(new Date());
		StateResultList<List<RechargeRecord>> u = super.update(rechargeRecord);
		return u;
	}
	/**
	 * 充值记录增加
	 * @return 
	 */
	@ApiOperation(value = "充值记录增加", notes = "充值记录增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<RechargeRecord>> add(@ModelAttribute RechargeRecord rechargeRecord, HttpSession session) {
		rechargeRecord.setCreateDate(new Date());
		rechargeRecord.setUpdateDate(new Date());
		StateResultList<List<RechargeRecord>> a = super.add(rechargeRecord);
		return a;
	}
	/**
	 * 充值记录删除
	 * @return
	 */
	@ApiOperation(value = "充值记录删除", notes = "充值记录删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="rechargeRecordId",value="充值记录ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<RechargeRecord>> delete(@RequestParam("rechargeRecordId") Long rechargeRecordId,HttpSession session)  {
		StateResultList<List<RechargeRecord>> d = super.delete(rechargeRecordId);
		return d;
	}
	/**
	 * 充值记录浏览数量
	 * @return
	 */
	@ApiOperation(value = "充值记录数量", notes = "充值记录数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
			@ApiImplicitParam(name="status",value="默认为1成功，2失败",dataType="int", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="status",required=false)Integer status,
			HttpSession session)  {
		Wrapper<RechargeRecord> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("account_id", accountId);
		map.put("status", status);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 充值记录单个加载
	 * @return
	 */
	@ApiOperation(value = "充值记录单个加载", notes = "充值记录单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="rechargeRecordId",value="充值记录ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<RechargeRecord>> loadRechargeRecord(@RequestParam("rechargeRecordId") Long rechargeRecordId,HttpSession session)  {
		 StateResultList<List<RechargeRecord>> l = super.load(rechargeRecordId);
		 return l;
	}
	
}
