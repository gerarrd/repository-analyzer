package com.webbfontaine.javatask.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class GithubRepository {
	private Long id;
	
	@JsonAlias("node_id")
	private String nodeId;
	
	private String name;
	
	@JsonAlias("full_name")
	private String fullName;
	
	private String description;
	
	private GithubUser owner;
	
	@JsonAlias("contributors_url")
	private String contributors;
	
	private String url;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GithubUser getOwner() {
		return owner;
	}

	public void setOwner(GithubUser owner) {
		this.owner = owner;
	}

	public String getContributors() {
		return contributors;
	}

	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}