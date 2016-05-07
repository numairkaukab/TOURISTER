<?php

namespace App\Http\Controllers;

use Illuminate\Routing\Controller as BaseController;
use App\User;
use Illuminate\Support\Facades\Input;
use Illuminate\Http\Request;
use Illuminate\Contracts\Filesystem\Factory;
use Storage;

class imgController extends BaseController {

    public function change(Request $request) {

        $filename = $request->input('user_id') . "pic.jpg";

        $request->file('pic')->move('../storage/app/profile_pictures', $filename);









        return "Profile Picture Changed Successfully!";
    }
    
    public function newUserMale($id){
        
         $filename = $id . "pic.jpg";
         
         

        Storage::copy('placeholder-profile-male.jpg' , 'profile_pictures/'.$filename);









        return "Profile Picture Changed Successfully!";
    }
    

public function newUserFemale($id){
        
         $filename = $id . "pic.jpg";
         
         

        Storage::copy('placeholder-profile-female.png' , 'profile_pictures/'.$filename);









        return "Profile Picture Changed Successfully!";
    }    
        
    

}
