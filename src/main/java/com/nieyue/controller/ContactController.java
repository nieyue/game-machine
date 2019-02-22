package com.nieyue.controller;

import com.nieyue.bean.Contact;
import com.nieyue.service.ContactService;
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
 * 联系控制类
 * @author yy
 *
 */
@Api(tags={"contact"},value="联系",description="联系管理")
@RestController
@RequestMapping("/contact")
public class ContactController extends BaseController<Contact,Long> {
	@Resource
	private ContactService contactService;
	
	/**
	 * 联系分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "联系列表", notes = "联系分页浏览")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	    @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	    @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="contactId"),
	    @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Contact>> browsePagingContact(
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="contactId") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			StateResultList<List<Contact>> rl = super.list(pageNum, pageSize, orderName, orderWay,null);
			return rl;
	}
	/**
	 * 联系修改
	 * @return
	 */
	@ApiOperation(value = "联系修改", notes = "联系修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Contact>> updateContact(
			@ModelAttribute Contact contact,HttpSession session)  {
		contact.setUpdateDate(new Date());
		StateResultList<List<Contact>> u = super.update(contact);
		return u;
	}
	/**
	 * 联系增加
	 * @return 
	 */
	@ApiOperation(value = "联系增加", notes = "联系增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Contact>> addContact(
			@ModelAttribute Contact contact,
			HttpSession session) {
		contact.setCreateDate(new Date());
		contact.setUpdateDate(new Date());
		StateResultList<List<Contact>> a = super.add(contact);
		return a;
	}
	/**
	 * 联系删除
	 * @return
	 */
	@ApiOperation(value = "联系删除", notes = "联系删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="contactId",value="联系ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Contact>> delContact(
			@RequestParam("contactId") Long contactId,HttpSession session)  {
		StateResultList<List<Contact>> d = super.delete(contactId);
		return d;
	}
	/**
	 * 联系浏览数量
	 * @return
	 */
	@ApiOperation(value = "联系数量", notes = "联系数量查询")
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			HttpSession session)  {
		StateResultList<List<Integer>> c = super.count(null);
		return c;
	}
	/**
	 * 联系单个加载
	 * @return
	 */
	@ApiOperation(value = "联系单个加载", notes = "联系单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="contactId",value="联系ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Contact>> loadContact(
			@RequestParam("contactId") Long contactId,HttpSession session)  {
		 StateResultList<List<Contact>> l = super.load(contactId);
		 return l;
	}
	
}
