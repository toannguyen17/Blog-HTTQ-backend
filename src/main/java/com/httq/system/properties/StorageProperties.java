package com.httq.system.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage", ignoreUnknownFields = false)
public class StorageProperties {
    private String location;

	public StorageProperties() {
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
