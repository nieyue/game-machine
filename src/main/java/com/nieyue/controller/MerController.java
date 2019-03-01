package com.nieyue.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Mer;
import com.nieyue.bean.Number;
import com.nieyue.exception.NotIsNotExistException;
import com.nieyue.service.MerService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * 商品控制类
 * @author yy
 *
 */
@Api(tags={"mer"},value="商品",description="商品管理")
@RestController
@RequestMapping("/mer")
public class MerController extends BaseController<Mer,Long> {
	@Resource
	private MerService merService;


	/**
	 * 商品分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "商品列表", notes = "商品分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="status",value="状态,默认1上架,2下架",dataType="int", paramType = "query"),
	  		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Mer>> list(
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {

			Wrapper<Mer> wrapper=new EntityWrapper<>();
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("status", status);
			wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
			StateResultList<List<Mer>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 商品修改
	 * @return
	 */
	@ApiOperation(value = "商品修改", notes = "商品修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Mer>> update(@ModelAttribute Mer mer,HttpSession session)  {
		mer.setUpdateDate(new Date());
		StateResultList<List<Mer>> u = super.update(mer);
		return u;
	}
	/**
	 * 商品增加
	 * @return 
	 */
	@ApiOperation(value = "商品增加", notes = "商品增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Mer>> add(@ModelAttribute Mer mer, HttpSession session) {
		mer.setCreateDate(new Date());
		mer.setUpdateDate(new Date());
		StateResultList<List<Mer>> a = super.add(mer);
		return a;
	}
	/**
	 * 商品删除
	 * @return
	 */
	@ApiOperation(value = "商品删除", notes = "商品删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="merId",value="商品ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Mer>> delete(@RequestParam("merId") Long merId,HttpSession session)  {
		StateResultList<List<Mer>> d = super.delete(merId);
		return d;
	}
	/**
	 * 商品浏览数量
	 * @return
	 */
	@ApiOperation(value = "商品数量", notes = "商品数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="status",value="状态,默认1上架,2下架",dataType="int", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="status",required=false)Integer status,
			HttpSession session)  {
		Wrapper<Mer> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("status", status);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 商品单个加载
	 * @return
	 */
	@ApiOperation(value = "商品单个加载", notes = "商品单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="merId",value="商品ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Mer>> loadMer(@RequestParam("merId") Long merId,HttpSession session)  {
		 StateResultList<List<Mer>> l = super.load(merId);
		 return l;
	}
	/**
	 * 商品抓取
	 * @return
	 */
	@ApiOperation(value = "商品抓取", notes = "商品抓取")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query",required=true),
		  @ApiImplicitParam(name="merId",value="商品ID",dataType="long", paramType = "query",required=true),
		  @ApiImplicitParam(name="cardType",value="卡片类型，0失败，1袋身卡，2面料卡，3手挽卡,4五金卡",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/catch", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Number>> merCatch(
			@RequestParam("accountId") Long accountId,
			@RequestParam("merId") Long merId,
			@RequestParam("cardType") Integer cardType,
			HttpSession session)  {
		Number number = merService.merCatch(accountId, merId, cardType);
		if(number!=null &&!number.equals("")){
			List<Number> list = new ArrayList<>();
			list.add(number);
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			throw new NotIsNotExistException("");//不存在
		}
	}

}
