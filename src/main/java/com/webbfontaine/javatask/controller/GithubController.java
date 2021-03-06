package com.webbfontaine.javatask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webbfontaine.javatask.helper.GithubRepositoryAnalysisHelper;
import com.webbfontaine.javatask.helper.GithubRepositoryDatatableHelper;
import com.webbfontaine.javatask.model.GithubRepository;
import com.webbfontaine.javatask.restservice.GithubRestService;
import com.webbfontaine.javatask.service.GithubRepositoryService;

@RestController
public class GithubController {
	private final GithubRestService githubRestService;
	private final GithubRepositoryService githubRepositoryService;
	
	@Autowired
	public GithubController(GithubRestService githubRestService, GithubRepositoryService githubRepositoryService) {
		this.githubRestService = githubRestService;
		this.githubRepositoryService = githubRepositoryService;
	}

	@GetMapping("list-repositories")
	public GithubRepositoryDatatableHelper getRepositories(
		@RequestParam(value="search", required=false, defaultValue="") String search,
		@RequestParam(value="offset", required=false, defaultValue="0") Long offset,
		@RequestParam(value="limit", required=false, defaultValue="10") Long limit
	) {
		return githubRepositoryService.getRepositories(search, offset, limit);
	}
	
	@GetMapping("repository-details")
	public GithubRepository getRepositoryDetails(@RequestParam("fullName") String fullName) {
		return githubRestService.getRepositoryDetails(fullName);
	}
	
	@GetMapping("repository-analysis")
	public GithubRepositoryAnalysisHelper getRepositoryAnalysis(@RequestParam("fullName") String fullName) {
		return githubRepositoryService.getRepositoryAnalysis(fullName);
	}
}