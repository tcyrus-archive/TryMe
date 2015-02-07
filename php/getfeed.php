<?php
require_once('loader.php');
 
$friends=$_GET["friends"];

/**
 * Registering a user device in database
 * Store reg id in users table
 */

if (isset($friends))
{
    $friends=json_decode($friends);
    // Store user details in db
    $res = getChallengeList($friends);
    echo '{"challenges":[{"friend":"782093555198877","desc":"This is an example challenge used for testing purposes."}]}';
/*    if(res)
    {
    	echo json_encode($res);
    }
    else
    {
       http_response_code(404);
*/    }
} 
else 
{
    // user details not found
}
?>
