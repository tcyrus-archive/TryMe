<?php
function send_push_notification($registration_ids, $message) {
// Set POST variables

$url = 'https://android.googleapis.com/gcm/send';

$fields = array(
'registration_ids' => $registration_ids,
'data' => $message,
);

$headers = array(
'Authorization: key=' . GOOGLE_API_KEY,
'Content-Type: application/json'
);

//print_r($headers);
// Open connection
$ch = curl_init();

// Set the url, number of POST vars, POST data
curl_setopt($ch, CURLOPT_URL, $url);

curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

// Disabling SSL Certificate support temporarly
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

// Execute post
$result = curl_exec($ch);

if ($result === FALSE) 
{
die('Curl failed: ' . curl_error($ch));
}

// Close connection
curl_close($ch);
echo $result;
}
?>
