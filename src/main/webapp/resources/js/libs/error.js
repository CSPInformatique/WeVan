window.Error = Backbone.Model.extend({
	url : $("header").attr("data-context") + 'error'
});

/*
	1-	Build the onError event handler. The function will need to build an error object 
		and call the save method. The save will trigger a call to the Error Controller on
		the server side. It will be responsible to send an email alert reporting the
		error details.
		
	2-	If that save call is to encounter an error the onError callback as to be used to display
		the error mentionning that the server is unavailable. If the save call is sucessfull,
		the client needs to be redirected to the oups error page.
*/