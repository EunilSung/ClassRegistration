<?php 
// ClassStatList.php
header("Content-Type: text/html; charset=UTF-8");
$con = mysqli_connect("localhost", "suneunil93", "Tjddmsdlf93", "suneunil93");
$userID = $_GET["userID"]; $semester = $_GET["semester"];
$result = mysqli_query($con, "SELECT TB_CLASS_COURSE.ID, TB_CLASS_COURSE.DEPARTMENT,
 TB_CLASS_CO URSE.NAME, TB_CLASS_COURSE.CAPACITY, TB_CLASS_COURSE.CREDIT,
 COUNT(TB_CLASS_SCHEDULE.COURSEID) FROM TB_CLASS_COURSE,
 TB_CLASS_SCHEDULE WHERE TB_CLASS_SCHEDULE.COURSEID IN 
(SELECT TB_CLASS_SCHEDULE.COURSEID FROM TB_CLASS_SCHEDULE WHERE USERID='$userID')
 AND TB_CLASS_SCHEDULE.COURSEID=TB_CLASS_COURSE.ID AND TB_CLASS_COURSE.SEMESTER='$semester' GROUP BY TB_CLASS_SCHEDULE.COURSEID");
$response = array();
while ($row = mysqli_fetch_array($result)) {
    array_push($response, array(
        "courseID" => $row[0],
        "department" => $row[1],
        "courseName" => $row[2],
        "capacity" => $row[3],
        "credit" => $row[4],
        "applicant" => $row[5]
    ));
}
echo json_encode(array(
    "response" => $response
), JSON_UNESCAPED_UNICODE);
mysqli_close($con);
?>