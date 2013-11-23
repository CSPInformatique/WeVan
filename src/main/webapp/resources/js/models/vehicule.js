window.Vehicule = Backbone.Model.extend({
	idAttribute: "id",
	url : function() {
		var base = 'vehicule';
		if (this.isNew() || this.toJSON().id == 0) return base;
		return base + (base.charAt(base.length - 1) == '/' ? '' : '/') + this.id;
	},
});

window.VehiculeList = MultiSortCollection.extend({
	model: Vehicule,
	url: "vehicule.json"
});