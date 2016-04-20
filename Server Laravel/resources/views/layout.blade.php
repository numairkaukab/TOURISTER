<!Doctype html>

<html>

    <head>

        <title>TOURISTER | Dashboard</title>

        <meta charset="utf-8">


        {!! Html::style('css/bootstrap2.css') !!}
        {!! Html::style('css/fa.css') !!}


        {!! Html::script('js/jquery.js') !!}
        
        
        {!! Html::script('js/jquery-ui.js') !!}
        {!! Html::script('js/bootstrap.js') !!}
        {!! Html::script('js/jquery.slimscroll.js') !!}
        {!! Html::style('css/jquery-ui.css') !!}
        
        {!! Html::style('css/jquery-ui.structure.css') !!}
        {!! Html::style('css/jquery-ui.theme.css') !!}
        
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.0.1/jquery.rateyo.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.0.1/jquery.rateyo.min.js"></script>

        <style>

            #bodyContent{
                visibility:hidden;
            }

        </style>



        <script>



            $(window).load(function () {

                $('#load').fadeOut('fast');
                $('#bodyContent').css('visibility', 'visible');

            });

            

            function renderPopover(event) {

                
                $('[data-toggle="popover"]').popover();
                



                $('#' + event.target.id).popover({placement: 'top', trigger: 'click', container: 'body', html: 'true'});






            }
            
            


           $(document).ready(function(){
              
              $('#accordion').accordion();
              $('#recommendations').slimScroll();
              $('#messenger').slimScroll();
              
           });
           
           function openChatWindow(user){
               
               $('#chatWindow').css('visibility','visible');
               $('#chatHeading').text(user);
               
           }
           
           function addUserToMessenger(event,user){
               
               $.get('username/'+user).done(function(data){
                   
                   var user2 = {!! Auth::user()->id !!};
               
               if(user==user2){
                   alert('Cannot Add Yourself!');
               }
               else{
                  
                  if(event!=null)
                  {
                   $('#userList').append('<li onclick="openChatWindow(\''+data+'\')" style="visibility:hidden" id="userItem'+user+'"class="list-group-item"></li>');
               }
               
               else{
                   
                   $('#userList').append('<li onclick="openChatWindow(\''+data+'\')" id="userItem'+user+'"class="list-group-item"></li>');
                   
               }
               var imgURL = '{!! asset("imgs/profile_pictures/'+user+'pic.jpg") !!}'
               
               $('#userItem'+user).append("<img width='50px' height='50px' class='img-circle' src="+imgURL+"> "+data);
                   
                   if(event != null){
                   
                 $('#' + event.target.id ).effect( "transfer", {to: "#userItem"+user, className: "ui-effects-transfer"}, 1000, function(){ 
                   
                 
        
        $('#userItem'+user).css('visibility','visible');
         
                 
    });  
             }}
               });
               
               if(event != null){
               
               var user2 = {!! Auth::user()->id !!};
               
               if(user==user2){
                   alert('Cannot Add Yourself!');
               }
               else{
              $.post('friends/'+user2+'/'+user).done(function(data){
                 
                 showAlerts(data, 'success');
                 
              });
               }
               
               }
           }

function addFriend(event,user){
                
                addUserToMessenger(event,user);
                
                
               
                
                
            }

        </script>


    </head>

    <body>



        <div id="load" style="z-index:2;">

            <img style="position:absolute; top:40%; left:45%;" src="{!! asset('imgs/loading.gif') !!}">

        </div>


        <div id="bodyContent">

            <div id="systemAlerts" style="z-index:10; width:50%; position:absolute; top:150px; right:300px;visibility:hidden;" class="alert alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <p id="alertText"></p>

            </div>
            
            <div id="chatWindow" class="panel panel-default" style="visibility:hidden;z-index:10;position:absolute; bottom:0px; right:270px; height:400px; width:300px;">
                
                <div class="panel-heading">
                    <p style="display: inline" id="chatHeading"></p>
                    
                    <button type="button" class="close" onclick="$('#chatWindow').css('visibility','hidden')" >
                    <span class="pull-right" aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                    </button>
                    
                </div>
  <div id="chatMessages" class="panel-body" style="height:80%">Messages</div>
  <div class="panel-footer">
      
      <textarea class="form-control" rows="1" style="width:70%"></textarea>
      <button class="btn btn-primary btn-sm" style="position:relative; bottom:35px; left:200px;">Send!</button>
      
  </div>
                
            </div>
            
            <div id="mapTools" class="container" style="display:none;z-index:10; position:absolute; left:350px; top:100px; width:700px; height:80px; background-color:white; opacity:0.7; border-radius:25px;">
                
                
                <img id="skyscanner" style="display:none;opacity:1.0 !important;" src="{!! asset('imgs/skyscanner.png') !!}"> 
                
            </div>

            <div class="modal fade" id="profilePicModal">

                <div class="modal-dialog">

                    <div class="modal-content">

                        <div class="modal-header">

                            <h4 class="modal-title">Upload Picture</h4>

                        </div>


                        <div id="profilePicModalBody" class="modal-body">


                            <form>

                                <div class="form-group form-control input-group">

                                    <input id="profilepic" type="file">

                                </div>

                            </form>

                        </div>

                        <div class="modal-footer">

                            <img id="imgLoading" style="position:relative; right:5px; visibility:hidden;" width="45px" height="40px" src="{!! asset('imgs/smallLoading.gif') !!}">
                            <button  id="pictureUpload" class="btn btn-primary">Upload</button>
                            <button class="btn btn-default">Cancel</button>

                        </div>

                    </div>


                </div>

            </div>






            <nav class="navbar navbar-default" style="margin-bottom:0px;">
                <div class="container-fluid">
                    <div class="navbar-header">

                        <img alt="brand" src="{!! asset('imgs/logo.png') !!}" style="width:40px;height:40px;position: relative; top:10px;">
                        <a href="dashboard" class="navbar-brand">TOURISTER</a>


                    </div>

                    <p id="taggingControls" style="position:relative; left:100px;" class="navbar-text">Tagging Controls: </p>


                    <ul style="position:relative; left:150px;" class="nav navbar-nav">
                        <li id="hotelTag"><a><i class="fa fa-bed"></i> Hotel</a></li>
                        <li id="eventTag"><a><i class="fa fa-calendar"></i> Event</a></li>
                        <li id="eventTag"><a><i class="fa fa-map-marker"></i> Tourist Attraction</a></li>
                        <li id="eventTag"><a><i class="fa fa-cutlery"></i> Restaurant</a></li>
                    </ul>



                    <form class="navbar-form navbar-right" role="search">
                        <div class="form-group">
                            <div class="input-group" style="position:relative; right:20px;">
                                <span class="input-group-addon"><i class="fa fa-search"></i></span>
                                <input type="text" class="form-control" placeholder="Search">

                            </div>
                        </div>

                        <a href="{!! route('logout') !!}"><i class="fa fa-power-off"></i> LOGOUT</a>

                    </form>



                </div>


            </nav>

            <div id="sidebar1" class="navbar navbar-default" style="width:20%; height:100%; position:absolute; left:0px; border-color:#eeeeee;">

                <h6 class="text-center">Logged in as: {!! Auth::user()->fname; !!} {!! Auth::user()->lname; !!}</h6>

                <img id="profilePic2" style="height:150px; width:150px" class="img-circle center-block" src="{!! asset('imgs/profile_pictures/'. Auth::user()->id .'pic.jpg') !!}">
                <h6 class="text-center"><a id="changePicture">Change Profile Picture</a></h6>
                <hr />

                <ul class="text-center nav nav-pills nav-stacked" style="font-family:makhina;color:black; ">
                    <li><a href="">Profile Builder</a></li>
                    <hr />
                    <li><a href="recommend">Recommender Service</a></li>
                    <hr />
                    <li><a href="">Planning Service</a></li>
                    <hr />
                    <li><a id="mapTools2">Map Tools</a></li>
                    <hr />
                    <li><a href="ontology">View Ontology</a></li>
                </ul>


            </div>

            <div id="sidebar2" class="navbar navbar-default" style="width:20%; height:100%; position:absolute; right:0px; border-color:#eeeeee;">

                <div style="height:40%">
                    <h5 style="font-family:makhina;color:#eb6864;" class="text-center">Recommended For You</h5>
                    
                    <div id="recommendations">
                    
                    <div id="accordion">
                        
                        <h3 id="hotels"><i class="fa fa-bed"></i> Hotels</h3> 
                            <div>
                                <div style="font-size:12px;">
                                 <p id="noRMsg">No Recommendations Available</p>
                                    
                                <ul id="recommendedHotelList">
                                        
                                   
                                    
                                </ul>
                                    
                                
                                </div>
                                
                                
                                
                                
                            </div>
                        
                         <h3><i class="fa fa-calendar"></i> Events</h3> 
                            <div>
                                <p style="font-size:12px;">No Recommendations Available</p>
                            </div>
                         
                         <h3><i class="fa fa-map-marker"></i> Attractions</h3> 
                            <div>
                                <p style="font-size:12px;">No Recommendations Available</p>
                            </div>
                         
                         <h3><i class="fa fa-cutlery"></i> Restaurants</h3> 
                            <div>
                                <p style="font-size:12px;">No Recommendations Available</p>
                            </div>
                         
                           
                        
                        
                    </div>
                    
                    </div>
                    
                </div>
                <hr />

                <div style="height:50%">
                    <h5 style="font-family:makhina; color:#eb6864;" class="text-center"><i class="fa fa-comments"></i> Messenger</h5>
                    <div id="messenger">
                    
                    <ul id="userList" class="list-group">
                   
                    
                    
                        
                    
                   
                    </ul>
                    
                    </div>
                </div>

            </div>

            <div id="mainContent" style="position:absolute; left:20%; height:100%; width:60%">


                @yield('mainContent')

            </div>


            <script>

                function showAlerts(text, status) {

                    $('#alertText').text(text);
                    $('#systemAlerts').addClass('alert-' + status);
                    $('#systemAlerts').css('visibility', 'visible');

                    $('#systemAlerts').show();

                    setTimeout(function () {

                        $('#systemAlerts').fadeOut(2000);

                    }, 1000);

                }

                $('#changePicture').click(function () {


                    $('#profilePicModal').modal('show');

                });

                $('body').on('click', '#pictureUpload', function () {

                    $('#imgLoading').css('visibility', 'visible');

                    var fileList = document.getElementById('profilepic').files;
                    var pic = fileList[0];

                    var formData = new FormData();
                    formData.append('pic', pic);
                    formData.append('user_id', '{!! Auth::user()->id !!}');



                    var url = '{!! action('imgController@change') !!}';



                    $.ajax({
                        type: "POST",
                        url: url,
                        data: formData,
                        processData: false,
                        contentType: false

                    }).done(function (msg) {


                        showAlerts(msg, "success");
                        d = new Date();
                        $('#profilePic2').attr("src","{!! asset('imgs/profile_pictures/'. Auth::user()->id .'pic.jpg') !!}?"+d.getTime());
                        $('#profilePicModal').modal('hide');
                        $('#imgLoading').css('visibility', 'hidden');

                    }).fail(function (msg) {

                        showAlerts(msg, "danger");
                        $('#profilePicModal').modal('hide');
                        $('#imgLoading').css('visibility', 'hidden');

                    });

                });



            </script>
            
            <script>
                    
                    $.get('friends/'+{!! Auth::user()->id !!}).done(function(data){
                       
                       for(var i=0; i<data.length; i++)
                       {
                       
                       addFriend(null,data[i]['user_2']);
                       
        
                       }
                       
                       
                    });
                    
                    $('#mapTools2').click(function(){
                       
                       
                       $( "#mapTools" ).toggle( 'fade', {}, 500, function(){
                           
                           $('#skyscanner').draggable({drag : function(){
                                   $('#skyscanner').addClass('pulsating_circle');
                           }, containment: [250,60,500,200] });
                          
                           
                           $('#skyscanner').show('drop',{direction:'up'}, 500);
                           
                       } );
                      
                       
                    });
                    
                    </script>

        </div>

    </body>

</html>