<?php

namespace App\Http\Controllers;

use Illuminate\Routing\Controller as BaseController;
use Illuminate\Http\Request;
use App\Marker;
use App\Hotel;
use App\hotelPreference;



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
        $hotels->type = $data2['type'];
        $hotels->stars = $data2['stars'];
        $hotels->price = $data2['total'];
        $hotels->prpn = $data2['prpn'];
        $hotels->rating = $data2['rating'];
        $hotels->room_type = $data2['room_type'];
        $hotels->facilities = $data2['features'];
        
        $hotels->save();
        
        $markers->item_id = $hotels->id;

        $markers->save();

        
    }

}
