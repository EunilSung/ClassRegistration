<?php
//ClassScheduleList.php
header("Content-Type: text/html; charset=UTF-8");
$con = mysqli_connect("localhost", "suneunil93", "Tjddmsdlf93", "suneunil93");
$userID = $_GET["userID"];
$semester = $_GET["semester"];
$result = mysqli_query($con, "SELECT TB_CLASS_COURSE.ID, TB_CLASS_COURSE.MON, TB_CLASS_COURSE.TUE, TB
_CLASS_COURSE.WED, TB_CLASS_COURSE.THU, TB_CLASS_COURSE.FRI, TB_CLASS_COURSE.NAME
FROM TB_CLASS_USER, TB_CLASS_COURSE, TB_CLASS_SCHEDULE
WHERE TB_CLASS_USER.ID='$userID' AND TB_CLASS_USER.ID=TB_CLASS_SCHEDULE.USERID AND TB_CLASS_SCHEDULE.
COURSEID=TB_CLASS_COURSE.ID AND TB_CLASS_COURSE.SEMESTER='$semester';" );
$response = array();
while ($row = mysqli_fetch_array($result)) {
    array_push($response, array("courseID" => $row[0], "mon" => $row[1], "tue" => $row[2], "wed" => $row[
        3], "thu" => $row[4], "fri" => $row[5], "courseName" => $row[6]));
}
echo json_encode(array("response" => $response), JSON_UNESCAPED_UNICODE);
mysqli_close($con);
?>