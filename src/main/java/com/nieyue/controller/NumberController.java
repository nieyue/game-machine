package com.nieyue.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Finance;
import com.nieyue.bean.Number;
import com.nieyue.service.NumberService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 次数控制类
 * @author yy
 *
 */
@Api(tags={"number"},value="次数",description="次数管理")
@RestController
@RequestMapping("/number")
public class NumberController extends BaseController<com.nieyue.bean.Number,Long> {
	@Resource
	private NumberService numberService;
	
	/**
	 * 次数分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "次数列表", notes = "次数分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="useNumber",value="使用次数",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query"),
	  		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Number>> list(
			@RequestParam(value="useNumber",required=false)Integer useNumber,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {

			Wrapper<Number> wrapper=new EntityWrapper<>();
			Map<String,Object> map=new HashMap<String,Object>();
			//map.put("use_number", useNumber);
			map.put("account_id", accountId);
			wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
			//大于等于
			if(useNumber!=null) {
				wrapper.andNew().ge("use_number", useNumber);
			}
			StateResultList<List<Number>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 次数修改
	 * @return
	 */
	@ApiOperation(value = "次数修改", notes = "次数修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Number>> update(@ModelAttribute Number number,HttpSession session)  {
		number.setUpdateDate(new Date());
		StateResultList<List<Number>> u = super.update(number);
		return u;
	}
	/**
	 * 次数增加
	 * @return 
	 */
	@ApiOperation(value = "次数增加", notes = "次数增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Number>> add(@ModelAttribute Number number, HttpSession session) {
		number.setCreateDate(new Date());
		number.setUpdateDate(new Date());
		StateResultList<List<Number>> a = super.add(number);
		return a;
	}
	/**
	 * 次数删除
	 * @return
	 */
	@ApiOperation(value = "次数删除", notes = "次数删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="numberId",value="次数ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Number>> delete(@RequestParam("numberId") Long numberId,HttpSession session)  {
		StateResultList<List<Number>> d = super.delete(numberId);
		return d;
	}
	/**
	 * 次数浏览数量
	 * @return
	 */
	@ApiOperation(value = "次数数量", notes = "次数数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="useNumber",value="使用次数",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="useNumber",required=false)Integer useNumber,
			@RequestParam(value="accountId",required=false)Long accountId,
			HttpSession session)  {
		Wrapper<Number> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<String,Object>();
		//map.put("use_number", useNumber);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		//大于等于
		if(useNumber!=null) {
			wrapper.andNew().ge("use_number", useNumber);
		}
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 次数单个加载
	 * @return
	 */
	@ApiOperation(value = "次数单个加载", notes = "次数单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="numberId",value="次数ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Number>> loadNumber(@RequestParam("numberId") Long numberId,HttpSession session)  {
		 StateResultList<List<Number>> l = super.load(numberId);
		 return l;
	}
	
}
