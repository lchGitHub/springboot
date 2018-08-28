package lac.job.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JobController {

	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public List<Map<String, Object>> query() {
		return null;
	}
}
