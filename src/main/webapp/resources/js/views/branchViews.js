window.BranchesComboBoxView = Backbone.View.extend({
    el : ".branchesComboBox-container",

	initialize : function() {
	    this.template = _.template($("#branchesComboBox-template").html());
	    
	    /*--- binding ---*/
	    _.bindAll(this, "render");
	    this.collection.bind("change", this.render);
	    this.collection.bind("add", this.render);
	    this.collection.bind("remove", this.render);
	    /*---------------*/
	},
	        
	render : function(){
		var renderedContent = this.template({branchesList : this.collection.toJSON()});
		
	    $(this.el).html(renderedContent);
	    
	    $(this.el).find("select").select2({width: 'resolve' });
	    
	    return this;
	}
});