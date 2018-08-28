package lac.job.bean;

public class JobBean {

	private String cronExp;
	private String serviceName;
	private String desc;

	public String getCronExp() {
		return cronExp;
	}

	public void setCronExp(String cronExp) {
		this.cronExp = cronExp;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
