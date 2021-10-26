<?php
session_start();
$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}
$trainIdQuery = "SELECT `id` FROM `train_table2` order by id DESC;";

if(!$resultTrainId = $conn->query($trainIdQuery)){
  die("Could not execute $trainIdQuery " . $conn->errno);
}

if($resultTrainId->num_rows == 0){
  die("No trainids found.");
}

$row1 = $resultTrainId->fetch_array();
$trainId = $row1['id'];

$stationIdStationNameSelectionQuery = "SELECT `id`, `stationName` FROM `station_table`;";

if(!$resultStationIdStationName = $conn->query($stationIdStationNameSelectionQuery)){
  die("Could not execute $stationIdStationNameSelectionQuery " . $conn->errno);
}

if($resultStationIdStationName->num_rows == 0){
  die("No stations found.");
}

if(isset($_POST['stationId'])){

  $stationId = $_POST['stationId'];
  $arrival = $_POST['arrival'];
  $departure = $_POST['departure'];

  $insertRouteTableQuery = "INSERT INTO `route_table2`(`trainId`, `stationId`, `arrival`, `departure`) VALUES($trainId, $stationId, '$arrival', '$departure')";

  if($conn->query($insertRouteTableQuery) === true){
    echo "Insert&nbsp;successful " . $conn->insert_id;
  } else{
    echo "Could&nbsp;not execute the query." . $conn->connect_errno;
  }
}

?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Page Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="main.css">
    <style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;border-color:#aabcfe;}
.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#669;background-color:#e8edff;}
.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#039;background-color:#b9c9fe;}
.tg .tg-s268{text-align:left}
.tg .tg-0lax{text-align:left;vertical-align:top}
</style>
</head>
<body>
<center>
        <form name="routeForm" action="<?php echo $_SERVER['PHP_SELF'];?>" method="post" onsubmit="return validateForm()">
          <table class="tg">
            <tr>
              <th class="tg-s268"></th>
              <th class="tg-0lax"></th>
            </tr>
            <tr>
              <td class="tg-s268">Station Name:</td>
              <td class="tg-0lax">
                <select class="" name="stationId">
                  <option value="">--Select Station --</option>
                  <?php if($resultStationIdStationName->num_rows != 0): ?>
                    <?php while($row = $resultStationIdStationName->fetch_array()): ?>
                        <option value="<?= $row['id']; ?>"><?= $row['stationName']; ?></option>
                    <?php endwhile; ?>
                  <?php endif; ?>
                </select>
              </td>
            </tr>
            <tr>
              <td class="tg-0lax">Arrival:</td>
              <td class="tg-0lax"><input type="text" size="20" name="arrival" required></td>
            </tr>
            <!--
            <tr>
              <td class="tg-0lax">Train Description</td>
              <td class="tg-0lax"><input type="radio" name="type" value="Express">Express&nbsp;
              <input type="radio" name="type" value="Local" checked="checked">Local</td>
            </tr>
          -->
            <tr>
              <td class="tg-0lax">Departure:</td>
              <td><input type="text" size="20" name="departure"></td>
            </tr>
            <tr>
              <tr>
                <td colspan="2" class="tg-0lax"><input type="submit" value="Submit" name="submit"></td>
              </tr>
            </tr>
          </table>
        </form>
      </center>
</body>
</html>
