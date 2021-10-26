<?php

$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}

if(!isset($_POST['userMail'])){
    die('Invalid url.');
}

$myKey = "myKey";

$user_mail = urldecode($_POST['userMail']);
$user_password = urldecode($_POST['userPassword']);

$user_info_qry = "SELECT * FROM `user_table2` WHERE `userMail` = '$user_mail';";

if(!$result = $conn->query($user_info_qry)){
    die("Couldn't execute query $user_info_qry " . $conn->errno);
}

if($result->num_rows <= 0){
    die("No matching user found.");
}

$row = $result->fetch_array();
if(password_verify($user_password.$myKey, $row['userPassword'])){
    $uId = $row['id'];
    $userMail = $row['userMail'];
    $userName = $row['userName'];
    echo json_encode(array("server_response"=>array('status'=>'true', 'uId'=>$uId, 'userName'=>$userName, 'userMail'=>$userMail)));
} else{
    echo json_encode(array("server_response"=>array('status'=>'false', 'uId'=>'none', 'userName'=>'none', 'userMail'=>'none')));
}


?>