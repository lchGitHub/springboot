package lac.job.listener;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import lac.job.schedule.DmsJobFactory;
import lac.job.schedule.DmsScheduler;

/**
 * 
 * @ClassName: JobSchedulerListener
 * @Description: Job监听
 * @author Chuan.lin
 * @date Jul 27, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Configuration
public class JobSchedulerListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	DmsScheduler dmsScheduler;

	@Autowired
	DmsJobFactory dmsJobFactory;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			dmsScheduler.schedulerJob();
			System.out.println("SynchronizedData job  start...");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Bean(name = "dmsSchedulerFactoryBean")
	public SchedulerFactoryBean mySchedulerFactory() {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(1);
		bean.setJobFactory(dmsJobFactory);
		return bean;
	}

}
