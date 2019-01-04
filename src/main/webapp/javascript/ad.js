function serialize(obj) {
	var params = [];
	for(var key in obj) {
		if(obj[key] != null){
			params.push(encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]));
		}
	}
	return params.join("&");
}
function serializeJson(obj) {
	var params = [];
	for(var key in obj) {
		params.push("\""+encodeURIComponent(key) + "\"" + ":" + encodeURIComponent(obj[key]));
	}
	return "{"+params.join(",")+"}";
}
function serializeObject(obj) {
	var _obj = {};
	for(var key in obj) {
		if(null != obj[key]){
			_obj[key] = obj[key];
		}
	}
	return _obj;
}