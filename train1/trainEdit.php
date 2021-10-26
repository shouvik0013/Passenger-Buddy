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
    $train_no = $_POST['train_no'];
    $train_name = $_POST['train_name'];
    $train_desc = $_POST['type'];

    $qry_update = "UPDATE `tbl_train` SET `train_no`=$train_no, `train_name`='$train_name', `train_desc`='$train_desc' WHERE `id`=$id;";

    // checking query
    if($conn->query($qry_update))
    {
        $_SESSION['msg'] = "Updated successfully";
        header("location:trainIndex.php");
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
      $qry = "SELECT `train_no`, `train_name`, `train_desc` FROM tbl_train WHERE `id`=$id;";
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
      $train_no = $row['train_no'];
      $train_name = $row['train_name'];
      $train_desc = $row['train_desc'];
    ?>

    <center>
      <form action="<?php echo $_SERVER['PHP_SELF'];?>?idd=222" method="post">
        <table>
          <tr>
            <td>ID</td>
            <td>Train Number</td>
            <td>Train Name</td>
            <td>Tran Description</td>
            <td>Perform</td>
          </tr>
          <tr>
            <td><?php echo $id;?></td>
            <input type="hidden" name="id" value="<?php echo $id;?>">
            <td><input type="text" name="train_no" value="<?php echo $train_no;?>"></td>
            <td><input type="text" name="train_name" value="<?php echo $train_name;?>"></td>
            <td>
              <!-- <input type="text" name="train_desc" value="<?php echo $train_desc;?>" -->
              <input type="radio" name="type" value="Express">Express<br>
              <input type="radio" name="type" value="Local" checked="checked">Local
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
