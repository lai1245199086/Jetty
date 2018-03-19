<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
<head> 
<meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
<title>A Click Event Test</title> 
<script type="text/javascript" src="assets/js/jquery.js"></script> 
<script type="text/javascript"> 
	$(function() {
		/* 点击事件会在超链接跳转前发生 */
		$("#div_test a").click(
				function() {
					var link = $(this).attr('href');
					$('#div_view').attr('src', link);
					var href = window.location.href;
					window.location.href = href.substr(0, href.indexOf('#'))
							+ '#' + link;
					return false;
				});
	});
</script> 
</head> 
<body> 
<div id="div_test"> 
<ol> 
<li><a href="http://www.jb51.net">jb51.net</a></li> 
<li><a href="http://s.jb51.net">server</a></li> 
<li><a href="http://sc.jb51.net">sc.jb51.net</a></li> 
</ol> 
</div> 
<iframe id="div_view" width="100%"></iframe> 
</body> 
</html> 
