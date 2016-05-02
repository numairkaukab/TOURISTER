<?php

use App\Marker;

/*
  |--------------------------------------------------------------------------
  | Routes File
  |--------------------------------------------------------------------------
  |
  | Here is where you will register all of the routes in an application.
  | It's a breeze. Simply tell Laravel the URIs it should respond to
  | and give it the controller to call when that URI is requested.
  |
 */



Route::get('fetch', 'dataFetchController@fetchData');

Route::get('markerXML', function() {

    $markers = Marker::all();

    $xml = new XMLWriter();
    $xml->openMemory();
    $xml->startDocument();
    $xml->startElement('markers');
    foreach ($markers as $marker) {
        $xml->startElement('marker');
        $xml->writeAttribute('lat', $marker->lat);
        $xml->writeAttribute('lng', $marker->lng);
        $xml->writeAttribute('type', $marker->type);
        $xml->writeAttribute('name', $marker->name);
        $xml->writeAttribute('addr', $marker->addr);
        $xml->writeAttribute('taggedby', $marker->tagged_by);
        $xml->writeAttribute('item_id', $marker->item_id);
        $xml->endElement();
    }
    $xml->endElement();
    $xml->endDocument();

    $content = $xml->outputMemory();
    $xml = null;

    return response($content)->header('Content-Type', 'text/xml');
});

Route::post('hotelTag', 'tagController@hotelTag');

Route::post('img', 'imgController@change');

Route::get('ontologies/tourism', function() {

    $file = File::get('C:\TouristerWorkspace\Ontologies\tourism.owl');
    return $file;
});

Route::get('username/{id}', ['as' => 'getUserById', 'uses' => 'userController@getUserById']);

Route::post('updateHotelPreference', 'userPreferenceController@updateHotelPreference');

Route::get('recommender/{id}', 'recommendController@collaborativefilter');

Route::get('item/{id}', 'itemController@getItemName');

Route::post('friends/{id1}/{id2}', 'friendController@addFriend');

Route::get('friends/{id}', 'friendController@getFriends');

Route::get('hotelPreference/{id1}/{id2}', 'userPreferenceController@getHotelPreference');



/*
  |--------------------------------------------------------------------------
  | Application Routes
  |--------------------------------------------------------------------------
  |
  | This route group applies the "web" middleware group to every route
  | it contains. The "web" middleware group is defined in your HTTP
  | kernel and includes session state, CSRF protection, and more.
  |
 */







Route::group(['middleware' => ['web']], function () {

    Route::get('auth/login', 'Auth\AuthController@getLogin');
    Route::post('auth/login', 'Auth\AuthController@postLogin');
    Route::get('auth/logout', ['as' => 'logout', 'uses' => 'Auth\AuthController@logout']);

    Route::get('auth/register', 'Auth\AuthController@getRegister');
    Route::post('auth/register', 'Auth\AuthController@postRegister');


    Route::get('recommend', function(){
       
        
        return view('recommend');
        
    });

    Route::get('/', function () {


        if (Auth::check()) {
            return view('dashboard');
        } else {

            return view('welcome');
        }
    });


    Route::get('dashboard', ['middleware' => 'auth', function() {
            return view('dashboard');
        }]);

    Route::get('ontology', function() {

        return view('ontology');
    });
});
