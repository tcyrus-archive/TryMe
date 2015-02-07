<?php
echo 1;
require_once('loader.php');
echo 2;
$friends=$_POST["friends"];
echo 3;
/**
 * Registering a user device in database
 * Store reg id in users table
 */

//echo '{"challenges":[{"friend":"782093555198877","desc":"This is an example challenge used for testing purposes."}]}';
echo 4;
if (isset($friends)) {
    $friends=$friends;
    print_r($friends);
    // Store user details in db
    echo json_encode(getChallengeList($friends));
}
?>
