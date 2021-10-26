<?php

$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}

$qury_stationCode_stationName = "SELECT `stationCode`, `stationName`
                                 FROM `station_table`";

// checking and saving query result
if(!$resultStationCodeStationName = $conn->query($qury_stationCode_stationName)){
    echo "Could not execute $result_stationCode_stationName " . $conn->errno;
}

// checking result has any rows or not
if($resultStationCodeStationName->num_rows <= 0){
    echo "No matching result is found.";
    exit();
}

$stationNameStationCodeList = array();  // array to store Station Name and Station Code

while($row = $resultStationCodeStationName->fetch_array()){
    $temp = $row['stationName'] . "-" . $row['stationCode'];
    array_push($stationNameStationCodeList, $temp);
}

echo json_encode(array("server_response" => $stationNameStationCodeList));

?>