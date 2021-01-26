function generateDailyCommitGraph(analysis) {
	var labels = [];
	var datasets = [];
	analysis.commitsPerDay.forEach(function(item, idx) {
		labels[idx] = item.date;
		datasets[idx] = item.count;
	});
	
	var ctx = document.getElementById("daily-commits-graph");
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			labels: labels,
			datasets: [{
				data: datasets,
				lineTension: 0,
				backgroundColor: 'transparent',
				borderColor: '#007bff',
				borderWidth: 4,
				pointBackgroundColor: '#007bff'
			}]
		},
		options: {
			scales: {
				yAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Count of commits'
					},
					ticks: {
						beginAtZero: false
					}
				}],
			},
			legend: {
				display: false,
			}
		}
	});
}

function loadRepo(fullName) {
	$("#search-result-container").hide();
	$("#loading-container").show();
	
	$.ajax({url: "repository-details?fullName="+fullName, success: function(result){
		$.ajax({url: "repository-analysis?fullName="+fullName, success: function(analysis){
			console.log(analysis);
			
			$("#repository-details-container").empty();
		
			$("#repository-details-container").append(
				$("<div class='border-bottom'>").append(
					$("<h2>").text(result.name),
					$("<h6>").html("<i>By "+result.owner.username+"</i>"),
					$("<p class='lead'>").text(result.description),
				),
				$("<canvas class='my-4 chartjs-render-monitor border-bottom' id='daily-commits-graph' width='1507' height='636' style='display: block; height: 424px; width: 1005px;'>")
			);
			
			generateDailyCommitGraph(analysis);
			
			var contributorDiv = $("<div class='row'>");
			
			$.each(analysis.contributors, function(i, contributor) {
				$("<div class='col-lg-4'>").append(
					$("<img class='rounded-circle float-left' src='"+contributor.avatarUrl+"' width='100' height='100'>"),
					$("<span class='f5 text-normal text-gray-light float-right'>").text("#"+i),
					$("<div style='margin: 10px 0px 0px 25%; font-size: 50px; font-weight: bolder; float: left;'>").text(contributor.commitImpact + "%"),
					$("<h3 style='clear: both'>").append(
						$("<a class='text-normal' href='#'>").text(contributor.username),
					),
					$("<p>").text("Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna.")
				).appendTo(contributorDiv);
			});
			
			contributorDiv.appendTo("#repository-details-container");
			
			$("#repository-details-container").show();
			$("#loading-container").hide();
		}});
	}});
}

$("#repo-search-input").on("keypress", function(e) {
	if(e.which == 13) {
		$("#repository-details-container").hide();
		$("#openning-container").hide();
		$("#search-result-container").hide();
		$("#loading-container").show();
		$("#repo-search-input").prop( "disabled", true );
		
		var queryString = $("#repo-search-input").val();
		$.ajax({url: "repositories?queryString="+queryString, success: function(result){
			$("#search-result-body").empty();
			
			$.each(result.items, function(i, item) {
				$("<tr>").append(
                    $("<td>").text(i+1),
                    $("<td>").append(
                    		$("<a href='#' onClick='loadRepo(\""+item.fullName+"\")'>")
                    		.text(item.name)
                    	),
                    $("<td>").text(item.owner.username)
                ).appendTo("#search-result-body");
			});
			
			$("#loading-container").hide();
			$("#search-result-container").show();
			$("#search-query-value").text(queryString);
			$("#repo-search-input").prop( "disabled", false );
		}});
	}
});

$(document).ready(function() {
	var bookmarks = localStorage.getItem("bookmarked-github-repositories") || "";
	var bookmarksArray = bookmarks.split(",");
	
	$.each(bookmarksArray, function(i, bookmark) {
		alert(bookmark);
	});
});