@extends('layout')

@section('mainContent')

<script>
    
    $(document).ready(function(){
        
        
    
   $('#hotelProfile').bootstrapDualListbox({
       nonSelectedListLabel: 'Attributes',
       selectedListLabel: 'Likes',
        moveOnSelect: false,
       
       
   });
        
        
      
      
      $(function() {
    $( "#priceRange" ).slider({
        max: 1000,
        min: 0,
        
        step: 5,
        create: function(event,ui){$('#priceVal').val( $('#priceRange').slider("value"));},
        slide: function(event,ui){$('#priceVal').val( $('#priceRange').slider("value"));},
        change: function(event,ui){$('#priceVal').val( $('#priceRange').slider("value"));},
        
        
        
    });
  });
  
 
  
     
        
        
    });
    
    
    
    
    
    
    
</script>



<h3 class="text-center" style="font-family: makhina; color: #eb6864">Profile Builder</h3>

<hr />

<div class="container" style="width:100%">

<div>

  
  <ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="#hotelPref" aria-controls="hotelPref" role="tab" data-toggle="tab">Hotel Preferences</a></li>
    <li role="presentation"><a href="#eventPref" aria-controls="eventPref" role="tab" data-toggle="tab">Event Preferences</a></li>
    <li role="presentation"><a href="#attractionPref" aria-controls="attractionPref" role="tab" data-toggle="tab">Tourist Attraction Preferences</a></li>
    <li role="presentation"><a href="#restaurantPref" aria-controls="restaurantPref" role="tab" data-toggle="tab">Restaurant Preferences</a></li>
  </ul>

  
  <div class="tab-content">
      <div role="tabpanel" class="tab-pane active" id="hotelPref">
          
          <label for="type">Type of Hotel: </label>
  
          <div class="row form-group">
              
              <div class="col-lg-4">     
              
          <div class="checkbox">
  <label><input id="Hotel" type="checkbox" value="">Hotel</label>
</div>
<div class="checkbox">
  <label><input id="Apartment" type="checkbox" value="">Apartment</label>
</div>
<div class="checkbox">
  <label><input id="GuestHouse" type="checkbox" value="">Guest House</label>
</div>
                  
              </div>
              
              <div class="col-lg-4">
              
               <div class="checkbox">
  <label><input id="Resort" type="checkbox" value="">Resort</label>
</div>
<div class="checkbox">
  <label><input id="BedandBreakfast" type="checkbox" value="">Bed and Breakfast</label>
</div>
<div class="checkbox">
  <label><input id="PrivateHome" type="checkbox" value="">Private Home</label>
</div>
                  
              </div>
            
              <div class="col-lg-4">
              
               <div class="checkbox">
  <label><input id="Hostel" type="checkbox" value="">Hostel</label>
</div>
<div class="checkbox">
  <label><input id="CountryHouse" type="checkbox" value="">Country House</label>
</div>

              
          </div>       
      </div>     
          <label for="type">Stars: </label>
          
          <div id="stars" class="form-inline">
              
              <div class="checkbox">
                  <label><input style="position:relative; top:20px; left:17px;" type="checkbox" value=""> <i class="fa fa-star"></i></label>
</div>
              <div class="checkbox">
  <label><input style="position:relative; top:20px; left:20px;" type="checkbox" value=""> <i class="fa fa-star"></i><i class="fa fa-star"></i></label>
</div>
              <div class="checkbox">
  <label><input style="position:relative; top:20px; left:23px;" type="checkbox" value=""><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i></label>
</div>
              <div class="checkbox">
  <label><input style="position:relative; top:20px; left:26px;" type="checkbox" value=""> <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i></label>
</div>
              <div class="checkbox">
  <label><input style="position:relative; top:20px; left:29px;" id="5" type="checkbox" value=""> <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i></label>
</div>
              
              
          </div>
          
          <br /><br />
          
          <label for="type">Price: </label>
          
          
          <div style="width:50%" id="priceRange">
              
              
          </div>
              
          <input id="priceVal" style="width:10%; position:absolute; bottom: 250px; right:35%; " class="form-control" type="text">
              
              
          
          <br />
          
           
               <label for="type">Room Type: </label>
          
               <div class="row">
                   
                   <div class="col-lg-3">
                       <div class="checkbox">
  <label><input id="Single" type="checkbox" value="">Single</label>
</div>
                   </div>
                   <div class="col-lg-3">
                       <div class="checkbox">
  <label><input id="Double" type="checkbox" value="">Double</label>
</div>
                   </div>
                   <div class="col-lg-3">
                       <div class="checkbox">
  <label><input id="FamilySuite" type="checkbox" value="">Family Suite</label>
</div>
                   </div>
                   <div class="col-lg-3">
                       <div class="checkbox">
  <label><input id="PresidentialSuite" type="checkbox" value="">Presidential/Executive Suite</label>
</div>
                   </div>
                   
                   
               </div>
              
          <br />
           
          
           
            <label for="type">Facilities: </label>
          
             <input class="form-control" id="facilities" type="text" placeholder="Enter Features Seperated By A Comma ',' ">
             
             <br />
            
            
         
             
          
             
                  <button id="hotelPrefSave" class="text-center btn btn-primary btn-block" style="width:50%;">Save Preferences</button>
                  <button id="hotelPrefLearn" class="text-center btn btn-primary btn-block" style="position:relative; bottom:45px; left:400px; width:50%; ">Learn My Profile!</button>
                  
             
          
      </div>
    <div role="tabpanel" class="tab-pane" id="eventPref"></div>
    <div role="tabpanel" class="tab-pane" id="attractionPref"></div>
    <div role="tabpanel" class="tab-pane" id="restaurantPref"></div>
  </div>

</div>

</div>

<script>
    
    var user = {!! Auth::user()->id !!}
    
    $('#hotelPrefLearn').click(function(){
       
       $.get('learnProfile/'+user).done(function(msg){
           
           console.log(msg);
           
           
           alert('Learned!');
           
            $("#" + msg.type[1]).prop("checked", true);
           $("#" + msg.type[1]).effect("pulsate");
           
            $("#priceRange").slider("value", msg.price);
           $("#priceRange").effect("pulsate");
           
           $("#" + msg.rating).prop("checked", true);
           $("#" + msg.rating).effect("pulsate");
           
            $("#" + msg.room_type).prop("checked", true);
           $("#" + msg.room_type).effect("pulsate");
           
           $('#facilities').val(msg.facilities);
            $('#facilities').effect("pulsate");
           
           
       });
       
    });

$('#hotelPrefSave').click(function(){
    
    
    
    var preferences = {
        
      user: user,  
      type : "Apartments",
      stars : "5",
      price: "100",
      room_type: "Single",
      facilities: "Pool, GYM",
      location: "Australia"
      
      
      
    };
    
   
    
    console.log(preferences);
   
    $.get('contentFilter', {data: preferences}).done(function(msg){
       
       alert(msg);
       
   });
   
   
});



</script>


@endsection

