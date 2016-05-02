@extends('layout')

@section('mainContent')

<script>
    
      $(function() {
    $( "#dialog" ).dialog({
        
        width : 'auto',
        dialogClass : 'myTitleClass'
        
    });
  });

var openRequest = indexedDB.open("tourister",2);

var clientDatabase;


 openRequest.onupgradeneeded = function(e) {
            
            var thisDB = e.target.result;
            
            
                thisDB.createObjectStore("recommendations");
                
            
            
            
        };
 
        openRequest.onsuccess = function(e) {
            
            clientDatabase = e.target.result;
            
            
           
            
        };
        
       

</script>


<div id="dialog" title="Warning">
    
    
    <pre>This is for Advance Users only. 
Only Change settings if you are familiar with statistical techniques used in a recommendation engine.
    

P.S. Recommendations for Novice Users are automatic.    
    </pre>
    
</div>

<div width="100%">
    
    
    
    <div class="row">
        
        <h3 class="text-center">Recommendation Engine</h3>
        
        <hr />
        
        <div class="col-lg-6">
            
            
            <div class="panel panel-default">
  <div class="panel-heading"><strong>Filtering Techniques</strong></div>
  
  <div class="panel-body">
      
      <div class="radio">
  <label><input type="radio" id="collabfilterradio" name="filterChoice">Collaborative Filter</label>
</div>
<div class="radio">
    <label><input type="radio" name="filterChoice" disabled="">Content Filter</label>
</div>
<div class="radio disabled">
    <label><input type="radio" name="filterChoice" disabled="">Knowledge-Based/Case Based Reasoning</label>
</div>
      
    
  </div>
</div>
            
            
        </div>
        
        <div class="col-lg-6">
            
            <div class="panel panel-default">
                <div class="panel-heading"><strong>Filter Settings</strong></div>
  <div id="filterSettings" class="panel-body" style="height:100%">
    
      <p>No Filter Selected!</p>
      
  </div>
</div>
            
        </div>
        
    </div>
    
    <div class="row">
        
        <div class="col-lg-12">
            
             <div class="panel panel-default">
                <div class="panel-heading"><strong>Evaluation</strong></div>
  <div id="filterEvaluation" class="panel-body">
    
      <p>No Evaluation Available!</p>
      
  </div>
</div>
            
        </div>
        
    </div>
    
    <a id="getRecommendation" style="width:50%; position:relative; left:25%;" class="btn btn-lg btn-block btn-primary">Recommend Me Places!</a>
    
</div>

<script>
    
 
        
    $('#collabfilterradio').click(function(){
        
        $('#filterSettings').html("");
        
        
        var html=`
        
        <p><strong>Similarity Measure</strong></p>
                        
                         <div class="radio">
  <label><input type="radio" id="" name="" >Pearson Co-Relation</label>
</div>

 <div class="radio">
  <label><input type="radio" id="" name="" >Euclidean Distance</label>
</div>
                        
        <p><strong>User Similarity Threshold</strong></p>                
       
<div id="fns">       
        <div class="radio">
  <label><input type="radio" id="fn" name="">Fixed Neighborhood</label>
</div>
</div>

<div id="tns">
 <div class="radio">
  <label><input type="radio" id="tn" name="">Threshold-based Neighborhood</label>
</div>
</div>        
        
        
        
        `
        
    var html2 = `
    
    <div class="row">
   
   <div class="col-lg-6">
   <p><strong>Similar Users</strong></p>
   </div>
   
   <div class="col-lg-6">
   <p><strong>Hold-Out Validation Results</strong></p>
   </div>
            
            </div>
    
    `
        
        $('#filterSettings').html(html);
        $('#filterEvaluation').html(html2);
        
        
    }) ;   
        


$('body').on("click", "#fn", function(){

   $('#fns').append('<input class="form-control" placeholder="Enter Size of Neighborhood" type="text" id="fnv" name="fnv">');
        
});

$('body').on("click", "#tn", function(){

   $('#tns').append('<input class="form-control" placeholder="Enter Threshold Value (Large Value, Large Accuracy)" type="text" id="tnv" name="tnv">');
        
});


      var user = {!! Auth::user()->id !!}  
     
       
        
    
    
    $('#getRecommendation').click(function(){
       
       $.get('recommender/'+user).done(function(msg){
           
           console.log(msg);
           
           $('#hotels').effect('highlight', {}, 1000);
           $('#hotels').append(' <span class="badge"> '+ msg.length +' </span>');
           $('#noRMsg').remove();
           
           $('#recommendedHotelList').html("");
           
           for(var i=0; i<msg.length; i++)
           {
             
             
             
                var transaction = clientDatabase.transaction(["recommendations"],"readwrite");
    var store = transaction.objectStore("recommendations");
    
    store.add(msg[i],i);
                 
                   $.get('item/'+msg[i]).done(function(msg2){
                   
                   
                   
                   $('#recommendedHotelList').append('<li onclick="openOnMap('+ msg2.id +')" id="' + msg2.id + '">'+ msg2.name +'</li>');
                   
               });
           
    
  
             
           }
           
       });
       
    });
    
</script>

@endsection
