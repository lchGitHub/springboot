package lac.job.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lac.job.mapper.McInstanceStatusMapper;

/**
 * 
 * @ClassName: McInstanceStatusService
 * @Description: 会员卡过期状态刷新
 * @author Chuan.lin
 * @date Jul 27, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Service
public class McInstanceStatusService implements Job {

	private static Logger log = LoggerFactory.getLogger(McInstanceStatusService.class);

	@Autowired
	private McInstanceStatusMapper mcInstanceStatusMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/**
		 * "逐行遍历会员卡实例(MT_MC_INSTANCE)表，
			对比该产品 如果 当前系统日期 > 有效期截止日(DT_EXPIRY_DATE)，就算作“会员卡过期”，刷新卡实例状态字段（ST_MC_INSTANCE_STATUS）成“MCIS005 已过期”状态。"
		 */
		log.info("=== 会员卡过期状态刷新 正在执行 ===");
		Long s = System.currentTimeMillis();
		mcInstanceStatusMapper.updateMcInstanceStatus();
		log.info("=== 会员卡过期状态刷新 执行完毕，耗时" + (System.currentTimeMillis() - s) + "ms ===");
	}

}
