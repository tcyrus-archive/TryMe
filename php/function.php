<?php

        function getChallengeList($friend) {
          $result = mysql_query("SELECT challenges FROM '$friends'");
          $arr = array();
          while ($r1 = mysql_fetch_assoc($result)) {
	     array_push($arr,mysql_query("SELECT * FROM challenges WHERE ID=$r1"));
	  }
          return $arr;
        }


	//Storing new user and returns user details

	function storeUser($user_fb) {
		if (doesUserExist($user_fb)) {
			return true;
		}

		// insert user into database

		$result = mysql_query("INSERT INTO users user_fb VALUES '$user_fb'");

        // check for successful store

        if ($result) {
            // get user details

            $result = mysql_query("SELECT * FROM users WHERE user_fb = '$user_fb'") or die(mysql_error());

            // return user details

            if (mysql_num_rows($result) > 0) {
		return true;
	    } else {
		var_dump(mysql_error());
		return false;
	    }

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
	function doesUserExist($user_fb) {
        	$result = mysql_query("SELECT FROM users WHERE fb_id = '$fb_id'");
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
