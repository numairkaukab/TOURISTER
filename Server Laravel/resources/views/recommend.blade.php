@extends('layout')

@section('mainContent')

<script>

var openRequest = indexedDB.open("tourister",1);

var clientDatabase;


 openRequest.onupgradeneeded = function(e) {
            
            var thisDB = e.target.result;
            
            
                thisDB.createObjectStore("recommendations");
                
            
            
            
        };
 
        openRequest.onsuccess = function(e) {
            
            clientDatabase = e.target.result;
            
            
           
            
        };
        
       

</script>


<div width="100%">
    
    <div class="row">
        
        <h3 class="text-center">Recommendation Engine</h3>
        
        <hr />
        
        <div class="col-lg-6">
            
            
            <div class="panel panel-default">
  <div class="panel-heading"><strong>Filtering Techniques</strong></div>
  <div class="panel-body">
    
  </div>
</div>
            
            
        </div>
        
        <div class="col-lg-6">
            
            <div class="panel panel-default">
                <div class="panel-heading"><strong>Filter Settings</strong></div>
  <div class="panel-body">
    
  </div>
</div>
            
        </div>
        
    </div>
    
    <div class="row">
        
        <div class="col-lg-12">
            
             <div class="panel panel-default">
                <div class="panel-heading"><strong>Evaluation</strong></div>
  <div class="panel-body">
    
  </div>
</div>
            
        </div>
        
    </div>
    
    <a id="getRecommendation" style="width:50%" class="btn btn-lg btn-block btn-primary">Recommend Me Places!</a>
    
</div>

<script>
    
 
        
        
        
   
        
     
       
        
    
    
    $('#getRecommendation').click(function(){
       
       $.get('recommender').done(function(msg){
           
           $('#hotels').effect('highlight', {}, 1000);
           $('#hotels').append(' <span class="badge"> '+ msg.length +' </span>');
           $('#noRMsg').remove();
           
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
