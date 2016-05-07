@extends('layout')

@section('mainContent')

<script>
    
    $(document).ready(function(){
        
        
    
   $('#hotelProfile').bootstrapDualListbox({
       nonSelectedListLabel: 'Attributes',
       selectedListLabel: 'Likes',
        moveOnSelect: false,
       
       
   });
        
        
    });
    
    
    
    
    
</script>



<h3 class="text-center" style="font-family: makhina; color: #eb6864">Profile Builder</h3>

<hr />

<div style="width:100%">

<div class="row">
    
    <div class="col-lg-6">
        
        <div  class="panel panel-default" >
  <div class="panel-heading" style=" color:white; background-color:#eb6864">Hotel Preferences</div>
  <div class="panel-body">
      
      <select multiple="multiple" size="1" id="hotelProfile">
          <option value="option">Finally!</option>
      </select>
     
      
      
  </div>
</div>
        
        
    </div>
    <div class="col-lg-6">
        
        <div class="panel panel-default">
  <div class="panel-heading" style=" color:white; background-color:#eb6864"">Event Preferences</div>
  <div class="panel-body"></div>
</div>
        
    </div>
    
</div>

    <div class="row">
        
        <div class="col-lg-6">
            
            <div class="panel panel-default">
  <div class="panel-heading" style=" color:white; background-color:#eb6864"">Attractions Preferences</div>
  <div class="panel-body"></div>
</div>
            
        </div>
        <div class="col-lg-6">
            
            <div class="panel panel-default">
  <div class="panel-heading" style=" color:white; background-color:#eb6864"">Restaurant Preferences</div>
  <div class="panel-body"></div>
</div>
            
            
        </div>
        
    </div>

</div>



@endsection

