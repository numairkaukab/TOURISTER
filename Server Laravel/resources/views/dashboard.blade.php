@extends('layout')

@section('mainContent')

<div id="map" style="width:100%; height:100%;"></div>


<div class="modal fade" id="tagModal">

    <div class="modal-dialog">

        <div class="modal-content">

            <div class="modal-header">

                <h4 id="tagTitle" class="modal-title">Tag</h4>

            </div>


            <div id="tagBody" class="modal-body"></div>

            <div class="modal-footer">

                <button id="tagSubmit" class="btn btn-primary">Submit</button>
                <button class="btn btn-default">Cancel</button>

            </div>

        </div>


    </div>

</div>

<div class="modal fade" id="autoSuggestModal">

    <div class="modal-dialog">

        <div class="modal-content">

            <div class="modal-header">

                <h4 id="asTitle" class="modal-title">Auto Suggest Places</h4>

            </div>


            <div id="asBody" class="modal-body">
                
                <select class="form-control" id="asSelect">
                    
                    
                </select>
                
            </div>

            <div class="modal-footer">

                <button id="asSubmit" class="btn btn-primary">Submit</button>
                <button class="btn btn-default">Cancel</button>

            </div>

        </div>


    </div>

</div>


<script>

    var marker = {
    name: "",
            lat: "",
            lng: "",
            addr: "",
            type: "",
            taggedby: ""
    };
    var hotel = {
    name: "",
            total: "",
            type: "",
            prpn: "",
            stars: "",
            rating: "",
            room_type: "",
            roomoccupancy: "",
            features: "",


    };
    $('body').on('click', '#hotelSubmit', function(){



    marker.name = $('#hotelName').val();
    marker.taggedby = "{!! Auth::user()->id !!}";
    hotel.name = $('#hotelName').val();
    hotel.type = $('#hotelType').val();
    hotel.total = $('#hotelTotal').val();
    
    hotel.room_type = $('#hotelRoomType').val();
    
    
    hotel.stars = $("#stars").rateYo("rating");
    hotel.features = $('#hotelFeatures').val();
    
    
   
    var url = '{!! action('tagController@hotelTag') !!}';
    $.post(url, {data : marker, data2: hotel}).done(function(result){
    
        $.post('addHotelToIndex/'+result).done(function(){
            alert("Ok!");
        });
        
        alert(result);
    });
    });</script>



<script>
    var map;
    
    function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: - 34.397, lng: 150.644},
            zoom: 8
    });
    
    
    
    
    
    var geocoder = new google.maps.Geocoder;
    $.get('markerXML').done(function(data){

    var xml = data;
    var infoWindow = new google.maps.InfoWindow({
        
    });
    var markers = xml.documentElement.getElementsByTagName("marker");
    
    
   
    
    var customIcons = {
    hotel: {
    icon: '{!! asset('imgs/hotelTag.png') !!}'
    },
            event: {
            icon: '{!! asset('imgs/hotelTag.png') !!}'
            },
            event: {
            icon: '{!! asset('imgs/hotelTag.png') !!}'
            },
            event: {
            icon: '{!! asset('imgs/hotelTag.png') !!}'
            }
    };
    for (var i = 0; i < markers.length; i++) {
        
         var lat = parseFloat(markers[i].getAttribute("lat"));
    var lng = parseFloat(markers[i].getAttribute("lng"));

    var point = new google.maps.LatLng(
            parseFloat(markers[i].getAttribute("lat")),
            parseFloat(markers[i].getAttribute("lng")));
    var name = markers[i].getAttribute("name");
    var item_id = markers[i].getAttribute("item_id");
    var addr = markers[i].getAttribute("addr");
    var taggedby = markers[i].getAttribute("taggedby");
    var type = markers[i].getAttribute("type");
    var icon = customIcons[markers[i].getAttribute("type")];
    var marker = new google.maps.Marker({
    map: map,
            position: point,
            icon : icon.icon,
    });
    
  
    
    markerArray[item_id]=marker;
    var hotelDetails;
    
    
    if(type=='hotel'){
        
        $.ajax({type: 'GET', async: false, url : "hotelDetails/" + item_id}).done(function(msg){
            hotelDetails = msg;
        });
        
        
    }
    
    
    
    
    if (taggedby > 0){

     var values = {
          user : {!! Auth::user()->id !!},
          item : item_id,
          type: type,
          rating: ''
      };

    $.ajax({type: 'GET', async: false, url : "username/" + taggedby}).done(function(data){


    

    var html = `

<div id="infoWindowContent" style="width:400px;">

 <p> Tagged By: <a href = "#" id = "profile${taggedby}" data-content = "<p style=' position:absolute; top:15px;'>${data}</p>  <button id='addFriend' style='position:absolute; bottom:35px; left:15px;' onclick='addFriend(event, ${taggedby} )' class='btn btn-primary btn-xs'>Add Friend</button> <img style='position:absolute; top:15px; right:15px;' width='100px' height='100px'src='{!! asset('imgs/profile_pictures/${taggedby}pic.jpg') !!}'>" onmousedown = 'renderPopover(event)' > ${data} </a></p>
   
   <div id="rating"></div>
   
   
      
             
             <h3> ${name} </h3>
             <h6> ${addr} </h6>
     
            <p><strong>Hotel Type: </strong><small>${hotelDetails.type}</small></p>
            <p><strong>${hotelDetails.stars}</strong><small> Star Hotel</small></p>
            <p><strong>Features: </strong><small>${hotelDetails.facilities}</small></p>
            <p><strong>Price: </strong><small>${hotelDetails.price}</small></p>
                
             <img src="http://placehold.it/206x150" style="position:absolute; top:0px; left:200px;">
                         <img src="http://placehold.it/66x85" style="position:absolute; top:155px; left:200px;">  
                                     <img src="http://placehold.it/66x85" style="position:absolute; top:155px; left:270px;">  
                                                 <img src="http://placehold.it/66x85" style="position:absolute; top:155px; left:340px;"> 
                                                             
                                                             <br />
                         
            <button class="btn btn-sm btn-default" style="position:absolute;right:20px;"><i class="fa fa-photo"></i> Upload Photos</button>    
                
            <button id="like" class="btn btn-sm btn-success"><i class="fa fa-thumbs-up"></i> Like</button>
            <button id="dislike" class="btn btn-sm btn-primary"><i class="fa fa-thumbs-down"></i> Dislike</button> 
            <button class="btn btn-sm btn-default" style="position:absolute;right:150px;"><i class="fa fa-comment"></i> Comments</button>
            
</div>

             `;
             
             
   
             
             
     bindInfoWindow(marker, map, infoWindow, html, values);
     
     
     
     
     });
     }
     else{
      
      var values = {
          user : {!! Auth::user()->id !!},
          item : item_id,
          rating: ''
      };
      
      

     var html = `
 <p> Tagged By: ${taggedby} </p>
 
 <div id="rating"></div>
 
 

             <h3> ${name} </h3>
             <h6> ${addr} </h6>

             `;
     bindInfoWindow(marker, map, infoWindow, html,values);
     }





     }

     


     function bindInfoWindow(marker, map, infoWindow, html, data2) {
     google.maps.event.addListener(marker, 'click', function() {
     infoWindow.setContent(html);
     infoWindow.open(map, marker);
     
     $('#like').click(function(){
        
        $.post('like/'+data2.user+'/'+data2.item+'/like').done(function(msg){
            
            alert(msg);
            
        });
        
     });
     
      $('#dislike').click(function(){
        
        $.post('like/'+data2.user+'/'+data2.item+'/dislike').done(function(msg){
            
            alert(msg);
            
        });
        
     });
    
     var hotelIcon = "{!! asset('imgs/hotelTag.png') !!}";
     for(var i=1; i<markerArray.length; i++){
         markerArray[i].setIcon(hotelIcon);
     }
     
    
     $.get('contentFilter/'+data2.item).done(function(msg){
         
           
         
           $('#hotelBadge').html("");
           var icon = "{!! asset('imgs/hotelTag.gif') !!}";
         
           $('#hotels').effect('highlight', {}, 1000);
           $('#hotelBadge').append(' <span class="badge"> '+ msg.length +' </span>');
           $('#noRMsg').remove();
           
           $('#recommendedHotelList').html("");
           
            $.get('item/'+msg).done(function(msg2){
                   
                   markerArray[msg2.id].setIcon(icon);
                   markerArray[msg2.id].setOptions({optimized:false});
                   
                   $.get('hotelDetails/'+msg2.id).done(function(msg3){
                       
                       var html=' <li onclick="openOnMap('+ msg2.id +')" id="' + msg2.id + '" class="list-group-item"> <small style="position:absolute; right:18px;">#</small><strong style="position:absolute; right:10px;">1</strong> <p><strong> </strong><small>'+msg3.name+'</small></p><p><strong> </strong><small>'+msg3.addr+'</small></p><button class="btn btn-success">Click to Open on Map</button> </li>';
                       
                       $('#recommendedHotelList').append(html);
                       
                   });
                   
                   
                   
               });
           
           
     });
     
     if(data2.type=="hotel"){
         
         $.get('hotelPreference/'+data2.user+'/'+data2.item).done(function(preference){
             
             var rating = preference[0]['preference'];
             
              $('#rating').rateYo({
                  
                 
         rating: rating,         
         starWidth: '15px',
         ratedFill: "#E74C3C",
         
         onSet: function(rate){
             
             data2.rating=rate;
             
             
             
             $.post('updateHotelPreference', {
                 
                 
                 data: data2
                 
             }).done(function(msg){
                 
             });
             
        
             
             
         }
     });
             
             
             
         });
         
     }
     
     
    
     
     
     });
     }




     });
     
     
     
     var drawingManager = new google.maps.drawing.DrawingManager({
     drawingMode: google.maps.drawing.OverlayType.MARKER,
             drawingControl: true,
             drawingControlOptions: {
             position: google.maps.ControlPosition.TOP_CENTER,
                     drawingModes: [
                             google.maps.drawing.OverlayType.MARKER

                     ]
             }

     });
     drawingManager.setMap(map);
     google.maps.event.addListener(drawingManager, 'overlaycomplete', function(event) {


     if ($("#hotelTag").hasClass("active") != true && $("#hotelTag").hasClass("active") != true && $("#hotelTag").hasClass("active") != true && $("#hotelTag").hasClass("active") != true)
     {



     $(function () {
     $('[data-toggle="tooltip"]').tooltip()
     });
     $('#taggingControls').tooltip({title: 'Select a Tag before tagging', placement: 'bottom'});
     $('#taggingControls').tooltip('show');
     event.overlay.setMap(null);
     }



     marker.lat = event.overlay.getPosition().lat();
     marker.lng = event.overlay.getPosition().lng();
     var latlng = {lat: parseFloat(marker.lat), lng: parseFloat(marker.lng)};
     geocoder.geocode({'location': latlng}, function(results, status) {

     if (results[1]) {



     marker.addr = results[1].formatted_address;
     }

     });
     if ($("#hotelTag").hasClass("active") == true)
     {

     $('#taggingControls').tooltip('destroy');
     marker.type = "hotel";
     var htmlString = `

             <form>
             <div class = "form-group">
             <label for = "hotelName"> Hotel Name </label>
 <input style="" type = "text" class = "form-control" id = "hotelName" placeholder = "Enter Hotel Name Here" >
                 
               
             </div>
     
     <div class="form-group">
     
             <label style="" for = "hotelType"> Hotel Type </label>
 <select style="" class = "form-control" id = "hotelType" >
             <option>Hotel</option>
     <option>Guest House</option>
     <option>Apartment</option>
     <option>Resort</option>
     <option>Bed and Breakfast</option>
     <option>Private Home</option>
     <option>Hostel</option>
     <option>Country House</option>
             
             </select>  
             
             </div>

             <label>Room Price </label>
             <div class = "form-group form-inline" >
 <select id = "currency" class = "form-control" >
             <option > USD </option>
             <option > GBP </option>
             <option > PKR </option>

             </select>


 <input type = "text" class = "form-control" id = "hotelTotal" placeholder = "Total" >
 
             </div>

            
             <div class = "form-group form-inline" >
             <label > Stars </label>
             <div  id="stars" ></div> 
             </div>            

                         <label > Room Types</label>
                         <div class = "form-group form-inline" >
             <select id = "hotelRoomType" class = "form-control" >
                         <option > Single </option>
                         
                         
                         
                         <option > Family Suite </option>
                         <option > Presidential/Executive Suite </option>

                         </select>

             
                         </div>

                         <label > Features </label >
                         <div class = "form-group" >
                         
                         <input class="form-control" id="hotelFeatures" type="text" placeholder="Enter Features Seperated by a comma ' , ' "> 

                         </div>


                         </form>

                         `;
                 $('#tagTitle').text('Hotel Tag');
                 $('#tagBody').html(htmlString);
                 $('#tagSubmit').attr('id', 'hotelSubmit');
                 $('#tagModal').modal('show');
                 
                 $('#stars').rateYo({
                     rating:3.6,
                     starWidth: '15px',
         ratedFill: "#E74C3C"
                 });
                 
               
                 
                 }




                 });
                 $('#hotelTag').click(function(){

                 $('#hotelTag').toggleClass('active');
                 drawingManager.setOptions({

                 markerOptions : {
                 icon : '{!! asset('imgs/hotelTag.png') !!}'
                 }

                 });
                 });
                 }






$('#mainContent').droppable({drop: function(event, ui){
        
        var point = {
            x: ui.offset.left ,
            y: ui.offset.top
        }
       
       var ll = point2LatLng(point, map);
       var geocoder = new google.maps.Geocoder;
       
       geocoder.geocode({'location': ll}, function(results, status) {

     if (results[1]) {

      alert(results[1].formatted_address);
      var str2 = results[1].formatted_address.replace(/[0-9]/g, '');
      var str3 = str2.replace(/\,/g,"");
      alert(str3)
      var encoded = encodeURIComponent(str3);
      console.log(results[1]);
      alert(encoded);

      $.get('autosuggest', {data : encoded}).done(function(msg){
          
         for(var i=0; i<msg['results'].length; i++)
         {
             console.log(msg['results'][i]);
             $('#asSelect').append('<option value="'+ msg['results'][i].individual_id +'">'+ msg['results'][i].display_name +'</option>');
             
         }
          
          $('#autoSuggestModal').modal('show');
          
      });
     }

     });
       
       function point2LatLng(point, map) {
  var topRight = map.getProjection().fromLatLngToPoint(map.getBounds().getNorthEast());
  var bottomLeft = map.getProjection().fromLatLngToPoint(map.getBounds().getSouthWest());
  var scale = Math.pow(2, map.getZoom());
  var worldPoint = new google.maps.Point(point.x / scale + bottomLeft.x, point.y / scale + topRight.y);
  return map.getProjection().fromPointToLatLng(worldPoint);
}
        
        alert(ui.offset.left + ' ' + ui.offset.top);
        
}});


$('#asSubmit').click(function(){
   
   var send = $('#asSelect').val();
   
   $.get('fetch', {data: send}).done(function(msg){
       
       console.log(msg);
       
       var hotelDetails = {
           name : "",
           lat : "",
           lng : "",
           point: "",
           addr : "",
           type : "",
           stars : "",
           features : [],
           features_display: "",
           rating : "",
           price: "",
           room_type : "", 
       };
       
       for(var i=0; i<msg.hotels.length; i++)
       {
           hotelDetails.name = msg.hotels[i].name;
           hotelDetails.lat = msg.hotels[i].latitude;
           hotelDetails.lng = msg.hotels[i].longitude;
           hotelDetails.type = msg.hotels[i].types[0];
           hotelDetails.stars = msg.hotels[i].star_rating;
           hotelDetails.price = msg.hotels_prices[i].agent_prices[0].price_total;
           
           hotelDetails.point = new google.maps.LatLng(
            parseFloat(hotelDetails.lat),
            parseFloat(hotelDetails.lng));
            
            var marker = new google.maps.Marker({
    map: map,
            position: hotelDetails.point,
            icon : '{!! asset('imgs/hotelTag.png') !!}'
            
    });
           
           
           
           for(var j=0; j<msg.hotels[i].amenities.length; j++)
           {
               hotelDetails.features[j] = msg.hotels[i].amenities[j];
               
           }
           
           for(var j=0; j<hotelDetails.features.length; j++)
           {
               for(var k=0; k<msg.amenities.length; k++)
               {
                   if
                   (msg.amenities[k].id == hotelDetails.features[j])
                   {
                       hotelDetails.features_display = hotelDetails.features_display.concat(msg.amenities[k].name + " ,");
                   }
               }
           }
           
          
           
       var html=`
               <p> Tagged By: <strong>SkyScanner</strong></p>
   
   <div id="rating"></div>
   
   
      
             
             <h3> ${hotelDetails.name} </h3>
             <h6>  </h6>
     
            <p><strong>Hotel Type: </strong><small>${hotelDetails.type}</small></p>
            <p><strong>${hotelDetails.stars}</strong><small> Star Hotel</small></p>
            <p><strong>Features: </strong><small>${hotelDetails.features_display}</small></p>
            <p><strong>Price: </strong><small>${hotelDetails.price}</small></p>
                
            <button class="btn btn-sm btn-success"><i class="fa fa-thumbs-up"></i> Like</button>
            <button class="btn btn-sm btn-primary"><i class="fa fa-thumbs-down"></i> Dislike</button>     
            
            `;
       var infoWindow = new google.maps.InfoWindow;
       
       google.maps.event.addListener(marker, 'click', function() {
           
           infoWindow.setContent(html);
           infoWindow.open(map,marker);
           
       });
       
       
      
       
       
       
           
       }
       
       
       
   });
   
});

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCV1fQD2VC6HoNbuuSPkE0q_QZvDf117PY&callback=initMap&libraries=drawing" async defer></script>


@endsection