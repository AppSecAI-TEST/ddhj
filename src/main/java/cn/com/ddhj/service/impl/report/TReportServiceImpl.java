package cn.com.ddhj.service.impl.report;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.ddhj.dto.BaseDto;
import cn.com.ddhj.dto.TReportDto;
import cn.com.ddhj.mapper.TLandedPropertyMapper;
import cn.com.ddhj.mapper.report.TReportEnvironmentLevelMapper;
import cn.com.ddhj.mapper.report.TReportMapper;
import cn.com.ddhj.mapper.report.TReportTemplateMapper;
import cn.com.ddhj.model.TLandedProperty;
import cn.com.ddhj.model.report.TReport;
import cn.com.ddhj.model.report.TReportEnvironmentLevel;
import cn.com.ddhj.model.report.TReportTemplate;
import cn.com.ddhj.result.report.PDFReportResult;
import cn.com.ddhj.result.report.TReportResult;
import cn.com.ddhj.service.ICityAirService;
import cn.com.ddhj.service.ITRubbishRecyclingService;
import cn.com.ddhj.service.IWaterQualityService;
import cn.com.ddhj.service.impl.BaseServiceImpl;
import cn.com.ddhj.service.report.ITReportService;
import cn.com.ddhj.util.PdfUtil;

/**
 * 
 * 类: TReportServiceImpl <br>
 * 描述: 环境报告业务逻辑处理接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年10月3日 下午1:58:12
 */
@Service
public class TReportServiceImpl extends BaseServiceImpl<TReport, TReportMapper, BaseDto> implements ITReportService {

	@Autowired
	private TReportMapper mapper;
	@Autowired
	private TReportTemplateMapper templateMapper;

	@Autowired
	private TReportEnvironmentLevelMapper levelMapper;
	@Autowired
	private TLandedPropertyMapper lpMapper;
	@Autowired
	private ICityAirService cityAirService;
	@Autowired
	private IWaterQualityService waterQualityService;
	@Autowired
	private ITRubbishRecyclingService rubbishService;

	/**
	 * 
	 * 方法: createPDF <br>
	 * 描述: TODO
	 * 
	 * @param array
	 * @return
	 * @see cn.com.ddhj.service.report.ITReportService#createPDF(com.alibaba.fastjson.JSONArray)
	 */
	@Override
	public PDFReportResult createPDF(String code, String path) {
		PDFReportResult result = new PDFReportResult();
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			String filePath = "report/" + code + ".pdf";
			path = path + "/" + filePath;
			// 根据code获取地产楼盘信息
			TLandedProperty lp = lpMapper.selectByCode(code);
			List<TReportTemplate> templateList = templateMapper.findReportTemplateAll();
			List<TReportEnvironmentLevel> levelList = levelMapper.findTReportEnvironmentLevelAll();
			// 获取绿地率等级
			int afforestLevel = 1;
			Double afforest = Double.valueOf(lp.getGreeningRate().substring(0, lp.getGreeningRate().indexOf("%")));
			if (afforest > 25 && afforest < 30) {
				afforestLevel = 2;
			} else if (afforest < 25) {
				afforestLevel = 3;
			}
			// 获取容积率等级
			int volumeLevel = 1;
			Double volume = Double.valueOf(lp.getVolumeRate().substring(0, lp.getVolumeRate().indexOf("元")));
			if (volume > 3 && volume < 5) {
				volumeLevel = 2;
			} else if (volume > 5) {
				volumeLevel = 3;
			}
			// 空气质量等级
			Integer airLevel = cityAirService.getAQILevel(lp.getCity());
			// 水质量等级
			Integer waterLevel = waterQualityService.getWaterLevel("北京密云古北口");
			// 垃圾设施等级
			Integer rubbishLevel = rubbishService.getRubbishLevel(lp.getCity(), lp.getLat(), lp.getLng());
			if (templateList != null && templateList.size() > 0) {
				JSONArray array = new JSONArray();
				for (int i = 0; i < templateList.size(); i++) {
					TReportTemplate model = templateList.get(i);
					JSONObject obj = new JSONObject();
					obj.put("title", model.getName());
					obj.put("content", model.getContent());
					if ("afforest".equals(model.getType())) {
						obj.put("level", getLevelContent(model.getType(), afforestLevel, levelList));
					} else if ("volume".equals(model.getType())) {
						obj.put("level", getLevelContent(model.getType(), volumeLevel, levelList));
					} else if ("air".equals(model.getType())) {
						obj.put("level", getLevelContent(model.getType(), airLevel, levelList));
					} else if ("water".equals(model.getType())) {
						obj.put("level", getLevelContent(model.getType(), waterLevel, levelList));
					} else if ("rubbish".equals(model.getType())) {
						obj.put("level", getLevelContent(model.getType(), rubbishLevel, levelList));
					} else {
						obj.put("level", getLevelContent(model.getType(), 1, levelList));
					}
					array.add(obj);
				}
				PdfUtil.instance().createPDF(array, path);
				result.setResultCode(0);
				result.setResultMessage("");
				result.setPath(filePath);
			} else {
				result.setResultCode(-1);
				result.setResultMessage("创建报告失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode(-1);
			result.setResultMessage("创建报告失败");
		}
		return result;
	}

	/**
	 * 
	 * 方法: getLevelContent <br>
	 * 描述: 获取环境等级描述信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年10月3日 下午2:27:42
	 * 
	 * @param type
	 * @param level
	 * @param list
	 * @return
	 */
	private static String getLevelContent(String type, Integer level, List<TReportEnvironmentLevel> list) {
		String content = "";
		if (list != null && list.size() > 0) {
			for (TReportEnvironmentLevel model : list) {
				if (type.equals(model.getType()) && level == model.getLevel()) {
					content = model.getContent();
					break;
				}
			}
		}
		return content;
	}

	/**
	 * 
	 * 方法: getReportData <br>
	 * 描述: TODO
	 * 
	 * @param dto
	 * @return
	 * @see cn.com.ddhj.service.report.ITReportService#getReportData(cn.com.ddhj.dto.TReportDto)
	 */
	@Override
	public TReportResult getReportData(TReportDto dto) {
		dto.setStart(dto.getPageIndex() * dto.getPageSize());
		TReportResult result = new TReportResult();
		String city = dto.getCity();
		if (city != null && !"".equals(city)) {
			List<TReport> list = mapper.findEntityAll(dto);
			if (list != null && list.size() > 0) {
				result.setList(list);
				Integer total = mapper.findEntityAllCount(dto);
				result.setTotal(total);
			} else {
				result.setList(new ArrayList<TReport>());
				result.setTotal(0);
			}
		} else {
			result.setResultCode(-1);
			result.setResultMessage("查询地点不能为空");
		}
		return null;
	}
}
