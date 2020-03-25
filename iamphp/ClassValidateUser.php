<?php
//ClassValidateUser.php
$con = mysqli_connect("localhost", "suneunil93", "Tjddmsdlf93", "suneunil93");
$userID = $_POST["userID"];
$statement = mysqli_prepare($con, "SELECT ID FROM TB_CLASS_USER WHERE ID=?");

mysqli_stmt_bind_param($statement, "s", $userID);
mysqli_stmt_execute($statement);
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $userID);
$response = array();
$response["success"] = true;
while (mysqli_stmt_fetch($statement)) {
    $response["success"] = false;
}
echo json_encode($response);
mysqli_close($con);
?>