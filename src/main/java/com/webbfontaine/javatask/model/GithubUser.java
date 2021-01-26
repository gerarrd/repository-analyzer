package com.webbfontaine.javatask.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class GithubUser {
	private Long id;
	
	@JsonAlias("login")
	private String username;
	
	@JsonAlias("avatar_url")
	private String avatarUrl;
	
	private int commitCount;
	
	private int commitImpact;
	
	public GithubUser() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public int getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}
	
	public void incrementCommitCount() {
		this.commitCount += 1;
	}

	public int getCommitImpact() {
		return commitImpact;
	}

	public void setCommitImpact(int commitImpact) {
		this.commitImpact = commitImpact;
	}
	
	public void adjustImpact(int totalCommits) {
		this.commitImpact = (int)Math.abs((new Double(commitCount) / new Double(totalCommits)) * 100);
	}
}