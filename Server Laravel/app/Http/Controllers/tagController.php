<?php

namespace App\Http\Controllers;

use Illuminate\Routing\Controller as BaseController;
use Illuminate\Http\Request;
use App\Marker;
use App\Hotel;
use App\hotelPreference;

//require_once("http://localhost:8087/JavaBridge/java/Java.inc");

class tagController extends BaseController {

    public function hotelTag(Request $request) {


        $data = $request->input('data');
        $data2 = $request->input('data2');




        $markers = new Marker();

        $markers->name = $data['name'];
        $markers->tagged_by = $data['taggedby'];

        $markers->addr = $data['addr'];
        $markers->type = $data['type'];

        $markers->lat = $data['lat'];
        $markers->lng = $data['lng'];

        
        
        $hotels = new Hotel();
        
        $hotels->name = $data['name'];
        $hotels->addr = $data['addr'];
        
        $hotels->save();
        
        $markers->item_id = $hotels->id;

        $markers->save();

        //$ontology = new \java("Ontology");
        //echo $ontology->updateOntologyWithHotel($data2);
    }

}
