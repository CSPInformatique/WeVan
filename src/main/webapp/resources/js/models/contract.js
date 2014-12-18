window.Contract = Backbone.Model.extend({
	idAttribute: "id",
	url : function() {
		var base = $("header").attr("data-context") + 'contract';
		if (this.isNew() || this.id == null) return base;
		
		base = base + (base.charAt(base.length - 1) == '/' ? '' : '/') + this.id;

		if(this.reset){
			base += "?reset";
		}

		return base;
	},
});

window.ContractPage = Backbone.Model.extend({
	model: Contract,
	url: function(){
		var base = $("header").attr("data-context") + "contract";
		if(this.branch == null){
			return base + ".json";
		}else{
			var url = $("header").attr("data-context") + "branch/" + this.branch + "/contract.json";
			
			url += "?page=" + this.page;
			url += "&results=" + this.results;

			if(this.status != null && this.status.length > 0){
				for(statusIndex in this.status){
					url += "&status=" + this.status[statusIndex];
				}
			}

			if(this.sortBy != null){
				url += "&sortBy=" + this.sortBy;
			}

			if(this.ascending != null){
				url += "&ascending=" + this.ascending;
			}
			
			return url;
		}
	}
});