package lac.job.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lac.job.mapper.InsuExpirationDateMapper;

/**
 * 
 * @ClassName: InsuExpirationDateService
 * @Description: 车辆保险提醒
 * @author Chuan.lin
 * @date Jul 27, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Service
public class InsuExpirationDateService implements Job {
	
	private static Logger log = LoggerFactory.getLogger(InsuExpirationDateService.class);

	@Autowired
	private InsuExpirationDateMapper insuExpirationDateMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/**
		 * "逐行遍历车辆主数据，
			如果满足：
			1.MT_VEHICLE.DT_INSU_EXPIRATION_DATE为空，则
			      1.1如果MT_VEHICLE.DT_INSU_DATE也为空或MT_VEHICLE.DT_INSU_DATE >= 当前系统时间，
			         则不对该数据做任何处理
			      1.2如果MT_VEHICLE.DT_INSU_DATE < 当前系统时间,
			         则：
			             1.2.1算出 差距年数 = （当前系统时间 - MT_VEHICLE.DT_INSU_DATE）的年数向上取整
			             1.2.2更新该数据的 MT_VEHICLE.DT_INSU_EXPIRATION_DATE 
			                                       = MT_VEHICLE.DT_INSU_DATE + 差距年数
			2.如果MT_VEHICLE.DT_INSU_EXPIRATION_DATE < 当前系统时间
			         则：
			             2.1算出 差距年数 = （当前系统时间 - MT_VEHICLE.DT_INSU_EXPIRATION_DATE）的年数向上取整
			             2.2更新该数据的 MT_VEHICLE.DT_INSU_EXPIRATION_DATE 
			                                       = MT_VEHICLE.DT_INSU_EXPIRATION_DATE + 差距年数
			3.如果MT_VEHICLE.DT_INSU_EXPIRATION_DATE >= 当前系统时间,
			         则不对该数据做任何处理"
		
		 */
		log.info("=== 车辆保险提醒 正在执行 ===");
		Long s = System.currentTimeMillis();
		insuExpirationDateMapper.updateInsuExpirationDate();
		/**
		 * "逐行遍历车辆主数据，
			如果同时满足：
			1.MT_VEHICLE.DT_INSU_EXPIRATION_DATE - 【业务参数：BP_V_INSU_REMINDER_BE_DAYS】 < 当前系统时间
			2.提醒表中没有
			      （1）相同车辆GUID
			      （2）并且业务到期日与MT_VEHICLE.DT_INSU_EXPIRATION_DATE相同
			      （3）并且提醒类型为“保险提醒（RC012）”
			  的数据。
			则向提醒表中插入一条数据
			(新数据的“业务到期日”为 MT_VEHICLE.DT_INSU_EXPIRATION_DATE)
			(新数据的“提醒开始时间”为 当前系统时间)
			(新数据的“提醒终止时间”为 “业务到期日” + 【业务参数：BP_V_INSU_REMINDER_AF_DAYS】)"
		
		 */
		insuExpirationDateMapper.insertInsuExpirationReminder();
		log.info("=== 车辆保险提醒 执行完毕，耗时" + (System.currentTimeMillis() - s) + "ms ===");
	}

}
