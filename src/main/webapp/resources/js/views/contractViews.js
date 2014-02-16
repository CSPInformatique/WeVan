window.ContractPageView = Backbone.View.extend({
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
		new Contract(this.getContractFromContent(contractId)).destroy({
			wait : true
		});
	},
	
	editContract: function(contractId){
		this.openContract(this.getContractFromContent(contractId));
	},
	
	el : $('#contractPage-container'),
	
    events : {
    	'click .delete button' : 'deleteContract',
    },

    getContractFromContent : function(contractId){
        contractId = parseInt(contractId);
        var contract;

        var content = this.model.get("content");

        for(contractIndex in content){
            var currentContract = content[contractIndex];
            if(contractId == currentContract.id){
                contract = currentContract;
            }
        }

        return contract;
    },
    
    initialize : function() {
    	this.model.fetch();
    	
        this.template = _.template($('#contractPage-template').html());

        /*--- binding ---*/
        _.bindAll(this, 'render');
        _.bindAll(this, 'openNewContract');
        _.bindAll(this, 'saveContract');
        this.model.bind('change', this.render);
        this.model.bind('add', this.render);
        this.model.bind('remove', this.render);
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
    	
    	var contractPageView = this;
    	$(".editContract button.addDriver").click(function(){
    		contractPageView.addAdditionalDriver({
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
    	
    	var contractPageView = this;
    	
    	$(".contractActions button.print").click(function(){
    		window.open(ctx + "/contract/" + $(this).attr("data-contract-id"));
    	});
    	
    	$(".contractActions button[data-status]").click(function(){
    		contract.status = $(this).attr("data-status");

            new Contract(contract).save({}, {
                success : function(){
                    contractPageView.refresh();
                }
            });

           

        	$('.contractActions.modal').modal("hide");
    	});
    	
        $(".contractActions button.edit").click(function(){
        	$('.contractActions.modal').modal("hide");
        	contractPageView.editContract($(this).attr("data-contract-id"));
        });
        
        $(".contractActions button.delete").click(function(){
        	$('.contractActions.modal').modal("hide");
        	contractPageView.deleteContract($(this).attr("data-contract-id"));
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

    refresh : function(){
        this.model.fetch();
        this.render();
    },
    	
    render : function(){ 
    	var page = this.model.toJSON();
    	
    	var renderedContent = this.template({page : page});
        $($('#contractPage-container')).html(renderedContent);
        
        var contractPageView = this;
        
        $("table tbody tr").click(function(){
        	contractPageView.openContractActions(contractPageView.getContractFromContent($(this).attr("data-contract-id")));
        });
        
        $("table .startDate, table .endDate").each(function(index, element){
        	var dateInput = $(element);
        	dateInput.html(moment(parseInt(dateInput.html())).format("YYYY-MM-DD HH:mm"));
        });

        var $statusSelect = $(".status select");
        $statusSelect.select2("val", null);
        for(statusIndex in this.model.status){
            var newData = $statusSelect.select2("val");
            newData.push(this.model.status[statusIndex]);

            $statusSelect.select2("val", newData);
        }

        $(".pager .previous a").attr(
            "href", 
            "#" + getBranchForRouter() + "/" + 
                (parseInt(contractPageView.model.page) - 1) + "/" + 
                contractPageView.model.results + "/" +
                buildStatusStringForRouter(), 
            {trigger: true}
        );

        $(".pager .next a").attr(
            "href", 
            "#" + getBranchForRouter() + "/" + 
                (parseInt(contractPageView.model.page) + 1) + "/" + 
                contractPageView.model.results + "/" +
                buildStatusStringForRouter(), 
            {trigger: true}
        );
        
        return this;
    },
    
    saveContract: function(){
    	$("button.save").prop('disabled', true);
    	$("button.close").prop('disabled', true);
    	
		var model = this.model.content;
		
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

		var contract = new Contract(
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
			}
		);

        contract.save({}, { 
            async : false,
            success : function(resp){
                model.fetch();
                
                $('.modal').modal('hide');
            }
        } );
	},
});