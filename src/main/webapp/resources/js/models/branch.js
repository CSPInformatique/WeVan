window.Branch = Backbone.Model.extend({
	idAttribute: "id",
	url : function() {
		var base = ctx + '/branch';
		if (this.isNew() || this.id == null) return base;
		return base + (base.charAt(base.length - 1) == '/' ? '' : '/') + this.id;
	}
});

window.BranchList = MultiSortCollection.extend({
	model: Branch,
	url: "branch.json"
});