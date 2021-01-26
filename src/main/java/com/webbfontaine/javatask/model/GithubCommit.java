package com.webbfontaine.javatask.model;

public class GithubCommit {
	private String sha;
	private GithubUser author;
	private GithubUser committer;
	private GithubCommitDetails commit;
	
	public GithubCommit() {}

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	public GithubUser getAuthor() {
		return author;
	}

	public void setAuthor(GithubUser author) {
		this.author = author;
	}

	public GithubUser getCommitter() {
		return committer;
	}

	public void setCommitter(GithubUser commiter) {
		this.committer = commiter;
	}

	public GithubCommitDetails getCommit() {
		return commit;
	}

	public void setCommit(GithubCommitDetails commit) {
		this.commit = commit;
	}
}