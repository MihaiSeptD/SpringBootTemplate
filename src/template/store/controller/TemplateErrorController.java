package template.store.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TemplateErrorController implements ErrorController {

	private final static Logger LOG = LoggerFactory.getLogger(TemplateErrorController.class);

	@RequestMapping("/error")
	@ResponseBody
	public String handleError(HttpServletRequest request) {
		Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
		String errorMessage = "Here we have an error " + (ex == null ? "N/A" : ex.getMessage()) + " with code " + code;

		LOG.info(errorMessage);
		return "Error code " + code + (ex == null ? "N/A" : ex.getMessage());
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
