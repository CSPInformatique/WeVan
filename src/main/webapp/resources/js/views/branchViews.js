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

window.BranchesView = Backbone.View.extend({
	closeModal: function(){
		$('.modal').modal('hide');
	},
	
	deleteBranch: function(id){
		var branch = this.collection.get(id);
		
		branch.set({active: 0});
		
		var collection = this.collection;
		
		branch.save({},{
			success : function(){
				collection.fetch();
			}
		});
	},
	
	editBranch : function(branchId){
		this.openBranch(this.collection.get(branchId).toJSON());
	},
	
	el : $('#branches-container'),
	
    initialize : function() {
    	this.collection.fetch();
    	
        this.template = _.template($('#branches-template').html());

        /*--- binding ---*/
        _.bindAll(this, "deleteBranch");
        _.bindAll(this, "render");
        _.bindAll(this, "openNewBranch");
        _.bindAll(this, "saveBranch");
        this.collection.bind("change", this.render);
        this.collection.bind("add", this.render);
        this.collection.bind("remove", this.render);
        /*---------------*/
        
        $(".new button").click(this.openNewBranch);
        $("button.save").click(this.saveBranch);
    },
    
    openBranch : function(branch){
    	$('.modal .modal-body').html(
			_.template($('#branch-template').html())({
	    		branch : branch
    		})
    	);
    	
    	$('.modal.branch').modal({backdrop : 'static'});

	    $(".registrationDate input").each(function(index, element){
    		var dateInput = $(element);
    		dateInput.datetimepicker({format: 'yyyy-mm-dd'});
    		if(dateInput.val() != ""){
    			dateInput.val(moment(parseInt($(element).val())).format("YYYY-MM-DD"));
    		}
    	});
    	
    	$("button.save").prop('disabled', false);
    	$("button.close").prop('disabled', false);
    },
    
    openNewBranch: function(){
    	this.openBranch({	id : 0,
    		name : "",
    		active : true,
    		addressNumber : "",
    		addressStreet : "",
    		companyName : "",
    		companyType : "",
    		euVatNumber : "",
    		headOffice : "",
    		postalCode : "",
    		city : "",
    		country : "",
    		phone : "",
    		registration : "",
    		registrationDate : "",
    		registrationLocation : "",
    		siret : "",
    		googleEmailAccount : ""
		});
    },
    	
    render : function(){;
    	this.collection.sortBy('id');
    	
    	var branches = this.collection.toJSON();
    	
    	var renderedContent = this.template({branches : branches});
    	$('#branches-container').html(renderedContent);
        
    	$('#branches-container').find("select.branch").select2();
        
        var branchesView = this;
        $(".edit button").click(function(){
            branchesView.editBranch($(this).attr("data-id"));
        });

        $(".delete button").click(function(){
            branchesView.deleteBranch($(this).attr("data-id"));
        });
        
        return this;
    },
    
    saveBranch: function(){
    	$("button.save").prop('disabled', true);
    	$("button.close").prop('disabled', true);
    	
		var collection = this.collection;
		
		this.collection.create({
	    		id : $(".branch input.id").val(),
	    		name : $(".branch .name input").val(),
	    		active : $(".branch input.active").val(),
	    		addressNumber : $(".branch .addressNumber input").val(),
	    		addressStreet : $(".branch .addressStreet input").val(),
	    		companyName : $(".branch .companyName input").val(),
	    		companyType : $(".branch .companyType input").val(),
	    		euVatNumber : $(".branch .euVatNumber input").val(),
	    		headOffice :	 $(".branch .headOffice input").val(),
	    		postalCode : $(".branch .postalCode input").val(),
	    		city : $(".branch .city input").val(),
	    		country : $(".branch .country input").val(),
	    		phone : $(".branch .phone input").val(),
	    		registration : $(".branch .registration input").val(),
	    		registrationDate : $(".branch .registrationDate input").val(),
	    		registrationLocation : $(".branch .registrationLocation input").val(),
	    		siret : $(".branch .siret input").val(),
	    		googleEmailAccount : $(".branch .googleEmailAccount input").val()
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