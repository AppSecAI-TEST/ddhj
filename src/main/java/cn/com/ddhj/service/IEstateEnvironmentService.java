package cn.com.ddhj.service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public interface IEstateEnvironmentService {
	
	
	/**
	 * @descriptions 地区环境接口|1025
	 *
	 * @param position
	 * @param city |当前城市名称 如：北京，注意不是北京市
	 * @return
	 * @date 2016年10月7日 下午8:19:29
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject apiAreaEnv(String position , String city , ServletContext application);
	

	/**
	 * @descriptions 环境综合评分接口|1032
	 *
	 * @param position|经纬度逗号分隔
	 * @param title | 地产名称
	 * @param city |当前城市名称 如：北京，注意不是北京市
	 * @return
	 * @date 2016年10月4日 下午5:30:13
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */ 
	public JSONObject apiEnvScore(String position , String city , String radius , ServletContext application);
	
	
	
	/**
	 * @descriptions 楼盘列表|检索该经纬度附近10Km内的楼盘信息|1033
	 * 								 城市名称|北京，上海，广州，深圳
	 *
	 * @param position|经纬度逗号分隔
	 * @param city |当前城市名称 如：北京，注意不是北京市
	 * @param page | 当前第几页，每页数据20条
	 * @return
	 * @date 2016年10月4日 下午5:30:13
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */ 
	public JSONObject apiEstateList(String position , String city , String page , String count , String radius);
	
	
	/**
	 * @description: 手动刷新楼盘评分 | 接口号 2048
	 * 
	 * @author Yangcl 
	 * @date 2016年10月18日 下午4:22:47 
	 * @version 1.0.0.1
	 */
	public void resyncEstateScore();
	
	
	/**
	 * @description:  手动刷新水环境信息 | 接口号 2049 
	 *  
	 * @author Yangcl 
	 * @date 2016年11月3日 下午3:07:54 
	 * @version 1.0.0.1
	 */
	public void resyncWaterEnviroment(); 
	
	
	public JSONObject getFutureSevenAqi(String city , String area , String type);
	
	/**
	 * @description: 万年历接口 
	 * 
	 * @author Yangcl 
	 * @date 2016年12月27日 上午10:17:54 
	 * @version 1.0.0.1
	 */
	public JSONObject perpetualCalendar(ServletContext application);


	public JSONObject landedScoreAverage(String city, String type, String date, String year , Integer pageIndex ,  Integer pageSize); 
}
































