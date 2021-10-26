<?php
session_start();
include("conn.php");
if($conn === false){
    die("CONNECTION ERROR " . mysqli_connect_error());
}
if(isset($_POST['train_no'])){
  $train_no = $_POST['train_no'];
  $train_name = $_POST['train_name'];
  $train_desc = $_POST['type'];

  $qry = "INSERT INTO `tbl_train`(`train_no`, `train_name`, `train_desc`) VALUES($train_no, '$train_name', '$train_desc');";
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
    <title>Insert Train Details</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="table.css">
    <script>
    function validateForm(){
        var x = document.forms['trainForm']['train_no'].value;
        var y = document.forms['trainForm']['train_name'].value;
        var z = doucument.forms['trainForm']['train_desc'].value;
        if(x == "" || y == "" || z == ""){
            alert("Fields must be filled out.");
            return false;
        }
    }
    </script>
</head>
<body>
    <center>
      <form  name="trainForm" action="<?php echo $_SERVER['PHP_SELF'];?>" method="post" onsubmit="return validateForm()">
        <table class="table">
          <tr class="row">
            <td class="cell">Train Number</td>
            <td class="cell"><input type="text" size="20" name="train_no" required></td>
          </tr>
          <tr class="row">
            <td class="cell">Train Name</td>
            <td class="cell"><input type="text" size="20" name="train_name" required></td>
          </tr>
          <tr class="row">
            <td class="cell">Train Description</td>
            <td>
              <input type="radio" name="type" value="Express">Express&nbsp;
              <input type="radio" name="type" value="Local" checked="checked">Local
            </td>
          </tr>
          <tr>
            <td colspan="2" class="cell"><input type="submit" value="Submit" name="submit"></td>
          </tr>
        </table>
      </form>
    </center>

</body>
</html>
