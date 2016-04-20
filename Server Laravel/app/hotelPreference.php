<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class hotelPreference extends Model
{
    
	public $timestamps = false;
        protected $fillable = array('user_id', 'preference', 'hotel_id');
	
}
