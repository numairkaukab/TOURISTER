<?php

namespace App\Http\Controllers;

use Illuminate\Routing\Controller as BaseController;
use App\Marker;

class dataFetchController extends BaseController {

    public function fetchData() {

        $skyscannerurl = 'http://partners.api.skyscanner.net/apiservices/hotels/liveprices/v2/UK/EUR/en-GB/27539733/2016-04-04/2016-11-04/2/1';
        $apiKEY = "prtl6749387986743898559646983194";



        $curl = new \anlutro\cURL\cURL;

        $url = $curl->buildUrl($skyscannerurl, ['apiKey' => $apiKEY]);



        $response = $curl->get($url);

        $url = "http://partners.api.skyscanner.net" . $response->headers['location'] . "";



        $response = $curl->get($url);

        $datajson = $response->body;

        $jsondecode = json_decode($datajson, true);


        var_dump($jsondecode['hotels']);

        foreach ($jsondecode['hotels'] as $row) {
            $markers = new Marker;
            $markers->lat = $row['latitude'];
            $markers->lng = $row['longitude'];
            $markers->name = $row['name'];
            $markers->addr = $row['address'];
            $markers->tagged_by = "SkyScanner";
            $markers->type = "hotel";



            $markers->save();
        }

        echo "Done";
    }

}
