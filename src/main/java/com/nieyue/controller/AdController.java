package com.nieyue.controller;

import com.nieyue.bean.Ad;
import com.nieyue.service.AdService;
import com.nieyue.util.StateResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


/**
 * 广告控制类
 * @author yy
 *
 */
@Api(tags={"ad"},value="广告",description="广告管理")
@RestController
@RequestMapping("/ad")
public class AdController extends BaseController<Ad,Long> {
	@Resource
	private AdService adService;
	
	/**
	 * 广告分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "广告列表", notes = "广告分页浏览")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	  @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Ad>> list(
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			StateResultList<List<Ad>> rl = super.list(pageNum, pageSize, orderName, orderWay,null);
			return rl;
	}
	/**
	 * 广告修改
	 * @return
	 */
	@ApiOperation(value = "广告修改", notes = "广告修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Ad>> update(@ModelAttribute Ad ad,HttpSession session)  {
		ad.setUpdateDate(new Date());
		StateResultList<List<Ad>> u = super.update(ad);
		return u;
	}
	/**
	 * 广告增加
	 * @return 
	 */
	@ApiOperation(value = "广告增加", notes = "广告增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Ad>> add(@ModelAttribute Ad ad, HttpSession session) {
		ad.setCreateDate(new Date());
		ad.setUpdateDate(new Date());
		StateResultList<List<Ad>> a = super.add(ad);
		return a;
	}
	/**
	 * 广告删除
	 * @return
	 */
	@ApiOperation(value = "广告删除", notes = "广告删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="adId",value="广告ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Ad>> delete(@RequestParam("adId") Long adId,HttpSession session)  {
		StateResultList<List<Ad>> d = super.delete(adId);
		return d;
	}
	/**
	 * 广告浏览数量
	 * @return
	 */
	@ApiOperation(value = "广告数量", notes = "广告数量查询")
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(HttpSession session)  {
		StateResultList<List<Integer>> c = super.count(null);
		return c;
	}
	/**
	 * 广告单个加载
	 * @return
	 */
	@ApiOperation(value = "广告单个加载", notes = "广告单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="adId",value="广告ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Ad>> loadAd(@RequestParam("adId") Long adId,HttpSession session)  {
		 StateResultList<List<Ad>> l = super.load(adId);
		 return l;
	}
	
}
