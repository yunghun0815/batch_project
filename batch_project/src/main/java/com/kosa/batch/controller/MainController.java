package com.kosa.batch.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.batch.dao.BatchDao;
import com.kosa.batch.model.BatchGroupVo;
import com.kosa.batch.scheduler.AgentJob;
import com.kosa.batch.service.impl.JobService;

import lombok.extern.slf4j.Slf4j;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String main(){
		return "main";
	}
}
