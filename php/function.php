<?php

        function getChallengeList($friends) {
          $arr = array();
          for ($friend as $friends) {
            $result=mysql_query("SELECT challenges FROM '$friend'");
            $arr2=array();
            while ($r1 = mysql_fetch_assoc($result)) {
	             array_push($arr2,mysql_query("SELECT * FROM challenges WHERE ID=$r1"));
	          }
            $arr[$friend]=$arr2;
          }
          return $arr;
        }


	//Storing new user and returns user details

	function storeUser($fb_id,$gcm_id) {
		if (doesUserExist($user_fb)) {
			return true;
		}

		// insert user into database

		$result = mysql_query("INSERT INTO users (fb_id,gcm_id) VALUES ('$fb_id','$gcm_id')");
		$result = mysql_query("CREATE TABLE $fb_id (challenge INT(11))");
    mkdir("uploads/".$fb_id);
  }

	// Getting all registered users
	function getAllUsers()
	{
		$result = mysql_query("SELECT * FROM users");
		return $result;
	}

	// Validate user
	function doesUserExist($fb_id) {
        	$result = mysql_query("SELECT * FROM users WHERE fb_id = '$fb_id' LIMIT 1");
		if ($result != false) {
			if (mysql_num_rows($result)>0) {
				return true;
			} else {
				return false;
			}
		} else {
			var_dump(mysql_error());
			return false;
		}
	}
?>
