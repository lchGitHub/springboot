package lac.job.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lac.job.mapper.MntnExpirationDateMapper;

/**
 * 
 * @ClassName: MntnExpirationDateService
 * @Description: 客户关怀-保养提醒数据
 * @author Chuan.lin
 * @date Jul 30, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Service
public class MntnExpirationDateService implements Job {

	private static Logger log = LoggerFactory.getLogger(MntnExpirationDateService.class);
	
	@Autowired
	private MntnExpirationDateMapper mntnExpirationDateMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/**
		 * "逐行遍历车辆主数据，
			如果同时满足：
			1.MT_VEHICLE.DT_MNTN_EXPIRATION_DATE - 【业务参数：BP_V_MNTN_REMINDER_BE_DAYS】 < 当前系统时间
			2.提醒表中没有
			      （1）相同车辆GUID
			      （2）并且业务到期日与MT_VEHICLE.DT_MNTN_EXPIRATION_DATE相同
			      （3）并且提醒类型为“保养提醒（RC011）”
			  的数据。
			则向提醒表中插入一条数据
			(新数据的“业务到期日”为 MT_VEHICLE.DT_MNTN_EXPIRATION_DATE)
			(新数据的“提醒开始时间”为 当前系统时间)
			(新数据的“提醒终止时间”为 “业务到期日” + 【业务参数：BP_V_MNTN_REMINDER_AF_DAYS】)"

		 */
		log.info("=== 客户关怀-保养提醒数据 正在执行 ===");
		Long s = System.currentTimeMillis();
		mntnExpirationDateMapper.insertMntnExpirationReminder();
		log.info("=== 客户关怀-保养提醒数据 执行完毕，耗时" + (System.currentTimeMillis() - s) + "ms ===");
	}

}
