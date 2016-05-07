<?php

namespace App\Http\Controllers;


use Illuminate\Routing\Controller as BaseController;
use App\Hotel;


class hotelController extends BaseController
{
    
    public function getHotelDetails($id){
        
       $row = Hotel::find($id);
       
       return $row;
        
    }
    
    
}


