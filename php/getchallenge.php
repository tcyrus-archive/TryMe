<?php

require_once('loader.php');

$friends=$_GET["id"];
/**
 * Registering a user device in database
 * Store reg id in users table
 */

if (isset($friends)) {
    // Store user details in db
    echo json_encode(getChallenges($id));
}
?>
