<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Routing\Controller as BaseController;
use App\hotelPreference;
use App\hotelPreferencesContent;
use App\Hotel;

require_once("http://localhost:8088/JavaBridge/java/Java.inc");

class userPreferenceController extends BaseController
{
    
    public $hotelPreferences;
    public $hotelPreferencesContent;
    public $user_id;
    
    public function __construct() {
        $this->hotelPreferences = new hotelPreference();
        $this->hotelPreferencesContent = new hotelPreferencesContent();
    }
    
    
    public function learnProfile($id){
        
        $model = hotelPreferencesContent::where('user_id', $id)->get();
        
         $cf= new \java("Recommendation.ContentFiltering");
         return java_values($cf->learnProfile($model->toArray()));
        
    }
    
     public function createPreferenceFile(){
        
         \Excel::create('hotel_preferences', function($excel) {
            
            
            $excel->sheet('Excel sheet', function($sheet) {

                $preferenceModel = hotelPreference::all();
                
        $sheet->fromModel($preferenceModel,null,'A1',false,false);

    });
    
    
            

})->store('csv');
        
        
    }
    
     public function createPreferenceFileUser($id){
        
        $this->user_id = $id;
         
         \Excel::create('hotel_preferences_user'.$id, function($excel) {
            
            
            $excel->sheet('Excel sheet', function($sheet) {

                 $preferenceModel = hotelPreferencesContent::where('user_id',$this->user_id)->get();
                 
                
                
        $sheet->fromModel($preferenceModel,null,'A1',false,false);

    });
    
    
            

})->store('csv');
        
        
    }
    
     
    
    public function like($id, $id2, $pref){
     
       
        
      $exist =   $this->hotelPreferencesContent->where(array('user_id'=>$id, 'hotel_id'=>$id2))->first();
      
      if($exist != null)
      {
          return "Already Liked!";
      }
      
      else{
          
          $data = Hotel::find($id2);
          
          $this->hotelPreferencesContent->user_id = $id;
          $this->hotelPreferencesContent->hotel_id = $id2;
          $this->hotelPreferencesContent->name = $data['name'];
          $this->hotelPreferencesContent->type = $data['type'];
          $this->hotelPreferencesContent->price = $data['price'];
          $this->hotelPreferencesContent->stars = $data['stars'];
          $this->hotelPreferencesContent->addr = $data['addr'];
          $this->hotelPreferencesContent->room_type = $data['room_type'];
          $this->hotelPreferencesContent->facilities = $data['facilities'];
          
          $this->hotelPreferencesContent->preference = $pref;
          
          $this->hotelPreferencesContent->save();
          
          $this->createPreferenceFileUser($id);
          
      }
        
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
