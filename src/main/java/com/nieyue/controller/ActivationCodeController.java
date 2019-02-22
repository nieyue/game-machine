package com.nieyue.controller;

import com.nieyue.bean.ActivationCode;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.business.OrderBusiness;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.util.DateUtil;
import com.nieyue.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.nieyue.service.ActivationCodeService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 激活码控制类
 * @author yy
 *
 */
@Api(tags={"activationCode"},value="激活码",description="激活码管理")
@RestController
@RequestMapping("/activationCode")
public class ActivationCodeController extends BaseController<ActivationCode,Long> {
	@Autowired
	private ActivationCodeService activationCodeService;
	@Autowired
	private OrderBusiness orderBusiness;

	/**
	 * 激活码分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "激活码列表", notes = "激活码分页浏览")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="isUsered",value="是否使用，1未使用，2已使用",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	  @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> list(
			@RequestParam(value="isUsered",required=false)Integer isUsered,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<ActivationCode> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("is_usered", isUsered);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		//大于等于
		/*if(createDate!=null) {
			wrapper.andNew().ge("create_date", createDate);
		}*/
		Map<String,Object> maplike=new HashMap<String,Object>();
		if(createDate!=null){
			maplike.put("create_date", DateUtil.dateFormatSimpleDate(createDate,"yyyy-MM-dd"));
		}
		Set<Map.Entry<String, Object>> newmaplie = MyDom4jUtil.getNoNullMap(maplike).entrySet();
		for (Map.Entry<String, Object> entry : newmaplie) {
			wrapper.like(entry.getKey(),(String)entry.getValue());
		}
		StateResultList<List<ActivationCode>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 激活码修改
	 * @return
	 */
	@ApiOperation(value = "激活码修改", notes = "激活码修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> update(@ModelAttribute ActivationCode activationCode,HttpSession session)  {
		activationCode.setUpdateDate(new Date());
		StateResultList<List<ActivationCode>> u = super.update(activationCode);
		return u;
	}
	/**
	 * 激活码提交
	 * @return
	 */
	@ApiOperation(value = "激活码提交", notes = "激活码提交")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query",required = true),
			@ApiImplicitParam(name="code",value="激活码",dataType="int", paramType = "query",required = true),
	})
	@RequestMapping(value = "/submit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> submit(
			@RequestParam(value="code")String code,
			@RequestParam(value="accountId")Long accountId,
			HttpSession session)  {
		Wrapper<ActivationCode> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		List<ActivationCode> al = activationCodeService.simplelist(wrapper);
		if(al.size()>0){
			throw new CommonRollbackException("账户已激活过");
		}
		if(StringUtils.isEmpty(code)){
			throw new CommonRollbackException("激活码错误");
		}
		Wrapper<ActivationCode> wrapper2=new EntityWrapper<>();
		Map<String,Object> map2=new HashMap<>();
		map2.put("code", code);
		wrapper2.allEq(MyDom4jUtil.getNoNullMap(map2));
		List<ActivationCode> al2 = activationCodeService.simplelist(wrapper2);
		if(al2.size()>0){
			ActivationCode activationCode=al2.get(0);
			if(activationCode.getIsUsered()!=1){
				throw new CommonRollbackException("激活码已使用");
			}else{
				activationCode.setUpdateDate(new Date());
				activationCode.setAccountId(accountId);
				activationCode.setIsUsered(2);
				StateResultList<List<ActivationCode>> u= super.update(activationCode);
				return u;
			}
		}else{
			throw new CommonRollbackException("激活码错误");
		}
	}
	/**
	 * 激活码增加
	 * @return 
	 */
	@ApiOperation(value = "激活码增加", notes = "激活码增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> add(@ModelAttribute ActivationCode activationCode, HttpSession session) {
		activationCode.setUpdateDate(new Date());
		StateResultList<List<ActivationCode>> a = super.add(activationCode);
		return a;
	}
	/**
	 * 激活码批量增加
	 * @return
	 */
	@ApiOperation(value = "激活码批量增加", notes = "激活码批量增加")
	@ApiImplicitParams({
			@ApiImplicitParam(name="number",value="增加数量",dataType="int", paramType = "query",required = true),
	})
	@RequestMapping(value = "/addBatch", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> addBatch(
			@RequestParam(value="number")Integer number,
			HttpSession session) {
		StateResultList<List<ActivationCode>> sr = new StateResultList<>();
		Lock lock = new ReentrantLock();
		for (int i = 0; i < number; i++) {
			try{
				lock.lock();
				ActivationCode ac=new ActivationCode();
				ac.setCreateDate(new Date());
				ac.setUpdateDate(new Date());
				ac.setIsUsered(1);
				ac.setCode(orderBusiness.generateShortUuid());
				sr = super.add(ac);
			}finally {
				lock.unlock();
			}
		}
		return sr;
	}
	/**
	 * 激活码删除
	 * @return
	 */
	@ApiOperation(value = "激活码删除", notes = "激活码删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="activationCodeId",value="激活码ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> delete(@RequestParam("activationCodeId") Long activationCodeId,HttpSession session)  {
		StateResultList<List<ActivationCode>> d = super.delete(activationCodeId);
		return d;
	}
	/**
	 * 激活码浏览数量
	 * @return
	 */
	@ApiOperation(value = "激活码数量", notes = "激活码数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="isUsered",value="是否使用，1未使用，2已使用",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
			@ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="isUsered",required=false)Integer isUsered,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			HttpSession session)  {
		Wrapper<ActivationCode> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("is_usered", isUsered);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		/*//大于等于
		if(createDate!=null){
			wrapper.andNew().ge("create_date",createDate);
		}*/
		Map<String,Object> maplike=new HashMap<String,Object>();
		if(createDate!=null){
			maplike.put("create_date", DateUtil.dateFormatSimpleDate(createDate,"yyyy-MM-dd"));
		}
		Set<Map.Entry<String, Object>> newmaplie = MyDom4jUtil.getNoNullMap(maplike).entrySet();
		for (Map.Entry<String, Object> entry : newmaplie) {
			wrapper.like(entry.getKey(),(String)entry.getValue());
		}
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 激活码单个加载
	 * @return
	 */
	@ApiOperation(value = "激活码单个加载", notes = "激活码单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="activationCodeId",value="激活码ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<ActivationCode>> loadActivationCode(@RequestParam("activationCodeId") Long activationCodeId,HttpSession session)  {
		 StateResultList<List<ActivationCode>> l = super.load(activationCodeId);
		 return l;
	}

}
