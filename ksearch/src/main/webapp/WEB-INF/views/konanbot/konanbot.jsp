<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>konanbot</title>
	<meta charset="utf-8">
	<meta name="referrer" content="always">
	<meta property="og:type" content="website">
	<meta property="og:title" content="konanbot">
<!-- 	<link type="text/css" rel="stylesheet"  href="resources/js/jquery-ui-1.12.1.custom/jquery-ui.css"> -->
	<link type="text/css" rel="stylesheet"  href="resources/konanbot/js/bootstrap/css/bootstrap.min.css">
	<link type="text/css" rel="stylesheet"  href="resources/konanbot/js/font-awesome/css/font-awesome.css">
	<link type="text/css" rel="stylesheet"  href="resources/konanbot/chat/reset.css">
	<link type="text/css" rel="stylesheet"  href="resources/konanbot/chat/konanbot-style.css">

<style type="text/css">
    .chat-container {height:100%; margin: 0 auto;}
    #chat-message {height: calc(100% - 140px);}
    .chat-wrap {height: 100%;}
    .chat-wrap .chat {height: 100%;}
  </style>
<!--     <script type="text/javascript" src="resources/js/jquery-ui-1.12.1.custom/external/jquery/jquery-3.4.1.js"></script> -->
<!--     <script type="text/javascript" src="resources/js/jquery-ui-1.12.1.custom/jquery-ui.js"></script>jQuery UI -->
    <script type="text/javascript" src="resources/konanbot/js/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/konanbot/js/jsrender/jsrender.js"></script>
    <script type="text/javascript" src="resources/konanbot/js/templateRenderer.js"></script>
    <script type="text/javascript" src="resources/konanbot/js/chat/main.js"></script>
	<script type="text/javascript">
    var contextpath ="";
    var CONTEXTPATH = "", MSG_SPEAK_MESSAGE = "please speak a message.";

	var openChatWindow = function() {
	    $("#chat-title").text("konanbot");
	    //$("#chat-message").show();
	    console.log("call");
	    chat.init("","","");
	};


	$(document).ready(function() {

	  $("#chatLayer .modal-dialog").draggable({
	        handle : ".header"
	    });


	    $("#chatLayer").on("shown.bs.modal", function(event) {
	        $("#chat-history > ul").empty();
	        $("#chat-message").show();
	       // chat.init(domain, user, scenario);
	       openChatWindow();
	       return false;
	    });
	});
	</script>
	    <script type="text/javascript" src="resources/konanbot/js/chat/speech.js "></script>
</head>
<body>
<div class="modal" id="chatLayer" tabindex="-1" role="dialog" style="display: none;">
       <div class="modal-dialog chat-dialog chat-container ui-draggable" role="document" style="left: 285px; top: 0px;">
           <!-- CHAT-MESSAGE  -->
           <div id="chat-message" class="header">
               <div class="head">
                   <h1 class="h1"><a id="chat-title" class="modal-title ellipsis ellipsis-a">common</a><span>admin</span></h1>
                   <ul>
                       <li><a class="ico-out" data-dismiss="modal" aria-label="Close"></a></li>
                   </ul>
               </div>
               <div class="chat-wrap">
                   <div class="chat" id="chat-history">
                       <ul class="message"></ul>
                   </div>
               </div>
               <div class="footer">
                   <div class="write-form">
                       <textarea id="chat-textarea" rows="1" placeholder="Type a message..."></textarea>
                       <span class="send" id="chat-send"><span></span></span>
                   </div>
               </div>
           </div>
       </div>
    </div>
</body>
</html>