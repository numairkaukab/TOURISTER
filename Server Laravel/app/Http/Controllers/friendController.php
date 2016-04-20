<?php

namespace App\Http\Controllers;

use Illuminate\Routing\Controller as BaseController;
use App\Friend;


class friendController extends BaseController
{
     public function addFriend($id1, $id2){
         
         
      
         $friends = new Friend();
         
         $friends->user_1 = $id1;
         $friends->user_2 = $id2;
         
         $friends->save();
         
         return "Friend Added!";
         
         
         
     }
     
     public function getFriends($id){
         
         $friends = Friend::where('user_1', $id)->get();
         
         if($friends->first()==null){
             
             return "No Friends";
         }
         
         else{
             
             
         return $friends;
         }
         
     }
     
     public function removeFriend(){
         
     }
}