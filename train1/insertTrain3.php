<?php
session_start();
$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}
if(isset($_POST['train_no'])){

  $train_no = $_POST['train_no'];
  $train_name = $_POST['train_name'];

  // CHECKING TRAIN RUNNING STATUS
  $sun;
  $mon;
  $tue;
  $wed;
  $thu;
  $fri;
  $sat;
  if(isset($_POST['sun'])){
    $sun = 1;
  } else{
    $sun = 0;
  }
  if(isset($_POST['mon'])){
    $mon = 1;
  } else{
    $mon = 0;
  }
  if(isset($_POST['tue'])){
    $tue = 1;
  } else{
    $tue = 0;
  }
  if(isset($_POST['wed'])){
    $wed = 1;
  } else{
    $wed = 0;
  }
  if(isset($_POST['thu'])){
    $thu = 1;
  } else{
    $thu = 0;
  }
   if(isset($_POST['fri'])){
    $fri = 1;
  } else{
    $fri = 0;
  }
  if(isset($_POST['sat'])){
    $sat = 1;
  } else{
    $sat = 0;
  }




  $qry = "INSERT INTO `train_table2`(`trainNo`, `trainName`, `sun`, `mon`,
  `tue`,`wed`, `thu`, `fri`, `sat`) VALUES($train_no, '$train_name', $sun,
  $mon, $tue, $wed, $thu, $fri, $sat);";
  if($conn->query($qry) === true){
    echo "Insert&nbsp;successful " . $conn->insert_id;
      header("location:insertRouteDetails.php");
  } else{
    echo "Could&nbsp;not execute the query." . $conn->connect_errno;
  }
}
$conn->close();
?>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title></title>
    <style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;border-color:#aabcfe;}
.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#669;background-color:#e8edff;}
.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#039;background-color:#b9c9fe;}
.tg .tg-s268{text-align:left}
.tg .tg-0lax{text-align:left;vertical-align:top}
</style>

<script>
function validateForm(){
    var x = document.forms['trainForm']['train_no'].value;
    var y = document.forms['trainForm']['train_name'].value;
    if(x == "" || y == ""){
        alert("Fields must be filled out.");
        return false;
    }
}
</script>


  </head>
  <body>
    <div class="">
      <center>
        <form name="trainForm" action="<?php echo $_SERVER['PHP_SELF'];?>" method="post" onsubmit="return validateForm()">
          <table class="tg">
            <tr>
              <th class="tg-s268"></th>
              <th class="tg-0lax"></th>
            </tr>
            <tr>
              <td class="tg-s268">Train Number</td>
              <td class="tg-0lax"><input type="text" size="20" name="train_no" required></td>
            </tr>
            <tr>
              <td class="tg-0lax">Train Name</td>
              <td class="tg-0lax"><input type="text" size="20" name="train_name" required></td>
            </tr>
            <!--
            <tr>
              <td class="tg-0lax">Train Description</td>
              <td class="tg-0lax"><input type="radio" name="type" value="Express">Express&nbsp;
              <input type="radio" name="type" value="Local" checked="checked">Local</td>
            </tr>
          -->
            <tr>
              <td class="tg-0lax">Train Running Status:</td>
              <td class="tg-0lax">Sunday:<input type="checkbox" name="sun" value="1" checked>&nbsp;
              Monday:<input type="checkbox" name="mon" value="1" checked>&nbsp;
              Tuesday:<input type="checkbox" name="tue" value="1" checked>&nbsp;
              Wednesday:<input type="checkbox" name="wed" value="1" checked>&nbsp;
              Thursday:<input type="checkbox" name="thu" value="1" checked>&nbsp;
              Friday:<input type="checkbox" name="fri" value="1" checked>&nbsp;
              Saturday:<input type="checkbox" name="sat" value="1" checked></td>
            </tr>
            <tr>
              <tr>
                <td colspan="2" class="tg-0lax" display: block;
margin: auto;><input type="submit" value="Submit" name="submit"></td>
              </tr>
            </tr>
          </table>
        </form>
      </center>
    </div>
  </body>
</html>
