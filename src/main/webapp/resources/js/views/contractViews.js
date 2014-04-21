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
	
    addOption : function(option){
        $(".editContract .none").hide();
        
        $(".editContract .options").append(
            _.template($('#option-template').html())({
                option : option
            })
        );
        
        $(".editContract .options button.remove").last().click(function(){
            $(this).closest(".form-inline").remove();
            
            if($(".editContract .options button.remove").size() == 0){
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

    hideEditContractLoading : function(){
        $(".editContract input").prop("disabled", false);

        if($('.editContract input.id').val() == null){
            $("button.reset").prop("disabled", true)
        }

        $(".editContract .loading").hide();
    },
    
    initialize : function() {
        var view = this;

    	this.model.fetch();
    	
        this.template = _.template($('#contractPage-template').html());

        /*--- binding ---*/
        _.bindAll(this, 'render');
        _.bindAll(this, 'openNewContract');
        _.bindAll(this, 'printContract');
        _.bindAll(this, 'resetContract');
        _.bindAll(this, 'saveContract');
        this.model.bind('change', this.render);
        this.model.bind('add', this.render);
        this.model.bind('remove', this.render);
        /*---------------*/
        
        $(".new button").click(this.openNewContract);
        $("button.print").click(this.printContract);
        $("button.reset").click(this.resetContract);
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

        $(".editContract.modal .modal-title span").html(contract.id);
    	
    	for(var driverIndex in contract.additionalDrivers){    		
    		this.addAdditionalDriver(contract.additionalDrivers[driverIndex]);
    	}
        
        for(var optionIndex in contract.options){         
            this.addOption(contract.options[optionIndex]);
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
    	
    	var contractPageView = this;
        $(".editContract button.addDriver").click(function(){
            contractPageView.addAdditionalDriver({
                id: 0,
                firstName:"", 
                lastName: "", 
                driverLicense : ""
            });
        });

        $(".editContract button.addOption").click(function(){
            contractPageView.addOption({
                id : 0,
                label: "",
                amount: ""
            });
        });

        this.hideEditContractLoading();
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
            amountAlreadyPaid : "",
    		totalAmount : "",
    		vehicule : null,
    		deductible : "",
    		deposit : "",
    		additionalDrivers : []
		});
    },
    
    printContract: function(contractId){
    	this.saveContract({
    		callback : function(){
    			window.open(ctx + "/contract/" + $('.editContract input.id').val());
    		} 
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
        	contractPageView.editContract($(this).attr("data-contract-id"));
        });
        
        $("table .startDate, table .endDate, table .creationDate").each(function(index, element){
        	var dateInput = $(element);
        	dateInput.html(moment(parseInt(dateInput.html())).format("YYYY-MM-DD"));
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
                contractPageView.model.sortBy + "/" +
                contractPageView.model.ascending +
                buildStatusStringForRouter(), 
            {trigger: true}
        );

        $(".pager .next a").attr(
            "href", 
            "#" + getBranchForRouter() + "/" + 
                (parseInt(contractPageView.model.page) + 1) + "/" + 
                contractPageView.model.results + "/" +
                contractPageView.model.sortBy + "/" +
                contractPageView.model.ascending +
                buildStatusStringForRouter(), 
            {trigger: true}
        );
        
        adjustTableSize();

        $(".contracts thead th a").each(function(index, element){
            var ascending = true;
            var sortBy = $(this).attr("data-sortBy");
            if(sortBy == contractPageView.model.sortBy){
                var ascending = contractPageView.model.ascending == "true";

                $(element).closest("th").find(ascending ? ".ascending" : ".descending").removeClass("hide");

                ascending = !ascending;

            }

            $(element).attr(            
                "href", 
                "#" + getBranchForRouter() + "/" + 
                    contractPageView.model.page + "/" + 
                    contractPageView.model.results + "/" +
                    sortBy + "/" +
                    ascending +
                    buildStatusStringForRouter(), 
                {trigger: true}
            );
        });

        $('.contracts table').floatThead({
            scrollContainer: function($table){
                return $table.closest('.contracts');
            }
        });

        return this;
    },

    resetContract: function(){
        var view = this;

        this.showEditContractLoading();

        var contractId = $('.editContract input.id').val();

        var model = this.model;

        var contract =  new Contract({   
                            id: contractId
                        });

        contract.reset = true;

        contract.save({}, { 
            success : function(resp){
                model.fetch().complete(function(){
                    //$('.modal').modal('hide');

                    view.editContract(contractId);
                });
            }
        } );
    },
    
    saveContract: function(options){   
        var view = this;
		var model = this.model;

        this.showEditContractLoading();

        var contractId =  $('.editContract input.id').val();
		
		var additionalDrivers = [];
		$(".additionalDrivers .driver").each(function(index, element){
			additionalDrivers.push({
				id : contractId,
				firstName : $(this).find("input.firstName").val(),
				lastName : $(this).find("input.lastName").val(),
				corporateName : "",
				driverLicense : $(this).find("input.driverLicense").val()
			});
		});

        var contractOptions = [];
        $(".options .option").each(function(index, element){
            contractOptions.push({
                id : $(this).attr("data-option-id"),
                contract : contractId,
                label : $(this).find("input.optionLabel").val(),
                active : true,
                amount : $(this).find("input.amount").val()
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
			{	id: contractId,
                reservationId : $(".editContract input.reservationId").val(),
                creationDate : +moment(parseInt($(".editContract input.creationDate").val())),
                editionDate : +moment(parseInt($(".editContract input.editionDate").val())),
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
				kilometersPackage : $(".editContract input.kilometers").val(),
                amountAlreadyPaid : $(".editContract input.amountAlreadyPaid").val(),
				totalAmount : $(".editContract input.totalAmount").val(),
				vehicule : {id : $(".editContract .vehicule select").select2("val")},
				deductible : $(".editContract input.deductible").val(),
				deposit : $(".editContract .deposit input").val(),
				additionalDrivers : additionalDrivers,
                options: contractOptions,
                showOptionsPrices : $(".showOptionsPrices input").is(":checked")
			}
		);

        contract.save({}, { 
            success : function(resp){
                model.fetch().complete(function(){
                    view.hideEditContractLoading();
                    
                    if(options != null && options.callback != null){
                        options.callback();
                    }
                });
            }
        } );
	},

    showEditContractLoading : function(){
        $(".editContract input").prop("disabled", true);

        $(".editContract .loading").show();
    }
});