package com.webbfontaine.javatask.model;

public class GithubCommitDetails {
	private GithubCommitUser author;
	private GithubCommitUser committer;
	
	public GithubCommitDetails() {}

	public GithubCommitUser getAuthor() {
		return author;
	}

	public void setAuthor(GithubCommitUser author) {
		this.author = author;
	}

	public GithubCommitUser getCommitter() {
		return committer;
	}

	public void setCommitter(GithubCommitUser committer) {
		this.committer = committer;
	}
}
