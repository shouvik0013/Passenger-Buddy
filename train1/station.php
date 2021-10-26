<?php
session_start();
if(isset($_GET['idd'])){

    $conn = new mysqli('localhost', 'rohon', 'password', 'test_train');
    if($conn === false){
    die("ERROR: Unable to access database " . mysqli_connect_error());
    }

    $stationName = $_POST['station_name'];
    $stationCode = $_POST['station_code'];

    $qry = "INSERT INTO tbl_station (stationName, stationCode) VALUES('$stationName','$stationCode');";
    if($conn->query($qry) === true){
        echo "Insert&nbsp;successful " . $conn->insert_id;
    } else{
        echo "Could&nbsp;not execute the query." . $conn->connect_errno;
    }
    $conn->close();

}
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Station Add</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="main.css">
    <script>
        function validateForm(){
            var x = document.forms['formStation']['station_name'].value;
            var y = document.forms['formStation']['station_code'].value;
            if(x == "" || y == ""){
                alert("Fields must be filled out.");
                return false;
            }
        }
    </script>
</head>
<body>
    <table style="margin:1em auto;" class="tbl">
        <form name="formStation" action="<?php echo $_SERVER['PHP_SELF'];?>?idd=222" method="POST" onsubmit="return validateForm()">
            <tr>
                <td style="background-color:#063; color:#FFF; font-family:Georgia, 'Times New Roman', Times, serif; font-size:14px;">Station Name:</td>
                <td><input type="text" name="station_name" size="10" required</td>
            </tr>
            <tr>
                <td style="background-color:#063; color:#FFF; font-family:Georgia, 'Times New Roman', Times, serif; font-size:14px;">Station Code:</td>
                <td><input type="text" name="station_code" size="10" required></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" name="submit" value="SUBMIT"></td>
            </tr>
        </form>
    </table>
</body>
</html>
