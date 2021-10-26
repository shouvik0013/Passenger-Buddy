<?php
echo "The time in " . date_default_timezone_get() . " is " . date("H:i:s");
echo"<br>";
date_default_timezone_set("Asia/Calcutta");
echo "The time in " . date_default_timezone_get() . " is " . date("H:i:s") . "<br>";
echo date("l jS \of F Y h:i:s A") . "<br>";
echo date("l j \of F Y h:i:s A") . "<br>";
echo date("j-m-Y h:i:s A") . "<br>";
$my_time = date("j-m-Y h:i:s A");
echo $my_time;
?>