window.ContractListView = Backbone.View.extend({
	addAdditionalDriver : function(driver){
		$(".editContract .none").hide();
		
		$(".editContract .additionalDrivers").append(
			_.template($('#additionalDriver-template').html())({
	    		driver : driver
    		})
		);
		
		$(".editContract .additionalDrivers button.remove").last().click(function(){
			$(this).closest(".form-inline").remove();
			
			if($(".editContract .additionalDrivers button.remove").size() == 0){
				$(".editContract .none").show();
			}
		});
	},
	
	closeModal: function(){
		$('.modal').modal('hide');
	},
	
	deleteContract: function(contractId){
		this.collection.get(contractId).destroy({
			wait : true
		});
	},
	
	editContract: function(contractId){
		this.openContract(this.collection.get(contractId).toJSON());
	},
	
	el : $('#contractList-container'),
	
    events : {
    	'click .delete button' : 'deleteContract',
    },
    
    initialize : function() {
    	this.collection.fetch();
    	
        this.template = _.template($('#contractList-template').html());

        /*--- binding ---*/
        _.bindAll(this, 'render');
        _.bindAll(this, 'openNewContract');
        _.bindAll(this, 'saveContract');
        this.collection.bind('change', this.render);
        this.collection.bind('add', this.render);
        this.collection.bind('remove', this.render);
        /*---------------*/
        
        $(".new button").click(this.openNewContract);
        $("button.save").click(this.saveContract);
    },
    
    openContract : function(contract){
    	var branchList = new BranchList();
    	branchList.fetch({async : false});

    	var vehiculeList = new VehiculeList();
    	var branchesSelect = $(".branches select");
    	if(branchesSelect.size() > 0){
    		vehiculeList.branch = branchesSelect.select2("val");
    	}else{
    		vehiculeList.branch = user.toJSON().branch.id;
    	}
    	vehiculeList.fetch({async : false});
    	
    	$('.editContract.modal .modal-body').html(
			_.template($('#newContract-template').html())({
	    		contract : contract,
	    		branchList : branchList.toJSON,
	    		vehiculeList : vehiculeList.toJSON()
    		})
    	);
    	
    	for(var driverIndex in contract.additionalDrivers){    		
    		this.addAdditionalDriver(contract.additionalDrivers[driverIndex]);
    	}
    	
    	$(".editContract .startDate input, .editContract .endDate input").each(function(index, element){
    		var dateInput = $(element);
    		dateInput.datetimepicker({format: 'yyyy-mm-dd hh:ii'});
    		if(dateInput.val() != ""){
    			dateInput.val(moment(parseInt($(element).val())).format("YYYY-MM-DD HH:mm"));
    		}
    	});
    	
    	$(".vehicule select").select2({ width: 'resolve' });
    	
    	$('.editContract.modal').modal({backdrop : 'static'});
    	
    	$("button.save").prop('disabled', false);
    	$("button.close").prop('disabled', false);
    	
    	var contractListView = this;
    	$(".editContract button.addDriver").click(function(){
    		contractListView.addAdditionalDriver({
    			id: 0,
    			firstName:"", 
    			lastName: "", 
    			driverLicense : ""
    		});
    	});
    },
    
    openContractActions: function(contract){
    	$('.contractActions.modal .modal-content').html(
			_.template($('#contractActions-template').html())({
	    		contract : contract
    		})
    	);
    	
    	$('.contractActions.modal').modal({backdrop : 'static'});
    	
    	var contractListView = this;
    	
    	$(".contractActions button.print").click(function(){
    		window.open(ctx + "/contract/" + $(this).attr("data-contract-id"));
    	});
    	
    	$(".contractActions button[data-status]").click(function(){
    		var contract = contractListView.collection.get($(this).attr("data-contract-id"));
    		contract.set("status", $(this).attr("data-status"));
    		contract.save();
        	$('.contractActions.modal').modal("hide");
    	});
    	
        $(".contractActions button.edit").click(function(){
        	$('.contractActions.modal').modal("hide");
        	contractListView.editContract($(this).attr("data-contract-id"));
        });
        
        $(".contractActions button.delete").click(function(){
        	$('.contractActions.modal').modal("hide");
        	contractListView.deleteContract($(this).attr("data-contract-id"));
        });
    },
    
    openNewContract: function(){
    	this.openContract({
    		id : 0,
    		branch : "",
    		driver : {
    			id : 0,
    			corporateName : "",
    			firstName : "",
    			lastName : "",
    			driverLicense : ""
    		},
    		startDate : "",
    		endDate : "",
    		kilometers : "",
    		totalAmount : "",
    		vehicule : null,
    		deductible : "",
    		deposit : "",
    		additionalDrivers : []
		});
    },
    	
    render : function(){ 
    	var contractList = this.collection.toJSON();
    	
    	var renderedContent = this.template({contractList : contractList});
        $($('#contractList-container')).html(renderedContent);
        
        var contractListView = this;
        
        $("table tbody tr").click(function(){
        	contractListView.openContractActions(contractListView.collection.get($(this).attr("data-contract-id")).toJSON());
        });
        
        $("table .startDate, table .endDate").each(function(index, element){
        	var dateInput = $(element);
        	dateInput.html(moment(parseInt(dateInput.html())).format("YYYY-MM-DD HH:mm"));
        });
        
        return this;
    },
    
    saveContract: function(){
    	$("button.save").prop('disabled', true);
    	$("button.close").prop('disabled', true);
    	
		var collection = this.collection;
		
		var additionalDrivers = [];
		$(".additionalDrivers .driver").each(function(index, element){
			additionalDrivers.push({
				id : $(this).attr("data-driver-id"),
				firstName : $(this).find("input.firstName").val(),
				lastName : $(this).find("input.lastName").val(),
				corporateName : "",
				driverLicense : $(this).find("input.driverLicense").val()
			});
		});
		
    	var branchesSelect = $(".branches select");
    	var branch = null;
    	if(branchesSelect.size() > 0){
    		branch = {id : branchesSelect.select2("val")};
    	}else{
    		branch = user.toJSON().branch;
    	}

		this.collection.create(
			{	id: $('.editContract input.id').val(),
				branch : branch,
				driver : {
					id : $(".editContract .driver input.id").val(),
					corporateName : $(".editContract .driver input.corporateName").val(),
					firstName : $(".editContract .driver input.firstName").val(),
					lastName : $(".editContract .driver input.lastName").val(),
					driverLicense : $(".editContract .driver input.driverLicense").val()
				},
				startDate : +moment($(".editContract input.startDate").val(), "YYYY-MM-DD HH:mm"),
				endDate : +moment($(".editContract input.endDate").val(), "YYYY-MM-DD HH:mm"),
				status : "OPEN",
				kilometers : $(".editContract input.kilometers").val(),
				totalAmount : $(".editContract input.totalAmount").val(),
				vehicule : {id : $(".editContract .vehicule select").select2("val")},
				deductible : $(".editContract input.deductible").val(),
				deposit : $(".editContract .deposit input").val(),
				additionalDrivers : additionalDrivers,
                options: $(".editContract .options textarea").val()
			},
			{	async : false,
				success : function(resp){
			        collection.fetch();
			        
			        $('.modal').modal('hide');
			    }
			}	
		);		
	},
});