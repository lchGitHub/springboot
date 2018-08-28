package lac.job.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lac.job.mapper.LowerTopLimitMapper;

/**
 * 
 * @ClassName: LowerTopLimitService
 * @Description: 物料超储状态刷新
 * @author Chuan.lin
 * @date Jul 27, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Service
public class LowerTopLimitService implements Job {
	
	private static Logger log = LoggerFactory.getLogger(LowerTopLimitService.class);

	@Autowired
	private LowerTopLimitMapper lowerTopLimitMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/**
		 * "逐行遍历库存表，
			对比【库存剩余量】与【物料主数据中的超限/低限值】，
			对比结果刷新到库存表超限状态字段"
		 */
		log.info("=== 物料超储状态刷新 正在执行 ===");
		Long s = System.currentTimeMillis();
		lowerTopLimitMapper.updateLimitStatus();
		log.info("=== 物料超储状态刷新 执行完毕，耗时" + (System.currentTimeMillis() - s) + "ms ===");
	}

}
