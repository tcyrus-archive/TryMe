<?php
require_once('loader.php');

// return json response
$json = array();

// GCM Registration ID got from device
$gcm_id = $_GET["gcm_id"];
$fb_id    = $_GET["fb_id"];

/**
 * Registering a user device in database
 * Store reg id in users table
 */
if (isset($gcm_id) and isset($fb_id)) {
    // Store user details in db
    $res = storeUser($fb_id, $gcm_id);

    if (res) {
        http_response_code(200);
    } else {
        http_response_code(404);
    }
} else {
    // user details not found
}
?>
