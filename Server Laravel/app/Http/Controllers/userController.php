<?php

namespace App\Http\Controllers;

use Illuminate\Routing\Controller as BaseController;
use App\User;

class userController extends BaseController {

    public function getUserById($id) {

        $user = User::find($id);
        $name = $user->fname . " " . $user->lname;

        return $name;
    }

}
