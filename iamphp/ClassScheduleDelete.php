<?php
//ClassScheduleDelete.php
$con = mysqli_connect("localhost", "suneunil93", "Tjddmsdlf93", "suneunil93");
$userID = $_POST["userID"];
$courseID = $_POST["courseID"];
$statement = mysqli_prepare($con, "DELETE FROM TB_CLASS_SCHEDULE WHERE USERID='$userID' AND COURSEID
='$courseID'");
mysqli_stmt_bind_param($statement, "si", $userID, $courseID);
$response = array();
$response["success"] = mysqli_stmt_execute($statement);
echo json_encode($response);
mysqli_close($con);
?>