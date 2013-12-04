window.VehiculeListView = Backbone.View.extend({
	closeModal: function(){
		$('.modal').modal('hide');
	},
	
	deleteVehicule: function(e){
		this.collection.get($(e.currentTarget).data('id')).destroy({
			wait : true
		});
	},
	
	editVehicule: function(vehiculeId){
		this.openVehicule(this.collection.get(vehiculeId).toJSON());
	},
	
	el : $('#vehiculeList-container'),
	
    events : {
    	'click .delete button' : 'deleteVehicule',
    },
    
    initialize : function() {
    	this.collection.fetch();
    	
        this.template = _.template($('#vehiculeList-template').html());

        /*--- binding ---*/
        _.bindAll(this, 'render');
        _.bindAll(this, 'openNewVehicule');
        _.bindAll(this, 'saveVehicule');
        this.collection.bind('change', this.render);
        this.collection.bind('add', this.render);
        this.collection.bind('remove', this.render);
        /*---------------*/
        
        $(".new button").click(this.openNewVehicule);
        $("button.save").click(this.saveVehicule);
    },
    
    openVehicule : function(vehicule){
    	var branchList = new BranchList();
    	branchList.fetch({async : false});
    	
    	var branchesSelect = $(".branches select");
    	var branch = null;
    	if(branchesSelect.size() > 0){
    		branch = branchesSelect.select2("val");
    	}else{
    		branch = user.toJSON().branch.id;
    	}
    	
    	$('.modal .modal-body').html(
			_.template($('#newVehicule-template').html())({
	    		vehicule : vehicule,
	    		branchList : branchList.toJSON(),
	    		selectedBranch : branch
    		})
    	);
    	
    	$('.modal').modal({backdrop : 'static'});
    	
    	$("button.save").prop('disabled', false);
    	$("button.close").prop('disabled', false);
    },
    
    openNewVehicule: function(){
    	this.openVehicule({
    		id : 0,
			name : '',
			number : '',
			branch : '',
			registration : ''
		});
    },
    	
    render : function(){;
    	this.collection.sortBy('branch', 'name', 'number', 'model'); 
    	var vehiculeList = this.collection.toJSON();
    	
    	var renderedContent = this.template({vehiculeList : vehiculeList});
    	$('#vehiculeList-container').html(renderedContent);
        
    	$('#vehiculeList-container').find("select.branch").select2();
        
        var vehiculeListView = this;
        $(".edit button").click(function(){
        	vehiculeListView.editVehicule($(this).attr("data-id"));
        });

        $("table").floatThead({
        	scrollingTop: 50,
        	useAbsolutePositioning: false
        });
        
        return this;
    },
    
    saveVehicule: function(){
    	$("button.save").prop('disabled', true);
    	$("button.close").prop('disabled', true);
    	
		var collection = this.collection;
		
		this.collection.create(
			{	id: $('form input.id').val(),
				branch: {id : $('form .branch select').val()},
				name: $('form .name input').val(),
				number: $('form .number input').val(),
				model: $('form .model input').val(),
				registration: $('form .registration input').val()
			},
			{	wait : true,
				success : function(resp){
			        collection.fetch();
			        
			        $('.modal').modal('hide');
			    }
			}	
		);		
	},
});