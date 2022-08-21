<?php
    $host = "localhost";
    $user = "root";
    $pw = "1234";
    $table = "mydb";
    $con = mysqli_connect($host, $user, $pw, $table);
    $user_user_id = $_POST['user_user_id'];
    $report_date = $_POST['report_date'];
    
    $sql = "SELECT * FROM mydb.report as re 
    LEFT JOIN mydb.user as u ON u.user_id = re.user_user_id 
    WHERE re.report_date = $report_date AND u.user_id = $user_user_id";

    $result = mysqli_query($con,$sql);

    $response = array();
    $response["success"] = false;
    
    while($row = mysqli_fetch_array($result)) {
        $response["success"] = true;
        $response["name"] = $row[10];
        $response["user_user_id"] = $row[5];
        $response["report_date"] = $row[0];
        $response["report_time"] = $row[1];
        $response["content"] = $row[2];
        $response["uAddress"] = $row[3];
        $response["report_check"] = $row[4];
        
        
    }

    echo json_encode($response,JSON_UNESCAPED_UNICODE);



?>