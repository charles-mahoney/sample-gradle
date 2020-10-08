package com.prft.sample.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("**/probes")
public class HealthChecks {

	@RequestMapping("/readiness")
	public String readiness() {
		return "Readiness Probe";
	}
	
	@RequestMapping("/liveness")
	public String liveness() {
		return "Liveness Probe";
	}
}
