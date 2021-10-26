<?php
session_start();
$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}
if(isset($_POST['stationName'])){
  $statinName = $_POST['stationName'];
  $stationCode = $_POST['stationCode'];

  $qry = "INSERT INTO `station_table`(`stationCode`, `stationName`) VALUES('$stationCode', '$statinName');";
  if($conn->query($qry) === true){
    echo "Insert&nbsp;successful " . $conn->insert_id;
  } else{
    echo "Could&nbsp;not execute the query." . $conn->connect_errno;
  }
}

$conn->close();
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Insert Station</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="main.css">
    <style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;border-color:#aabcfe;}
.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#669;background-color:#e8edff;}
.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#039;background-color:#b9c9fe;}
.tg .tg-s268{text-align:left}
.tg .tg-0lax{text-align:left;vertical-align:top}
</style>
<script>
function validateForm(){
    var x = document.forms['stationForm']['stationName'].value;
    var y = document.forms['stationForm']['stationCode'].value;
    if(x == "" || y == ""){
        alert("Fields must be filled out.");
        return false;
    }
}
</script>


</head>
<body>
<center>
        <form name="stationForm" action="<?php echo $_SERVER['PHP_SELF'];?>" method="post" onsubmit="return validateForm()">
          <table class="tg">
            <tr>
              <th class="tg-s268"></th>
              <th class="tg-0lax"></th>
            </tr>
            <tr>
              <td class="tg-s268">Station Code</td>
              <td class="tg-0lax"><input type="text" size="20" name="stationCode" required></td>
            </tr>
            <tr>
              <td class="tg-0lax">Station Name</td>
              <td class="tg-0lax"><input type="text" size="20" name="stationName" required></td>
            </tr>
              <tr>
                <td colspan="2" class="tg-0lax"><input type="submit" value="Submit" name="submit"></td>
              </tr>
            </tr>
          </table>
        </form>
      </center>
</body>
</html>