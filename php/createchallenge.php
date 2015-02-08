<?php

require_once('loader.php');

$title=$_GET["title"];
$desc=$_GET["desc"];

/**
 * Registering a user device in database
 * Store reg id in users table
 */

if (isset($title)) {
    // Store user details in db
    echo createChallenge($title,$desc);
}
?>
