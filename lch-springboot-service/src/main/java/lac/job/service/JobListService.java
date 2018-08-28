package lac.job.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lac.job.bean.JobBean;
import lac.job.mapper.JobListMapper;

/**
 * 
 * @ClassName: JobListService
 * @Description: 获取Job List
 * @author Chuan.lin
 * @date Jul 30, 2018
 	 __           __               __                     
 	|  |.-----.--|  |.-----.-----.|  |_.-----.-----.-----.
 	|  ||  _  |  _  ||  -__|__ --||   _|  _  |     |  -__|
 	|__||_____|_____||_____|_____||____|_____|__|__|_____|
 */
@Service
public class JobListService {

	@Autowired
	private JobListMapper jobListMapper;

	public List<JobBean> getJobList() {
		return jobListMapper.getJobList();
	}

}
