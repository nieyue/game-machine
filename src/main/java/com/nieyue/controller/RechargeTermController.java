package com.nieyue.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.RechargeTerm;
import com.nieyue.service.RechargeTermService;
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
 * 充值项控制类
 * @author yy
 *
 */
@Api(tags={"rechargeTerm"},value="充值项",description="充值项管理")
@RestController
@RequestMapping("/rechargeTerm")
public class RechargeTermController extends BaseController<RechargeTerm,Long> {
	@Resource
	private RechargeTermService rechargeTermService;
	
	/**
	 * 充值项分页浏览
	 * @param orderName 充值项排序数据库字段
	 * @param orderWay 充值项排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "充值项列表", notes = "充值项分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="status",value="状态,默认1上架,2下架",dataType="int", paramType = "query"),
	  		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<RechargeTerm>> list(
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {

			Wrapper<RechargeTerm> wrapper=new EntityWrapper<>();
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("status", status);
			wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
			StateResultList<List<RechargeTerm>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 充值项修改
	 * @return
	 */
	@ApiOperation(value = "充值项修改", notes = "充值项修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<RechargeTerm>> update(@ModelAttribute RechargeTerm rechargeTerm,HttpSession session)  {
		rechargeTerm.setUpdateDate(new Date());
		StateResultList<List<RechargeTerm>> u = super.update(rechargeTerm);
		return u;
	}
	/**
	 * 充值项增加
	 * @return 
	 */
	@ApiOperation(value = "充值项增加", notes = "充值项增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<RechargeTerm>> add(@ModelAttribute RechargeTerm rechargeTerm, HttpSession session) {
		rechargeTerm.setCreateDate(new Date());
		rechargeTerm.setUpdateDate(new Date());
		StateResultList<List<RechargeTerm>> a = super.add(rechargeTerm);
		return a;
	}
	/**
	 * 充值项删除
	 * @return
	 */
	@ApiOperation(value = "充值项删除", notes = "充值项删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="rechargeTermId",value="充值项ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<RechargeTerm>> delete(@RequestParam("rechargeTermId") Long rechargeTermId,HttpSession session)  {
		StateResultList<List<RechargeTerm>> d = super.delete(rechargeTermId);
		return d;
	}
	/**
	 * 充值项浏览数量
	 * @return
	 */
	@ApiOperation(value = "充值项数量", notes = "充值项数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="status",value="状态,默认1上架,2下架",dataType="int", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="status",required=false)Integer status,
			HttpSession session)  {
		Wrapper<RechargeTerm> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("status", status);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 充值项单个加载
	 * @return
	 */
	@ApiOperation(value = "充值项单个加载", notes = "充值项单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="rechargeTermId",value="充值项ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<RechargeTerm>> loadRechargeTerm(@RequestParam("rechargeTermId") Long rechargeTermId,HttpSession session)  {
		 StateResultList<List<RechargeTerm>> l = super.load(rechargeTermId);
		 return l;
	}
	
}
