package cn.com.ddhj.mapper;

import java.util.List;

import cn.com.ddhj.dto.BaseDto;
import cn.com.ddhj.model.TProductInfo;

public interface TProductInfoMapper extends BaseMapper<TProductInfo, BaseDto>{
 
	/**
	 * 
	 * 方法: getProductsByList <br>
	 * 描述: 根据集合参数查询商品列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2017年7月24日 上午9:41:50
	 * @param list
	 * @return
	 */
	List<TProductInfo> findProductsByList(List<TProductInfo> list);
}