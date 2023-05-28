<?php
    session_start();
    include "config.php";
    $unique_id = $_SESSION["unique_id"];
    $sql = mysqli_query($conn, "SELECT * FROM users WHERE unique_id != {$unique_id}");
    
    $output = "";

    if (mysqli_num_rows($sql) == 0) {
        $output .= "No users are available to chat.";
    } elseif (mysqli_num_rows($sql) > 0){
        include "data.php";
    }

    echo $output;
?>