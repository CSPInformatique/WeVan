window.Vehicule = Backbone.Model.extend({
	idAttribute: "id",
	url : function() {
		var base = $("header").attr("data-context") + 'vehicule';
		if (this.isNew() || this.id == null) return base;
		return base + (base.charAt(base.length - 1) == '/' ? '' : '/') + this.id;
	},
});

window.VehiculeList = MultiSortCollection.extend({
	model: Vehicule,
	url: function(){
		var base = $("header").attr("data-context") + "vehicule";
		if(this.branch == null){
			return base + ".json";
		}else{
			return $("header").attr("data-context") + "branch/" + this.branch + "/vehicule.json";
		}
	}
});