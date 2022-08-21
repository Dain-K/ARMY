<?php

$host = "localhost";
$user = "root";
$pw = "1234";
$table = "mydb";
$con = mysqli_connect($host,$user,$pw,$table);
$uid = $_POST["user_id"];
$birth = $_POST["birth"];
// $uid = 1872004496;
// $birth = 980120;
mysqli_query($con,'SET NAMES utf8');
$sql = "SELECT * FROM user as u LEFT JOIN unit as un on u.unit_unit_code = un.unit_code   WHERE user_id = ? and birth = ? ;";

$statement = mysqli_prepare($con, $sql);
mysqli_stmt_bind_param($statement, "ss", $uid, $birth);
mysqli_stmt_execute($statement);

mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $userID, $user_birth,$uclass,$unit, $userName,$token,$nono,$unit_name);
echo $userName;
$response = array();
$response["success"] = false;

while(mysqli_stmt_fetch($statement)) {
    $response["success"] = true;
    $response["userID"] = $userID;
    $response["birth"] = $user_birth;
    $response["userName"] = $userName;
    $response["uclass_code"] = $uclass;
    $response["unit_code"] = $unit;
    $response["unit_name"] = $unit_name;
}

echo json_encode($response,JSON_UNESCAPED_UNICODE);



?>
