<%@ page contentType="text/html"%>
<%@ page pageEncoding="iso-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="../css/chosen.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.pack.js"></script>
<script src="../js/chosen.jquery.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/selectchart.js"></script>
<script type="text/javascript">
function destroy(){
	$('#container').highcharts() == undefined ? 'undefined' : $('#container').highcharts().destroy();
}
$(document).ready(function(){
	$('#realtimemetrics').change(function() {
		destroy();
		loadRealtimeChart($('#realtimemetrics').val());
		});
	$('#avgdurationcategory').click(function() {
		destroy();
		loadBatchLineChart("avgdurationcategories");
		});
	$('#avgdurationchannel').click(function() {
		destroy();
		loadBatchLineChart("avgdurationchannels");
		});
	$('#top10channels').click(function() {
		destroy();
		loadBarChart("Top 10 Channels", "top10channels", "Viewers");
		});
	$('#top10categories').click(function() {
		destroy();
		loadBarChart("Top 10 Categories", "top10categories", "Viewers");
		});
	$('#topchannelads').click(function() {
		destroy();
		loadBarChart("Channels With Most Ads", "topchannelads", "Ads");
		});
	$('#audiencepertype').click(function() {
		destroy();
		loadPieChart("Audience Per Type", "audiencepertype");
		});
	$('#audienceperfg').click(function() {
		destroy();
		loadPieChart("Audience Per Family Group", "audienceperfg");
		});
	$('#worstshows').click(function() {
		destroy();
		loadBarChart("Worst Shows", "worstshows", "People that Changed Channel");
		});
});
</script>
</head>
<body>
	<div class="navbar container">
	    <div class="navbar-inner">
	    <a class="brand" href="/bigdata/bin/index">BigData-Pink Elephant TV</a>
	    <ul class="nav">
	    </ul>
	    </div>
	</div>
	<div class="container">
	
