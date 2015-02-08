<?php

require_once('loader.php');
require_once('push.php');

$fb_id=$_GET["fb_id"];
$message=$_GET["message"];

/**
 * Registering a user device in database
 * Store reg id in users table
 */

if (isset($fb_id)) {
    // Store user details in db
    $gcm_id=getGCM($fb_id);
    send_push_notification([$gcm_id],$message);
}
?>
