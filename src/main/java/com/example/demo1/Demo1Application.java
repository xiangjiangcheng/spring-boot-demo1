package com.example.demo1;

import com.example.demo1.utils.BusiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@RestController
public class Demo1Application extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory.getLogger(Demo1Application.class);

	@RequestMapping("/")
	public String home() {
		Date now = new Date();
		logger.info("url:{},time:{},", "/", now);
		return "hello 朋友";
	}

	@RequestMapping("/user/{id}")
	public String getUser(@PathVariable("id") Integer id) {
		if (id == null) {
			throw new BusiException(500, "参数错误");
		}

		Date now = new Date();
		logger.info("url:{},time:{},id:{}", "/user/{id}", now, id);
		return "user:...........";
	}

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Demo1Application.class);
	}
}
