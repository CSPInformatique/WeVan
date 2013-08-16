window.Vehicule = Backbone.Model.extend({
	idAttribute: "id",
	url : function() {
		var base = 'vehicule';
		if (this.isNew()) return base;
		return base + (base.charAt(base.length - 1) == '/' ? '' : '/') + this.id;
	},
});

window.VehiculeList = Backbone.Collection.extend({
	model: Vehicule,
	url: "vehicule.json"
});