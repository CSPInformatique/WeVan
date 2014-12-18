$(document).ready(function(){
	var temp = jQuery.event.dispatch;
	jQuery.event.dispatch  = function () {
		try {
			temp.apply(this, arguments);
		} catch (exception) {
			notifyError(exception);
		}
	}
});

//setup ajax error handling
$(document).ajaxError(function(event,xhr,options,exc){
	notifyError(exc);
});

var notifyError = function(exception){
	var jsError = {};
	jsError.message = exception.message;
	jsError.lineNumber = exception.lineNumber;
	jsError.fileName = exception.fileName;
	jsError.columnNumber = exception.columnNumber;
	//jsError.userAgent = $('#UserAgent').val(navigator.userAgent);
	//jsError.userOs = $('#UserAgent').val(navigator.oscpu);

	$.ajax({
		url : $("header").attr("data-context") + "error.json",
		type : "PUT",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(jsError)
	}).done(function(data){
		// Inform of the error and explain that technical support has been informed.
		$(".modal.generic-error").modal({backdrop : "static"});
		$(".modal.generic-error .clientError").show();
		$(".modal.generic-error .serverUnreachable").hide();
		throw exception;
		
	}).fail(function(){
		// Display the server unavailable error. 
		$(".modal.generic-error").modal({backdrop : "static"});
		$(".modal.generic-error .clientError").hide();
		$(".modal.generic-error .serverUnreachable").show();

		throw exception;
	})
};