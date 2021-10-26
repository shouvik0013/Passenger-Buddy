<?php

$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}

if(!isset($_POST['userName'])){
    die('Invalid url.');
}

$myKey = "myKey";

$user_name = urldecode($_POST['userName']);
$user_password = urldecode($_POST['userPassword']);

$user_info_qry = "SELECT * FROM `user_table` WHERE `userName` = '$user_name';";

if(!$result = $conn->query($user_info_qry)){
    die("Couldn't execute query $user_info_qry " . $conn->errno);
}

if($result->num_rows <= 0){
    die("No matching user found.");
}

$row = $result->fetch_array();
if(password_verify($user_password.$myKey, $row['userPassword'])){
    echo json_encode(array("server_response"=>"true"));
} else{
    echo json_encode(array("server_response"=>"false"));
}


?>