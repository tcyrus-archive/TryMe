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
  echo 5;
    $friends=$friends;
    print_r($friends);
    // Store user details in db
    echo 8;
    $res     = getChallengeList([$friends]);
    echo 7;
    if (res) {
        echo $res;
    } else {
        http_response_code(404);
    }
} else {
  echo 6;
    // user details not found
}
?>
