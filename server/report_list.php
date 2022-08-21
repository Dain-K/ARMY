<?php
    $host = "localhost";
    $user = "root";
    $pw = "1234";
    $table = "mydb";
    $con = mysqli_connect($host, $user, $pw, $table);
    $unit_code = $_POST['unit_unit_code'];
    $select_date = $_POST['select_date'];
    $sql = "SELECT * FROM mydb.report as re 
    LEFT JOIN mydb.user as u ON u.user_id = re.user_user_id 
    WHERE re.report_date = $select_date AND u.unit_unit_code = $unit_code";

    $result = mysqli_query($con,$sql);

    $response = array();
    //$response["success"] = false;
    $count = 0;
    while($row = mysqli_fetch_array($result)) {
        $response[$count]["report_date"] = $row[0];
        $response[$count]["report_time"] = $row[1];
        $response[$count]["report_check"] = $row[4];
        $response[$count]["user_user_id"] = $row[5];
        $count++;
    }

    echo json_encode($response,JSON_UNESCAPED_UNICODE);



?>
