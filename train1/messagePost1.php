<?php

// SETTING UP ASIA-KOLKATA TIME ZONE
date_default_timezone_set("Asia/Calcutta");
$server_time_stamp = date("j-m-Y h:i:s A");

$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}

if(!isset($_POST['post_head'])){
    die('Invalid url.');
}

$post_head = urldecode($_POST['post_head']);
$post_desc = urldecode($_POST['post_desc']);
$uId = urldecode($_POST['uId']);

$insert_message_qry = "INSERT INTO `tbl_post`(`uId`, `post_head`, `post_desc`, `date_time`)
                                    VALUES('$uId', '$post_head', '$post_desc', '$server_time_stamp');";

if($conn->query($insert_message_qry)){
    echo json_encode(array('server_response'=>'true'));
} else{
    echo json_encode(array('server_response'=>'false'));
}

$conn->close();

?>