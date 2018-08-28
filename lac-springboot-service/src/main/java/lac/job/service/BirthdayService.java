package lac.job.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lac.job.mapper.BirthdayMapper;

/**
 * 
 * @ClassName: BirthdayService
 * @Description: 生日提醒
 * @author Chuan.lin
 * @date Jul 30, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Service
public class BirthdayService implements Job {

	private static Logger log = LoggerFactory.getLogger(BirthdayService.class);
	
	@Autowired
	private BirthdayMapper birthdayMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		log.info("=== 生日提醒 正在执行 ===");
		Long s = System.currentTimeMillis();
		birthdayMapper.insertBirthdayReminder();
		log.info("=== 生日提醒 执行完毕，耗时" + (System.currentTimeMillis() - s) + "ms ===");
	}

}
