window.Contract = Backbone.Model.extend({
	idAttribute: "id",
	url : function() {
		var base = ctx + '/contract';
		if (this.isNew() || this.id == null) return base;
		return base + (base.charAt(base.length - 1) == '/' ? '' : '/') + this.id;
	},
});

window.ContractList = MultiSortCollection.extend({
	model: Contract,
	url: function(){
		var base = ctx + "/contract";
		if(this.branch == null){
			return base + ".json";
		}else{
			var url = ctx + "/branch/" + this.branch + "/contract.json";
			
			if(this.status != null && this.status.length > 0){
				
				for(statusIndex in this.status){
					if(statusIndex == 0){
						url += "?";
					}else{
						url += "&";
					}
					url += "status=" + this.status[statusIndex];
				}
			}
			
			return url;
		}
	}
});