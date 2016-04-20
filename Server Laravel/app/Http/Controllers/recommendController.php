<?php

namespace App\Http\Controllers;


use Illuminate\Routing\Controller as BaseController;

require_once("http://localhost:8087/JavaBridge/java/Java.inc");


class recommendController extends BaseController
{
    
    public function collaborativefilter(){
        
        
        $dispatcher = new \java("Main.Dispatcher");
        $returnArray = java_values($dispatcher->recommend());
        return $returnArray;
        
        
    }
    
}

