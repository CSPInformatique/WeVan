window.VehiculeListView = Backbone.View.extend({
	deleteVehicule: function(e){
		this.collection.get($(e.currentTarget).data("id")).destroy({
			wait : true
		});
	},
	
	el : $("#vehiculeList-container"),
	
    events : {
        "click .delete" : "deleteVehicule",
        "click .newVehicule button" : "saveNewVehicule"
    },
    
    initialize : function() {
    	this.collection.fetch();
        this.template = _.template($("#vehiculeList-template").html());

        /*--- binding ---*/
        _.bindAll(this, "render");
        this.collection.bind("change", this.render);
        this.collection.bind("add", this.render);
        this.collection.bind("remove", this.render);
        /*---------------*/
        
        $(".vehicules > button").click(this.newVehicule);
    },
    
    newVehicule: function(){
    	$(".vehicules table").append($("#newVehicule-template").html());
    },
    
	saveNewVehicule: function(){
		var collection = this.collection;
		this.collection.create(
			{	id: null,
				branch: $(".newVehicule .branch input").val(),
				name: $(".newVehicule .name input").val(),
				model: $(".newVehicule .model input").val(),
				registration: $(".newVehicule .registration input").val()
			},
			{	wait : true,
				success : function(resp){
			        collection.fetch();
			    }
			}	
		);		
	},
    	
    render : function(){;
    	var renderedContent = this.template({vehiculeList : this.collection.toJSON()});
        $(this.el).html(renderedContent);
        return this;
    }
});