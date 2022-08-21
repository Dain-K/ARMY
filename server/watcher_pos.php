<?php
    $host = "localhost";
    $user = "root";
    $pw = "1234";
    $table = "mydb";
    $con = mysqli_connect($host, $user, $pw, $table);
    // $unit_code = $_POST['unit_unit_code'];
    $unit_code = 1234;
    $sql = "SELECT * FROM user WHERE uclass_uclass_code >= 503 AND unit_unit_code = $unit_code";

    $result = mysqli_query($con,$sql);

    $response = array();
    //$response["success"] = false;
    $count = 0;
    while($row = mysqli_fetch_array($result)) {
        $response[$count]["userID"] = $row[0];
        $response[$count]["birth"] = $row[1];
        $response[$count]["userName"] = $row[4];
        $response[$count]["uclass_code"] = $row[2];
        $response[$count]["unit_code"] = $row[3];
        $count++;
    }

    echo json_encode($response,JSON_UNESCAPED_UNICODE);



?>
