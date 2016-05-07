<?php

namespace App\Http\Controllers;

use Illuminate\Routing\Controller as BaseController;
use App\Marker;
use Illuminate\Http\Request;

class dataFetchController extends BaseController {

    public function autoSuggest(Request $request){

        $curl = new \anlutro\cURL\cURL;
       $location = $request->input('data');
       
        $autosuggesturl = 'http://partners.api.skyscanner.net/apiservices/hotels/autosuggest/v2/UK/EUR/en-GB/'.$location.'?apikey=prtl6749387986743898559646983194';
        $response = $curl->get($autosuggesturl);
        return json_decode($response->body, true);
        
        
    }
    
    public function fetchData(Request $request){
        
        $loc = $request->input('data');
        $curl = new \anlutro\cURL\cURL;
        
        $skyscannerurl = 'http://partners.api.skyscanner.net/apiservices/hotels/liveprices/v2/UK/EUR/en-GB/'.$loc.'/2016-05-14/2016-05-21/2/1';
        $apiKEY = "prtl6749387986743898559646983194";



        

        $url = $curl->buildUrl($skyscannerurl, ['apiKey' => $apiKEY]);



        $response = $curl->get($url);
        
        

        $url = "http://partners.api.skyscanner.net" . $response->headers['location'] . "";



        $response = $curl->get($url);

        $datajson = $response->body;

        $jsondecode = json_decode($datajson, true);


        return $jsondecode;

       
    }

}
