<?php

namespace App\Http\Controllers;

use Illuminate\Routing\Controller as BaseController;
use App\Marker;


class itemController extends BaseController
{
    
    public function getItemName($id){
        
        $markers = Marker::find($id);
        $name = $markers->name;
        return $name;
         
        
        
        
        
    }
    
    
}

