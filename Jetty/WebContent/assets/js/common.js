//
function writePage(html) {
	window.document.open();
	window.document.write(html);
	window.document.close();
}
function post2SRV(url, params) {
	params = resolveParams(params);
	console.log("url>> " + url);
	console.log("params>> " + params);
	// 加载等待层
	var index = layer.load(0, {shade : [ 0.2, '#B3B3B3' ]}); // 0代表加载的风格，支持0-2
	$.post(url, params, function(data) {
		var resultType = (typeof data);
		if(resultType=='object'){
			if (null != data.AjaxError && "" != data.AjaxError)
				layer.msg(data.AjaxError);
			console.log("Server Data >> " + JSON.stringify(data));
		}else if (typeof data == 'string') writePage(data);
	}).error(function(data) {
		console.log("AjaxError Loaded: " + data.AjaxError);
		console.log("请求失败后");
	}).complete(function(data) {
		console.log("请求完成后");
		layer.close(index);
	});
}
function resolveParams(params){
	return params;
}

