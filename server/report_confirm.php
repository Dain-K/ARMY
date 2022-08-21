
<?php

$host= "localhost";
$user = "root";
$pw = "1234";
$table = "mydb";
$con = mysqli_connect($host, $user, $pw, $table);
$uid = $_POST['user_id'];
$date = $_POST['date'];
$isCheck = $_POST['isCheck'];
date_default_timezone_set("Asia/Seoul");

$report_check = 0;
$response = array();
$response['success'] = false;
$sql = "UPDATE report SET report_check = '$isCheck' WHERE user_user_id = '$uid' AND report_date = '$date';";

$result = mysqli_query($con, $sql);
if(mysqli_affected_rows($con)>0)
{
    $response["success"] = true;
}
echo json_encode($response);




?>
