window.User = Backbone.Model.extend({
	idAttribute: "username",
	url : function() {
		var base = $("header").attr("data-context") + 'user';
		if (this.isNew() || this.toJSON().username == null) return base;
		return base + (base.charAt(base.length - 1) == '/' ? '' : '/') + this.toJSON().username;
	},
	hasRole : function(role){
		var roles = this.toJSON().roles;
		for(var roleIndex in roles){
			var ownedRole = roles[roleIndex];
			
			if(ownedRole.name == role){
				return true;
			}
		}
		
		return false;
	}
});