<?php
require_once('loader.php');
 
// return json response 
$json = array();
 
$friends=$_GET["friends"];

/**
 * Registering a user device in database
 * Store reg id in users table
 */
if (isset($friends))
{
    // Store user details in db
    $res = getChallengeList($friends);
 
    if(res)
    {
    	echo $res;
    }
    else
    {
		http_response_code(404);
    }
} 
else 
{
    // user details not found
}
?>
