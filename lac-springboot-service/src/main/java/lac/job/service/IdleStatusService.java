package lac.job.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lac.job.mapper.IdleStatusMapper;

/**
 * 
 * @ClassName: IdleStatusService
 * @Description: 物料呆滞状态刷新
 * @author Chuan.lin
 * @date Jul 27, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Service
public class IdleStatusService implements Job {
	
	private static Logger log = LoggerFactory.getLogger(IdleStatusService.class);

	@Autowired
	private IdleStatusMapper idleStatusMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/**
		 * "逐行遍历库存表，
			对比该产品【当前系统时间 - 最后一次出库凭证的系统时间】(换算成天数)与【物料主数据中的呆滞时长】，如果前者大于后者就算作“呆滞”，否则算“正常”
			对比结果刷新到库存表呆滞状态字段"
		 */
		log.info("=== 物料呆滞状态刷新 正在执行 ===");
		Long s = System.currentTimeMillis();
		idleStatusMapper.updateIdleStatus();
		log.info("=== 物料呆滞状态刷新 执行完毕，耗时" + (System.currentTimeMillis() - s) + "ms ===");
	}

}
