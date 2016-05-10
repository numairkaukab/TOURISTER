<?php

namespace App\Http\Controllers;


use App\Hotel;
use Illuminate\Http\Request;
use Illuminate\Routing\Controller as BaseController;

require_once("http://localhost:8088/JavaBridge/java/Java.inc");

class contentFilteringController extends BaseController
{
    
    public function addHotelToIndex($id){
        
        $row = Hotel::find($id);
        
        
        $cf= new \java("Recommendation.ContentFiltering");
        $cf->addHotelToIndex($row->toArray());
        
        
    }
    
    public function generateUserProfile(Request $request){
        
         $data = $request->input('data');
        
         $cf= new \java("Recommendation.ContentFiltering");
         return java_values($cf->generateUserProfile($data));
        
    }
    
    public function filter($id){
        
          $cf= new \java("Recommendation.ContentFiltering");
          return java_values($cf->hotelFilter($id));
        
    }
    
    
}

