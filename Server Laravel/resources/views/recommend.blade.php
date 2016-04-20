@extends('layout')

@section('mainContent')


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
             
            
                 
                   $.get('item/'+msg[i]).done(function(msg2){
                   
                   
                   
                   $('#recommendedHotelList').append('<li>'+ msg2 +'</li>');
                   
               });
           
    
  
             
           }
           
       });
       
    });
    
</script>

@endsection
