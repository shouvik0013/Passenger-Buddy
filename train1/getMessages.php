<?php

$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}

$messages_qry = "SELECT * FROM `tbl_post` ORDER BY `id` desc;";

$messages_array = array();

if(!$result = $conn->query($messages_qry)){
    die("Could not execute the query");
}

if($result->num_rows <= 0){
    echo "No messages found.";
    die("No matching user found.");
}

while($row = $result->fetch_array()){
    $message_post_head = $row['post_head'];
    $message_post_desc = $row['post_desc'];
    $message_date_time = $row['date_time'];
    $uId = $row['uId'];
    $fetch_user_name_qry = "SELECT `userName` FROM `user_table2` WHERE `id` = $uId";
    $result2 = $conn->query($fetch_user_name_qry);
    $row2 = $result2->fetch_array();
    $user_name = $row2['userName'];
    array_push($messages_array, array("user_name"=>$user_name,"post_head"=>$message_post_head, "post_desc"=>$message_post_desc,"date_time"=>$message_date_time));
}

echo json_encode(array("server_response"=>$messages_array));

?>