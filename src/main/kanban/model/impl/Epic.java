package main.kanban.model.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import main.kanban.util.Status;

public class Epic extends Task {

	private static final String PREFIX = "E-";
	private static long count;

	public Epic() {
		type = "EPIC";
		++count;
		id = PREFIX + count;
		status = Status.NEW;
	}

	public Epic(String name, String description) {
		type = "EPIC";
		++count;
		id = PREFIX + count;
		status = Status.NEW;
		this.name = name;
		this.description = description;
	}

	@JsonCreator
	public Epic(@JsonProperty("type") String type, @JsonProperty("id") String id, @JsonProperty("name") String name,
			@JsonProperty("description") String description, @JsonProperty("status") Status status) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	@Override
	@JsonValue
	public String toString() {
		return type + "," + id + "," + name + "," + description + "," + status;
	}
}
