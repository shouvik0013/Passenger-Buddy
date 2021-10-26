<?php
session_start();
include("conn.php");

// Checking the connection object
try{
    if($conn === false){
        echo("Connection failed.");
    }
} catch(Exception $e){
    echo "Connection failed.";
    echo $e->getMessage();
    die("Error");
}
    // POST UPDATE
    if(isset($_GET['idd'])){
    $id = $_POST['id'];
    $stationName = $_POST['name'];
    $stationCode = $_POST['code'];
    $qry2 = "UPDATE `tbl_station` SET `stationName`='$stationName', `stationCode`='$stationCode' WHERE `id`=$id";

    // checking query
    if($conn->query($qry2))
    {
        $_SESSION['msg'] = "Updated successfully";
        header("location:index.php");
    }else{
        $_SESSION['fmsg']="Failed" ;
        header("location:".$_SERVER['PHP_SELF']);
        echo("ERROR");
        exit();
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Edit Station</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="main.css">
    <script src="main.js"></script>
</head>
<body>

<?php 
$id = $_GET['id'];
$qry1 = "SELECT stationCode, stationName FROM tbl_station WHERE id = $id;";
// CHECKING THE QUERY IS EXECUTABLE OR NOT
if(!($result = $conn->query($qry1))){
    echo "Couldn't execute $qry1".$conn->errno;
    exit("ERROR");
}
// check result has any rows or not
if(!($result->num_rows > 0)){
echo "No record matching your query were found.";
die("ERROR");
} 
// GET THE STATION NAME AND STATION CODE
$row = $result->fetch_array();
$stationName = $row['stationName'];
$stationCode = $row['stationCode']; 
?>

    <center>
        <form name="frm2" action="<?php echo $_SERVER['PHP_SELF'];?>?idd=222" method="post">
            <table width="450" border="1" class="tbl">
                <tr style="background-color:#063; color:#FFF; font-family:Georgia, 'Times New Roman', Times, serif; font-size:14px;">
                        <td>Station Id</td>
                        <td>Station Name</td>
                        <td>Station Code</td>
                        <td>Perform</td>
                </tr>
                <tr>
                    <td><?php echo htmlentities($id);?></td>
                    <input type="hidden" name="id" value="<?php echo $id;?>">
                    <td><input type="text" name="name" value="<?php echo $stationName;?>"></td>
                    <td><input type="text" name="code" value="<?php echo $stationCode;?>"></td>
                    <td><input type="submit" value="SUBMIT"></td>
                </tr>
            </table>
        </form>
    </center>
    <?php
   if(isset($_SESSION['fmsg']))
    {
		echo $_SESSION['fmsg'];
		unset($_SESSION['fmsg']);
	}
 
 ?>
</body>
</html>