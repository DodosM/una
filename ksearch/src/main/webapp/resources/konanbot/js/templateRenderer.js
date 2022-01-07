(function($) {
	$.templateRenderer = function(filename, renderData, contextpath) {
		/*
		if(!contextpath) contextpath = CONTEXTPATH;
		$.extend(renderData, {
			"contextpath" : contextpath
		});
  */
		var content = $.ajax({
	    	url: "resources/konanbot/js/tmpl/"+filename+".tmpl",
	        dataType:"text",
	        async : false,
	        type:"get",
	        processData:false
	    });

		var template = $.templates(content.responseText);
		return template.render(renderData);
	};

	$.getTemplateStr = function(filename, contextpath) {
		//if(!contextpath) contextpath = CONTEXTPATH || "./";
		var content = $.ajax({
	    	url:"resources/konanbot/js/tmpl/"+filename+".tmpl",
	        dataType:"text",
	        async : false,
	        type:"get",
	        processData:false
	    });
		return $.templates(content.responseText);
	};

	$.registTemplateStr = function(registName, filename, contextpath) {
		//if(!contextpath) contextpath = CONTEXTPATH || "./";
		var content = $.ajax({
	    	url:"resources/konanbot/js/tmpl/"+filename+".tmpl",
	        dataType:"text",
	        async : false,
	        type:"get",
	        processData:false
	    });
		return $.templates(registName, content.responseText);
	};

	$.dialogResponse = function(contents, tmpl) {
		if(!tmpl)
			tmpl = $.getTemplateStr("dialog-response");

		var data = {
			contents : contents,
			contextpath : CONTEXTPATH,
			functionName : "chat.sendRichMessage",
			botBubbleId : $("li.bot").length + 1
		};
		var $dialogResponse = $(tmpl.render(data));
		return $dialogResponse;
	};

	$.views.converters({
		escape: function(val) {
			return escape(val);
		},
		imagesize: function(w, h, maxW) {
			var rtnW = w;
			var rtnH = h;
			if(maxW && w > maxW) {
				rtnW = maxW;
				rtnH = h*maxW/w;
				return "width=" + maxW + " height=" + h*maxW/w;
			}
			return "";
		}
	});
}(jQuery));