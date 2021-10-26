<?php
    $conn = new mysqli('localhost', 'root', '', 'my_train_db');
    if($conn === false){
        echo "Connection can not be established".mysqli_connect_errno();
    }
    echo "Done"."<br>";

    $sourceStationIdQuery = "SELECT `id` FROM `station_table` WHERE `stationName`='SEALDAH';";
    $destinationStationIdQuery = "SELECT `id` FROM `station_table` WHERE `stationName`='DUM DUM JUNCTION';";

    // checking source station id query
    if(!$resultSourceStation = $conn->query($sourceStationIdQuery)){
        die("Could not execute $sourceStationIdQuery " . $conn->errno);
    }
    // checking destination station id query
    if(!$resultDestinationStation = $conn->query($destinationStationIdQuery)){
        die("Could not execute $destinationStationIdQuery " . $conn->errno);
    }

    // GETTING SOURCE STATION ID
    $row5 = $resultSourceStation->fetch_array();
    $sourceStationId = $row5['id'];

    // GETTING DESTINATION STATION ID
    $row6 = $resultDestinationStation->fetch_array();
    $destinationStationId = $row6['id'];

    $test_qry1 = "SELECT `trainId` FROM `route_table` WHERE `stationId`= $sourceStationId ORDER BY arrival ASC";
    $test_qry2 = "SELECT `trainId` FROM `route_table` WHERE `stationId`= $destinationStationId ORDER BY arrival ASC";

    // check and save result of query1
    if(!$result1 = $conn->query($test_qry1)){
        die("Could not execute the query $test_qry1".$conn->errno);
    }
    // check and save result of query2
    if(!$result2 = $conn->query($test_qry2)){
        die("Could not execute the query $test_qry2".$conn->errno);
    }

    // check the result1 has any rows or not
    if(!($result1->num_rows > 0)){
        echo "No matching results were found.";
        die("No matching results were found.");
    }

    // check the result2 has any rows or not
    if(!($result2->num_rows > 0)){
        echo "No matching results were found.";
        die("No matching results were found.");
    }

    // saving train's id of source and destination station
    $sourceStationTrainIds = array();
    $destinationStationTrainIds = array();

    // Loop through the result1
    while($row1 = $result1->fetch_array()){
        array_push($sourceStationTrainIds, $row1[0]);
    }

    // loop through the result2
    while($row2 = $result2->fetch_array()){
        array_push($destinationStationTrainIds, $row2[0]);
    }

    //printing train ids of source station
    foreach($sourceStationTrainIds as $c){
        echo $c."<br>";
    }

    echo "<br><br>";

    // printing train ids of destination stations
    foreach($destinationStationTrainIds as $c){
        echo $c."<br>";
    }

    echo "<br><br>";

    // variable to store matching train ids
    $matchedIds = array();

    // finding common trains between stations
    foreach($sourceStationTrainIds as $c){
        foreach($destinationStationTrainIds as $d){
            if($c == $d){
                array_push($matchedIds, $c);
            }
        }
    }
    echo "<br><br>";
    echo "Matched trains: <br>";
    // PRINTING THE TRAIN IDS WHICH MATCHED
    foreach($matchedIds as $c){
        echo $c."<br>";
    }
    
    echo "<br><br>";

    // COUNTING THE NUMBER OF TRAINS
    $trainsCount = sizeof($matchedIds);

    // ARRAY TO STORE SCHEDULE INFO
    $scheduleInfo = array();

    $trainSpecificSchedule = array();   // STORE SCHEDULE OF A PARTICULAR TRAIN

    // FETCHING STATION IDS AND ARRIVAL TIMES OF INDIVIDUAL TRAIN
    for($i=0; $i<$trainsCount; $i++){
        $scheduleInfoQry = "SELECT `stationId`, `arrival` FROM `route_table` WHERE `trainId`=$matchedIds[$i];";
        $scheduleInfoResult = $conn->query($scheduleInfoQry);
        $trainId = $matchedIds[$i]; // STORE TRAIN ID
        $stationId; // STORE STATION ID
        $arrivalTime;   // STORE ARRIVAL TIME
        $stationAndArrivalArray = array();  // TO STORE SPECIFIC STATION AND IT'S ARRIVAL TIME
        while($row = $scheduleInfoResult->fetch_array()){
            $stationId = $row['stationId'];
            $arrivalTime = $row['arrival'];
            array_push($stationAndArrivalArray, array("stationId"=>$stationId, "arrival"=>$arrivalTime));
        }
        $trainSpecificSchedule["$trainId"] = $stationAndArrivalArray; 
    }
    
    echo "PRINTING DETAILS OF TRAINS: <br><br>";
    foreach($trainSpecificSchedule as $key => $value){
        echo "Train No: ".$key."<br>";
        foreach($value as $info){
            echo "Station Id: ".$info['stationId']."<br>";
            echo "Arrival Time: ".$info['arrival']."<br>";
        }
        echo"<br><br><br>";
    }
    
    echo json_encode(array("server_response" => $trainSpecificSchedule));
    echo "<br><br>";

    $allTrainRouteDetails = array();   // STORES FULL DETAILS OF ALL TRAINS
                                    // TRAIN NUMBER, TRAIN NAME
                                    // ALL STATION'S NAMES, CODE, ARRIVAL TIME


    foreach($trainSpecificSchedule as $key => $value){
        // FETCHING TRAIN NAME AND TRAIN NUMBER FROM TRAIN TABLE
        $qry_for_train_name_and_train_no = "SELECT `trainName`, `trainNo` FROM `train_table` WHERE `id`=$key;";
        
        // STORING TRAIN NAME AND TRAIN NUMBER
        $result_train_name_train_no = $conn->query($qry_for_train_name_and_train_no);
        
        $row3 = $result_train_name_train_no->fetch_array();
        $trainNo = $row3['trainNo'];
        $trainName = $row3['trainName'];
        
        echo "Train Number = " . $trainNo . "<br>" . "Train Name = " . $trainName . "<br>";

        $scheduleArray = array();   // SAVES STATION-NAME, STATION-CODE, STATION-ARRIVAL TIME

        foreach($value as $info){
            $tempStationId = $info['stationId'];
            $qry_for_stationNam_and_stationCode = "SELECT `stationName`, `stationCode` FROM `station_table` WHERE `id` = $tempStationId;";
            $result4 = $conn->query($qry_for_stationNam_and_stationCode);
            $row4 = $result4->fetch_array();
            $stationCode = $row4['stationCode'];
            $stationName = $row4['stationName'];
            array_push($scheduleArray, array('stationName'=>$stationName, 'stationCode'=>$stationCode, 'arrival'=>$info['arrival']));
        }
        array_push($allTrainRouteDetails, array('trainName'=>$trainName, 'trainNumber'=>$trainNo, 'schedule'=>$scheduleArray));
    }

    echo "<br><br><br>";

    // PRINTING ALL TRAINS AND THEIR CORRESPONDING SCHEDULE
    foreach($allTrainRouteDetails as $info){
        echo "Train Name: &nbsp;" . $info['trainName'] . "<br>";
        echo "Train Number: &nbsp;" . $info['trainNumber'] . "<br>";
        $temp_scheduleArray = $info['schedule'];        // Temporary variable to store schedule a train
        foreach($temp_scheduleArray as $c){
            echo "Station Name:&nbsp;&nbsp;" . $c['stationName'] . "&nbsp;&nbsp;Station Code: &nbsp;" . $c['stationCode'] . "&nbsp;&nbsp;Arrival Time: " . $c['arrival']."<br>";
        }

        echo "<br><br>";
    }

    echo "<br><br><br>";
    echo "JSON ARRAY REPRESENTATION OF THE QUERY OF THE USER: <br><br>";

    // JSON ARRAY CONVERSION
    echo json_encode(array("server_response"=>$allTrainRouteDetails));

    $conn->close();

 ?>
