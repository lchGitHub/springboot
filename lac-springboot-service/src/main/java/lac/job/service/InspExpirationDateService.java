package lac.job.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lac.job.mapper.InspExpirationDateMapper;

/**
 * 
 * @ClassName: InspExpirationDateService
 * @Description: 客户关怀-年检提醒数据
 * @author Chuan.lin
 * @date Jul 30, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Service
public class InspExpirationDateService implements Job {
	
	private static Logger log = LoggerFactory.getLogger(InspExpirationDateService.class);

	@Autowired
	private InspExpirationDateMapper inspExpirationDateMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/**
		 * "逐行遍历车辆主数据，
			如果满足：
			1.如果MT_VEHICLE.DT_INSP_EXPIRATION_DATE < 当前系统时间
			         则：
			             1.1算出 差距年数 = （当前系统时间 - MT_VEHICLE.DT_INSP_EXPIRATION_DATE）的年数向上取整
			             1.2如果差距年数是奇数，则差距年数=差距年数+1
			             1.3更新该数据的 MT_VEHICLE.DT_INSP_EXPIRATION_DATE 
			                                       = MT_VEHICLE.DT_INSP_EXPIRATION_DATE + 差距年数
			2.如果MT_VEHICLE.DT_INSP_EXPIRATION_DATE为空 或 MT_VEHICLE.DT_INSP_EXPIRATION_DATE >= 当前系统时间,
			         则不对该数据做任何处理"
		
		 */
		log.info("=== 客户关怀-年检提醒数据 正在执行 ===");
		Long s = System.currentTimeMillis();
		inspExpirationDateMapper.updateInspExpirationDate();
		/**
		 * "逐行遍历车辆主数据，
			如果同时满足：
			1.MT_VEHICLE.DT_INSP_EXPIRATION_DATE - 【业务参数：BP_V_INSP_REMINDER_BE_DAYS】 < 当前系统时间
			2.提醒表中没有
			      （1）相同车辆GUID
			      （2）并且业务到期日与MT_VEHICLE.DT_INSP_EXPIRATION_DATE相同
			      （3）并且提醒类型为“年检提醒（RC013）”
			  的数据。
			则向提醒表中插入一条数据
			(新数据的“业务到期日”为 MT_VEHICLE.DT_INSP_EXPIRATION_DATE)
			(新数据的“提醒开始时间”为 当前系统时间)
			(新数据的“提醒终止时间”为 “业务到期日” + 【业务参数：BP_V_INSP_REMINDER_AF_DAYS】)"
		
		 */
		inspExpirationDateMapper.insertInspExpirationReminder();
		log.info("=== 客户关怀-年检提醒数据 执行完毕，耗时" + (System.currentTimeMillis() - s) + "ms ===");
	}

}
