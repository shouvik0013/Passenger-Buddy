<?php

$conn = new mysqli('localhost', 'root', '', 'db_train');
if($conn === false){
    die("ERROR: Unable to access database " . mysqli_connect_error());
}
?>
