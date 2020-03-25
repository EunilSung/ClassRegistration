<?php
//ClassNoticeList.php
$con = mysqli_connect("localhost", "suneunil93", "Tjddmsdlf93", "suneunil93");
$result = mysqli_query($con, "SELECT * FROM TB_CLASS_NOTICE ORDER BY DATE DESC;");
$response = array();
while ($row = mysqli_fetch_array($result)) {
    array_push($response, array("title" => $row[0], "name" => $row[1], "date" => $row[2]));
}
echo json_encode(array("response" => $response));
mysqli_close($con);
?>