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


<script>

var openRequest = indexedDB.open("tourister",1);

var clientDatabase;

 openRequest.onupgradeneeded = function(e) {
            
            var thisDB = e.target.result;
            
            
                thisDB.createObjectStore("markers");
                
            
            
            
        }
 
        openRequest.onsuccess = function(e) {
            
            clientDatabase = e.target.result;
            
        }

</script>

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
            prpn: "",
            stars: "",
            userrating: "",
            roomtype: "",
            roomoccupancy: "",
            features : [{
            wifi : "",
                    pool: "",
                    parking: "",
                    spa: ""
            }]


    };
    $('body').on('click', '#hotelSubmit', function(){



    marker.name = $('#hotelName').val();
    marker.taggedby = "{!! Auth::user()->id !!}";
    hotel.name = $('#hotelName').val();
    hotel.total = $('#hotelName').val();
    hotel.prpn = $('#hotelName').val();
    hotel.roomtype = $('#hotelName').val();
    hotel.roomoccupancy = $('#hotelName').val();
    hotel.features['wifi'] = $('#wifi').val();
    hotel.features['pool'] = $('#pool').val();
    hotel.features['parking'] = $('#parking').val();
    hotel.features['spa'] = $('#spa').val();
    var url = '{!! action('tagController@hotelTag') !!}';
    $.post(url, {data : marker, data2: hotel}).done(function(result){
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
    var infoWindow = new google.maps.InfoWindow;
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
    
    var transaction = clientDatabase.transaction(["markers"],"readwrite");
    var store = transaction.objectStore("markers");
    
    
    
    //store.add(marker, item_id);
    
    
    
    
    
    
    
    if (taggedby > 0){

     var values = {
          user : {!! Auth::user()->id !!},
          item : item_id,
          type: type,
          rating: ''
      };

    $.ajax({type: 'GET', async: false, url : "username/" + taggedby}).done(function(data){


    

    var html = `


 <p> Tagged By: <a href = "#" id = "profile${taggedby}" data-content = "<p style=' position:absolute; top:15px;'>${data}</p>  <button id='addFriend' style='position:absolute; bottom:35px; left:15px;' onclick='addFriend(event, ${taggedby} )' class='btn btn-primary btn-xs'>Add Friend</button> <img style='position:absolute; top:15px; right:15px;' width='100px' height='100px'src='{!! asset('imgs/profile_pictures/${taggedby}pic.jpg') !!}'>" onmousedown = 'renderPopover(event)' > ${data} </a></p>
   
   <div id="rating"></div>
   
   
      
             
             <h3> ${name} </h3>
             <h6> ${addr} </h6>



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
 <input type = "text" class = "form-control" id = "hotelName" placeholder = "Enter Hotel Name Here" >
             </div>

             <label> Price </label>
             <div class = "form-group form-inline" >
 <select id = "currency" class = "form-control" >
             <option > USD </option>
             <option > GBP </option>
             <option > PKR </option>

             </select>


 <input type = "text" class = "form-control" id = "totalPrice" placeholder = "Total" >
 <input type = "text" class = "form-control" id = "perRoomPerNight" placeholder = "Per Room, Per Night" >
             </div>

             <label for = "rating" > Rating: </label>
             <div class = "form-group form-inline" >
             <label > Stars </label>
             <i class = "fa fa-star" > </i> <i class="fa fa-star"> </i> <i class = "fa fa-star"> </i> <i class="fa fa-star"></i > <i class = "fa fa-star" > </i> 
                         <label > Your Rating </label>
             <i class = "fa fa-star"> </i> <i class="fa fa-star"> </i > <i class = "fa fa-star" > </i> <i class="fa fa-star"></i > <i class = "fa fa-star" > </i>

                         </div>

                         <label > Room </label>
                         <div class = "form-group form-inline" >
             <select id = "roomType" class = "form-control" >
                         <option > Single </option>
                         <option > Double </option>
                         <option > Triple </option>
                         <option > Twin </option>
                         <option > Family Suite </option>
                         <option > Presidential Suite </option>

                         </select>

             <input type = "text" class = "form-control" id = "roomOccupants" placeholder = "Room Occupancy" >
                         </div>

                         <label > Features <label >
                         <div class = "form-group" >
                         <div class = "checkbox" >
                         <label >
             <input id = "wifi" type = "checkbox" value = "wifiTrue"> WIFI
                         </label>
                         </div>

                         <div class = "checkbox" >
                         <label >
             <input id = "pool" type = "checkbox" value = "poolTrue" > Pool
                         </label>
                         </div>

                         <div class = "checkbox" >
                         <label >
             <input id = "parking" type = "checkbox" value = "parkingTrue" > Parking
                         </label>
                         </div>

                         <div class = "checkbox" >
                         <label >
             <input id = "spa" type = "checkbox" value = "spaTrue" > SPA
                         </label>
                         </div>

                         </div>


                         </form>

                         `;
                 $('#tagTitle').text('Hotel Tag');
                 $('#tagBody').html(htmlString);
                 $('#tagSubmit').attr('id', 'hotelSubmit');
                 $('#tagModal').modal('show');
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






$('#mainContent').droppable({drop: function(){
        $('#skyscanner').toggleClass('pulsating_circle');
}});


</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCV1fQD2VC6HoNbuuSPkE0q_QZvDf117PY&callback=initMap&libraries=drawing" async defer></script>


@endsection