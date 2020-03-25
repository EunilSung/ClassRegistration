<?php
//ClassCourseAdd.php
$con = mysqli_connect("localhost", "suneunil93", "Tjddmsdlf93", "suneunil93");
$userID = $_POST["userID"];
$courseID = $_POST["courseID"];
$statement = mysqli_prepare($con, "INSERT INTO TB_CLASS_SCHEDULE VALUES (?,?)");
mysqli_stmt_bind_param($statement, "si", $userID, $courseID);
$response = array();
$response["success"] = mysqli_stmt_execute($statement);
echo json_encode($response);
mysqli_close($con);
?>