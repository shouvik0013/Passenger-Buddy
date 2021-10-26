<?php
session_start();
$conn = new mysqli('localhost', 'root', '', 'my_train_db');
if($conn === false){
    echo "Connection can not be established".mysqli_connect_errno();
}
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
    $train_no = $_POST['train_no'];
    $train_name = $_POST['train_name'];
    $statSunday;
    $statMonday;
    $statTuesday;
    $statWednesday;
    $statThursday;
    $statFriday;
    $statSaturday;

    if(isset($_POST['sun'])){
      $statSunday = 1;
    } else{
      $statSunday = 0;
    }
    if(isset($_POST['mon'])){
      $statMonday = 1;
    } else{
      $statMonday = 0;
    }
    if(isset($_POST['tue'])){
      $statTuesday = 1;
    } else{
      $statTuesday = 0;
    }
    if(isset($_POST['wed'])){
      $statWednesday = 1;
    } else{
      $statWednesday = 0;
    }
    if(isset($_POST['thu'])){
      $statThursday = 1;
    } else{
      $statThursday = 0;
    }
    if(isset($_POST['fri'])){
      $statFriday = 1;
    } else{
      $statFriday = 0;
    }
    if(isset($_POST['sat'])){
      $statSaturday = 1;
    } else{
      $statSaturday = 0;
    }
    $qry_update = "UPDATE `train_table2` SET `trainNo`=$train_no, `trainName`='$train_name', `sun`=$statSunday,
      `mon`=$statMonday, `tue`=$statTuesday, `wed`=$statWednesday, `thu`=$statThursday,
      `fri`=$statFriday, `sat`=$statSaturday
      WHERE `id`=$id;";

    // checking query
    if($conn->query($qry_update))
    {
        $_SESSION['msg'] = "Updated successfully";
        header("location:trainEditDelete.php");
    }else{
        $_SESSION['fmsg']="Failed" ;
        header("location:".$_SERVER['PHP_SELF']);
        echo("ERROR");
        exit();
    }

  }
?>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title></title>
  </head>
  <body>
    <?php
      $id = $_GET['id'];
      $qry = "SELECT * FROM `train_table2` WHERE `id`=$id;";
      // CHECKING THE QUERY IS EXECUTABLE OR NOT
      if(!($result = $conn->query($qry))){
          echo "Couldn't execute $qry".$conn->errno;
          exit("ERROR");
      }
      // check result has any rows or not
      if(!($result->num_rows > 0)){
      echo "No record matching your query were found.";
      die("ERROR");
      }

      // Get information train
      $row = $result->fetch_array();
      $trainNo = $row['trainNo'];
      $trainName = $row['trainName'];
      $sun = $row['sun'];
      $mon = $row['mon'];
      $tue = $row['tue'];
      $wed = $row['wed'];
      $thu = $row['thu'];
      $fri = $row['fri'];
      $sat = $row['sat'];

    ?>

    <center>
      <form action="<?php echo $_SERVER['PHP_SELF'];?>?idd=222" method="post">
        <table>
          <tr>
            <td>ID</td>
            <td>Train Number</td>
            <td>Train Name</td>
            <td>Sunday</td>
            <td>Monday</td>
            <td>Tuesday</td>
            <td>Wednesday</td>
            <td>Thursday</td>
            <td>Friday</td>
            <td>Saturday</td>
            <td>Perform</td>
          </tr>
          <tr>
            <td><?php echo $id;?></td>
            <input type="hidden" name="id" value="<?php echo $id;?>">
            <td><input type="text" name="train_no" value="<?php echo $trainNo;?>"></td>
            <td><input type="text" name="train_name" value="<?php echo $trainName;?>"></td>
            <td>
              <?php
                if($sun=='1'){
                  echo "<input type='checkbox' name='sun' checked>";
                } else{
                  echo "<input type='checkbox' name='sun'>";
                }
              ?>
            </td>
            <td>
              <?php
                if($sun=='1'){
                  echo "<input type='checkbox' name='mon' checked>";
                } else{
                  echo "<input type='checkbox' name='mon'>";
                }
              ?>
            </td>
            <td>
              <?php
                if($sun=='1'){
                  echo "<input type='checkbox' name='tue' checked>";
                } else{
                  echo "<input type='checkbox' name='tue'>";
                }
              ?>
            </td>
            <td>
              <?php
                if($sun=='1'){
                  echo "<input type='checkbox' name='wed' checked>";
                } else{
                  echo "<input type='checkbox' name='wed'>";
                }
              ?>
            </td>
            <td>
              <?php
                if($sun=='1'){
                  echo "<input type='checkbox' name='thu' checked>";
                } else{
                  echo "<input type='checkbox' name='thu'>";
                }
              ?>
            </td>
            <td>
              <?php
                if($sun=='1'){
                  echo "<input type='checkbox' name='fri' checked>";
                } else{
                  echo "<input type='checkbox' name='fri'>";
                }
              ?>
            </td>
            <td>
              <?php
                if($sun=='1'){
                  echo "<input type='checkbox' name='sat' checked>";
                } else{
                  echo "<input type='checkbox' name='sat'>";
                }
              ?>
            </td>
            <td><input type="submit" value="UPDATE"></td>
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
