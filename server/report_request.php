<?php
    $host = "localhost";
    $user = "root";
    $pw = "1234";
    $table = "mydb";
    $con = mysqli_connect($host, $user, $pw, $table);
    $uid = $_POST['user_id'];
    date_default_timezone_set("Asia/Seoul");
    //$uid = 1872004496;
    $today_date = date("Ymd");
    $sql = "SELECT * FROM report WHERE report_date = '$today_date' AND user_user_id = '$uid';";

    $result = mysqli_query($con, $sql);

    $response = array();
    

    // 값이 있는지 없는지 확인
    if($result->num_rows >0){ // 값이 있을 때

        
        
        while($row = mysqli_fetch_array($result)) {
            $response["success"] = true;
            $response["report_date"] = $row[0];
            $response["report_time"] = $row[1];
            $response["content"] = $row[2];
            $response["report_check"] = $row[4];
            $response["user_id"] = $row[5];
            $response["uAddress"] = $row[3];

            
        }
        echo json_encode($response,JSON_UNESCAPED_UNICODE);

    }else{ // 값이 없을 때
        $response["success"] = false;
        echo json_encode($response);
    }


    


?>
