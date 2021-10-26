<?php
    include("conn.php");
    if(isset($_GET['delid'])){
        $id = $_GET['delid'];
        $qry3 = "DELETE FROM `tbl_train` WHERE `id`='$id'";
        if($conn->query($qry3)){
            $_SESSION['msg'] = 'Deleted successfully';
            header('location:trainIndex.php');
        }
        else{
            $_SESSION['fmsg'] = "Could not be deleted";
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
    <title>Page Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="table2.css">
    <script>
        function validate(){
                var temp = confirm("Delete?");
                if(temp)
                    return true;
                else
                    return false;

        }
    </script>
</head>
<body>
  <center>
    <table class="table">
      <tr id="row1">
        <td>ID</td>
        <td>Train Number</td>
        <td>Train Name</td>
        <td>Train Description</td>
        <td colspan="2">Perform</td>
      </tr>
      <?php
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
        // Creating query
        $qry = "SELECT * FROM tbl_train;";
        // check the query is executable or not
        // Check the query is executable or not
        if(!($result = $conn->query($qry))){
          echo "Could not execute $qry".$conn->errno;
          die("ERROR");
        }
        // check result has any rows or not
        if(!($result->num_rows > 0)){
            echo "No record matching your query were found.";
            die("ERROR");
        }
        // LOOP THROUGH THE RESULT
        while($row = $result->fetch_array())
        {
       ?>
        <tr>
          <td><?php echo $row['id']; ?></td>
          <td><?php echo $row['train_no'] ?></td>
          <td><?php echo $row['train_name'] ?></td>
          <td><?php echo $row['train_desc'] ?></td>
          <td><a href="trainEdit.php?id=<?php echo $row['id'];?>"><img src="edit.png" alt="Edit" width="30" height="30"></a></td>
          <td><a href="<?php echo $_SERVER['PHP_SELF'];?>?delid=<?php echo $row['id'];?>" onclick="return validate();"><img src="delete.png" alt="Delete" width="30" height="30"></a></td>
        </tr>
        <?php
        }
        ?>
    </table>
    <?php
   if(isset($_SESSION['msg']))
    {
		echo $_SESSION['msg'];
		unset($_SESSION['msg']);
	  }
    ?>
  </center>
</body>
</html>
