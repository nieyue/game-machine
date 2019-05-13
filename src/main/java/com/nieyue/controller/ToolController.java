package com.nieyue.controller;

import com.nieyue.comments.IPCountUtil;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.util.*;
import com.nieyue.verification.VerificationCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;


/**
 * 工具控制类
 * @author yy
 *
 */
@Api(tags={"tool"},value="工具",description="工具接口管理")
@RestController
@RequestMapping("/tool")
public class ToolController extends BaseController<Object,Long>{
	@Resource
	VerificationCode verificationCode;
	@Value("${myPugin.uploaderPath.rootPath}")
	String rootPath;
	@Value("${myPugin.uploaderPath.locationPath}")
	String locationPath;

	/**
	 * addUrl
	 * @return
	 * @throws InterruptedException
	 */
	@ApiOperation(value = "addUrl", notes = "addUrl")
	@RequestMapping(value = "/addUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public  String addUrl(
			@RequestParam("number") Integer number,
			@RequestParam("url") String url,
			HttpServletRequest request,
			HttpSession	 session
	) throws Exception{
		HashMap<String, Object> shm = SingletonHashMap.getInstance();
		Map<String,Integer> innerMap;
		if(ObjectUtils.isEmpty(shm.get("url"))){
			innerMap=new ConcurrentHashMap<>();
			shm.put("url",innerMap);
		}else{
			innerMap=(Map<String,Integer>)shm.get("url");
		}
		innerMap.put(url,number);
		return "success";
	}
	/**
	 * getUrl
	 * @return url
	 * @throws InterruptedException
	 */
	@ApiOperation(value = "getUrl", notes = "getUrl")
	@RequestMapping(value = "/getUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public  String getUrl(
			HttpServletRequest request,
			HttpSession	 session
	) throws Exception{
		HashMap<String, Object> shm = SingletonHashMap.getInstance();
		Set<String> innerSet;
		if(ObjectUtils.isEmpty(shm.get("urlip"))){
			innerSet=new ConcurrentSkipListSet<>();
			shm.put("urlip",innerSet);
		}else{
			innerSet=(Set<String>)shm.get("urlip");
		}
		boolean iscontains = innerSet.contains(IPCountUtil.getIpAddr(request));

		Map<String,Integer> innerMap;
		if(ObjectUtils.isEmpty(shm.get("url"))){
			innerMap=new ConcurrentHashMap<>();
			shm.put("url",innerMap);
		}else{
			innerMap=(Map<String,Integer>)shm.get("url");
		}
		String url = null;
		for (Map.Entry<String, Integer> entry : innerMap.entrySet()) {
			boolean isSuccess=false;
			while(!isSuccess){
				url=entry.getKey();
				Integer number = entry.getValue();
				if(number>0){
					if(!iscontains){
						innerSet.add(IPCountUtil.getIpAddr(request));
						number--;
					}
					shm.put(url,number);

					isSuccess=true;
					return url;
				}
			}
		}
			
		return url;
	}
	/**
	 * listUrl
	 * @return url
	 * @throws InterruptedException
	 */
	@ApiOperation(value = "listUrl", notes = "listUrl")
	@RequestMapping(value = "/listUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public   Object listUrl(
	) throws Exception{
		HashMap<String, Object> shm = SingletonHashMap.getInstance();
		return shm.get("url");
	}
	/**
	 * 验证码
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "验证码", notes = "验证码")
	@RequestMapping(value = "/getVerificationCode", method = {RequestMethod.GET,RequestMethod.POST})
	public void getVerificationCode(
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
			ByteArrayOutputStream vc = verificationCode.execute(session);
			response.getOutputStream().write(vc.toByteArray());
		return ;
	}
	/**
	 * 文件增加、修改
	 * @return
	 * @throws IOException 
	 */
	@ApiOperation(value = "上传文件", notes = "上传文件")
	@RequestMapping(value = "/file/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String addFile(
			@RequestParam("editorUpload") MultipartFile file,
			HttpServletRequest request,HttpSession session ) throws IOException  {
		String fileUrl = null;
		String filedir=DateUtil.getImgDir();
		try{
			fileUrl = FileUploadUtil.FormDataMerImgFileUpload(file, session,rootPath,locationPath,filedir);
		}catch (IOException e) {
			throw new IOException();
		}
		StringBuffer url=request.getRequestURL();
		String redirect_url = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
		return redirect_url+fileUrl;
	}
	/**
	 * 图片增加、修改
	 * @param file 上传图片
	 * @param width （可选）固定图片宽度
	 * @param height （可选）固定图片高度
	 * @param compression 默认0，原图不压缩，1压缩最优
	 * @return
	 * @throws IOException 
	 */
	@ApiOperation(value = "上传图片", notes = "上传图片")
	@RequestMapping(value = "/img/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String addAdvertiseImg(
			@RequestParam("editorUpload") MultipartFile file,
			@RequestParam(value="width",required=false) Integer width,
			@RequestParam(value="height",required=false) Integer height,
			@RequestParam(value="compression",required=false,defaultValue="0")Integer compression,
			HttpServletRequest request,HttpSession session ) throws IOException  {
		String imgUrl = null;
		String imgdir=DateUtil.getImgDir();
		Map<String ,Integer> kgmap=new HashMap<String,Integer>();
		kgmap.put("width", width);
		kgmap.put("height", height);
		kgmap.put("compression", compression);
		try{
			imgUrl = ThumbnailatorUtils.fileUpload(file, session,rootPath,locationPath,imgdir,kgmap);
		}catch (IOException e) {
			throw new IOException();
		}
		StringBuffer url=request.getRequestURL();
		String redirect_url = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
		//String redirect_url="http://118.123.15.27";
		return redirect_url+imgUrl;
	}
	/**
	 * 获取Session
	 * @return
	 */
	@ApiOperation(value = "获取sessionId", notes = "获取sessionId")
	@RequestMapping(value = "/getSession", method = {RequestMethod.GET,RequestMethod.POST})
	public String getSession(
			@RequestParam("createDate") Date createDate,
			HttpSession	 session
			){
		System.err.println(session.getAttribute("acount"));
		System.err.println(session.getAttribute("role"));
		System.err.println(session.getAttribute("finance"));
		this.logger.debug("\ngetId：\n{} ",session.getId());
		this.logger.info("\ngetId：\n{} ",session.getId());
		this.logger.warn("\ngetId：\n{} ",session.getId());
		//this.logger.error("\ngetId：\n{} ",session.getId());
		System.err.println(createDate);
		return session.getId();
		
	}
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	/**
	 * test
	 * @return
	 * @throws InterruptedException 
	 */
	@ApiOperation(value = "test", notes = "test")
	@RequestMapping(value = "/test", method = {RequestMethod.GET,RequestMethod.POST})
	public  String test(
			HttpServletRequest request,
			HttpSession	 session
			) throws Exception{
		BufferedReader br = request.getReader();
		String str, wholeStr = "";
		while((str = br.readLine()) != null){
			wholeStr += str;
		}
		JSONObject json = JSONObject.fromObject(wholeStr);

		return (String)json.get("orderId");
	}
	/**
	 * 根据url获取内容
	 * @return
	 * @throws InterruptedException
	 */
	@ApiOperation(value = "根据url获取内容", notes = "根据url获取内容")
	@ApiImplicitParams({
			@ApiImplicitParam(name="selector",value="选择器，如#id,.class",dataType="string", paramType = "query"),
			@ApiImplicitParam(name="url",value="请求url",dataType="string", paramType = "query"),
	})
	@RequestMapping(value = "/contentByUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList<List<String>> contentByUrl(
			@RequestParam("selector") String selector,
			@RequestParam("url") String url,
			HttpServletRequest request,
			HttpSession	 session
			) {
		// HtmlUnit 模拟浏览器
		/*WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
		webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
		webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setTimeout(10 * 1000);                   // 设置连接超时时间
		HtmlPage page = webClient.getPage(url);
		webClient.waitForBackgroundJavaScript(3* 1000);               // 等待js后台执行30秒
		String pageAsXml = page.asXml();
		// Jsoup解析处理
		//Document doc = Jsoup.parse(pageAsXml, url);
		Document doc = Jsoup.parse(pageAsXml);*/
		Document doc = null;
		try {
			Connection jp = Jsoup.connect(url);
			doc = jp.get();
		} catch (Exception e) {
			throw new CommonRollbackException("url只支持微信文章");
		}
		Element idd;
		if(selector.startsWith("#")){
			idd = doc.getElementById(selector.substring(1));
		}else if(selector.startsWith(".")){
			idd = doc.getElementsByClass(selector.substring(1)).get(0);
		}else{
			throw new CommonRollbackException("只支持id和class选择器");
		}
		StringBuffer urlbuffer=request.getRequestURL();
		String redirect_url = urlbuffer.delete(urlbuffer.length() - request.getRequestURI().length(), urlbuffer.length()).toString();
		//把远程图片变为base64
		//Elements pngs = idd.select("img[src]");
		Elements pngs = idd.select("img[data-src]");

		for (Element element : pngs) {
			//ReentrantLock reentrantLock=new ReentrantLock();
			//reentrantLock.lock();
			try {
				String imgUrl = element.attr("data-src");
				String imgdir=DateUtil.getImgDir();
				String fmt="jpg";
				if(imgUrl.indexOf("wx_fmt=")>=0 ){
					fmt=imgUrl.substring(imgUrl.indexOf("wx_fmt=")+7,imgUrl.indexOf("wx_fmt=")+10);
				    if(fmt.equals("jpe")){
				        fmt="jpg";
                    }
				}
				String filename=DateUtil.timeStamp()+(int)(Math.random()*1000)+"."+fmt;
				IMGIndivisibleUtil.imgPathFromUrl(imgUrl,rootPath+locationPath+"/"+imgdir,"/"+filename);

				element.attr("src",redirect_url+locationPath+"/"+imgdir+"/"+filename);
			}catch (Exception e){
				this.logger.info("e:",e);
			}finally {
				//reentrantLock.unlock();
			}
		}
		String h=idd.html();
		//Element style = doc.getElementsByTag("style").get(0);
		//h="<div id="+"\""+selector.substring(1)+"\""+">"+"<style>"+style.html()+"</style><style>"+selector+"{overflow-x: hidden;}"+selector+" img{max-width:100% !important;}</style>"+h+"</div>";
		h="<div id="+"\""+selector.substring(1)+"\""+">"+"<style>"+selector+"{overflow-x: hidden;}@media screen and (min-width:640px){ "+selector+"{ width:640px;margin:auto;}}"+selector+" img,video,iframe,iframe img,iframe video{max-width:100% !important;} </style>"+h+"</div>";
		List<String> list=new ArrayList<>();
		list.add(h);
		return ResultUtil.getSlefSRSuccessList(list);
	}
}
