package com.webbfontaine.javatask.restservice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.webbfontaine.javatask.model.GithubCommit;
import com.webbfontaine.javatask.model.GithubRepository;
import com.webbfontaine.javatask.model.GithubRepositorySearchResult;
import com.webbfontaine.javatask.model.GithubUser;
import com.webbfontaine.javatask.restservice.GithubRestService;

@Service
public class GithubRestServiceImpl implements GithubRestService {
	private final RestTemplate restTemplate;
	private final String searchRepoByNameURLTemplate;
	private final String getRepositoryDetailsURLTemplate;
	private final String getRepositoryContributorsURLTemplate;
	private final String getLastHundredCommitsURLTemplate;

	@Autowired
	public GithubRestServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
		searchRepoByNameURLTemplate = "https://api.github.com/search/repositories?q=%s is:public&per_page=%s&page=%s";
		getRepositoryDetailsURLTemplate = "https://api.github.com/repos/%s";
		getRepositoryContributorsURLTemplate = getRepositoryDetailsURLTemplate + "/contributors?per_page=100&";
		getLastHundredCommitsURLTemplate = getRepositoryDetailsURLTemplate + "/commits?per_page=100&merge=exclude";
	}

	public GithubRepository getRepositoryDetails(String fullName) {
		ResponseEntity<GithubRepository> response = restTemplate.exchange(
				String.format(getRepositoryDetailsURLTemplate, fullName), HttpMethod.GET, null, GithubRepository.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}

		return null;
	}

	@Override
	public List<GithubUser> getContributors(String fullName) {
		ResponseEntity<List<GithubUser>> response = restTemplate.exchange(
				String.format(getRepositoryContributorsURLTemplate, fullName), HttpMethod.GET, null, new ParameterizedTypeReference<List<GithubUser>>() {});

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}

		return null;
	}
	
	@Override
	public List<GithubCommit> getLastHundredCommits(String fullName) {
		ResponseEntity<List<GithubCommit>> response = restTemplate.exchange(
				String.format(getLastHundredCommitsURLTemplate, fullName), HttpMethod.GET, null, new ParameterizedTypeReference<List<GithubCommit>>() {});

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}

		return null;
	}

	@Override
	public GithubRepositorySearchResult getRepositories(String likeString, Long page, Long perPage) {
		ResponseEntity<GithubRepositorySearchResult> response = restTemplate.exchange(
				String.format(searchRepoByNameURLTemplate, likeString, perPage, page), HttpMethod.GET, null,
				GithubRepositorySearchResult.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}

		return null;
	}
}