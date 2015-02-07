<?php

        function getChallengeList($friends) {
          $result = mysql_query("SELECT DISTINCT challenges FROM ".implode(',',$friends).") or die(mysql_error());
          return $result;
        }

 
	//Storing new user and returns user details
    
	function storeUser($user_fb,$gcm_regid)
	{
		if (doesUserExist($user_fb))
		{
			return true;
		}

		// insert user into database

		$result = mysql_query("INSERT INTO users (user_fb,gcm_regid) VALUES ('$user_fb','$gcm_regid')");
         
        // check for successful store

        if ($result) {
            // get user details

            $result = mysql_query( "SELECT * FROM users WHERE gcm_regid = $gcm_regid") or die(mysql_error());

            // return user details 

            if (mysql_num_rows($result) > 0) {  return mysql_fetch_array($result);}
	    else {return false;}
             
        }
	else {return false;}
    }
 
	// Getting all registered users
	function getAllUsers() 
	{
		$result = mysql_query("SELECT * FROM users");
		return $result;
	}
 
	// Validate user
	function doesUserExist($user_fb)
	{
        $result = mysql_query("SELECT fb_id from users WHERE fb_id = '$fb_id'");

		if ($result != false)
		{
			$NumOfRows = mysql_num_rows($result);
		}
		else
		{
			var_dump(mysql_error());
			return false;
		}

        if ($NumOfRows > 0)
		{
			// user existed
			return true;
        } 
		else
		{
			// user not existed
			return false;
        }
	}
     
	//Sending Push Notification

	function send_push_notification($registration_ids, $message) 
	{
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
