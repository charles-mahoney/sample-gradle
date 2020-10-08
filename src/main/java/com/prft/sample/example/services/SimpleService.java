package com.prft.sample.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.prft.sample.example.components.DatabaseComponent;

@Service
public class SimpleService {

	@Autowired
	DatabaseComponent dbComponent;
	
	@Value("${ui.message}")
	String uiMessage;
	
	@Value("${ui.sharedMessage}")
	String uiSharedMessage;

	public String getUiMessage() {
		return uiMessage;
	}

	public void setUiMessage(String uiMessage) {
		this.uiMessage = uiMessage;
	}

	public String getUiSharedMessage() {
		return uiSharedMessage;
	}

	public void setUiSharedMessage(String uiSharedMessage) {
		this.uiSharedMessage = uiSharedMessage;
	}

	public DatabaseComponent getDbComponent() {
		return dbComponent;
	}

	public void setDbComponent(DatabaseComponent dbComponent) {
		this.dbComponent = dbComponent;
	}
	
	
	
	
}
