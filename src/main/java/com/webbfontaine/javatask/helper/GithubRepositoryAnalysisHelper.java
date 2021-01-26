package com.webbfontaine.javatask.helper;

import java.util.List;

import com.webbfontaine.javatask.model.GithubUser;

public class GithubRepositoryAnalysisHelper {
	public List<GithubCommitsPerDayHelper> commitsPerDay;
	public List<GithubUser> contributors;
	
	public GithubRepositoryAnalysisHelper() {}

	public List<GithubCommitsPerDayHelper> getCommitsPerDay() {
		return commitsPerDay;
	}

	public void setCommitsPerDay(List<GithubCommitsPerDayHelper> commitsPerDay) {
		this.commitsPerDay = commitsPerDay;
	}

	public List<GithubUser> getContributors() {
		return contributors;
	}

	public void setContributors(List<GithubUser> contributors) {
		this.contributors = contributors;
	}
}