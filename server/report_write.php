
<?php

    $host = "localhost";
    $user = "root";
    $pw = "1234";
    $table = "mydb";
    $con = mysqli_connect($host, $user, $pw, $table);
    $uid = $_POST['user_user_id'];
    date_default_timezone_set("Asia/Seoul");
    //$report_date = date("Ymd"); // 20220820
    $report_time = $_POST['report_time'];
    $content = $_POST['content'];
    
    //$latitude = $_POST['latitude'];
    //$longitude = $_POST['longitude'];
    $address = $_POST['address'];
    
    $check = 0;
    /*
    $uid = 1872004496;
    $content = "테스트해보겠습니다. 맛있는 밥 먹고 놀러가고 싶다 정말 하하";
    $img_url = "23001394829384we892sd0.jpg";
    $latitude = 4.2938;
    $longitude = 17.2938;
    $report_time = 2030; 
    */
    $report_date = date("Ymd"); // 20220820
    

    $report_check = 0;
    $response = array();
    $response['success'] = false;
    $sql = "INSERT into report (report_date, report_time, content, uAddress, report_check, user_user_id)
            values ('{$report_date}', '{$report_time}','{$content}','{$address}','{$report_check}', '{$uid}')
            ON DUPLICATE KEY UPDATE report_time= '$report_time', content= '$content', uAddress='$address', report_check=0;";

    $result = mysqli_query($con, $sql);
    if(mysqli_affected_rows($con)>0)
    {
        $response["success"] = true;
    }
    echo json_encode($response);

    
   

?>
