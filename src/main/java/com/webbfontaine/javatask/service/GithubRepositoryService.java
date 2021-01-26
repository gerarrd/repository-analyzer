package com.webbfontaine.javatask.service;

import com.webbfontaine.javatask.helper.GithubRepositoryAnalysisHelper;

public interface GithubRepositoryService {
	public GithubRepositoryAnalysisHelper getRepositoryAnalysis(String fullName);
}
