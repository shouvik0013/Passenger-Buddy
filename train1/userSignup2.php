<?php

$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}

if(!isset($_POST['userName'])){
 die("Invalid url");
}

$user_name = urldecode($_POST['userName']);
$user_password = urldecode($_POST['userPassword']);
$user_mail = urldecode($_POST['userMail']);
$user_contact_number = urldecode($_POST['userContactNumber']);

// Checking email-id
$mail_id_check_query = "SELECT `userMail` FROM `user_table2` WHERE `userMail`='$user_mail';";

$result3 = $conn->query($mail_id_check_query);
if($result3->num_rows != 0){
    echo json_encode(array("server_response"=>"same_mail"));
    $conn->close();
    die();
}

if($user_name==null || $user_password==null ||
    $user_mail==null || $user_contact_number==null){
        die("Invalid url...any field is null.");
    }

$myKey = "myKey";

$hashed_password = password_hash($user_password.$myKey, PASSWORD_DEFAULT);

$insertDataQuery = "INSERT INTO `user_table2` (`userName`, `userPassword`, `userMail`, `userContactNumber`)
                                VALUES('$user_name', '$hashed_password', '$user_mail', '$user_contact_number');";

if($conn->query($insertDataQuery)){
    echo json_encode(array('server_response'=>'true'));
} else{
    echo json_encode(array("server_response"=>"false"));
}

?>