<?php
    while ($row = mysqli_fetch_assoc($sql)) {
        ($row['status'] == "Offline now") ? $offline = "offline" : $offline = "";

        $output .= '<a href="chat.php?user_id='.$row["unique_id"].'">
                    <div class="content">
                        <img src="./php/images/'. $row["img"] .'" alt="">
                        <div class="details">
                            <span>' . $row["fname"] . " " . $row["lname"] .'</span>
                            <p>'. $row["status"] .'</p>
                        </div>
                    </div>
                    <div class="status-dot '. $offline .'"><i class="fas fa-circle"></i></div>
                    </a>';
    }

?>