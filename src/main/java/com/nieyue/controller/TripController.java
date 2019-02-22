package com.nieyue.controller;

import com.nieyue.bean.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.service.AccountService;
import com.nieyue.service.ConfigService;
import com.nieyue.service.IntegralService;
import com.nieyue.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nieyue.service.TripService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 行程控制类
 * @author yy
 *
 */
@Api(tags={"trip"},value="行程",description="行程管理")
@RestController
@RequestMapping("/trip")
public class TripController extends BaseController<Trip,Long> {
	@Autowired
	private TripService tripService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private IntegralService integralService;

	/**
	 * 顺风车查询
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "顺风车查询", notes = "顺风车查询分页浏览")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="type",value="类型，1车主，2乘客",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="startCity",value="当前城市",dataType="string", paramType = "query"),
	  @ApiImplicitParam(name="endCity",value="目的城市",dataType="string", paramType = "query"),
	  @ApiImplicitParam(name="isDoor",value="是否上门接送，1是，2否",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	  @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="createDate"),
	  @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/search", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> search(
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="startCity",required=false)String startCity,
			@RequestParam(value="endCity",required=false)String endCity,
			@RequestParam(value="isDoor",required=false)Integer isDoor,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="createDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<Trip> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("type", type);
		map.put("is_door", isDoor);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		//大于等于3天前
		if(createDate!=null) {
			wrapper.andNew().ge("start_date", createDate);
		}else{
			wrapper.andNew().ge("start_date", new Date(new Date().getTime()-1000*60*60*24*3));
		}
		//第1种起始地址与目的地址都相近
		Map<String,Object> maplike=new HashMap<String,Object>();
		if(type!=null&&type==1){//车主
			maplike.put("start_address", startCity);
			maplike.put("end_address", endCity);
/*
			maplike.put("start_address", startCity.substring(0,startCity.indexOf('市')));
			maplike.put("end_address", endCity.substring(0,endCity.indexOf('市')));
*/
			Set<Map.Entry<String, Object>> newmaplie = MyDom4jUtil.getNoNullMap(maplike).entrySet();
			for (Map.Entry<String, Object> entry : newmaplie) {
				wrapper.like(entry.getKey(),(String)entry.getValue());
			}
		}
		if(type!=null&&type==2){
			wrapper.andNew().like("start_address", startCity);
			wrapper.or().like("middle_address",startCity);
		}

		List<Trip> tl = tripService.list(pageNum, pageSize, orderName, orderWay, wrapper);
		if(tl!=null&&tl.size()>0){
			List<Trip> ntl=new ArrayList<>();
			tl.forEach(t->{
				Account account = accountService.load(t.getAccountId());
				if(account!=null){
					t.setAccount(account);
					ntl.add(t);
				}
			});
			return ResultUtil.getSlefSRSuccessList(ntl);
		}else{
			//第2种起始地址相近
			/*Wrapper<Trip> wrapper2=new EntityWrapper<>();
			Map<String,Object> map2=new HashMap<>();
			map2.put("is_door", isDoor);
			map2.put("account_id", accountId);
			wrapper2.allEq(MyDom4jUtil.getNoNullMap(map2));
			//大于等于
			if(createDate!=null) {
				wrapper2.andNew().ge("start_date", createDate);
			}else{
			wrapper.andNew().ge("start_date", new Date());
		}
			Map<String,Object> maplike2=new HashMap<String,Object>();
			maplike2.put("start_address", startCity);
			Set<Map.Entry<String, Object>> newmaplie2 = MyDom4jUtil.getNoNullMap(maplike2).entrySet();
			for (Map.Entry<String, Object> entry2 : newmaplie2) {
				wrapper2.like(entry2.getKey(),(String)entry2.getValue());
			}
			List<Trip> tl2 = tripService.list(pageNum, pageSize, orderName, orderWay, wrapper2);
			if(tl2!=null&&tl2.size()>0){
			List<Trip> ntl2=new ArrayList<>();
				tl2.forEach(t2->{
					Account account = accountService.load(t2.getAccountId());
					if(account!=null){
					t2.setAccount(account);
					ntl2.add(t);
				}
				});
				return ResultUtil.getSlefSRSuccessList(ntl2);
			}*/

		}
		throw new NotAnymoreException();//没有更多
	}
	/**
	 * 行程分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "行程列表", notes = "行程分页浏览")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="type",value="类型，1车主，2乘客",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="isDoor",value="是否上门接送，1是，2否",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	  @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="createDate"),
	  @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> list(
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="isDoor",required=false)Integer isDoor,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="createDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<Trip> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("type", type);
		map.put("is_door", isDoor);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		//大于等于
		if(createDate!=null) {
			wrapper.andNew().ge("create_date", createDate);
		}
		List<Trip> tl = tripService.list(pageNum, pageSize, orderName, orderWay, wrapper);
		if(tl!=null&&tl.size()>0){
			tl.forEach(t->{
				Account account = accountService.load(t.getAccountId());
				t.setAccount(account);
			});
			return ResultUtil.getSlefSRSuccessList(tl);
		}
		throw new NotAnymoreException();//没有更多
	}
	/**
	 * 行程修改
	 * @return
	 */
	@ApiOperation(value = "行程修改", notes = "行程修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> update(@ModelAttribute Trip trip,HttpSession session)  {
		trip.setUpdateDate(new Date());
		StateResultList<List<Trip>> u = super.update(trip);
		return u;
	}
	/**
	 * 行程增加
	 * @return
	 */
	@ApiOperation(value = "行程增加", notes = "行程增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> add(@ModelAttribute Trip trip, HttpSession session) {
		trip.setCreateDate(new Date());
		trip.setUpdateDate(new Date());
		StateResultList<List<Trip>> u = super.add(trip);
		return u;
	}
	/**
	 * 车主行程增加
	 * @return 
	 */
	@ApiOperation(value = "车主行程增加", notes = "车主行程增加")
	@RequestMapping(value = "/caradd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> caradd(@ModelAttribute Trip trip, HttpSession session) {
		Lock lock=new ReentrantLock();
		try{
			lock.lock();
			Account account = accountService.load(trip.getAccountId());
			Role role = (Role)session.getAttribute("role");
			if(account==null || 1!=trip.getType()){
				throw new CommonRollbackException("非车主发布");
			}
			if((role.getName().indexOf("管理")<=-1)
					&&(account.getAuth()!=2)){
				throw new CommonRollbackException("账户没有认证");
			}
			if(trip.getStartDate().before(new Date())){
				throw new CommonRollbackException("请选择开始时间大于当前时间");
			}
			List<Config> cl = configService.simplelist(null);
			if(cl.size()>0){
				Config c = cl.get(0);
				Integer freeNumber = c.getFreeNumber();
				Wrapper<Trip> w=new EntityWrapper<>();
				Map<String,Object> m=new HashMap<>();
				m.put("type", trip.getType());
				m.put("account_id", trip.getAccountId());
				w.allEq(MyDom4jUtil.getNoNullMap(m));
				Map<String,Object> maplike=new HashMap<String,Object>();
				maplike.put("create_date", DateUtil.dateFormatSimpleDate(new Date(),"yyyy-MM-dd"));
				Set<Map.Entry<String, Object>> newmaplie = MyDom4jUtil.getNoNullMap(maplike).entrySet();
				for (Map.Entry<String, Object> entry : newmaplie) {
					w.like(entry.getKey(),(String)entry.getValue());
				}
				List<Trip> tl = tripService.list(1, freeNumber + 1, "create_date", "desc", w);
				if(tl.size()>=freeNumber){
					throw new CommonRollbackException("每日免费发布"+freeNumber+"次");
				}

				if(tl.size()>0&&(new Date().before(new Date(tl.get(0).getCreateDate().getTime()+30*60*1000)))){
					throw new CommonRollbackException("两次发布时间间隔30分钟");

				}
			}
			trip.setUpdateDate(new Date());
			trip.setCreateDate(new Date());
			StateResultList<List<Trip>> a = super.add(trip);
			Wrapper<Integral> wrapper=new EntityWrapper<>();
			Map<String,Object> map=new HashMap<>();
			map.put("account_id", trip.getAccountId());
			wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
			List<Integral> il = integralService.list(1, 1, null, null, wrapper);
			if(il.size()>0){
				Integral integral = il.get(0);
				integral.setIntegral(Arith.add(il.get(0).getIntegral(),1));//加1积分
				integralService.update(integral);
			}
			return a;
		}finally {
			lock.unlock();
		}
	}
	/**
	 * 乘客行程增加
	 * @return
	 */
	@ApiOperation(value = "乘客行程增加", notes = "乘客行程增加")
	@RequestMapping(value = "/useradd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> useradd(@ModelAttribute Trip trip, HttpSession session) {
		Lock lock=new ReentrantLock();
		try{
			lock.lock();
			Account account = accountService.load(trip.getAccountId());
			Role role = (Role)session.getAttribute("role");
			if(account==null ||2!=trip.getType()){
				throw new CommonRollbackException("非乘客发布");
			}

			/*if((role.getName().indexOf("管理")<=-1)
					&&( account.getAuth()!=2)){
				throw new CommonRollbackException("账户没有认证");
			}*/
			if(new Date().getHours()<6 || new Date().getHours()>21){
				throw new CommonRollbackException("每天6-21时发布");
			}
			if(trip.getStartDate().before(new Date())){
				throw new CommonRollbackException("请选择开始时间大于当前时间");
			}
			List<Config> cl = configService.simplelist(null);
			if(cl.size()>0){
				Config c = cl.get(0);
				Integer freeNumber = c.getFreeNumber();
				Wrapper<Trip> w=new EntityWrapper<>();
				Map<String,Object> m=new HashMap<>();
				m.put("type", trip.getType());
				m.put("account_id", trip.getAccountId());
				w.allEq(MyDom4jUtil.getNoNullMap(m));
				Map<String,Object> maplike=new HashMap<String,Object>();
				maplike.put("create_date", DateUtil.dateFormatSimpleDate(new Date(),"yyyy-MM-dd"));
				Set<Map.Entry<String, Object>> newmaplie = MyDom4jUtil.getNoNullMap(maplike).entrySet();
				for (Map.Entry<String, Object> entry : newmaplie) {
					w.like(entry.getKey(),(String)entry.getValue());
				}
				List<Trip> tl = tripService.list(1, freeNumber + 1, "create_date", "desc", w);
				/*if(tl.size()>=freeNumber){
					throw new CommonRollbackException("每日免费发布"+freeNumber+"次");
				}*/

				if(tl.size()>0&&(new Date().before(new Date(tl.get(0).getCreateDate().getTime()+c.getPerSeconds()*1000)))){
					throw new CommonRollbackException("两次发布时间间隔"+c.getPerSeconds()/60+"分钟");

				}
			}
			trip.setUpdateDate(new Date());
			trip.setCreateDate(new Date());
			StateResultList<List<Trip>> a = super.add(trip);
			Wrapper<Integral> wrapper=new EntityWrapper<>();
			Map<String,Object> map=new HashMap<>();
			map.put("account_id", trip.getAccountId());
			wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
			List<Integral> il = integralService.list(1, 1, null, null, wrapper);
			if(il.size()>0){
				Integral integral = il.get(0);
				integral.setIntegral(Arith.add(il.get(0).getIntegral(),1));//加1积分
				integralService.update(integral);
			}
			return a;
		}finally {
			lock.unlock();
		}
	}
	/**
	 * 行程删除
	 * @return
	 */
	@ApiOperation(value = "行程删除", notes = "行程删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="tripId",value="行程ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> delete(@RequestParam("tripId") Long tripId,HttpSession session)  {
		StateResultList<List<Trip>> d = super.delete(tripId);
		return d;
	}
	/**
	 * 行程浏览数量
	 * @return
	 */
	@ApiOperation(value = "行程数量", notes = "行程数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="type",value="类型，1车主，2乘客",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="isDoor",value="是否上门接送，1是，2否",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
			@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="isDoor",required=false)Integer isDoor,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="accountId",required=false)Long accountId,
			HttpSession session)  {
		Wrapper<Trip> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("type", type);
		map.put("is_door", isDoor);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		//大于等于
		if(createDate!=null) {
			wrapper.andNew().ge("create_date", createDate);
		}
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 行程单个加载
	 * @return
	 */
	@ApiOperation(value = "行程单个加载", notes = "行程单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="tripId",value="行程ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Trip>> loadTrip(@RequestParam("tripId") Long tripId,HttpSession session)  {
		 StateResultList<List<Trip>> l = super.load(tripId);
		 return l;
	}
	
}
