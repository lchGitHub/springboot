package lac.job.service;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lac.job.mapper.PrcMapper;

/**
 * 
 * @ClassName: OpsService
 * @Description: Ops传输
 * @author Chuan.lin
 * @date Jul 30, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Service
public class OpsService implements Job {
	
	private static Logger log = LoggerFactory.getLogger(OpsService.class);

	@Autowired
	private PrcMapper prcMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		GregorianCalendar ca = new GregorianCalendar();
		int ampm = ca.get(GregorianCalendar.AM_PM);
		Map<String, Integer> param = new HashMap<String, Integer>();
		if (ampm == 0) {
			param.put("in1", -1);
			param.put("in2", 1);
		}
		if (ampm == 1) {
			param.put("in1", 0);
			param.put("in2", 0);
		}
		log.info("=== Ops传输 正在执行 ===");
		Long s = System.currentTimeMillis();
		prcMapper.prcDwhTransfer(param);
		log.info("=== Ops传输 执行完毕，耗时" + (System.currentTimeMillis() - s) + "ms ===");
	}

}
