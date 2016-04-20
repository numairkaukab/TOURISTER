<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Routing\Controller as BaseController;
use App\hotelPreference;


class userPreferenceController extends BaseController
{
    
    public $hotelPreferences;
    
    public function __construct() {
        $this->hotelPreferences = new hotelPreference();
    }
    
     public function createPreferenceFile(){
        
         \Excel::create('hotel_preferences', function($excel) {
            
            
            $excel->sheet('Excel sheet', function($sheet) {

                $preferenceModel = hotelPreference::all();
                
        $sheet->fromModel($preferenceModel,null,'A1',false,false);

    });
            

})->store('csv');
        
        
    }
    
    
    public function updateHotelPreference(Request $request)
    {
        
        $data = $request->input('data');
        
        
        $exist = $this->hotelPreferences->where(array('user_id'=>$data['user'], 'hotel_id'=>$data['item']))->first();
        
        if($exist != null){
            
        $this->hotelPreferences->where(array('user_id'=>$data['user'], 'hotel_id'=>$data['item']))->update(array('preference' => $data['rating']));
        
        
           $this->createPreferenceFile();
           return "Preference Updated2";
        }
        
        else{
            
       
        $this->hotelPreferences->user_id = $data['user'];
        $this->hotelPreferences->hotel_id = $data['item'];
        $this->hotelPreferences->preference = $data['rating'];
        
        $this->hotelPreferences->save();
        
         }
       
        $this->createPreferenceFile();
        return 'Preference Updated';
        
    }
    
    public function getHotelPreference($user_id, $item_id){
        
        $result = hotelPreference::where(array('user_id'=> $user_id, 'hotel_id' => $item_id ))->get();
        
        if($result->first() == null){
            
           return 0;
        }
        
        else{
        return $result;
        }
        
        
    }
    
   
    
    
}
