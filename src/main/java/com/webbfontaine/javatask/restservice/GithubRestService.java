package com.webbfontaine.javatask.restservice;

import java.util.List;

import com.webbfontaine.javatask.model.GithubCommit;
import com.webbfontaine.javatask.model.GithubRepository;
import com.webbfontaine.javatask.model.GithubRepositorySearchResult;
import com.webbfontaine.javatask.model.GithubUser;

public interface GithubRestService {
	public GithubRepositorySearchResult getRepositories(String likeString);
	
	public GithubRepositorySearchResult getRepositories(String likeString, Long page, Long perPage);
	
	public GithubRepository getRepositoryDetails(String fullName);
	
	public List<GithubUser> getContributors(String fullName);
	
	public List<GithubCommit> getLastHundredCommits(String fullName);
}