package com.june.vueService;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class SampleController {

	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Greetings from Spring Boot!";
    }
	
	@RequestMapping("/getData")
	@ResponseBody
	public String getData() {
		return "[{\"_id\": \"5a44566725149770946a7527\", \"id\": \"5a44566725149770946a7527\", \"stars\": \"9\", \"title\": \"Vue.js--\u8def\u7531\"}]]";
	}
}
