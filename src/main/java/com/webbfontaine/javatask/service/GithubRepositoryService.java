package com.webbfontaine.javatask.service;

import com.webbfontaine.javatask.helper.GithubRepositoryAnalysisHelper;
import com.webbfontaine.javatask.helper.GithubRepositoryDatatableHelper;

public interface GithubRepositoryService {
	public GithubRepositoryDatatableHelper getRepositories(String search, Long offset, Long limit);
	
	public GithubRepositoryAnalysisHelper getRepositoryAnalysis(String fullName);
}
