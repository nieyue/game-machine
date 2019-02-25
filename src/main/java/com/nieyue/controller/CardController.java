package com.nieyue.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Card;
import com.nieyue.service.CardService;
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
 * 卡片控制类
 * @author yy
 *
 */
@Api(tags={"card"},value="卡片",description="卡片管理")
@RestController
@RequestMapping("/card")
public class CardController extends BaseController<Card,Long> {
	@Resource
	private CardService cardService;
	
	/**
	 * 卡片分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "卡片列表", notes = "卡片分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="merId",value="商品id",dataType="long", paramType = "query"),
			@ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query"),
	  		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Card>> list(
			@RequestParam(value="merId",required=false)Long merId,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {

			Wrapper<Card> wrapper=new EntityWrapper<>();
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("mer_id", merId);
			map.put("account_id", accountId);
			wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
			StateResultList<List<Card>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 卡片修改
	 * @return
	 */
	@ApiOperation(value = "卡片修改", notes = "卡片修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Card>> update(@ModelAttribute Card card,HttpSession session)  {
		card.setUpdateDate(new Date());
		StateResultList<List<Card>> u = super.update(card);
		return u;
	}
	/**
	 * 卡片增加
	 * @return 
	 */
	@ApiOperation(value = "卡片增加", notes = "卡片增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Card>> add(@ModelAttribute Card card, HttpSession session) {
		card.setCreateDate(new Date());
		card.setUpdateDate(new Date());
		StateResultList<List<Card>> a = super.add(card);
		return a;
	}
	/**
	 * 卡片删除
	 * @return
	 */
	@ApiOperation(value = "卡片删除", notes = "卡片删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="CardId",value="卡片ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Card>> delete(@RequestParam("cardId") Long cardId,HttpSession session)  {
		StateResultList<List<Card>> d = super.delete(cardId);
		return d;
	}
	/**
	 * 卡片浏览数量
	 * @return
	 */
	@ApiOperation(value = "卡片数量", notes = "卡片数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="merId",value="商品id",dataType="long", paramType = "query"),
			@ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="merId",required=false)Long merId,
			@RequestParam(value="accountId",required=false)Long accountId,
			HttpSession session)  {
		Wrapper<Card> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("mer_id", merId);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 卡片单个加载
	 * @return
	 */
	@ApiOperation(value = "卡片单个加载", notes = "卡片单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="cardId",value="卡片ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Card>> loadCard(@RequestParam("cardId") Long cardId,HttpSession session)  {
		 StateResultList<List<Card>> l = super.load(cardId);
		 return l;
	}
	
}
