<!DOCTYPE html>
<html>
    <head>
        <title>TOURISTER</title>


        {!! Html::style('css/bootstrap.css') !!}
        {!! Html::style('css/fa.css') !!}

        {!! Html::script('js/jquery.js') !!}
        {!! Html::script('js/bootstrap.js') !!}




        <style>

            body {

                font-family: bakersfield;






            }

            #bodyContent {
                visibility: hidden;
            }




        </style>

        <script>

            $(window).load(function () {

                $('#load').fadeOut('fast');
                $('#bodyContent').css('visibility', 'visible');

            });

        </script>




    </head>



    <body>

        <div id="load" style="z-index:10; background-color:white;">

            <img style="position:absolute; top:40%; left:45%;" src="{!! asset('imgs/loading.gif') !!}">


        </div>

        <div id="bodyContent">


            @if (count($errors))

            <div style="width:50%; position:absolute; top: 10px; right: 250px;" class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <ul>
                    @foreach($errors->all() as $error)

                    <li>
                        {{ $error }}
                    </li>
                    @endforeach
                </ul>
            </div>

            @endif


            <form method="POST" action="/auth/login">

                {!! csrf_field() !!}

                <input id="user" name="email" class="pull-right" type="text" placeholder="&#xF007 Email" style="font-family: bakersfield, fa; position:relative; right:24px; ">

                <input id="pass" name="password" class="pull-right" type="password" placeholder="&#xF084 Password" style="font-family: bakersfield, fa; position:relative; top:30px; right:-149px; ">
                <button type="submit" class="btn btn-sm btn-primary pull-right" style="position:relative; top:60px; right:-323px;">Login</button>


            </form>

            <nav class="navbar navbar-default" style="background-color: rgba(0,0,0,0.5) ; border-color:rgba(0,0,0,0.5);  top:100px;" >
                <div class="container-fluid">

                    <div class="navbar-header">
                        <a class="navbar-brand" >
                            <img  class="img-thumbnail" alt="Brand" src="{!! asset('imgs/logo.png') !!}" style="width:150px;height:150px;position:absolute;top:-50px ;left:50px;">
                        </a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="active-link"><a href="#"><span style="color:white;"><i class="fa fa-home"></i> Home</span></a></li>

                        <li><a href="#"><span style="color:white;" ><i class="fa fa-suitcase"></i> Plan</span></a></li>
                        <li><a href="#"><span style="color:white;"><i class="fa fa-globe"></i> Maps</span></a></li>
                    </ul>

                </div>



            </nav>

            <div id="banner" style="position:absolute; top:0px; z-index:-1; background-color: black ">

                <img id="bannerImg" src="{!! asset('imgs/banner.jpg') !!}" style="width:100%; opacity: 0.8">


                <h1 style="position:absolute; top: 200px; left: 100px; color: white; font-family: fabfelt; font-size: 100px;">Welcome!</h1>
                <p style="position:absolute; top: 350px; left: 100px; color: white; font-family:modeka; font-size: 50px ">Ontology based<br/>
                    social network for<br/>
                    travellers and tourists</p>
            </div>

            <button class="btn btn-lg btn-danger" style="position:absolute; top: 600px; left: 100px;" data-toggle="modal" data-target="#learnMoreModal">Learn More >></button>


            <div id="learnMoreModal" class="modal fade" role="dialog">
                <div class="modal-dialog">


                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">About The Project</h4>
                        </div>
                        <div class="modal-body">

                            <h3>What is Tourister?</h3>

                            <p>Tourister is a smart Web and Mobile based social network, which also includes </p>

                            <hr />

                            <h3>saldkas</h3>

                        </div>

                    </div>

                </div>
            </div>

            <h1 style="font-family:fabfelt; font-size: 50px; position:absolute; right:450px; top:200px; color:white; transform:rotate(-15deg); z-index: 3;">Get Started!</h1>

            <div id="signup" style="color:white; background-color: rgba(0,0,0,0.5);position:absolute; top: 200px; right: 60px; width:600px; height: 600px; border-radius:25px;">

                <form method="POST" action="/auth/register" >

                    {!! csrf_field() !!}
                    
                    

                    <div class="input-group input-group-lg" style="position:relative; left:50px; top:150px; width:80%; ">

                        <input  name="name" type="text" class="form-control" placeholder="Username">

                    </div>


                    <br />
                    
                     <div class="input-group input-group-lg" style="position:relative; left:50px; top:150px; width:35%; ">

                        <input  name="fname" type="text" class="form-control" placeholder="First Name">

                    </div>


                    <br />
                    
                     <div class="input-group input-group-lg" style="position:relative; left:300px; top:85px; width:35%; ">

                        <input  name="lname" type="text" class="form-control" placeholder="Last Name">

                    </div>


                    <br />
                    
                    <input id="sexVal" name="sex" value="" hidden> 
                    
                    <div class="form-inline input-group input-group-lg" style="position:relative; top:90px; left:50px;">
                        
                        <label for="sex">Sex: </label>
                        
                        <div class="radio" style="position:relative; left:20px;">
  <label><input type="radio"  id="sex" value="m"> Male</label>
</div>
                        
<div class="radio" style="position:relative; left:30px;">
  <label><input type="radio"  id="sex" value="f"> Female</label>
</div>
                        
                        
                    </div>

                    <div class="input-group input-group-lg" style="position:relative; left:50px; top:130px; width:80%; ">

                        <input  name="password" type="password" class="form-control" placeholder="Password">

                    </div>

                    <br />

                    <div class="input-group input-group-lg" style="position:relative; left:50px; top:130px; width:80%; ">

                        <input  name="password_confirmation" type="password" class="form-control" placeholder="Confirm Password">

                    </div>

                    <br />

                    <div class="input-group input-group-lg" style="position:relative; left:50px; top:130px; width:80%; ">

                        <input   name="email" type="text" class="form-control" placeholder="Email">

                    </div>



                    <button id="submitBtn" type="submit" class="btn btn-success btn-block" style="width:80%; position:absolute; bottom:20px; left:50px;">Sign Up</button>

                </form>
            </div>


            <footer id="footer" style="position: absolute; background-image:url({!! asset('imgs/footer.png') !!}); background-repeat: no-repeat; height:200px; width:100%;">

                <p style="position: absolute; bottom: 10px; left:10px; color:white;">2016 &copy; Being developed as part of Final Year Project, FAST-NUCES, Chiniot Faisalabad Campus</p>

                <ul style="position: absolute; bottom: 30px; right:180px; color:white;"> Group Members:
                    <li>Numair Kaukab 12f-8250</li>
                    <li>Mehmood Akhtar 13f-8260</li>
                    <li>Attia Nassar 12F-8150</li>

                </ul>

                <img src="{!! asset('imgs/nu.png') !!}" style="position: absolute; bottom: 30px; right:50px; width:100px; height:100px;" >

            </footer>


            <script>
                
                
                $('#submitBtn').click(function(){
                   
                   $('#sexVal').val($('#sex').val());
                   
                });

                $(document).height(900);

                var height = 900 - 115;

                var bannerId = 0;
                var bannerName = "banner";
                var count = 0;



                $('#footer').css('top', height);

                setInterval(function () {

                    if (count == 3)
                    {
                        bannerId = 0;
                        count = 0;
                    }


                    bannerId = bannerId + 1;
                    bannerName = bannerName + bannerId;
                    count++;



                    $('#bannerImg').fadeTo(1000, 0.2, function () {

                        $('#bannerImg').attr('src', '../imgs/' + bannerName + '.jpg').fadeTo(1000, 0.8);
                        bannerName = "banner";


                    });


                }, 10000);




            </script>

        </div>

    </body>
</html>
