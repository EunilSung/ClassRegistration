<?php
$con = mysqli_connect("localhost", "suneunil93", "Tjddmsdlf93", "suneunil93");
$userID = $_POST["userID"];
$userPassword = $_POST["userPassword"];
$userGender = $_POST["userGender"];
$userDepartment = $_POST["userDepartment"];
$userEmail = $_POST["userEmail"];
$statement = mysqli_prepare($con, "INSERT INTO TB_CLASS_USER VALUES (?,?,?,?,?)");
mysqli_stmt_bind_param($statement, "sssss", $userID, $userPassword, $userGender,
    $userDepartment, $userEmail);
$response = array();
$response["success"] = mysqli_stmt_execute($statement);
echo json_encode($response);
mysqli_close($con);
?>