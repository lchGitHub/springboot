package lac.job.schedule;

import java.util.List;
import java.util.UUID;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lac.job.bean.JobBean;
import lac.job.service.JobListService;
import lac.job.util.DmsSpringUtil;

/**
 * 
 * @ClassName: DmsScheduler
 * @Description: job执行
 * @author Chuan.lin
 * @date Jul 26, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */

/**
 * 现有job
 * 保险提醒 √
 * 保养提醒 √
 * 生日提醒 √
 * 高低储 √
 * 呆滞状态 √
 * ops传输 √
 * 会员卡状态 √
 */

@Component
public class DmsScheduler {
	
	private static Logger log = LoggerFactory.getLogger(DmsScheduler.class);

	@Autowired
	private JobListService jobListService;

	public void schedulerJob() throws SchedulerException {
		ApplicationContext annotationContext = DmsSpringUtil.getApplicationContext();
		StdScheduler stdScheduler = (StdScheduler) annotationContext.getBean("dmsSchedulerFactoryBean");
		Scheduler myScheduler = stdScheduler;
		List<JobBean> jobList = jobListService.getJobList();
		if (jobList != null && jobList.size() > 0) {
			for (JobBean bean : jobList) {
				log.info("=== JOB " + bean.getServiceName() + ":" + bean.getDesc() + " 注册成功 ===");
				try {
					startScheduler(myScheduler, bean.getServiceName(), bean.getCronExp());
				} catch (Exception e) {
					log.info("=== JOB " + bean.getServiceName() + ":" + bean.getDesc() + " 注册异常 ===");
				}
			}
		}
		myScheduler.start();
	}

	@SuppressWarnings("unchecked")
	public void startScheduler(Scheduler scheduler, String name, String cronExp) throws SchedulerException {
		String jobName = name + UUID.randomUUID();
		ApplicationContext annotationContext = DmsSpringUtil.getApplicationContext();
		JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) annotationContext.getBean(name).getClass()).withIdentity(jobName, jobName).build();
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobName).withSchedule(cronScheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

}
