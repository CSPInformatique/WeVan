window.Vehicule = Backbone.Model.extend({
	idAttribute: "username",
	url : function() {
		var base = ctx + '/user';
		if (this.isNew() || this.toJSON().id == 0) return base;
		return base + (base.charAt(base.length - 1) == '/' ? '' : '/') + this.username;
	},
});