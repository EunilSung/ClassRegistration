<?php
//ClassCourseList.php
$con = mysqli_connect("localhost", "suneunil93", "Tjddmsdlf93", "suneunil93");
$semester = $_GET["semester"];
$department = $_GET["department"];
$result = mysqli_query($con, "SELECT * FROM TB_CLASS_COURSE WHERE SEMESTER='$semester' AND
DEPARTMENT='$department';");
$response = array();
while ($row = mysqli_fetch_array($result)) {
    array_push($response, array("courseID" => $row[0], "semester" => $row[1], "department" =>
        $row[2], "name" => $row[3], "credit" => $row[4], "mon" => $row[5], "tue" => $row[6], "wed"
        => $row[7], "thu" => $row[8], "fri" => $row[9]));
}
echo json_encode(array("response" => $response));
mysqli_close($con);
?>
