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

}
